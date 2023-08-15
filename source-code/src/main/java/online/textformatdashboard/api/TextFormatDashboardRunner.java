package online.textformatdashboard.api;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TextFormatDashboardRunner {

    public static void main(final String[] commandLineArguments) throws IOException {

        System.out.println("hello :: TextFormatDashboardRunner");

        SpringApplication.run(TextFormatDashboardRunner.class, commandLineArguments);

        System.out.println("all done :: TextFormatDashboardRunner");
    }

}
