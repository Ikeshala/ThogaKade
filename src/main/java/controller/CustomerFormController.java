package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import db.DBConnection;
import dto.CustomersDto;
import dto.tm.CustomerTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import model.CustomerModel;
import model.impl.CustomerModelImpl;

import java.io.IOException;
import java.sql.*;
import java.util.List;

public class CustomerFormController {

    @FXML
    private ImageView customerPane;

    @FXML
    private JFXButton btnBack;

    @FXML
    private JFXTextField txtCustomerID;

    @FXML
    private JFXTextField txtCustomerName;

    @FXML
    private JFXTextField txtCustomerAddress;

    @FXML
    private JFXTextField txtCustomerSalary;

    @FXML
    private JFXButton btnRefresh;

    @FXML
    private JFXTextField txtSearchCustomer;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXTreeTableView<CustomerTm> tblCustomers;

    @FXML
    private TreeTableColumn colCustomerID;

    @FXML
    private TreeTableColumn colCustomerName;

    @FXML
    private TreeTableColumn colCustomerAddress;

    @FXML
    private TreeTableColumn colCustomerSalary;

    @FXML
    private TreeTableColumn colOption;
    private CustomerModel customerModel = new CustomerModelImpl();

    public void initialize(){
        colCustomerID.setCellValueFactory(new TreeItemPropertyValueFactory<>("id"));
        colCustomerName.setCellValueFactory(new TreeItemPropertyValueFactory<>("name"));
        colCustomerAddress.setCellValueFactory(new TreeItemPropertyValueFactory<>("address"));
        colCustomerSalary.setCellValueFactory(new TreeItemPropertyValueFactory<>("salary"));
        colOption.setCellValueFactory(new TreeItemPropertyValueFactory<>("btn"));
        loadCustomersTable();
    }
    private void loadCustomersTable() {
        ObservableList<CustomerTm> tmList = FXCollections.observableArrayList();

        try {
            List<CustomersDto> dtoList = customerModel.allCustomers();

            for (CustomersDto dto : dtoList) {
                JFXButton button = new JFXButton("DELETE");
                button.setFont(Font.font("System", FontWeight.BOLD, 13));
                button.setButtonType(JFXButton.ButtonType.RAISED);
                button.setBlendMode(BlendMode.EXCLUSION);
                button.setTextAlignment(TextAlignment.CENTER);
                button.setTextFill(Color.WHITE);
                button.setStyle("-fx-border-color: #A9A9A9; -fx-border-radius: 5; -fx-background-color:  #B5592A;");

                CustomerTm customerTm = new CustomerTm(
                        dto.getId(),
                        dto.getName(),
                        dto.getAddress(),
                        dto.getSalary(),
                        button
                );

                button.setOnAction(actionEvent -> {
                    deleteCustomer(customerTm.getId());
                });

                tmList.add(customerTm);
            }
            TreeItem<CustomerTm> treeItem = new RecursiveTreeItem<>(tmList, RecursiveTreeObject::getChildren);
            tblCustomers.setRoot(treeItem);
            tblCustomers.setShowRoot(false);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void deleteCustomer(String id) {
        String sql = "DELETE FROM customer WHERE id=?";

        try {
            PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
            pstm.setString(1, id);
            int result = pstm.executeUpdate();

            if (result > 0) {
                new Alert(Alert.AlertType.INFORMATION, "Item Deleted!").show();
                loadCustomersTable();
            } else {
                new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void BackButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) customerPane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/DashboardForm.fxml"))));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void RefreshButtonOnAction(ActionEvent event) {

    }

    @FXML
    void SaveButtonOnAction(ActionEvent event) {
        if (txtCustomerID.getText().isEmpty() || txtCustomerName.getText().isEmpty() ||
                txtCustomerAddress.getText().isEmpty() || txtCustomerSalary.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please fill in all fields!").show();
            return;
        }

        try {
            CustomersDto customersDto = new CustomersDto(
                    txtCustomerID.getText(),
                    txtCustomerName.getText(),
                    txtCustomerAddress.getText(),
                    Double.parseDouble(txtCustomerSalary.getText())
            );

            boolean isSaved = customerModel.saveCustomer(customersDto);

            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Customer Saved!").show();
                loadCustomersTable();
                clearFields();
            }

        } catch (SQLIntegrityConstraintViolationException ex) {
            new Alert(Alert.AlertType.ERROR, "Duplicate Entry!").show();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void clearFields() {
        tblCustomers.refresh();

        txtCustomerID.clear();
        txtCustomerName.clear();
        txtCustomerAddress.clear();
        txtCustomerSalary.clear();

        txtCustomerID.setEditable(true);
    }

    @FXML
    void UpdateButtonOnAction(ActionEvent event) {

    }

}
