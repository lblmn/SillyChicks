package com.bing.monkey.haagendzs.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.Hibernate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * @program: freemarkerTest
 * @description: 数据表sys_imgs的实体类
 * @author: HelloWorld
 * @create: 2021-1-27 12:16:50
 **/


@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Accessors(chain = true)
@Table(name = "sys_imgs")
@ApiModel(value = "sys_imgs", description = "系统图片")
public class SysImgs implements Serializable {

    @ApiModelProperty(value = "")
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "")
    @Column(name = "code")
    private String code;

    @ApiModelProperty(value = "")
    @Column(name = "acgurl")
    private String acgurl;

    @ApiModelProperty(value = "")
    @Column(name = "size")
    private String size;

    @ApiModelProperty(value = "")
    @Column(name = "width")
    private String width;

    @ApiModelProperty(value = "")
    @Column(name = "height")
    private String height;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        SysImgs sysImgs = (SysImgs) o;
        return id != null && Objects.equals(id, sysImgs.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
