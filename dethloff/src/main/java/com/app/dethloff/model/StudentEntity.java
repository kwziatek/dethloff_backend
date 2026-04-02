package com.app.dethloff.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "student")
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class StudentEntity extends PersonAbstractEntity {

    @ManyToMany(mappedBy = "students")
    @Builder.Default
    private List<CourseEntity> courses = new ArrayList<>();

//    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "address_data_id", referencedColumnName = "id")
//    private StudentAddressDataEntity addressData;

    @Column(nullable = false)
    private Boolean isActive = false;

    @Column(name = "guardian_name")
    String guardianName;

    @Column(name = "guardian_surname")
    String guardianSurname;

    @Column(name = "guardian_city")
    String guardianCity;

    @Column(name = "guardian_street")
    String guardianStreet;

    @Column(name = "guardian_flat_number")
    String guardianFlatNumber;

    @Column(name = "guardian_postal_code")
    String guardianPostalCode;

    @Column(name = "guardian_phone_number")
    String guardianPhoneNumber;

    @Column(name = "guardian_email")
    String guardianEmail;

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

    @Column(name = "marketing_sources")
    String marketingSources;
}
