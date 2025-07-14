package com.katarzyna.novikova.expense.tracker.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ExpenseDTO {
    // holds the data converted from RequestBody - JSON

    private String name;
    private double amount;
    private String category;

}
