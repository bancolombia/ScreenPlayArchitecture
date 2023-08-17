package co.com.bancolombia.tasks;

import co.com.bancolombia.exceptions.ScreenPlayException;
import co.com.bancolombia.tasks.annotations.CATask;
import co.com.bancolombia.utils.Constants;
import co.com.bancolombia.utils.FileUtil;
import co.com.bancolombia.utils.Util;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@CATask(name = "generateCriticalRoot", shortCut = "gcr", description = "Generate json critical root file")
public class GenerateCriticalRoot extends AbstracScreenPlayArchitectureDefaultTask {


    @Override
    public void execute() throws IOException, ScreenPlayException {
        logger.lifecycle("ScreenPlay architecture plugin version: {}", Util.getVersionPlugin());
        logger.lifecycle("Critical Root name: {}", Constants.CRITICAL_ROOT);
        dataExtractor();
    }

    private void dataExtractor() throws IOException {
        File[] featureSubFolder = builder.getProject().file("src/test/resources/features/").listFiles();
        List<String> internFeatures = new ArrayList<>();
        List<String> paths = new ArrayList<>();
        List<String> subFolder = new ArrayList<>();
        List<String> dataFile = new ArrayList<>();
        String scenario = "";
        for (File subFolders : featureSubFolder) {
            subFolder.add(subFolders.getName());
            for(File features : subFolders.listFiles()) {
                internFeatures.add(features.getName());
                paths.add(subFolders.getName() + "/" + features.getName());
            }
        }
        for (String scen : paths){
            scenario = FileUtil.readFile(builder.getProject(), "src/test/resources/features/" + scen);
            int i = 0;
            while (this.findMatches(scenario, "Scenario").get(1).size() >= i + 1){
                String values = scenario.substring(
                        this.findMatches(scenario, "Scenario").get(1).get(i),
                        this.findMatches(scenario, "Given").get(0).get(i)
                ).replace(":", "").trim();
                dataFile.add(values);
                i++;
            }

        }


    }

    private List<List<Integer>> findMatches(String file, String patternToFind) {
        // Compila el patrón en un objeto Pattern
        Pattern pattern = Pattern.compile(patternToFind);

        // Crea un objeto Matcher para el texto y el patrón
        Matcher matcher = pattern.matcher(file);

        // Listas para almacenar los índices de inicio y fin de las coincidencias
        List<Integer> indicesInicio = new ArrayList<>();
        List<Integer> indicesFin = new ArrayList<>();

        // Encuentra todas las coincidencias y almacena los índices
        while (matcher.find()) {
            int inicio = matcher.start(); // Índice de inicio de la coincidencia
            int fin = matcher.end();       // Índice de fin de la coincidencia
            indicesInicio.add(inicio);
            indicesFin.add(fin);
        }
        List<List<Integer>> index = new ArrayList<>();
        index.add(indicesInicio);
        index.add(indicesFin);
        return index;
    }
}
