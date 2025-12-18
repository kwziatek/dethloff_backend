package com.app.dethloff.DAO;

import com.app.dethloff.model.Student;

public interface StudentDAO {
    void save(Student theStudent);
    void findById(int theId);
}
