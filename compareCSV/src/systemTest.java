package compareCSV;

import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import static org.junit.Assert.*;
import java.util.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@RunWith(Parameterized.class)

public class systemTest {
    String[] args;
    int testOutput;
    String output;
    List<String> expected = Arrays.asList(new String[] {
        "\"ID99\",\"BOS8059799\",\"SGD\",\"CURRENT\",\"208045\"",
        "\"ID198\",\"BOS30360198\",\"USD\",\"CURRENT\",\"679878\"",
        "\"ID298\",\"BOS50591298\",\"CHF\",\"SAVINGS\",\"988459\"",
        "\"ID99\",\"BOS8059799\",\"SGD\",\"CURRENT\",\"208043\"",
        "\"ID198\",\"BOS30360198\",\"USD\",\"CURRENT\",\"6798788\"",
        "\"ID298\",\"BOS50591298\",\"USD\",\"SAVINGS\",\"988459\""
    });

    public systemTest(String[] args, int testOutput, String output) {
        this.args = args;
        this.testOutput = testOutput;
        this.output = output;
    }

    public static Collection<Object[]> parameters() {
        return Arrays.asList (new Object[][] {
                {new String[] {"-h"}, 0, ""},
                {new String[] {"-h", "kajhskja", "should_not_exist.csv"}, 1, ""},
                {new String[] {"sample_file_1.csv", "sample_file_3.csv"}, 0, ""},
                {new String[] {"sample_file_1.csv", "sample_file_3.csv", "should_not_exist.csv", "2", "y", "aadsjda"}, 1, ""},
                {new String[] {"sample_file.csv", "sample_file_3.csv", "should_not_exist.csv"}, 1, ""},
                {new String[] {"sample_file_1.csv", "sample_file.csv", "should_not_exist.csv"}, 1, ""},
                {new String[] {"sample_file_1.csv", "sample_file_3.csv", "sample_file_1.csv"}, 0, ""},
                {new String[] {"sample_file_1.csv", "sample_file_3.csv", "test1.csv"}, 2, "test1.csv"},
                {new String[] {"sample_file_1.csv", "sample_file_3.csv", "test2.csv", "2"}, 2, "test2.csv"},
                {new String[] {"sample_file_1.csv", "sample_file_3.csv", "test3.csv", "y"}, 2, "test3.csv"},
                {new String[] {"sample_file_1.csv", "sample_file_3.csv", "should_not_exist.csv", "t"}, 1, ""},
                {new String[] {"sample_file_1.csv", "sample_file_3.csv", "test4.csv", "2", "y"}, 2, "test4.csv"},
                {new String[] {"sample_file_1.csv", "sample_file_3.csv", "should_not_exist.csv", "t", "y"}, 1, ""},
                {new String[] {"sample_file_1.csv", "sample_file_3.csv", "should_not_exist.csv", "2", "t"}, 1, ""},
                {new String[] {"sample_file_1.csv", "sample_file_3.csv", "test5.csv", "2", "n"}, 2, "test5.csv"}
            }); 
    }

    @Test
    public void test() {
        try {
            entry.main(this.args);
            if (this.testOutput == 2) {
                List<String> actual = helper.ReadLines(this.output);
                assertTrue(validateList(expected, actual));
            }
            else if (this.testOutput == 1) { assertTrue(!(new File("should_not_exist.csv").exists())); }
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