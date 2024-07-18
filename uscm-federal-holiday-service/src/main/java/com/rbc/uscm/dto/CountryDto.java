package com.rbc.uscm.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CountryDto {
	@NotBlank (message = "Invalid Name: name cannot be blank")
	@NotNull(message = "Invalid Name: Name is NULL")
    @Size(min = 3, max = 255, message = "Invalid Name: Must be of 3 - 255 characters")
	private String name;
	
	@NotBlank (message = "Invalid Code: code cannot be blank")
	@NotNull(message = "Invalid Code: Name is NULL")
    @Size(min = 3, max = 255, message = "Invalid Code: Must be of 3 - 255 characters")
	private String code;
}
