package com.rbc.uscm.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.rbc.uscm.dto.CountryDto;
import com.rbc.uscm.entity.Country;
import com.rbc.uscm.repository.LocaleRepository;

@Service
public class LocaleService {
	
	@Autowired
	private LocaleRepository localeRepository;
	
	
	public List<CountryDto> findAll(){
		List<Country> entities= localeRepository.findAll();
		List<CountryDto> dtoList = new ArrayList<CountryDto>();
		if (entities != null && entities.size()>0) {
			//entities.stream().map(c -> new ModelMapper().map(c, CountryDto.class)).collect(Collectors.toList());
			for (Country c: entities) {
				dtoList.add(new ModelMapper().map(c, CountryDto.class));
			}
		}
		return dtoList;
	}
	
	public CountryDto save(CountryDto countryDto) {
		ModelMapper modelMapper = new ModelMapper();
		Country newCountry = modelMapper.map(countryDto, Country.class);
		Country country= localeRepository.save(newCountry);
		return  modelMapper.map(country, CountryDto.class);
	}
	
	public Optional<Country> findByCode(String code) {
		return localeRepository.findByCode(code);
	}
	
	public CountryDto update(CountryDto countryDto, int Id) {
		ModelMapper modelMapper = new ModelMapper();
		Country country = modelMapper.map(countryDto, Country.class);
		country.setId(Id);
		Country updatedCountry= localeRepository.save(country);
		return  modelMapper.map(updatedCountry, CountryDto.class);
	}
}
