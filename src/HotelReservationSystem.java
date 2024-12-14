import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class HotelReservationSystem {

    protected void reserveRoom(Statement statement, Scanner scanner) {
        try {
            System.out.println("Please enter the guest name: ");
            String guestName = scanner.next();
            scanner.nextLine();
            System.out.println("Please enter the room number: ");
            String roomNumber = scanner.next();
            scanner.nextLine();
            System.out.println("Please enter contact number: ");
            String contactNumber = scanner.next();
            scanner.nextLine();

            String query = "INSERT INTO reservations(guest_name,room_number,contact_number) " +
                    "VALUES('" + guestName + "'," + roomNumber + ",'" + contactNumber + "');";
            try {
                int rowsAffected = statement.executeUpdate(query);

                if (rowsAffected > 0) {
                    System.out.println("Reservation successfully!!");
                } else {
                    System.out.println("Reservation failed!!");
                }
            } catch (SQLException e) {
                System.err.println("SQL Exception: " + e.getMessage());
            }
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }

    protected void viewReservations(Statement statement) {
        String query = "SELECT * FROM reservations;";
        try {
            ResultSet resultSet = statement.executeQuery(query);

            // Print the table header with vertical separators
            System.out.format("| %-15s | %-20s | %-15s | %-20s | %-25s |%n",
                    "Reservation ID", "Guest Name", "Room Number", "Contact Number", "Reservation Date");
            System.out.println("|-----------------|----------------------|-----------------" +
                    "|----------------------|" +
                    "---------------------------|");

            while (resultSet.next()) {
                int reservationID = resultSet.getInt("reservation_id");
                String guestName = resultSet.getString("guest_name");
                int roomNumber = resultSet.getInt("room_number");
                String contactNumber = resultSet.getString("contact_number");
                String reservationDate = resultSet.getTimestamp("reservation_date").toString();

                System.out.format("| %-15d | %-20s | %-15d | %-20s | %-25s |%n",
                        reservationID, guestName, roomNumber, contactNumber, reservationDate);
            }
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }

    protected void getRoomNumber(Statement statement, Scanner scanner) {
        System.out.println("Please enter the reservation id: ");
        int reservationID = scanner.nextInt();
        System.out.println("Please enter the guest name: ");
        String guestName = scanner.next();

        String query = "SELECT room_number FROM reservations" +
                "WHERE reservation_id = " + reservationID +
                "AND guest_name = '" + guestName + "';";

        try {
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                int roomNumber = resultSet.getInt("room_number");
                System.out.println("Room number for Reservation ID " + reservationID +
                        "and Guest " + guestName + "is: " + roomNumber);
            } else {
                System.out.println("Reservation not found for the given " +
                        "Reservation ID and Guest");
            }
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }

    protected void updateReservation(Statement statement, Scanner scanner) {
        try {
            System.out.println("Please enter the reservation id to be updated: ");
            int reservationID = scanner.nextInt();
            scanner.nextLine();

            if (!reservationExists(statement, reservationID)) {
                System.out.println("Reservation not found for given reservation ID");
                return;
            }

            System.out.println("Enter the new Guest name: ");
            String newGuestName = scanner.next();
            System.out.println("Enter the new Room Number: ");
            int newRoomNumber = scanner.nextInt();
            System.out.println("Enter the new Contact Number: ");
            String newContactNumber = scanner.next();

            String query = "UPDATE reservations" +
                    "SET guest_name = '" + newGuestName + "', room_number = " + newRoomNumber +
                    ",contact_number = '" + newContactNumber + "'" +
                    "WHERE reservation_id = " + reservationID + ";";
            try {
                int affectedRows = statement.executeUpdate(query);
                if (affectedRows > 0) {
                    System.out.println("Reservation updated successfully!!!");
                } else {
                    System.out.println("Reservation updating failed!!!");
                }
            }catch (SQLException e){
                System.err.println("Exception: " + e.getMessage());
            }

        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }

    protected void deleteReservation(Statement statement, Scanner scanner) {
        try {
            System.out.println("Please enter the reservation id to be deleted: ");
            int reservationID = scanner.nextInt();

            if (!reservationExists(statement, reservationID)) {
                System.out.println("Reservation not found for given reservation ID");
                return;
            }
            String query = "DELETE FROM reservations WHERE reservation_id = " + reservationID;

            try {
                int affectedRows = statement.executeUpdate(query);
                if (affectedRows > 0) {
                    System.out.println("Reservation deleted successfully!!!");
                } else {
                    System.out.println("Reservation deletion failed!!!");
                }
            } catch (SQLException e) {
                System.err.println("Exception: " + e.getMessage());
            }
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }

    private boolean reservationExists(Statement statement, int reservationID) {
        try {
            String query = "SELECT reservation_id FROM reservations " +
                    "WHERE reservation_id = " + reservationID + ";";
            ResultSet resultSet = statement.executeQuery(query);
            return resultSet.next();
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
            return false;
        }
    }

    protected void exit() throws InterruptedException {
        System.out.println("Exiting System!!!");
        int i = 5;
        while (i != 0) {
            System.out.print(".");
            Thread.sleep(450);
            i--;
        }
        System.out.println();
        System.out.println("Thank You for using Hotel Reservation System");
    }
}
