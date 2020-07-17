package com.destiny.generator;

import cn.hutool.core.io.FileUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 代码生成入口
 *
 * @Author Destiny
 * @Date 2020-07-17
 */
public class CodeGenerator {

    public static void main(String[] args) {
        Configuration config = new Configuration();
        AutoGenerator generator = initCodes(config);
        moveXml(config, generator.getPackageInfo());
    }


    /**
     * ============================ 私有方法 ============================
     */

    /**
     * 初始化 - 全局配置
     * @param outputPath
     * @param author
     * @return
     */
    private static GlobalConfig initGlobalConfig(String outputPath, String author) {
        GlobalConfig gc = new GlobalConfig();
        gc.setOpen(false);
        gc.setFileOverride(true);
        gc.setOutputDir(outputPath);//输出文件路径
        gc.setFileOverride(true);
        gc.setActiveRecord(false);// 不需要ActiveRecord特性的请改为false
        gc.setEnableCache(false);// XML 二级缓存
        gc.setBaseResultMap(true);// XML ResultMap
        gc.setBaseColumnList(true);// XML columnList
        gc.setAuthor(author);// 作者
        gc.setIdType(IdType.AUTO);
        return gc;
    }

    /**
     * 数据源配置
     * @param config
     * @return
     */
    private static DataSourceConfig initDataSourceConfig(Configuration config) {
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(config.getDbType());
        dsc.setDriverName(config.getDbDriver());
        dsc.setUsername(config.getDbUsername());
        dsc.setPassword(config.getDbPassword());
        dsc.setUrl(config.getDbUrl());
        return dsc;
    }

    /**
     * 策略配置
     * @param tablePrefixArray
     * @param tableArray
     * @return
     */
    private static StrategyConfig initStrategyConfig(String[] tablePrefixArray, String[] tableArray) {
        StrategyConfig strategy = new StrategyConfig();
        strategy.setTablePrefix(tablePrefixArray);// 此处可以修改为您的表前缀
        strategy.setNaming(NamingStrategy.underline_to_camel);// 表名生成策略
        strategy.setInclude(tableArray); // 需要生成的表
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);

//        strategy.setSuperMapperClass("com.destiny.common.persistence.BaseMapper");
//        strategy.setSuperServiceClass("com.destiny.common.persistence.BaseService");
//        strategy.setSuperServiceImplClass("com.destiny.common.persistence.BaseServiceImpl");
        return strategy;
    }

    /**
     * 自定义生成
     * 1. 响应实体
     * @param config
     * @return
     */
    private static InjectionConfig initCustom(Configuration config) {
        Map<String, Object> map = new HashMap<>();
        map.put("responsePackagePath", config.getPackagePath() + ".api.response");
        map.put("configPackagePath", config.getPackagePath() + ".config");

        // swagger 属性
        map.put("swaggerProjectName", config.getSwaggerProjectName());
        map.put("swaggerVersion", config.getSwaggerVersion());
        map.put("swaggerDescription", config.getSwaggerDescription());

        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                this.setMap(map);
            }
        };

        String baseOutputPath = new StringBuilder(config.getOutputPath()).append(File.separator).append("#").append(File.separator).toString();
        Map<String, Object> outputPathMap = new HashMap<>();
        outputPathMap.put("responseOutputDir", baseOutputPath.replace("#", String.valueOf(map.get("responsePackagePath")).replace(".", File.separator)));
        outputPathMap.put("configOutputDir", baseOutputPath.replace("#", String.valueOf(map.get("configPackagePath")).replace(".", File.separator)));
        outputPathMap.forEach((key, value) -> System.out.println(key + "===" + value));

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        // 模板引擎是 velocity
        focList.add(buildFileOutConfig("/templates/response.java.vm",
                outputPathMap.get("responseOutputDir").toString(), true, "Resp"));
        focList.add(buildFileOutConfig("/templates/config-swagger.java.vm",
                outputPathMap.get("configOutputDir").toString(), false, "SwaggerConfig"));
        focList.add(buildFileOutConfig("/templates/config-mybatisplus.java.vm",
                outputPathMap.get("configOutputDir").toString(), false, "MybatisPlusConfig"));
        cfg.setFileOutConfigList(focList);
        return cfg;
    }

    private static FileOutConfig buildFileOutConfig(String templatePath, String outputPath,
                                                    boolean isUseEntityName, String fileName) {
        return new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                StringBuilder builder = new StringBuilder(outputPath);
                if (isUseEntityName) {
                    builder.append(tableInfo.getEntityName()).append(fileName);
                } else {
                    builder.append(fileName);
                }
                return builder.append(StringPool.DOT_JAVA).toString();
            }
        };
    }

    /**
     * 初始化 - 代码
     */
    private static AutoGenerator initCodes(Configuration config) {
        AutoGenerator mpg = new AutoGenerator();

        GlobalConfig gc = initGlobalConfig(config.getOutputPath(), config.getAuthor());
        // 自定义文件命名，注意 %s 会自动填充表实体属性！
        gc.setControllerName("%sController");
        gc.setServiceName("%sService");
        gc.setServiceImplName("%sServiceImpl");
        gc.setMapperName("%sMapper");
        gc.setXmlName("%sMapper");
        gc.setSwagger2(true);
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = initDataSourceConfig(config);
        mpg.setDataSource(dsc);

        StrategyConfig strategy = initStrategyConfig(config.getTablePrefixArray(), config.getTableArray());
        mpg.setStrategy(strategy);

        // 包配置
        // 默认生成当前项目 ${PROJECT_PATH}/src/main/java/${PackageConfig.parent} 下
        PackageConfig pc = new PackageConfig();
        pc.setParent(config.getPackagePath());
        pc.setController("api.controller");
        pc.setService("service");
        pc.setServiceImpl("service.impl");
        pc.setMapper("persistence.mapper");
        pc.setEntity("persistence.model");
        pc.setXml("persistence.xml");
        mpg.setPackageInfo(pc);

        mpg.setCfg(initCustom(config));

        mpg.execute();
        return mpg;
    }

    /**
     * 将 xml 移动到规范位置
     * @param config
     */
    private static void moveXml(Configuration config, PackageConfig packageInfo) {
        String outputDir = config.getOutputPath() + File.separator
                + packageInfo.getParent().replace(".", File.separator) + File.separator
                + packageInfo.getXml().replace(".", File.separator) + File.separator;
        File file = new File(config.getOutputPath());
        String toDir = file.getParentFile().getAbsolutePath() + File.separator + "resources" + File.separator + "mapper";
        File toFile = new File(toDir);
        toFile.mkdir();
        List<String> fileNames = FileUtil.listFileNames(outputDir);
        fileNames.stream().forEach((name) -> {
            FileUtil.move(new File(outputDir + name), new File(toDir + File.separator + name), true);
        });
        new File(outputDir).delete();
    }
}
