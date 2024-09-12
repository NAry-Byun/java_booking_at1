public class Customer {
    private int custId;
    private String name;
    private String level;
    static int nextID = 1;

    // Constructor with customer ID, name, and level
    public Customer(int custId, String name, String level) {
        this.custId = custId; // Correctly assigning custId
        this.name = name;
        setLevel(level);
    }

    // Constructor with only name and level, assigns automatic customer ID
    public Customer(String name, String level) {
        this.custId = nextID++;
        this.name = name;
        setLevel(level);
    }

    // Getter for custId
    public int getCustId() { // Added missing curly brace
        return custId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        if (level.equalsIgnoreCase("Beginner") ||
            level.equalsIgnoreCase("Intermediate") ||
            level.equalsIgnoreCase("Advanced")) {
            this.level = capitalize(level);
        } else {
            throw new IllegalArgumentException("Level must be Beginner, Intermediate, or Advanced.");
        }
    }

    // Method to capitalize the first letter of the level
    private String capitalize(String level) {
        return level.substring(0, 1).toUpperCase() + level.substring(1).toLowerCase();
    }

    @Override
    public String toString() {
        return "Customer ID: " + custId + ", Name: " + name + ", Level: " + level;
    }
}
