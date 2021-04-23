package com.bing.monkey.common.GenerateUtils;

import com.bing.monkey.common.entity.ColumnInfo;
import com.bing.monkey.common.util.ToolkitUtil;
import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.NullCacheStorage;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Slf4j
public class TemplateUtils {

    private TemplateUtils() {

    }

    private static String author = "HelloWorld";

    private static String tableComment;

    // 当前项目所在的文件夹目录 + maven项目源码路径src/main/java
    static final String rootPath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "java";

    // 项目源码包前缀
    static final String projectRoot = "com.bing.monkey.generate";

    // 固定文件格式，后缀
    static final String SUFFIX = ".java";

    // 将项目源码包中的.替换为/，为即将生成的文件指定路径
    static final String projectPath = projectRoot.replace(".", File.separator);

    // 生成的controller的包路径
    static final String controllerPath = rootPath + File.separator + projectPath + File.separator + "controllers";

    // 生成的entity的包路径
    static final String entityPath = rootPath + File.separator + projectPath + File.separator + "models";

    // 生成的service的包路径
    static final String servicePath = rootPath + File.separator + projectPath + File.separator + "services";

    // 生成的service.impl的包路径
    static final String serviceImplPath = rootPath + File.separator + projectPath + File.separator + "services" + File.separator + "impl";

    // 生成的exception的包路径
    static final String exceptionPath = rootPath + File.separator + projectPath + File.separator + "exceptions";

    // 生成的exception的包路径
    static final String exceptionEnumPath = rootPath + File.separator + projectPath + File.separator + "exceptions" + File.separator + "enums";

    // 生成的repository的包路径
    static final String repositoryPath = rootPath + File.separator + projectPath + File.separator + "repositories";

    // 获取freemarker的模板配置
    private static final Configuration CONFIGURATION = new Configuration(Configuration.VERSION_2_3_23);

    static {

        // 这里用来指定模板所在的路径，本项目配置在resources/templates目录下，springBoot项目会默认到resources下读文件
        CONFIGURATION.setTemplateLoader(new ClassTemplateLoader(com.bing.monkey.common.GenerateUtils.TemplateUtils.class, "/templates"));

        // 设置默认的编码格式
        CONFIGURATION.setDefaultEncoding("UTF-8");

        // 设置默认的模板异常处理
        CONFIGURATION.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

        // 设置缓存类型，非具体
        CONFIGURATION.setCacheStorage(NullCacheStorage.INSTANCE);
    }


    /**
     * 获取模板方法
     *
     * @param templateName 具体的模板名称
     * @return
     * @throws IOException
     */
    public static Template getTemplate(String templateName) throws IOException {
        try {
            return CONFIGURATION.getTemplate(templateName);
        } catch (IOException e) {
            // todo 捕获这里的异常进行处理
            throw e;
        }
    }


    /**
     * 根据表名获取表注释并赋值全局变量
     *
     * @param tableName 数据库中的表名
     */
    public static void getTableCommentByTableName(String tableName) {
        tableComment = Objects.requireNonNull(DataSourceUtils.getTableRemarks(tableName)).get("tableComment");
    }

    /**
     * 根据表名和模板生成文件entity文件
     *
     * @param tableName 数据库中的表名
     */
    public static void generateEntity(String tableName) {
        // 根据表名获取该表中的字段信息
        List<ColumnInfo> columnInfos = DataSourceUtils.getTableColumnsInfo(tableName);

        // 创建文件
        File pageFile = new File(entityPath);
        if (!pageFile.exists()) {
            if (!pageFile.mkdirs()) {
                log.error("目标文件夹创建失败");
                return;
            }
        }

        // 将表名转化成驼峰命名并首字母大写，生成实体类文件
        final String entityFileLocation = entityPath + File.separator + ToolkitUtil.underline2Camel(tableName, true) + SUFFIX;

        // 获取模板文件
        Template entityTemplate = null;
        try {
            entityTemplate = getTemplate("Entity.ftl");
        } catch (IOException e) {
            log.error("获取模板文件失败");
            e.printStackTrace();
        }

        HashMap<String, Object> templateInfoMap = new HashMap<>();
        ArrayList<HashMap<String, String>> allColumns = new ArrayList<>();
        // 循环处理字段信息
        for (ColumnInfo ci :
                columnInfos) {
            HashMap<String, String> columnInfo = new HashMap<>();
            columnInfo.put("fieldNote", ci.getColumnComment());
            columnInfo.put("fieldType", ToolkitUtil.sqlType2java(ci.getColumnType()));
            columnInfo.put("fieldName", ci.getCamelColumnName());
            columnInfo.put("fieldOriName", ci.getColumnName());
            allColumns.add(columnInfo);
        }
        templateInfoMap.put("columns", allColumns);

        // 处理模板中的其他信息
        templateInfoMap.put("beanName", ToolkitUtil.underline2Camel(tableName, true));
        templateInfoMap.put("packagePath", projectRoot);
        templateInfoMap.put("author", author);
        templateInfoMap.put("projectName", "freemarkerTest");
        templateInfoMap.put("tableName", tableName);
        templateInfoMap.put("tableComment", tableComment);

        try {
            // 将流指向实体类文件
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(entityFileLocation), StandardCharsets.UTF_8));
            assert entityTemplate != null;

            // 模板处理信息，并写入流
            entityTemplate.process(templateInfoMap, bufferedWriter);

            // 流刷入文件
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (TemplateException e) {
            log.error("处理模板异常");
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            log.error("找不到实体写入的目标文件");
            e.printStackTrace();
        } catch (IOException e) {
            log.error("实体写入异常");
            e.printStackTrace();
        }
    }

    public static void generateEntityQuery(String tableName) {

        // 创建文件
        File pageFile = new File(entityPath);
        if (!pageFile.exists()) {
            if (!pageFile.mkdirs()) {
                log.error("目标文件夹创建失败");
                return;
            }
        }

        // 将表名转化成驼峰命名并首字母大写，生成实体类文件
        final String entityFileLocation = entityPath + File.separator + ToolkitUtil.underline2Camel(tableName, true) + "Query" + SUFFIX;

        // 获取模板文件
        Template entityTemplate = null;
        try {
            entityTemplate = getTemplate("EntityQuery.ftl");
        } catch (IOException e) {
            log.error("获取模板文件失败");
            e.printStackTrace();
        }

        HashMap<String, Object> templateInfoMap = new HashMap<>();
        ArrayList<HashMap<String, String>> allColumns = new ArrayList<>();
        // 处理模板中的其他信息
        templateInfoMap.put("modelClass", ToolkitUtil.underline2Camel(tableName, true));
        templateInfoMap.put("modelName", ToolkitUtil.underline2Camel(tableName));
        templateInfoMap.put("packagePath", projectRoot);
        templateInfoMap.put("author", author);
        templateInfoMap.put("tableComment", tableComment);

        try {
            // 将流指向实体类文件
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(entityFileLocation), StandardCharsets.UTF_8));
            assert entityTemplate != null;

            // 模板处理信息，并写入流
            entityTemplate.process(templateInfoMap, bufferedWriter);

            // 流刷入文件
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (TemplateException e) {
            log.error("处理模板异常");
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            log.error("找不到实体写入的目标文件");
            e.printStackTrace();
        } catch (IOException e) {
            log.error("实体写入异常");
            e.printStackTrace();
        }
    }

    public static void generateController(String tableName) {

        // 创建文件
        File pageFile = new File(controllerPath);
        if (!pageFile.exists()) {
            if (!pageFile.mkdirs()) {
                log.error("目标文件夹创建失败");
                return;
            }
        }

        // 将表名转化成驼峰命名并首字母大写，生成实体类文件
        final String controllerFileLocation = controllerPath + File.separator + ToolkitUtil.underline2Camel(tableName, true) + "Controller" + SUFFIX;

        // 获取模板文件
        Template controllerTemplate = null;
        try {
            controllerTemplate = getTemplate("Controller.ftl");
        } catch (IOException e) {
            log.error("获取模板文件失败");
            e.printStackTrace();
        }

        HashMap<String, String> templateInfoMap = new HashMap<>();

        // 处理模板中的其他信息
        // TODO 各方法内的变量名称和冗余代码整合
        templateInfoMap.put("packagePath", projectRoot);
        templateInfoMap.put("requestPathHead", ToolkitUtil.underline2Camel(tableName));
        templateInfoMap.put("classComment", tableComment);
        templateInfoMap.put("serviceClass", ToolkitUtil.underline2Camel(tableName, true) + "Service");
        templateInfoMap.put("serviceName", ToolkitUtil.underline2Camel(tableName) + "Service");
        templateInfoMap.put("modelClass", ToolkitUtil.underline2Camel(tableName, true));
        templateInfoMap.put("modelName", ToolkitUtil.underline2Camel(tableName));

        try {
            // 将流指向实体类文件
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(controllerFileLocation), StandardCharsets.UTF_8));
            assert controllerTemplate != null;

            // 模板处理信息，并写入流
            controllerTemplate.process(templateInfoMap, bufferedWriter);

            // 流刷入文件
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (TemplateException e) {
            log.error("处理模板异常");
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            log.error("找不到实体写入的目标文件");
            e.printStackTrace();
        } catch (IOException e) {
            log.error("实体写入异常");
            e.printStackTrace();
        }
    }

    public static void generateService(String tableName) {

        // 创建文件
        File pageFile = new File(servicePath);
        if (!pageFile.exists()) {
            if (!pageFile.mkdirs()) {
                log.error("目标文件夹创建失败");
                return;
            }
        }

        // 将表名转化成驼峰命名并首字母大写，生成实体类文件
        final String serviceFileLocation = servicePath + File.separator + ToolkitUtil.underline2Camel(tableName, true) + "Service" + SUFFIX;

        // 获取模板文件
        Template entityTemplate = null;
        try {
            entityTemplate = getTemplate("ServiceInterface.ftl");
        } catch (IOException e) {
            log.error("获取模板文件失败");
            e.printStackTrace();
        }

        HashMap<String, String> templateInfoMap = new HashMap<>();

        // 处理模板中的其他信息
        // TODO 各方法内的变量名称和冗余代码整合
        templateInfoMap.put("packagePath", projectRoot);
        templateInfoMap.put("requestPathHead", ToolkitUtil.underline2Camel(tableName));
        templateInfoMap.put("classComment", tableComment);
        templateInfoMap.put("modelClass", ToolkitUtil.underline2Camel(tableName, true));
        templateInfoMap.put("modelName", ToolkitUtil.underline2Camel(tableName));

        try {
            // 将流指向实体类文件
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(serviceFileLocation), StandardCharsets.UTF_8));
            assert entityTemplate != null;

            // 模板处理信息，并写入流
            entityTemplate.process(templateInfoMap, bufferedWriter);

            // 流刷入文件
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (TemplateException e) {
            log.error("处理模板异常");
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            log.error("找不到实体写入的目标文件");
            e.printStackTrace();
        } catch (IOException e) {
            log.error("实体写入异常");
            e.printStackTrace();
        }
    }

    public static void generateServiceImpl(String tableName) {

        // 创建文件
        File pageFile = new File(serviceImplPath);
        if (!pageFile.exists()) {
            if (!pageFile.mkdirs()) {
                log.error("目标文件夹创建失败");
                return;
            }
        }

        // 将表名转化成驼峰命名并首字母大写，生成实体类文件
        final String serviceFileLocation = serviceImplPath + File.separator + ToolkitUtil.underline2Camel(tableName, true) + "ServiceImpl" + SUFFIX;

        // 获取模板文件
        Template entityTemplate = null;
        try {
            entityTemplate = getTemplate("ServiceImpl.ftl");
        } catch (IOException e) {
            log.error("获取模板文件失败");
            e.printStackTrace();
        }

        HashMap<String, String> templateInfoMap = new HashMap<>();

        // 处理模板中的其他信息
        // TODO 各方法内的变量名称和冗余代码整合
        templateInfoMap.put("packagePath", projectRoot);
        templateInfoMap.put("serviceClass", ToolkitUtil.underline2Camel(tableName, true) + "Service");
        templateInfoMap.put("serviceName", ToolkitUtil.underline2Camel(tableName) + "Service");
        templateInfoMap.put("classComment", tableComment);
        templateInfoMap.put("exceptionClassName", ToolkitUtil.underline2Camel(tableName, true) + "Exception");
        templateInfoMap.put("modelClass", ToolkitUtil.underline2Camel(tableName, true));
        templateInfoMap.put("modelName", ToolkitUtil.underline2Camel(tableName));
        templateInfoMap.put("RepoClass", ToolkitUtil.underline2Camel(tableName, true) + "Repo");
        templateInfoMap.put("RepoName", ToolkitUtil.underline2Camel(tableName) + "Repo");

        try {
            // 将流指向实体类文件
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(serviceFileLocation), StandardCharsets.UTF_8));
            assert entityTemplate != null;

            // 模板处理信息，并写入流
            entityTemplate.process(templateInfoMap, bufferedWriter);

            // 流刷入文件
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (TemplateException e) {
            log.error("处理模板异常");
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            log.error("找不到实体写入的目标文件");
            e.printStackTrace();
        } catch (IOException e) {
            log.error("实体写入异常");
            e.printStackTrace();
        }
    }

    public static void generateRepository(String tableName) {

        // 创建文件
        File pageFile = new File(repositoryPath);
        if (!pageFile.exists()) {
            if (!pageFile.mkdirs()) {
                log.error("目标文件夹创建失败");
                return;
            }
        }

        // 将表名转化成驼峰命名并首字母大写，生成实体类文件
        final String serviceFileLocation = repositoryPath + File.separator + ToolkitUtil.underline2Camel(tableName, true) + "Repo" + SUFFIX;

        // 获取模板文件
        Template entityTemplate = null;
        try {
            entityTemplate = getTemplate("Repository.ftl");
        } catch (IOException e) {
            log.error("获取模板文件失败");
            e.printStackTrace();
        }

        HashMap<String, String> templateInfoMap = new HashMap<>();

        // 处理模板中的其他信息
        // TODO 各方法内的变量名称和冗余代码整合
        templateInfoMap.put("packagePath", projectRoot);
        templateInfoMap.put("modelClass", ToolkitUtil.underline2Camel(tableName, true));

        try {
            // 将流指向实体类文件
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(serviceFileLocation), StandardCharsets.UTF_8));
            assert entityTemplate != null;

            // 模板处理信息，并写入流
            entityTemplate.process(templateInfoMap, bufferedWriter);

            // 流刷入文件
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (TemplateException e) {
            log.error("处理模板异常");
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            log.error("找不到实体写入的目标文件");
            e.printStackTrace();
        } catch (IOException e) {
            log.error("实体写入异常");
            e.printStackTrace();
        }
    }

    public static void generateException(String tableName) {

        // 创建文件
        File pageFile = new File(exceptionPath);
        if (!pageFile.exists()) {
            if (!pageFile.mkdirs()) {
                log.error("目标文件夹创建失败");
                return;
            }
        }

        // 将表名转化成驼峰命名并首字母大写，生成实体类文件
        final String serviceFileLocation = exceptionPath + File.separator + ToolkitUtil.underline2Camel(tableName, true) + "Exception.java";

        // 获取模板文件
        Template entityTemplate = null;
        try {
            entityTemplate = getTemplate("Exception.ftl");
        } catch (IOException e) {
            log.error("获取模板文件失败");
            e.printStackTrace();
        }

        HashMap<String, String> templateInfoMap = new HashMap<>();

        // 处理模板中的其他信息
        // TODO 各方法内的变量名称和冗余代码整合
        templateInfoMap.put("packagePath", projectRoot);
        templateInfoMap.put("classComment", tableComment);
        templateInfoMap.put("modelClass", ToolkitUtil.underline2Camel(tableName, true));
        templateInfoMap.put("modelName", ToolkitUtil.underline2Camel(tableName));

        try {
            // 将流指向实体类文件
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(serviceFileLocation), StandardCharsets.UTF_8));
            assert entityTemplate != null;

            // 模板处理信息，并写入流
            entityTemplate.process(templateInfoMap, bufferedWriter);

            // 流刷入文件
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (TemplateException e) {
            log.error("处理模板异常");
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            log.error("找不到实体写入的目标文件");
            e.printStackTrace();
        } catch (IOException e) {
            log.error("实体写入异常");
            e.printStackTrace();
        }
    }

    public static void generateExceptionEnum(String tableName) {

        // 创建文件
        File pageFile = new File(exceptionEnumPath);
        if (!pageFile.exists()) {
            if (!pageFile.mkdirs()) {
                log.error("目标文件夹创建失败");
                return;
            }
        }

        // 将表名转化成驼峰命名并首字母大写，生成实体类文件
        final String serviceFileLocation = exceptionEnumPath + File.separator + ToolkitUtil.underline2Camel(tableName, true) + "ExceptionEnum.java";

        // 获取模板文件
        Template entityTemplate = null;
        try {
            entityTemplate = getTemplate("ExceptionEnum.ftl");
        } catch (IOException e) {
            log.error("获取模板文件失败");
            e.printStackTrace();
        }

        HashMap<String, String> templateInfoMap = new HashMap<>();

        // 处理模板中的其他信息
        // TODO 各方法内的变量名称和冗余代码整合
        templateInfoMap.put("packagePath", projectRoot);
        templateInfoMap.put("classComment", tableComment);
        templateInfoMap.put("modelClass", ToolkitUtil.underline2Camel(tableName, true));

        try {
            // 将流指向实体类文件
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(serviceFileLocation), StandardCharsets.UTF_8));
            assert entityTemplate != null;

            // 模板处理信息，并写入流
            entityTemplate.process(templateInfoMap, bufferedWriter);

            // 流刷入文件
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (TemplateException e) {
            log.error("处理模板异常");
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            log.error("找不到实体写入的目标文件");
            e.printStackTrace();
        } catch (IOException e) {
            log.error("实体写入异常");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String tableName = "sys_imgs";
        // 根据表名获取表注释
        getTableCommentByTableName(tableName);

        // 调用生成方法生成文件
        generateEntity(tableName);
        generateEntityQuery(tableName);
        generateController(tableName);
        generateService(tableName);
        generateServiceImpl(tableName);
        generateRepository(tableName);
        generateException(tableName);
        generateExceptionEnum(tableName);

    }
}
