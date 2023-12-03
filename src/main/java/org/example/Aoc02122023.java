package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Aoc02122023 {
    public static void main(String[] args) {
        System.out.println(sum(new int[]{12, 13, 14}, "files/02122023data.txt"));
    }

    public static String sum(int[] maxAmountOfColors, String fileName) {
        int resultFirstTask = 0;
        int resultSecondTask = 0;
        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                resultFirstTask += findPossibleGame(maxAmountOfColors, line);
                resultSecondTask += findLeastAmountOfColorsNeeded(line);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File cannot be found.");
            e.printStackTrace();
        }
        return "result of first task: " + resultFirstTask + "\nresult of second task: " + resultSecondTask;
    }

    public static int getId(String line) {
        Matcher matcher = Pattern.compile("\\d+").matcher(line);
        int index = 0;
        if (matcher.find()) {
            index = Integer.parseInt(matcher.group());
        }
        return index;
    }

    public static int findPossibleGame(int[] maxAmountOfColors, String line) {
        List<String> sets = new ArrayList<>(List.of(line.substring(line.indexOf(":") + 2).split("; |, ")));

        for (String set : sets) {
            String[] colors = new String[]{"red", "green", "blue"};
            for (int j = 0; j < colors.length; j++) {
                if (set.contains(colors[j])) {
                    String[] number = set.split("\\s", 2);
                    int amount = Integer.parseInt(number[0]);
                    if (amount > maxAmountOfColors[j]) {
                        return 0;
                    }
                }
            }
        }

        return getId(line);
    }

    public static int findLeastAmountOfColorsNeeded(String line) {
        int[] colorsNeeded = new int[3]; //order: red, green, blue

        List<String> sets = new ArrayList<>(List.of(line.substring(line.indexOf(":") + 2).split("; |, ")));

        for (String set : sets) {
            String[] colors = new String[]{"red", "green", "blue"};
            for (int j = 0; j < colors.length; j++) {
                if (set.contains(colors[j])) {
                    String[] number = set.split("\\s", 2);
                    int amount = Integer.parseInt(number[0]);
                    if (amount > colorsNeeded[j]) {
                        colorsNeeded[j] = amount;
                    }
                }
            }
        }

        return colorsNeeded[0] * colorsNeeded[1] * colorsNeeded[2];
    }
}
