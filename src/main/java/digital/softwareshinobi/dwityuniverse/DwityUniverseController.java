package digital.softwareshinobi.dwityuniverse;

import biweekly.Biweekly;
import biweekly.ICalendar;
import biweekly.component.VEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("dwity-universe")
public class DwityUniverseController {

    public DwityUniverseController() {

        System.out.println();
        System.out.println("## ");
        System.out.println("## init / Dwity Universe API");
        System.out.println("## ");
        System.out.println();

    }

    @GetMapping("/")
    public String activeactive() {
        return "/////////////////////";
    }

    @GetMapping("/health-check")
    public String returnHealthCheckContentJSON() {
        return "textformatdashboard.online API Is Up!";
    }

    @GetMapping("/active")
    public String actdddiveactive() throws IOException {


        System.out.println("enter > /active");

        //   File localDirectoryOfCalendarExport = new File("/tmp/");
        String randomFileName = "dwity-online-calendar-export." + UUID.randomUUID().toString() + ".ics";

        System.out.println("location of calendar file: " + randomFileName);
        // System.exit(1);

        System.out.println("Here...");

        Wget.wGet(randomFileName, "https://gist.githubusercontent.com/DeMarko/6142417/raw/1cd301a5917141524b712f92c2e955e86a1add19/sample.ics");
        System.out.println("Here...");

        File f = new File(randomFileName);

        ICalendar icals = Biweekly.parse(f).first();

        System.out.println("Here...");

        List<VEvent> listOfActiveEvents = new ArrayList();

        List<VEvent> eventList = icals.getEvents();

        //  listOfActiveEvents.addAll(getInProgress(eventList));
        f.delete();

        for (int x = 0; x < 45; x++) {
            System.out.println("");
        }

        System.out.println("last event????: ");

        VEvent theLastEvent = eventList.get(eventList.size() - 1);
        System.out.println(theLastEvent);

        return theLastEvent.getSummary().getValue();
    }

    @GetMapping("/latest-known-event")
    public String prepareTextContentForRewriting() throws IOException {

        System.out.println("hello-universe");

        //   File localDirectoryOfCalendarExport = new File("/tmp/");
        String randomFileName = "dwity-online-calendar-export." + UUID.randomUUID().toString() + ".ics";

        System.out.println("location of calendar file: " + randomFileName);
        // System.exit(1);

        System.out.println("Here...");

        Wget.wGet(randomFileName, "https://gist.githubusercontent.com/DeMarko/6142417/raw/1cd301a5917141524b712f92c2e955e86a1add19/sample.ics");
        System.out.println("Here...");

        File f = new File(randomFileName);

        ICalendar icals = Biweekly.parse(f).first();

        System.out.println("Here...");

        List<VEvent> listOfActiveEvents = new ArrayList();

        List<VEvent> eventList = icals.getEvents();

        //  listOfActiveEvents.addAll(getInProgress(eventList));
        f.delete();

        for (int x = 0; x < 45; x++) {
            System.out.println("");
        }

        System.out.println("last event????: ");

        VEvent theLastEvent = eventList.get(eventList.size() - 1);
        System.out.println(theLastEvent);

        return theLastEvent.getSummary().getValue();
    }


}
