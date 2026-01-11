package com.app.dethloff.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Objects;

@Data
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    protected String id;

    @Column(name = "name")
    protected String name;

    @Column(name = "surname")
    protected String surname;

//    @Override
//    public boolean equals(Object o) {
//        if (!(o instanceof Person person)) return false;
//        return Objects.equals(id, person.id);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(id, name, surname);
//    }
}
