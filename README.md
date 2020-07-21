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
    
2. running the sql in `${YOUR_SOURCE_PATH}/springboot-generator/db` to mysql. Or you can create the tables you need.
3. build your spring-boot project
4. setting your configuration in `${YOUR_SOURCE_PATH}/springboot-generator/src/test/resources`
5. run the `main` function in Class `CodeGenerator` in path `${YOUR_SOURCE_PATH}/springboot-generator/src/test/java/com/destiny/generator`
6. go to your project and run the `spring-boot:run`, you can open `http://localhost:8080/swagger-ui.html` to see the generated api document.

# an demo config for xxx-project
```
# db configuration
db.type=mysql
db.driver=com.mysql.cj.jdbc.Driver
db.url=jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&characterEncoding=utf-8&useSSL=false&allowMultiQueries=true
db.username=username
db.password=password

# other
author=Destiny

# the output path to put the generated java files
file.output.path=/Users/home/sources/demo-server/src/main/java
# package path
file.package.path=com.destiny.demoserver

# --- table configurations ---
# use to remove prefix
table.prefix.list=xtl_,
# the tables need to generate
table.name.list=xtl_admin_user,

# swagger config
swagger.projectName=The Demo Server
swagger.version=1.0.0
swagger.description=This project use to show how to use this generator

# pom config
pom.springboot.version=2.3.1.RELEASE
pom.project.groupId=com.destiny
pom.project.artifactId=demo-server
pom.project.version=0.0.1-SNAPSHOT
pom.project.name=demo-server
pom.project.description=To show how to use the generator for project
pom.properties.java.version=1.8
pom.properties.mybatisplus.version=3.3.2
pom.properties.pagehelper.version=5.1.11
pom.properties.swagger2.version=2.9.2
```

# setting structure
```
|-java
    |-com.xxx.xxx (config -> file.package.path)
        |-api
            |-controller
            |-response
        |-service
        |-persistence
            |-model
            |-mapper
|-resources
    |-mapper
```

# Remind
If you want to use this project to be the destination project after generate the codes, then you need to change the project name and update the pom configuration (such as project name, not useful dependencies after generated). 

# plan
1. Now we lack the test case, we will plan to make the codes more strong and safe in the future.