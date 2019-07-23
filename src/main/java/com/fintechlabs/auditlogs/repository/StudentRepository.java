package com.fintechlabs.auditlogs.repository;

import com.fintechlabs.auditlogs.model.Student;
import org.springframework.data.repository.CrudRepository;

public interface StudentRepository extends CrudRepository<Student,Long> {



}
