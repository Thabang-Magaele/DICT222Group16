package application;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;

import java.io.File;

public class fuel_controller {

    @FXML
    private ComboBox<String> carComboBox;
    @FXML
    private Label capacityLabel;
    @FXML
    private Label priceLabel;

    private static final double FUEL_PRICE_PER_LITRE = 21.51;
    @FXML
    public void initialize() {
        loadCarModels();
    }

    private void loadCarModels() {
        try {
            File xmlFile = new File("cars.xml");
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xmlFile);
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("Car");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element carElement = (Element) nodeList.item(i);
                String make = carElement.getElementsByTagName("Make").item(0).getTextContent();
                String model = carElement.getElementsByTagName("Model").item(0).getTextContent();
                carComboBox.getItems().add(make + " " + model);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCarSelection() {
        String selectedCar = carComboBox.getValue();
        if (selectedCar != null) {
            try {
                File xmlFile = new File("cars.xml");
                Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xmlFile);
                doc.getDocumentElement().normalize();

                NodeList nodeList = doc.getElementsByTagName("Car");
                for (int i = 0; i < nodeList.getLength(); i++) {
                    Element carElement = (Element) nodeList.item(i);
                    String make = carElement.getElementsByTagName("Make").item(0).getTextContent();
                    String model = carElement.getElementsByTagName("Model").item(0).getTextContent();

                    if ((make + " " + model).equals(selectedCar)) {
                        int capacity = Integer.parseInt(carElement.getElementsByTagName("FuelTankCapacityLitres").item(0).getTextContent());
                        double fullTankPrice = capacity * FUEL_PRICE_PER_LITRE;

                        capacityLabel.setText(capacity + " litres");
                        priceLabel.setText(String.format("R %.2f", fullTankPrice));
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
