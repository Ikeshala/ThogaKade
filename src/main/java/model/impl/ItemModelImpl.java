package model.impl;

import db.DBConnection;
import dto.CustomersDto;
import dto.ItemsDto;
import model.ItemModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemModelImpl implements ItemModel {
    @Override
    public boolean saveItem(ItemsDto dto) {
        return false;
    }

    @Override
    public boolean updateItem(ItemsDto dto) {
        return false;
    }

    @Override
    public boolean searchItem(String code) {
        return false;
    }

    @Override
    public boolean deleteItem(String code) {
        return false;
    }

    @Override
    public List<ItemsDto> allItems() throws SQLException, ClassNotFoundException {
        List<ItemsDto> list = new ArrayList<>();
        String sql = "SELECT * FROM item";

        PreparedStatement ptsm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet resultSet = ptsm.executeQuery();

            while (resultSet.next()) {
                list.add(new ItemsDto(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getDouble(3),
                        resultSet.getInt(4)
                ));
            }

        return list;
    }
}
