import javax.swing.*;

/**
 * Created by Katri Vidén
 * Date: 2020-10-09
 * Time: 11:28
 * Project: OOPInlämning2Test
 * Copyright: MIT
 */
public class Main {

    public static void main(String[] args) {
        Gym gym = new Gym("BestGymEver");
        gym.open();

        while (gym.isOpen()) {
            int pressedButton = receptionSelection();
            if (pressedButton == 0) {
                String input = readInputData();
                Customer customer = gym.findCustomer(input);
                if (customer == null) {
                    int newCustomerSelection = registerNewCustomerSelection();
                    if (newCustomerSelection == 0) {
                        boolean tryToRegisterCustomer = true;

                        while (tryToRegisterCustomer) {
                            String newCustomerData = registerNewCustomerData();
                            if (newCustomerData == null) {
                                JOptionPane.showMessageDialog(null, "Vad synd, hoppas vi " +
                                        "ses igen!");
                                tryToRegisterCustomer = false;
                            } else if (gym.registerNewCustomer(newCustomerData)) {
                                JOptionPane.showMessageDialog(null, "Registreringen utförd!");
                                tryToRegisterCustomer = false;
                            } else {
                                JOptionPane.showMessageDialog(null, "Felaktig inmatning, " +
                                        "vänligen försök igen!");
                            }
                        }
                    }

                    continue;
                }

                if (customer.isActiveMembership()) {
                    GymUtils.writeCustomerDataToFile(customer);
                    JOptionPane.showMessageDialog(null, "Ditt besök har registrerats, " +
                            "välkommen att träna!");
                } else {
                    JOptionPane.showMessageDialog(null, "Kunden är inte aktiv längre.");
                }
            } else if (pressedButton == 1) {
                JOptionPane.showMessageDialog(null, "Tack för idag, ses igen imorgon!");
                gym.close();
            }
        }
    }

    // Dialogrutor

    private static int receptionSelection() {
        String[] selection1 = new String[]{
                "Ta emot kund",
                "Stäng gym"};

        ImageIcon icon = new ImageIcon(
                "icons/gym2.png");

        int pressedButton = JOptionPane.showOptionDialog(null,
                "Vad vill du göra?", "Gymregister", JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE, icon, selection1, selection1[1]);

        if (pressedButton == -1) {
            JOptionPane.showMessageDialog(null, "Du valde att avbryta.");
            System.exit(0);
        }
        return pressedButton;
    }

    private static String readInputData() {
        ImageIcon icon = new ImageIcon(
                "icons/gym.png");

        String input = (String) JOptionPane.showInputDialog(null, "Skriv kundens namn " +
                        "eller personnummer:", "Gymregister", JOptionPane.QUESTION_MESSAGE, icon,
                null,"");

        if (input == null) {
            JOptionPane.showMessageDialog(null, "Du valde att avbryta.");
            System.exit(0);
        }
        return input;
    }

    private static int registerNewCustomerSelection() {
        String[] selection2 = new String[]{
                "Ja",
                "Nej"};

        ImageIcon icon = new ImageIcon(
                "icons/gym3.png");

        int pressedButton = JOptionPane.showOptionDialog(null,
                "Kunden finns inte med i registret.\nVill du registrera en ny kund?",
                "Gymregister", JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE, icon, selection2, selection2[1]);

        if (pressedButton == -1) {
            JOptionPane.showMessageDialog(null, "Du valde att avbryta.");
        }
        return pressedButton;
    }

    private static String registerNewCustomerData() {
        return JOptionPane.showInputDialog("Skriv in kundens personnummer och namn:\n " +
                "(Ex. 6704170428, Kalle Anka)");
    }
}

