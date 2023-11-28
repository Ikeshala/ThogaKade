package model;

import dto.tm.OrderDto;

public interface OrderModel {
    boolean saveOrder(OrderDto dto);
}
