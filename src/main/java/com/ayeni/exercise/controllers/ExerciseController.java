package com.ayeni.exercise.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ayeni.exercise.DAO.CardSchemeDAO;
import com.ayeni.exercise.DAO.ResponseHandler;
import com.ayeni.exercise.models.CardScheme;

@RestController
public class ExerciseController {

	@Autowired
	private CardSchemeDAO cardSchemeDAO;

	@GetMapping("/")
	String sayHello() {

		return "Hello! from Ayeni Jeremiah";
	}

	// get details of a card number
	// card-scheme/verify/card-number
	@GetMapping("/card-scheme/verify/{number}")
	public ResponseEntity<Object> cardScheme(@PathVariable("number") String cardNumber) {

		Optional<CardScheme> thisCard = cardSchemeDAO.getCardDetails(cardNumber);

		if (thisCard.isPresent()) {
			CardScheme card = thisCard.get();
			cardSchemeDAO.updateCount(card);
			return ResponseHandler.getCardDetails(HttpStatus.OK, true, thisCard);
		}
		return ResponseHandler.getCardDetails(HttpStatus.NOT_FOUND, false, thisCard);
	}

	// get statistics of limited number of cards based on their searches
	// card-scheme/stats?start={start}&limit={limit}
	@GetMapping("/card-scheme/stats")
	public ResponseEntity<Object> stats(@RequestParam String start, @RequestParam String limit) {
		int startNo, limitNo;
		try {
			startNo = Integer.parseInt(start);
			limitNo = Integer.parseInt(limit);
		} catch (Exception e) {
			return ResponseHandler.invalidEntry(HttpStatus.BAD_REQUEST);
		}

		List<CardScheme> retrievedCards = cardSchemeDAO.getCardsByOffset(limitNo, startNo);
		long size = (int) cardSchemeDAO.getTotalCardsCounts();

		return ResponseHandler.getStats(HttpStatus.OK, true, startNo, limitNo, size, retrievedCards);

	}

	// get statistics of all cards based on their searches
	// card-scheme/stats/
	@GetMapping("/card-scheme/stats/")
	public ResponseEntity<Object> all() {

		Iterable<CardScheme> retrievedCards = cardSchemeDAO.getAllCards();
		long size = (int) cardSchemeDAO.getTotalCardsCounts();

		return ResponseHandler.getStats(HttpStatus.OK, true, 1, 0, size, (List<CardScheme>) retrievedCards);

	}
}
