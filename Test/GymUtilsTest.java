import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Katri Vidén
 * Date: 2020-10-09
 * Time: 16:46
 * Project: OOPInlämning2Test
 * Copyright: MIT
 */
public class GymUtilsTest {

    String testGymName = "TestGym";

    @Test
    // Testar att det går att läsa från filen
    public final void readCustomersFromFileTest() {
        ArrayList<Customer> customerList = GymUtils.readCustomersFromFile();
        assertEquals("Alhambra Aromes", customerList.get(0).getName());
        assertEquals("Bear Belle", customerList.get(1).getName());
        assertEquals("Chamade Coriola", customerList.get(2).getName());
        assertNotEquals("Hilmer Heur", customerList.get(3).getName());
        assertEquals("7608021234", customerList.get(3).getSocialSecurityNr());
    }

    @Test
    // Testar att det går att skriva till en fil
    public final void writeCustomerDataToFileTest() throws IOException {
        String socialSecurityNr = "7911041833";
        String name = "Test customer";
        Customer customer1 = new Customer(socialSecurityNr, name);

        assertTrue(GymUtils.writeCustomerDataToFile(customer1));
        assertPersonalInformation(customer1, 1);

        deleteFile(customer1);
    }

    @Test
    public final void testMultipleVisits() throws IOException {
        String socialSecurityNr = "8101041833";
        String name = "Test customer2";
        int numberOfVisits = 10;
        Customer customer2 = new Customer(socialSecurityNr, name);

        for (int i = 0; i < numberOfVisits; i++) {
            assertTrue(GymUtils.writeCustomerDataToFile(customer2));
        }
        assertPersonalInformation(customer2, numberOfVisits);

        deleteFile(customer2);
    }

    // Hjälpmetoder

    private void assertPersonalInformation(Customer customer, int numberOfVisits) throws IOException {
        ArrayList<String> visits = getVisitingCustomerHistory(customer);
        String testDate = LocalDate.now().toString();
        for (String visit : visits) {
            String[] split = visit.split(",");

            assertEquals(customer.getSocialSecurityNr(), split[0]);
            assertEquals(customer.getName(), split[1].trim());
            assertEquals(testDate, split[2].trim());

        }
        assertEquals(numberOfVisits, visits.size());
    }

    // Kundens besökshistorik
    private ArrayList<String> getVisitingCustomerHistory(Customer customer) throws IOException {
        ArrayList<String> customerHistory = new ArrayList<>();
        String filePath = "gymdata/history/" + customer.getSocialSecurityNr() + ".txt";
        BufferedReader in = new BufferedReader(new FileReader(filePath));
        Scanner sc = new Scanner(in);
        while (sc.hasNextLine()) {
            String historyData = sc.nextLine();
            if (!historyData.isEmpty()) {
                customerHistory.add(historyData);
            }
        }
        sc.close();
        in.close();
        return customerHistory;
    }

    private void deleteFile(Customer customer) {
        String filePath = "gymdata/history/" + customer.getSocialSecurityNr() + ".txt";
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
    }
}
