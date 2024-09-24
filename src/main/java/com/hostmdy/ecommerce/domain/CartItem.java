package com.hostmdy.ecommerce.domain;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor
public class CartItem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Integer quantity;
	private Double subTotal;
	
	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;
	
	@OneToMany(mappedBy = "cartItem",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	@JsonIgnore
	private Set<ProductToCartItem> productToCartItems = new HashSet<>();
	
	@ManyToOne
	@JoinColumn(name = "cart_id")
	@JsonIgnore
	private ShoppingCart cart;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	@JsonIgnore
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "order_id")
	
	private Order order;
	
	@JsonIgnore
	private LocalDateTime createdAt;
	
	@JsonIgnore
	private LocalDateTime updatedAt;
	
	@PrePersist
	private void prePersist() {
		createdAt = LocalDateTime.now();
	}
	
	@PreUpdate
	private void preUpdate() {
		updatedAt = LocalDateTime.now();
	}
}
