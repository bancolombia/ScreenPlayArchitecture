package co.com.bancolombia.utils;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import com.github.mustachejava.resolver.DefaultResolver;
import org.apache.commons.io.IOUtils;

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
}
