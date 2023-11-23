package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import db.DBConnection;
import dto.ItemsDto;
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

import java.io.IOException;
import java.sql.*;

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
        String sql = "SELECT * FROM item";

        try {
            Statement statement = DBConnection.getInstance().getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()){
                JFXButton button = new JFXButton("DELETE");
                button.setFont(Font.font("System", FontWeight.BOLD, 13));
                button.setButtonType(JFXButton.ButtonType.RAISED);
                button.setBlendMode(BlendMode.EXCLUSION);
                button.setTextAlignment(TextAlignment.CENTER);
                button.setTextFill(Color.WHITE);
                button.setStyle("-fx-border-color: #A9A9A9; -fx-border-radius: 5; -fx-background-color: #45474B;");
                ItemsTm itemsTm = new ItemsTm(resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getDouble(3),
                        resultSet.getInt(4),
                        button
                );

                button.setOnAction(actionEvent -> {
                   // deleteItem(itemsTm.getCode());
                });

                tmList.add(itemsTm);
            }
            TreeItem<ItemsTm> treeItem = new RecursiveTreeItem<ItemsTm>(tmList, RecursiveTreeObject::getChildren);
            tblItems.setRoot(treeItem);
            tblItems.setShowRoot(false);

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
        ItemsDto itemDto = new ItemsDto(txtItemCode.getText(),
                txtItemDescription.getText(),
                Double.parseDouble(txtUnitPrice.getText()),
                Integer.parseInt(txtQuantityOnHand.getText())
        );
        String sql = "INSERT INTO item VALUES(?,?,?,?)";

        try {
            PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
            pstm.setString(1,itemDto.getCode());
            pstm.setString(2,itemDto.getDescription());
            pstm.setDouble(3,itemDto.getUnitPrice());
            pstm.setInt(4,itemDto.getQuantity());
            int result = pstm.executeUpdate();
            if (result>0){
                new Alert(Alert.AlertType.INFORMATION,"Item Saved!").show();
                //loadItemTable();
            }

        } catch (SQLIntegrityConstraintViolationException ex){
            new Alert(Alert.AlertType.ERROR,"Duplicate Entry!").show();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void UpdateButtonOnAction(ActionEvent event) {

    }

}
