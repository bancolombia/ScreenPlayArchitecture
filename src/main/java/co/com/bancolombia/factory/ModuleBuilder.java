package co.com.bancolombia.factory;


import co.com.bancolombia.exceptions.ParamNotFoundException;
import co.com.bancolombia.models.FileModel;
import co.com.bancolombia.models.Release;
import co.com.bancolombia.models.TemplateDefinition;
import co.com.bancolombia.utils.FileUtil;
import co.com.bancolombia.utils.Util;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import com.github.mustachejava.resolver.DefaultResolver;
import lombok.Getter;
import lombok.Setter;
import org.gradle.api.Project;
import org.gradle.api.logging.Logger;
import org.gradle.internal.logging.text.StyledTextOutput;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.gradle.internal.logging.text.StyledTextOutput.Style.*;

public class ModuleBuilder {

    @Getter
    private Project project;
    @Setter
    private StyledTextOutput styledLogger;
    private final Map<String, Object> params = new HashMap<>();
    private final DefaultResolver resolver = new DefaultResolver();
    private static final String DEFINITION_FILES = "definition.json";
    private final ObjectMapper mapper = new ObjectMapper();
    private final List<String> dirs = new ArrayList<>();
    private final MustacheFactory mustacheFactory = new DefaultMustacheFactory();
    private final Map<String, FileModel> files = new ConcurrentHashMap<>();
    private final List<String> dirsToDelete = new ArrayList<>();
    private final Logger logger;
    public static final String LATEST_RELEASE = "latestRelease";




    public ModuleBuilder(Project project){
        this.project = project;
        this.logger = getProject().getLogger();

    }

    public void addParamPackage(String packageName) {
        this.params.put("package", packageName.toLowerCase());
        this.params.put("packagePath", packageName.replace('.', '/').toLowerCase());
    }

    public void addParam(String key, Object value) {
        this.params.put(key, value);
    }

    public void addDir(String path) {
        if (path != null) {
            this.dirs.add(path);
        }
    }

    public void addFile(String path, String content) {
        String finalPath = FileUtil.toRelative(path);
        this.files.put(finalPath, FileModel.builder().path(finalPath).content(content).build());
    }

    public void setupFromTemplate(String resourceGroup) throws IOException, ParamNotFoundException {
        TemplateDefinition templateDefinition = loadTemplateDefinition(resourceGroup);

        for (String folder : templateDefinition.getFolders()){
            addDir(Util.fillPath(folder, params));
        }
        Map<String, String> projectFiles = new HashMap<>(templateDefinition.getFiles());
        projectFiles.putAll(templateDefinition.getJava());

        for (Map.Entry<String, String> fileEntry : projectFiles.entrySet()){
            String path = Util.fillPath(fileEntry.getValue(), params);
            String content = buildFromTemplate(fileEntry.getKey());
            addDir(Util.extractDir(path));
            addFile(path, content);
        }
    }

    public void persist() throws IOException{
        styledLogger.style(StyledTextOutput.Style.Header).println("Applying changes on disk");

        styledLogger
                .style(StyledTextOutput.Style.Header)
                .append("Files: ")
                .style(StyledTextOutput.Style.Success)
                .append(Integer.toString(files.size()))
                .style(StyledTextOutput.Style.Header)
                .append(", dirs: ")
                .style(StyledTextOutput.Style.Success)
                .append(", deleted dirs; ")
                .style(StyledTextOutput.Style.Success)
                .append(Integer.toString(dirsToDelete.size()))
                .println();

        dirs.forEach(dir -> {
            getProject().mkdir(dir);
            logger.debug("Creating dir {}", dir);
        });

        files.forEach(
                (key, file) -> {
                    try {
                        FileUtil.writeString(getProject(), file.getPath(), file.getContent());
                    } catch (IOException e) {
                        logger.error("error to write file {}", file.getPath());
                        throw new RuntimeException(e.getMessage(), e);
                    }
                    logger.debug("file {} written", file.getPath());
                });
        styledLogger.style(StyledTextOutput.Style.Success).println("Changes successfully applied");
        //getLatestRelease();
    }

    private TemplateDefinition loadTemplateDefinition(String resourceGroup) throws IOException {
        String targetString = FileUtil.getResourceAsString(resolver, Util.joinPath(resourceGroup, DEFINITION_FILES));
        return mapper.readValue(targetString, TemplateDefinition.class);
    }

    private String buildFromTemplate(String resource) {
        Mustache mustache = mustacheFactory.compile(resource);
        StringWriter stringWriter = new StringWriter();
        mustache.execute(stringWriter, params);
        return stringWriter.toString();
    }

    /*public Release getLatestRelease() {
        if (params.get(LATEST_RELEASE) == null) {
            loadLatestRelease();
        }
        return (Release) params.get(LATEST_RELEASE);
    }*/
    /*private void loadLatestRelease() {
        Release latestRelease = restService.getLatestPluginVersion();
        if (latestRelease != null && !latestRelease.getTagName().equals(Utils.getVersionPlugin())) {
            styledLogger
                    .style(Description)
                    .append("You have an old version of the plugin ")
                    .style(Normal)
                    .append("the latest version is: ")
                    .style(Header)
                    .append(latestRelease.getTagName())
                    .style(Normal)
                    .append(" to update it please run: ")
                    .style(Success)
                    .append("gradle u")
                    .println();
            params.put(LATEST_RELEASE, latestRelease);
        }
    }*/
}
