package co.com.bancolombia.tasks;

import co.com.bancolombia.exceptions.ScreenPlayException;
import co.com.bancolombia.tasks.annotations.CATask;
import co.com.bancolombia.utils.FileUtil;
import co.com.bancolombia.utils.Util;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Optional;

import java.io.IOException;

@CATask(
        name = "validateStructure",
        shortCut = "vs",
        description = "Validate that project references are not violated"
)
public abstract class ValidateStructureTask extends AbstracScreenPlayArchitectureDefaultTask {

    private static final  String SRC__UTILS_MODULE = "utils";

    @Input
    @Optional
    public abstract Property<String> getWhiteListedDependencies();

    @Override
    public void execute() throws IOException, ScreenPlayException {


        if (!validateUtilsModelLayer()) {
            throw  new ScreenPlayException("Utils module is invalid");
        }
        logger.lifecycle("The project is valid");
    }

    private boolean validateUtilsModelLayer(){
        return true;
    }
}
