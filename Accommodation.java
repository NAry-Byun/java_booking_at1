import java.util.ArrayList;
import java.util.List;

public class Accommodation {

    public static class Room {
        private String code;             // Unique code for the room
        private String name;             // Name of the accommodation
        private String type;             // Type (Apartment, Hotel, Lodge)
        private double pricePerDay;      // Price per day
        private String availability = "yes"; // Room availability initialized as "yes"
        private static int nextId = 1;   // Auto-increment room number
        private int roomNo;              // Room number

        // Constructor with code, name, type, and price
        public Room(String code, String name, String type, double pricePerDay) {
            this.code = code;
            this.name = name;
            this.type = type;
            this.pricePerDay = pricePerDay;
            this.roomNo = nextId++;
        }

        // Getters and Setters
        public String getCode() {
            return code;
        }

        public String getName() {
            return name;
        }

        public String getType() {
            return type;
        }

        public double getPricePerDay() {
            return pricePerDay;
        }

        // Check if the room is available ("yes" means available)
        public boolean isAvailable() {
            return availability.equalsIgnoreCase("yes");
        }

        // Set availability as "yes" or "no"
        public void setAvailability(String availability) {
            if (availability.equalsIgnoreCase("yes") || availability.equalsIgnoreCase("no")) {
                this.availability = availability.toLowerCase();
            } else {
                throw new IllegalArgumentException("Availability must be 'yes' or 'no'.");
            }
        }

        public int getRoomNo() {
            return roomNo; // Ensures getRoomNo() method is defined
        }

        @Override
        public String toString() {
            return "Room No: " + roomNo + ", Code: " + code + ", Name: " + name + ", Type: " + type +
                    ", Price per Day: $" + pricePerDay + ", Available: " + availability;
        }
    }

    // List to store all accommodations
    private List<Room> rooms;

    // Constructor to initialize the list of accommodations
    public Accommodation() {
        rooms = new ArrayList<>();
        initializeRooms(); // Populates the list with predefined rooms
    }

    // Method to initialize the rooms with predefined accommodations
    private void initializeRooms() {
        // Apartments
        rooms.add(new Room("A-1", "Moose Apartment", "Apartment", 150));
        rooms.add(new Room("A-2", "The Park Apartment", "Apartment", 180));
        rooms.add(new Room("A-3", "White Horse Apartment", "Apartment", 200));

        // Hotels
        rooms.add(new Room("H-1", "Abom Hotel", "Hotel", 110));
        rooms.add(new Room("H-2", "Duck Inn Hotel", "Hotel", 320));
        rooms.add(new Room("H-3", "Breath Take Hotel", "Hotel", 730));

        // Lodges
        rooms.add(new Room("L-1", "Awsc Lodge", "Lodge", 150));
        rooms.add(new Room("L-2", "The Bike Lodge", "Lodge", 180));
        rooms.add(new Room("L-3", "Amber Lodge", "Lodge", 200));
    }

    // Method to get all rooms
    public List<Room> getRooms() {
        return rooms; // Ensures getRooms() method is defined
    }

    // Method to find a room by its code
    public Room findRoomByCode(String code) {
        for (Room room : rooms) {
            if (room.getCode().equalsIgnoreCase(code)) {
                return room;
            }
        }
        return null;
    }

    // Display all rooms
    public void displayAllRooms() {
        for (Room room : rooms) {
            System.out.println(room);
        }
    }

    // Display available rooms
    public void displayAvailableRooms() {
        for (Room room : getAvailableRooms()) {
            System.out.println(room);
        }
    }

    // Method to get all available rooms
    public List<Room> getAvailableRooms() {
        List<Room> availableRooms = new ArrayList<>();
        for (Room room : rooms) {
            if (room.isAvailable()) {
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }
}
