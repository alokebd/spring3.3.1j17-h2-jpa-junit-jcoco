package com.rbc.uscm.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CountryDto {
	@NotBlank (message = "name cannot be blank")
	private String name;
	@NotBlank (message = "code cannot be blank")
	private String code;
}
