package co.com.bancolombia.tasks;

import co.com.bancolombia.exceptions.ScreenPlayException;
import co.com.bancolombia.tasks.annotations.CATask;
import co.com.bancolombia.utils.Util;
import org.gradle.api.tasks.options.Option;

import java.io.IOException;

@CATask(
        name = "generateRunner", shortCut = "grun", description = "Generate runner class"
)
public class GenerateRunnerTask extends AbstracScreenPlayArchitectureDefaultTask{

    private String name = "";
    private String folderName = "";
    @Option(option = "name", description = "Set the runner name")
    public void setName(String runnerName){ this.name = runnerName; }
    @Option(option = "folderName", description = "Set the runner folder name")
    public void setFolderName(String folderName){ this.folderName = folderName; }

    @Override
    public void execute() throws IOException, ScreenPlayException {
        if (name.isEmpty() && folderName.isEmpty()) {
            printHelp();
            throw new IllegalArgumentException("The runner and folder name are necessary: use gradle generateRunner --name=[nameClass] --folderName=[folderName]");
        }
        name = Util.capitalize(name);
        logger.lifecycle("ScreenPlay architecture plugin version: {}", Util.getVersionPlugin());
        logger.lifecycle("Runner name: {}", name);
        builder.addParam("runnerName", name);
        builder.addParam("folderName", folderName.toLowerCase());
        builder.setupFromTemplate("runners");
        builder.persist();
    }
}
