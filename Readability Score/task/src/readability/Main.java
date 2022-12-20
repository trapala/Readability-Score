package readability;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;


public class Main {
    protected static String readFile(String pathFile) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(pathFile));
        return scanner.nextLine();
    }

    public static double ARI(String text) {
        int amountOfCharacter = characters(text);
        int amountOfWords = words(text);
        int amountOfSentences = sentences(text);

        return 4.71 * ((double) amountOfCharacter / amountOfWords) + 0.5 * ((double) amountOfWords / amountOfSentences) - 21.43;
    }

    protected static int ageText(double score) {
        int scoreCeil = (int) Math.ceil(score);
        int age = switch (scoreCeil) {
            case 1 -> 6;
            case 2 -> 7;
            case 3 -> 8;
            case 4 -> 9;
            case 5 -> 10;
            case 6 -> 11;
            case 7 -> 12;
            case 8 -> 13;
            case 9 -> 14;
            case 10 -> 15;
            case 11 -> 16;
            case 12 -> 17;
            case 13 -> 18;
            case 14 -> 22;
            default -> 0;
        };
        return age;
    }


    public static int words(String text) {
        return text.split("\\s+").length;
    }

    public static int sentences(String text) {
        return text.split("[!.?]").length;
    }

    public static int characters(String text) {
        return text.replaceAll("\\s+", "").length();
    }

    public static int syllables(String text) {
        return 0;
    }

    public static int polysyllables(String text) {
        return 0;
    }

    public static void main(String[] args) throws IOException {
        /*
         * stage 3
         */

        String text = readFile(args[0]);

        System.out.println("The text is:");
        System.out.println(text);
        System.out.println();
        System.out.println("Words: " + words(text));
        System.out.println("Sentences: " + sentences(text));
        System.out.println("Characters: " + characters(text));
        System.out.printf("The score is: %.2f\n", ARI(text));
        System.out.println("This text should be understood by " + ageText(ARI(text)) + " year-olds.");
    }
}
