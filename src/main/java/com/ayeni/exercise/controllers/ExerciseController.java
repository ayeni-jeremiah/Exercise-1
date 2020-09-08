package com.ayeni.exercise.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ayeni.exercise.DAO.CardSchemeDAO;
import com.ayeni.exercise.DAO.ResponseHandler;
import com.ayeni.exercise.interfaces.CardSchemeRepository;
import com.ayeni.exercise.models.CardScheme;

@RestController
public class ExerciseController {

	CardSchemeRepository cardSchemeRepository;
	CardSchemeDAO cardSchemeDAO = new CardSchemeDAO();

	@GetMapping("/")
	String sayHello() {
		return "Hello";
	}

	// get details of a card number
	// card-scheme/verify/card-number
	@GetMapping("/card-scheme/verify/{number}")
	public ResponseEntity<Object> cardScheme(@PathVariable("number") String cardNumber) {

		Optional<CardScheme> thisCard = cardSchemeDAO.getCardDetails(cardNumber);

		if (thisCard.isPresent()) {
			// get current card hit counts
			int currentCount = cardSchemeDAO.getCurrentCount(thisCard.get());
			thisCard.get().setCount(currentCount++);
			cardSchemeRepository.save(thisCard.get());

			return ResponseHandler.getCardDetails(HttpStatus.OK, true, thisCard);
		}
		return ResponseHandler.getCardDetails(HttpStatus.NOT_FOUND, false, thisCard);

	}

	@GetMapping("/card-scheme/stats")
	public ResponseEntity<Object> count(@RequestParam String start, @RequestParam String limit) {
		int startNo, limitNo;
		try {
			startNo = Integer.parseInt(start);			
			limitNo = Integer.parseInt(limit);
		} catch (Exception e) {
			return ResponseHandler.invalidEntry(HttpStatus.BAD_REQUEST);
		}
		
		List<CardScheme> retrievedCards = cardSchemeDAO.getCards(limitNo, startNo);
		long size = (int) cardSchemeDAO.getTotalCards();
		
		return ResponseHandler.getStats(HttpStatus.OK, true, startNo, limitNo, size, retrievedCards);

	}
}
