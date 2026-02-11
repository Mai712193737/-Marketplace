package implmnts;

import model.Shipment;
import model.enums.ShipmentStatus;
import interfaces.Repository;

import java.io.*;
import java.util.ArrayList;

public class ShipmentRepository implements Repository<Shipment, Long> {

    private final String filePath = "./data/shipments.csv";
    private final ArrayList<Shipment> shipments = new ArrayList<>();

    public ShipmentRepository() {
        loadFromFile();
    }

    private void loadFromFile() {

        File file = new File(filePath);

        if (!file.exists()) {
            createFileWithHeader();
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            String line = br.readLine();

            while ((line = br.readLine()) != null) {

                String[] parts = line.split(",");

                Long trackingNumber = Long.valueOf(parts[0]);
                String carrierCode = parts[1];
                Long orderId = Long.valueOf(parts[2]);
                ShipmentStatus status = ShipmentStatus.valueOf(parts[3]);

                Shipment shipment = new Shipment(trackingNumber, carrierCode, orderId, status);

                shipments.add(shipment);
            }

        } catch (Exception e) {
            System.out.println("Error loading shipments file");
        }
    }

    private void createFileWithHeader() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filePath))) {
            pw.println("trackingNumber,carrierCode,orderId,status");
        } catch (IOException e) {
            System.out.println("Could not create shipments file");
        }
    }

    @Override
    public ArrayList<Shipment> findAll() {
        return new ArrayList<>(shipments);
    }

    @Override
    public Shipment findById(Long trackingNumber) {
        for (Shipment s : shipments) {
            if (s.getTrackingNumber().equals(trackingNumber)) {
                return s;
            }
        }
        return null;
    }

    @Override
    public boolean existsById(Long trackingNumber) {
        return findById(trackingNumber) != null;
    }

    @Override
    public void save(Shipment shipment) {

        Shipment existing = findById(shipment.getTrackingNumber());

        if (existing == null) {
            shipments.add(shipment);
        } else {
            shipments.remove(existing);
            shipments.add(shipment);
        }

        saveToFile();
    }

    @Override
    public void deleteById(Long trackingNumber) {
        Shipment s = findById(trackingNumber);
        if (s != null) {
            shipments.remove(s);
            saveToFile();
        }
    }

    private void saveToFile() {

        try (PrintWriter pw = new PrintWriter(new FileWriter(filePath))) {

            pw.println("trackingNumber,carrierCode,orderId,status");

            for (Shipment s : shipments) {
                pw.println(
                        s.getTrackingNumber() + "," +
                                s.getCarrierCode() + "," +
                                s.getOrderId() + "," +
                                s.getStatus());
            }

        } catch (IOException e) {
            System.out.println("Error saving shipments file");
        }
    }
}
