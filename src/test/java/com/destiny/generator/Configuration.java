package com.destiny.generator;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.setting.dialect.Props;
import com.baomidou.mybatisplus.annotation.DbType;
import lombok.Data;

import java.io.File;

/**
 * 用于生成代码的配置实体
 * 对应 resources/config.properties
 *
 * @Author Destiny
 * @Date 2020-07-17
 */
@Data
public class Configuration {
    /**
     * db类型 对应 DbType
     */
    private DbType dbType;
    private String dbDriver;
    private String dbUrl;
    private String dbUsername;
    private String dbPassword;

    /**
     * 作者
     */
    private String author;

    /**
     * 项目名
     */
    private String projectName;
    /**
     * 生成的文件所存放的路径
     * 默认放在当前项目 ${projectName}/src/main/java/ 下
     */
    private String outputPath;
    /**
     * 包路径
     */
    private String packagePath;
    /**
     * 表前缀
     */
    private String[] tablePrefixArray;
    /**
     * 待生成的表
     */
    private String[] tableArray;


    /**
     * swagger - 项目名
     */
    private String swaggerProjectName;
    /**
     * swagger - 项目版本
     */
    private String swaggerVersion;
    /**
     * swagger - 项目描述
     */
    private String swaggerDescription;

    public Configuration() {
        Props props = new Props("config.properties", CharsetUtil.UTF_8);
        String dbTypeProp = props.getProperty("db.type");
        this.dbDriver = props.getProperty("db.driver");
        this.dbUrl = props.getProperty("db.url");
        this.dbUsername = props.getProperty("db.username");
        this.dbPassword = props.getProperty("db.password");

        this.author = props.getProperty("author");

        this.swaggerProjectName = props.getProperty("swagger.projectName");
        this.swaggerVersion = props.getProperty("swagger.version");
        this.swaggerDescription = props.getProperty("swagger.description");

        this.projectName = props.getProperty("project.name");

        String outputPathProps = props.getProperty("file.output.path");
        this.outputPath = StrUtil.isEmpty(outputPathProps) ? getDefaultOutputPath(projectName) : outputPathProps;
        this.packagePath = props.getProperty("file.package.path");

        String tablePrefixProps = props.getProperty("table.prefix.list");
        tablePrefixArray = tablePrefixProps.split(",");

        String tableProps = props.getProperty("table.name.list");
        tableArray = tableProps.split(",");


        if ("mysql".equals(dbTypeProp)) {
            this.dbType = DbType.MYSQL;
        }
    }


    /**
     * 默认输出路径
     * @param projectName
     * @return
     */
    private String getDefaultOutputPath(String projectName) {
        String currentPath = Configuration.class.getResource("/").getPath();
        String[] splitArray = currentPath.split(projectName);
        if (splitArray != null && splitArray[0] != null) {
            return new StringBuilder(splitArray[0]).append(projectName).append(File.separator)
                    .append("src").append(File.separator).append("main").append(File.separator).append("java").toString();

        }
        return "";
    }

}
