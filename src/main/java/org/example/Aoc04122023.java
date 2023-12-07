package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Aoc04122023 {
    public static void main(String[] args) {
        System.out.println(sum(35, "files/04122023data.txt"));
    }

    public static int sum(int totalAmountOfNumbersInALine, String fileName) {
        int result = 0;
        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                result += winnings(totalAmountOfNumbersInALine, scanner.nextLine().substring(10));
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File cannot be found.");
            e.printStackTrace();
        }
        return result;
    }

    private static int winnings(int totalAmountOfNumbersInALine, String line) {
        Set<Integer> numbers = new HashSet<>();
        Matcher matcher = Pattern.compile("\\d+").matcher(line);

        while (matcher.find()) {
            numbers.add(Integer.parseInt(matcher.group()));
        }

        int winners = totalAmountOfNumbersInALine - numbers.size();

        if (winners == 0) return 0;
        return (int) Math.pow(2, (double) winners - 1);
    }
}