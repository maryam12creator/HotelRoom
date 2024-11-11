import java.util.Date;

public class Reservation {
    private User user;
    private Room room;
    private Date checkInDate;
    private Date checkOutDate;

    public Reservation(User user, Room room, Date checkInDate, Date checkOutDate) {
        this.user = user;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    public double getTotalPrice() {
        long diff = checkOutDate.getTime() - checkInDate.getTime();
        long days = diff / (1000 * 60 * 60 * 24); // Calculate number of days
        return room.getPrice() * days;
    }

    public void printReservationDetails() {
        System.out.println("\nReservation Details:");
        System.out.println("User: " + user.getName() + " (" + user.getEmail() + ")");
        System.out.println("Room Type: " + room.getType());
        System.out.println("Check-in Date: " + checkInDate);
        System.out.println("Check-out Date: " + checkOutDate);
        System.out.println("Total Price: $" + getTotalPrice());
    }
}
