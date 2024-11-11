import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class ReservationWindow {
    private JDialog dialog;
    private JTextField checkInField, checkOutField;
    private JButton confirmButton;
    private Room room;
    private String userName, userEmail;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public ReservationWindow(Room room, String userName, String userEmail) {
        this.room = room;
        this.userName = userName;
        this.userEmail = userEmail;

        dialog = new JDialog();
        dialog.setTitle("Reservation Details");
        dialog.setSize(300, 200);
        dialog.setLayout(new GridLayout(4, 2));

        JLabel checkInLabel = new JLabel("Check-in Date (YYYY-MM-DD):");
        checkInField = new JTextField();
        JLabel checkOutLabel = new JLabel("Check-out Date (YYYY-MM-DD):");
        checkOutField = new JTextField();

        confirmButton = new JButton("Confirm Reservation");

        dialog.add(checkInLabel);
        dialog.add(checkInField);
        dialog.add(checkOutLabel);
        dialog.add(checkOutField);
        dialog.add(new JLabel()); // Empty cell
        dialog.add(confirmButton);

        confirmButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                confirmReservation();
            }
        });

        dialog.setVisible(true);
    }

    private void confirmReservation() {
        try {
            String checkInDateStr = checkInField.getText();
            String checkOutDateStr = checkOutField.getText();

            Date checkInDate = dateFormat.parse(checkInDateStr);
            Date checkOutDate = dateFormat.parse(checkOutDateStr);

            long diff = checkOutDate.getTime() - checkInDate.getTime();
            long days = diff / (1000 * 60 * 60 * 24);
            double totalPrice = room.getPrice() * days;

            JOptionPane.showMessageDialog(dialog, "Reservation Confirmed!\n" +
                    "Room: " + room.getType() + "\n" +
                    "Check-in: " + checkInDateStr + "\n" +
                    "Check-out: " + checkOutDateStr + "\n" +
                    "Total Price: $" + totalPrice);

            room.setAvailable(false); // Mark the room as booked
            dialog.dispose(); // Close the reservation window
        } catch (Exception e) {
            JOptionPane.showMessageDialog(dialog, "Error: Please check your date format.");
        }
    }
}
