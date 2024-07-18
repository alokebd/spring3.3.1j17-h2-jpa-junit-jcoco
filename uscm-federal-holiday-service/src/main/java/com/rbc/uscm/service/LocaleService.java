package com.rbc.uscm.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.rbc.uscm.dto.CountryDto;
import com.rbc.uscm.entity.Country;
import com.rbc.uscm.exception.ResourceAlreadyExistsException;
import com.rbc.uscm.exception.ResourceNotFoundException;
import com.rbc.uscm.repository.LocaleRepository;

@Service
public class LocaleService {
	
	@Autowired
	private LocaleRepository localeRepository;
	
	
	public List<CountryDto> findAll(){
		List<Country> entities= localeRepository.findAll();
		if (entities == null || entities.size()==0) {
			throw new ResourceNotFoundException("Resource not found");
		}
		List<CountryDto> dtoList = new ArrayList<CountryDto>();
		//entities.stream().map(c -> new ModelMapper().map(c, CountryDto.class)).collect(Collectors.toList());
		for (Country c: entities) {
			dtoList.add(new ModelMapper().map(c, CountryDto.class));
		}
		return dtoList;
	}
	
	public CountryDto save(CountryDto countryDto) {
		if (countryDto.getCode().isBlank() || countryDto.getName().isBlank()) {
			 throw new ResourceNotFoundException("Resource agruments name and city are required.");
		}
		if (findByCode(countryDto.getCode()).isPresent()){
			throw new ResourceAlreadyExistsException("Resource already exists.");
		}
		ModelMapper modelMapper = new ModelMapper();
		Country newCountry = modelMapper.map(countryDto, Country.class);
		Country country= localeRepository.save(newCountry);
		return  modelMapper.map(country, CountryDto.class);
	}
	
	public Optional<Country> findByCode(String code) {
		Optional<Country> country =  localeRepository.findByCode(code);
		return country;
	}
	
	public CountryDto update(CountryDto countryDto, int Id) {
		if (countryDto.getName().isBlank() || countryDto.getCode().isBlank()) {
			throw new ResourceNotFoundException("CountryDto values (name and code) are requied. They are non blank.");
		}
		
		ModelMapper modelMapper = new ModelMapper();
		Country country = modelMapper.map(countryDto, Country.class);
		country.setId(Id);
		Country updatedCountry= localeRepository.save(country);
		return  modelMapper.map(updatedCountry, CountryDto.class);
	}
}
