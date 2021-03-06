# Tim's Analytics App Server

## Notes

#### Tomcat Environment Config
Create/update /tomcat/bin/setenv.bat.  
```text
echo "Running setenv.bat"
set JAVA_OPTS=%JAVA_OPTS% -Dspring.profiles.active=dev -agentlib:jdwp=transport=dt_socket,address=5005,server=y,suspend=n
```

#### Oracle Query Note
To change the order of columns within a table:
```text
create table
    newTable
as
select
    col3,
    col2,
    col1
from
    table;
rename table to oldTable;
rename newTable to table;
```

## Install Local JAR
```text
mvn install:install-file \
   -Dfile=<path-to-file> \
   -DgroupId=<group-id> \
   -DartifactId=<artifact-id> \
   -Dversion=<version> \
   -Dpackaging=<packaging> \
   -DgeneratePom=true
```

`mvn install:install-file -Dfile=C:\Users\tqp\.m2\repository\com\timsanalytics\jar\external-jar\1.0.0 -DgroupId=com.timsanalytics.jar -DartifactId=external-jar -Dversion=1.0.0 -Dpackaging=jar -DgeneratePom
 =true`

## AWS Configruation

#### Deploy server to Elastic Beanstalk (with https)

Elastic Beanstalk -> Environments -> <environment-name> -> Configuration  
Capacity:
- Environment Type: Load Balanced
- Min: 1
- Max: 1
Apply, confirm, and wait.
Re-enter configuration:  
Load Balancer:  
- Add Listener. Listener Port: 443, Listener Protocol: HTTPS (and Instance Port: 80, Instance Protocol: HTTP, if asked)
- Use ELBSecurityPolicy-2016-08
- Disable Port 80.
Apply, confirm, and wait.

Route 53:
- Create Record
- Provide URL
- Alias to Elastic Beanstalk environment
- Zone: US East
- Choose Elastic Beanstalk environment
- Record Type: A - Routes traffic to an IPv4 address and some AWS resources.

MAKE SURE YOU UPDATE CORS ON THE SERVER!!!

#### Spring Profiles Active
To set "spring.profiles.active" the way you would when starting Tomcat, open ElasticBeanstalk, 
go to Configuration -> Software -> Environment Properties. Then, set:  
```text
name = spring.profiles.active, value = prod
```
  
Don't get fooled by all the websites that say to set SPRING_PROFILES_ACTIVE.
