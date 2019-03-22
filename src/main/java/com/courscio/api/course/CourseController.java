package com.courscio.api.course;

import com.courscio.api.schedule.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

/**
 * @author Jiupeng Zhang
 * @since 03/14/2019
 */
@RestController("courseController")
@RequestMapping("/course")
public class CourseController {
    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/keyword")
    public ResponseEntity<List<Course>> listByKeyword(@RequestParam("keyword") String keyword) {
        if (keyword.trim().length() < 4) {
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(courseService.listByCombinedKeyword(keyword), HttpStatus.OK);
    }

    @GetMapping("/filters")
    public ResponseEntity<List<Course>> listByFilters(@RequestParam("semester") String semester,
                                                      @RequestParam("major") String major,
                                                      @RequestParam("credit") Short credit,
                                                      @RequestParam("weekdays") List<Schedule.WeekDay> weekdays) {
        return new ResponseEntity<>(courseService.listByFilters(semester, major, credit, weekdays),
                HttpStatus.OK);
    }


}
