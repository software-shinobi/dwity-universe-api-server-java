package online.textformatdashboard.utility;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SpecialTextFormattingUtility {

    public static final String rewrite_DELIMITER = "!!";

    public static final String emptystring = "";

    public static final String SPECIAL_DELIMITER = " @@ ";

    public static final String OUTER_DELIMITER = "";

    public static String cleanAndFormatText(String content) {

        System.out.println("enter :: cleanAndFormatText()");

        File tempFile = new File(UUID.randomUUID() + ".txt");

        FileReadWriteOperationsUtility.writeToFile(content, tempFile);

        String returnString ="\n\n"+ cleanAndFormatText(tempFile)+"\n\n" ;

        tempFile.delete();
        tempFile = null;

        return returnString;
    }

    private static String cleanAndFormatText(File filePath) {

        List<String> contentFromFile = SpecialTextFormattingUtility.parseFileLinesFromFile(filePath);
        FileReadWriteOperationsUtility.writeToFile(contentFromFile, new File(filePath + "pass-1-simple-save.md"));

        contentFromFile = SpecialTextFormattingUtility.removeEmptyContent(contentFromFile);
        FileReadWriteOperationsUtility.writeToFile(contentFromFile, new File(filePath + "pass-2-remove-spaces.md"));

        contentFromFile = SpecialTextFormattingUtility.removeBadContent(contentFromFile);
        FileReadWriteOperationsUtility.writeToFile(contentFromFile, new File(filePath + "pass-2-bad-content-spaces.md"));

        contentFromFile = SpecialTextFormattingUtility.toLowerCaseAll(contentFromFile);
        FileReadWriteOperationsUtility.writeToFile(contentFromFile, new File(filePath + "pass-3-to-lowercase.md"));

        contentFromFile = SpecialTextFormattingUtility.splitContentOnPeriods(contentFromFile);
        contentFromFile = SpecialTextFormattingUtility.splitContentOnQuestionMarks(contentFromFile);

        FileReadWriteOperationsUtility.writeToFile(contentFromFile, new File(filePath + "pass-4-split-sentences.md"));

       contentFromFile = SpecialTextFormattingUtility.capitalizeContent(contentFromFile);
     ///   FileReadWriteOperationsUtility.writeToFile(contentFromFile, new File(filePath + "pass-5-correct-capitalization.md"));

       contentFromFile = SpecialTextFormattingUtility.addSpacesAndStuff(contentFromFile);
//        FileUtil.writeToFile(contentFromFile, new File(filePath + "pass-7-add-spaces-and-stuff.md"));

    //    contentFromFile = prepContentForRewriteFormat(contentFromFile);
        FileReadWriteOperationsUtility.writeToFile(contentFromFile, new File(filePath + "convert-to-rewrite-format.dat.rytr"));

        File fff = new File(filePath + "-final.md");
        fff.delete();

        SpecialTextFormattingUtility.writeFinalFile(contentFromFile, fff);
        //   FileUtil.writeToFile(contentFromFile, new File(filePath + "-final.md"));

        SpecialTextFormattingUtility.printCurrentContent(contentFromFile);

        StringBuilder buf = new StringBuilder();

        for (String o : contentFromFile) {
            buf.append("\n");
            buf.append(o.trim());
            //buf.append(o);
        /////    buf.append("\n");

        }
        return buf.toString().trim();
    }

    private static List<String> prepContentForRewriteFormat(List<String> contentFromFile) {
        List<String> list = new ArrayList();

        for (String cc : contentFromFile) {
            //    System.out.println("c: " + cc);

            // if (!cc.trim().isBlank()) {
            //  String s = " [ " + cc + SPECIAL_DELIMITER + cc + " ] ";
            String s = OUTER_DELIMITER + cc + SPECIAL_DELIMITER + cc + OUTER_DELIMITER;
            list.add(s.trim());

            //  }
        }
        return list;
    }

    public static List<String> parseFileLinesFromFile(final File file) {

        //   System.out.println("stating...");
        String[] c = FileReadWriteOperationsUtility.readFileContents(file);
        List<String> list = new ArrayList();
        for (String cc : c) {
            // System.out.println("c: " + cc);

            if (!cc.trim().isBlank()) {
                list.add(cc.trim());
            }
        }
        return list;
    }

    public static List<String> removeEmptyContent(List<String> content) {
        List<String> list = new ArrayList();

        for (String cc : content) {
            //    System.out.println("c: " + cc);

            if (!cc.trim().isBlank()) {
                list.add(cc.trim());

            }
        }
        return list;
    }

    public static List<String> toLowerCaseAll(List<String> content) {
        List<String> list = new ArrayList();

        for (String cc : content) {
            //    System.out.println("c: " + cc);

            //if (!cc.trim().isBlank()) {
            list.add(cc.toLowerCase());

            //}
        }
        return list;
    }

    public static void printCurrentContent(List<String> content) {
        System.out.println();
        System.out.println("list of all strings to process");
        System.out.println();
        for (String cc : content) {
            System.out.println("content: " + cc);

            //if (!cc.trim().isBlank()) {
            //   list.add(cc.toLowerCase());
            //}
        }
    }

    public static List<String> splitContentOnPeriods(List<String> content) {
        System.out.println();
        System.out.println("list of all strings to process");
        System.out.println();
        List<String> list = new ArrayList();
        for (final String cc : content) {

            System.out.println("in: " + cc);

            if (cc.trim().startsWith("#")) {
                //    System.out.println("skipping b/c header");
                list.add(cc);
//     list.add(StringUtil.toBookCase(cc));
                continue;
            }

            String[] splitOut = cc.split("[\\.;]");

            System.out.println("out: " + cc);
            for (final String split : splitOut) {

                System.out.println("split: " + split);
                list.add(split + ".");
            }
        }
        return list;
    }
    public static List<String> splitContentOnQuestionMarks(List<String> content) {
        System.out.println();
        System.out.println("list of all strings to process");
        System.out.println();
        List<String> list = new ArrayList();
        for (final String cc : content) {

            System.out.println("in: " + cc);

            if (cc.trim().startsWith("#")) {
                //    System.out.println("skipping b/c header");
                list.add(cc);
//     list.add(StringUtil.toBookCase(cc));
                continue;
            }

            String[] splitOut = cc.split("\\?");

            System.out.println("out: " + cc);
            for (final String split : splitOut) {

                System.out.println("???: " + split);
             if(!split.endsWith(".")) {  list.add(split + "?");}else{list.add(split);}
            }
        }
        return list;
    }

    public static List<String> capitalizeContent(List<String> content) {
        System.out.println();
        System.out.println("list of all strings to process");
        System.out.println();
        List<String> list = new ArrayList();
        for (final String cc : content) {

            System.out.println("in: " + cc);

            if (cc.trim().startsWith("#")) {
                //    System.out.println("skipping b/c header");
                list.add(StandardTextFormattingUtility.toBookCase(cc));
                continue;
            }

            String[] splitOut = cc.split("\\.");

            System.out.println("out: " + cc);
            for (final String split : splitOut) {

                System.out.println("split: " + split);
                list.add(StandardTextFormattingUtility.toSentenceCase(split) + ".");
            }
        }
        return list;
    }

    public static List<String> addSpacesAndStuff(List<String> content) {
        System.out.println();
        System.out.println("list of all strings to process");
        System.out.println();
        List<String> list = new ArrayList();

        String BLANKLINE = "\n";

        for (final String cc : content) {

            System.out.println("in: " + cc);

            if (cc.trim().startsWith("#")) {
                //    System.out.println("skipping b/c header");
                list.add(cc);

                list.add(BLANKLINE);

            } else {
                list.add(cc);

                list.add(BLANKLINE);
            }
        }
        return list;
    }

    public static String rew333rite(String content) throws IOException, InterruptedException, URISyntaxException {

        System.out.println("enter :: rew333rite");

        String[] contenttt = content.split("\n");

        List<String> rewrittenContent = new ArrayList();

        for (String originalText : contenttt) {

            if (originalText.startsWith(rewrite_DELIMITER)) {

                System.out.println(" -> rewrite: " + originalText);

                String isolatedLeft = originalText.split(SPECIAL_DELIMITER)[0].replaceFirst(rewrite_DELIMITER, emptystring);

                String isolatedRight = originalText.split(SPECIAL_DELIMITER)[1];
                System.out.println(" -> -> isolated: " + isolatedRight);

                String rewrittenText = StandardTextFormattingUtility.toLeetSpeak(isolatedRight);
                System.out.println(" -> -> rewriten: " + rewrittenText);

                String AA = OUTER_DELIMITER + isolatedLeft + SPECIAL_DELIMITER + rewrittenText + OUTER_DELIMITER;
                System.out.println(" <- rewritten: " + AA);

                rewrittenContent.add(AA);

            } else {
                rewrittenContent.add(originalText);
            }
        }

        StringBuilder buf = new StringBuilder();

        for (String o : rewrittenContent) {
            buf.append(o.trim());
            buf.append("\n");
            // buf.append("\n");
        }

        return buf.toString().trim();
    }

    public static List<String> removeBadContent(List<String> contentFromFile) {
        return contentFromFile;
    }

    public static void writeFinalFile(List<String> contentFromFile, File file) {
        for (String x : contentFromFile) {
            FileReadWriteOperationsUtility.writeToFile(x, file);
        }
    }

}
