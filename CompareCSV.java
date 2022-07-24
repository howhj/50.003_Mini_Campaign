import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class CompareCSV {
    public static void main(String[] args) {
        List<String> raw1;
        List<String> raw2;
        List<String[]> in1;
        List<String[]> in2;
        FileWriter fw;
        int index;
        int bounds;
        String letter;
        boolean check_yn = false;
        String help_suggestion = "Use 'java CompareCSV -h' for help.";

        // TODO: print more strings to explain each argument
        // Help messages
        if (args[0].toLowerCase().equals("-h")) {
            System.out.println("Usage: java CompareCSV [input file 1] [input file 2] [output file] <index> <y/[n]>");
            return;
        }

        // Check if number of arguments is correct
        if (args.length < 3) {
            System.out.println("Insufficient arguments.");
            System.out.println(help_suggestion);
            return;
        }
        else if (args.length > 5) {
            System.out.println("Too many arguments.");
            System.out.println(help_suggestion);
            return;
        }

        // TODO: Say which filepath is wrong in error message
        // Read from input file
        try {
            raw1 = ReadLines(args[0]);
            raw2 = ReadLines(args[1]);
            if (new File(args[2]).exists()) {
                System.out.println("Output file already exists.");
                System.out.println(help_suggestion);
                return;
            }
        }
        catch (IOException e) {
            System.out.println("Invalid input filepath(s).");
            System.out.println(help_suggestion);
            return;
        }

        bounds = CheckBounds(raw1, raw2);
        if (bounds == -3) {
            System.out.println("Input files have different number of columns.");
            System.out.println(help_suggestion);
            return;
        }
        else if (bounds < 0) {
            System.out.println(String.format("Incomplete row(s) in file %d", -bounds));
            System.out.println(help_suggestion);
            return;
        }

        // Select specific columns (default: rightmost)
        index = bounds;
        if (args.length >= 4) {
            try {
                index = Integer.parseInt(args[3]);
                if (args.length == 5) { check_yn = true; }
                if (index > bounds || index < 0) {
                    System.out.println("Index is out of bounds.");
                    System.out.println(help_suggestion);
                    return;
                }
            }
            catch (NumberFormatException e) {
                if (args.length == 4) { check_yn = true; }
                else {
                    System.out.println("Index provided is not an integer.");
                    System.out.println(help_suggestion);
                    return;
                }
            }
        }

        // Check if column headers match (default: do not check)
        if (check_yn && args.length >= 4) {
            letter = args[args.length-1];

            if (letter.toLowerCase().equals("y")) {
                if (!raw1.get(0).equals(raw2.get(0))) {
                    System.out.println("Headers do not match.");
                    System.out.println(help_suggestion);
                    return;
                }
            }
            else if (!letter.toLowerCase().equals("n")) {
                System.out.println(String.format("Unknown argument %s.", letter));
                System.out.println(help_suggestion);
                return;
            }
        }

        // Compare lines of data
        in1 = SplitLines(raw1, index);
        in2 = SplitLines(raw2, index);
        List<String> result = new ArrayList<>();
        FindExceptions(in1, in2, result);
        FindExceptions(in2, in1, result);

        // Write exceptions to output file
        try {
            fw = new FileWriter(args[2]);
            for (String s : result) { fw.write(s); }
            fw.close();
        }
        catch (IOException e) { System.out.println("Failed to write to output file."); }
    }

    // TODO: use switch case
    private static int CheckBounds(List<String> in1, List<String> in2) {
        int bounds1 = 0;
        int bounds2 = 0;
        int temp;
        boolean quoted = false;

        for (char c : in1.get(0).toCharArray()) {
            if (c == ',' && !quoted) { bounds1++; }
            else if (c == '"' && quoted) { quoted = false; }
            else if (c == '"' && !quoted) { quoted = true; }
            else if (c == '\n') { quoted = false; }
        }

        quoted = false;
        for (char c : in2.get(0).toCharArray()) {
            if (c == ',' && !quoted) { bounds2++; }
            else if (c == '"' && quoted) { quoted = false; }
            else if (c == '"' && !quoted) { quoted = true; }
            else if (c == '\n') { quoted = false; }
        }

        if (bounds1 != bounds2) { return -3; }

        for (String s : in1) {
            temp = 0;
            quoted = false;
            for (char c : s.toCharArray()) {
                if (c == ',' && !quoted) { temp++; }
                else if (c == '"' && quoted) { quoted = false; }
                else if (c == '"' && !quoted) { quoted = true; }
                else if (c == '\n') { quoted = false; }
            }
            if (temp != bounds1) { return -1; }
        }

        for (String s : in2) {
            temp = 0;
            quoted = false;
            for (char c : s.toCharArray()) {
                if (c == ',' && !quoted) { temp++; }
                else if (c == '"' && quoted) { quoted = false; }
                else if (c == '"' && !quoted) { quoted = true; }
                else if (c == '\n') { quoted = false; }
            }
            if (temp != bounds1) { return -2; }
        }

        return bounds1;
    }

    private static List<String> ReadLines(String path) throws IOException {
        return Files.readAllLines(Paths.get(path));
    }

    private static List<String[]> SplitLines(List<String> in, int index) {
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
                else if (c == '"' && !quoted) { quoted = true; }
                else if (c == '\n') { quoted = false; }
            }

            if (y.equals("")) {
                y = s.substring(lower);
            }

            String[] temp = {x, y, z};
            out.add(temp);
        }
        return out;
    }

    private static void FindExceptions(List<String[]> in1, List<String[]> in2, List<String> out) {
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