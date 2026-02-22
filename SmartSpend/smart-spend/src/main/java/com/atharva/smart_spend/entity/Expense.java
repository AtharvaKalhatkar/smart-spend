package com.atharva.smart_spend.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "expenses")
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description; // "Uber to Airport"
    private Double amount;      // 500.00
    private String category;    // AI will fill this! (e.g., "TRAVEL")
    private LocalDate date;

    // Getters and Setters (You can generate these via Right Click > Generate > Getters and Setters)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }
    
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
}