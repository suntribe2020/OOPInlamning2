import java.time.LocalDate;

/**
 * Created by Katri Vidén
 * Date: 2020-10-09
 * Time: 11:28
 * Project: OOPInlämning2Test
 * Copyright: MIT
 */
public class Customer {

    private final String socialSecurityNr;
    private final String name;
    private LocalDate paymentDate;
    private LocalDate lastVisit;

    public Customer(String socialSecurityNr, String name) {
        this.name = name;
        this.socialSecurityNr = socialSecurityNr;
        this.lastVisit = LocalDate.now();
    }

    public String getSocialSecurityNr() {
        return socialSecurityNr;
    }

    public String getName() {
        return name;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    // Kontrollerar om kunden är aktiv eller inte beroende på när betalningen gjordes
    public boolean isActiveMembership() {
        LocalDate allowedPaymentDate = LocalDate.now().minusYears(1);
        if (paymentDate == null) {
            return false;
        }
        int active = paymentDate.compareTo(allowedPaymentDate);
        return active >= 0;
    }

    public void setLastVisit(LocalDate lastVisit) {
        this.lastVisit = lastVisit;
    }

    public LocalDate getLastVisit() {
        return lastVisit;
    }
}
