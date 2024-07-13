package com.rbc.uscm.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RequestHolidayDto {
	private Integer day;
	private Integer month;
	private Integer year;
	private String description;
}
