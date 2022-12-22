package readability;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class Main {
    public static String readFile(String pathFile) {
        Scanner scanner;
        try {
            scanner = new Scanner(new File(pathFile));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return scanner.nextLine();
    }

    public static double ARI(String text) {
        int amountOfCharacters = characters(text);
        int amountOfWords = words(text);
        int amountOfSentences = sentences(text);

        return 4.71 * ((double) amountOfCharacters / amountOfWords) + 0.5 * ((double) amountOfWords / amountOfSentences) - 21.43;
    }

    public static double CL(String text) {
        int amountOfCharacters = characters(text);
        int amountOfWords = words(text);
        int amountOfSentences = sentences(text);

        double L = amountOfCharacters / (amountOfWords / 100.00);
        double S = amountOfSentences / (amountOfWords / 100.00);
        return 0.0588 * L - 0.296 * S - 15.8;
    }

    public static double SMOG(String text) {
        int amountOfSentences = sentences(text);
        int amountOfPolysyllables = polysyllables(text);

        return 1.043 * Math.sqrt(amountOfPolysyllables * (30.0 / amountOfSentences)) + 3.1291;
    }

    public static double FK(String text) {
        int amountOfWords = words(text);
        int amountOfSentences = sentences(text);
        int amountOfSyllables = syllables(text);
        return 0.39 * ((double) amountOfWords / amountOfSentences) + 11.8 * ((double) amountOfSyllables / amountOfWords) - 15.59;
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
        if (scoreCeil > 14) {
            age = 22;
        }
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

        String[] sentencesSplit = text.split("[.!?]");
        int countSyllables = 0;
        for (String sentence : sentencesSplit
        ) {
            String[] wordArray = sentence.split(" ");
            for (String word : wordArray
            ) {
                if (word.matches("\\S+")) {
                    int currentSyllables = 0;
                    String[] wordCharacters = word.split("");
                    for (int i = 0; i < wordCharacters.length; i++) {
                        if (isVowel(wordCharacters[i])) {
                            if (i == 0) {
                                currentSyllables++;
                            } else if (!isVowel(wordCharacters[i - 1])) {
                                if (i != wordCharacters.length - 1) {
                                    currentSyllables++;
                                } else if (!wordCharacters[i].toLowerCase().matches("e")) {
                                    currentSyllables++;
                                }
                            }
                        }
                    }
                    countSyllables += currentSyllables > 0 ? currentSyllables : 1;
                }
            }
        }
        return countSyllables;
    }

    private static boolean isVowel(String character) {
        return character.toLowerCase().matches("[aeiouy]");
    }

    public static int polysyllables(String text) {
        int countPolysyllables = 0;
        String[] sentencesSplit = text.split("[.!?]");
        for (String sentence : sentencesSplit
        ) {
            String[] wordsArray = sentence.split(" ");
            for (String word : wordsArray
            ) {
                if (word.matches("\\S+")) {
                    if (syllables(word) > 2) {
                        countPolysyllables++;
                    }
                }
            }
        }
        return countPolysyllables;
    }

    public static void main(String[] args) throws IOException {
        /*
         * stage 4
         */
        String text = Files.readString(Path.of(args[0]));
        startMenu(text);
    }

    protected static void startMenu(String text) {
        System.out.println("The text is:");
        System.out.println(text);
        System.out.println();
        System.out.println("Words: " + words(text));
        System.out.println("Sentences: " + sentences(text));
        System.out.println("Characters: " + characters(text));
        System.out.println("Syllables: " + syllables(text));
        System.out.println("Polysyllables: " + polysyllables(text));

        System.out.println("Enter the score you want to calculate (ARI, FK, SMOG, CL, all): ");
        Scanner scanner = new Scanner(System.in);
        String score = scanner.nextLine();

        System.out.println();
        switch (score.toLowerCase()) {
            case "all" -> {
                printARI(text);
                printFK(text);
                printSMOG(text);
                printCL(text);
                System.out.println();
                AverageAge(text);
            }
            case "ari" -> {
                printARI(text);
            }
            case "fk" -> {
                printFK(text);
            }
            case "smog" -> {
                printSMOG(text);
            }
            case "cl" -> {
                printCL(text);
            }
            default -> {
                System.out.println("Wrong input");
            }
        }
    }

    protected static void printARI(String text) {
        double scoreARI = ARI(text);
        int ageARI = ageText(ARI(text));
        System.out.printf("Automated Readability Index: %.2f (about %s-years-olds).\n", scoreARI, ageARI);
    }

    protected static void printFK(String text) {
        double scoreFK = FK(text);
        int ageFK = ageText(FK(text));
        System.out.printf("Flesch–Kincaid readability tests: %.2f (about %s-years-olds).\n", scoreFK, ageFK);
    }

    protected static void printSMOG(String text) {
        double scoreSMOG = SMOG(text);
        int ageSMOG = ageText(SMOG(text));
        System.out.printf("Simple Measure of Gobbledygook: %.2f (about %s-years-olds).\n", scoreSMOG, ageSMOG);

    }

    protected static void printCL(String text) {
        double scoreCL = CL(text);
        int ageCL = ageText(CL(text));
        System.out.printf("Coleman–Liau index: %.2f (about %s-years-olds).\n", scoreCL, ageCL);
    }

    protected static void AverageAge(String text) {
        double age = ageText(ARI(text)) + ageText(FK(text)) + ageText(SMOG(text)) + ageText(CL(text));
        System.out.printf("This text should be understood in average by %.2f-years-olds\n", age / 4);
    }
}
