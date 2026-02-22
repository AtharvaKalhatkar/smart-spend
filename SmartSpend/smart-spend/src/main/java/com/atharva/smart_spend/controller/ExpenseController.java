package com.atharva.smart_spend.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.*;
import com.atharva.smart_spend.entity.Expense;
import com.atharva.smart_spend.repository.ExpenseRepository;
import com.atharva.smart_spend.service.ExpenseAiService;

@RestController
@RequestMapping("/api/expenses")
@CrossOrigin("*") 
public class ExpenseController {

    private final ExpenseRepository repository;
    private final ExpenseAiService aiService;

    public ExpenseController(ExpenseRepository repository, ExpenseAiService aiService) {
        this.repository = repository;
        this.aiService = aiService;
    }

    // 1. Add Expense (AI-Powered)
    @PostMapping("/add")
    public Expense addExpense(@RequestParam String description, @RequestParam Double amount) {
        String aiCategory = aiService.categorizeExpense(description);

        Expense expense = new Expense();
        expense.setDescription(description);
        expense.setAmount(amount);
        expense.setCategory(aiCategory);
        expense.setDate(LocalDate.now());

        return repository.save(expense);
    }

    // 2. Get All Expenses (Full List)
    @GetMapping("/all")
    public List<Expense> getAllExpenses() {
        return repository.findAll();
    }

    // 3. Get Stats for Dashboard (Filtered by Week/Month)
    @GetMapping("/stats")
    public Map<String, Object> getStats(@RequestParam String range) {
        LocalDate end = LocalDate.now();
        LocalDate start = switch (range) {
            case "week" -> end.minusWeeks(1);
            case "month" -> end.minusMonths(1);
            default -> end.minusDays(7); 
        };

        // Fetch filtered data
        List<Expense> expenses = repository.findByDateBetween(start, end);

        // Math for Dashboard Cards
        double totalSpent = expenses.stream()
                .mapToDouble(Expense::getAmount)
                .sum();

        // Find the top category
        String topCategory = expenses.stream()
                .collect(Collectors.groupingBy(Expense::getCategory, Collectors.summingDouble(Expense::getAmount)))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("None");

        // The 'list' key here fixes your empty table issue
        return Map.of(
            "totalSpent", totalSpent,
            "topCategory", topCategory,
            "itemCount", expenses.size(),
            "list", expenses 
        );
    }
    // 4. Flexible Filter Endpoint (Search + Date Range)
@GetMapping("/filter")
public Map<String, Object> getFilteredStats(
        @RequestParam(required = false) String startDate, 
        @RequestParam(required = false) String endDate,
        @RequestParam(required = false) String category) {

    // 1. Determine Dates (Default to current month if empty)
    LocalDate end = (endDate != null && !endDate.isEmpty()) ? LocalDate.parse(endDate) : LocalDate.now();
    LocalDate start = (startDate != null && !startDate.isEmpty()) ? LocalDate.parse(startDate) : end.withDayOfMonth(1);

    // 2. Fetch Data from DB
    List<Expense> expenses = repository.findByDateBetween(start, end);

    // 3. Filter by Category (if user typed one)
    if (category != null && !category.isEmpty()) {
        expenses = expenses.stream()
                .filter(e -> e.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }

    // 4. Calculate Stats
    double totalSpent = expenses.stream().mapToDouble(Expense::getAmount).sum();
    
    // Top Category Calculation
    String topCategory = expenses.isEmpty() ? "---" : expenses.stream()
            .collect(Collectors.groupingBy(Expense::getCategory, Collectors.summingDouble(Expense::getAmount)))
            .entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .orElse("None");

    return Map.of(
        "totalSpent", totalSpent,
        "topCategory", topCategory,
        "itemCount", expenses.size(),
        "list", expenses
    );
}
// 4. NEW: Delete Endpoint
    @DeleteMapping("/delete/{id}")
    public void deleteExpense(@PathVariable Long id) {
        repository.deleteById(id);
    }
}