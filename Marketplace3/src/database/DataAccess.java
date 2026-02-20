package database;

import model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataAccess {

    // ================= USERS =================

    public static void addUser(User user) throws SQLException {

        String checkSql = "SELECT id FROM users WHERE email=?";
        String insertSql = "INSERT INTO users (name,email,password,role,active) VALUES (?,?,?,?,true)";

        try (Connection conn = DatabaseManager.getConnection()) {

            // check duplicate email
            try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                checkStmt.setString(1, user.getEmail());
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next())
                    throw new RuntimeException("Email already exists");
            }

            try (PreparedStatement stmt = conn.prepareStatement(insertSql)) {
                stmt.setString(1, user.getName());
                stmt.setString(2, user.getEmail());
                stmt.setString(3, user.getPassword());
                stmt.setString(4, user.getRole());
                stmt.executeUpdate();
            }
        }
    }

    public static User login(String email, String password) throws SQLException {

        String sql = "SELECT * FROM users WHERE email=? AND password=? AND active=true";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                String role = rs.getString("role");

                switch (role) {
                    case "ADMIN":
                        return new Admin(
                                rs.getInt("id"),
                                rs.getString("name"),
                                rs.getString("email"),
                                rs.getString("password")
                        );
                    case "SELLER":
                        return new Seller(
                                rs.getInt("id"),
                                rs.getString("name"),
                                rs.getString("email"),
                                rs.getString("password")
                        );
                    default:
                        return new Customer(
                                rs.getInt("id"),
                                rs.getString("name"),
                                rs.getString("email"),
                                rs.getString("password")
                        );
                }
            }
        }
        return null;
    }

    public static void deleteUser(int userId) throws SQLException {
        String sql = "DELETE FROM users WHERE id=?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.executeUpdate();
        }
    }

    public static void deactivateUser(int userId) throws SQLException {
        String sql = "UPDATE users SET active=false WHERE id=?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.executeUpdate();
        }
    }

    public static void updateUserRole(int userId, String role) throws SQLException {
        String sql = "UPDATE users SET role=? WHERE id=?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, role);
            stmt.setInt(2, userId);
            stmt.executeUpdate();
        }
    }

    public static int getUsersCount() throws SQLException {
        String sql = "SELECT COUNT(*) FROM users";
        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) return rs.getInt(1);
        }
        return 0;
    }

    // ================= PRODUCTS =================

    public static void addProduct(Product product) throws SQLException {
        String sql = "INSERT INTO products (name,description,price,seller_id) VALUES (?,?,?,?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, product.getName());
            stmt.setString(2, product.getDescription());
            stmt.setDouble(3, product.getPrice());
            stmt.setInt(4, product.getSellerId());
            stmt.executeUpdate();
        }
    }

    public static void updateProduct(Product product) throws SQLException {
        String sql = "UPDATE products SET name=?, description=?, price=? WHERE id=? AND seller_id=?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, product.getName());
            stmt.setString(2, product.getDescription());
            stmt.setDouble(3, product.getPrice());
            stmt.setInt(4, product.getId());
            stmt.setInt(5, product.getSellerId());
            stmt.executeUpdate();
        }
    }

    public static void deleteProduct(int productId) throws SQLException {
        String sql = "DELETE FROM products WHERE id=?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, productId);
            stmt.executeUpdate();
        }
    }

    public static Product getProductById(int productId) throws SQLException {

        String sql = "SELECT * FROM products WHERE id=?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, productId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getInt("seller_id")
                );
            }
        }
        return null;
    }

    public static List<Product> getAllProducts() throws SQLException {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM products";

        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getInt("seller_id")
                ));
            }
        }
        return list;
    }

    public static List<Product> getSellerProducts(int sellerId) throws SQLException {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE seller_id=?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, sellerId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getInt("seller_id")
                ));
            }
        }
        return list;
    }

    public static List<Product> searchProducts(String keyword) throws SQLException {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE name LIKE ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + keyword + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getInt("seller_id")
                ));
            }
        }
        return list;
    }

    // ================= ORDERS =================
    public static List<OrderItem> getOrderItems(int orderId)
            throws SQLException {

        List<OrderItem> list = new ArrayList<>();

        String sql = "SELECT * FROM order_items WHERE order_id=?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                list.add(new OrderItem(
                        rs.getInt("id"),
                        rs.getInt("order_id"),
                        rs.getInt("product_id"),
                        rs.getInt("quantity"),
                        rs.getDouble("price")
                ));
            }
        }

        return list;
    }

// ================= EXTRA ORDER FUNCTIONS =================

    public static Order getOrderById(int orderId) throws SQLException {

        String sql = "SELECT * FROM orders WHERE id=?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                Timestamp ts = rs.getTimestamp("created_at");

                return new Order(
                        rs.getInt("id"),
                        rs.getInt("customer_id"),
                        rs.getDouble("total_amount"),
                        rs.getString("status"),
                        ts != null ? ts.toLocalDateTime() : null
                );
            }
        }

        return null;
    }

    public static int createOrder(Order order) throws SQLException {

        String sql = "INSERT INTO orders (customer_id,total_amount,status) VALUES (?,?,?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, order.getCustomerId());
            stmt.setDouble(2, 0);
            stmt.setString(3, order.getStatus());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1);
        }
        return -1;
    }

    public static void addOrderItem(int orderId, int productId, int quantity, double price)
            throws SQLException {

        Product product = getProductById(productId);
        if (product == null)
            throw new RuntimeException("Product not found");

        String sql = "INSERT INTO order_items (order_id,product_id,quantity,price) VALUES (?,?,?,?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, orderId);
            stmt.setInt(2, productId);
            stmt.setInt(3, quantity);
            stmt.setDouble(4, price);
            stmt.executeUpdate();
        }

        updateOrderTotal(orderId);
    }

    public static void updateOrderTotal(int orderId) throws SQLException {

        String sumSql =
                "SELECT COALESCE(SUM(quantity * price),0) FROM order_items WHERE order_id=?";

        String updateSql =
                "UPDATE orders SET total_amount=? WHERE id=?";

        try (Connection conn = DatabaseManager.getConnection()) {

            double total = 0;

            try (PreparedStatement sumStmt = conn.prepareStatement(sumSql)) {
                sumStmt.setInt(1, orderId);
                ResultSet rs = sumStmt.executeQuery();
                if (rs.next())
                    total = rs.getDouble(1);
            }

            try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                updateStmt.setDouble(1, total);
                updateStmt.setInt(2, orderId);
                updateStmt.executeUpdate();
            }
        }
    }

    public static void updateOrderStatus(int orderId, String status) throws SQLException {
        String sql = "UPDATE orders SET status=? WHERE id=?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setInt(2, orderId);
            stmt.executeUpdate();
        }
    }

    public static List<Order> getAllOrders() throws SQLException {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT * FROM orders";

        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Timestamp ts = rs.getTimestamp("created_at");
                list.add(new Order(
                        rs.getInt("id"),
                        rs.getInt("customer_id"),
                        rs.getDouble("total_amount"),
                        rs.getString("status"),
                        ts != null ? ts.toLocalDateTime() : null
                ));
            }
        }
        return list;
    }

    public static List<Order> getSellerOrders(int sellerId) throws SQLException {

        List<Order> list = new ArrayList<>();

        String sql =
                "SELECT DISTINCT o.* FROM orders o " +
                        "JOIN order_items oi ON o.id = oi.order_id " +
                        "JOIN products p ON oi.product_id = p.id " +
                        "WHERE p.seller_id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, sellerId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Timestamp ts = rs.getTimestamp("created_at");

                list.add(new Order(
                        rs.getInt("id"),
                        rs.getInt("customer_id"),
                        rs.getDouble("total_amount"),
                        rs.getString("status"),
                        ts != null ? ts.toLocalDateTime() : null
                ));
            }
        }
        return list;
    }

    public static List<Order> getCustomerOrders(int customerId) throws SQLException {

        List<Order> list = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE customer_id=?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Timestamp ts = rs.getTimestamp("created_at");

                list.add(new Order(
                        rs.getInt("id"),
                        rs.getInt("customer_id"),
                        rs.getDouble("total_amount"),
                        rs.getString("status"),
                        ts != null ? ts.toLocalDateTime() : null
                ));
            }
        }
        return list;
    }

    // ================= INVOICE =================

    public static void createInvoice(Invoice invoice) throws SQLException {

        String checkSql = "SELECT id FROM invoices WHERE order_id=?";
        String insertSql = "INSERT INTO invoices (order_id,total) VALUES (?,?)";

        try (Connection conn = DatabaseManager.getConnection()) {

            try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                checkStmt.setInt(1, invoice.getOrderId());
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next())
                    throw new RuntimeException("Invoice already exists");
            }

            try (PreparedStatement stmt = conn.prepareStatement(insertSql)) {
                stmt.setInt(1, invoice.getOrderId());
                stmt.setDouble(2, invoice.getTotal());
                stmt.executeUpdate();
            }
        }
    }

    public static Invoice getInvoice(int orderId) throws SQLException {

        String sql = "SELECT * FROM invoices WHERE order_id=?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Invoice(
                        rs.getInt("id"),
                        rs.getInt("order_id"),
                        rs.getTimestamp("issue_date") != null ?
                                rs.getTimestamp("issue_date").toLocalDateTime() : null,
                        rs.getDouble("total")
                );
            }
        }
        return null;
    }

    // ================= SHIPMENTS =================

    public static void createShipment(int orderId, String status, String trackingNumber)
            throws SQLException {

        String checkSql = "SELECT id FROM shipments WHERE order_id=?";
        String insertSql = "INSERT INTO shipments (order_id,status,tracking_number) VALUES (?,?,?)";

        try (Connection conn = DatabaseManager.getConnection()) {

            try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                checkStmt.setInt(1, orderId);
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next())
                    throw new RuntimeException("Shipment already exists");
            }

            try (PreparedStatement stmt = conn.prepareStatement(insertSql)) {
                stmt.setInt(1, orderId);
                stmt.setString(2, status);
                stmt.setString(3, trackingNumber);
                stmt.executeUpdate();
            }
        }
    }

    public static void updateShipmentStatus(int orderId, String status)
            throws SQLException {

        String sql = "UPDATE shipments SET status=? WHERE order_id=?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status);
            stmt.setInt(2, orderId);
            stmt.executeUpdate();
        }
    }

    public static Shipment getShipment(int orderId) throws SQLException {

        String sql = "SELECT * FROM shipments WHERE order_id=?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Shipment(
                        rs.getInt("id"),
                        rs.getInt("order_id"),
                        rs.getString("status"),
                        rs.getString("tracking_number")
                );
            }
        }
        return null;
    }

    // ================= REPORTS =================

    public static double getTotalSales() throws SQLException {

        String sql =
                "SELECT COALESCE(SUM(total_amount),0) FROM orders " +
                        "WHERE status IN ('PAID','SHIPPED','DELIVERED')";

        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) return rs.getDouble(1);
        }
        return 0;
    }

    public static double getSellerProfit(int sellerId) throws SQLException {

        String sql =
                "SELECT COALESCE(SUM(oi.price * oi.quantity),0) " +
                        "FROM order_items oi " +
                        "JOIN products p ON oi.product_id = p.id " +
                        "JOIN orders o ON o.id = oi.order_id " +
                        "WHERE p.seller_id=? AND o.status IN ('PAID','SHIPPED','DELIVERED')";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, sellerId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) return rs.getDouble(1);
        }
        return 0;
    }
    public static List<Customer> getAllCustomers() throws SQLException {

        List<Customer> list = new ArrayList<>();

        String sql = "SELECT * FROM users WHERE role='CUSTOMER' AND active=true";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                list.add(new Customer(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password")
                ));
            }
        }

        return list;
    }


}
