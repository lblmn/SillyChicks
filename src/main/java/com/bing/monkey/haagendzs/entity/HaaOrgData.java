package com.bing.monkey.haagendzs.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @program: freemarkerTest
 * @description: 数据表haa_org_data的实体类
 * @author: HelloWorld
 * @create: 2020-11-29 18:42:59
 **/


@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "haa_org_data", description = "原始信息表")
public class HaaOrgData implements Serializable {

    @ApiModelProperty(value = "ID")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @ApiModelProperty(value = "哈根达斯唯一识别")
    @Column(name = "union_id")
    private String unionId;

    @ApiModelProperty(value = "通知uid")
    @Column(name = "uid")
    private String uid;

    @ApiModelProperty(value = "姓名")
    @Column(name = "name")
    private String name;

    @ApiModelProperty(value = "备注")
    @Column(name = "memo")
    private String memo;

    @ApiModelProperty(value = "修改时间")
    @Column(name = "modify_time")
    private String modifyTime;

    @ApiModelProperty(value = "创建时间")
    @Column(name = "create_time")
    private String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
}
