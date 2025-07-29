package com.katarzyna.novikova.expense.tracker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.katarzyna.novikova.expense.tracker.dto.ExpenseDTO;
import com.katarzyna.novikova.expense.tracker.service.ExpenseServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ExpensesController.class)
class ExpensesControllerTest {

    @MockitoBean
    private ExpenseServiceImpl expenseService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Successfully create new expense and add it to expenseService")
    void createExpenseSuccessfullyAddExpenseToExpenseService() throws Exception {
        // Given
        ExpenseDTO expenseDTO = new ExpenseDTO();
        expenseDTO.setName("T-shirts");
        expenseDTO.setAmount(199.9);
        expenseDTO.setCategory("Clothes");
        String requestBody = objectMapper.writeValueAsString(expenseDTO);

        long id = 1;
        when(expenseService.add(expenseDTO)).thenReturn(id);

        // When Then
        mockMvc.perform(post("/expenses").contentType(MediaType.APPLICATION_JSON).content(requestBody)).andDo(print())
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.header().string("Location", "http://localhost/expenses/1"));
    }

    @Test
    @DisplayName("Successfully get existing expense by id")
    void getExpenseSuccessfullyForExistingExpense() throws Exception {

        // Given
        ExpenseDTO expenseDTO = new ExpenseDTO();
        expenseDTO.setName("T-shirts");
        expenseDTO.setAmount(199.9);
        expenseDTO.setCategory("Clothes");

        long id = 1;
        when(expenseService.get(1)).thenReturn(Optional.of(expenseDTO));

        // When Then
        mockMvc.perform(get("/expenses/{id}", id)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(expenseDTO.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.amount").value(expenseDTO.getAmount()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.category").value(expenseDTO.getCategory()));
    }

    @Test
    void getAllExpenses() {
    }

    @Test
    void deleteExpense() {
    }

    @Test
    void updateExpense() {
    }
}