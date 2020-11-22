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
        return 5;
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
            case 4:
                return item.isBought();
            default:
                return null;
        }
    }

    @Override
    public Class getColumnClass(int column) {
        switch (column) {
            case 0:
                return String.class;
            case 1:
                return Double.class;
            case 2:
                return Integer.class;
            case 3:
                return Double.class;
            case 4:
                return Boolean.class;
            default:
                return null;
        }
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        if (column == 4) {
            return true;
        }
        return false;
    }

    @Override
    public void setValueAt(Object value, int row, int col) {
        ShoppingCartItem item = cart.getItems().get(row);
        //Protože jde upravovat jen hodnota bought, můžu si dovolit přetypovat na bool. Ale správně to rozhodně není.
        item.setBought((boolean)value);

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
            case 4:
                return "Zakoupeno";
            default:
                return null;
        }
    }
}
