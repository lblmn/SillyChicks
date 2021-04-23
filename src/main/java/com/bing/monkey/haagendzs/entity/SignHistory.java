package com.bing.monkey.haagendzs.entity;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "student_signhistory")
public class SignHistory {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "sign_date")
    private String signDate;

    @Column(name = "result")
    private Integer result;

    @Column(name = "type")
    private Integer type;

}
