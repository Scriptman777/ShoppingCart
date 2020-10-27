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

    //MenuBar
        createMenuBar();


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
    lblTotalPrice = new JLabel();
    panelFooter.add(lblTotalPrice,BorderLayout.WEST);

    //ADD PANELS
    panelMain.add(panelInputs, BorderLayout.NORTH);
    panelMain.add(panelTable, BorderLayout.CENTER);
    panelMain.add(panelFooter, BorderLayout.SOUTH);


    add(panelMain);

    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();


        JMenu fileMenu = new JMenu("Soubor");
        fileMenu.add(new AbstractAction("Nový nákupní seznam") {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });
        fileMenu.add(new AbstractAction("Otevřít") {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });
        fileMenu.add(new AbstractAction("Uložit") {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });
        menuBar.add(fileMenu);

        JMenu aboutMenu = new JMenu("O programu");
        menuBar.add(aboutMenu);




        setJMenuBar(menuBar);
    }

    public void updatePrice()  {
        lblTotalPrice.setText("Celková cena: " + String.format("%.2f",shoppingCart.getTotalPrice())  +  " Kč");
    }



    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == btnInputAdd) {
            //TODO: Validovat věci
            addToCart();

        } else if (actionEvent.getSource() == btnInputDelete){


        }

    }

    public void addToCart() {
        if (!txtInputName.getText().isBlank()) {
            try {
                ShoppingCartItem item = new ShoppingCartItem(txtInputName.getText().trim(),Double.parseDouble(txtPrice.getText().replace(',','.')) ,(int)spNumber.getValue());
                JOptionPane.showMessageDialog(this,"Přidáno","Úspěch",JOptionPane.INFORMATION_MESSAGE);
                shoppingCart.addItem(item);
            }
            catch (NumberFormatException err)
            {
                JOptionPane.showMessageDialog(this,"Počet kusů nebo cena není zadána správně","CHYBA",JOptionPane.ERROR_MESSAGE);
            }

        }
        else {
            JOptionPane.showMessageDialog(this,"Musí být zadán název","CHYBA",JOptionPane.ERROR_MESSAGE);
        }
        shoppingCartTableModel.fireTableDataChanged();
        updatePrice();
    }


}
