package com.hostmdy.ecommerce.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "code is required")
	private String code;
	
	@NotBlank(message = "name is required")
	private String name;
	
	@NotNull(message = "category is null")
	@Enumerated(EnumType.STRING)
	private Category category;
	
	@DecimalMin(inclusive = false,value = "0",message = "quantity is equal or less than 0")
	@NotNull(message = "quantity is null")
	private Integer quantity;
	
	@NotNull(message = "price is null")
	private Double price;
	
	private Integer discountPercent = 0;
	
	private Double review = 0.0;
	
	@NotBlank(message = "description is required")
	@Column(columnDefinition = "text")
	private String description;
	
//	@NotBlank(message = "image is required")
//	private String image;
	
	@OneToMany(mappedBy = "product",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	@JsonIgnore
	private Set<ProductToCartItem> productToCartItems = new HashSet<>();
	
	//one way
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@OneToMany(mappedBy = "product",cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Review> reviews = new ArrayList<>();
	
	private LocalDateTime createdAt;
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
