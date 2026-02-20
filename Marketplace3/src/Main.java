import model.*;
import service.EmailService;
import service.SystemManager;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {

    static Scanner scanner = new Scanner(System.in);
    static SystemManager system = new SystemManager();

    public static void main(String[] args) {

        while (true) {

            System.out.println("\n==== MARKETPLACE SYSTEM ====");
            System.out.println("1- Login");
            System.out.println("2- Register");
         //   System.out.println("3- Test Email Sending 🔥");
            System.out.println("4- Exit");

            System.out.print("Choose: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> login();
                case 2 -> register();
                //case 3 -> testEmail();
                case 4 -> {
                    System.out.println("Goodbye 👋");
                    return;
                }

            }
        }
    }

    // ================= REGISTER =================

    private static void register() {
        try {
            System.out.print("Name: ");
            String name = scanner.nextLine();
            System.out.print("Email: ");
            String email = scanner.nextLine();
            System.out.print("Password: ");
            String pass = scanner.nextLine();

            System.out.println("Role: 1- Seller  2- Customer");
            int roleChoice = scanner.nextInt();
            scanner.nextLine();

            User user;

            if (roleChoice == 1)
                user = new Seller(name, email, pass);
            else
                user = new Customer(name, email, pass);

            system.register(user); // ✅ مش محتاج Admin
            System.out.println("Registered Successfully ✅");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // ================= LOGIN =================

    private static void login() {
        try {
            System.out.print("Email: ");
            String email = scanner.nextLine();
            System.out.print("Password: ");
            String pass = scanner.nextLine();

            User user = system.login(email, pass);

            if (user == null) {
                System.out.println("Invalid credentials ❌");
                return;
            }

            System.out.println("Welcome " + user.getName());

            if (user instanceof Admin)
                adminMenu();
            else if (user instanceof Seller)
                sellerMenu();
            else
                customerMenu();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // ================= ADMIN MENU =================

    private static void adminMenu() throws SQLException {

        while (true) {
            System.out.println("\n--- ADMIN MENU ---");
            System.out.println("1- Add User");
            System.out.println("2- Delete User");
            System.out.println("3- Delete Product");
            System.out.println("4- View All Products");
            System.out.println("5- View All Orders");
            System.out.println("6- View Total Sales");
            System.out.println("7- View Users Count");
            System.out.println("8- Create Shipment");
            System.out.println("9- Update Shipment Status");
            System.out.println("10- Logout");

            int ch = scanner.nextInt();
            scanner.nextLine();

            switch (ch) {

                case 1 -> {
                    System.out.print("Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Email: ");
                    String email = scanner.nextLine();
                    System.out.print("Password: ");
                    String pass = scanner.nextLine();
                    System.out.print("Role (ADMIN/SELLER/CUSTOMER): ");
                    String role = scanner.nextLine();

                    User user;
                    if (role.equalsIgnoreCase("ADMIN"))
                        user = new Admin(name, email, pass);
                    else if (role.equalsIgnoreCase("SELLER"))
                        user = new Seller(name, email, pass);
                    else
                        user = new Customer(name, email, pass);

                    system.addUser(user);
                    System.out.println("User Added ✅");
                }

                case 2 -> {
                    System.out.print("User ID: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    system.deleteUser(id);
                    System.out.println("Deleted ✅");
                }

                case 3 -> {
                    System.out.print("Product ID: ");
                    int pid = scanner.nextInt();
                    scanner.nextLine();
                    system.deleteProduct(pid); //
                    System.out.println("Product Deleted ✅");
                }

                case 4 -> {
                    List<Product> products = system.viewAllProducts();
                    for (Product p : products)
                        System.out.println(p.getId() + " - " + p.getName());
                }

                case 5 -> {
                    List<Order> orders = system.viewAllOrders();
                    for (Order o : orders)
                        System.out.println("Order ID: " + o.getId() + " - " + o.getStatus());
                }

                case 6 -> System.out.println("Total Sales: " + system.viewTotalSales());

                case 7 -> System.out.println("Users Count: " + system.viewUsersCount());

                case 8 -> {
                    System.out.print("Order ID: ");
                    int orderId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Tracking: ");
                    String tracking = scanner.nextLine();
                    system.createShipment(orderId, tracking);
                }

                case 9 -> {
                    System.out.print("Order ID: ");
                    int orderId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Status: ");
                    String status = scanner.nextLine();
                    system.updateShipmentStatus(orderId, status);
                }

                case 10 -> {
                    system.logout();
                    return;
                }
            }
        }
    }

    // ================= SELLER MENU =================

    private static void sellerMenu() throws SQLException {

        while (true) {

            System.out.println("\n--- SELLER MENU ---");
            System.out.println("1- Add Product");
            System.out.println("2- Update Product");
            System.out.println("3- Delete Product");
            System.out.println("4- My Products");
            System.out.println("5- My Orders");
            System.out.println("6- Change Order Status");
            System.out.println("7- Create Invoice");
            System.out.println("8- View My Profit");
            System.out.println("9- Logout");

            int ch = scanner.nextInt();
            scanner.nextLine();

            switch (ch) {

                case 1 -> {
                    System.out.print("Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Desc: ");
                    String desc = scanner.nextLine();
                    System.out.print("Price: ");
                    double price = scanner.nextDouble();
                    scanner.nextLine();
                    system.addProduct(name, desc, price);
                }

                case 2 -> {
                    System.out.print("Product ID: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("New Name: ");
                    String name = scanner.nextLine();
                    System.out.print("New Desc: ");
                    String desc = scanner.nextLine();
                    System.out.print("New Price: ");
                    double price = scanner.nextDouble();
                    scanner.nextLine();
                    system.updateMyProduct(id, name, desc, price);
                }

                case 3 -> {
                    System.out.print("Product ID: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    system.deleteMyProduct(id);
                }

                case 4 -> {
                    List<Product> list = system.viewMyProducts();
                    for (Product p : list)
                        System.out.println(p.getId() + " - " + p.getName());
                }

                case 5 -> {
                    List<Order> list = system.viewMyOrders();
                    for (Order o : list)
                        System.out.println(o.getId() + " - " + o.getStatus());
                }

                case 6 -> {
                    System.out.print("Order ID: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Status: ");
                    String status = scanner.nextLine();
                    system.changeOrderStatus(id, status);
                }

                case 7 -> {
                    System.out.print("Order ID: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    system.createInvoice(id); //  fixed
                }

                case 8 -> System.out.println("Profit: " + system.viewMyProfit());

                case 9 -> {
                    system.logout();
                    return;
                }
            }
        }
    }

    // ================= CUSTOMER MENU =================

    private static void customerMenu() throws SQLException {

        while (true) {

            System.out.println("\n--- CUSTOMER MENU ---");
            System.out.println("1- Browse Products");
            System.out.println("2- Create Order");
            System.out.println("3- My Orders");
            System.out.println("4- View Invoice");
            System.out.println("5- Track Shipment");
            System.out.println("6- Logout");

            int ch = scanner.nextInt();
            scanner.nextLine();

            switch (ch) {

                case 1 -> {
                    List<Product> products = system.browseProducts();
                    for (Product p : products)
                        System.out.println(p.getId() + " - " + p.getName() + " - " + p.getPrice());
                }

                case 2 -> {
                    int orderId = system.createOrder(); //  fixed
                    System.out.println("Order ID: " + orderId);

                    while (true) {
                        System.out.print("Product ID (0 to finish): ");
                        int pid = scanner.nextInt();
                        if (pid == 0) break;

                        System.out.print("Quantity: ");
                        int qty = scanner.nextInt();
                        scanner.nextLine();

                     // system.addOrderItem(orderId, pid, qty); //  fixed
                    }
                }

                case 3 -> {
                    List<Order> list = system.viewMyCustomerOrders();
                    for (Order o : list)
                        System.out.println(o.getId() + " - " + o.getStatus());
                }

                case 4 -> {
                    System.out.print("Order ID: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    Invoice invoice = system.viewInvoice(id);
                    if (invoice != null)
                        System.out.println("Total: " + invoice.getTotal());
                }

                case 5 -> {
                    System.out.print("Order ID: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    Shipment shipment = system.trackShipment(id);
                    if (shipment != null)
                        System.out.println("Status: " + shipment.getStatus());
                }

                case 6 -> {
                    system.logout();
                    return;
                }
            }
        }
    }
    // ================= TEST EMAIL =================

    private static void testEmail() {

        try {
            System.out.print("Enter your email to receive test message: ");
            String email = scanner.nextLine();

            EmailService.sendWelcomeEmail(email, "Test User");

        } catch (Exception e) {
            System.out.println("Error while sending email:");
            e.printStackTrace();
        }
    }

}
