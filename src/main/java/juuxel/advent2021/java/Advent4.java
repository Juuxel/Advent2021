package juuxel.advent2021.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Advent4 {
    private final int[] guesses;
    private final Map<Integer, List<MatPos>> locs;
    private final List<Mat> mats;

    private Advent4(String data) {
        Iterator<String> lines = data.lines().iterator();
        guesses = Arrays.stream(lines.next().split(",")).mapToInt(Integer::parseInt).toArray();
        locs = new HashMap<>();
        mats = new ArrayList<>();

        while (lines.hasNext()) {
            lines.next(); // skip empty line
            Mat mat = new Mat();
            MatElement[][] rows = new MatElement[5][];

            for (int y = 0; y < 5; y++) {
                MatElement[] row = rows[y] = Arrays.stream(lines.next().trim().split(" +"))
                    .mapToInt(Integer::parseInt)
                    .mapToObj(MatElement::new)
                    .toArray(MatElement[]::new);

                for (int x = 0; x < row.length; x++) {
                    MatElement value = row[x];
                    locs.computeIfAbsent(value.value, val -> new ArrayList<>()).add(new MatPos(mat, x, y));
                }
            }

            mat.rows = rows;
            mat.transpose();
            mats.add(mat);
        }
    }

    public static void part1(String data) {
        Advent4 main = new Advent4(data);

        outer: for (int guess : main.guesses) {
            for (MatPos pos : main.locs.getOrDefault(guess, List.of())) {
                pos.element().found = true;
            }

            for (Mat mat : main.mats) {
                if (mat.done()) {
                    System.out.println(mat.score(guess));
                    break outer;
                }
            }
        }
    }

    public static void part2(String data) {
        Advent4 main = new Advent4(data);

        outer: for (int guess : main.guesses) {
            for (MatPos pos : main.locs.getOrDefault(guess, List.of())) {
                pos.element().found = true;
            }

            Iterator<Mat> iter = main.mats.iterator();

            while (iter.hasNext()) {
                Mat mat = iter.next();

                if (mat.done()) {
                    if (main.mats.size() == 1) {
                        System.out.println(mat.score(guess));
                        break outer;
                    } else {
                        iter.remove();
                    }
                }
            }
        }
    }

    private static final class Mat {
        MatElement[][] rows;
        MatElement[][] cols;

        void transpose() {
            cols = new MatElement[5][];

            for (int x = 0; x < 5; x++) {
                MatElement[] col = cols[x] = new MatElement[5];

                for (int y = 0; y < 5; y++) {
                    col[y] = rows[y][x];
                }
            }
        }

        boolean done() {
            for (MatElement[] row : rows) {
                boolean done = true;

                for (MatElement element : row) {
                    done &= element.found;
                }

                if (done) return true;
            }

            for (MatElement[] col : cols) {
                boolean done = true;

                for (MatElement element : col) {
                    done &= element.found;
                }

                if (done) return true;
            }

            return false;
        }

        int score(int guess) {
            int sum = 0;

            for (int x = 0; x < 5; x++) {
                for (int y = 0; y < 5; y++) {
                    MatElement el = rows[y][x];
                    if (!el.found) sum += el.value;
                }
            }

            return sum * guess;
        }
    }

    private static class MatElement {
        final int value;
        boolean found = false;

        MatElement(int value) {
            this.value = value;
        }
    }

    private static record MatPos(Mat mat, int x, int y) {
        MatElement element() {
            return mat.rows[y][x];
        }
    }
}
