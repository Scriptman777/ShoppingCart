package uhk.fim.model;

import org.dom4j.*;
import org.dom4j.io.SAXReader;

import java.io.*;
import java.nio.file.InvalidPathException;
import java.util.Iterator;

public class Saver {

    public static void save(ShoppingCart shoppingCart, String fileName) throws Exception {
        int dotLocation = fileName.lastIndexOf('.');
        String extension = fileName.substring(dotLocation);

        if (extension.toLowerCase().equals(".csv")){
            saveCsv(shoppingCart,fileName);
        }
        else if (extension.toLowerCase().equals(".xml")){
            saveXml(shoppingCart,fileName);
        }
        else {
            throw new InvalidPathException(fileName,"Není CSV ani XML");
        }

    }

    public static ShoppingCart load(String fileName) throws Exception{
        int dotLocation = fileName.lastIndexOf('.');
        String extension = fileName.substring(dotLocation);

        if (extension.toLowerCase().equals(".csv")){
            return loadCsv(fileName);
        }
        if (extension.toLowerCase().equals(".xml")){
            return loadXml(fileName);
        }
        throw new InvalidPathException(fileName,"Není CSV ani XML");

    }

    public static ShoppingCart loadCsv(String fileName) {
        ShoppingCart outputCart = new ShoppingCart();
        try {
            BufferedReader csvReader = new BufferedReader(new FileReader(fileName));
            String itemRow;
            while ((itemRow = csvReader.readLine()) != null) {

                String[] itemLine = itemRow.split(";");
                ShoppingCartItem item = new ShoppingCartItem(itemLine[0],Double.parseDouble(itemLine[1]),Integer.parseInt(itemLine[2]),Boolean.parseBoolean(itemLine[3]));
                outputCart.addItem(item);

            }
            csvReader.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return outputCart;

    }

    public static void saveCsv(ShoppingCart shoppingCart, String fileName) {

            try {
                BufferedWriter bfw = new BufferedWriter(new FileWriter(fileName));

                for (ShoppingCartItem item: shoppingCart.getItems()) {
                    bfw.write(item.getName()+";"+item.getPricePerPiece()+";"+item.getPieces()+";"+item.isBought());
                    bfw.newLine();
                }
                bfw.close();

            } catch (IOException e) {
                e.printStackTrace();
            }


    }

    public static ShoppingCart loadXml(String fileName) {
        ShoppingCart outputCart = new ShoppingCart();
        DocumentFactory df = DocumentFactory.getInstance();
        SAXReader reader = new SAXReader(df);

        try {

            org.dom4j.Document doc = reader.read(new File(fileName));
            Element root = doc.getRootElement();
            for (Iterator<Element> it = root.elementIterator("Item"); it.hasNext();) {
                Element item = it.next();
                String name = item.attributeValue("Name");
                String PPP = item.attributeValue("PricePerPiece");
                String amount = item.attributeValue("Pieces");
                String bought = item.attributeValue("Bought");
                outputCart.addItem(new ShoppingCartItem(name,Double.parseDouble(PPP),Integer.parseInt(amount),Boolean.parseBoolean(bought)));
            }

        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return outputCart;

    }

    public static void saveXml(ShoppingCart shoppingCart, String fileName) {
        DocumentFactory factory = DocumentFactory.getInstance();
        Document doc = factory.createDocument();
        Element cart = doc.addElement("ShoppingCart");


        for (ShoppingCartItem item: shoppingCart.getItems()) {
            Element eleItem = cart.addElement("Item");
            eleItem.addAttribute("Name",item.getName());
            eleItem.addAttribute("PricePerPiece",Double.toString(item.getPricePerPiece()));
            eleItem.addAttribute("Pieces",Integer.toString(item.getPieces()));
            eleItem.addAttribute("Bought",Boolean.toString(item.isBought()));
        }
        try {
            FileWriter out = new FileWriter(fileName);
            doc.write(out);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
