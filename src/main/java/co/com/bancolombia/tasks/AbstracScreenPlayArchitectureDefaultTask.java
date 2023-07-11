package co.com.bancolombia.tasks;

import co.com.bancolombia.exceptions.ScreenPlayException;
import co.com.bancolombia.factory.ModuleBuilder;
import org.gradle.api.DefaultTask;
import org.gradle.api.logging.Logger;
import org.gradle.internal.logging.text.StyledTextOutputFactory;

import javax.inject.Inject;
import java.io.IOException;

public abstract class AbstracScreenPlayArchitectureDefaultTask extends DefaultTask {

    protected final ModuleBuilder builder = new ModuleBuilder(getProject());
    protected final Logger logger = getProject().getLogger();

    protected AbstracScreenPlayArchitectureDefaultTask(){
        builder.setStyledLogger(
                getTextOutputFactory().create(AbstracScreenPlayArchitectureDefaultTask.class)
        );
    }


    @Inject
    protected StyledTextOutputFactory getTextOutputFactory() {
        throw new UnsupportedOperationException();
    }

    public abstract void execute() throws IOException, ScreenPlayException;
}
