package readability;

import java.io.IOException;
import java.util.Scanner;

import static java.lang.Math.round;

public class ReadabilityAge {

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        String filePath = args[0];
        TextFile textFile = new TextFile(filePath);

        System.out.println("Words: " + textFile.getWords());
        System.out.println("Sentences: " + textFile.getSentences());
        System.out.println("Characters: " + textFile.getCharacters());
        System.out.println("Syllables: " + textFile.getSyllabus());
        System.out.println("Polysyllables: " + textFile.getPolysyllabus());

        String option = "";
        System.out.print("Enter the score you want to calculate (ARI, FK, SMOG, CL, all): ");
        option = scanner.nextLine();
        if (option.equalsIgnoreCase("ARI")) {
            textFile.calculateARIndex();
            System.out.printf("Automated Readability Index: %.2f ", textFile.getARIndex());
            System.out.printf("(about %d year olds).\n", getReadabilityAge(textFile.getARIndex()));
        } else if (option.equalsIgnoreCase("FK")) {
            textFile.calculateFKIndex();
            System.out.printf("Flesch–Kincaid readability tests: %.2f ", textFile.getFKIndex());
            System.out.printf("(about %d year olds).\n", getReadabilityAge(textFile.getFKIndex()));
        } else if (option.equalsIgnoreCase("SMOG")) {
            textFile.calculateSMOGIndex();
            System.out.printf("Simple Measure of Gobbledygook: %.2f ", textFile.getSMOGIndex());
            System.out.printf("(about %d year olds).\n", getReadabilityAge(textFile.getSMOGIndex()));
        } else if (option.equalsIgnoreCase("CL")) {
            textFile.calculateCLIndex();
            System.out.printf("Coleman–Liau index: %.2f ", textFile.getCLIndex());
            System.out.printf("(about %d year olds).\n", getReadabilityAge(textFile.getCLIndex()));
        } else if (option.equalsIgnoreCase("ALL")) {
            textFile.calculateAll();

            double averageAge = 0.0;

            System.out.printf("\nAutomated Readability Index: %.2f ", textFile.getARIndex());
            System.out.printf("(about %d year olds).\n", getReadabilityAge(textFile.getARIndex()));
            averageAge += getReadabilityAge(textFile.getARIndex());

            System.out.printf("Flesch–Kincaid readability tests: %.2f ", textFile.getFKIndex());
            System.out.printf("(about %d year olds).\n", getReadabilityAge(textFile.getFKIndex()));
            averageAge += getReadabilityAge(textFile.getFKIndex());

            System.out.printf("Simple Measure of Gobbledygook: %.2f ", textFile.getSMOGIndex());
            System.out.printf("(about %d year olds).\n", getReadabilityAge(textFile.getSMOGIndex()));
            averageAge += getReadabilityAge(textFile.getSMOGIndex());

            System.out.printf("Coleman–Liau index: %.2f ", textFile.getCLIndex());
            System.out.printf("(about %d year olds).\n", getReadabilityAge(textFile.getCLIndex()));
            averageAge += getReadabilityAge(textFile.getCLIndex());

            averageAge/=4;
            System.out.printf("\nThis text should be understood in average by %.2f year olds.\n", averageAge);

        } else {
            System.out.println("Enter Valid Option!");
        }
    }

    static int getReadabilityAge(double readabilityScore) {
        int roundedScore = (int) round(readabilityScore);
        int[] age = new int[]{0, 6, 7, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 24, 25};
        return age[roundedScore];
    }
}