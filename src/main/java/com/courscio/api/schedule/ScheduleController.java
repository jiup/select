package com.courscio.api.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController("scheduleController")
@RequestMapping("/schedule/{scheduleId}")
public class ScheduleController {
	private final ScheduleService scheduleService;

    @Autowired
    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @GetMapping
    public ResponseEntity<Schedule> get(@PathVariable Long scheduleId) {
        return new ResponseEntity<>(scheduleService.getById(scheduleId), HttpStatus.OK);
    }
}
