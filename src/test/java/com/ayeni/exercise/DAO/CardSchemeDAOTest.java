package com.ayeni.exercise.DAO;

import static org.mockito.Mockito.doReturn;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.ayeni.exercise.interfaces.CardSchemeRepository;
import com.ayeni.exercise.models.CardScheme;

@SpringBootTest
public class CardSchemeDAOTest {

	@Autowired
	private CardSchemeDAO cardSchemeDAO;

	@MockBean
	private CardSchemeRepository cardSchemeRepository;

	String number = "4166676667666746";

	@Test
	public void getCardDetailsTest() {
		CardScheme card = new CardScheme("4166676667666746", "FCMD", "debit", "mastercard", 0);
		doReturn(Optional.of(card)).when(cardSchemeRepository).findByCardNumber(number);

		// Execute the service call
		Optional<CardScheme> returnedCard = cardSchemeDAO.getCardDetails(number);

		Assertions.assertTrue(returnedCard.isPresent(), "Card was not found");
		Assertions.assertSame(returnedCard.get(), card, "The Card returned was not the same as the mock");

	}

	@Test
	void getCardsByOffsetTest() {
		// Setup our mock repository
		CardScheme card1 = new CardScheme("4166676667666746", "FCMD", "debit", "mastercard", 0);
		CardScheme card2 = new CardScheme("5136333333333335", "UBS", "debit", "visa", 0);
		doReturn(Arrays.asList(card1, card2)).when(cardSchemeRepository).findCardWithinLimit(1, 0);

		// Execute the service call
		List<CardScheme> returnedCards = (List<CardScheme>) cardSchemeDAO.getCardsByOffset(1, 0);

		// Assert the response
		Assertions.assertEquals(2, returnedCards.size(), "The size should be 2");
	}

	@Test
	void getTotalCardsCountsTest() {
		// Setup our mock repository
		long counts = 1;
		doReturn(counts).when(cardSchemeRepository).count();

		// Execute the service call
		long returnedCard = cardSchemeDAO.getTotalCardsCounts();

		// Assert the response
		Assertions.assertEquals(1, returnedCard, "The count should be 1");
	}

	@Test
	void getAllCardsTest() {
		// Setup our mock repository
		CardScheme card1 = new CardScheme("4166676667666746", "FCMD", "debit", "mastercard", 0);
		CardScheme card2 = new CardScheme("5136333333333335", "UBS", "debit", "visa", 0);
		doReturn(Arrays.asList(card1, card2)).when(cardSchemeRepository).findAll();

		// Execute the service call
		List<CardScheme> returnedCards = (List<CardScheme>) cardSchemeDAO.getAllCards();

		// Assert the response
		Assertions.assertEquals(2, returnedCards.size(), "The size should be same");
	}

	@Test
	void testSave() {
		// Setup our mock repository
		CardScheme card = new CardScheme("4166676667666746", "FCMD", "debit", "mastercard", 0);
		doReturn(card).when(cardSchemeRepository).save(card);

		// Execute the service call
		CardScheme returnedCard = cardSchemeDAO.save(card);

		// Assert the response
		Assertions.assertNotNull(returnedCard, "The saved widget should not be null");
		Assertions.assertEquals("4166676667666746", returnedCard.getCardNo(), "The version should be incremented");
	}
}
