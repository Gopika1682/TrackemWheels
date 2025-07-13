package TrackemWheels;
import java.util.*;

class User {
    protected String username;
    protected String password;

    public boolean login(String uname, String pwd) {
        return this.username.equals(uname) && this.password.equals(pwd);
    }

    public String getUsername() {
        return username;
    }
}

class Customer extends User {
    private String bookingId;
    private String vin;
    private String vehicleDetails;
    private String deliveryInfo = "Not Assigned";
    private String status = "Booking Confirmed";
    private List<String> orderHistory = new ArrayList<>();

    public Customer(String username, String password, String bookingId, String vin, String vehicleDetails) {
        this.username = username;
        this.password = password;
        this.bookingId = bookingId;
        this.vin = vin;
        this.vehicleDetails = vehicleDetails;
        this.orderHistory.add(vehicleDetails);
    }

    public boolean matchBooking(String bookingId, String vin) {
        return this.bookingId.equals(bookingId) && this.vin.equals(vin);
    }

    public void updateStatus(String status) {
        this.status = status;
    }

    public void updateDeliveryInfo(String info) {
        this.deliveryInfo = info;
    }

    public void viewBooking() {
        System.out.println("Vehicle Details: " + vehicleDetails);
    }

    public void viewDeliveryInfo() {
        System.out.println("Delivery Info: " + deliveryInfo);
    }

    public void trackDelivery() {
        System.out.println("Current Delivery Status: " + status);
    }

    public void viewOrderHistory() {
        System.out.println("Order History:");
        for (String order : orderHistory) {
            System.out.println("- " + order);
        }
    }
}

class CRE extends User {
    private List<Customer> customers;

    public CRE(String username, String password, List<Customer> customers) {
        this.username = username;
        this.password = password;
        this.customers = customers;
    }

    public void addCustomer(Scanner sc) {
        System.out.print("Enter customer username: ");
        String uname = sc.next();
        System.out.print("Enter password: ");
        String pwd = sc.next();
        System.out.print("Enter booking ID: ");
        String bookingId = sc.next();
        System.out.print("Enter VIN: ");
        String vin = sc.next();
        sc.nextLine();
        System.out.print("Enter vehicle details: ");
        String details = sc.nextLine();

        customers.add(new Customer(uname, pwd, bookingId, vin, details));
        System.out.println("Customer added successfully.");
    }

    public void updateNotification(Scanner sc) {
        System.out.print("Enter customer username: ");
        String uname = sc.next();
        for (Customer c : customers) {
            if (c.getUsername().equals(uname)) {
                System.out.print("Enter new delivery status: ");
                sc.nextLine();
                String status = sc.nextLine();
                c.updateStatus(status);
                System.out.print("Enter delivery date, time, and location: ");
                String info = sc.nextLine();
                c.updateDeliveryInfo(info);
                System.out.println("Notification updated.");
                return;
            }
        }
        System.out.println("Customer not found.");
    }

    public void viewCustomers() {
        if (customers.isEmpty()) {
            System.out.println("No customers found.");
        } else {
            System.out.println("Customer List:");
            for (Customer c : customers) {
                System.out.println("- " + c.getUsername());
            }
        }
    }
}

public class OnlineVehicleDeliveryManagement {
    private static Scanner sc = new Scanner(System.in);
    private static List<Customer> customers = new ArrayList<>();
    private static List<CRE> creList = new ArrayList<>();

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n===== Vehicle Delivery Management =====");
            System.out.println("1. Register as CRE");
            System.out.println("2. Login as CRE");
            System.out.println("3. Register as Customer");
            System.out.println("4. Login as Customer");
            System.out.println("5. Exit");
            System.out.print("Choose: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    registerCRE();
                    break;
                case 2:
                    loginCRE();
                    break;
                case 3:
                    registerCustomer();
                    break;
                case 4:
                    loginCustomer();
                    break;
                case 5:
                    System.out.println("Thank you. Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    // ---------------- CRE Methods -------------------
    public static void registerCRE() {
        System.out.print("Enter new CRE username: ");
        String uname = sc.next();
        System.out.print("Enter password: ");
        String pwd = sc.next();
        creList.add(new CRE(uname, pwd, customers));
        System.out.println("CRE registered successfully.");
    }

    public static void loginCRE() {
        System.out.print("Enter CRE username: ");
        String uname = sc.next();
        System.out.print("Enter password: ");
        String pwd = sc.next();

        for (CRE cre : creList) {
            if (cre.login(uname, pwd)) {
                creDashboard(cre);
                return;
            }
        }
        System.out.println("Invalid CRE credentials.");
    }

    public static void creDashboard(CRE cre) {
        while (true) {
            System.out.println("\n--- CRE Dashboard ---");
            System.out.println("1. Add Customer");
            System.out.println("2. Update Delivery Notification");
            System.out.println("3. View All Customers");
            System.out.println("4. Logout");
            System.out.print("Choose: ");
            int op = sc.nextInt();

            switch (op) {
                case 1:
                    cre.addCustomer(sc);
                    break;
                case 2:
                    cre.updateNotification(sc);
                    break;
                case 3:
                    cre.viewCustomers();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    // ---------------- Customer Methods -------------------
    public static void registerCustomer() {
        System.out.print("Enter username: ");
        String uname = sc.next();
        System.out.print("Enter password: ");
        String pwd = sc.next();
        System.out.print("Enter booking ID: ");
        String bookingId = sc.next();
        System.out.print("Enter VIN: ");
        String vin = sc.next();
        sc.nextLine();
        System.out.print("Enter vehicle details: ");
        String details = sc.nextLine();

        customers.add(new Customer(uname, pwd, bookingId, vin, details));
        System.out.println("Customer registered successfully.");
    }

    public static void loginCustomer() {
        System.out.print("Enter username: ");
        String uname = sc.next();
        System.out.print("Enter password: ");
        String pwd = sc.next();

        for (Customer c : customers) {
            if (c.login(uname, pwd)) {
                customerDashboard(c);
                return;
            }
        }
        System.out.println("Invalid Customer credentials.");
    }

    public static void customerDashboard(Customer c) {
        while (true) {
            System.out.println("\n--- Customer Dashboard ---");
            System.out.println("1. My Booking");
            System.out.println("2. Delivery Management");
            System.out.println("3. Track Delivery");
            System.out.println("4. Order History");
            System.out.println("5. Logout");
            System.out.print("Choose: ");
            int op = sc.nextInt();

            switch (op) {
                case 1:
                    System.out.print("Enter Booking ID: ");
                    String bId = sc.next();
                    System.out.print("Enter VIN: ");
                    String vin = sc.next();
                    if (c.matchBooking(bId, vin)) {
                        c.viewBooking();
                    } else {
                        System.out.println("Booking not matched.");
                    }
                    break;
                case 2:
                    c.viewDeliveryInfo();
                    break;
                case 3:
                    c.trackDelivery();
                    break;
                case 4:
                    c.viewOrderHistory();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }
}