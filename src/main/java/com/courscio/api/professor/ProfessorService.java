package com.courscio.api.professor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Jiupeng Zhang
 * @since 03/14/2019
 */
@Service
public class ProfessorService {
    private final ProfessorRepository professorRepository;

    @Autowired
    public ProfessorService(ProfessorRepository professorRepository) {
        this.professorRepository = professorRepository;
    }

    public Professor getById(Long id) {
        return professorRepository.findById(id);
    }
}
