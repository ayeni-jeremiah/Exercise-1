package com.ayeni.exercise.interfaces;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.ayeni.exercise.models.CardScheme;

public interface CardSchemeRepository extends CrudRepository<CardScheme, String> {

	@Query(value = "select * from CardScheme LIMIT %?1 OFFSET %?2", nativeQuery = true)
	List<CardScheme> findCardWithinLimit(int limit, int offset);
}
