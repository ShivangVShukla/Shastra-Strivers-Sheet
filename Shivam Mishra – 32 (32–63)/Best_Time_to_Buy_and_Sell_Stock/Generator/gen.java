
import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public class gen {

    static final int TOTAL = 101;
    static final int MAX_PRICE = 1_000_000;
    static final Random random = new Random();

    static int randomInt(int min, int max) {
        return min + random.nextInt(max - min + 1);
    }

    static int[] generatePrices(int testIndex) {
        int n;

        if (testIndex == 0) {
            n = 1;
        } else if (testIndex == 1) {
            n = 2;
        } else if (testIndex == 2) {
            n = 10;
        } else if (testIndex % 15 == 0) {
            n = 100_000;
        } else if (testIndex % 7 == 0) {
            n = randomInt(1000, 10000);
        } else {
            n = randomInt(1, 300);
        }

        int[] prices = new int[n];
        int pattern = testIndex % 6;

        switch (pattern) {
            case 0:
                // Completely random prices
                for (int i = 0; i < n; i++) {
                    prices[i] = randomInt(0, MAX_PRICE);
                }
                break;

            case 1:
                // Increasing prices
                int value = randomInt(
                    0, Math.max(0, MAX_PRICE - n)
                );

                for (int i = 0; i < n; i++) {
                    prices[i] = value + i;
                }
                break;

            case 2:
                // Decreasing prices
                value = randomInt(0, MAX_PRICE);

                for (int i = 0; i < n; i++) {
                    prices[i] = Math.max(0, value - i);
                }
                break;

            case 3:
                // Repeated prices
                value = randomInt(0, MAX_PRICE);
                Arrays.fill(prices, value);
                break;

            case 4:
                // Random walk
                value = randomInt(0, MAX_PRICE);

                for (int i = 0; i < n; i++) {
                    value += randomInt(-10000, 10000);
                    value = Math.max(
                        0, Math.min(MAX_PRICE, value)
                    );
                    prices[i] = value;
                }
                break;

            default:
                // Random prices with boundary values
                for (int i = 0; i < n; i++) {
                    int choice = random.nextInt(10);

                    if (choice == 0) {
                        prices[i] = 0;
                    } else if (choice == 1) {
                        prices[i] = MAX_PRICE;
                    } else {
                        prices[i] = randomInt(0, MAX_PRICE);
                    }
                }
                break;
        }

        return prices;
    }

    static long maxProfit(int[] prices) {
        int minPrice = Integer.MAX_VALUE;
        long bestProfit = 0;

        for (int price : prices) {
            minPrice = Math.min(minPrice, price);

            bestProfit = Math.max(
                bestProfit, (long) price - minPrice
            );
        }

        return bestProfit;
    }

    static String formatInput(int[] prices) {
        StringBuilder sb = new StringBuilder();

        sb.append(prices.length);

        for (int price : prices) {
            sb.append(' ').append(price);
        }

        return sb.toString();
    }

    public static void main(String[] args) throws IOException {
        Path baseDir = Paths.get("Testcases");

        if (!Files.isDirectory(baseDir)) {
            baseDir = Paths.get("../Testcases");
        }

        Path inputDir = baseDir.resolve("input");
        Path outputDir = baseDir.resolve("output");

        Files.createDirectories(inputDir);
        Files.createDirectories(outputDir);

        for (int i = 0; i < TOTAL; i++) {
            int[] prices = generatePrices(i);

            String suffix = String.format("%02d", i + 1);

            Files.writeString(
                inputDir.resolve("input" + suffix + ".txt"),
                formatInput(prices)
                    + System.lineSeparator()
            );

            Files.writeString(
                outputDir.resolve("output" + suffix + ".txt"),
                maxProfit(prices)
                    + System.lineSeparator()
            );
        }

        System.out.println(
            "Generated " + TOTAL
            + " random test cases successfully."
        );

        System.out.println(
            "Input directory: "
            + inputDir.toAbsolutePath()
        );

        System.out.println(
            "Output directory: "
            + outputDir.toAbsolutePath()
        );
    }
}
