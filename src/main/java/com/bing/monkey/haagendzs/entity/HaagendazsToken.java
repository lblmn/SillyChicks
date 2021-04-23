package com.bing.monkey.haagendzs.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "student_haagendazstoken")
public class HaagendazsToken {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "token")
    private String token;

}
