package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Aoc03122023 {
    public static void main(String[] args) {
        System.out.println(sum("files/03122023data.txt"));
    }

    public static int sum(String fileName) {
        int result = 0;
        try {
            List<String> lines = replaceSpecialCharactersAndAddLinesAndDotsToBorders(Files.readAllLines(Paths.get(fileName)));
            result += findEnginePart(lines);
        } catch (IOException e) {
            System.out.println("The file cannot be read!");
            e.printStackTrace();
        }
        return result;
    }

    private static List<String> replaceSpecialCharactersAndAddLinesAndDotsToBorders(List<String> lines) {
        lines.add(0, "............................................................................................................................................");
        lines.add(lines.size(), "............................................................................................................................................");
        lines.replaceAll(s -> ".".concat(s.concat(".")));
        lines.replaceAll(s -> s.replaceAll("[^\\w.]", "*"));
        return lines;
    }

    public static int findEnginePart(List<String> lines) {
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
}
