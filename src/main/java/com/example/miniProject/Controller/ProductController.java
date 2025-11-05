package com.example.miniProject.Controller;

import com.example.miniProject.Entity.Product;

import com.example.miniProject.Service.ProductService;
import com.example.miniProject.dto.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;
@RestController
@RequestMapping("/api/products")
public class ProductController {

	@Autowired
	private ProductService productServ;

	@GetMapping
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public ResponseEntity<List<ProductDto>> getAllProducts() {
		List<ProductDto> products = productServ.getAllProducts()
				.stream().map(this::toDto).collect(Collectors.toList());
		return ResponseEntity.ok(products);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id, @RequestBody ProductDto productDto) {
		Product product = productServ.getProductById(id)
				.orElseThrow(() -> new RuntimeException("Product not found"));
		product.setName(productDto.getName());
		product.setPrice(productDto.getPrice());
		product.setStockQuantity(productDto.getStockQuantity());
		Product saved = productServ.saveProduct(product);
		return ResponseEntity.ok(toDto(saved));
	}

	@DeleteMapping("{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
		productServ.deleteProduct(id);
		return ResponseEntity.noContent().build();
	}

	private ProductDto toDto(Product product) {
		ProductDto dto = new ProductDto();
		dto.setId(product.getId());
		dto.setName(product.getName());
		dto.setPrice(product.getPrice());
		dto.setStockQuantity(product.getStockQuantity());
		return dto;
	}

	private Product toEntity(ProductDto dto) {
		Product product = new Product();
		product.setName(dto.getName());
		product.setPrice(dto.getPrice());
		product.setStockQuantity(dto.getStockQuantity());
		return product;
	}
}
