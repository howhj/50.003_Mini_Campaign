package compareCSV;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class entry {
    public static void main(String[] args) {
        List<String> raw1;
        List<String> raw2;
        FileWriter fw;
        boolean check_yn = false;
        String help_suggestion = "Use 'java CompareCSV -h' for help.";

        // TODO: print more strings to explain each argument
        // Help messages
        if (args.length > 0 && args[0].toLowerCase().equals("-h")) {
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
            raw1 = helper.ReadLines(args[0]);
            raw2 = helper.ReadLines(args[1]);
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

        int bounds = helper.CheckBounds(raw1, raw2);
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
        int index = bounds;
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
            String letter = args[args.length-1];

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
        List<String[]>in1 = helper.SplitLines(raw1, index);
        List<String[]>in2 = helper.SplitLines(raw2, index);
        List<String> result = new ArrayList<>();
        helper.FindExceptions(in1, in2, result);
        helper.FindExceptions(in2, in1, result);

        // Write exceptions to output file
        try {
            fw = new FileWriter(args[2]);
            for (String s : result) { fw.write(s); }
            fw.close();
        }
        catch (IOException e) { System.out.println("Failed to write to output file."); }
    }
}