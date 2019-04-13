package com.courscio.api.teaching;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController("teachingController")
public class TeachingController {
    private final TeachingService teachingService;

    @Autowired
    public TeachingController(TeachingService teachingService) {
        this.teachingService = teachingService;
    }

    @GetMapping("/teaching/{teachingId}")
    public ResponseEntity<?> get(@PathVariable Long teachingId) {
        Teaching result = teachingService.getById(teachingId);
        return result != null ? new ResponseEntity<>(result, HttpStatus.OK) : ResponseEntity.notFound().build();
    }

    @GetMapping(value = "/list", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> list(@RequestBody Long[] ids) {
        List<TeachingResult> resultList = teachingService.getByIds(ids);
        return new ResponseEntity<>(resultList, HttpStatus.OK);
    }
}
