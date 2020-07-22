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
     * 生成的文件所存放的路径
     */
    private String outputPath;
    /**
     * 包路径
     */
    private String packagePath;
    /**
     * 通用工具包路径
     */
    private String commonUtilPath;
    /**
     * 表前缀
     */
    private String[] tablePrefixArray;
    /**
     * 待生成的表
     */
    private String[] tableArray;
    /**
     * 生成的pom文件所存放的路径
     */
    private String pomPath;
    /**
     * 生成的resources文件所存放的根路径
     */
    private String resourcesPath;


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


    /**
     * pom - spring-boot 版本号
     */
    private String pomSpringBootVersion;
    /**
     * pom - 项目 GroupId
     */
    private String pomGroupId;
    /**
     * pom - 项目 ArtifactId
     */
    private String pomArtifactId;
    /**
     * pom - 项目版本号
     */
    private String pomProjectVersion;
    /**
     * pom - 项目名
     */
    private String pomProjectName;
    /**
     * pom - 项目描述
     */
    private String pomProjectDescription;
    /**
     * pom - 属性 - Java 版本
     */
    private String pomPropJavaVersion;
    /**
     * pom - 属性 - Mybatisplus 版本
     */
    private String pomPropMybatisplusVersion;
    /**
     * pom - 属性 - Pagehelper 版本
     */
    private String pomPropPagehelperVersion;
    /**
     * pom - 属性 - Swagger2 版本
     */
    private String pomPropSwagger2Version;

    /**
     * app 项目名 - 从 pom 项目名配置中获取
     */
    private String appName;

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

        this.outputPath = props.getProperty("file.output.path");
        this.packagePath = props.getProperty("file.package.path");
        this.commonUtilPath = props.getProperty("file.commonutil.path");

        this.pomSpringBootVersion = props.getProperty("pom.springboot.version");
        this.pomGroupId = props.getProperty("pom.project.groupId");
        this.pomArtifactId = props.getProperty("pom.project.artifactId");
        this.pomProjectVersion = props.getProperty("pom.project.version");
        this.pomProjectName = props.getProperty("pom.project.name");
        this.pomProjectDescription = props.getProperty("pom.project.description");
        this.pomPropJavaVersion = props.getProperty("pom.properties.java.version");
        this.pomPropMybatisplusVersion = props.getProperty("pom.properties.mybatisplus.version");
        this.pomPropPagehelperVersion = props.getProperty("pom.properties.pagehelper.version");
        this.pomPropSwagger2Version = props.getProperty("pom.properties.swagger2.version");

        String tablePrefixProps = props.getProperty("table.prefix.list");
        tablePrefixArray = tablePrefixProps.split(",");

        String tableProps = props.getProperty("table.name.list");
        tableArray = tableProps.split(",");

        File outputFile = new File(this.outputPath);
        this.pomPath = outputFile.getParentFile().getParentFile().getParentFile().getAbsolutePath() + File.separator;
        this.resourcesPath = outputFile.getParentFile().getAbsolutePath() + File.separator + "resources" + File.separator;

        this.appName = getAppNameFromPomProjectName();

        if ("mysql".equals(dbTypeProp)) {
            this.dbType = DbType.MYSQL;
        }
    }

    /**
     * pom 的项目名转为 spring-boot 应用名
     * @return
     */
    private String getAppNameFromPomProjectName() {
        String[] splitArray = this.pomProjectName.split("-");
        if (splitArray != null && splitArray.length > 1) {
            return StrUtil.upperFirst(splitArray[0]) + StrUtil.upperFirst(splitArray[1]) + "Application";
        }
        return StrUtil.upperFirst(this.pomProjectName) + "Application";
    }

}
