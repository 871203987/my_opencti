@echo off
setlocal enabledelayedexpansion

set CLASSPATH=target/classes

for %%f in (target/dependency/*.jar) do (
    set CLASSPATH=!CLASSPATH!;target/dependency/%%f
)

java -cp "%CLASSPATH%" io.opencti.OpenCTIApplication
