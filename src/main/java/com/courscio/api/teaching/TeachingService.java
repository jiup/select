package com.courscio.api.teaching;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeachingService {
    private final TeachingRepository teachingRepository;

    @Autowired
    public TeachingService(TeachingRepository teachingRepository) {
        this.teachingRepository = teachingRepository;
    }

    public Teaching getById(Long id) {
        return teachingRepository.findById(id);
    }
}

