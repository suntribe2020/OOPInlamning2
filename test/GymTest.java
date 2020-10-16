import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Katri Vidén
 * Date: 2020-10-09
 * Time: 14:50
 * Project: OOPInlämning2Test
 * Copyright: MIT
 */
public class GymTest {

    private static Gym TEST_GYM;

    // Körs en gång innan alla andra tester
    @BeforeAll
    static void beforeAllTests() {
        // Skapar och öppnar ett testgym som sedan används gemensamt av de olika testerna
        TEST_GYM = new Gym("TestGym");
        TEST_GYM.open();
    }

    @Test
    public final void openGymTest() {
        Gym gym = new Gym("TestGymEver");
        assertFalse(gym.isOpen());
        gym.open();
        assertTrue(gym.isOpen());
    }

    @Test
    public final void findCustomerBySecNumberTest() {
        Customer customer = TEST_GYM.findCustomer("7603021234");
        assertNotNull(customer);
        assertEquals("7603021234", customer.getSocialSecurityNr());
        assertEquals("Alhambra Aromes", customer.getName());
        assertFalse(customer.isActiveMembership());
    }

    @Test
    public final void findCustomerByNameTest() {
        Customer customer1 = TEST_GYM.findCustomer("Greger Ganache");
        assertNotNull(customer1);
        assertEquals("7512166544", customer1.getSocialSecurityNr());
        assertEquals("Greger Ganache", customer1.getName());
        assertTrue(customer1.isActiveMembership());
    }

    @Test
    public final void findNonExistingCustomerTest() {
        Customer customer2 = TEST_GYM.findCustomer("Hej jag finns inte");
        assertNull(customer2);
    }
}
