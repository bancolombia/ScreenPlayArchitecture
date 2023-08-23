package co.com.bancolombia.utils;

import java.io.*;
import java.nio.charset.MalformedInputException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.github.mustachejava.resolver.DefaultResolver;
import org.apache.commons.io.IOUtils;
import org.gradle.api.Project;

public class FileUtil {

    public static boolean exists(String dir, String file) {
        return Files.exists(Paths.get(dir, file));
    }

    public static String getResourceAsString(DefaultResolver resolver, String path)
            throws IOException {
        Reader reader = resolver.getReader(path);
        return IOUtils.toString(reader);
    }

    public static String toRelative(String path) {
        if (path.startsWith("./")) {
            return path;
        }
        if (path.startsWith(".\\")) {
            return path;
        }
        if (Paths.get(path).isAbsolute()) {
            return path;
        }
        return "./" + path;
    }

    public static void writeString(Project project, String filePath, String content) throws IOException{
        project.getLogger().debug(project.file(filePath).getAbsolutePath());
        File file = project.file(filePath).getAbsoluteFile();
        try (FileWriter fileWriter = new FileWriter(file)){
            fileWriter.write(content);
        }
    }

    public static String readProperties(String projectPath, String fileName, String variable) throws IOException {
        Properties properties = new Properties();
        try (BufferedReader br = new BufferedReader(new FileReader(projectPath + fileName))) {
            properties.load(br);
        }
        if (properties.getProperty(variable) != null) {
            return properties.getProperty(variable);
        } else {
            throw new IOException("No parameter " + variable + " in " + fileName + " file");
        }
    }

    public static String readFile(Project project, String filePath) throws IOException {
        File file = project.file(filePath).getAbsoluteFile();
        project.getLogger().debug(file.getAbsolutePath());
        try {
            return Files.lines(Paths.get(file.toURI())).collect(Collectors.joining("\n"));
        } catch (MalformedInputException e) {
            project
                    .getLogger()
                    .warn(
                            "error '{}' reading file {}, trying to read with ISO_8859_1 charset",
                            e.getMessage(),
                            file.getAbsoluteFile());
            return Files.lines(Paths.get(file.toURI()), StandardCharsets.ISO_8859_1)
                    .collect(Collectors.joining("\n"));
        }
    }

    public static List<List<Integer>> findMatches(String file, String patternToFind) {
        // Compile the pattern into a pattern object
        Pattern pattern = Pattern.compile(patternToFind);

        // Create a Matcher Object for the text an the pattern
        Matcher matcher = pattern.matcher(file);

        // List for saving the start and end indexes for matches
        List<Integer> indicesInicio = new ArrayList<>();
        List<Integer> indicesFin = new ArrayList<>();

        // find all matches and save the indexes
        while (matcher.find()) {
            int inicio = matcher.start(); // Match star index
            int fin = matcher.end();       // Match end index
            indicesInicio.add(inicio);
            indicesFin.add(fin);
        }
        List<List<Integer>> index = new ArrayList<>();
        index.add(indicesInicio);
        index.add(indicesFin);
        return index;
    }
}
