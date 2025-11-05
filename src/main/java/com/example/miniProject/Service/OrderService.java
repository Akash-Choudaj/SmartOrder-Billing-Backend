package com.example.miniProject.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.miniProject.Entity.Order;
import com.example.miniProject.Entity.OrderItem;
import com.example.miniProject.Entity.Product;
import com.example.miniProject.Entity.User;
import com.example.miniProject.repository.OrderRepository;
import com.example.miniProject.repository.ProductRepository;
import com.example.miniProject.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private ProductRepository productRepo;

    @Transactional
    public Order createOrder(Long userId, List<OrderItem> items) {
        User user = userRepo.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));

        Order order = new Order();
        order.setUser(user);

        List<OrderItem> orderItems = new ArrayList<>();
        long total = 0;

        for (OrderItem item : items) {
            Product product = productRepo.findById(item.getProduct().getId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

            if (product.getStockQuantity() < item.getQuantity()) {
                throw new RuntimeException("Insufficient stock for product: " + product.getName());
            }

            product.setStockQuantity(product.getStockQuantity() - item.getQuantity());
            productRepo.save(product);

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(item.getQuantity());
            orderItem.setPrice(product.getPrice());  // assuming product price stored as long

            orderItems.add(orderItem);

            total += product.getPrice() * item.getQuantity();
        }

        order.setOrderItems(orderItems);
        order.setTotal(total);
        order.setStatus("PENDING");

        return orderRepo.save(order);
    }

    public Optional<Order> getOrder(Long orderId) {
        return orderRepo.findById(orderId);
    }

    public List<Order> getAllOrders() {
        return orderRepo.findAll();
    }
}
