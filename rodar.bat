@echo off
echo Carregando variaveis de ambiente...
call :reloadPath

echo.
echo Compilando projeto...
call mvn clean compile -q
if errorlevel 1 (
    echo Erro na compilacao!
    pause
    exit /b 1
)

echo Executando ProductivityHub...
call mvn javafx:run

:reloadPath
set "PATH=%PATH%;%USERPROFILE%\Apache\maven\apache-maven-3.9.5\bin"
if exist "C:\Program Files\Eclipse Adoptium\jdk-17.0.17.10-hotspot\bin" (
    set "PATH=%PATH%;C:\Program Files\Eclipse Adoptium\jdk-17.0.17.10-hotspot\bin"
)
exit /b

