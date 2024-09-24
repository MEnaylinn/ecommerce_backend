package com.hostmdy.ecommerce.service;

import java.util.List;
import com.hostmdy.ecommerce.domain.Review;
import com.hostmdy.ecommerce.domain.User;

public interface ReviewService {
	
	Review saveReview(Review review);
	
	Review createReview(Review review,Long productId,User user);
	
	List<Review> getAllReview(Long productId);
	
	void deleteReviewById(Long reviewId,Long productId,User user);
	
	public void setProductReview(Long productId);

}
