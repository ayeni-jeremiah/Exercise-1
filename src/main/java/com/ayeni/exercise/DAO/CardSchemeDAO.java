package com.ayeni.exercise.DAO;

import java.util.List;
import java.util.Optional;

import com.ayeni.exercise.interfaces.CardSchemeRepository;
import com.ayeni.exercise.models.CardScheme;

public class CardSchemeDAO {

	CardSchemeRepository cardSchemeRepository;

	public Optional<CardScheme> getCardDetails(String cardNo) {
		System.out.println(cardNo);
		return cardSchemeRepository.findByCardNumber(cardNo);
	}
	
	public Optional<CardScheme> getCardDetails2() {
		return cardSchemeRepository.findById(1);
	}

	public int getCurrentCount(CardScheme card) {
		return card.getCount();
	}

	public List<CardScheme> getCards(int limit, int start) {
		int offset = start - 1;
		if (limit < 0 && start > 0) {
			return cardSchemeRepository.findCardWithinLimit(1, offset);
		} else if (limit > 0 && start < 0) {
			return cardSchemeRepository.findCardWithinLimit(limit, 0);
		} else if (limit > 0 && start > 0) {
			return cardSchemeRepository.findCardWithinLimit(limit, offset);
		} else {
			return cardSchemeRepository.findCardWithinLimit(1, 0);
		}
	}

	public long getTotalCards() {
		return cardSchemeRepository.count();
	}
}
