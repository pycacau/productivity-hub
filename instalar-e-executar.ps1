# Script de Instalacao e Execucao - ProductivityHub
# Este script tenta instalar Java e Maven automaticamente e executar o projeto

Write-Host "ProductivityHub - Instalacao e Execucao Automatica" -ForegroundColor Cyan
Write-Host ""

# Verificar se Java esta instalado
Write-Host "Verificando Java..." -ForegroundColor Yellow
$javaInstalled = $false
try {
    $javaVersion = java -version 2>&1 | Select-String "version"
    if ($javaVersion) {
        Write-Host "Java encontrado!" -ForegroundColor Green
        $javaVersion
        $javaInstalled = $true
    }
} catch {
    Write-Host "Java nao encontrado" -ForegroundColor Red
}

# Verificar se Maven esta instalado
Write-Host "`nVerificando Maven..." -ForegroundColor Yellow
$mavenInstalled = $false
try {
    $mavenVersion = mvn -version 2>&1 | Select-String "Apache Maven"
    if ($mavenVersion) {
        Write-Host "Maven encontrado!" -ForegroundColor Green
        $mavenVersion
        $mavenInstalled = $true
    }
} catch {
    Write-Host "Maven nao encontrado" -ForegroundColor Red
}

# Se nao estiver instalado, tentar instalar
if (-not $javaInstalled -or -not $mavenInstalled) {
    Write-Host "`nTentando instalar dependencias..." -ForegroundColor Yellow
    
    # Verificar se Winget esta disponivel
    $wingetAvailable = $false
    try {
        $wingetCheck = winget --version 2>&1
        if ($LASTEXITCODE -eq 0) {
            $wingetAvailable = $true
            Write-Host "Winget encontrado!" -ForegroundColor Green
        }
    } catch {
        Write-Host "Winget nao encontrado" -ForegroundColor Red
    }
    
    # Verificar se Chocolatey esta disponivel
    $chocoAvailable = $false
    try {
        $chocoCheck = choco --version 2>&1
        if ($LASTEXITCODE -eq 0) {
            $chocoAvailable = $true
            Write-Host "Chocolatey encontrado!" -ForegroundColor Green
        }
    } catch {
        Write-Host "Chocolatey nao encontrado" -ForegroundColor Red
    }
    
    if ($wingetAvailable) {
        Write-Host "`nInstalando via Winget..." -ForegroundColor Cyan
        if (-not $javaInstalled) {
            Write-Host "Instalando Java..." -ForegroundColor Yellow
            winget install --id Microsoft.OpenJDK.17 --accept-package-agreements --accept-source-agreements
        }
        if (-not $mavenInstalled) {
            Write-Host "Instalando Maven..." -ForegroundColor Yellow
            winget install --id Apache.Maven --accept-package-agreements --accept-source-agreements
        }
    } elseif ($chocoAvailable) {
        Write-Host "`nInstalando via Chocolatey..." -ForegroundColor Cyan
        if (-not $javaInstalled) {
            Write-Host "Instalando Java..." -ForegroundColor Yellow
            choco install openjdk17 -y
        }
        if (-not $mavenInstalled) {
            Write-Host "Instalando Maven..." -ForegroundColor Yellow
            choco install maven -y
        }
    } else {
        Write-Host "`nNenhum gerenciador de pacotes encontrado!" -ForegroundColor Red
        Write-Host "`nInstalacao Manual Necessaria:" -ForegroundColor Yellow
        Write-Host "1. Instale Java JDK 17+: https://adoptium.net/" -ForegroundColor White
        Write-Host "2. Instale Maven: https://maven.apache.org/download.cgi" -ForegroundColor White
        Write-Host "3. Configure as variaveis de ambiente JAVA_HOME e MAVEN_HOME" -ForegroundColor White
        Write-Host "4. Reinicie o terminal e execute este script novamente" -ForegroundColor White
        Write-Host "`nOu instale o Winget/Chocolatey primeiro:" -ForegroundColor Yellow
        Write-Host "- Winget: Ja vem com Windows 11, ou instale do Microsoft Store" -ForegroundColor White
        Write-Host "- Chocolatey: https://chocolatey.org/install" -ForegroundColor White
        exit 1
    }
    
    Write-Host "`nAguardando instalacao..." -ForegroundColor Yellow
    Write-Host "Apos a instalacao, REINICIE O TERMINAL e execute este script novamente!" -ForegroundColor Red
    Write-Host "   (As variaveis de ambiente precisam ser recarregadas)" -ForegroundColor Yellow
    exit 0
}

# Se tudo estiver instalado, compilar e executar
Write-Host "`nCompilando o projeto..." -ForegroundColor Cyan
mvn clean compile

if ($LASTEXITCODE -eq 0) {
    Write-Host "`nCompilacao bem-sucedida!" -ForegroundColor Green
    Write-Host "`nExecutando a aplicacao..." -ForegroundColor Cyan
    mvn javafx:run
} else {
    Write-Host "`nErro na compilacao. Verifique os erros acima." -ForegroundColor Red
    exit 1
}
