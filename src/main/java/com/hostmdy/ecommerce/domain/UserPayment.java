package com.hostmdy.ecommerce.domain;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor
public class UserPayment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String cardName;
	private String holderName;
	private CardType cardType;
	private String cardNo;
	private Integer expireMonth;
	private Integer expireYear;
	private Integer cvv;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	@JsonIgnore
	private User user;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_billing_address_id")
	private UserBillingAddress userBillingAddress;
	
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
