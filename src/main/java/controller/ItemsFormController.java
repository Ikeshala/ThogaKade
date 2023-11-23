package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import db.DBConnection;
import dto.CustomersDto;
import dto.ItemsDto;
import dto.tm.CustomerTm;
import dto.tm.ItemsTm;
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
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import dto.tm.ItemsTm;
import model.ItemModel;
import model.impl.ItemModelImpl;

import java.io.IOException;
import java.sql.*;
import java.util.List;

public class ItemsFormController {

    @FXML
    private ImageView itemPane;

    @FXML
    private JFXButton btnBack;

    @FXML
    private JFXTextField txtItemCode;

    @FXML
    private JFXTextField txtItemDescription;

    @FXML
    private JFXTextField txtUnitPrice;

    @FXML
    private JFXTextField txtQuantityOnHand;

    @FXML
    private JFXButton btnRefresh;

    @FXML
    private JFXTextField txtSearchItem;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXTreeTableView<ItemsTm> tblItems;

    @FXML
    private TreeTableColumn colItemCode;

    @FXML
    private TreeTableColumn colItemDescription;

    @FXML
    private TreeTableColumn colUnitPrice;

    @FXML
    private TreeTableColumn colQuantity;

    @FXML
    private TreeTableColumn colOption;
    private ItemModel itemModel = new ItemModelImpl();

    public void initialize(){
        colItemCode.setCellValueFactory(new TreeItemPropertyValueFactory<>("code"));
        colItemDescription.setCellValueFactory(new TreeItemPropertyValueFactory<>("description"));
        colUnitPrice.setCellValueFactory(new TreeItemPropertyValueFactory<>("unitPrice"));
        colQuantity.setCellValueFactory(new TreeItemPropertyValueFactory<>("quantity"));
        colOption.setCellValueFactory(new TreeItemPropertyValueFactory<>("btn"));
        loadItemsTable();
    }

    private void loadItemsTable() {
        ObservableList<ItemsTm> tmList = FXCollections.observableArrayList();

        try {
            List<ItemsDto> dtoList = itemModel.allItems();

            for (ItemsDto dto : dtoList) {
                JFXButton button = new JFXButton("DELETE");
                button.setFont(Font.font("System", FontWeight.BOLD, 13));
                button.setButtonType(JFXButton.ButtonType.RAISED);
                button.setBlendMode(BlendMode.EXCLUSION);
                button.setTextAlignment(TextAlignment.CENTER);
                button.setTextFill(Color.WHITE);
                button.setStyle("-fx-border-color: #A9A9A9; -fx-border-radius: 5; -fx-background-color:   #45474B;");

                ItemsTm itemsTm = new ItemsTm(
                        dto.getCode(),
                        dto.getDescription(),
                        dto.getUnitPrice(),
                        dto.getQuantity(),
                        button
                );

                button.setOnAction(actionEvent -> {
                    deleteItem(itemsTm.getCode());
                });

                tmList.add(itemsTm);
            }
            TreeItem<ItemsTm> treeItem = new RecursiveTreeItem<>(tmList, RecursiveTreeObject::getChildren);
            tblItems.setRoot(treeItem);
            tblItems.setShowRoot(false);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void deleteItem(String code) {
        String sql = "DELETE FROM item WHERE code=?";

        try {
            PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
            pstm.setString(1, code);
            int result = pstm.executeUpdate();

            if (result > 0) {
                new Alert(Alert.AlertType.INFORMATION, "Item Deleted!").show();
                loadItemsTable();
            } else {
                new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void BackButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) itemPane.getScene().getWindow();
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
        if (txtItemCode.getText().isEmpty() || txtItemDescription.getText().isEmpty() ||
                txtUnitPrice.getText().isEmpty() || txtQuantityOnHand.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please fill in all fields!").show();
            return;
        }

        try {
            ItemsDto itemsDto = new ItemsDto(
                    txtItemCode.getText(),
                    txtItemDescription.getText(),
                    Double.parseDouble(txtUnitPrice.getText()),
                    Integer.parseInt(txtQuantityOnHand.getText())
            );

            boolean isSaved = itemModel.saveItem(itemsDto);

            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Item Saved!").show();
                loadItemsTable();
                clearFields();
            }

        } catch (SQLIntegrityConstraintViolationException ex) {
            new Alert(Alert.AlertType.ERROR, "Duplicate Entry!").show();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void clearFields() {
        tblItems.refresh();

        txtItemCode.clear();
        txtItemDescription.clear();
        txtUnitPrice.clear();
        txtQuantityOnHand.clear();

        txtItemCode.setEditable(true);
    }

    @FXML
    void UpdateButtonOnAction(ActionEvent event) {

    }

}
