package com.app.dethloff.model;

import com.app.dethloff.model.coverters.PeselConverter;
import com.app.dethloff.model.pesel.Pesel;
import com.app.dethloff.model.pesel.PeselDecoder;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Data
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class PersonAbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    protected String id;

    @Column(name = "name")
    protected String name;

    @Column(name = "surname")
    protected String surname;

    @Column(name = "birth_date")
    protected LocalDate birthDate;

    @Column(name = "place_of_birth")
    protected String placeOfBirth;

    @Column(name = "pesel", length = 11)
    @Convert(converter = PeselConverter.class)
    protected Pesel pesel;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    protected Gender gender;

    //setter for Jackson
    public void setPesel(String peselValue) {
        this.pesel = new Pesel(peselValue);
    }

    public String getString(Pesel pesel) {
        return pesel.toString();
    }

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
