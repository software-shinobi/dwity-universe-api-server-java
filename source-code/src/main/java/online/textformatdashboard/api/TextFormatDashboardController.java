package online.textformatdashboard.api;

import online.textformatdashboard.utility.SpecialTextFormattingUtility;
import online.textformatdashboard.utility.TextWorkerUtility;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("")
public class TextFormatDashboardController {

    public TextFormatDashboardController() {

        System.out.println();
        System.out.println("*** ");
        System.out.println("*** init -- textformatdashboard.online API");
        System.out.println("*** ");
        System.out.println();

    }

    @GetMapping("/health-check")
    public String returnHealthCheckContentJSON() {
        return "textformatdashboard.online API Is Up!";
    }

    @PostMapping("/simple-text-format")
    public String prepareTextContentForRewriting(@RequestBody String textContentToBeFormatted) {

        System.out.println("textContentToBeFormatted: ");
        System.out.println(textContentToBeFormatted);

        String formattedTextContent = SpecialTextFormattingUtility.cleanAndFormatText(textContentToBeFormatted);

        formattedTextContent = formattedTextContent.replaceAll("\\?\\.","?");

        formattedTextContent= formattedTextContent.trim();

        return formattedTextContent;
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
