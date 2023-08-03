package co.com.bancolombia.utils;

import java.io.*;
import java.nio.charset.MalformedInputException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.stream.Collectors;

import com.github.mustachejava.resolver.DefaultResolver;
import org.apache.commons.io.IOUtils;
import org.gradle.api.Project;

public class FileUtil {
    private static final String GRADLE_PROPERTIES = "/gradle.properties";

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

    public static String readProperties(String projectPath, String variable) throws IOException {
        Properties properties = new Properties();
        try (BufferedReader br = new BufferedReader(new FileReader(projectPath + GRADLE_PROPERTIES))) {
            properties.load(br);
        }
        if (properties.getProperty(variable) != null) {
            return properties.getProperty(variable);
        } else {
            throw new IOException("No parameter " + variable + " in gradle.properties file");
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
}
