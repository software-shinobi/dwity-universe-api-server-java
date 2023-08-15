package online.textformatdashboard.api;

import biweekly.Biweekly;
import biweekly.ICalendar;
import biweekly.component.VEvent;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import online.textformatdashboard.utility.TextWorkerUtility;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("dwity")
public class DwityUniverseRestController {

    public DwityUniverseRestController() {

        System.out.println();
        System.out.println("*** ");
        System.out.println("*** init -- DwityUniverseRestController.online API");
        System.out.println("*** ");
        System.out.println();

    }

    @GetMapping("/health-check")
    public String returnHealthCheckContentJSON() {
        return "textformatdashboard.online API Is Up!";
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


        for (int x = 0; x < 45; x++) System.out.println("");


        System.out.println("last event????: " );

        VEvent theLastEvent = eventList.get(eventList.size()-1);
        System.out.println(theLastEvent );

return theLastEvent.getSummary().getValue();
    }

    @PostMapping("/calculate-text-percentage-difference")
    public Double calculateTextPercentageDifference(@RequestBody String userContentToBeRewritter) {

        System.out.println(" enter :: calculateTextPercentageDifference()");

        JSONObject ox = new JSONObject(userContentToBeRewritter);

        System.out.println("the user string: ");
        System.out.println(userContentToBeRewritter);

        String originalContent = ox.getString("originalContent")
                .toLowerCase().replaceAll("\\n+"," ").replaceAll("\\s+"," ").trim();

        String rewrittenContent = ox.getString("rewrittenContent")
                .toLowerCase().replaceAll("\\n+"," ").replaceAll("\\s+"," ").trim();

        System.out.println("originalContent:  " + originalContent);
        System.out.println("rewrittenContent: " + rewrittenContent);

        Double returnC = TextWorkerUtility.calculateTextSimilarityPercentage(
                originalContent,
                rewrittenContent
        );

        System.out.println(" returning: " + returnC);
        System.out.println(" enter :: calculateTextPercentageDifference()");

        return returnC;
    }

}
