package com.app.dethloff.dao;

import com.app.dethloff.model.SetOfCoursesEntity;

import java.util.List;
import java.util.Optional;

public interface SetOfCoursesDAO {
    Optional<List<SetOfCoursesEntity>> findAll();
}
