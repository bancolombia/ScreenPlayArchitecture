package co.com.bancolombia.utils;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class UtilTest {

    @Test
    public void getVersionPlugin() {
        Assert.assertEquals(Constants.PLUGIN_VERSION, Util.getVersionPlugin());
    }

    @Test
    public void capitalize() {
        String test1 = "capitalize";
        String test2 = "capitalizeTest";

        Assert.assertEquals("Capitalize", Util.capitalize(test1));
        Assert.assertEquals("CapitalizeTest", Util.capitalize(test2));
    }
    @Test
    public void nocapitalize() {
        String test1 = "capitalize";
        String test2 = "capitalizeTest";

        Assert.assertEquals("capitalize", Util.nocapitalize(test1));
        Assert.assertEquals("capitalizeTest", Util.nocapitalize(test2));
    }
    @Test
    public void getDate() {
        LocalDate fechaActual = LocalDate.now();
        // Formatear la fecha en el formato "dd/mm/yyyy"
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        Assert.assertEquals(fechaActual.format(formato), Util.getDate());
    }
    @Test
    public void shouldJoinPath() {
        String expected = "a/b/c/d";
        String result = Util.joinPath("a", "b", "c", "d");
        Assert.assertEquals(expected, result);
    }

    @Test
    public void shouldExtractDir() {
        // Arrange
        String classPath = "default/driven-adapters/package/src/main/Model.java";
        // Act
        String result = Util.extractDir(classPath);
        // Assert
        assertEquals("default/driven-adapters/package/src/main", result);
    }

    @Test
    public void shouldFormatTaskOptions() {
        // Arrange
        List<?> options = Arrays.asList(Options.values());
        // Act
        String result = Util.formatTaskOptions(options);
        // Assert
        assertEquals("[A|BC|D]", result);
    }

    private enum Options {
        A,
        BC,
        D
    }
}
