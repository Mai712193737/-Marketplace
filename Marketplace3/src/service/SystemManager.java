package service;

import database.DataAccess;
import model.*;

import java.sql.SQLException;
import java.util.List;

public class SystemManager {

    private User currentUser;

    // ================= AUTH =================

    public User login(String email, String password) throws SQLException {
        currentUser = DataAccess.login(email, password);

        if (currentUser == null)
            throw new RuntimeException("Invalid email or password");

        return currentUser;
    }

    public void logout() {
        currentUser = null;
    }

    public User getCurrentUser() {
        checkLoggedIn();
        return currentUser;
    }

    public void register(User user) throws Exception {

        DataAccess.addUser(user);

        // Send welcome email
        EmailService.sendWelcomeEmail(
                user.getEmail(),
                user.getName()
        );
    }


    // ================= ADMIN =================

    public void addUser(User user) throws SQLException {
        checkAdmin();
        DataAccess.addUser(user);
    }

    public void deleteUser(int userId) throws SQLException {
        checkAdmin();
        DataAccess.deleteUser(userId);
    }

    public void deleteProduct(int productId) throws SQLException {
        checkAdmin();
        DataAccess.deleteProduct(productId);
    }

    public List<Product> viewAllProducts() throws SQLException {
        checkAdmin();
        return DataAccess.getAllProducts();
    }

    public List<Order> viewAllOrders() throws SQLException {
        checkAdmin();
        return DataAccess.getAllOrders();
    }

    public double viewTotalSales() throws SQLException {
        checkAdmin();
        return DataAccess.getTotalSales();
    }

    public int viewUsersCount() throws SQLException {
        checkAdmin();
        return DataAccess.getUsersCount();
    }

    public void createShipment(int orderId, String tracking) throws SQLException {
        checkAdmin();
        DataAccess.createShipment(orderId, "PENDING", tracking);
    }

    public void updateShipmentStatus(int orderId, String status) throws SQLException {
        checkAdmin();
        DataAccess.updateShipmentStatus(orderId, status);
    }

    // ================= SELLER =================

    public void addProduct(String name, String desc, double price) throws SQLException {
        checkSeller();
        Seller seller = (Seller) currentUser;
        Product product = new Product(name, desc, price, seller.getId());
        DataAccess.addProduct(product);
    }

    public void updateMyProduct(int id, String name, String desc, double price)
            throws SQLException {

        checkSeller();
        Seller seller = (Seller) currentUser;

        Product existing = DataAccess.getProductById(id);

        if (existing == null || existing.getSellerId() != seller.getId())
            throw new RuntimeException("You can update only your products");

        Product updated = new Product(id, name, desc, price, seller.getId());
        DataAccess.updateProduct(updated);
    }

    public void deleteMyProduct(int productId) throws SQLException {
        checkSeller();
        Seller seller = (Seller) currentUser;

        Product existing = DataAccess.getProductById(productId);

        if (existing == null || existing.getSellerId() != seller.getId())
            throw new RuntimeException("You can delete only your products");

        DataAccess.deleteProduct(productId);
    }

    public List<Product> viewMyProducts() throws SQLException {
        checkSeller();
        Seller seller = (Seller) currentUser;
        return DataAccess.getSellerProducts(seller.getId());
    }

    public List<Order> viewMyOrders() throws SQLException {
        checkSeller();
        Seller seller = (Seller) currentUser;
        return DataAccess.getSellerOrders(seller.getId());
    }

    public void changeOrderStatus(int orderId, String status) throws SQLException {
        checkSeller();

        if (status.equalsIgnoreCase("DELIVERED"))
            throw new RuntimeException("Seller cannot mark as DELIVERED");

        DataAccess.updateOrderStatus(orderId, status);
    }

    public void createInvoice(int orderId) throws SQLException {
        checkSeller();

        Order order = DataAccess.getAllOrders().stream()
                .filter(o -> o.getId() == orderId)
                .findFirst()
                .orElse(null);

        if (order == null || order.getTotalAmount() <= 0)
            throw new RuntimeException("Invalid order");

        Invoice invoice = new Invoice(orderId, order.getTotalAmount());
        DataAccess.createInvoice(invoice);
    }

    public double viewMyProfit() throws SQLException {
        checkSeller();
        Seller seller = (Seller) currentUser;
        return DataAccess.getSellerProfit(seller.getId());
    }

    public int createOrderForCustomer(int customerId) throws SQLException {
        checkSeller();

        Order order = new Order(
                0,
                customerId,
                0,
                "CREATED",
                null
        );

        return DataAccess.createOrder(order);
    }

    public List<Customer> viewAllCustomers() throws SQLException {
        checkSeller();
        return DataAccess.getAllCustomers();
    }

    public void addOrderItemBySeller(int orderId, int productId, int quantity)
            throws SQLException {

        checkSeller();

        if (quantity <= 0)
            throw new RuntimeException("Invalid quantity");

        Product product = DataAccess.getProductById(productId);

        if (product == null)
            throw new RuntimeException("Product not found");

        Seller seller = (Seller) currentUser;

        if (product.getSellerId() != seller.getId())
            throw new RuntimeException("You can add only your products");

        DataAccess.addOrderItem(orderId, productId, quantity, product.getPrice());
    }

    // ================= CUSTOMER =================

    public List<Product> browseProducts() throws SQLException {
        checkCustomer();
        return DataAccess.getAllProducts();
    }

    public int createOrder() throws SQLException {

        checkCustomer();
        Customer customer = (Customer) currentUser;

        Order order = new Order(customer.getId(), "CREATED");

        int orderId = DataAccess.createOrder(order);

        // 🔥 إنشاء فاتورة تلقائي
        Invoice invoice = new Invoice(orderId, 0);
        DataAccess.createInvoice(invoice);

        return orderId;
    }


    public void addProductToMyOrder(int orderId, int productId, int quantity)
            throws SQLException {

        checkCustomer();

        if (quantity <= 0)
            throw new RuntimeException("Invalid quantity");

        Customer customer = (Customer) currentUser;

        Order order = DataAccess.getOrderById(orderId);

        if (order == null || order.getCustomerId() != customer.getId())
            throw new RuntimeException("You can modify only your orders");

        if (!order.getStatus().equalsIgnoreCase("CREATED"))
            throw new RuntimeException("Cannot modify this order");

        Product product = DataAccess.getProductById(productId);

        if (product == null)
            throw new RuntimeException("Product not found");

        DataAccess.addOrderItem(orderId, productId, quantity, product.getPrice());
    }

    public List<Order> viewMyCustomerOrders() throws SQLException {
        checkCustomer();
        Customer customer = (Customer) currentUser;
        return DataAccess.getCustomerOrders(customer.getId());
    }

    public List<OrderItem> viewMyOrderDetails(int orderId)
            throws SQLException {

        checkCustomer();

        Customer customer = (Customer) currentUser;

        Order order = DataAccess.getOrderById(orderId);

        if (order == null || order.getCustomerId() != customer.getId())
            throw new RuntimeException("Access denied to this order");

        return DataAccess.getOrderItems(orderId);
    }

    public void cancelMyOrder(int orderId) throws SQLException {

        checkCustomer();

        Customer customer = (Customer) currentUser;

        Order order = DataAccess.getOrderById(orderId);

        if (order == null || order.getCustomerId() != customer.getId())
            throw new RuntimeException("You can cancel only your orders");

        if (!order.getStatus().equalsIgnoreCase("CREATED"))
            throw new RuntimeException("Only CREATED orders can be cancelled");

        DataAccess.updateOrderStatus(orderId, "CANCELLED");
    }

    public Invoice viewInvoice(int orderId) throws SQLException {
        checkCustomer();
        return DataAccess.getInvoice(orderId);
    }

    public Shipment trackShipment(int orderId) throws SQLException {
        checkCustomer();
        return DataAccess.getShipment(orderId);
    }

    // ================= SECURITY =================

    private void checkLoggedIn() {
        if (currentUser == null)
            throw new RuntimeException("You must login first");
    }

    private void checkAdmin() {
        checkLoggedIn();
        if (!(currentUser instanceof Admin))
            throw new RuntimeException("Access Denied: Admin only");
    }

    private void checkSeller() {
        checkLoggedIn();
        if (!(currentUser instanceof Seller))
            throw new RuntimeException("Access Denied: Seller only");
    }

    private void checkCustomer() {
        checkLoggedIn();
        if (!(currentUser instanceof Customer))
            throw new RuntimeException("Access Denied: Customer only");
    }

}
