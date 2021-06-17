package dev.mouhieddine.finance.service.dto;

public class RevenueSourceDto {

    private String category;
    private String color;
    private Double amount = 0D;

    public RevenueSourceDto(String category, String color, Double amount) {
        this.category = category;
        this.color = color;
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
