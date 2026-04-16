package com.app.dethloff.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Table(name = "teacher")
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor

public class TeacherEntity extends PersonAbstractEntity {

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "teacher")
    private List<CourseEntity> courses;

    @Column(nullable = false)
    private Boolean isActive = false;

    @Column(name = "company_name")
    String companyName;

    @Column
    String NIP;

    @Column(name = "company_city")
    String companyCity;

    @Column(name = "company_street")
    String companyStreet;

    @Column(name = "company_flat_number")
    String companyFlatNumber;

    @Column(name = "company_postal_code")
    String companyPostalCode;

    @Column(name = "company_phone_number")
    String companyPhoneNumber;

    @Column(name = "company_email")
    String companyEmail;
}
