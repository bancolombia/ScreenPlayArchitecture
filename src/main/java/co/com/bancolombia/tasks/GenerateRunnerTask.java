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

    private String name = "Runner";


    @Option(option = "name", description = "Set the runner name")
    public void setName(String runnerName){ this.name = runnerName; }

    @Override
    public void execute() throws IOException, ScreenPlayException {
        if (name.isEmpty()) {
            printHelp();
            throw new IllegalArgumentException("The runner name is necessary: use gradle generateRunner --name[nameClass]");
        }
        name = Util.capitalize(name);
        logger.lifecycle("ScreenPlay architecture plugin version: {}", Util.getVersionPlugin());
        logger.lifecycle("Runner name: {}", name);
        builder.addParam("runnerName", name);
        builder.setupFromTemplate("runners");
        builder.persist();
    }
}
