package model.tm;

import com.jfoenix.controls.JFXButton;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemsTm {
    private String code;
    private String description;
    private double unitPrice;
    private int quantity;
    private JFXButton btn;
}
