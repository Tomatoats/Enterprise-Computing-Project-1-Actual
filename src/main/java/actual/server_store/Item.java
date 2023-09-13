package actual.server_store;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Item {


    //set up  what each item has
    String id;
    String name;
    boolean  have_Any;
    int quantity;
    double price;

    public Item(String ID,String Name, boolean Have, int Quantity, double Price){
        this.id = ID;
        this.name = Name;
        this.have_Any = Have;
        this.quantity = Quantity;
        this.price = Price;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getHave_Any() {
        return have_Any;
    }

    public void setHave_Any(boolean have_Any) {
        this.have_Any = have_Any;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double worth) {
        //BigDecimal number = new BigDecimal(worth);
        //number.setScale(2, RoundingMode.HALF_UP);
        //String priceActual = number.toString();
        this.price = worth;
    }

}
