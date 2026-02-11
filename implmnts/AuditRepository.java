package implmnts;

import interfaces.Repository;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import model.Audit;
import model.Order;

public class AuditRepository implements Repository<Audit, Long> {

    private final String filePath = "./data/audit_out.csv";
    private final ArrayList<Audit> events = new ArrayList<>();

    public AuditRepository() {
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

                String[] parts = line.split(",", 6);

                Long id = Long.valueOf(parts[0]);
                Long orderId = Long.valueOf(parts[1]);
                String actor = parts[2];
                String action = parts[3];
                Long timestamp = Long.valueOf(parts[4]);
                String details = parts[5];

                Audit event = new Audit(id, orderId, actor, action, timestamp, details);

                events.add(event);
            }

        } catch (Exception e) {
            System.out.println("Error loading audit file");
        }
    }

    private void createFileWithHeader() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filePath))) {
            pw.println("id,orderId,actor,action,timestampMillis,details");
        } catch (IOException e) {
            System.out.println("Could not create audit file");
        }
    }

    @Override
    public ArrayList<Audit> findAll() {
        return new ArrayList<>(events);
    }

    @Override
    public Audit findById(Long id) {
        for (Audit e : events) {
            if (e.getId().equals(id)) {
                return e;
            }
        }
        return null;
    }

    @Override
    public boolean existsById(Long id) {
        return findById(id) != null;
    }

    @Override
    public void save(Audit event) {

        events.add(event);

        saveToFile(event);
    }

    private void saveToFile(Audit e) {

        try (PrintWriter pw = new PrintWriter(new FileWriter(filePath, true))) {

            pw.println(
                    e.getId() + "," +
                            e.getOrderId() + "," +
                            e.getActor() + "," +
                            e.getAction() + "," +
                            e.getTimestampMillis() + "," +
                            e.getDetails());

        } catch (IOException ex) {
            System.out.println("Error writing audit file");
        }
    }

    @Override
    public void deleteById(Long id) {
        Audit o = findById(id);
        if (o != null) {
            Audit.remove(o);
        }
    }
}
