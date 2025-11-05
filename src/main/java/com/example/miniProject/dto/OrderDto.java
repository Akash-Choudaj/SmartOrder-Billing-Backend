package com.example.miniProject.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.example.miniProject.Entity.OrderItem;
import com.example.miniProject.Entity.Product;

public class OrderDto {

    private List<OrderItemDto> items;

    public List<OrderItemDto> getItems() { return items; }
    public void setItems(List<OrderItemDto> items) { this.items = items; }

    public List<OrderItem> toOrderItemList() {
        return items.stream().map(dto -> {
            OrderItem item = new OrderItem();
            item.setQuantity(dto.getQuantity());

            Product product = new Product();
            product.setId(dto.getProductId());
            item.setProduct(product);
            return item;
        }).collect(Collectors.toList());
    }
}
