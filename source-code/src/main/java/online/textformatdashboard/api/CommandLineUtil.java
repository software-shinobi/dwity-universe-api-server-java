package online.textformatdashboard.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.slf4j.LoggerFactory;

public class CommandLineUtil {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(CommandLineUtil.class);

    public int executeUnixCommand(final String unixCommand) throws IOException, InterruptedException {

        String s = "";
        int returnCode;
        String[] bashCommand = {"/bin/sh", "-c", unixCommand};
        logger.debug("Running Unix Terminal Command: [" + unixCommand + "]");

        // Run the passed Unix Command Using the Runtime exec method:
        Process p = Runtime.getRuntime().exec(bashCommand);
        BufferedReader standardInputBufferedReader = new BufferedReader(new InputStreamReader(p.getInputStream()));
        BufferedReader standardErrorBufferedReader = new BufferedReader(new InputStreamReader(p.getErrorStream()));

        while ((s = standardInputBufferedReader.readLine()) != null) {
            logger.debug("     Standard Output: " + s);
        }

        // read any errors from the attempted command
        while ((s = standardErrorBufferedReader.readLine()) != null) {
            logger.debug("     Standard  Error: " + s);
        }

        returnCode = p.waitFor();
        logger.debug("Execution Of Command [" + unixCommand + "] Has Completed");

        return returnCode;
    }
}
