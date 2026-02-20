package model;

public class Seller extends User {

    public Seller(int id, String name, String email, String password) {
        super(id, name, email, password, "SELLER");
    }

    public Seller(String name, String email, String password) {
        super(name, email, password, "SELLER");
    }
}
