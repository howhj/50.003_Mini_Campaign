package compareCSV;

import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import static org.junit.Assert.*;
import java.util.*;

@RunWith(Parameterized.class)

public class checkBoundsTest {
    List<String> in1, in2;
    int expected;

    public checkBoundsTest(List<String> in1, List<String> in2, int expected) {
        this.in1 = in1;
        this.in2 = in2;
        this.expected = expected;
    }
    
    @Parameters
    public static Collection<Object[]> parameters() {
        return Arrays.asList (new Object[][] {
                {Arrays.asList(new String[] {"1,2,3,4,5", "6,7,8,9,10"}),
                Arrays.asList(new String[] {"11,12,13,14,15","16,17,18,19,20"}),
                4},

                {Arrays.asList(new String[] {"1,2,3,4,5", "6,7,8,9,10"}),
                Arrays.asList(new String[] {"11,12,13,14","15,16,17,18,19"}),
                -3},

                {Arrays.asList(new String[] {"1,2,3,4", "5,6,7,8", "9,10,11,12"}),
                Arrays.asList(new String[] {"13,14,15,\"16,17", "18,19,20,21\"", "22,23,24,\"25,26\""}),
                3},

                {Arrays.asList(new String[] {"1,2,3,4,5", "6,7,8,9"}),
                Arrays.asList(new String[] {"10,11,12,13,14", "15,16,17,18"}),
                -1},

                {Arrays.asList(new String[] {"1,2,3,4,5", "6,7,8,9,10"}),
                Arrays.asList(new String[] {"11,12,13,14,15", "16,17,18,19"}),
                -2}
            }); 
    }

    @Test
    public void test() {
        int actual = helper.CheckBounds(this.in1, this.in2);
        assertEquals(this.expected, actual);
    }
}