package juuxel.advent2021.java;

import java.util.Arrays;

public final class Advent6 {
    public static void part1(String data) {
        partN(data, 80);
    }

    public static void part2(String data) {
        partN(data, 256);
    }

    private static void partN(String data, int days) {
        long[] fishByAge = new long[9]; // 9 = 0..8

        // initial setup
        for (String age : data.trim().split(",")) {
            fishByAge[Integer.parseInt(age)] += 1;
        }

        // simulation
        for (int day = 0; day < days; day++) {
            long fishAt0 = fishByAge[0];
            // shift 1..8 to 0..7
            // noinspection SuspiciousSystemArraycopy: please idea this is correct
            System.arraycopy(fishByAge, 1, fishByAge, 0, 8);
            // the old fish at 0 go back to 6...
            fishByAge[6] += fishAt0;
            // ...and create the same number of new fish at 8
            fishByAge[8] = fishAt0;
        }

        long fish = 0;

        for (long count : fishByAge) {
            fish += count;
        }

        System.out.println(fish);
    }
}
