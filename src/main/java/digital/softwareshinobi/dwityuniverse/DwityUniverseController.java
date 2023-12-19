package digital.softwareshinobi.dwityuniverse;

import digital.softwareshinobi.dwityuniverse.thirdparty.Wget;
import biweekly.Biweekly;
import biweekly.ICalendar;
import biweekly.component.VEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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
        System.out.println("## init > Dwity Universe API");
        System.out.println("## ");
        System.out.println();

    }

    @GetMapping("/health")
    public String staticHealthCheck() {

        return "ONLINE";

    }

    @GetMapping("/")
    public List<VEvent> listAllEvents() throws IOException {

        List<VEvent> allEvents = this.fetchAllEventsFromCalendar();

        return allEvents;

    }

    @GetMapping("/active")
    public List<VEvent> listActiveEvents() throws IOException {

        List<VEvent> allEvents = this.fetchAllEventsFromCalendar();

        List<VEvent> onlyActiveEvents = new ArrayList();

        Date rightNowDate = new Date();

        System.out.println("rightNowDate / " + rightNowDate);

        for (final VEvent singleEvent : allEvents) {

            boolean startDateHasPassed = singleEvent.getDateStart().getValue().before(rightNowDate);

            boolean endDateHasNotPassed = singleEvent.getDateEnd().getValue().after(rightNowDate);

            boolean isActive = startDateHasPassed && endDateHasNotPassed;

            if (isActive) {

                System.out.println("found active / " + singleEvent);

                onlyActiveEvents.add(singleEvent);

            }

        }

        return onlyActiveEvents;

    }

    private List<VEvent> fetchAllEventsFromCalendar() throws IOException {

        String randomFileName
                = "dwity-online-calendar-export." + UUID.randomUUID().toString() + ".ics";

        File temporaryICSFile = new File(randomFileName);

        //https://calendar.google.com/calendar/ical/f21b14c75f2698d2fd73960a3c7bf846ee65dddc8042a793eae1d09b42de9c66%40group.calendar.google.com/public/basic.ics
        String url = "https://calendar.google.com/calendar/ical/f21b14c75f2698d2fd73960a3c7bf846ee65dddc8042a793eae1d09b42de9c66%40group.calendar.google.com/public/basic.ics";
//   Wget.wGet(randomFileName, "https://gist.githubusercontent.com/DeMarko/6142417/raw/1cd301a5917141524b712f92c2e955e86a1add19/sample.ics");

//String url = "https://calendar.google.com/calendar/ical/ht3jlfaac5lfd6263ulfh4tql8%40group.calendar.google.com/public/basic.ics";
        Wget.wGet(randomFileName, url);

        ICalendar icals = Biweekly.parse(temporaryICSFile).first();

        List<VEvent> eventList = icals.getEvents();

        temporaryICSFile.delete();

        return eventList;

    }

}
