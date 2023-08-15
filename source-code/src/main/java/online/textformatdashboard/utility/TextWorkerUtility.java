package online.textformatdashboard.utility;

public class TextWorkerUtility {

    public static double calculateTextSimilarityPercentage(final String originalContentString, String rewrittenContentString) {

        if (originalContentString == null || rewrittenContentString == null) {
            throw new IllegalArgumentException("Strings must not be null");
        }

        double maxLength = Double.max(originalContentString.length(), rewrittenContentString.length());

        if (maxLength > 0) {
            // optionally ignore case if needed
            return (maxLength - calculateLevenshteinDistance(originalContentString, rewrittenContentString)) / maxLength;
        }

        return 1.0;
    }

    private static int calculateLevenshteinDistance(final String originalContentString, final String rewrittenContentString) {

        int m = originalContentString.length();
        int n = rewrittenContentString.length();

        int[][] T = new int[m + 1][n + 1];
        for (int i = 1; i <= m; i++) {
            T[i][0] = i;
        }
        for (int j = 1; j <= n; j++) {
            T[0][j] = j;
        }

        int cost;

        for (int i = 1; i <= m; i++) {

            for (int j = 1; j <= n; j++) {

                cost = originalContentString.charAt(i - 1) == rewrittenContentString.charAt(j - 1) ? 0 : 1;

                T[i][j] = Integer.min(Integer.min(T[i - 1][j] + 1, T[i][j - 1] + 1),

                        T[i - 1][j - 1] + cost);

            }

        }

        return T[m][n];

    }

}