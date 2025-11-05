package com.example.miniProject.dto;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;
import java.util.stream.Collectors;

import com.example.miniProject.Entity.OrderItem;

public class OrderRequestDto {

    @NotEmpty
    private List<OrderItemDto> items;

    // Getters and setters

    public List<OrderItemDto> getItems() {
        return items;
    }

    public void setItems(List<OrderItemDto> items) {
        this.items = items;
    }

    // Convert DTO list to OrderItem entity list
    public List<OrderItem> toOrderItemList() {
        return items.stream().map(itemDto -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setQuantity(itemDto.getQuantity());

            // Set product with only id to fetch later from DB
            com.example.miniProject.Entity.Product product = new com.example.miniProject.Entity.Product();
            product.setId(itemDto.getProductId());
            orderItem.setProduct(product);

            return orderItem;
        }).collect(Collectors.toList());
    }
}
