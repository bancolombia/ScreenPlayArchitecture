package co.com.bancolombia.tasks;

import co.com.bancolombia.exceptions.ScreenPlayException;
import co.com.bancolombia.tasks.annotations.CATask;
import co.com.bancolombia.utils.Constants;
import co.com.bancolombia.utils.Util;
import org.gradle.api.tasks.options.Option;

import java.io.IOException;

@CATask(name = "generateRestInteraction", shortCut = "gri", description = "Generate rest Interaction classes")
public class GenerateRestInteractionTask extends AbstracScreenPlayArchitectureDefaultTask{
    protected String typeInteraction = "";

    @Option(option = "typeInteraction", description = "set the type of interaction to use ")
    public void setTypeInteraction(String typeInteraction) { this.typeInteraction = typeInteraction; }

    @Override
    public void execute() throws IOException, ScreenPlayException {
        if (typeInteraction.isEmpty() ) {
            throw new IllegalArgumentException("The type interaction is necessary: use gradle generateRestInteraction --typeInteraction[interaction]");
        }
        if (!Constants.INTERACTIONS.contains(typeInteraction.toUpperCase())) {
            throw new IllegalArgumentException("The type interaction is not valid");
        }
        logger.lifecycle("ScreenPlay architecture plugin version: {}", Util.getVersionPlugin());
        logger.lifecycle("Type interaction: {}", typeInteraction);
        builder.setupFromTemplate("interactions/" + typeInteraction.toLowerCase());
        builder.persist();
    }
}
