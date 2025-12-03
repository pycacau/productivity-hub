# 📦 Guia de Instalação - ProductivityHub

## Pré-requisitos

Para executar o ProductivityHub, você precisa ter instalado:

### 1. Java JDK 17 ou superior

**Windows:**
1. Baixe o JDK 17+ do site oficial: https://adoptium.net/ ou https://www.oracle.com/java/technologies/downloads/
2. Instale o JDK
3. Configure a variável de ambiente `JAVA_HOME` apontando para a pasta de instalação do JDK
4. Adicione `%JAVA_HOME%\bin` ao PATH do sistema

**Verificar instalação:**
```bash
java -version
javac -version
```

### 2. Apache Maven 3.8+

**Windows:**
1. Baixe o Maven: https://maven.apache.org/download.cgi
2. Extraia em uma pasta (ex: `C:\Program Files\Apache\maven`)
3. Configure a variável de ambiente `MAVEN_HOME` apontando para a pasta do Maven
4. Adicione `%MAVEN_HOME%\bin` ao PATH do sistema

**Verificar instalação:**
```bash
mvn -version
```

### 3. JavaFX (Opcional - será baixado automaticamente pelo Maven)

O JavaFX será baixado automaticamente através das dependências do Maven.

## Instalação Rápida com Chocolatey (Windows)

Se você tem o Chocolatey instalado:

```powershell
# Instalar Java
choco install openjdk17

# Instalar Maven
choco install maven

# Verificar instalações
java -version
mvn -version
```

## Após a Instalação

1. Abra um novo terminal (para carregar as variáveis de ambiente)
2. Navegue até a pasta do projeto:
```bash
cd C:\Projetos\productivity-hub
```

3. Compile o projeto:
```bash
mvn clean compile
```

4. Execute a aplicação:
```bash
mvn javafx:run
```

## Alternativa: Usar uma IDE

Você também pode abrir o projeto em uma IDE como:
- **IntelliJ IDEA** (recomendado) - Detecta automaticamente o Maven
- **Eclipse** - Importe como projeto Maven
- **VS Code** - Com extensões Java e Maven

As IDEs geralmente têm gerenciamento automático de dependências e execução simplificada.

## Solução de Problemas

### Erro: "mvn não é reconhecido"
- Verifique se o Maven está no PATH
- Reinicie o terminal após adicionar ao PATH
- Verifique se `MAVEN_HOME` está configurado corretamente

### Erro: "java não é reconhecido"
- Verifique se o Java está no PATH
- Reinicie o terminal após adicionar ao PATH
- Verifique se `JAVA_HOME` está configurado corretamente

### Erro ao executar JavaFX
- Certifique-se de estar usando Java 17 ou superior
- O JavaFX será baixado automaticamente pelo Maven

## Links Úteis

- [Download Java JDK](https://adoptium.net/)
- [Download Maven](https://maven.apache.org/download.cgi)
- [Documentação JavaFX](https://openjfx.io/)

