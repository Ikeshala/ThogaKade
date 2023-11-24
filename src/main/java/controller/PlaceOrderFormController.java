package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class PlaceOrderFormController {

    public AnchorPane placeOrderPane;
    @FXML
    private ImageView itemPane;

    @FXML
    private JFXButton btnBack;

    @FXML
    private JFXTextField txtItemDescription;

    @FXML
    private JFXTextField txtUnitPrice;

    @FXML
    private JFXTextField txtBuyingQuantity;

    @FXML
    private JFXButton btnPlaceOrder;

    @FXML
    private JFXButton btnAddToCart;

    @FXML
    private JFXTreeTableView<?> tblItems;

    @FXML
    private TreeTableColumn<?, ?> colItemCode;

    @FXML
    private TreeTableColumn<?, ?> colItemDescription;

    @FXML
    private TreeTableColumn<?, ?> colUnitPrice;

    @FXML
    private TreeTableColumn<?, ?> colQuantity;

    @FXML
    private TreeTableColumn<?, ?> colOption;

    @FXML
    private JFXTextField txtCustomerName;

    @FXML
    private JFXComboBox<?> cmbCustomerID;

    @FXML
    private JFXComboBox<?> cmbItemCode;

    @FXML
    void AddToCartButtonOnAction(ActionEvent event) {

    }

    @FXML
    void BackButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) placeOrderPane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/DashboardForm.fxml"))));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void PlaceOrderButtonOnAction(ActionEvent event) {

    }

}
