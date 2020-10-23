package uhk.fim.model;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    private List<ShoppingCartItem> items;

    public ShoppingCart() {
        this.items = new ArrayList<ShoppingCartItem>();
    }

    public List<ShoppingCartItem> getItems() {
        return items;
    }

    public void addItem(ShoppingCartItem item) {
        boolean exists = false;

        int index = 0;

        for (ShoppingCartItem itm: items) {

            if (item.getName().equals(itm.getName()) && itm.getPricePerPiece() == itm.getPricePerPiece()) {
                exists = true;
                ShoppingCartItem temp = itm;
                index = items.indexOf(temp);
                System.out.println(index);
            }
        }



        if (exists) {
            items.get(index).setPieces(items.get(index).getPieces()+item.getPieces());
        }
        else  {
            items.add(item);
        }



    }
}
