package com.e_commerce.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("course_id")
    private Long course_id;

    @JsonProperty("course_name")
    private String course_name;

    private int price;

    @JsonProperty("instructor")
    private String instructor;

    private String description;

    @JsonProperty("p_link")
    private String p_link;

    @JsonProperty("y_link")
    private String y_link;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Feedback> feedbacks;

    @OneToMany(mappedBy = "course")
    @JsonIgnore
    private List<Questions> questions;

}
