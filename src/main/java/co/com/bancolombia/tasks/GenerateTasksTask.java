package co.com.bancolombia.tasks;

import co.com.bancolombia.exceptions.ParamNotFoundException;
import co.com.bancolombia.exceptions.ScreenPlayException;
import co.com.bancolombia.tasks.annotations.CATask;
import co.com.bancolombia.utils.Constants;
import co.com.bancolombia.utils.FileUtil;
import co.com.bancolombia.utils.Util;
import org.gradle.api.tasks.options.Option;

import java.io.IOException;

@CATask( name = "generateTask", shortCut = "gtk", description = "Generate Serenity task")
public class GenerateTasksTask extends AbstracScreenPlayArchitectureDefaultTask{

    private String name = "";
    private String typeTask = "";
    private String method = "";

    @Option(option = "name", description = "set the class name")
    public void setName(String name) { this.name = name; }

    @Option(option = "typeTask", description = "set the class type to use")
    public void setTypeTask(String type) { this.typeTask = type; }

    @Option(option = "method", description = "set type method rest to use")
    public void setmethod(String method) { this.method = method; }


    @Override
    public void execute() throws IOException, ScreenPlayException {
        if (!name.isEmpty() && !typeTask.isEmpty() && Constants.TASKS.contains(typeTask.toUpperCase())) {
            if (typeTask.equalsIgnoreCase("ux")){
                validateOperation(false);
            }else if (typeTask.equalsIgnoreCase("rest") && (!method.isEmpty() &&
                    Constants.INTERACTIONS.contains(method.toUpperCase()))){
                validateOperation(true);
            }else{
                throw new IllegalArgumentException("The class name and type are necessary: use gradle generateTask" +
                        " --name[nameClass] --type[typeClass] \n" +
                        "Keep in mind that if you choose a class of type 'rest', the 'method' field is mandatory: \n" +
                        "use gradle generateTask --name[nameClass] --type[typeClass] --method=[methodRestToUse]");
            }
            builder.persist();
        }else{
            throw new IllegalArgumentException("The class name and type are necessary: use gradle generateTask" +
                    " --name[nameClass] --type[typeClass] \n" +
                    "Keep in mind that if you choose a class of type 'rest', the 'method' field is mandatory: \n" +
                    "use gradle generateTask --name[nameClass] --type[typeClass] --method=[methodRestToUse]");
        }
    }

    private void validateOperation(Boolean flag) throws ParamNotFoundException, IOException {
        logger.lifecycle("ScreenPlay architecture plugin version: {}", Util.getVersionPlugin());
        logger.lifecycle("Task Name: {}", name);
        logger.lifecycle("Task Type: {}", Util.capitalize(typeTask));
        builder.addParam("taskName", Util.capitalize(name));
        builder.addParam("taskStatic", Util.nocapitalize(name));
        if (flag){
            builder.addParam("method", Util.capitalize(method));
            builder.addParam("access", Util.validateMethod(method).get(0));
            builder.addParam("data", Util.validateMethod(method).get(1));
        }
        builder.setupFromTemplate("tasks/" + typeTask.toLowerCase());
    }
}
