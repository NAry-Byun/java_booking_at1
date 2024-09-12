import java.io.Serializable;
import java.time.LocalDate;

public class TravelPackage implements Serializable {

    private int bookingId;
    private int custId;
    private int roomNo;
    private LocalDate startDate;
    private int duration;
    private double accommodationCost;
    private boolean isPaid;
    private LiftPass liftPass;
    private Lesson lesson;
    static int nextID = 200;

    public enum LiftPass {
        FULL_DAY_PASS(26),
        FIVE_DAYS_PASS(26 * 5 * 0.9),
        SEASON_PASS(200);

        private final double price;

        LiftPass(double price) {
            this.price = price;
        }

        public double getPrice() {
            return price;
        }
    }

    public enum Lesson {
        ADVANCED(15),
        INTERMEDIATE(20),
        BEGINNER(25);

        private final double fee;

        Lesson(double fee) {
            this.fee = fee;
        }

        public double getFee() {
            return fee;
        }
    }

    // Default constructor
    public TravelPackage() {
        bookingId = nextID++;
    }

    // Main constructor with all necessary parameters including accommodation cost
    public TravelPackage(int custId, int roomNo, LocalDate startDate, int duration, LiftPass liftPass, Lesson lesson, double accommodationCost) {
        this.custId = custId;
        this.roomNo = roomNo;
        this.startDate = startDate;
        this.duration = duration;
        this.liftPass = liftPass;
        this.lesson = lesson;
        this.accommodationCost = accommodationCost;
        bookingId = nextID++;
    }

    // Calculate total cost including accommodation, lift pass, and lesson fees
    private double calculateTotalCost() {
        double total = accommodationCost; // Use the accommodation cost passed to the constructor
        if (liftPass != null) total += liftPass.getPrice();  // Add lift pass cost
        if (lesson != null) total += lesson.getFee();        // Add lesson fee
        return total;
    }

    // Getters and setters
    public int getBookingId() {
        return bookingId;
    }

    public int getCustId() {
        return custId;
    }

    public LocalDate getDate() {
        return startDate;
    }

    public int getDuration() {
        return duration;
    }

    public int getRoomNo() {
        return roomNo;
    }

    public void setAccommodationCost(double cost) {
        this.accommodationCost = cost; // Corrected to set accommodation cost
    }

    public boolean getIsPaid() {
        return isPaid;
    }

    public void setDate(String dateStr) {
        startDate = LocalDate.parse(dateStr);
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setRoomNo(int roomNo) {
        this.roomNo = roomNo;
    }

    public void setIsPaid(boolean isPaid) {
        this.isPaid = isPaid;
    }

    public double getTotalCost() {
        return calculateTotalCost(); // Call the calculateTotalCost method to get the correct total
    }

    // Set lift pass and update total cost
    public void setLiftPass(LiftPass liftPass) {
        this.liftPass = liftPass;
    }

    // Set lesson and update total cost
    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    // Get lift pass
    public LiftPass getLiftPass() {
        return liftPass;
    }

    // Get lesson
    public Lesson getLesson() {
        return lesson;
    }

    // toString method to display booking details
    @Override
    public String toString() {
        return "Booking ID: " + bookingId +
               ", Customer ID: " + custId +
               ", Room #: " + roomNo +
               ", Duration: " + duration + " days" +
               ", Accommodation Cost: $" + accommodationCost +
               ", Lift Pass : " + (liftPass != null ? liftPass.name() + " ($" + liftPass.getPrice() + ")" : "None") +
               ", Lesson Level : " + (lesson != null ? lesson.name() + " ($" + lesson.getFee() + ")" : "None") +
               ", Total Cost: $" + getTotalCost();
    }
}
