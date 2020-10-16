import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Katri Vidén
 * Date: 2020-10-09
 * Time: 15:39
 * Project: OOPInlämning2Test
 * Copyright: MIT
 */
public class GymUtils {

    public static ArrayList<Customer> readCustomersFromFile() {
        ArrayList<Customer> importedCustomers = new ArrayList<>();
        final String filePath = "gymdata/customers.txt";
        // Try-with-resources
        try (BufferedReader in = new BufferedReader(new FileReader(filePath))) {
            try (Scanner sc = new Scanner(in)) {
                while (sc.hasNextLine()) {
                    String personalData = sc.nextLine();
                    if (!personalData.isEmpty()) {
                        // Splittar strängen för att plocka ut personnummer och namn för att kunna skapa
                        // en kund av dessa
                        String[] splitData = personalData.split(",");
                        String socialNr = splitData[0];
                        String name = splitData[1].trim();

                        // Läser datumet från nästa rad i samma loop för att inte tappa informationen
                        if (sc.hasNextLine()) {
                            String paymentData = sc.nextLine();
                            LocalDate parsedPaymentDate = LocalDate.parse(paymentData);

                            // Skapar och lägger till kunden till listan utifrån hämtad information
                            Customer customer = new Customer(socialNr, name);
                            customer.setPaymentDate(parsedPaymentDate);
                            importedCustomers.add(customer);
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Kunde inte hitta filen att läsa ifrån");
            e.printStackTrace();
            System.exit(1);
        } catch (IOException e) {
            System.out.println("Fel vid läsning av fil");
            e.printStackTrace();
            System.exit(1);
        }
        return importedCustomers;
    }

    public static boolean writeCustomerDataToFile(Customer customer) {
        File file = new File("gymdata/history/" + customer.getSocialSecurityNr() + ".txt");
        try {
            if (!file.exists()) {
                file.createNewFile();
            }

            StringBuilder customerRegistration = new StringBuilder();
            customerRegistration.append(customer.getSocialSecurityNr() + ", ");
            customerRegistration.append(customer.getName() + ", ");
            customerRegistration.append(LocalDate.now() + "\n");

            // Try-with-resources
            try (FileWriter writer = new FileWriter(file, true)) {
                writer.write(customerRegistration.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Kunde inte registrera kundbesök");
            return false;
        }
        return true;
    }

    public static boolean registerNewCustomerToFile(Customer customer) {
        StringBuilder newCustomerRegistration = new StringBuilder();
        newCustomerRegistration.append(customer.getSocialSecurityNr() + ", ");
        newCustomerRegistration.append(customer.getName() + "\n");
        newCustomerRegistration.append(LocalDate.now() + "\n");

        try (FileWriter writer = new FileWriter("gymdata/customers.txt", true)) {
            writer.write(newCustomerRegistration.toString());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Kunde inte registrera kunden");
            return false;
        }
        return writeCustomerDataToFile(customer);
    }
}
