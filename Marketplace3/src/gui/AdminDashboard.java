package gui;

import model.Order;
import model.Product;
import service.SystemManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AdminDashboard extends JFrame {

    private final SystemManager system;
    private final JPanel contentPanel;

    public AdminDashboard(SystemManager system){

        this.system = system;

        // Modern Look
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception ignored) {}

        setTitle("Admin Dashboard");
        setSize(1100,650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ===== Sidebar Gradient =====
        JPanel sidebar = new GradientPanel();
        sidebar.setLayout(new GridLayout(12,1,10,10));
        sidebar.setPreferredSize(new Dimension(230,600));
        sidebar.setBorder(BorderFactory.createEmptyBorder(20,10,20,10));

        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(new Color(240,240,240));
        contentPanel.setBorder(
                BorderFactory.createEmptyBorder(20,20,20,20)
        );

        sidebar.add(menuButton("Users Count", e -> showUsersCount()));
        sidebar.add(menuButton("Total Sales", e -> showTotalSales()));
        sidebar.add(menuButton("View All Products", e -> loadProducts()));
        sidebar.add(menuButton("Delete Product", e -> deleteProduct()));
        sidebar.add(menuButton("View All Orders", e -> loadOrders()));
        sidebar.add(menuButton("Delete User", e -> deleteUser()));
        sidebar.add(menuButton("Create Shipment", e -> createShipment()));
        sidebar.add(menuButton("Update Shipment", e -> updateShipment()));

        JButton logout = menuButton("Logout", e -> {
            system.logout();
            dispose();
            new LoginFrame(system);
        });

        sidebar.add(logout);

        add(sidebar, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    // ================= FUNCTIONS =================

    private void showUsersCount(){
        try{
            showSuccess("Users Count: " + system.viewUsersCount());
        }catch(Exception ex){
            showError(ex);
        }
    }

    private void showTotalSales(){
        try{
            showSuccess("Total Sales: " + system.viewTotalSales());
        }catch(Exception ex){
            showError(ex);
        }
    }

    private void loadProducts(){
        try{
            List<Product> list = system.viewAllProducts();

            DefaultTableModel model = new DefaultTableModel(
                    new String[]{"ID","Name","Price","Seller ID"},0);

            for(Product p : list){
                model.addRow(new Object[]{
                        p.getId(),
                        p.getName(),
                        p.getPrice(),
                        p.getSellerId()
                });
            }

            JTable table = new JTable(model);
            styleTable(table);
            showTable(table);

        }catch(Exception ex){
            showError(ex);
        }
    }

    private void deleteProduct(){
        try{
            int id = Integer.parseInt(
                    JOptionPane.showInputDialog("Product ID:"));
            system.deleteProduct(id);
            showSuccess("Product Deleted Successfully");
        }catch(Exception ex){
            showError(ex);
        }
    }

    private void loadOrders(){
        try{
            List<Order> list = system.viewAllOrders();

            DefaultTableModel model = new DefaultTableModel(
                    new String[]{"ID","Customer ID","Total","Status"},0);

            for(Order o : list){
                model.addRow(new Object[]{
                        o.getId(),
                        o.getCustomerId(),
                        o.getTotalAmount(),
                        o.getStatus()
                });
            }

            JTable table = new JTable(model);
            styleTable(table);
            showTable(table);

        }catch(Exception ex){
            showError(ex);
        }
    }

    private void deleteUser(){
        try{
            int id = Integer.parseInt(
                    JOptionPane.showInputDialog("User ID:"));
            system.deleteUser(id);
            showSuccess("User Deleted Successfully");
        }catch(Exception ex){
            showError(ex);
        }
    }

    private void createShipment(){
        try{
            int orderId = Integer.parseInt(
                    JOptionPane.showInputDialog("Order ID:"));
            String tracking = JOptionPane.showInputDialog("Tracking Code:");

            system.createShipment(orderId, tracking);
            showSuccess("Shipment Created Successfully");

        }catch(Exception ex){
            showError(ex);
        }
    }

    private void updateShipment(){
        try{
            int orderId = Integer.parseInt(
                    JOptionPane.showInputDialog("Order ID:"));
            String status = JOptionPane.showInputDialog("New Status:");

            system.updateShipmentStatus(orderId, status);
            showSuccess("Shipment Updated Successfully");

        }catch(Exception ex){
            showError(ex);
        }
    }

    // ================= UI STYLING =================

    private JButton menuButton(String text,
                               java.awt.event.ActionListener action){

        JButton b = new JButton(text);
        b.setFocusPainted(false);
        b.setFont(new Font("Segoe UI", Font.BOLD, 14));
        b.setBackground(new Color(40,40,40));
        b.setForeground(Color.WHITE);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.setBorder(BorderFactory.createEmptyBorder(12,20,12,20));

        b.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                b.setBackground(new Color(0,150,136));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                b.setBackground(new Color(40,40,40));
            }
        });

        b.addActionListener(action);
        return b;
    }

    private void styleTable(JTable table){
        table.setRowHeight(30);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(0,150,136));
        table.getTableHeader().setForeground(Color.WHITE);
        table.setSelectionBackground(new Color(0,150,136));
        table.setGridColor(new Color(200,200,200));
    }

    private void showTable(JTable table){
        contentPanel.removeAll();
        contentPanel.add(new JScrollPane(table),
                BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void showSuccess(String message){
        JOptionPane.showMessageDialog(
                this,
                message,
                "Success",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void showError(Exception ex){
        UIManager.put("OptionPane.background", new Color(30,30,30));
        UIManager.put("Panel.background", new Color(30,30,30));
        UIManager.put("OptionPane.messageForeground", Color.WHITE);

        JOptionPane.showMessageDialog(
                this,
                ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
    }

    // ===== Gradient Sidebar Class =====
    class GradientPanel extends JPanel {
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            GradientPaint gp = new GradientPaint(
                    0,0,new Color(15,15,15),
                    0,getHeight(),new Color(45,45,45));
            g2d.setPaint(gp);
            g2d.fillRect(0,0,getWidth(),getHeight());
        }
    }
}
