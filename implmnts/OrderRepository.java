package implmnts;

import model.Order;
import interfaces.Repository;

import java.io.*;
import java.util.ArrayList;

public class OrderRepository implements Repository<Order, Long> {

    private final String filePath = "./data/orders_out.csv";
    private final ArrayList<Order> orders = new ArrayList<>();

    public OrderRepository() {
        loadFromFile();
    }

    private void loadFromFile() {

        File file = new File(filePath);

        if (!file.exists()) {
            createFileWithHeader();
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            String line = br.readLine(); // skip header

            while ((line = br.readLine()) != null) {

                String[] parts = line.split(",");

                Long id = Long.valueOf(parts[0]);
                String customerId = parts[1];
                String status = parts[2];

                Double subtotal = Double.valueOf(parts[3]);
                Double discount = Double.valueOf(parts[4]);
                Double shippingFee = Double.valueOf(parts[5]);
                Double grandTotal = Double.valueOf(parts[6]);

                Order order = new Order(id, customerId);
                order.setStatusFromString(status);
                order.setSubtotal(subtotal);
                order.setDiscount(discount);
                order.setShippingFee(shippingFee);
                order.setGrandTotal(grandTotal);

                orders.add(order);
            }

        } catch (Exception e) {
            System.out.println("Error loading orders file");
        }
    }

    private void createFileWithHeader() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filePath))) {
            pw.println("id,customerId,status,subtotal,discount,shippingFee,grandTotal");
        } catch (IOException e) {
            System.out.println("Could not create orders file");
        }
    }

    @Override
    public ArrayList<Order> findAll() {
        return new ArrayList<>(orders);
    }

    @Override
    public Order findById(Long id) {
        for (Order o : orders) {
            if (o.getId().equals(id)) {
                return o;
            }
        }
        return null;
    }

    @Override
    public boolean existsById(Long id) {
        return findById(id) != null;
    }

    @Override
    public void save(Order order) {

        Order existing = findById((Long) order.getId());

        if (existing == null) {
            orders.add(order);
        } else {
            orders.remove(existing);
            orders.add(order);
        }

        saveToFile();
    }

    @Override
    public void deleteById(Long id) {
        Order o = findById(id);
        if (o != null) {
            orders.remove(o);
            saveToFile();
        }
    }

    private void saveToFile() {

        try (PrintWriter pw = new PrintWriter(new FileWriter(filePath))) {

            pw.println("id,customerId,status,subtotal,discount,shippingFee,grandTotal");

            for (Order o : orders) {
                pw.println(
                        o.getId() + "," +
                                o.getCustomerId() + "," +
                                o.getStatus() + "," +
                                o.getSubtotal() + "," +
                                o.getDiscount() + "," +
                                o.getShippingFee() + "," +
                                o.getGrandTotal());
            }

        } catch (IOException e) {
            System.out.println("Error saving orders file");
        }
    }
}
