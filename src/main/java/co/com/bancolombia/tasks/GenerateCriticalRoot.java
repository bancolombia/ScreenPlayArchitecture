package co.com.bancolombia.tasks;

import co.com.bancolombia.exceptions.ParamNotFoundException;
import co.com.bancolombia.exceptions.ScreenPlayException;
import co.com.bancolombia.tasks.annotations.CATask;
import co.com.bancolombia.utils.Constants;
import co.com.bancolombia.utils.FileUtil;
import co.com.bancolombia.utils.Util;
import org.gradle.api.tasks.options.Option;

import java.io.IOException;
import java.util.*;

@CATask(name = "generateCriticalRoot", shortCut = "gcr", description = "Generate json critical root file")
public class GenerateCriticalRoot extends AbstracScreenPlayArchitectureDefaultTask {

    private String componentName = "";
    private String features;
    private String language;

    @Option(option = "componentName", description = "Set the component name")
    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }
    @Option(option = "features", description = "Set the features name to find in the project")
    public void setFeatures(String listFeature) {
        this.features = listFeature;
    }
    @Option(option = "language", description = "Set the Gherkin language used in the feature file")
    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public void execute() throws IOException, ScreenPlayException {

        String step = Constants.STEPS.get(0);
        String example = "Ejemplos:";
        if (!Constants.LANGUAGE.contains(language.toUpperCase())) {
            throw new IllegalArgumentException("The language is not valid, use EN -> English or ES -> Spanish");
        }

        if (language.equalsIgnoreCase("EN")) {
            example = "Examples:";
            step = Constants.STEPS.get(1);
        }

        logger.lifecycle("ScreenPlay architecture plugin version: {}", Util.getVersionPlugin());
        logger.lifecycle("Critical Root name: {}", Constants.CRITICAL_ROOT_NAME);
        logger.lifecycle("Feature to create Critical Root");
        builder.addParam("componentName", componentName);
        builder.addParam("rootName", Constants.CRITICAL_ROOT_NAME);
        builder.addParam("date", Util.getDate());
        builder.addParam("status", true);
        builder.addParam("features", dataExtractor(List.of(features.split(",")), example, step));// Get the features name and Scenarios contained within the feature file
        builder.setupFromTemplate("criticalroot");
        builder.persist();
    }

    private String dataExtractor(List<String> features, String example, String step) throws IOException {
        String featureContent = "";
        List<String> dataToSet = new ArrayList<>();
        List<String> values = new ArrayList<>();
        List<String> consolidate = new ArrayList<>();
        String scenario = "";
        for (String pathFeature : features) {
            int iterator = 0;
            values.clear();
            featureContent = FileUtil.readFile(
                    builder.getProject(), "src/test/resources/features/" + pathFeature + ".feature");
            if (!FileUtil.findMatches(featureContent, example).get(0).isEmpty()){
                scenario = language.equalsIgnoreCase("EN") ? Constants.SCENARIOS.get(3):Constants.SCENARIOS.get(2);
            }else {
                scenario = language.equalsIgnoreCase("ES") ? Constants.SCENARIOS.get(1):Constants.SCENARIOS.get(0);
            }
            while (FileUtil.findMatches(featureContent, scenario).get(0).size() >= iterator + 1) {
                values.add("\"" + featureContent.substring(
                                FileUtil.findMatches(featureContent, scenario).get(1).get(iterator),
                                FileUtil.findMatches(featureContent, step).get(0).get(iterator))
                        .replace(":", "").trim() + "\"");
                iterator++;
            }
            consolidate.add(pathFeature.substring(pathFeature.indexOf("/") + 1) + values.toString());
        }
        for (String s : consolidate) {
            dataToSet.add(stringBuilder(s.substring(s.indexOf("[")),
                    s.substring(0, s.indexOf("["))));
        }
        return dataToSet.toString();
    }

    private String stringBuilder(String data, String featureName) {
        return "{ \"nombre_feature\": \"" + featureName + ".feature\", \"escenarios\": " + data +" }";
    }


}
