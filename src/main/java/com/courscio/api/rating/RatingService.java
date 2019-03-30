package com.courscio.api.rating;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class RatingService {
    private final RatingRepository ratingRepository;

    @Autowired
    public RatingService(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }
    
    public List<Rating> findById(long teaching_id) {
    	return ratingRepository.findById(teaching_id);
    }
    
    public boolean addRating(Rating rating) {
    	return ratingRepository.insert(rating) > 0;
    }
    
    public boolean addScore(long user_id, long teaching_id, Integer score) {
        return ratingRepository.updateScore(user_id, teaching_id, score) > 0;
    }
    
    public boolean addComment(long user_id, long teaching_id, String comment) {
    	return ratingRepository.updateComment(user_id, teaching_id, comment) > 0;
    }

}
