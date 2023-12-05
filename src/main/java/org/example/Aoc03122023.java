package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Aoc03122023 {
    private static final String DOTTED_LINE = "............................................................................................................................................";
    public static void main(String[] args) {
        System.out.println(sumForFirstTask("files/03122023data.txt"));
        System.out.println(sumForSecondTask("files/03122023data.txt"));
    }

    public static int sumForFirstTask(String fileName) {
        int result = 0;
        try {
            List<String> lines = replaceSpecialCharactersAndAddLinesAndDotsToBordersForFirstTask(Files.readAllLines(Paths.get(fileName)));
            result = findEnginePartForFirstTask(lines);
        } catch (IOException e) {
            System.out.println("The file cannot be read!");
            e.printStackTrace();
        }
        return result;
    }

    private static List<String> replaceSpecialCharactersAndAddLinesAndDotsToBordersForFirstTask(List<String> lines) {
        lines.add(0, DOTTED_LINE);
        lines.add(lines.size(), DOTTED_LINE);
        lines.replaceAll(s -> ".".concat(s.concat(".")));
        lines.replaceAll(s -> s.replaceAll("[^\\w.]", "*"));
        return lines;
    }

    public static int findEnginePartForFirstTask(List<String> lines) {
        int sumOfPartNumbers = 0;

        for (int i = 0; i < lines.get(i).length() - 1; i++) {
            Matcher matcher = Pattern.compile("\\d+").matcher(lines.get(i));

            while (matcher.find()) {
                int numberStartIndex = matcher.start();
                int numberEndIndex = matcher.end();
                int number= (Integer.parseInt(matcher.group()));

                String previousLine = lines.get(i - 1).substring(numberStartIndex - 1, numberEndIndex + 1);
                String currentLine = lines.get(i).substring(numberStartIndex - 1, numberEndIndex + 1);
                String nextLine = lines.get(i + 1).substring(numberStartIndex - 1, numberEndIndex + 1);
                String area = previousLine.concat(currentLine).concat(nextLine);
                if (area.contains("*")) sumOfPartNumbers += number;
            }
        }

        return sumOfPartNumbers;
    }

    public static int sumForSecondTask(String fileName) {
        int result = 0;
        try {
            List<String> lines = addLinesAndDotsToBordersForSecondTask(Files.readAllLines(Paths.get(fileName)));
            result = findEnginePartForSecondTask(lines);
        } catch (IOException e) {
            System.out.println("The file cannot be read!");
            e.printStackTrace();
        }
        return result;
    }

    private static List<String> addLinesAndDotsToBordersForSecondTask(List<String> lines) {
        lines.add(0, DOTTED_LINE);
        lines.add(lines.size(), DOTTED_LINE);
        lines.replaceAll(s -> ".".concat(s.concat(".")));
        return lines;
    }

    public static int findEnginePartForSecondTask(List<String> lines) {
        int sumOfPartNumbers = 0;

        for (int i = 0; i < lines.size(); i++) {
            Matcher matcherForStar = Pattern.compile("\\*").matcher(lines.get(i));
            while (matcherForStar.find()) {
                int findNumberFrom = matcherForStar.start();
                int findNumberTo = matcherForStar.end();

                String previousLine = lines.get(i - 1).substring(findNumberFrom - 3, findNumberTo + 3);
                String currentLine = lines.get(i).substring(findNumberFrom - 3, findNumberTo + 3);
                String nextLine = lines.get(i + 1).substring(findNumberFrom - 3, findNumberTo + 3);
                String[] area = new String[]{previousLine, currentLine, nextLine};

                List<Integer> numbersFound = checkArea(area);

                if(numbersFound.size() > 1) {
                    sumOfPartNumbers += numbersFound.get(0) * numbersFound.get(1);
                }
            }
        }

        return sumOfPartNumbers;
    }

    public static List<Integer> checkArea(String[] area) {
        List<Integer> numbersFound = new ArrayList<>();
        for (String line : area) {
            Matcher matcherForNumber = Pattern.compile("\\d+").matcher(line);
            while (matcherForNumber.find()) {
                int number = Integer.parseInt(matcherForNumber.group());
                int startIndex = matcherForNumber.start();
                int endIndex = matcherForNumber.end() - 1;
                if ((startIndex > 1 && startIndex < 5) || (endIndex > 1 && endIndex < 5)) {
                    numbersFound.add(number);
                }
            }
        }
        return numbersFound;
    }
}
