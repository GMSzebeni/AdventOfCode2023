package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Aoc04122023 {
    public static void main(String[] args) {
        System.out.println(sum(35, "files/04122023data.txt"));
    }

    public static String sum(int totalAmountOfNumbersInALine, String fileName) {
        int resultOfFirstTask = 0;
        int resultOfSecondTask = 0;
        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                int winnings = winningsForFirstTask(totalAmountOfNumbersInALine, line.substring(10));
                resultOfFirstTask += winnings;
            }
            scanner.close();
            resultOfSecondTask = resultOfSecondTask(totalAmountOfNumbersInALine, Files.readAllLines(Path.of("files/04122023data.txt")));
        } catch (FileNotFoundException e) {
            System.out.println("File cannot be found.");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("File cannot be read.");
            e.printStackTrace();
        }
        return "Result of first task: " + resultOfFirstTask + " & result of second task: " + resultOfSecondTask;
    }

    private static int winningsForFirstTask(int totalAmountOfNumbersInALine, String line) {
        Set<Integer> numbers = new HashSet<>();
        Matcher matcher = Pattern.compile("\\d+").matcher(line);

        while (matcher.find()) {
            numbers.add(Integer.parseInt(matcher.group()));
        }

        int winners = totalAmountOfNumbersInALine - numbers.size();

        if (winners == 0) return 0;
        return (int) Math.pow(2, (double) winners - 1);
    }

    private static int resultOfSecondTask(int totalAmountOfNumbersInALine, List<String> lines) {
        List<List<Integer>> amountsAndWinnings = new ArrayList<>();
        for (String line : lines) {
            amountsAndWinnings.add(new ArrayList<>(List.of(1, winningsForSecondTask(totalAmountOfNumbersInALine, line.substring(10)))));
        }

        int result = 0;

        for (int i = 1; i < amountsAndWinnings.size(); i++) {
            int winnersInATicket = amountsAndWinnings.get(i - 1).get(1);
            int amountOfTicketsLeft = amountsAndWinnings.size() - (i - 1);
            for (int j = i; j < i + winnersInATicket && winnersInATicket < amountOfTicketsLeft; j++) {
                amountsAndWinnings.get(j).set(0, amountsAndWinnings.get(j).get(0) + amountsAndWinnings.get(i - 1).get(0));
            }
            result += amountsAndWinnings.get(i - 1).get(0);
        }

        return result + amountsAndWinnings.get(amountsAndWinnings.size() - 1).get(0);
    }

    private static int winningsForSecondTask(int totalAmountOfNumbersInALine, String line) {
        Set<Integer> numbers = new HashSet<>();
        Matcher matcher = Pattern.compile("\\d+").matcher(line);

        while (matcher.find()) {
            numbers.add(Integer.parseInt(matcher.group()));
        }

        return totalAmountOfNumbersInALine - numbers.size();
    }
}