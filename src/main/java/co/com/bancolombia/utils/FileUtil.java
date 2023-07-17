package co.com.bancolombia.utils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import com.github.mustachejava.resolver.DefaultResolver;
import org.apache.commons.io.IOUtils;
import org.gradle.api.Project;

public class FileUtil {

    public static boolean exists(String dir, String file) {
        return Files.exists(Paths.get(dir, file));
    }
    private static final String GRADLE_PROPERTIES = "/gradle.properties";


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
}
