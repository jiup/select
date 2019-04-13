package com.courscio.api.teaching;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController("teachingController")
public class TeachingController {
    private static final Logger LOG = LoggerFactory.getLogger(TeachingController.class);

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

    @GetMapping(value = "/list")
    public ResponseEntity<?> list(@RequestParam("ids") Long[] ids) {
        if (ids.length == 0) {
            LOG.warn("bad-request: /list followed with zero-length 'ids' list");
            return ResponseEntity.badRequest().build();
        }
        List<TeachingResult> resultList = teachingService.getByIds(ids);
        return new ResponseEntity<>(resultList, HttpStatus.OK);
    }
}
