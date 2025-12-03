# Script de Execução Rápida - ProductivityHub
# Use este script se Java e Maven já estão instalados

Write-Host "🚀 Executando ProductivityHub..." -ForegroundColor Cyan
Write-Host ""

# Verificar Java
try {
    java -version | Out-Null
} catch {
    Write-Host "❌ Java não encontrado! Execute 'instalar-e-executar.ps1' primeiro." -ForegroundColor Red
    exit 1
}

# Verificar Maven
try {
    mvn -version | Out-Null
} catch {
    Write-Host "❌ Maven não encontrado! Execute 'instalar-e-executar.ps1' primeiro." -ForegroundColor Red
    exit 1
}

# Compilar
Write-Host "🔨 Compilando..." -ForegroundColor Yellow
mvn clean compile

if ($LASTEXITCODE -ne 0) {
    Write-Host "❌ Erro na compilação!" -ForegroundColor Red
    exit 1
}

# Executar
Write-Host "`n✅ Compilação concluída!" -ForegroundColor Green
Write-Host "🚀 Iniciando aplicação..." -ForegroundColor Cyan
Write-Host ""

mvn javafx:run

