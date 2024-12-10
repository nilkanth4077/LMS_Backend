package com.e_commerce.entity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "discussion")
public class Discussion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    private String uName;

    private String content;

}

