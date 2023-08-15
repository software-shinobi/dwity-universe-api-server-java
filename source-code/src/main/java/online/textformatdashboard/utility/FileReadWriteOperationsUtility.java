package online.textformatdashboard.utility;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import org.slf4j.LoggerFactory;

/**
 * General File-based functionality utility class
 *
 * @author Troy Burney
 */
public class FileReadWriteOperationsUtility {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(FileReadWriteOperationsUtility.class);

    public enum APPEND {
        DO_APPEND, DONT_APPEND;
    }

    public static final String DIRECTORY_CHAR_UNIX = "/";
    public static final String FILENAME_SEPERATOR = ".";
    public static final String EXTENSION_JSON = "json";

    private static final String LINE_SEPERATOR = System.getProperty("line.separator");
    private static final String NEW_LINE = LINE_SEPERATOR;
    private static final APPEND DEFAULT_FILE_APPEND = APPEND.DO_APPEND;

    /**
     * Writes data to an output file
     *
     * @param data Data to write to file
     * @param outputFile File to write data out to
     * @return boolean value representing file write status
     */
    public static boolean writeToFile(final Object data, final File outputFile) {
        return writeToFile(data, outputFile, DEFAULT_FILE_APPEND);
    }

    /**
     * Writes data to an output file
     *
     * @param data Data to write to file
     * @param outputFile File to write data out to
     * @param append Whether to append data to an existing file
     * @return boolean value representing file write status
     */
    public static boolean writeToFile(final Object data, final File outputFile, final APPEND append) {
        if (logger.isTraceEnabled()) {
            logger.trace("enter :: writeToFile()");
            logger.trace("param - file: {}", outputFile);
            logger.trace("param - append: {}", append);
        }

        if (data == null) {
            if (logger.isTraceEnabled()) {
                logger.trace("data object to write to file was null. nothing further to do.");
                logger.trace("returning: {}", false);
                logger.trace("exit :: writeToFile()");
            }
            return false;
        } else if (outputFile == null) {
            if (logger.isTraceEnabled()) {
                logger.trace("output file object was null. nothing further to do.");
                logger.trace("returning: {}", false);
                logger.trace("exit :: writeToFile()");
            }
            return false;
        }

        try {

            BufferedWriter bw;

            if (append.equals(APPEND.DO_APPEND)) {
                bw = new BufferedWriter(new FileWriter(outputFile, true));
            } else {
                bw = new BufferedWriter(new FileWriter(outputFile, false));
            }
            bw.write(data.toString().replaceAll(NEW_LINE, LINE_SEPERATOR));
            bw.newLine();
            bw.flush();
            bw.close();
            bw = null;

            if (logger.isTraceEnabled()) {
                logger.trace("content successfully written to file");
            }

        } catch (IOException ex) {

            logger.error("error writing to file. message: {}", ex.getMessage());
            if (logger.isTraceEnabled()) {
                logger.trace("returning: {}", false);
                logger.trace("exit :: writeToFile()");
            }
            return false;
        }

        if (logger.isTraceEnabled()) {
            logger.trace("returning: {}", true);
            logger.trace("exit :: writeToFile()");
        }
        return true;
    }

    public static final File addTimeStampToFileName() {
        throw new UnsupportedOperationException("Please correct the FileUtil.addTimeStampToFileName() function");
    }

    public static String readFromFile(final File inputFile) {

        StringBuilder contents = new StringBuilder();
        BufferedReader input;

        try {

            //use buffering, reading one line at a time
            //FileReader always assumes default encoding is OK!
            input = new BufferedReader(new FileReader(inputFile));

            try {

                String line = null; //not declared within while loop
                /*
                 * readLine is a bit quirky : it returns the content of a line
                 * MINUS the newline. it returns null only for the END of the
                 * stream. it returns an empty String if two newlines appear in
                 * a row.
                 */
                while ((line = input.readLine()) != null) {
                    contents.append(line);
                    contents.append(System.getProperty("line.separator"));
                }
            } finally {
                input.close();
                input = null;
            }
        } catch (IOException ex) {
            logger.error("{}", ex);
        } catch (Exception ex) {
            logger.error("{}", ex);
        }
        return contents.toString();
    }

    public static String[] readFileContents(final File filePath) {
        InputStream fis;
        BufferedReader br;
        String line;

        ArrayList<String> fileList = new ArrayList<String>();
        String[] stringArray = null;

        try {

            fis = new FileInputStream(filePath);

            br = new BufferedReader(new InputStreamReader(fis, Charset.forName("UTF-8")));
            while ((line = br.readLine()) != null) {
                fileList.add(line);

            }

            stringArray = new String[fileList.size()];

            for (int i = 0; i < fileList.size(); i++) {
                stringArray[i] = fileList.get(i);
            }

        } catch (FileNotFoundException ex) {
            logger.error("{}", ex);
        } catch (IOException ex) {
            logger.error("{}", ex);
        }

        return stringArray;
    }

    public static String getFileDirectory(String filePath) {

        //Trim The File Path String
        filePath = filePath.trim();

        //If File Path Ends with  '/' or a '\' return the full String
        //As it already is a directory
        if (filePath.endsWith("\\|/")) {
            return formatFilePath(filePath);
        }

        //Create A String to hold the directory path
        String directoryString = "";

        //Create A Array Of All The FileName Parts
        String filePathParts[] = filePath.split("/");

        //Iterate over the array and append the values of the file path directory
        for (int index = 0; index < (filePathParts.length - 1); index++) {
            directoryString = directoryString + filePathParts[index] + "/";
        }

        //Return The Created Directory String
        return directoryString;
    }

    public static String formatFilePath(String path) {
        return path.trim().trim().replaceAll("[\\\\\\\\]+", DIRECTORY_CHAR_UNIX).replaceAll("[/]+", DIRECTORY_CHAR_UNIX);
    }

    private FileReadWriteOperationsUtility() {
    }
}
