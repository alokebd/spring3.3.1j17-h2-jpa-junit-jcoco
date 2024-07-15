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
import com.rbc.uscm.repository.HolidayRepository;
import com.rbc.uscm.utils.UscmDates;

@Service
public class HolidayService {
	
	@Autowired
	private HolidayRepository holidayRepository;
	
	public ReponseHolidayDto save(RequestHolidayDto requestHolidayDto, Country country) {
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
		LocalDate localDate = UscmDates.newLocalDate(requestHolidayDto.getYear(), requestHolidayDto.getMonth(), requestHolidayDto.getDay());
		holiday.setCountry(country);
		holiday.setOfficialDate(localDate);
		holiday.setDescription(requestHolidayDto.getDescription());
		Holiday holiddayDb = holidayRepository.save(holiday);
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(holiddayDb, ReponseHolidayDto.class);
	}

}
