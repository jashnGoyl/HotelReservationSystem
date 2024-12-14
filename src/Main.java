import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Main {

    private static final HotelReservationSystem hotelReservationSystem = new HotelReservationSystem();

    private static final String url = "jdbc:mysql://localhost:3306/hotel_db";

    private static final String username = "root";

    private static final String password = "blackngrey";

    public static void main(String[] args) {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Drivers loaded successfully!!!");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        try{
            Connection connection = DriverManager.getConnection(url,username,password);
            Statement statement = connection.createStatement();
            Scanner scanner = new Scanner(System.in);
            while (true){
                System.out.println();
                System.out.println("HOTEL MANAGEMENT SYSTEM");
                System.out.println("1. Reserve a room");
                System.out.println("2. View Reservation");
                System.out.println("3. Get Room Number");
                System.out.println("4. Update Reservations");
                System.out.println("5. Delete Reservations");
                System.out.println("0. Exit");
                System.out.println("Choose an option: ");
                int choice = scanner.nextInt();

                switch (choice){
                    case 1:
                        hotelReservationSystem.reserveRoom(statement,scanner);
                        break;
                    case 2:
                        hotelReservationSystem.viewReservations(statement);
                        break;
                    case 3:
                        hotelReservationSystem.getRoomNumber(statement,scanner);
                        break;
                    case 4:
                        hotelReservationSystem.updateReservation(statement,scanner);
                        break;
                    case 5:
                        hotelReservationSystem.deleteReservation(statement,scanner);
                        break;
                    case 0:
                        hotelReservationSystem.exit();
                        scanner.close();
                        return;
                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            }
        }catch (SQLException e){
            System.err.println("Exception: " + e.getMessage());
        }catch (InterruptedException e){
            throw new RuntimeException();
        }
    }
}
