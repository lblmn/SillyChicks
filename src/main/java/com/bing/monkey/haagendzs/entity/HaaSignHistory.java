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
* @description: 数据表haa_sign_history的实体类
* @author: HelloWorld
* @create: 2020-12-22 14:25:34
**/


@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Table(name = "haa_sign_his")
@ApiModel(value = "haa_sign_his", description = "签到历史记录")
public class HaaSignHistory implements Serializable  {

        @ApiModelProperty(value = "ID")
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "id")
        private Integer id;

        @ApiModelProperty(value = "签到日期")
        @Column(name = "sign_date")
        private String signDate;

        @ApiModelProperty(value = "姓名")
        @Column(name = "name")
        private String name;

        @ApiModelProperty(value = "签到结果（1-成功，2-已经签到，3-签到失败）")
        @Column(name = "result")
        private Integer result;
}
