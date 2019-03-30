package com.courscio.api.rating;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController("ratingController")
@RequestMapping("/rating")
public class RatingController {
	private final RatingService ratingService;

    @Autowired
    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @GetMapping("/{teaching_id}")
    public ResponseEntity<List<Rating>> get(@PathVariable Long teaching_id) {
        return new ResponseEntity<>(ratingService.findById(teaching_id), HttpStatus.OK);
    }

    @PostMapping
    public HttpStatus post(@ModelAttribute Rating rating) {
        return ratingService.addRating(rating) ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
    }

    @PutMapping("/{user_id}/{teaching_id}/score")
    @PreAuthorize("authentication.principal.get(\"id\").toString().equals(\"\" + #user_id)")
    public HttpStatus put(@PathVariable Long user_id, @PathVariable Long teaching_id, @RequestParam("score") Integer score) {
        boolean result = ratingService.addScore(user_id, teaching_id, score);
        return result ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
    }
    
    @PutMapping("/{user_id}/{teaching_id}/comment")
    @PreAuthorize("authentication.principal.get(\"id\").toString().equals(\"\" + #user_id)")
    public HttpStatus put(@PathVariable Long user_id, @PathVariable Long teaching_id, @RequestParam("comment") String comment) {
        return ratingService.addComment(user_id, teaching_id, comment) ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
    }


}
