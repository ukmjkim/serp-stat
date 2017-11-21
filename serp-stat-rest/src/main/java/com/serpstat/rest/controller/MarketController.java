package com.serpstat.rest.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.serpstat.rest.domain.ExceptionInfo;
import com.serpstat.rest.domain.Market;
import com.serpstat.rest.exception.MarketNotFoundException;
import com.serpstat.rest.exception.MarketRegionAndLangNotUniqueException;
import com.serpstat.rest.repository.MarketRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class MarketController {

	@Autowired
	MarketRepository marketRepository;
	
	@GetMapping("/markets")
	public ResponseEntity<List<Market>> listAllMarkets() {
		List<Market> markets = marketRepository.findAll();
		if (markets.isEmpty()) {
			return new ResponseEntity<List<Market>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Market>>(markets, HttpStatus.OK);
	}
	
	@GetMapping("/markets/{marketId}")
	public ResponseEntity<Market> getMarket(@PathVariable Integer marketId) {
		Market market = marketRepository.findById(marketId).orElse(null);
		if (market == null) {
			return new ResponseEntity<Market>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Market>(market, HttpStatus.OK);		
	}
	
	@PostMapping("/markets")
	public ResponseEntity<Void> createMarket(@RequestBody Market market) throws MarketRegionAndLangNotUniqueException {
		Market entity = marketRepository.findByRegionAndLang(market.getRegion(), market.getLang()).orElse(null);
		if (entity != null) {
			log.info("The region and lang given is already exist [], []", market.getRegion(), market.getLang());
			throw new MarketRegionAndLangNotUniqueException("The region and lang given is already exist");
		}

		Market createdEntity = marketRepository.saveAndFlush(market);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdEntity.getId()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@PutMapping("/markets/{marketId}")
	public ResponseEntity<Market> updateMarket(@PathVariable Integer marketId, @RequestBody Market market) throws MarketNotFoundException {
		Market entity = marketRepository.findById(market.getId()).orElse(null);
		if (entity == null) {
			throw new MarketNotFoundException("Market with id not found");
		}

		Market updatedEntity = marketRepository.saveAndFlush(market);
		return new ResponseEntity<Market>(updatedEntity, HttpStatus.OK);
	}
	
	@DeleteMapping("/markets/{marketId}")
	public ResponseEntity<Void> deleteMarket(@PathVariable Integer marketId) throws MarketNotFoundException {
		Market entity = marketRepository.findById(marketId).orElse(null);
		if (entity == null) {
			throw new MarketNotFoundException("Market with id not found");
		}

		marketRepository.deleteById(marketId);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	@ExceptionHandler({ MarketNotFoundException.class, MarketRegionAndLangNotUniqueException.class })
	public ResponseEntity<ExceptionInfo> handleException(Exception ex) {
		ExceptionInfo error = new ExceptionInfo();
		if (AnnotationUtils.findAnnotation(ex.getClass(), ResponseStatus.class) != null) {
			ResponseStatus rs = AnnotationUtils.findAnnotation(ex.getClass(), ResponseStatus.class);
			error.setErrorCode(Integer.parseInt(rs.value().toString()));
		} else {
			error.setErrorCode(HttpStatus.PRECONDITION_FAILED.value());
		}
		error.setMessage(ex.getMessage());
		return new ResponseEntity<ExceptionInfo>(error, HttpStatus.OK);
	}
}
