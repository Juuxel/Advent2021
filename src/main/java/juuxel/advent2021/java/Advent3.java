package juuxel.advent2021.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Advent3 {
    private int n, w;
    private final int[] values;
    private int[] ones;

    private Advent3(String[] args) {
        n = args.length;
        w = -1;
        values = new int[n];

        for (int j = 0; j < args.length; j++) {
            String line = args[j];
            values[j] = Integer.parseInt(line, 2);

            if (w == -1) {
                w = line.length();
                ones = new int[w];
            }

            for (int i = 0; i < w; i++) {
                if (line.charAt(i) == '1') ones[w - (i + 1)] += 1;
            }
        }
    }

    public static void part1(String[] args) {
        Advent3 main = new Advent3(args);

        int gamma = 0, epsilon = 0;

        for (int i = 0; i < main.w; i++) {
            int common = 2 * main.ones[i] > main.n ? 1 : 0;
            int least = 1 - common;
            gamma |= common << i;
            epsilon |= least << i;
        }

        System.out.println(gamma * epsilon);
    }

    @Deprecated
    public static void part2(String[] args) {
        Advent3 main = new Advent3(args);
        int o2 = main.findO2();
        int co2 = main.findCO2();
        System.out.println(o2 * co2);
    }

    private int findO2() {
        List<Integer> remaining = Arrays.stream(values).boxed().collect(Collectors.toCollection(ArrayList::new));

        for (int i = 1; i <= w; i++) {
            int j = w - i;
            int o = ones[j];
            int mostCommon = (2 * o >= n) ? 1 : 0;
            remaining.removeIf(value -> ((value >> j) & 1) == mostCommon);

            if (remaining.size() == 1) {
                return remaining.get(0);
            }
        }

        throw new AssertionError("what");
    }

    private int findCO2() {
        List<Integer> remaining = Arrays.stream(values).boxed().collect(Collectors.toCollection(ArrayList::new));

        for (int i = 1; i <= w; i++) {
            int j = w - i;
            int o = ones[j];
            int leastCommon = (2 * o < n) ? 1 : 0;
            remaining.removeIf(value -> ((value >> j) & 1) == leastCommon);

            if (remaining.size() == 1) {
                return remaining.get(0);
            }
        }

        throw new AssertionError("what");
    }
}
