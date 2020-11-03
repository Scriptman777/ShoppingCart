package uhk.fim.gui;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import uhk.fim.model.ShoppingCart;
import uhk.fim.model.ShoppingCartItem;

import javax.swing.*;
import javax.swing.border.Border;
import javax.xml.parsers.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

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
            //TODO: LOAD FILE HERE
            }
        });
        fileMenu.add(new AbstractAction("Uložit") {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                saveFileCsv();

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

    //Tohle tu teoreticky nemá být

    private void saveFileCsv() {
        JFileChooser jeff = new JFileChooser();
        if (jeff.showSaveDialog(this) == JFileChooser.APPROVE_OPTION){
            String fileName = jeff.getSelectedFile().getAbsolutePath();

            try {
                BufferedWriter bfw = new BufferedWriter(new FileWriter(fileName));

                for (ShoppingCartItem item: shoppingCart.getItems()) {

                    bfw.write(item.getName()+";"+item.getPricePerPiece()+";"+item.getPieces());
                    bfw.newLine();


                }
                bfw.close();

            } catch (IOException e) {
                e.printStackTrace();
            }


        }


    }
/*
    private void loadFileXmlSax() {
        try {
            //TODO complete
            String pathname = "TODO";
            CharArrayWriter content = new CharArrayWriter();
            SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
            parser.parse(new File(pathname),new DefaultHandler()
            {
                @Override
                public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                    //System.out.println("Start: " + qName);
                    content.reset();
                }

                @Override
                public void endElement(String uri, String localName, String qName) throws SAXException {
                   // System.out.println("End: " + qName);

                }

                @Override
                public void characters(char[] ch, int start, int length) throws SAXException {
                    super.characters(ch, start, length);
                    content.write(ch,start,length);
                }
            });






        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void loadFileDom(){

        try {
            String pathname = "TODO";


            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(new File(pathname));


            Node root = doc.getFirstChild();
            short nodeType = root.getNodeType();

            if (root.hasChildNodes()){
                NodeList list = root.getChildNodes();
                for (int i = 0; i < list.getLength(); i++){
                    Node nextNode = list.item(i);

                }

            }




        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

*/




}
