package co.com.bancolombia.tasks;

import co.com.bancolombia.exceptions.ScreenPlayException;
import co.com.bancolombia.tasks.annotations.CATask;
import co.com.bancolombia.utils.Constants.ProjectType;
import co.com.bancolombia.utils.Constants.JavaVersion;
import co.com.bancolombia.utils.Constants.Language;
import co.com.bancolombia.utils.Constants.BooleanOption;
import static co.com.bancolombia.utils.Constants.BUILD_GRADLE;

import co.com.bancolombia.utils.FileUtil;
import co.com.bancolombia.utils.Util;
import org.gradle.api.tasks.options.Option;
import org.gradle.api.tasks.options.OptionValues;


import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@CATask(
        name = "screenPlayArchitecture",
        shortCut = "spa",
        description = "Scaffolding ScreePlay architecture project"
)
public class GenerateArchitectureDefaultTask extends AbstracScreenPlayArchitectureDefaultTask{
    private String packageName = "co.com.bancolombia.certificacion";
    private ProjectType type = ProjectType.UX;
    private String name = "ScreenPlayArchitecture";
    private BooleanOption lombok = BooleanOption.TRUE;
    private BooleanOption force = BooleanOption.FALSE;
    private Language language = Language.JAVA;
    private JavaVersion javaVersion = JavaVersion.VERSION_11;

    @Option(option = "package", description = "Set principal package to use in the project")
    public void setPackageName(String packageName) { this.packageName = this.packageName + packageName; }

    /*@Option(option = "aplicationName", description = "Set application name or MAC")
    public void aplicationName(String aplicationName) { this.aplicationName = aplicationName; }*/

    @Option(option = "type", description = "Set project type to implement")
    public void setType(ProjectType type) { this.type = type; }

    @Option(option = "name", description = "Set project name, by default is ScreenPlayArchitecture")
    public void name(String name) { this.name = name; }

    @Option(option = "lombok", description = "Switch the status of lombok in this project")
    public void lombok(BooleanOption lombok) { this.lombok = lombok; }

    @Option(option = "language", description = "Set code language project, by default is Java")
    public void language(Language language) { this.language = language; }

    @Option(option = "javaVersion", description = "Set Java version")
    public void javaVersion(JavaVersion javaVersion) { this.javaVersion = javaVersion; }

    @Option(option = "force", description = "Force regenerates all files")
    public void setForce(BooleanOption force) {
        this.force = force;
    }


    /*@OptionValues("lombok")
    public List<BooleanOption> getLombok(BooleanOption lombok) { return Arrays.asList(BooleanOption.values());
    }

    @OptionValues("language")
    public List<Language> getLanguage(Language language) { return Arrays.asList(Language.values()); }

    @OptionValues("javaVersion")
    public List<JavaVersion> getJavaVersion(JavaVersion javaVersion) { return Arrays.asList(JavaVersion.values()); }

    @OptionValues("force")
    public List<BooleanOption> getForce(BooleanOption force) { return Arrays.asList(BooleanOption.values()); }*/

    @Override
    public void execute() throws IOException, ScreenPlayException {
        logger.lifecycle("ScreenPlay architecture plugin version: {}" , Util.getVersionPlugin());
        logger.lifecycle("Package: {}" , packageName);
        logger.lifecycle("Project type: {}" , type);
        logger.lifecycle("Java Version: {}" , javaVersion);
        logger.lifecycle("Project Name: {}" , name);
        builder.addParamPackage(packageName);
        builder.addParam("projectName", name);
        builder.addParam("lombok", lombok == BooleanOption.TRUE);
        builder.addParam("language", language.name().toLowerCase());
        builder.addParam("javaVersion", javaVersion);
        builder.addParam("java11", javaVersion == JavaVersion.VERSION_11);

        Boolean exists = FileUtil.exists(builder.getProject().getProjectDir().getPath(), BUILD_GRADLE);
        if (exists && force == BooleanOption.FALSE){
            logger.lifecycle("Another project was found in the same directory, rewriting exist files ");
            builder.setupFromTemplate("structure");
        }
        builder.persist();
    }
}
