package com.dto;
import lombok.Data;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.*;

@Data
public class SocksIncomeOutcomeDto  {
    @NotBlank(message = "provide color")
    String color;

    @Min(0)
    @Max(100)
    @NotNull(message = "provide cottonPart")
    Integer cottonPart;

    @Min(1)
    @NotNull(message = "provide quantity")
    int quantity;

}
