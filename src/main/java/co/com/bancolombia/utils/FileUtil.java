package co.com.bancolombia.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
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
}
