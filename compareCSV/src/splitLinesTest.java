package compareCSV;

import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import static org.junit.Assert.*;
import java.util.*;

@RunWith(Parameterized.class)

public class splitLinesTest {
    List<String> in;
    int index;
    List<String[]> expected;

    public splitLinesTest(List<String> in, int index, List<String[]> expected) {
        this.in = in;
        this.index = index;
        this.expected = expected;
    }
    
    @Parameters
    public static Collection<Object[]> parameters() {
        return Arrays.asList (new Object[][] {
                {Arrays.asList(new String[] {"1,2,3,4,5", "6,7,8,9,10"}),
                3,
                Arrays.asList(new String[][] {new String[] {"1,2,3", ",4", ",5"}, new String[] {"6,7,8", ",9", ",10"}})},

                {Arrays.asList(new String[] {"1,2,3,4,5", "6,7,8,9,10"}),
                0,
                Arrays.asList(new String[][] {new String[] {"", "1", ",2,3,4,5"}, new String[] {"", "6", ",7,8,9,10"}})},

                {Arrays.asList(new String[] {"1,2,3,4,5", "6,7,8,9,10"}),
                4,
                Arrays.asList(new String[][] {new String[] {"1,2,3,4", ",5", ""}, new String[] {"6,7,8,9", ",10", ""}})}
                }
            ); 
    }

    @Test
    public void test() {
        List<String[]> actual = helper.SplitLines(this.in, this.index);
        assertTrue(validateList(this.expected, actual));
    }

    private boolean validateList(List<String[]> expected, List<String[]> actual) {
        for (int i=0; i<expected.size(); i++) {
            String[] e = expected.get(i);
            String[] a = actual.get(i);
            for (int j=0; j<3; j++) {
                if (!e[j].equals(a[j])) { return false; }
            }
        }
        return true;
    }
}