package uhk.fim.gui;

import com.google.gson.Gson;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentFactory;
import org.dom4j.io.SAXReader;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import uhk.fim.model.Saver;
import uhk.fim.model.ShoppingCart;
import uhk.fim.model.ShoppingCartItem;

import javax.swing.*;
import javax.swing.border.Border;
import javax.xml.parsers.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.net.URL;
import java.nio.file.InvalidPathException;

public class MainFrame extends JFrame implements ActionListener{
    JButton btnInputAdd;
    JButton btnInputDelete;
    JTextField txtInputName;
    JTextField txtPrice;
    JSpinner spNumber;
    JLabel lblTotalPrice;
    JTable table;
    MainFrame mainFrame = this;

    ShoppingCart shoppingCart;
    ShoppingCartTableModel shoppingCartTableModel  = new ShoppingCartTableModel();


    public MainFrame(int wdth, int hght) {
        super("PRO2 - Shopping cart");
        setSize(wdth, hght);

        initGUI();
        shoppingCart = new ShoppingCart();
        shoppingCartTableModel.setCart(shoppingCart);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initData();
    }

    public void initData() {
        File file = new File("./src/main/resources/data.csv");
        ShoppingCart newCart = new ShoppingCart();
        try {
            newCart = Saver.load(file.getAbsolutePath());
        }
        catch (Exception err){

        }
        this.shoppingCart = newCart;
        this.shoppingCartTableModel.setCart(newCart);
        shoppingCartTableModel.fireTableDataChanged();
        updatePrice();
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
    table = new JTable();
    table.setModel(shoppingCartTableModel);
    table.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            updatePrice();
        }
    });
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
                newList();
            }
        });
        fileMenu.add(new AbstractAction("Otevřít") {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
            loadFile();
            }
        });
        fileMenu.add(new AbstractAction("Uložit") {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                saveInteral();

            }
        });
        fileMenu.add(new AbstractAction("Uložit jako") {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                saveFile();

            }
        });

        menuBar.add(fileMenu);

        JMenu aboutMenu = new JMenu("O programu");
        menuBar.add(aboutMenu);




        setJMenuBar(menuBar);
    }

    public void updatePrice()  {

        String labeltext = "";

        labeltext += "Celková cena: " + String.format("%.2f",shoppingCart.getTotalPrice())  +  " Kč";
        labeltext += " | ";
        labeltext += "Cena zakoupených : " + String.format("%.2f",shoppingCart.getBoughtPrice())  +  " Kč";
        labeltext += " | ";
        labeltext += "Rozdíl : " + String.format("%.2f",(shoppingCart.getTotalPrice()-shoppingCart.getBoughtPrice()))  +  " Kč";


        lblTotalPrice.setText(labeltext);
    }



    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == btnInputAdd) {
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
                displayError(err,UserAction.ADDING);
            }

        }
        else {
            JOptionPane.showMessageDialog(this,"Musí být zadán název","CHYBA",JOptionPane.ERROR_MESSAGE);
        }
        shoppingCartTableModel.fireTableDataChanged();
        updatePrice();
    }

    //Nový košík
    private void newList() {
        int answer = JOptionPane.showConfirmDialog(this,"Košík bude smazán, chcete pokračovat?");
        if (answer == 0) {
            ShoppingCart newCart = new ShoppingCart();
            this.shoppingCart = newCart;
            this.shoppingCartTableModel.setCart(newCart);
            shoppingCartTableModel.fireTableDataChanged();
            updatePrice();
        }
    }


    //Uložení
    private void saveFile() {
        JFileChooser jeff = new JFileChooser();
        if (jeff.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            String fileName = jeff.getSelectedFile().getAbsolutePath();
            try {
                Saver.save(shoppingCart,fileName);
            }
            catch (Exception err){
                displayError(err,UserAction.SAVING);
            }

        }
    }

    //Uložení do interního csv
    public void saveInteral() {
        File file = new File("./src/main/resources/data.csv");
        try {
            Saver.save(shoppingCart,file.getAbsolutePath());
        }
        catch (Exception err){
            displayError(err,UserAction.SAVING);
        }

    }

    //Načtení
    private void loadFile() {
        JFileChooser jeff = new JFileChooser();
        if (jeff.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            ShoppingCart newCart = new ShoppingCart();
            String fileName = jeff.getSelectedFile().getAbsolutePath();
            try {
               newCart = Saver.load(fileName);
            }
            catch (Exception err){
                displayError(err,UserAction.LOADING);
            }
            this.shoppingCart = newCart;
            this.shoppingCartTableModel.setCart(newCart);
            shoppingCartTableModel.fireTableDataChanged();
            updatePrice();
        }
    }

    private void displayError(Exception err,int context) {

        switch (context) {
            case UserAction.SAVING:
                JOptionPane.showMessageDialog(this,"Při ukládání došlo k chybě " + err.getMessage(),"Chyba",JOptionPane.ERROR_MESSAGE);
                break;
            case UserAction.LOADING:
                JOptionPane.showMessageDialog(this,"Při načítaní došlo k chybě " + err.getMessage(),"Chyba",JOptionPane.ERROR_MESSAGE);
                break;
            case UserAction.ADDING:
                JOptionPane.showMessageDialog(this,"Počet kusů nebo cena není zadána správně " + err.getMessage(),"Chyba",JOptionPane.ERROR_MESSAGE);
                break;

        }



    }






}
