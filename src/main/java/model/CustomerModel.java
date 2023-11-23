package model;

import dto.CustomersDto;

import java.sql.SQLException;
import java.util.List;

public interface CustomerModel {
    boolean saveCustomer(CustomersDto dto);
    boolean updateCustomer(CustomersDto dto);
    boolean searchCustomer(String id);
    boolean deleteCustomer(String id);
    List<CustomersDto> allCustomers() throws SQLException, ClassNotFoundException;
}
