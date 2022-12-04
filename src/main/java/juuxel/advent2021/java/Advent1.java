package juuxel.advent2021.java;

public class Advent1 {
    public static void part1(String[] args) {
        int prev = -1;
        int incr = 0;

        for (int i = 0; i < args.length; i++) {
            int next = Integer.parseInt(args[i]);

            if (i != 0 && prev < next) incr++;

            prev = next;
        }

        System.out.println(incr);
    }

    public static void part2(String[] args) {
        int prevsum = 0;
        int prevprev = 0, prev = 0;
        int incr = 0;

        for (int i = 0; i < args.length; i++) {
            int curr = Integer.parseInt(args[i]);

            if (i >= 2) {
                int sum = prevprev + prev + curr;
                if (i > 2 && sum > prevsum) incr++;
                prevsum = sum;
            }

            prevprev = prev;
            prev = curr;
        }

        System.out.println(incr);
    }
}
