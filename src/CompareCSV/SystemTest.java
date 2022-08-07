package CompareCSV;

import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import static org.junit.Assert.*;
import java.util.*;
import java.io.File;

@RunWith(Parameterized.class)

public class SystemTest {
    String[] args;
    int testOutput;
    String output;
    List<String> expected = Arrays.asList("\"ID99\",\"BOS8059799\",\"SGD\",\"CURRENT\",\"208045\"",
            "\"ID198\",\"BOS30360198\",\"USD\",\"CURRENT\",\"679878\"",
            "\"ID298\",\"BOS50591298\",\"CHF\",\"SAVINGS\",\"988459\"",
            "\"ID99\",\"BOS8059799\",\"SGD\",\"CURRENT\",\"208043\"",
            "\"ID198\",\"BOS30360198\",\"USD\",\"CURRENT\",\"6798788\"",
            "\"ID298\",\"BOS50591298\",\"USD\",\"SAVINGS\",\"988459\"");

    public SystemTest(String[] args, int testOutput, String output) {
        this.args = args;
        this.testOutput = testOutput;
        this.output = output;
    }

    @Parameters
    public static Collection<Object[]> parameters() {
        String noexist_path = "resources/should_not_exist.csv";
        String file1_path = "resources/sample_file_1.csv";
        String file3_path = "resources/sample_file_3.csv";
        String typo_path = "resources/sample_file.csv";
        String test1_path = "resources/test1.csv";
        String test2_path = "resources/test2.csv";
        String test3_path = "resources/test3.csv";
        String test4_path = "resources/test4.csv";
        String test5_path = "resources/test5.csv";

        return Arrays.asList (new Object[][] {
                // print help
                {new String[] {"-h"}, 0, ""},
                // print help
                {new String[] {"-h", "kajhskja", noexist_path}, 1, ""},
                // insufficient args
                {new String[] {file1_path, file3_path}, 0, ""},
                // excessive args
                {new String[] {file1_path, file3_path, noexist_path, "2", "y", "aadsjda"}, 1, ""},
                // invalid input file 1
                {new String[] {typo_path, file3_path, noexist_path}, 1, ""},
                // invalid input file 2
                {new String[] {file1_path, typo_path, noexist_path}, 1, ""},
                // output file exists
                {new String[] {file1_path, file3_path, file1_path}, 0, ""},
                // success
                {new String[] {file1_path, file3_path, test1_path}, 2, test1_path},
                // success
                {new String[] {file1_path, file3_path, test2_path, "2"}, 2, test2_path},
                // success
                {new String[] {file1_path, file3_path, test3_path, "y"}, 2, test3_path},
                // unknown arg
                {new String[] {file1_path, file3_path, noexist_path, "t"}, 1, ""},
                // success
                {new String[] {file1_path, file3_path, test4_path, "2", "y"}, 2, test4_path},
                // unknown arg
                {new String[] {file1_path, file3_path, noexist_path, "t", "y"}, 1, ""},
                // unknown arg
                {new String[] {file1_path, file3_path, noexist_path, "2", "t"}, 1, ""},
                // success
                {new String[] {file1_path, file3_path, test5_path, "2", "n"}, 2, test5_path}
        });
    }

    @Test
    public void test() {
        /*
        Status codes
        0: no output filepath supplied -> manually check console output
        1: invalid inputs -> output file should not be created
        2: valid inputs -> check if output file content is correct
        */
        try {
            new Bootstrap().start(this.args);
            if (this.testOutput == 2) {
                List<String> actual = Helper.ReadLines(this.output);
                assertTrue(validateList(expected, actual));
            }
            else if (this.testOutput == 1) {
                assertFalse(new File("resources/should_not_exist.csv").exists());
            }
        }
        catch (Exception e) { fail("Unexpected error."); }
    }

    private boolean validateList(List<String> expected, List<String> actual) {
        for (int i=0; i<expected.size(); i++) {
            if (!expected.get(i).equals(actual.get(i))) { return false; }
        }
        return true;
    }
}
