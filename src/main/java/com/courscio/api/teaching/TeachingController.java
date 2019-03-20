package com.courscio.api.teaching;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController("teachingController")
@RequestMapping("/teaching/{teachingId}")
public class TeachingController {
    private final TeachingService teachingService;

    @Autowired
    public TeachingController(TeachingService teachingService) {
        this.teachingService = teachingService;
    }

    @GetMapping
    public ResponseEntity<Teaching> get(@PathVariable Long teachingId) {
        return new ResponseEntity<>(teachingService.getById(teachingId), HttpStatus.OK);
    }
}
