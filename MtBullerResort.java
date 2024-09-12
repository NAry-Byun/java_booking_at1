import java.io.*;
import java.util.*;
import java.time.*;
import java.time.format.DateTimeParseException;

public class MtBullerResort {
    private FileInputStream fis;
    private ObjectInputStream ois;
    private FileOutputStream fos;
    private ObjectOutputStream oos;

    private ArrayList<Accommodation.Room> rooms;
    private ArrayList<Customer> customers;
    private ArrayList<TravelPackage> bookings;
    private Scanner input; // Single Scanner instance for the entire class

    // Constructor initializes lists and Scanner
    public MtBullerResort() {
        rooms = new ArrayList<>();
        customers = new ArrayList<>();
        bookings = new ArrayList<>();
        input = new Scanner(System.in); // Initialize Scanner once
    }

    // Populate the rooms and customers lists with predefined data
    public void populateLists() {
      try {
        Accommodation accommodation = new Accommodation(); // Initializes rooms with predefined data
        rooms.addAll(accommodation.getRooms()); // Ensure `getRooms()` method is defined in Accommodation class

        // Adding initial customers with specified levels
        customers.add(new Customer("Natasha", "Beginner"));
        customers.add(new Customer("Jeff", "Intermediate"));
        customers.add(new Customer("Sam", "Advanced"));
    } catch (Exception e) {
        System.out.println("Error populating lists: " + e.getMessage());
    }
    }

    // Main method to run the application
    public static void main(String[] args) {
        MtBullerResort resort = new MtBullerResort();
        resort.populateLists();
        resort.run();
        resort.input.close(); // Close Scanner at the end of the program
    }

    // Run the main menu
    public void run() {
        boolean flag = true;
        while (flag) {
          try {
            System.out.println("MtBuller Resort options\n------------------------\n" +
                    "1: Display all accommodations\n" +
                    "2: Display available accommodations\n" +
                    "3: Add customer\n" +
                    "4: List customers\n" +
                    "5: Create a package\n" +
                    "6: List packages\n" +
                    "7: Add a lift pass to package\n" +
                    "8: Add lesson fees to package\n" +
                    "9: Save packages to file\n" +
                    "10: Read packages from file\n" +
                    "11: Quit\n");

            System.out.print("Choose an option: ");
            int option = input.nextInt();
            input.nextLine();
            switch (option) {
                case 1:
                    displayAllRooms();
                    break;
                case 2:
                    displayAvailableRooms();
                    break;
                case 3:
                    addCustomer();
                    break;
                case 4:
                    listCustomers();
                    break;
                case 5:
                    createPackage();
                    break;
                case 6:
                    listPackages();
                    break;
                case 7:
                    addLiftPassToPackage();
                    break;
                case 8:
                    addLessonFeesToPackage();
                    break;
                case 9:
                    savePackages();
                    break;
                case 10:
                    readPackages();
                    break;
                case 11:
                    flag = false;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
          } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number.");
            input.nextLine(); // Clear the invalid input
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
        }
    }

    // Display all rooms
    public void displayAllRooms() {
        for (Accommodation.Room r : rooms) {
            System.out.println(r);
        }
    }

    // Display available rooms
    public void displayAvailableRooms() {
        for (Accommodation.Room r : rooms) {
            if (r.isAvailable()) {
                System.out.println(r);
            }
        }
    }

    // Add a customer with level selection
    public void addCustomer() {
      try {
        System.out.print("Customer name? ");
        String name = input.nextLine();

        // Prompt user to select the level
        String level = null;
        while (level == null) {
            System.out.println("Select level: 1. Beginner, 2. Intermediate, 3. Advanced");
            System.out.print("Choose an option: ");
            int choice = input.nextInt();
            input.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    level = "Beginner";
                    break;
                case 2:
                    level = "Intermediate";
                    break;
                case 3:
                    level = "Advanced";
                    break;
                default:
                    System.out.println("Invalid choice. Please select 1, 2, or 3.");
            }
        }

        // Create the customer with the chosen level
        Customer c = new Customer(name, level);
        customers.add(c);
        System.out.println("Customer added successfully: " + c);
      } catch (Exception e) {
        System.out.println("Error adding customer: " + e.getMessage());
    }
    }

    // List all customers
    public void listCustomers() {
        for (Customer c : customers) {
            System.out.println(c);
        }
    }

    // Create a booking package
    public void createPackage() {
      try {
        System.out.print("Customer ID? ");
        int custId = input.nextInt();
        input.nextLine();
        System.out.print("Duration (in days)? ");
        int duration = input.nextInt();
        input.nextLine();
        System.out.print("Date in format yyyy-MM-dd? ");
        String dateStr = input.nextLine();
        LocalDate startDate = LocalDate.parse(dateStr);

        // Select accommodation
        System.out.print("Enter room code (e.g., A-1, H-2, L-3): ");
        String roomCode = input.nextLine();
        Accommodation.Room room = searchRoomByCode(roomCode);
        if (room == null || !room.isAvailable()) {
            System.out.println("Room not available. Try again.");
            return;
        }
        double accommodationCost = room.getPricePerDay() * duration;

        // Create a new travel package
        TravelPackage travelPackage = new TravelPackage(custId, room.getRoomNo(), startDate, duration, null, null, accommodationCost
        );
        room.setAvailability("no"); // Correctly set availability to "no" when booked
        bookings.add(travelPackage);
        System.out.println("Package created successfully!");
      } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please use yyyy-MM-dd.");
        } catch (Exception e) {
            System.out.println("Error creating package: " + e.getMessage());
        }
    }

    public void addLiftPassToPackage() {
      try {
        System.out.print("Enter Booking ID to add a lift pass: ");
        int bookingId = input.nextInt();
        TravelPackage booking = searchPackageById(bookingId);
        if (booking == null) {
            System.out.println("Package not found.");
            return;
        }

    Customer customer = customers.stream()
      .filter(c -> c.getCustId()== booking.getCustId())
      .findFirst()
      .orElse(null);
    if (customer == null){
      System.out.println("Customer not found.");
      return;
    }


        TravelPackage.LiftPass liftPass = selectLiftPass();
        
       
        booking.setLiftPass(liftPass);
        System.out.println("Lift pass added to package.");
      }catch (Exception e) {
        System.out.println("Error adding lift pass: " + e.getMessage());
    }
    }

    // Add lesson fees to the package
    public void addLessonFeesToPackage() {
      try {
        System.out.print("Enter Booking ID to add lesson fees: ");
        int bookingId = input.nextInt();
        TravelPackage booking = searchPackageById(bookingId);
        if (booking == null) {
            System.out.println("Package not found.");
            return;
        }

        TravelPackage.Lesson lesson = selectLesson();
        booking.setLesson(lesson);
        System.out.println("Lesson fees added to package.");
      } catch (Exception e) {
        System.out.println("Error adding lesson fees: " + e.getMessage());
    }
    }

    // Method to select lift pass
    private TravelPackage.LiftPass selectLiftPass() {
      try {
        System.out.println("Select Lift Pass: 1. Full Day ($26), 2. Five Days (10% discount), 3. Season ($200)");
        int choice = input.nextInt();
        switch (choice) {
            case 1:
                return TravelPackage.LiftPass.FULL_DAY_PASS;
            case 2:
                return TravelPackage.LiftPass.FIVE_DAYS_PASS;
            case 3:
                return TravelPackage.LiftPass.SEASON_PASS;
            default:
                System.out.println("Invalid choice, defaulting to Full Day Pass.");
                return TravelPackage.LiftPass.FULL_DAY_PASS;
        }
      }catch (Exception e) {
        System.out.println("Error selecting lift pass: " + e.getMessage());
        return TravelPackage.LiftPass.FULL_DAY_PASS;
    }
    }

    // Method to select lesson type
    private TravelPackage.Lesson selectLesson() {
      try {
        System.out.println("Select Lesson: 1. Beginner ($25), 2. Intermediate ($20), 3. Advanced ($15)");
        int choice = input.nextInt();
        switch (choice) {
            case 1:
                return TravelPackage.Lesson.BEGINNER;
            case 2:
                return TravelPackage.Lesson.INTERMEDIATE;
            case 3:
                return TravelPackage.Lesson.ADVANCED;
            default:
                System.out.println("Invalid choice, defaulting to Beginner.");
                return TravelPackage.Lesson.BEGINNER;
        }
      } catch (Exception e) {
        System.out.println("Error selecting lesson: " + e.getMessage());
        return TravelPackage.Lesson.BEGINNER;
    }
    }

    // List all packages
    public void listPackages() {
        for (TravelPackage p : bookings) {
            System.out.println(p);
        }
    }

    // Save bookings to file
    public void saveBookings() {
        try {
            fos = new FileOutputStream("bookings.dat");
            oos = new ObjectOutputStream(fos);
            for (TravelPackage b : bookings) {
                oos.writeObject(b);
            }
            fos.close();
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Read bookings from file
    public void readBookings() {
        bookings.clear();
        try {
            fis = new FileInputStream("bookings.dat");
            ois = new ObjectInputStream(fis);
            while (true) {
                try {
                    TravelPackage b = (TravelPackage) ois.readObject();
                    int roomNo = b.getRoomNo();
                    Accommodation.Room r = searchRoomByNumber(roomNo);
                    if (r != null) {
                        r.setAvailability("no");
                    }
                    bookings.add(b);
                    System.out.println(b);
                } catch (EOFException eof) {
                    fis.close();
                    ois.close();
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Search for a room by code
    public Accommodation.Room searchRoomByCode(String code) {
        for (Accommodation.Room r : rooms) {
            if (r.getCode().equalsIgnoreCase(code)) {
                return r;
            }
        }
        return null;
    }

    // Search for a room by number
    public Accommodation.Room searchRoomByNumber(int roomNo) {
        for (Accommodation.Room r : rooms) {
            if (r.getRoomNo() == roomNo) {
                return r;
            }
        }
        return null;
    }

    // Search for a package by booking ID
    public TravelPackage searchPackageById(int bookingId) {
        for (TravelPackage b : bookings) {
            if (b.getBookingId() == bookingId) {
                return b;
            }
        }
        return null;
    }
 public void savePackages() {
    try {
      fos = new FileOutputStream("bookings.dat");
      oos = new ObjectOutputStream(fos);
      for (TravelPackage b:bookings) {
        oos.writeObject(b);
      }
      fos.close();
      oos.close();
      
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  
  public void readPackages() {
   bookings.clear();
    try {
      fis = new FileInputStream("bookings.dat");
      ois = new ObjectInputStream(fis);
      
      while (true) {
        try {
          Object object = ois.readObject();
          TravelPackage b = (TravelPackage)object;
          //update room status
           int roomNo = b.getRoomNo();
          Accommodation.Room r = searchRoomByNumber(roomNo);
          r.setAvailability("no");
          //add to array list
          bookings.add(b);
          System.out.println(b);
        } catch (EOFException eof) {
          fis.close();
          ois.close();
          break;
        }
      }
      
    } catch (Exception e) {
      e.printStackTrace();
    }
  
  }
  }
