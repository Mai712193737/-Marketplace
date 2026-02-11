package interfaces;

// import model.Order;
// import model.Product;

import java.util.ArrayList;

public interface Repository<T, ID> {

  ArrayList<T> findAll();

  T findById(ID id);

  void save(T entity);

  void deleteById(ID id);

  boolean existsById(ID id);

}