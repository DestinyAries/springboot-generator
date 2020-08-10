# springboot-generator
To generate codes quickly (springboot+mysql+swagger)

# dependencies
```
// https://github.com/DestinyAries/plugin-tools/tree/master/common-tool
<dependency>
    <groupId>com.destiny</groupId>
    <artifactId>common-tool</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
```

# steps
1. prepare your local environment.
    * jdk8
    * mysql
    * maven
    
2. Go to your mysql database to create database and tables. Or you can running [this demo sql file](https://github.com/DestinyAries/springboot-generator/blob/master/db/init.sql) to create a demo table.
3. build your spring-boot project
4. setting your configuration in [This properties file](https://github.com/DestinyAries/springboot-generator/blob/master/src/test/resources/config.properties). The path is `springboot-generator/src/test/resources/config.properties`
5. run the `main` function in Class `CodeGenerator` in path `springboot-generator/src/test/java/com/destiny/generator`
6. go to your project and run the `spring-boot:run`, you can open `http://localhost:8080/swagger-ui.html` to see the generated api document.

# an demo config for xxx-project
See in [The configuration properties](https://github.com/DestinyAries/springboot-generator/blob/master/src/test/resources/config.properties)

# setting structure
```
|-java
    |-com.xxx.xxx (config -> file.package.path)
        |-api
            |-controller
            |-request
            |-response
        |-config
        |-enumeration
        |-exception
        |-service
        |-persistence
            |-model
            |-mapper
|-resources
    |-mapper
```

# plan
Now we lack the test case, we will plan to make the codes more strong and safe in the future.