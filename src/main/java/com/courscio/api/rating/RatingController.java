package com.courscio.api.rating;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController("ratingController")
@RequestMapping("/rating")
public class RatingController {
	private final RatingService ratingService;

    @Autowired
    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @GetMapping
    public ResponseEntity<Rating> get(@PathVariable Long Id) {
        return new ResponseEntity<>(ratingService.findById(Id), HttpStatus.OK);
    }

    @PostMapping
    public HttpStatus post(@ModelAttribute Rating rating) {
        return ratingService.addRating(rating) ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
    }

    @PutMapping("/{user_id}/{teaching_id}")
    public HttpStatus put(@PathVariable Long user_id, @PathVariable Long teaching_id, Integer score) {
        return ratingService.addScore(user_id, teaching_id, score) ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
    }
    
    @PutMapping("/{user_id}/{teaching_id}")
    public HttpStatus put(@PathVariable Long user_id, @PathVariable Long teaching_id, String comment) {
        return ratingService.addComment(user_id, teaching_id, comment) ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
    }


}
