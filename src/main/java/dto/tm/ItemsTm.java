package dto.tm;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemsTm extends RecursiveTreeObject<ItemsTm> {
    private String code;
    private String description;
    private double unitPrice;
    private int quantity;
    private JFXButton btn;

    public ItemsTm(String code, String description, double unitPrice, int quantity) {
        this.code = code;
        this.description = description;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }
}
