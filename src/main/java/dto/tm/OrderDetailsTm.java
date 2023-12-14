package dto.tm;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import lombok.*;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailsTm extends RecursiveTreeObject<OrderDetailsTm> {
    private String itemCode;
    private String description;
    private int quantity;
    private double amount;
}
