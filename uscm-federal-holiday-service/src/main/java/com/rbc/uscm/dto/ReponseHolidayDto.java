package com.rbc.uscm.dto;
import java.time.LocalDate;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReponseHolidayDto {
	
	private Integer holidayId;
	private LocalDate officialDate;
	
	private String description;
}
