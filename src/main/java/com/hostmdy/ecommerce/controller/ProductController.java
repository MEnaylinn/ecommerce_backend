package com.hostmdy.ecommerce.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hostmdy.ecommerce.domain.Category;
import com.hostmdy.ecommerce.domain.Product;
import com.hostmdy.ecommerce.service.MapValidationErrorService;
import com.hostmdy.ecommerce.service.ProductService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
@CrossOrigin(origins = "http://localhost:3000")
public class ProductController {
	
	private final ProductService productService;
	private final MapValidationErrorService mapValidationErrorService;
	
	@GetMapping("/all")
	public ResponseEntity<?> getAllProduct(){
		List<Product> products = productService.getAllProduct();
		
		if(products.isEmpty()) {
			return new ResponseEntity<String>("products are empty",HttpStatus.NOT_FOUND);
		}
		
		return ResponseEntity.ok(products);
	}
	
	@GetMapping("/{productId}")
	public ResponseEntity<?> getProductById(@PathVariable Long productId){
		Optional<Product> productOptional = productService.getProductById(productId);
		
		if(productOptional.isEmpty()) {
			return new ResponseEntity<String>("product with id="+productId+" is not found",HttpStatus.NOT_FOUND);
		}
		
		return ResponseEntity.ok(productOptional.get());
	}
	
	@PostMapping("/create")
	public ResponseEntity<?> createNewProduct(@Valid @RequestBody Product product,BindingResult result){
		
		ResponseEntity<Map<String,String>> errorResponse = mapValidationErrorService.validate(result);
		
		if(errorResponse != null) {
			return errorResponse;
		}
		
		return new ResponseEntity<Product>(productService.save(product),HttpStatus.CREATED);
	}
	
	@PutMapping("/update")
	public ResponseEntity<?> updateProduct(@Valid @RequestBody Product product,BindingResult result){
		
		ResponseEntity<Map<String,String>> errorResponse = mapValidationErrorService.validate(result);
		
		if(errorResponse != null) {
			return errorResponse;
		}
		
		if(product.getId() == null) {
			return new ResponseEntity<String>("product without id is not allowed",HttpStatus.BAD_REQUEST);
		}
		
		Product updatedProduct = productService.save(product);
		if(updatedProduct == null) {
			return new ResponseEntity<String>("product update is failed",HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<Product>(updatedProduct,HttpStatus.OK);
	}
	
	@DeleteMapping("/{productId}/delete")
	public ResponseEntity<String> deleteProductById(@PathVariable Long productId){
		System.out.println("product deleting is ...");
		if(productService.getProductById(productId).isEmpty()) {
			return new ResponseEntity<String>("product with id="+productId+" does not exist",HttpStatus.NOT_FOUND);
		}
		
		productService.deleteProductById(productId);
		return new ResponseEntity<String>(productId.toString(),HttpStatus.OK);
		
	}
	
	@GetMapping("/category")
	public ResponseEntity<?> getAllCategory(){
		Category[] categories = Category.values();
		return ResponseEntity.ok(categories);
	}

}
