package CompareCSV;

import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;
import java.io.FileWriter;
import java.io.IOException;

public class NonParamUnitTests {
    @Test
    public void readLinesTest() {
        try {
            String path = "resources/test.csv";
            String[] testcsv = new String[] {"1\n", "2\n", "3\n"};
            List<String> expected = Arrays.asList("1", "2", "3");

            FileWriter fw = new FileWriter(path);
            for (String s : testcsv) { fw.write(s); }
            fw.close();

            List<String> actual = Helper.ReadLines(path);
            assertTrue(validateList(expected, actual));
        }
        catch(IOException e) { fail("Unexpected error."); }
    }

    @Test
    public void findExceptionsTest() {
        List<String[]> in1 = Arrays.asList(new String[] {"1,2,3,4,", "5", ""},
                new String[] {"6,7,8,9,", "10", ""},
                new String[] {"11,12,13,14,", "15", ""});

        List<String[]> in2 = Arrays.asList(new String[] {"1,2,3,4,", "5", ""},
                new String[] {"6,7,8,9,", "0", ""},
                new String[] {"16,17,18,19,", "20", ""});

        List<String> expected = Arrays.asList("6,7,8,9,10\n", "11,12,13,14,15\n", "6,7,8,9,0\n", "16,17,18,19,20\n");

        List<String> actual = new ArrayList<>();
        Helper.FindExceptions(in1, in2, actual);
        Helper.FindExceptions(in2, in1, actual);
        assertTrue(validateList(expected, actual));
    }

    private boolean validateList(List<String> expected, List<String> actual) {
        for (int i=0; i<expected.size(); i++) {
            if (!expected.get(i).equals(actual.get(i))) { return false; }
        }
        return true;
    }
}
