package com.bing.monkey.haagendzs.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

/**
* @program: freemarkerTest
* @description: 数据表student_haagendazstoken的实体类
* @author: HelloWorld
* @create: 2020-11-29 22:16:21
**/


@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Table(name = "haatoken")
@ApiModel(value = "student_haagendazstoken", description = "token类")
public class Haatoken implements Serializable  {

        @ApiModelProperty(value = "id")
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "id")
        private Integer id;

        @ApiModelProperty(value = "token")
        @Column(name = "token")
        private String token;
}
