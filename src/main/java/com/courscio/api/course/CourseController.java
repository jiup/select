package com.courscio.api.course;

import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Jiupeng Zhang
 * @since 03/14/2019
 */
@RestController("courseController")
@RequestMapping("/course")
public class CourseController {
    private static final Logger LOG = LoggerFactory.getLogger(CourseController.class);
    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/keyword")
    public ResponseEntity<?> listByKeyword(@RequestParam("keyword") String keyword) {
        if (keyword.trim().length() < 4) {
            LOG.warn("bad-request: keyword too short (< 4)", keyword.trim());
            return ResponseEntity.badRequest().body("keyword too short (length < 4)");
        }
        return new ResponseEntity<>(courseService.listByCombinedKeyword(keyword), HttpStatus.OK);
    }

    @GetMapping("/filters")
    public ResponseEntity<List<CourseResult>> listByFilters(@RequestParam("semester") String semester,
                                                            @RequestParam("major") String major,
                                                            @RequestParam(value = "credit", required = false) @ApiParam(required = false) Short credit,
                                                            @RequestParam(value = "weekdays", required = false) @ApiParam(required = false) List<String> weekdays) {
        return new ResponseEntity<>(courseService.listByFilters(semester, major, credit, weekdays),
                HttpStatus.OK);
    }


}
