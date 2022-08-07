package CompareCSV;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

// TODO: use switch case
public class Helper {
    // Check number of columns by counting the number of unquoted commas
    public static int CheckBounds(List<String> in1, List<String> in2) {
        int bounds1 = CountCommas(in1.get(0));
        int bounds2 = CountCommas(in2.get(0));

        // Different number of columns
        if (bounds1 != bounds2) { return -3; }

        // Input file 1 does not have consistent number of columns in all rows
        int temp;
        for (String s : in1) {
            temp = CountCommas(s);
            if (temp != bounds1) { return -1; }
        }

        // Input file 2 does not have consistent number of columns in all rows
        for (String s : in2) {
            temp = CountCommas(s);
            if (temp != bounds1) { return -2; }
        }

        return bounds1;
    }

    // Count the number of unquoted commas in 1 row of data
    // e.g. you have a CSV file that has columns "days","months"
    // "mon,tue,wed...sun","jan,feb,mar...dec" = 1 comma
    public static int CountCommas(String in) {
        int counter = 0;
        boolean quoted = false;

        for (char c : in.toCharArray()) {
            if (c == ',' && !quoted) { counter++; }
            else if (c == '"' && quoted) { quoted = false; }
            else if (c == '"') { quoted = true; }
        }

        return counter;
    }

    public static List<String> ReadLines(String path) throws IOException {
        return Files.readAllLines(Paths.get(path));
    }

    public static List<String[]> SplitLines(List<String> in, int index) {
        List<String[]> out = new ArrayList<>();

        // Split each line into 3 parts: the unique combination (x,z) and the data to compare (y)
        for (String s : in) {
            int lower = 0;
            int upper = 0;
            int commas = 0;
            String x = "";
            String y = "";
            String z = "";
            boolean quoted = false;

            for (char c : s.toCharArray()) {
                upper++;
                if (c == ',' && !quoted) {
                    commas++;
                    if (commas == index) {
                        x = s.substring(0, upper);
                        lower = upper;
                    }
                    else if (commas == index + 1) {
                        y = s.substring(lower, upper);
                        z = s.substring(upper);
                        break;
                    }
                }
                else if (c == '"' && quoted) { quoted = false; }
                else if (c == '"') { quoted = true; }
            }

            if (y.equals("")) {
                y = s.substring(lower);
            }

            String[] temp = {x, y, z};
            out.add(temp);
        }
        return out;
    }

    public static void FindExceptions(List<String[]> in1, List<String[]> in2, List<String> out) {
        for (String[] arr1 : in1) {
            boolean found = false;

            for (String[] arr2 : in2) {
                if (arr1[0].equals(arr2[0]) && arr1[2].equals(arr2[2])) {
                    found = true;

                    // Mismatch
                    if (!arr1[1].equals(arr2[1])) {
                        out.add(arr1[0] + arr1[1] + arr1[2] + "\n");
                        break;
                    }
                }
            }

            // Entry not found in other file
            if (!found) { out.add(arr1[0] + arr1[1] + arr1[2] + "\n"); }
        }
    }
}
