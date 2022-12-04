package juuxel.advent2021.java;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Deprecated // broken
public final class Advent5Linear {
    public static void part1(String[] args) {
        partN(args, false);
    }

    public static void part2(String[] args) {
        partN(args, true);
    }

    private static void partN(String[] args, boolean part2) {
        Pattern pattern = Pattern.compile("^([0-9]+),([0-9]+) -> ([0-9]+),([0-9]+)$");
        List<Line> lines = new ArrayList<>();

        for (String line : args) {
            Matcher matcher = pattern.matcher(line);

            if (matcher.matches()) {
                int x1 = Integer.parseInt(matcher.group(1));
                int x2 = Integer.parseInt(matcher.group(3));
                int y1 = Integer.parseInt(matcher.group(2));
                int y2 = Integer.parseInt(matcher.group(4));

                if (!part2 && x1 != x2 && y1 != y2) continue;

                lines.add(Line.of(x1, x2, y1, y2));
            } else {
                throw new UnsupportedOperationException();
            }
        }

        int n = pairs(lines).stream()
            .map(LinePair::intersection)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(Collectors.toSet())
            .size();

        System.out.println(n);
    }

    private static List<LinePair> pairs(List<Line> lines) {
        List<LinePair> pairs = new ArrayList<>();

        // inefficient, causes duplicates
        for (Line first : lines) {
            for (Line second : lines) {
                if (first.equals(second)) continue;
                pairs.add(new LinePair(first, second));
            }
        }

        return pairs;
    }

    // ax + by = c
    private static record Line(int a, int b, int c) {
        static Line of(int x1, int x2, int y1, int y2) {
            int a = y1 - y2;
            int b = x2 - x1;

            // det ( x1 x2 )
            //     ( y1 y2 )
            int c = x2 * y1 - x1 * y2;

            return new Line(a, b, c);
        }

        Optional<Vec2i> intersection(Line other) {
            int div = a * other.b - b * other.a;
            if (div == 0) return Optional.empty();
            int x = (c * other.b - b * other.c) / div;
            int y = (a * other.c - c * other.a) / div;
            return Optional.of(new Vec2i(x, y));
        }
    }

    private static record LinePair(Line first, Line second) {
        Optional<Vec2i> intersection() {
            return first.intersection(second);
        }
    }

    private static record Vec2i(int x, int y) {
    }
}
