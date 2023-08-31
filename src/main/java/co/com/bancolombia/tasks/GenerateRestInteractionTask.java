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
    protected String nameInteraction = "";

    @Option(option = "typeInteraction", description = "set the type of interaction to use ")
    public void setTypeInteraction(String typeInteraction) { this.typeInteraction = typeInteraction; }

    @Option(option = "nameInteraction", description = "set the name of interaction to use ")
    public void setNameInteraction(String nameInteraction) { this.nameInteraction = nameInteraction; }


    @Override
    public void execute() throws IOException, ScreenPlayException {
        if (typeInteraction.isEmpty() ) {
            throw new IllegalArgumentException("The type interaction is necessary: use gradle generateRestInteraction --typeInteraction=[interaction]");
        }
        if (!Constants.INTERACTIONS.contains(typeInteraction.toUpperCase())) {
            throw new IllegalArgumentException("The type interaction is not valid");
        }
        if (typeInteraction.equalsIgnoreCase("GENERIC") && nameInteraction.isEmpty()) {
            throw new IllegalArgumentException("When you choose GENERIC as a interaction type, the name for" +
                    " that class is necessary: \nuse gradle generateRestInteraction --typeInteraction=GENERIC " +
                    "--nameInteraction=[name]");
        }
        logger.lifecycle("ScreenPlay architecture plugin version: {}", Util.getVersionPlugin());
        logger.lifecycle("Type interaction: {}", typeInteraction);
        if (!nameInteraction.isEmpty()) {
            builder.addParam("nameInteCapitalize", Util.capitalize(nameInteraction));
            builder.addParam("nameInteLower", Util.nocapitalize(nameInteraction));
        }
        builder.setupFromTemplate("interactions/" + typeInteraction.toLowerCase());
        builder.persist();
    }
}
