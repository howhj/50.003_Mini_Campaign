import java.io.*;
import java.util.ArrayList;

/* file1 - file2 = file3*/
public class CSVreader {
    public static void read(String file1, String file2)
            throws FileNotFoundException, IOException, IllegalArgumentException {

        // We declare an arraylist called collumns that will be used later for iterating
        // through
        ArrayList<String> collumns = new ArrayList<>();
        collumns.add("Customer ID#");
        collumns.add("Account No.");
        collumns.add("Currency");
        collumns.add("Type");
        collumns.add("Balance");

        // We check if the files extension doesn't end with csv, we throw an exception,
        // We are not using a try catch block here because the test cases expect an
        // exception

        if ((!(file1.substring(file1.length() - 4, file1.length()).equals(".csv")))) {
            System.out.print("Given incorrect extension of ");
            System.out.println(file1.substring(file1.length() - 4, file1.length()));
            throw new IllegalArgumentException();
        }

        if ((!(file2.substring(file2.length() - 4, file2.length()).equals(".csv")))) {
            System.out.print("Given incorrect extension of ");
            System.out.println(file2.substring(file2.length() - 4, file2.length()));
            throw new IllegalArgumentException();
        }

        String output_file = "output.csv";// Define an output file to write the output contents here
        ArrayList<String> al1 = new ArrayList<>();// Define multiple arraylists to read the contents
        ArrayList<String> al2 = new ArrayList<>();
        ArrayList<String> words = new ArrayList<>();

        // The below 2 try catch blocks check for any incorrect formats in data values
        // If errors are found, the function returns with the corresponding error print
        // statement

        // This try catch block sees for errors in file1
        try (BufferedReader CSVFiletest1 = new BufferedReader(new FileReader(file1))) {
            String dataRow2 = CSVFiletest1.readLine();
            try {
                Integer N = 0;
                while (dataRow2 != null) {
                    if (N > 0) {
                        Boolean incorrect = false;
                        String[] words_split = dataRow2.split(",");// This finds the words
                        if (!words_split[0].contains("ID")) {
                            System.out.println("Incorrect ID format");
                            incorrect = true;
                        }

                        if (!words_split[1].contains("BOS")) {
                            System.out.println("Incorrect Account number format");
                            incorrect = true;
                        }

                        if (words_split[2].length() > 3) {
                            System.out.println("Incorrect currency format");
                            incorrect = true;
                        }
                        if ((!words_split[3].equals("CURRENT")) && !words_split[3].equals("SAVINGS")) {
                            System.out.println("Incorrect type format");
                            incorrect = true;
                        }
                        if (Integer.valueOf(words_split[4]) < 0) {
                            System.out.println("Incorrect contain balance format");
                            incorrect = true;
                        }
                        if (incorrect) {
                            return;
                        }
                    }
                    dataRow2 = CSVFiletest1.readLine(); // Read next line of data.
                    N++;
                }
            } catch (Exception e) {
                System.out.println(e);
            }
            CSVFiletest1.close();
        }

        // This try catch block sees for erros in file2
        try (BufferedReader CSVFiletest2 = new BufferedReader(new FileReader(file2))) {
            String dataRow2 = CSVFiletest2.readLine();
            try {
                Integer N = 0;
                while (dataRow2 != null) {
                    if (N > 0) {
                        Boolean incorrect2 = false;
                        String[] words_split = dataRow2.split(",");// This finds the words
                        if (!words_split[0].contains("ID")) {
                            System.out.println("Incorrect ID format");
                            incorrect2 = true;
                        }

                        if (!words_split[1].contains("BOS")) {
                            System.out.println("Incorrect Account number format");
                            incorrect2 = true;
                        }

                        if (words_split[2].length() > 3) {
                            System.out.println("Incorrect currency format");
                            incorrect2 = true;
                        }
                        if ((!words_split[3].equals("CURRENT")) && !words_split[3].equals("SAVINGS")) {
                            System.out.println("Incorrect type format");
                            incorrect2 = true;
                        }
                        if (Integer.valueOf(words_split[4]) < 0) {
                            System.out.println("Incorrect contain balance format");
                            incorrect2 = true;
                        }
                        if (incorrect2) {
                            return;
                        }
                    }
                    dataRow2 = CSVFiletest2.readLine(); // Read next line of data.
                    N++;
                }
            } catch (Exception e) {
                System.out.println(e);
            }
            CSVFiletest2.close();
        }

        BufferedReader CSVFile1 = new BufferedReader(new FileReader(file1));

        String dataRow1 = CSVFile1.readLine();
        while (dataRow1 != null) {

            String[] words_split = dataRow1.split(",");
            for (String word2 : words_split) {
                words.add(word2);
            }

            String[] dataArray1 = dataRow1.split("\n");
            for (String item1 : dataArray1) {
                al1.add(item1);
            }

            dataRow1 = CSVFile1.readLine(); // Read next line of data.
        }

        CSVFile1.close();

        BufferedReader CSVFile2 = new BufferedReader(new FileReader(file2));
        String dataRow2 = CSVFile2.readLine();
        try {
            while (dataRow2 != null) {

                String[] dataArray2 = dataRow2.split("\n");
                for (String item2 : dataArray2) {
                    al2.add(item2);
                }
                dataRow2 = CSVFile2.readLine(); // Read next line of data.
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        CSVFile2.close();

        ArrayList<Integer> indexes = new ArrayList<>();
        for (String a : collumns) {
            int index = words.indexOf(a);
            System.out.println(a);
            indexes.add(index);
        }

        // The following lines are responsible for fetching values of the 2 files and
        // comparing them
        for (int k = 0; k < al1.size(); k++) {
            String b = al1.get(k);
            String[] temp_word = b.split(",");

            String tempString = "";
            for (int i : indexes) {
                tempString += temp_word[i];
                if (i < indexes.size() - 1) {
                    tempString += ",";
                }
            }
            al1.set(k, tempString);
        }

        for (int k = 0; k < al2.size(); k++) {

            String b = al2.get(k);
            String[] temp_word = b.split(",");

            String tempString = "";
            for (int i : indexes) {
                tempString += temp_word[i];
                if (i < indexes.size() - 1) {
                    tempString += ",";
                }
            }

            al2.set(k, tempString);
        }

        for (String bs : al2) {
            if (al1.contains(bs)) {

                al1.remove(bs);
            } else {
                al1.add(bs);

            }
        }

        int size = al1.size();
        FileWriter writer = new FileWriter(output_file);// We open a file writer that writes to our output file
        while (size != 0) {
            size--;
            writer.append("" + al1.get(size));
            writer.append('\n');
        }
        writer.flush();
        writer.close();
    }

    public static void read_output(String output) throws IllegalArgumentException {

        if ((!(output.substring(output.length() - 4, output.length()).equals(".csv")))) {
            System.out.print("Given incorrect extension of ");
            System.out.println(output.substring(output.length() - 4, output.length()));
            throw new IllegalArgumentException();
        }
    }

}