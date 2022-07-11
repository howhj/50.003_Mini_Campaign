import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.io.FileWriter;
import java.util.List;
import java.util.ArrayList;

public class CompareCSV {
    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("Please input 3 filepaths. The first 2 should be the CSV files to read from. The last is the output file to be written to.");
            return;
        }

        // TODO: Check if files exist
        // TODO: Allow user to select specific columns
        // TODO: Check bounds
        // TODO: Check if columns match (how?)

        try {
            // Read from input file
            List<String[]> in1 = ReadLines(args[0]);
            List<String[]> in2 = ReadLines(args[1]);

            // Compare lines of data
            List<String> result = new ArrayList<>();
            FindExceptions(in1, in2, result);
            FindExceptions(in2, in1, result);

            // Write exceptions to output file
            FileWriter fw = new FileWriter(args[2]);
            for (String s : result) {
                fw.write(s);
            }
            fw.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<String[]> ReadLines(String path) throws IOException {
        List<String> in = Files.readAllLines(Paths.get(path));
        List<String[]> out = new ArrayList<>();

        // Find number of commas
        int total_commas = 0;
        for (char c : in.get(0).toCharArray()) {
            if (c == ',') {
                total_commas++;
            }
        }

        // Split each line into 2 parts: the unique combination (x) and the data to compare (y)
        // TODO: make this work even if y isn't the rightmost column
        for (String s : in) {
            int chars = 0;
            int commas = 0;
            String x;
            String y;

            for (char c : s.toCharArray()) {
                chars++;
                if (c == ',') {
                    commas++;
                    if (commas == total_commas) {
                        x = s.substring(0, chars);
                        y = s.substring(chars);
                        String[] temp = {x, y};
                        out.add(temp);
                        break;
                    }
                }
            }
        }
        return out;
    }

    private static void FindExceptions(List<String[]> in1, List<String[]> in2, List<String> out) {
        for (String[] arr1 : in1) {
            boolean found = false;

            for (String[] arr2 : in2) {
                if (arr1[0].equals(arr2[0])) {
                    found = true;

                    // Mismatch
                    if (!arr1[1].equals(arr2[1])) {
                        out.add(arr1[0] + arr1[1] + "\n");
                        break;
                    }
                }
            }

            // Entry not found in other file
            if (!found) {
                out.add(arr1[0] + arr1[1] + "\n");
            }
        }
    }
}