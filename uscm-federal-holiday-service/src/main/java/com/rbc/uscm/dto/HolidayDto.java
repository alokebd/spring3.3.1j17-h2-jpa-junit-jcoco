package com.rbc.uscm.dto;

import java.time.LocalDate;
import com.rbc.uscm.entity.Country;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HolidayDto {
	private LocalDate officialDate;
	private String description;
	private Country country;
}
