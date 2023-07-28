package co.com.bancolombia.tasks;

import co.com.bancolombia.exceptions.ScreenPlayException;
import co.com.bancolombia.tasks.annotations.CATask;
import co.com.bancolombia.utils.Util;
import org.gradle.api.tasks.options.Option;

import java.io.IOException;

@CATask(
        name = "generateFeature", shortCut = "gft", description = "generate feature file"
)
public class GenerateFeatureTask extends AbstracScreenPlayArchitectureDefaultTask{

    private String name;
    private String nameSubFolder;
    private Boolean example = false;

    @Option(option = "name", description = "Set feature file name")
    public void setName(String name){ this.name = name; }

    @Option(option = "example", description = "Define if Scenario Outline are needed")
    public void setExample(boolean example){ this.example = example; }

    @Option(option = "nameSubFolder", description = "Define if folder new are needed")
    public void setNameSubFolder(String subFolder) { this.nameSubFolder = subFolder; }

    @Override
    public void execute() throws IOException, ScreenPlayException {
        if (name.isEmpty()) {
            printHelp();
            throw new IllegalArgumentException("The feature name is necessary: use gradle generateFeature --name[filename]");
        }
        name = name.toLowerCase();
        logger.lifecycle("ScreenPlay architecture plugin version: {}", Util.getVersionPlugin());
        logger.lifecycle("Feature name: {}", name);
        logger.lifecycle("Implement Scenario Outline {}", example);
        builder.addParam("featureName", name);
        if (!nameSubFolder.isEmpty()){
            builder.addParam("subfolder", nameSubFolder);
            builder.setupFromTemplate("features/subfolder");
        }
        if (example){
            builder.setupFromTemplate("features/outline");
        }else{
            builder.setupFromTemplate("features/feature");
        }
    }
}
