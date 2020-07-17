# springboot-generator
To generate codes quickly (springboot+mysql+swagger)

# steps
1. prepare your local environment.
    * jdk8
    * mysql
    * maven
    
2. running the sql in `${YOUR_SOURCE_PATH}/springboot-generator/db` to mysql. Or you can create the tables you need.
3. setting your configuration in `${YOUR_SOURCE_PATH}/springboot-generator/src/test/resources`
4. run the `main` function in Class `CodeGenerator` in path `${YOUR_SOURCE_PATH}/springboot-generator/src/test/java/com/destiny/generator`
5. run the `spring-boot:run` and open `http://localhost:8080/swagger-ui.html` can see the generated api document.

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
1. plan to generate a project outside, not only generate the codes in this project.
2. let the codes more strong and safe.