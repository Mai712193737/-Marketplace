package gui;

import model.*;
import service.SystemManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class UserDashboard extends JFrame {

    private final SystemManager system;
    private final User user;
    private final JPanel contentPanel;

    public UserDashboard(SystemManager system, User user) {

        this.system = system;
        this.user = user;

        setTitle("Dashboard - " + user.getRole());
        setSize(950, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel sidebar = new JPanel();
        sidebar.setLayout(new GridLayout(18, 1, 5, 5));
        sidebar.setBackground(new Color(40, 40, 40));
        sidebar.setPreferredSize(new Dimension(220, 600));

        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);

        if (user instanceof Seller) {
            addSellerButtons(sidebar);
        } else if (user instanceof Customer) {
            addCustomerButtons(sidebar);
        }

        JButton logout = new JButton("Logout");
        logout.addActionListener(e -> {
            system.logout();
            dispose();
            new LoginFrame(system);
        });

        sidebar.add(logout);

        add(sidebar, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    // ================= SELLER =================

    private void addSellerButtons(JPanel panel) {

        panel.add(menuButton("Add Product", e -> addProduct()));
        panel.add(menuButton("Update Product", e -> updateProduct()));
        panel.add(menuButton("Delete Product", e -> deleteProduct()));
        panel.add(menuButton("My Products", e -> loadMyProducts()));
        panel.add(menuButton("My Orders", e -> loadMyOrders()));
      //  panel.add(menuButton("Change Order Status", e -> changeStatus()));
      //  panel.add(menuButton("Create Invoice", e -> createInvoice()));
        //panel.add(menuButton("View Profit", e -> viewProfit()));

        panel.add(menuButton("View Customers", e -> loadCustomers()));
       // panel.add(menuButton("Create Order For Customer", e -> createOrderForCustomer()));
       // panel.add(menuButton("Add Product To Order", e -> addProductToOrderBySeller()));
    }

    private void addProduct() {
        try {
            String name = JOptionPane.showInputDialog("Name:");
            if (name == null) return;

            String desc = JOptionPane.showInputDialog("Description:");
            if (desc == null) return;

            double price = Double.parseDouble(
                    JOptionPane.showInputDialog("Price:"));

            system.addProduct(name, desc, price);
            JOptionPane.showMessageDialog(this, "Product Added");

        } catch (Exception e) {
            showError(e);
        }
    }

    private void updateProduct() {
        try {
            int id = Integer.parseInt(
                    JOptionPane.showInputDialog("Product ID:"));

            String name = JOptionPane.showInputDialog("New Name:");
            String desc = JOptionPane.showInputDialog("New Desc:");
            double price = Double.parseDouble(
                    JOptionPane.showInputDialog("New Price:"));

            system.updateMyProduct(id, name, desc, price);
            JOptionPane.showMessageDialog(this, "Updated");

        } catch (Exception e) {
            showError(e);
        }
    }

    private void deleteProduct() {
        try {
            int id = Integer.parseInt(
                    JOptionPane.showInputDialog("Product ID:"));

            system.deleteMyProduct(id);
            JOptionPane.showMessageDialog(this, "Deleted");

        } catch (Exception e) {
            showError(e);
        }
    }

    private void loadMyProducts() {
        try {
            List<Product> list = system.viewMyProducts();

            DefaultTableModel model = new DefaultTableModel(
                    new String[]{"ID", "Name", "Price"}, 0);

            for (Product p : list)
                model.addRow(new Object[]{
                        p.getId(),
                        p.getName(),
                        p.getPrice()
                });

            showTable(new JTable(model));

        } catch (Exception e) {
            showError(e);
        }
    }

    private void loadMyOrders() {
        try {
            List<Order> list = system.viewMyOrders();

            DefaultTableModel model = new DefaultTableModel(
                    new String[]{"ID", "Customer ID", "Total", "Status"}, 0);

            for (Order o : list)
                model.addRow(new Object[]{
                        o.getId(),
                        o.getCustomerId(),
                        o.getTotalAmount(),
                        o.getStatus()
                });

            showTable(new JTable(model));

        } catch (Exception e) {
            showError(e);
        }
    }

    private void changeStatus() {
        try {
            int id = Integer.parseInt(
                    JOptionPane.showInputDialog("Order ID:"));

            String status = JOptionPane.showInputDialog("New Status:");

            system.changeOrderStatus(id, status);
            JOptionPane.showMessageDialog(this, "Status Updated");

        } catch (Exception e) {
            showError(e);
        }
    }

    private void createInvoice() {
        try {
            int id = Integer.parseInt(
                    JOptionPane.showInputDialog("Order ID:"));

            system.createInvoice(id);
            JOptionPane.showMessageDialog(this, "Invoice Created");

        } catch (Exception e) {
            showError(e);
        }
    }

    private void viewProfit() {
        try {
            JOptionPane.showMessageDialog(this,
                    "Profit: " + system.viewMyProfit());

        } catch (Exception e) {
            showError(e);
        }
    }

    private void loadCustomers() {
        try {
            List<Customer> list = system.viewAllCustomers();

            DefaultTableModel model = new DefaultTableModel(
                    new String[]{"ID", "Name", "Email"}, 0);

            for (Customer c : list)
                model.addRow(new Object[]{
                        c.getId(),
                        c.getName(),
                        c.getEmail()
                });

            showTable(new JTable(model));

        } catch (Exception e) {
            showError(e);
        }
    }

    private void createOrderForCustomer() {
        try {
            int customerId = Integer.parseInt(
                    JOptionPane.showInputDialog("Customer ID:"));

            int orderId = system.createOrderForCustomer(customerId);

            JOptionPane.showMessageDialog(this,
                    "Order Created ID: " + orderId);

        } catch (Exception e) {
            showError(e);
        }
    }

    private void addProductToOrderBySeller() {
        try {

            int orderId = Integer.parseInt(
                    JOptionPane.showInputDialog("Order ID:"));

            int productId = Integer.parseInt(
                    JOptionPane.showInputDialog("Product ID:"));

            int quantity = Integer.parseInt(
                    JOptionPane.showInputDialog("Quantity:"));

            system.addOrderItemBySeller(orderId, productId, quantity);

            JOptionPane.showMessageDialog(this,
                    "Product Added To Order Successfully");

        } catch (Exception e) {
            showError(e);
        }
    }

    // ================= CUSTOMER =================

    private void addCustomerButtons(JPanel panel) {

        panel.add(menuButton("Browse Products", e -> browseProducts()));
        panel.add(menuButton("Create Order", e -> createOrder()));
        panel.add(menuButton("My Orders", e -> loadCustomerOrders()));
        panel.add(menuButton("View Order Details", e -> viewMyOrderDetails()));
        panel.add(menuButton("Cancel Order", e -> cancelMyOrder()));
        panel.add(menuButton("View Invoice", e -> viewInvoice()));
        panel.add(menuButton("Track Shipment", e -> trackShipment()));
    }

    private void addProductToMyOrder() {
        try {
            int orderId = Integer.parseInt(
                    JOptionPane.showInputDialog("Order ID:"));

            int productId = Integer.parseInt(
                    JOptionPane.showInputDialog("Product ID:"));

            int quantity = Integer.parseInt(
                    JOptionPane.showInputDialog("Quantity:"));

            system.addProductToMyOrder(orderId, productId, quantity);

            JOptionPane.showMessageDialog(this,
                    "Product Added Successfully");

        } catch (Exception e) {
            showError(e);
        }
    }

    private void viewMyOrderDetails() {
        try {
            int orderId = Integer.parseInt(
                    JOptionPane.showInputDialog("Order ID:"));

            List<OrderItem> list =
                    system.viewMyOrderDetails(orderId);

            DefaultTableModel model =
                    new DefaultTableModel(
                            new String[]{"Product ID", "Quantity", "Price"}, 0);

            for (OrderItem item : list)
                model.addRow(new Object[]{
                        item.getProductId(),
                        item.getQuantity(),
                        item.getPrice()
                });

            showTable(new JTable(model));

        } catch (Exception e) {
            showError(e);
        }
    }

    private void cancelMyOrder() {
        try {
            int orderId = Integer.parseInt(
                    JOptionPane.showInputDialog("Order ID:"));

            system.cancelMyOrder(orderId);

            JOptionPane.showMessageDialog(this,
                    "Order Cancelled Successfully");

        } catch (Exception e) {
            showError(e);
        }
    }

    private void browseProducts() {
        try {
            List<Product> list = system.browseProducts();

            DefaultTableModel model = new DefaultTableModel(
                    new String[]{"ID", "Name", "Price"}, 0);

            for (Product p : list)
                model.addRow(new Object[]{
                        p.getId(),
                        p.getName(),
                        p.getPrice()
                });

            showTable(new JTable(model));

        } catch (Exception e) {
            showError(e);
        }
    }

    private void createOrder() {

        try {

            int orderId = system.createOrder();

            List<Product> products = system.browseProducts();

            DefaultTableModel model =
                    new DefaultTableModel(
                            new String[]{"ID", "Name", "Price"}, 0);

            for (Product p : products)
                model.addRow(new Object[]{
                        p.getId(),
                        p.getName(),
                        p.getPrice()
                });

            JTable table = new JTable(model);

            JTextField qtyField = new JTextField();

            JButton addBtn = new JButton("Add Selected Product");

            JPanel bottomPanel = new JPanel(new BorderLayout());
            bottomPanel.add(new JLabel("Quantity:"), BorderLayout.WEST);
            bottomPanel.add(qtyField, BorderLayout.CENTER);
            bottomPanel.add(addBtn, BorderLayout.EAST);

            contentPanel.removeAll();
            contentPanel.add(new JScrollPane(table), BorderLayout.CENTER);
            contentPanel.add(bottomPanel, BorderLayout.SOUTH);
            contentPanel.revalidate();
            contentPanel.repaint();

            addBtn.addActionListener(e -> {

                try {

                    int selectedRow = table.getSelectedRow();

                    if (selectedRow == -1) {
                        JOptionPane.showMessageDialog(this,
                                "Select a product first");
                        return;
                    }

                    int productId =
                            (int) model.getValueAt(selectedRow, 0);

                    int qty =
                            Integer.parseInt(qtyField.getText());

                    system.addProductToMyOrder(orderId,
                            productId,
                            qty);

                    JOptionPane.showMessageDialog(this,
                            "Product Added Successfully ✅");

                    qtyField.setText("");

                } catch (Exception ex) {
                    showError(ex);
                }
            });

        } catch (Exception e) {
            showError(e);
        }
    }


    private void loadCustomerOrders() {
        try {
            List<Order> list = system.viewMyCustomerOrders();

            DefaultTableModel model = new DefaultTableModel(
                    new String[]{"ID", "Total", "Status"}, 0);

            for (Order o : list)
                model.addRow(new Object[]{
                        o.getId(),
                        o.getTotalAmount(),
                        o.getStatus()
                });

            showTable(new JTable(model));

        } catch (Exception e) {
            showError(e);
        }
    }

    private void viewInvoice() {
        try {
            int id = Integer.parseInt(
                    JOptionPane.showInputDialog("Order ID:"));

            Invoice invoice = system.viewInvoice(id);

            if (invoice == null) {
                JOptionPane.showMessageDialog(this,
                        "No Invoice Found For This Order");
                return;
            }

            JOptionPane.showMessageDialog(this,
                    "Order ID: " + invoice.getOrderId()
                            + "\nTotal: " + invoice.getTotal());

        } catch (Exception e) {
            showError(e);
        }
    }

    private void trackShipment() {
        try {
            int id = Integer.parseInt(
                    JOptionPane.showInputDialog("Order ID:"));

            Shipment s = system.trackShipment(id);

            if (s == null) {
                JOptionPane.showMessageDialog(this,
                        "No Shipment Found");
                return;
            }

            JOptionPane.showMessageDialog(this,
                    "Tracking: " + s.getTrackingNumber()
                            + "\nStatus: " + s.getStatus());

        } catch (Exception e) {
            showError(e);
        }
    }

    // ================= HELPERS =================

    private JButton menuButton(String text,
                               java.awt.event.ActionListener action) {
        JButton b = new JButton(text);
        b.setFocusPainted(false);
        b.addActionListener(action);
        return b;
    }

    private void showTable(JTable table) {
        contentPanel.removeAll();
        contentPanel.add(new JScrollPane(table),
                BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void showError(Exception e) {
        JOptionPane.showMessageDialog(this,
                e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
    }
}
