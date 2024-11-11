import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.ArrayList;
import java.util.List;

public class HotelReservationSystemGUI {

    private JFrame frame;
    private JTextField nameField, emailField;
    private JTextField checkInField, checkOutField;
    private JTable roomTable;
    private JButton searchButton, reserveButton;
    private List<Room> rooms = new ArrayList<>();
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public HotelReservationSystemGUI() {
        // Initialize rooms
        rooms.add(new Room(101, "Single", 100));
        rooms.add(new Room(102, "Double", 150));
        rooms.add(new Room(103, "Suite", 250));

        // Set up the main frame
        frame = new JFrame("Hotel Reservation System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        // Input fields for user info
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(3, 2));

        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField();
        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField();

        inputPanel.add(nameLabel);
        inputPanel.add(nameField);
        inputPanel.add(emailLabel);
        inputPanel.add(emailField);

        frame.add(inputPanel, BorderLayout.NORTH);

        // Room table setup
        String[] columnNames = { "Room ID", "Type", "Price", "Availability" };
        Object[][] data = {};
        roomTable = new JTable(data, columnNames);
        roomTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(roomTable);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel();
        searchButton = new JButton("Search Available Rooms");
        reserveButton = new JButton("Reserve Room");

        buttonPanel.add(searchButton);
        buttonPanel.add(reserveButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Action Listeners for buttons
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateRoomTable();
            }
        });

        reserveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                makeReservation();
            }
        });

        frame.setVisible(true);
    }

    // Method to update the table with available rooms
    private void updateRoomTable() {
        String[][] roomData = new String[rooms.size()][4];
        int i = 0;
        for (Room room : rooms) {
            roomData[i][0] = String.valueOf(room.getRoomId());
            roomData[i][1] = room.getType();
            roomData[i][2] = "$" + room.getPrice();
            roomData[i][3] = room.isAvailable() ? "Available" : "Booked";
            i++;
        }
        roomTable.setModel(new javax.swing.table.DefaultTableModel(roomData,
                new String[] { "Room ID", "Type", "Price", "Availability" }));
    }

    // Method to handle reservation
    private void makeReservation() {
        try {
            String name = nameField.getText();
            String email = emailField.getText();

            if (name.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please fill out your name and email.");
                return;
            }

            int selectedRow = roomTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(frame, "Please select a room to reserve.");
                return;
            }

            Room selectedRoom = rooms.get(selectedRow);
            if (!selectedRoom.isAvailable()) {
                JOptionPane.showMessageDialog(frame, "Room is already booked.");
                return;
            }

            // Open a new window for reservation details
            new ReservationWindow(selectedRoom, name, email);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Error occurred. Please try again.");
        }
    }

    public static void main(String[] args) {
        new HotelReservationSystemGUI();
    }
}
