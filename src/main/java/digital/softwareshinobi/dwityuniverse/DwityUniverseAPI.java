package digital.softwareshinobi.dwityuniverse;

import java.io.IOException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DwityUniverseAPI {

    public static void main(final String[] commandLineArguments) throws IOException {

        System.out.println("begin > DwityUniverseAPI");

        SpringApplication.run(DwityUniverseAPI.class, commandLineArguments);

    }

}
