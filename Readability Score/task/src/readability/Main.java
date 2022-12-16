package readability;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        /*
         * stage 2
         */
        Scanner scanner = new Scanner(System.in);
        String[] strings = scanner.nextLine().split("[!.?]");


        double avg = 0.0;
        for (int i = 0; i < strings.length; i++) {
            String[] strArray = strings[i].split("\\s+");

            avg += i == 1 ? strArray.length : strArray.length - 1;
        }

        double result = avg / strings.length;

        System.out.println(result > 10 ? "HARD" : "EASY");

    }
}
