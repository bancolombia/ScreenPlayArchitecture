package co.com.bancolombia.tasks;

import co.com.bancolombia.exceptions.ScreenPlayException;
import co.com.bancolombia.tasks.annotations.CATask;
import co.com.bancolombia.utils.Constants;
import co.com.bancolombia.utils.Constants.ProjectType;
import co.com.bancolombia.utils.Constants.Language;
import co.com.bancolombia.utils.Constants.BooleanOption;
import static co.com.bancolombia.utils.Constants.BUILD_GRADLE;

import co.com.bancolombia.utils.FileUtil;
import co.com.bancolombia.utils.Util;
import org.gradle.api.tasks.options.Option;


import java.io.IOException;

@CATask(
        name = "screenPlayArchitecture",
        shortCut = "spa",
        description = "Scaffolding ScreePlay architecture project"
)
public class GenerateArchitectureDefaultTask extends AbstracScreenPlayArchitectureDefaultTask{
    private String groupId = "co.com.bancolombia.certificacion";
    private ProjectType type = ProjectType.UX;
    private String projectName = "Screenplay_architecture";
    private String principalPackage = "screen";
    private BooleanOption lombok = BooleanOption.TRUE;
    private BooleanOption force = BooleanOption.FALSE;
    private Language language = Language.JAVA;
    private int javaVersion = Constants.Java11;

    @Option(option = "groupId", description = "Set group id to use in the project")
    public void setgroupId(String groupId) { this.groupId = this.groupId; }

    @Option(option = "principalPackage", description = "Set name principal package on project")
    public void principalPackage(String principalPackage) { this.principalPackage = principalPackage; }

    @Option(option = "type", description = "Set project type to implement")
    public void setType(ProjectType type) { this.type = type; }

    @Option(option = "projectName", description = "Set project name, by default is ScreenPlayArchitecture")
    public void name(String projectName) { this.projectName = projectName; }

    @Option(option = "lombok", description = "Switch the status of lombok in this project")
    public void lombok(BooleanOption lombok) { this.lombok = lombok; }

    @Option(option = "language", description = "Set code language project, by default is Java")
    public void language(Language language) { this.language = language; }

    @Option(option = "javaVersion", description = "Set Java version")
    public void javaVersion(String javaVersion) { this.javaVersion = Integer.parseInt(javaVersion); }

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
        logger.lifecycle("Project Name: {}" , projectName);
        logger.lifecycle("Group id: {}" , groupId);
        logger.lifecycle("Principal Package: {}" , principalPackage);
        logger.lifecycle("Project type: {}" , type);
        logger.lifecycle("Java Version: {}" , javaVersion);
        builder.addGroupId(groupId);
        builder.addParam("projectName", projectName);
        builder.addParam("principalPackage", principalPackage);
        builder.addParam("lombok", lombok == BooleanOption.TRUE);
        builder.addParam("language", language.name().toLowerCase());
        builder.addParam("javaVersion", javaVersion);
        builder.addParam("java11", javaVersion == Constants.Java11);
        builder.addParam("remoteRepository", Constants.BANCOLOMBIA_REPOSITORIES);
        builder.addParam("serenityV", Constants.SERENITY_VERSION);
        builder.addParam("cucumberV",Constants.SERENITY_CUCUMBER_VERSION);
        builder.addParam("lombookV", Constants.LOMBOK_VERSION);
        builder.addParam("junitV", Constants.JUNIT);
        builder.addParam("hamcrestV", Constants.HAMCREST);
        builder.addParam("screenArchitectureV", Util.getVersionPlugin());

        Boolean exists = FileUtil.exists(builder.getProject().getProjectDir().getPath(), BUILD_GRADLE);
        if (exists && force == BooleanOption.FALSE){
            logger.lifecycle("Another project was found in the same directory, rewriting build.gradle and setting.gradle files ");
            builder.setupFromTemplate("structure/restructure");
        }else{
            builder.setupFromTemplate("structure");
        }
        builder.persist();
    }
}
