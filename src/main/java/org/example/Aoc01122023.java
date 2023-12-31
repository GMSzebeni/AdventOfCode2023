package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Aoc01122023 {
    public static void main(String[] args) {
        System.out.println(calibrate("files/01122023data.txt"));
    }

    public static int getNumbersFromLines(String line) {
        line = replaceTextWithNumbers(line);
        int number = 0;
        Matcher matcherFirst = Pattern.compile("\\d").matcher(line);
        Matcher matcherLast = Pattern.compile("\\d(?!.*\\d)").matcher(line);
        if (matcherFirst.find() && matcherLast.find()) {
            number += Integer.parseInt(matcherFirst.group()) * 10;
            number += Integer.parseInt(matcherLast.group());
        }
        return number;
    }

    public static int calibrate(String fileName) {
        int result = 0;
        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                result += getNumbersFromLines(scanner.nextLine());
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File cannot be found.");
            e.printStackTrace();
        }
        return result;
    }

    public static String replaceTextWithNumbers(String line) {
        String[] numbersFromText = new String[]{"zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};
        for (int i = 0; i < numbersFromText.length; i++) {
            if (line.contains(numbersFromText[i])) {
                line = line.replace(numbersFromText[i], numbersFromText[i] + i + numbersFromText[i]);
            }
        }
        return line;
    }
}
