package com.courscio.api.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * @author Jiupeng Zhang
 * @since 03/14/2019
 */
@Service
public class CourseService {
    private final CourseRepository courseRepository;
    private final List<String> WEEKDAYS_DEFAULT = Arrays.asList("MON", "TUE", "WEN", "THU", "FRI", "SAT", "SUN");

    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<CourseResult> listByCombinedKeyword(String keyword) {
        return courseRepository.listByCombinedKeyword(keyword);
    }

    public List<CourseResult> listByFilters(String semester, String major, Short credit, List<String> weekdays) {
        return courseRepository.listByFilters(semester, major, credit, weekdays == null ? WEEKDAYS_DEFAULT:weekdays);
    }
}
