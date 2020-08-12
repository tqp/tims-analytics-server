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
