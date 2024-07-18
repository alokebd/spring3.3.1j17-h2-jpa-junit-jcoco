package com.rbc.uscm.dto;

import java.time.LocalDate;
import com.rbc.uscm.entity.Country;

import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HolidayDto {
	private LocalDate officialDate;
	@Size(min = 5, max = 255, message = "Invalid Description: Must be of 5 - 255 characters")
	private String description;
	private Country country;
}
