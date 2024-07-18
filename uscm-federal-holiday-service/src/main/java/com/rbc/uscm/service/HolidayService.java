package com.rbc.uscm.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.rbc.uscm.dto.HolidayDto;
import com.rbc.uscm.dto.ReponseHolidayDto;
import com.rbc.uscm.dto.RequestHolidayDto;
import com.rbc.uscm.entity.Country;
import com.rbc.uscm.entity.Holiday;
import com.rbc.uscm.exception.ResourceNotFoundException;
import com.rbc.uscm.repository.HolidayRepository;
import com.rbc.uscm.utils.UscmDates;

@Service
public class HolidayService {
	
	@Autowired
	private HolidayRepository holidayRepository;
	
	public ReponseHolidayDto save(RequestHolidayDto requestHolidayDto, Country country) {
		if ((requestHolidayDto.getYear()==0 || requestHolidayDto.getMonth()==0|| requestHolidayDto.getDay() == 0 || requestHolidayDto.getDescription().isBlank() )
				|| (requestHolidayDto.getYear()>0 && ( String.valueOf(requestHolidayDto.getYear()).length() !=4))) {
			 throw new ResourceNotFoundException("RequestHolidayDto values are requied. Year must be non zero and lenght 4, Month must be between 1 and 12 according to calendar Year, Day must be between 1 and 31 according to calendar Month");
		}
		LocalDate localDate = UscmDates.newLocalDate(requestHolidayDto.getYear(), requestHolidayDto.getMonth(), requestHolidayDto.getDay());
		HolidayDto holidayDto = new HolidayDto();
		holidayDto.setCountry(country);
		holidayDto.setOfficialDate(localDate);
		holidayDto.setDescription(requestHolidayDto.getDescription());
		
		ModelMapper modelMapper = new ModelMapper();
		Holiday holiday = modelMapper.map(holidayDto, Holiday.class);
		
		Holiday holiddayDb = holidayRepository.save(holiday);
		return modelMapper.map(holiddayDb, ReponseHolidayDto.class);
	}
	
	public List<ReponseHolidayDto> getAllHolidays(int countyId) {
		List<Holiday> holidays= holidayRepository.findByCountryId(countyId);
		if (holidays == null || holidays.size()==0) {
			throw new ResourceNotFoundException("Resource not found");
		}
		List<ReponseHolidayDto> dtoList = new ArrayList<ReponseHolidayDto>();
		if (holidays != null && holidays.size()>0) {
			for (Holiday h: holidays) {
				dtoList.add(new ModelMapper().map(h, ReponseHolidayDto.class));
			}
		}
		return dtoList;
	}
	
	public Optional<Holiday> findById(Integer id){
		return holidayRepository.findById(id);
	}
	
	public ReponseHolidayDto update(RequestHolidayDto requestHolidayDto, Country country, Holiday holiday) {
		if ((requestHolidayDto.getYear()==0 || requestHolidayDto.getMonth()==0|| requestHolidayDto.getDay() == 0 || requestHolidayDto.getDescription().isBlank() )
				|| (requestHolidayDto.getYear()>0 && ( String.valueOf(requestHolidayDto.getYear()).length() !=4))) {
			 throw new ResourceNotFoundException("RequestHolidayDto values are requied. Year must be non zero and lenght 4, Month must be between 1 and 12 according to calendar Year, Day must be between 1 and 31 according to calendar Month");
		}
		LocalDate localDate = UscmDates.newLocalDate(requestHolidayDto.getYear(), requestHolidayDto.getMonth(), requestHolidayDto.getDay());
		holiday.setCountry(country);
		holiday.setOfficialDate(localDate);
		holiday.setDescription(requestHolidayDto.getDescription());
		Holiday holiddayDb = holidayRepository.save(holiday);
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(holiddayDb, ReponseHolidayDto.class);
	}

}
