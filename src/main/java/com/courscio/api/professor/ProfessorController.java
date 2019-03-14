package com.courscio.api.professor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Jiupeng Zhang
 * @since 03/14/2019
 */
@RestController("professorController")
@RequestMapping("/professor/{professorId}")
public class ProfessorController {
    private final ProfessorService professorService;

    @Autowired
    public ProfessorController(ProfessorService professorService) {
        this.professorService = professorService;
    }

    @GetMapping
    public ResponseEntity<Professor> get(@PathVariable Long professorId) {
        return new ResponseEntity<>(professorService.getById(professorId), HttpStatus.OK);
    }
}
