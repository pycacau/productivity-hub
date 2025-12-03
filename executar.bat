@echo off
echo 🚀 Executando ProductivityHub...
echo.

REM Verificar Java
java -version >nul 2>&1
if errorlevel 1 (
    echo ❌ Java não encontrado! Execute 'instalar-e-executar.ps1' primeiro.
    pause
    exit /b 1
)

REM Verificar Maven
mvn -version >nul 2>&1
if errorlevel 1 (
    echo ❌ Maven não encontrado! Execute 'instalar-e-executar.ps1' primeiro.
    pause
    exit /b 1
)

REM Compilar
echo 🔨 Compilando...
call mvn clean compile
if errorlevel 1 (
    echo ❌ Erro na compilação!
    pause
    exit /b 1
)

REM Executar
echo.
echo ✅ Compilação concluída!
echo 🚀 Iniciando aplicação...
echo.

call mvn javafx:run

pause

