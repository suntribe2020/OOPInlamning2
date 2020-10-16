import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Created by Katri Vidén
 * Date: 2020-10-09
 * Time: 14:58
 * Project: OOPInlämning2Test
 * Copyright: MIT
 */
public class Gym {

    private ArrayList<Customer> allCustomers = new ArrayList<>();
    private boolean open = false;
    private String gymName;

    public Gym(String gymName) {
        this.gymName = gymName;
    }

    public void open() {
        allCustomers = GymUtils.readCustomersFromFile();
        open = true;
    }

    public void close() {
        open = false;
    }

    public boolean isOpen() {
        return open;
    }

    public Customer findCustomer(String input) {
        //loopar igenom kundlistan för att kolla om input matchar med existerande kund
        for (Customer customer : allCustomers) {
            if (input.equals(customer.getSocialSecurityNr()) ||
                    input.toLowerCase().equals(customer.getName().toLowerCase())) {
                return customer;
            }
        }
        return null;
    }

    public boolean registerNewCustomer(String input) {
        // Kontrollerar att input har två strängar, personnummer och namn
        String[] split = input.split(",");
        if (split.length != 2) {
            return false;
        }

        String socialNr = split[0];
        String name = split[1].trim();

        // Kontrollerar att första inmatningen är en siffra eftersom personnumret ska matas in först
        for (int i = 0; i < socialNr.length(); i++) {
            char firstCharacter = socialNr.charAt(i);
            if (!Character.isDigit(firstCharacter)) {
                return false;
            }
        }
        // En ny kund skapas om input är godkänd
        // Kunden läggs sedan till på listan
        Customer customer = new Customer(socialNr, name);
        customer.setPaymentDate(LocalDate.now());
        GymUtils.registerNewCustomerToFile(customer);

        return true;
    }

    public String getGymName() {
        return gymName;
    }

    public ArrayList<Customer> getAllCustomers() {
        return allCustomers;
    }
}



