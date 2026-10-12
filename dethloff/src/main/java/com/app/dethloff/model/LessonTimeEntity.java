package com.app.dethloff.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "lesson_time")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LessonTimeEntity {
    @Id
    @Column(length = 36)
    private String id;

    @OneToOne
    @JoinColumn(name = "lesson_id", nullable = false, unique = true)
    @ToString.Exclude
    private LessonEntity lesson;

    @Column(nullable = false)
    private LocalDate date;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;
}
