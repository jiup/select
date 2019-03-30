package com.courscio.api.rating;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger LOG = LoggerFactory.getLogger(RatingController.class);


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
        if (ratingService.addRating(rating)){
            return HttpStatus.OK;
        }
        LOG.warn("bad-request: invalid rating = {}", rating);
        return HttpStatus.BAD_REQUEST;
    }

    @PutMapping("/{user_id}/{teaching_id}/score")
    @PreAuthorize("authentication.principal.get(\"id\").toString().equals(\"\" + #user_id)")
    public HttpStatus put(@PathVariable Long user_id, @PathVariable Long teaching_id, @RequestParam("score") Integer score) {
        boolean result = ratingService.addScore(user_id, teaching_id, score);
        if (result) {
            return HttpStatus.OK;
        }
        LOG.warn("bad-request: invalid score={} (teachingId={}, userId={})", score, teaching_id, user_id);
        return HttpStatus.BAD_REQUEST;
    }
    
    @PutMapping("/{user_id}/{teaching_id}/comment")
    @PreAuthorize("authentication.principal.get(\"id\").toString().equals(\"\" + #user_id)")
    public HttpStatus put(@PathVariable Long user_id, @PathVariable Long teaching_id, @RequestParam("comment") String comment) {
        if (ratingService.addComment(user_id, teaching_id, comment)) {
            return HttpStatus.OK;
        }
        LOG.warn("bad-request: invalid comment={} (teachingId={}, userId={})", comment, teaching_id, user_id);
        return HttpStatus.BAD_REQUEST;
    }


}
