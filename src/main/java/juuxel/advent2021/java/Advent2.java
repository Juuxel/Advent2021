package juuxel.advent2021.java;

public final class Advent2 {
    public static void part1(String[] args) {
        int x = 0, y = 0;

        for (String cmd : args) {
            int num = Integer.parseInt(cmd.substring(cmd.lastIndexOf(' ') + 1));

            switch (cmd.charAt(0)) {
                case 'f' -> x += num;
                case 'd' -> y += num;
                default -> y -= num; // up
            }
        }

        System.out.println(x * y);
    }

    public static void part2(String[] args) {
        int x = 0, y = 0, aim = 0;

        for (String cmd : args) {
            int num = Integer.parseInt(cmd.substring(cmd.lastIndexOf(' ') + 1));

            switch (cmd.charAt(0)) {
                case 'u' -> aim -= num;
                case 'd' -> aim += num;
                default -> { // forward
                    x += num;
                    y += aim * num;
                }
            }
        }

        System.out.println(x * y);
    }
}
