package com.hostmdy.ecommerce.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hostmdy.ecommerce.domain.Product;
import com.hostmdy.ecommerce.domain.Review;
import com.hostmdy.ecommerce.domain.User;
import com.hostmdy.ecommerce.exception.AlreadyReviewedException;
import com.hostmdy.ecommerce.exception.ProductNotFoundException;
import com.hostmdy.ecommerce.exception.ReviewNotFoundException;
import com.hostmdy.ecommerce.exception.UserNotMatchException;
import com.hostmdy.ecommerce.repository.ProductRepository;
import com.hostmdy.ecommerce.repository.ReviewRepository;
import com.hostmdy.ecommerce.service.ReviewService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService{
	
	private final ReviewRepository reviewRepository;
	private final ProductRepository productRepository;

	@Override
	public Review saveReview(Review review) {
		// TODO Auto-generated method stub
		return reviewRepository.save(review);
	}

	@Override
	@Transactional
	public Review createReview(Review review, Long productId, User user) {
		// TODO Auto-generated method stub
		Optional<Product> productOpt = productRepository.findById(productId);
		
		if(productOpt.isEmpty()) {
			throw new ProductNotFoundException("product with id="+productId+" is not found");
		}
		Product product = productOpt.get();
		List<Review> reviews = getAllReview(productId);
		
		boolean alreayReview = reviews.stream().anyMatch(r -> r.getUser().getId().equals(user.getId()));
		if(alreayReview) {
			throw new AlreadyReviewedException("you already reviewd this product");
		}
		
		review.setProduct(product);
		review.setUser(user);
		Review createdReivReview = reviewRepository.save(review);
		
		setProductReview(productId);
		return createdReivReview;
	}

	@Override
	public List<Review> getAllReview(Long productId) {
		// TODO Auto-generated method stub
		Optional<Product> productOpt = productRepository.findById(productId);
		
		if(productOpt.isEmpty()) {
			throw new ProductNotFoundException("product with id="+productId+" is not found");
		}
		
		return reviewRepository.findByProduct(productOpt.get());
	}

	@Override
	@Transactional
	public void deleteReviewById(Long reviewId,Long productId, User user) {
		// TODO Auto-generated method stub
		Optional<Review> reviewOpt = reviewRepository.findById(reviewId);
		if(reviewOpt.isEmpty()) {
			throw new ReviewNotFoundException("review with id="+reviewId+" is not found");
		}
		
		Review review = reviewOpt.get();
		if(!review.getUser().getId().equals(user.getId())) {
			throw new UserNotMatchException("user id is not match with review user id");
		}
		
		review.setProduct(null);
		review.setUser(null);
		saveReview(review);
		reviewRepository.deleteById(reviewId);
		setProductReview(productId);
	}
	
	public void setProductReview(Long productId) {
		Optional<Product> productOpt = productRepository.findById(productId);
		
		if(productOpt.isEmpty()) {
			throw new ProductNotFoundException("product with id="+productId+" is not found");
		}
		
		Product product = productOpt.get();
		
		List<Review> reviews = getAllReview(productId);
		Double averageReview = reviews.stream()
				.collect(Collectors.averagingDouble(r -> r.getRate()));
		product.setReview(averageReview);
		productRepository.save(product);
	}

}
