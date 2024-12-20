
package org.stathissideris.ascii2image.test;

import org.junit.Test;
import static org.junit.Assert.*;
import org.stathissideris.ascii2image.core.CommandLineConverter;
import org.stathissideris.ascii2image.core.ConversionOptions;

public class CommandLineConverterTest {

    @Test
    public void testValidArguments() throws Exception {
        String[] args = {"input.txt", "output.png"};
        ConversionOptions options = CommandLineConverter.parseArguments(args);
        assertEquals("input.txt", options.processingOptions.getInputFilename());
        assertEquals("output.png", options.processingOptions.getOutputFilename());
    }

    @Test(expected = org.apache.commons.cli.ParseException.class)
    public void testNoArguments() throws Exception {
        String[] args = {};
        CommandLineConverter.parseArguments(args);
    }

    @Test
    public void testOverwriteOption() throws Exception {
        String[] args = {"input.txt", "output.png", "--overwrite"};
        ConversionOptions options = CommandLineConverter.parseArguments(args);
        assertTrue(options.processingOptions.overwriteFiles());

        String[] args2 = {"input.txt", "output.png"};
        ConversionOptions options2 = CommandLineConverter.parseArguments(args2);
        assertFalse(options2.processingOptions.overwriteFiles());
    }

    @Test
    public void testOtherOptions() throws Exception {
        String[] args = {"input.txt", "output.png", "--no-shadows", "--scale", "2", "--background", "FF0000"};
        ConversionOptions options = CommandLineConverter.parseArguments(args);
        assertFalse(options.renderingOptions.isDropShadows());
        assertEquals(2.0f, options.renderingOptions.getScale(), 0.001f);
        assertEquals(0xFF0000, options.renderingOptions.getBackgroundColor());


        String[] args2 = {"input.txt", "output.png", "-S", "-s", "3", "-b", "0000FF"};
        ConversionOptions options2 = CommandLineConverter.parseArguments(args2);
        assertFalse(options2.renderingOptions.isDropShadows());
        assertEquals(3.0f, options2.renderingOptions.getScale(), 0.001f);
        assertEquals(0x0000FF, options2.renderingOptions.getBackgroundColor());

    }

    @Test
    public void testHtmlOption() throws Exception {
        String[] args = {"input.html", "output.html", "--html"};
        ConversionOptions options = CommandLineConverter.parseArguments(args);
        assertTrue(options.processingOptions.isHtml());
    }
}

