# Poseidon Inc.
## Technical:

1. Spring boot v2.4.0-RC1
2. Spring security
3. Java 8
4. JUnit 5
5. H2
6. Thymeleaf
7. Bootstrap v.4.3.1


## Setup with Intellij IDE
1. Create project from Initializr: File > New > project > Spring Initializr
2. Add lib repository into pom.xml
3. Add folders
    - Source root: src/main/java
    - View: src/main/resources
    - Static: src/main/resource/static
4. Create database with name "poseidoninc" as configuration in application.properties
5. Run sql script to create table doc/data.sql

## Implement a feature:
1. Create mapping domain class and place in package com.nnk.springboot.domain
2. Create repository class and place in package com.nnk.springboot.repositories
3. Create controller class and place in package com.nnk.springboot.controllers
4. Create view files and place in src/main/resource/templates

## Implement a constraint:
1. Create a constraint following the already-made @interface constraint and place in the same package : com.nnk.springboot.constraint
2. Create the validator of the constraint following the already-made validator classes and place in the same package : com.nnk.springboot.constraint.validator

## Write unit test
1. Create unit test and place in package com.nnk.springboot in folder test/java

## Write integration test
2. Create integration test and place in package com.nnk.springboot.integration in folder test/java

## Security
1. Create user service to load user from  database and place in package com.nnk.springboot.services
2. Add configuration class and place in package com.nnk.springboot.config