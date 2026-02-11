package implmnts;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import model.Product;

public class ProductRepository {

  private final String filePath = "./data/products.csv";
  private final ArrayList<Product> products = new ArrayList<>();

  public ProductRepository() {
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

        String sellerId = parts[0];
        String sku = parts[1];
        String title = parts[2];
        Integer qty = Integer.valueOf(parts[3]);
        Double price = Double.valueOf(parts[4]);
        Boolean active = Boolean.valueOf(parts[5]);

        Product product = new Product(sellerId, sku, title, qty, price, active);

        products.add(product);
      }
    } catch (Exception e) {
      System.out.println("Error loading products: " + e.getMessage());
    }
  }

  private void createFileWithHeader() {
    try (PrintWriter pw = new PrintWriter(new FileWriter(filePath))) {
      pw.println("sellerId,sku,title,availableQty,price,active");
    } catch (IOException e) {
      System.out.println("Could not create products file");
    }
  }

  public ArrayList<Product> findAll() {
    return new ArrayList<>(products);
  }

  public Product findBySellerAndSku(Object sellerId, Object sku) {
    for (Product p : products) {
      if (p.getSellerId().equals(sellerId) && p.getSku().equals(sku)) {
        return p;
      }
    }
    return null;
  }

  public boolean existsBySellerAndSku(String sellerId, String sku) {
    return findBySellerAndSku(sellerId, sku) != null;
  }

  public void save(Product product) {

    Product existing = findBySellerAndSku(product.getSellerId(), product.getSku());

    if (existing == null) {
      products.add(product);
    } else {
      products.remove(existing);
      products.add(product);
    }

    saveToFile();
  }

  public void deleteBySellerAndSku(String sellerId, String sku) {
    Product p = findBySellerAndSku(sellerId, sku);
    if (p != null) {
      products.remove(p);
      saveToFile();
    }
  }

  private void saveToFile() {
    try (PrintWriter pw = new PrintWriter(new FileWriter(filePath))) {

      pw.println("sellerId,sku,title,availableQty,price,active");

      for (Product p : products) {
        pw.println(p.getSellerId() + "," + p.getSku() + "," + p.getTitle() + "," + p.getAvailableQty() + ","
            + p.getPrice() + "," + p.getActive());
      }

    } catch (IOException e) {
      System.out.println("Error saving products file");
    }
  }
}
