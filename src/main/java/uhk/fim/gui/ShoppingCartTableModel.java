package uhk.fim.gui;

import uhk.fim.model.ShoppingCart;
import uhk.fim.model.ShoppingCartItem;

import javax.swing.table.AbstractTableModel;

public class ShoppingCartTableModel extends AbstractTableModel {
    private ShoppingCart cart;


    @Override
    public int getRowCount() {
        return cart.getItems().size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int row, int col) {
        ShoppingCartItem item = cart.getItems().get(row);
        switch (col) {
            case 0:
                return item.getName();
            case 1:
                return item.getPricePerPiece();
            case 2:
                return item.getPieces();
            case 3:
                return (item.getPieces() * item.getPricePerPiece());
            default:
                return null;
        }
    }

    public void setCart(ShoppingCart cart) {
        this.cart = cart;
    }

    @Override
    public String getColumnName(int col){
        switch (col) {
            case 0:
                return "Název";
            case 1:
                return "Cena/kus";
            case 2:
                return "Počet kusů";
            case 3:
                return "Celková cena";
            default:
                return null;
        }
    }
}
