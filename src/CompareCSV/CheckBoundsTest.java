package CompareCSV;

import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import static org.junit.Assert.*;
import java.util.*;

@RunWith(Parameterized.class)

public class CheckBoundsTest {
    List<String> in1, in2;
    int expected;

    public CheckBoundsTest(List<String> in1, List<String> in2, int expected) {
        this.in1 = in1;
        this.in2 = in2;
        this.expected = expected;
    }

    @Parameters
    public static Collection<Object[]> parameters() {
        return Arrays.asList (new Object[][] {
                {Arrays.asList("1,2,3,4,5", "6,7,8,9,10"),
                        Arrays.asList("11,12,13,14,15","16,17,18,19,20"),
                        4},

                {Arrays.asList("1,2,3,4,5", "6,7,8,9,10"),
                        Arrays.asList("11,12,13,14","15,16,17,18,19"),
                        -3},

                {Arrays.asList("1,2,3,4", "5,6,7,8", "9,10,11,12"),
                        Arrays.asList("13,14,15,\"16,17", "18,19,20,21\"", "22,23,24,\"25,26\""),
                        3},

                {Arrays.asList("1,2,3,4,5", "6,7,8,9"),
                        Arrays.asList("10,11,12,13,14", "15,16,17,18"),
                        -1},

                {Arrays.asList("1,2,3,4,5", "6,7,8,9,10"),
                        Arrays.asList("11,12,13,14,15", "16,17,18,19"),
                        -2}
        });
    }

    @Test
    public void test() {
        int actual = Helper.CheckBounds(this.in1, this.in2);
        assertEquals(this.expected, actual);
    }
}
