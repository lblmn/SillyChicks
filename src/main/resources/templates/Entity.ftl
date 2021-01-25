package ${packagePath}.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
* @program: ${projectName}
* @description: 数据表${tableName}的实体类
* @author: ${author}
* @create: ${.now?datetime}
**/


@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "${tableName}", description = "${tableComment}")
public class ${beanName} implements Serializable  {
<#list columns as column>

    <#if column.fieldOriName == "id">
        @ApiModelProperty(value = "${column.fieldNote}")
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "${column.fieldOriName}")
        private ${column.fieldType} ${column.fieldName};
    <#elseif column.fieldOriName == "create_time">
        @CreatedDate
        @ApiModelProperty(value = "${column.fieldNote}")
        @Column(name = "${column.fieldOriName}")
        private ${column.fieldType} ${column.fieldName};
    <#elseif column.fieldOriName == "update_time">
        @LastModifiedDate
        @ApiModelProperty(value = "${column.fieldNote}")
        @Column(name = "${column.fieldOriName}")
        private ${column.fieldType} ${column.fieldName};
    <#else>
        @ApiModelProperty(value = "${column.fieldNote}")
        @Column(name = "${column.fieldOriName}")
        private ${column.fieldType} ${column.fieldName};
    </#if>
</#list>
}
