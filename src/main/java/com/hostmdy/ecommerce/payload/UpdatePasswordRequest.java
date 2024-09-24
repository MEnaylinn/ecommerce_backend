package com.hostmdy.ecommerce.payload;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class UpdatePasswordRequest {
	String oldPassword;
	String newPassword;

}
