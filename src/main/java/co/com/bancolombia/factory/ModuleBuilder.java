package co.com.bancolombia.factory;


import co.com.bancolombia.exceptions.ParamNotFoundException;
import co.com.bancolombia.models.FileModel;
import co.com.bancolombia.models.TemplateDefinition;
import co.com.bancolombia.utils.FileUtil;
import co.com.bancolombia.utils.Util;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import com.github.mustachejava.resolver.DefaultResolver;
import lombok.Getter;
import lombok.Setter;
import org.gradle.api.Project;
import org.gradle.internal.logging.text.StyledTextOutput;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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



    public ModuleBuilder(Project project){
        this.project = project;

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
}
