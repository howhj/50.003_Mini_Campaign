import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException, IOException {
        FuzzTester.FuzzWithEverythingRandom(1001); // Generates
        String file1 = "sample_file_1.csv";
        String file3 = "sample_file_3.csv";
        //String file1 = "modified_sample_file_1.csv";
        //String file3 = "modified_sample_file_3.csv";
        String FuzzInput1 = "FuzzInput1.csv";
        String FuzzInput2 = "FuzzInput2.csv";

        CSVreader.read(file1, file3); // Todo: change to the Fuzz inputs if needed
        System.out.println("Code Ran Succesfully");

    }
}
