package readability;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


public class Main {

    public static void main(String[] args) throws IOException {
        /*
         * stage 3
         */
        String file = Files.readString(Path.of(args[0]));
        String[] strings = file.split("\\s+");
        int words = strings.length;
        int sentences = file.split("[!.?]").length;
        int characters = file.replaceAll("\\s+", "").length();
        double score = 4.71 * ((double) characters / words) + 0.5 * ((double) words / sentences) - 21.43;
        int scoreCeil = (int) Math.ceil(score);
        String age = switch (scoreCeil) {
            case 1 -> "5-6";
            case 2 -> "6-7";
            case 3 -> "7-8";
            case 4 -> "8-9";
            case 5 -> "9-10";
            case 6 -> "10-11";
            case 7 -> "11-12";
            case 8 -> "12-13";
            case 9 -> "13-14";
            case 10 -> "14-15";
            case 11 -> "15-16";
            case 12 -> "16-17";
            case 13 -> "17-18";
            case 14 -> "18-22";
            default -> null;
        };

        System.out.println("The text is:");
        System.out.println(file);
        System.out.println();
        System.out.println("Words: " + words);
        System.out.println("Sentences: " + sentences);
        System.out.println("Characters: " + characters);
        System.out.printf("The score is: %.2f\n", score);
        System.out.println("This text should be understood by " + age + " year-olds.");

    }
}
