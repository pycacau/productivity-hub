# Script para instalar Maven automaticamente (sem precisar de admin)

Write-Host "Baixando e instalando Apache Maven..." -ForegroundColor Cyan

$mavenVersion = "3.9.5"
$mavenUrl = "https://dlcdn.apache.org/maven/maven-3/$mavenVersion/binaries/apache-maven-$mavenVersion-bin.zip"
$tempDir = $env:TEMP
$mavenZip = "$tempDir\maven.zip"
$installDir = "$env:USERPROFILE\Apache\maven"

# Criar diretorio de instalacao
if (-not (Test-Path $installDir)) {
    New-Item -ItemType Directory -Path $installDir -Force | Out-Null
}

# Baixar Maven
Write-Host "Baixando Maven $mavenVersion..." -ForegroundColor Yellow
try {
    Invoke-WebRequest -Uri $mavenUrl -OutFile $mavenZip -UseBasicParsing
} catch {
    Write-Host "Erro ao baixar. Tentando URL alternativa..." -ForegroundColor Yellow
    $mavenUrl = "https://archive.apache.org/dist/maven/maven-3/$mavenVersion/binaries/apache-maven-$mavenVersion-bin.zip"
    Invoke-WebRequest -Uri $mavenUrl -OutFile $mavenZip -UseBasicParsing
}

# Extrair
Write-Host "Extraindo..." -ForegroundColor Yellow
Expand-Archive -Path $mavenZip -DestinationPath $tempDir -Force
$extractedDir = Get-ChildItem "$tempDir\apache-maven-*" | Select-Object -First 1

# Mover para diretorio final
Write-Host "Instalando..." -ForegroundColor Yellow
if (Test-Path "$installDir\apache-maven-$mavenVersion") {
    Remove-Item "$installDir\apache-maven-$mavenVersion" -Recurse -Force
}
Move-Item $extractedDir.FullName "$installDir\apache-maven-$mavenVersion" -Force

# Configurar variaveis de ambiente (usuario)
Write-Host "Configurando variaveis de ambiente..." -ForegroundColor Yellow
[System.Environment]::SetEnvironmentVariable("MAVEN_HOME", "$installDir\apache-maven-$mavenVersion", "User")
$currentPath = [System.Environment]::GetEnvironmentVariable("Path", "User")
if ($currentPath -notlike "*$installDir\apache-maven-$mavenVersion\bin*") {
    $newPath = if ($currentPath) { "$currentPath;$installDir\apache-maven-$mavenVersion\bin" } else { "$installDir\apache-maven-$mavenVersion\bin" }
    [System.Environment]::SetEnvironmentVariable("Path", $newPath, "User")
}

# Atualizar PATH na sessao atual
$env:MAVEN_HOME = "$installDir\apache-maven-$mavenVersion"
$env:Path += ";$installDir\apache-maven-$mavenVersion\bin"

# Limpar
Remove-Item $mavenZip -Force -ErrorAction SilentlyContinue

Write-Host "`nMaven instalado com sucesso!" -ForegroundColor Green
Write-Host "MAVEN_HOME: $installDir\apache-maven-$mavenVersion" -ForegroundColor Cyan
Write-Host "`nTestando instalacao..." -ForegroundColor Yellow
mvn -version
