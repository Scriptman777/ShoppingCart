package uhk.fim.gui;

import uhk.fim.model.ShoppingCart;
import uhk.fim.model.ShoppingCartItem;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame implements ActionListener{
    JButton btnInputAdd;
    JButton btnInputDelete;
    JTextField txtInputName;
    JTextField txtPrice;
    JSpinner spNumber;
    JLabel lblTotalPrice;
    MainFrame mainFrame = this;

    ShoppingCart shoppingCart;
    ShoppingCartTableModel shoppingCartTableModel  = new ShoppingCartTableModel();;


    public MainFrame(int wdth, int hght) {
        super("PRO2 - Shopping cart");
        setSize(wdth, hght);
        initFrame();
        initGUI();
        shoppingCart = new ShoppingCart();
        shoppingCartTableModel.setCart(shoppingCart);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void initFrame() {

    }

    public void initGUI() {
    JPanel panelMain = new JPanel(new BorderLayout());

    //Panely
    JPanel panelInputs = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JPanel panelTable = new JPanel(new BorderLayout());
    JPanel panelFooter = new JPanel(new BorderLayout());

    //Input
    JLabel lblInputName = new JLabel("Název: ");
    txtInputName = new JTextField("",15);
    JLabel lblNumber = new JLabel("Počet kusů: ");
    spNumber = new JSpinner(new SpinnerNumberModel(1,1,999,1));
    JLabel lblPrice = new JLabel("Cena za kus: ");
    txtPrice = new JTextField("",5);

    //Tabulka
    JTable table = new JTable();
    table.setModel(shoppingCartTableModel);
    panelTable.add(new JScrollPane(table),BorderLayout.CENTER);


    //Tlačítka
    btnInputAdd = new JButton("Přidat");
    btnInputAdd.addActionListener(this);
    btnInputDelete = new JButton("Smazat");
    btnInputDelete.addActionListener(this);

    //ADD ALL THE THINGS
    panelInputs.add(lblInputName);
    panelInputs.add(txtInputName);
    panelInputs.add(lblNumber);
    panelInputs.add(spNumber);
    panelInputs.add(lblPrice);
    panelInputs.add(txtPrice);
    panelInputs.add(btnInputAdd);
    panelInputs.add(btnInputDelete);

    //Label dole
    lblTotalPrice = new JLabel("Celková cena: 0,0 Kč");
    panelFooter.add(lblTotalPrice,BorderLayout.WEST);

    //ADD PANELS
    panelMain.add(panelInputs, BorderLayout.NORTH);
    panelMain.add(panelTable, BorderLayout.CENTER);
    panelMain.add(panelFooter, BorderLayout.SOUTH);


    add(panelMain);

    }

    public void updatePrice()  {
        double price = 0;
        for (ShoppingCartItem itm: shoppingCart.getItems()) {
            price += (itm.getPricePerPiece() * itm.getPieces());
        }
        lblTotalPrice.setText("Celková cena: " + price +  " Kč");

    }



    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == btnInputAdd) {
            //TODO: Validovat věci
            if (!txtInputName.getText().isEmpty()) {
                try {
                    ShoppingCartItem item = new ShoppingCartItem(txtInputName.getText(),Double.parseDouble(txtPrice.getText()) ,(int)spNumber.getValue());
                    JOptionPane.showMessageDialog(this,"Přidáno","Úspěch",JOptionPane.INFORMATION_MESSAGE);
                    shoppingCart.addItem(item);
                }
                catch (NumberFormatException err)
                {
                    JOptionPane.showMessageDialog(this,"Číslo musí být zadáno s desetinnou tečkou","CHYBA",JOptionPane.ERROR_MESSAGE);
                }

            }
            else {
                JOptionPane.showMessageDialog(this,"Musí být zadán název","CHYBA",JOptionPane.ERROR_MESSAGE);
            }




            shoppingCartTableModel.fireTableDataChanged();
            updatePrice();

        } else if (actionEvent.getSource() == btnInputDelete){


        }

    }


}
