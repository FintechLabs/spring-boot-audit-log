package com.fintechlabs.auditlogs.dto;

import com.fintechlabs.auditlogs.model.Student;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StudentDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String phoneNumber;

    public StudentDTO(Student student) {
        this.id = student.getId();
        this.firstName = student.getFirstName();
        this.lastName = student.getLastName();
        this.phoneNumber = student.getPhoneNumber();
        this.emailAddress = student.getEmailAddress();
    }

}
