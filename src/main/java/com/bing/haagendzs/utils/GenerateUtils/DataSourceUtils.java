package com.bing.haagendzs.utils.GenerateUtils;

import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 根据配置文件中的信息，获取数据库元素集合
@Slf4j
public class DataSourceUtils {

//    url: jdbc:p6spy:mysql://61.185.20.127:10001/ylbang_db?serverTimezone=UTC&characterEncoding=UTF-8&useSSL=false
//    username: root
//    password: diDIAO2012.

//    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
//    private static final String URL = "jdbc:mysql://61.185.20.127:10001/ylbang_db?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=UTC";
//    private static final String USER = "root";
//    private static final String PASSWORD = "diDIAO2012.";

    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://124.70.129.91/python_mysql?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "KXTsoft@2010";

    public static Connection getConnection() {

        Connection connection = null;
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            log.error("连接数据库异常");
            e.printStackTrace();
        } catch (ClassNotFoundException throwables) {
            log.error("加载数据库驱动异常:{ }", throwables);
            throwables.printStackTrace();
        }
        return connection;

    }

    /**
     * 每个列描述都有以下列：
     * <p>
     * TABLE_CAT String => 表类别（可为 null）
     * TABLE_SCHEM String => 表模式（可为 null）
     * TABLE_NAME String => 表名称
     * COLUMN_NAME String => 列名称
     * DATA_TYPE int => 来自 java.sql.Types 的 SQL 类型
     * TYPE_NAME String => 数据源依赖的类型名称，对于 UDT，该类型名称是完全限定的
     * COLUMN_SIZE int => 列的大小。
     * BUFFER_LENGTH 未被使用。
     * DECIMAL_DIGITS int => 小数部分的位数。对于 DECIMAL_DIGITS 不适用的数据类型，则返回 Null。
     * NUM_PREC_RADIX int => 基数（通常为 10 或 2）
     * NULLABLE int => 是否允许使用 NULL。
     * columnNoNulls - 可能不允许使用 NULL 值
     * columnNullable - 明确允许使用 NULL 值
     * columnNullableUnknown - 不知道是否可使用 null
     * REMARKS String => 描述列的注释（可为 null）
     * COLUMN_DEF String => 该列的默认值，当值在单引号内时应被解释为一个字符串（可为 null）
     * SQL_DATA_TYPE int => 未使用
     * SQL_DATETIME_SUB int => 未使用
     * CHAR_OCTET_LENGTH int => 对于 char 类型，该长度是列中的最大字节数
     * ORDINAL_POSITION int => 表中的列的索引（从 1 开始）
     * IS_NULLABLE String => ISO 规则用于确定列是否包括 null。
     * YES --- 如果参数可以包括 NULL
     * NO --- 如果参数不可以包括 NULL
     * 空字符串 --- 如果不知道参数是否可以包括 null
     * SCOPE_CATLOG String => 表的类别，它是引用属性的作用域（如果 DATA_TYPE 不是 REF，则为 null）
     * SCOPE_SCHEMA String => 表的模式，它是引用属性的作用域（如果 DATA_TYPE 不是 REF，则为 null）
     * SCOPE_TABLE String => 表名称，它是引用属性的作用域（如果 DATA_TYPE 不是 REF，则为 null）
     * SOURCE_DATA_TYPE short => 不同类型或用户生成 Ref 类型、来自 java.sql.Types 的 SQL 类型的源类型（如果 DATA_TYPE 不是 DISTINCT 或用户生成的 REF，则为 null）
     * IS_AUTOINCREMENT String => 指示此列是否自动增加
     * YES --- 如果该列自动增加
     * NO --- 如果该列不自动增加
     * 空字符串 --- 如果不能确定该列是否是自动增加参数
     * COLUMN_SIZE 列表示给定列的指定列大小。对于数值数据，这是最大精度。对于字符数据，这是字符长度。对于日期时间数据类型，这是 String 表示形式的字符长度（假定允许的最大小数秒组件的精度）。对于二进制数据，这是字节长度。对于 ROWID 数据类型，这是字节长度。对于列大小不适用的数据类型，则返回 Null。
     * <p>
     * <p>
     * 参数：
     * catalog - 类别名称；它必须与存储在数据库中的类别名称匹配；该参数为 "" 表示获取没有类别的那些描述；为 null 则表示该类别名称不应该用于缩小搜索范围
     * schemaPattern - 模式名称的模式；它必须与存储在数据库中的模式名称匹配；该参数为 "" 表示获取没有模式的那些描述；为 null 则表示该模式名称不应该用于缩小搜索范围
     * tableNamePattern - 表名称模式；它必须与存储在数据库中的表名称匹配
     * columnNamePattern - 列名称模式；它必须与存储在数据库中的列名称匹配
     *
     * @param tableName 数据库具体的表名称
     * @return 该表的所有字段信息集合
     * @throws SQLException
     */
    public static List<ColumnInfo> getTableColumnsInfo(String tableName) {

        List<ColumnInfo> columnInfoList = new ArrayList<>();

        try {
            DatabaseMetaData databaseMetaData = getConnection().getMetaData();
            ResultSet resultSet = databaseMetaData.getColumns(null, "%", tableName, "%");
            while (resultSet.next()) {
                ColumnInfo columnInfo = new ColumnInfo();
                //获取字段名称
                columnInfo.setColumnName(resultSet.getString("COLUMN_NAME"));
                //获取字段类型
                columnInfo.setColumnType(resultSet.getString("TYPE_NAME"));
                //转换字段名称，如 sys_name 变成 SysName
                // columnClass.setChangeColumnName(replaceUnderLineAndUpperCase(resultSet.getString("COLUMN_NAME")));
                //字段在数据库的注释
                columnInfo.setColumnComment(resultSet.getString("REMARKS"));
                //字段在数据库的默认值
                columnInfo.setColumnDefValue(resultSet.getString("COLUMN_DEF"));
//                log.info("columnInfo:{}", columnInfo);
                columnInfoList.add(columnInfo);
            }
        } catch (Exception throwables) {
            log.error("获取表的列信息错误{}", throwables.getMessage());
            throwables.printStackTrace();
        }
        return columnInfoList;
    }

    /**
     * Description : 获取表的注释
     *
     * @param tableName 数据表名称
     * @return : java.lang.String
     */
    public static Map<String, String> getTableRemarks(String tableName) {
        Connection connection = getConnection();
        String type = "mysql";
        if (tableName != null) {
            Statement statement = null;
            try {
                statement = connection.createStatement();
            } catch (SQLException throwables) {
                log.error("创建SQL查询语句异常:{}\n{}", throwables.getMessage(), throwables.getStackTrace());
            }
            Map<String, String> map = new HashMap<>();
            String sql = null;
            if ("MYSQL".equals(type.toUpperCase())) {
                sql = "Select TABLE_COMMENT COMMENT from INFORMATION_SCHEMA.TABLES Where table_name = '" + tableName + "'";
            }
            if ("ORACLE".equals(type.toUpperCase())) {
                sql = "select comments COMMENT from user_tab_comments Where table_name='" + tableName + "'";
            }
            if ("SQLSERVER".equals(type.toUpperCase())) {
                sql = "SELECT COMMENT = case when a.colorder=1 then isnull(f.value,'') else '' end FROM syscolumns a " +
                        "left join systypes b on a.xusertype=b.xusertype " +
                        "inner join sysobjects d on a.id=d.id  and d.xtype='U' and  d.name<>'dtproperties' " +
                        "left join syscomments e on a.cdefault=e.id " +
                        "left join sys.extended_properties   g on a.id=G.major_id and a.colid=g.minor_id " +
                        "left join sys.extended_properties f on d.id=f.major_id and f.minor_id=0 " +
                        "where d.name='" + tableName + "'";
            }
            try {
                ResultSet resultSet = statement.executeQuery(" SELECT COUNT(1) COUNT FROM " + tableName);
                while (resultSet.next()) {
                    String tableCount = resultSet.getString("COUNT");
                    map.put("tableCount", tableCount);
                }
                ResultSet resultSet1 = statement.executeQuery(sql);
                while (resultSet1.next()) {
                    String tableComment = resultSet1.getString("COMMENT");
                    map.put("tableComment", tableComment);
                }
                resultSet1.close();
                resultSet.close();
            } catch (SQLException sqlException) {
                log.error("执行SQL查询异常:{}\n{}", sqlException.getMessage(), sqlException.getStackTrace());
            }
            return map;
        }
        return null;
    }


}
