package com.bing.monkey.common.util;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.Set;

@Log4j2
public class ToolkitUtil {

    public static void copyNonNullProperties(Object src, Object target) {
        BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
    }

    public static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    /**
     * 下划线转换为驼峰
     *
     * @param line             下划线字符串
     * @param firstIsUpperCase 首字母是否转换为大写
     * @return
     */
    public static String underline2Camel(String line, boolean... firstIsUpperCase) {

        String str = null;

        if (StringUtils.isEmpty(line)) {
            return str;
        } else {
            StringBuilder sb = new StringBuilder();
            String[] strArr;
            // 不包含下划线，且第二个参数是空的
            if (!line.contains("_") && firstIsUpperCase.length == 0) {
                sb.append(line.substring(0, 1).toLowerCase()).append(line.substring(1));
                str = sb.toString();
            } else if (!line.contains("_") && firstIsUpperCase.length != 0) {
                if (!firstIsUpperCase[0]) {
                    sb.append(line.substring(0, 1).toLowerCase()).append(line.substring(1));
                    str = sb.toString();
                } else {
                    sb.append(line.substring(0, 1).toUpperCase()).append(line.substring(1));
                    str = sb.toString();
                }
            } else if (line.contains("_") && firstIsUpperCase.length == 0) {
                strArr = line.split("_");
                for (String s : strArr) {
                    sb.append(s.substring(0, 1).toUpperCase()).append(s.substring(1));
                }
                str = sb.toString();
                str = str.substring(0, 1).toLowerCase() + str.substring(1);
            } else if (line.contains("_") && firstIsUpperCase.length != 0) {
                strArr = line.split("_");
                for (String s : strArr) {
                    sb.append(s.substring(0, 1).toUpperCase()).append(s.substring(1));
                }
                if (!firstIsUpperCase[0]) {
                    str = sb.toString();
                    str = str.substring(0, 1).toLowerCase() + str.substring(1);
                } else {
                    str = sb.toString();
                }
            }
        }
        return str;
    }

    /**
     * MYSQL数据类型转化JAVA
     *
     * @param sqlType：MYSQL类型名称
     * @return MYSQL数据类型对应的JAVA数据类型
     */
    public static String sqlType2java(String sqlType) {
        if (sqlType == null || sqlType.trim().length() == 0) return sqlType;
        sqlType = sqlType.toLowerCase();
        switch (sqlType) {
            case "nvarchar":
            case "char":
            case "varchar":
            case "text":
            case "nchar":
                // 根据项目实际情况决定
                return "String";
            case "timestamp":
                return "LocalDateTime";
            case "blob":
            case "image":
                return "byte[]";
            case "integer":
            case "id":
            case "bigint unsigned":
                return "Long";
            case "tinyint":
            case "int":
            case "int unsigned":
            case "smallint":
            case "mediumint":
                return "Integer";
            case "bit":
            case "boolean":
                return "Boolean";
            case "bigint":
                return "java.math.BigInteger";
            case "float":
                return "Fload";
            case "double":
            case "money":
            case "smallmoney":
                return "Double";
            case "decimal":
            case "numeric":
            case "real":
                return "java.math.BigDecimal";
            case "year":
            case "datetime":
            case "date":
                return "java.util.Date";
            case "time":
                return "java.sql.Time";
            default:
                log.error("-----------------》转化失败：未发现的类型{}", sqlType);
                break;
        }
        return sqlType;
    }
}
