package implmnts;

import interfaces.Repository;
import model.Seller;

import java.util.ArrayList;

public class SellerRepository implements Repository<Seller, String> {

    private final ArrayList<Seller> sellers = new ArrayList<>();

    @Override
    public ArrayList<Seller> findAll() {
        return sellers;
    }

    @Override
    public Seller findById(String id) {
        for (Seller seller : sellers) {
            if (seller.getId().equals(id)) {
                return seller;
            }
        }
        return null;
    }

    @Override
    public void save(Seller entity) {

        for (int i = 0; i < sellers.size(); i++) {
            if (sellers.get(i).getId().equals(entity.getId())) {
                sellers.set(i, entity);
                return;
            }
        }

        sellers.add(entity);
    }

    @Override
    public void deleteById(String id) {
        sellers.removeIf(seller -> seller.getId().equals(id));
    }

    @Override
    public boolean existsById(String id) {
        for (Seller seller : sellers) {
            if (seller.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }
}
