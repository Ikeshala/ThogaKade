package controller;

import com.google.protobuf.StringValue;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import dto.CustomersDto;
import dto.ItemsDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.CustomerModel;
import model.ItemModel;
import model.impl.CustomerModelImpl;
import model.impl.ItemModelImpl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlaceOrderFormController {

    @FXML
    private AnchorPane placeOrderPane;

    @FXML
    private ImageView itemPane;

    @FXML
    private JFXButton btnBack;

    @FXML
    private JFXComboBox<?> cmbCustomerID;

    @FXML
    private JFXTextField txtCustomerName;

    @FXML
    private JFXComboBox<?> cmbItemCode;

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
    private Label txtTotal;

    @FXML
    private Label txtOrderId;
    private List<CustomersDto> customers;
    private List<ItemsDto> items;
    private CustomerModel customerModel = new CustomerModelImpl();
    private ItemModel itemModel = new ItemModelImpl();
    public void initialize(){
        loadCustomerIds();
        loadItemCodes();

        cmbCustomerID.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, customerId) ->
                {
                    for (CustomersDto dto:customers) {
                        if (dto.getId().equals(customerId)){
                            txtCustomerName.setText(dto.getName());
                        }

                    }
                }
        );

        cmbItemCode.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, itemCode) ->
                {
                    for (ItemsDto dto:items) {
                        if (dto.getCode().equals(itemCode)){
                            txtItemDescription.setText(dto.getDescription());
                            txtUnitPrice.setText(String.format("%.2f",dto.getUnitPrice()));
                        }

                    }
                }
        );
    }

    private void loadItemCodes() {
        try {
            items = itemModel.allItems();
            ObservableList list = FXCollections.observableArrayList();
            for (ItemsDto dto: items) {
                list.add(dto.getCode());
            }
            cmbItemCode.setItems(list);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadCustomerIds() {
        try {
            customers = customerModel.allCustomers();
            ObservableList list = FXCollections.observableArrayList();
            for (CustomersDto dto: customers) {
                list.add(dto.getId());
            }
            cmbCustomerID.setItems(list);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

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
