import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class FuzzTester {
    // This function generates a random Integer
    public static Integer random_int(Integer upperbound) {
        Random rand = new Random();
        int int_random = rand.nextInt(upperbound);
        return int_random;
    }

    // This generates a random string
    public static String random_string(int n) {
        // chose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {
            int index = (int) (AlphaNumericString.length()
                    * Math.random());
            sb.append(AlphaNumericString
                    .charAt(index));
        }
        return sb.toString();
    }

    // Final function to make two output csv files that contain random fuzzed values
    public static void FuzzWithEverythingRandom(Integer num_records) {
        ArrayList<String> CustomerID = new ArrayList<>();
        ArrayList<String> AccountNo = new ArrayList<>();
        ArrayList<String> Currency = new ArrayList<>();
        ArrayList<String> Type = new ArrayList<>();
        ArrayList<Integer> Balance = new ArrayList<>();

        for (Integer i = 0; i < num_records; i++) {
            CustomerID.add(random_string(4));
            AccountNo.add(random_string(9));
            Currency.add(random_string(4));
            Type.add(random_string(4));
            Balance.add(random_int(10000000));
        }

        FileWriter writer1;
        try {
            Integer size2 = 0;
            writer1 = new FileWriter("FuzzInput1.csv");
            writer1.append("Customer ID#,Account No.,Currency,Type,Balance");
            writer1.append('\n');
            // We open a file writer that writes to our output file
            while (size2 < num_records) {
                writer1.append(CustomerID.get(size2).toString() + "," + AccountNo.get(size2).toString() + "," +
                        Currency.get(size2).toString() + "," + Type.get(size2).toString() + ","
                        + Balance.get(size2).toString());
                writer1.append('\n');
                size2++;

            }
            writer1.flush();
            writer1.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        CustomerID.removeAll(CustomerID);
        AccountNo.removeAll(AccountNo);
        Currency.removeAll(Currency);
        Type.removeAll(Type);
        Balance.removeAll(Balance);

        for (Integer i = 0; i < num_records; i++) {
            CustomerID.add(random_string(4));
            AccountNo.add(random_string(9));
            Currency.add(random_string(4));
            Type.add(random_string(4));
            Balance.add(random_int(10000000));
        }

        FileWriter writer2;
        try {
            Integer size2 = 0;
            writer2 = new FileWriter("FuzzInput2.csv");
            writer2.append("Customer ID#,Account No.,Currency,Type,Balance");
            writer2.append('\n');
            // We open a file writer that writes to our output file
            while (size2 < num_records) {
                writer2.append(CustomerID.get(size2).toString() + "," + AccountNo.get(size2).toString() + "," +
                        Currency.get(size2).toString() + "," + Type.get(size2).toString() + ","
                        + Balance.get(size2).toString());
                writer2.append('\n');
                size2++;

            }
            writer2.flush();
            writer2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
