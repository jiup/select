package com.courscio.api.teaching;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<TeachingResult> getByIds(Long[] ids) {
        return teachingRepository.findByIds(ids);
    }
}

