package com.zhangquanli.utils;

import com.zhangquanli.pojo.ColumnClass;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述：代码生成器
 */
@Component
public class CodeGenerateUtils {
    @Value("${jdbc.driver}")
    private String driver;
    @Value("${jdbc.url}")
    private String url;
    @Value("${jdbc.username}")
    private String username;
    @Value("${jdbc.password}")
    private String password;
    @Value("${packageName}")
    private String packageName;

    @Autowired
    private FreeMarkerTemplateUtils freeMarkerTemplateUtils;

    public void generate() throws Exception {
        Connection connection = getConnection();
        List<String> tableNames = getTableNames(connection);
        for (String tableName : tableNames) {
            // 生成模型数据
            Map<String, Object> dataModel = generateDataModel(connection, tableName);
            // 生成持久层接口文件
            generateDaoFile(dataModel);
            // 生成服务层接口文件
            generateServiceInterfaceFile(dataModel);
            // 生成服务实现层文件
            generateServiceImplFile(dataModel);
            // 生成控制层文件
            generateControllerFile(dataModel);
        }
        connection.close();
    }

    private List<String> getTableNames(Connection connection) throws Exception {
        DatabaseMetaData databaseMetaData = connection.getMetaData();
        ResultSet tables = databaseMetaData.getTables(connection.getCatalog(), connection.getSchema(), null, new String[]{"TABLE"});
        List<String> tableNames = new ArrayList<>();

        while (tables.next()) {
            String tableName = tables.getString("TABLE_NAME");
            if (isG(tableName)) {
                if (tableName.startsWith("t_")) {
                    tableNames.add(tableName);
                }
            }
        }
        return tableNames;
    }
    public boolean isG(String tableName){
        List<String> names = new ArrayList<>();
//        names.add("t_activity");
//        names.add("t_activity_discussion");
//        names.add("t_activity_category");
        names.add("t_big_category");
        int i = 0;
        for (String name : names){
            if (tableName.equals(name)){
                i++;
            }
        }
        if (i > 0){
            return true;
        }
        return false;
    }
    private Map<String, Object> generateDataModel(Connection connection, String tableName) throws Exception {
        DatabaseMetaData databaseMetaData = connection.getMetaData();
        // 主键元数据
        ResultSet primaryKeys = databaseMetaData.getPrimaryKeys(connection.getCatalog(), connection.getSchema(), tableName);
        // 列元数据
        ResultSet columns = databaseMetaData.getColumns(connection.getCatalog(), connection.getSchema(), tableName, null);
        // 封装模型数据
        Map<String, Object> dataModel = new HashMap<>();
        ColumnClass primaryKey = new ColumnClass();
        while (primaryKeys.next()) {
            String columnName = primaryKeys.getString("COLUMN_NAME");
            primaryKey.setColumnName(columnName);
            primaryKey.setChangeColumnName(getFieldName(getModelName(columnName)));
        }
        List<ColumnClass> columnClassList = new ArrayList<>();
        while (columns.next()) {
            ColumnClass columnClass = new ColumnClass();
            // 1.数据库字段名称
            String columnName = columns.getString("COLUMN_NAME");
            columnClass.setColumnName(columnName);
            if (columnName.equals("is_delete") || columnName.endsWith("_id")) {
                if (!(columnName.contains("order_id") || columnName.contains("waybill_id"))) {
                    continue;
                }
            }
            // 2.Java变量名称
            String changeColumnName = getFieldName(getModelName(columnName));
            columnClass.setChangeColumnName(changeColumnName);
            // 4.Java数据类型
            String typeName = columns.getString("TYPE_NAME");
            columnClass.setColumnType(getJavaType(typeName));
            // 3.数据库中的字段注释
            String remarks = columns.getString("REMARKS");
            columnClass.setColumnComment(remarks);
            // 5.更新主键的Java数据类型
            if (changeColumnName.equals(primaryKey.getChangeColumnName())) {
                columnClass.setPrimaryKey(true);
                primaryKey.setColumnType(getJavaType(typeName));
            } else {
                columnClass.setPrimaryKey(false);
            }
            // 6.将columnClass对象添加到集合中
            columnClassList.add(columnClass);
        }
        dataModel.put("primaryKey", primaryKey);//主键列
        dataModel.put("columnClassList", columnClassList);//所有字段信息
        dataModel.put("tableName", tableName);
        dataModel.put("modelName", getModelName(tableName));
        dataModel.put("fieldName", getFieldName(getModelName(tableName)));
        dataModel.put("packageName", packageName);
        return dataModel;
    }

    private void generateDaoFile(Map<String, Object> dataModel) throws Exception {
        // 生成文件夹的名称
        File f = new File("code-generator/src/main/java");
        String diskPath = f.getCanonicalPath();
        String packagePath = packageName.replace(".", "\\") + "\\dao";
        File dir = new File(diskPath, packagePath);
        if (!dir.exists()) {
            boolean b = dir.mkdirs();
            System.out.println("创建Repository文件夹：" + b);
        }
        // 生成文件的名称
        String modelName = (String) dataModel.get("modelName");
        String suffix = "Repository.java";
        File file = new File(dir, modelName + suffix);
        // 模板名称
        String templateName = "Repository.ftl";
        generateFileByTemplate(templateName, file, dataModel);
    }

    private void generateServiceInterfaceFile(Map<String, Object> dataModel) throws Exception {
        // 生成文件夹的名称
        File f = new File("code-generator/src/main/java");
        String diskPath = f.getCanonicalPath();
        String packagePath = packageName.replace(".", "\\") + "\\service";
        File dir = new File(diskPath, packagePath);
        if (!dir.exists()) {
            boolean b = dir.mkdirs();
            System.out.println("创建ServiceInterface文件夹：" + b);
        }
        // 生成文件的名称
        String modelName = (String) dataModel.get("modelName");
        String suffix = "Service.java";
        File file = new File(dir, modelName + suffix);
        // 模板名称
        String templateName = "ServiceInterface.ftl";
        generateFileByTemplate(templateName, file, dataModel);
    }

    private void generateServiceImplFile(Map<String, Object> dataModel) throws Exception {
        // 生成文件夹的名称
        File f = new File("code-generator/src/main/java");
        String diskPath = f.getCanonicalPath();
        String packagePath = packageName.replace(".", "\\") + "\\service\\impl";
        File dir = new File(diskPath, packagePath);
        if (!dir.exists()) {
            boolean b = dir.mkdirs();
            System.out.println("创建ServiceImpl文件夹：" + b);
        }
        // 生成文件的名称
        String modelName = (String) dataModel.get("modelName");
        String suffix = "ServiceImpl.java";
        File file = new File(dir, modelName + suffix);
        // 模型数据
        String templateName = "ServiceImpl.ftl";
        generateFileByTemplate(templateName, file, dataModel);
    }

    private void generateControllerFile(Map<String, Object> dataModel) throws Exception {
        // 生成文件夹的名称
        File f = new File("code-generator/src/main/java");
        String diskPath = f.getCanonicalPath();
        String packagePath = packageName.replace(".", "\\") + "\\controller";
        File dir = new File(diskPath, packagePath);
        if (!dir.exists()) {
            boolean b = dir.mkdirs();
            System.out.println("创建Controller文件夹：" + b);
        }
        // 生成文件的名称
        String modelName = (String) dataModel.get("modelName");
        String suffix = "Controller.java";
        File file = new File(dir, modelName + suffix);
        // 模型数据
        String templateName = "Controller.ftl";
        generateFileByTemplate(templateName, file, dataModel);
    }

    private void generateFileByTemplate(String templateName, File file, Map<String, Object> dataModel) throws Exception {
        Template template = freeMarkerTemplateUtils.getTemplate(templateName);
        FileOutputStream fos = new FileOutputStream(file);
        Writer out = new BufferedWriter(new OutputStreamWriter(fos, "UTF-8"), 10240);
        template.process(dataModel, out);
        out.close();
    }

    private Connection getConnection() throws Exception {
        Class.forName(driver);
        return DriverManager.getConnection(url, username, password);
    }

    private String getModelName(String str) {
        StringBuilder sb = new StringBuilder();
        // 将字符串全部变为小写
        str = str.toLowerCase();
        // 切割字符串为字符数组
        String[] strArray = str.split("_");
        // 遍历字符数组，将首字母变为大写
        for (String s : strArray) {
            // 如果第一个字符为一个字母，则跳过
            if (s.length() == 1) {
                continue;
            }
            String c = s.substring(0, 1);
            sb.append(c.toUpperCase());
            sb.append(s.substring(1));
        }
        return sb.toString();
    }

    private String getFieldName(String str) {
        String s1 = str.substring(0, 1).toLowerCase();
        String s2 = str.substring(1);
        return s1 + s2;
    }

    private String getJavaType(String typeName) {
        Map<String, String> map = new HashMap<>();
        map.put("VARCHAR", "String");
        map.put("TEXT", "String");
        map.put("INT", "Integer");
        map.put("BIGINT", "Long");
        map.put("DOUBLE", "Double");
        map.put("DECIMAL", "BigDecimal");
        map.put("DATE", "java.util.Date");
        map.put("DATETIME", "java.util.Date");
        return map.get(typeName);
    }
}