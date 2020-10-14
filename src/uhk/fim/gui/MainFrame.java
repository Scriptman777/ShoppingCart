package uhk.fim.gui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame implements ActionListener {
    JButton btnInputAdd;
    JButton btnInputDelete;
    JTextField txtInputName;
    JTextField txtNumber;
    JTextField txtPrice;


    public MainFrame(int wdth, int hght) {
        super("PRO2 - Shopping cart");
        setSize(wdth, hght);
        initFrame();
        initGUI();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void initFrame() {

    }

    public void initGUI() {
    JPanel panelMain = new JPanel(new BorderLayout());

    JPanel panelInputs = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JPanel panelTable = new JPanel();
    JPanel panelFooter = new JPanel();

    JLabel lblInputName = new JLabel("Název: ");
    txtInputName = new JTextField("",15);
    JLabel lblNumber = new JLabel("Počet kusů: ");
    txtNumber = new JTextField("",5);
    JLabel lblPrice = new JLabel("Cena za kus: ");
    txtPrice = new JTextField("",5);

    btnInputAdd = new JButton("Přidat");
    btnInputAdd.addActionListener(this);
    btnInputDelete = new JButton("Smazat");
    btnInputDelete.addActionListener(this);

    panelInputs.add(lblInputName);
    panelInputs.add(txtInputName);
    panelInputs.add(lblNumber);
    panelInputs.add(txtNumber);
    panelInputs.add(lblPrice);
    panelInputs.add(txtPrice);
    panelInputs.add(btnInputAdd);
    panelInputs.add(btnInputDelete);

    panelMain.add(panelInputs, BorderLayout.NORTH);
    panelMain.add(panelTable, BorderLayout.CENTER);
    panelMain.add(panelFooter, BorderLayout.SOUTH);


    add(panelMain);

    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == btnInputAdd) {
            //TODO: print out "přidán produkt: Název, cena, počet"
            System.out.println("Přidán produkt: " + txtInputName.getText() + ", " + txtPrice.getText() + " Kč/kus, " + txtNumber.getText() + " kusů");


        } else if (actionEvent.getSource() == btnInputDelete){


        }

    }
}
