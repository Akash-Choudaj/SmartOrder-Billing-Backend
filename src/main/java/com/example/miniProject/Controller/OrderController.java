package com.example.miniProject.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.example.miniProject.Entity.Order;
import com.example.miniProject.Entity.OrderItem;
import com.example.miniProject.Service.OrderService;
import com.example.miniProject.repository.UserRepository;
import com.example.miniProject.Entity.User;
import com.example.miniProject.dto.OrderDto;
import org.springframework.http.HttpStatus;
import java.security.Principal;
import java.util.List;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderServ;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Order> createOrder(@Valid @RequestBody OrderDto orderRequest, Principal principal) {

        Long userId = getUserIdFromPrincipal(principal);

        List<OrderItem> items = orderRequest.toOrderItemList();

        Order order = orderServ.createOrder(userId, items);

        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<Order> getOrder(@PathVariable Long id) {
        Order order = orderServ.getOrder(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return ResponseEntity.ok(order);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderServ.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    private Long getUserIdFromPrincipal(Principal principal) {
        String username = principal.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return user.getId();
    }
}
