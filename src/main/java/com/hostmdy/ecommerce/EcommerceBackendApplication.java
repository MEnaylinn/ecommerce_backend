package com.hostmdy.ecommerce;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.hostmdy.ecommerce.domain.Category;
import com.hostmdy.ecommerce.domain.Product;
import com.hostmdy.ecommerce.domain.security.Role;
import com.hostmdy.ecommerce.repository.ProductRepository;
import com.hostmdy.ecommerce.repository.RoleRepository;

@SpringBootApplication
public class EcommerceBackendApplication implements CommandLineRunner{
	
	@Autowired
	public ProductRepository productRepository;
	
	@Autowired
	public RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(EcommerceBackendApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		if(!((List<Product>)(productRepository.findAll())).isEmpty()) {
			return;
		}
		
		if(!((List<Role>)(roleRepository.findAll())).isEmpty()) {
			return;
		}
		
		Role role1 = new Role();
		role1.setName("ROLE_ADMIN");
		
		Role role2 = new Role();
		role2.setName("ROLE_USER");
		
		roleRepository.save(role1);
		roleRepository.save(role2);
		
		
		Product product1 = new Product();
		product1.setCode("E-001");
		product1.setName("Panasonic A-101 Stand Fan");
		product1.setCategory(Category.ELECTRONIC);
		product1.setQuantity(100);
		product1.setPrice(29.99);
		product1.setDiscountPercent(30);
		product1.setReview(4.5);
		product1.setDescription("This product is a fan to get cool air. Brand = Panasonic. Model = A-101. rpm - 7200");
//		product1.setImage("https://www.abenson.com/media/catalog/product/1/6/169956.jpg");
		
		Product product2 = new Product();
		product2.setCode("P-001");
		product2.setName("Nubia Redmagic 8s pro");
		product2.setCategory(Category.PHONE);
		product2.setQuantity(20);
		product2.setPrice(799.99);
		product2.setDiscountPercent(0);
		product2.setDescription("This is just nubia red magic description.");
//		product2.setImage("https://i.gadgets360cdn.com/large/red_magic_8s_pro_plus_1688554361491.jpg");
		
		Product product3 = new Product();
		product3.setCode("F-001");
		product3.setName("Double Beef Cheese Burger");
		product3.setCategory(Category.FOOD);
		product3.setQuantity(40);
		product3.setPrice(6.99);
		product3.setDiscountPercent(20);
		product3.setDescription("This product is a double beef cheese burger.");
//		product3.setImage("https://www.kitchensanctuary.com/wp-content/uploads/2021/05/Double-Cheeseburger-square-FS-42.jpg");
		
		productRepository.save(product1);
		productRepository.save(product2);
		productRepository.save(product3);
		
	}

}
