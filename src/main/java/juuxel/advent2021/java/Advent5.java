package juuxel.advent2021.java;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Advent5 {
    public static void part1(String[] args) {
        partN(args, false);
    }

    public static void part2(String[] args) {
        partN(args, true);
    }

    private static void partN(String[] args, boolean part2) {
        Pattern pattern = Pattern.compile("^([0-9]+),([0-9]+) -> ([0-9]+),([0-9]+)$");
        Map<Vec2i, PointData> data = new HashMap<>();

        for (String line : args) {
            Matcher matcher = pattern.matcher(line);

            if (matcher.matches()) {
                int x1 = Integer.parseInt(matcher.group(1));
                int x2 = Integer.parseInt(matcher.group(3));
                int y1 = Integer.parseInt(matcher.group(2));
                int y2 = Integer.parseInt(matcher.group(4));

                if (x1 == x2) {
                    for (int y = Math.min(y1, y2); y <= Math.max(y1, y2); y++) {
                        data.computeIfAbsent(new Vec2i(x1, y), v -> new PointData()).oneUp();
                    }
                } else if (y1 == y2) {
                    for (int x = Math.min(x1, x2); x <= Math.max(x1, x2); x++) {
                        data.computeIfAbsent(new Vec2i(x, y1), v -> new PointData()).oneUp();
                    }
                } else if (part2) {
                    int xA, yA, xB, yB;

                    if (x1 < x2) {
                        xA = x1;
                        xB = x2;
                        yA = y1;
                        yB = y2;
                    } else {
                        xB = x1;
                        xA = x2;
                        yB = y1;
                        yA = y2;
                    }

                    int y = yA;
                    int k = yB - yA > 0 ? 1 : -1;

                    for (int x = xA; x <= xB; x++) {
                        data.computeIfAbsent(new Vec2i(x, y), v -> new PointData()).oneUp();
                        y += k;
                    }
                }
            } else {
                throw new UnsupportedOperationException();
            }
        }

        long result = data.values().stream().filter(point -> point.lines >= 2).count();
        System.out.println(result);
    }

    private static record Vec2i(int x, int y) {
    }

    private static class PointData {
        int lines = 0;

        void oneUp() {
            lines++;
        }
    }
}
