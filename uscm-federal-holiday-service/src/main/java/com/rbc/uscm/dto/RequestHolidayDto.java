package com.rbc.uscm.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RequestHolidayDto {
	@Min(value = 1, message = "day should be greater than 0")
    @Max(value = 31, message = "day max value is 31 excepting February. Please check Februay which can be 28 or 29")
	private Integer day;
	@Min(value = 1, message = "month should be greater than 0")
    @Max(value = 12, message = "month maximum is 12")
	private Integer month;
	@Min(value = 1900, message = "year should be greater than 1900")
    @Max(value = 5000, message = "year should be less than 5000")
	private Integer year;
	
	@NotBlank (message = "Invalid Description: description cannot be blank")
	@NotNull(message = "Invalid Description: description is NULL")
    @Size(min = 5, max = 255, message = "Invalid Description: Must be of 5 - 255 characters")
	private String description;
}
