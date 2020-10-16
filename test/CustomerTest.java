import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Katri Vidén
 * Date: 2020-10-09
 * Time: 14:50
 * Project: OOPInlämning2Test
 * Copyright: MIT
 */
public class CustomerTest {

    @Test
    public final void createCustomerTest() {
        String socialSecurityNr = "5003280192";
        String name = "Kurt Bert";
        Customer customer = new Customer(socialSecurityNr, name);

        assertEquals(socialSecurityNr, customer.getSocialSecurityNr());
        assertEquals(name, customer.getName());
    }

    @Test
    public final void noPaymentDateTest() {
        String socialSecurityNr = "5603081847";
        String name = "Helga Holm";
        Customer customer = new Customer(socialSecurityNr, name);
        assertFalse(customer.isActiveMembership());
    }

    @Test
    public final void activeCustomerTest() {
        String socialSecurityNr = "7803179287";
        String name = "Berit Malm";
        Customer customer = new Customer(socialSecurityNr, name);

        LocalDate paymentDate = LocalDate.now().minusMonths(6);
        customer.setPaymentDate(paymentDate);
        assertTrue(customer.isActiveMembership());
    }

    @Test
    public final void inactiveCustomerTest() {
        String socialSecurityNr = "9303179287";
        String name = "Sego Bert";
        Customer customer = new Customer(socialSecurityNr, name);

        LocalDate paymentDate = LocalDate.now().minusYears(2);
        customer.setPaymentDate(paymentDate);
        assertFalse(customer.isActiveMembership());
    }

    @Test
    public final void lastAllowedPaymentDateTest() {
        String socialSecurityNr = "7803159207";
        String name = "Lucky Luke";
        Customer customer = new Customer(socialSecurityNr, name);

        LocalDate paymentDate = LocalDate.now().minusYears(1);
        customer.setPaymentDate(paymentDate);
        assertTrue(customer.isActiveMembership());
    }

    @Test
    public final void oneDayAfterAllowedPaymentTest() {
        String socialSecurityNr = "8503179287";
        String name = "Unlucky Luke";
        Customer customer = new Customer(socialSecurityNr, name);

        LocalDate paymentDate = LocalDate.now().minusYears(1).minusDays(1);
        customer.setPaymentDate(paymentDate);
        assertFalse(customer.isActiveMembership());
    }

    @Test
    public final void lastVisitTest() {
        String socialSecurityNr = "7903179787";
        String name = "Eva Sandström";
        Customer customer = new Customer(socialSecurityNr, name);
        customer.setLastVisit(LocalDate.now());
        assertEquals(LocalDate.now(), customer.getLastVisit());
    }
}
