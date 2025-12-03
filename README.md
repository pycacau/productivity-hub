# 🚀 ProductivityHub

<div align="center">

![Java](https://img.shields.io/badge/Java-17-orange?style=for-the-badge&logo=java)
![JavaFX](https://img.shields.io/badge/JavaFX-21-blue?style=for-the-badge)
![Maven](https://img.shields.io/badge/Maven-3.8+-red?style=for-the-badge&logo=apache-maven)
![License](https://img.shields.io/badge/License-MIT-green?style=for-the-badge)

**Dashboard de Produtividade Pessoal com Interface Moderna e Analytics Avançados**

[Características](#-características) • [Tecnologias](#-tecnologias) • [Instalação](#-instalação) • [Uso](#-uso) • [Estrutura](#-estrutura-do-projeto)

</div>

---

## 📋 Sobre o Projeto

**ProductivityHub** é uma aplicação desktop moderna desenvolvida em Java com JavaFX que oferece um sistema completo de gerenciamento de tarefas e projetos pessoais, acompanhado de analytics avançados e visualizações interativas.

Este projeto foi desenvolvido para demonstrar:
- ✨ Interface gráfica moderna e intuitiva
- 📊 Analytics e visualizações de dados
- 💾 Persistência de dados local (JSON)
- 🎨 Design responsivo e atraente
- 🔄 Arquitetura MVC bem estruturada

---

## ✨ Características

### 🎯 Gerenciamento de Tarefas
- ✅ Criar, editar e excluir tarefas
- 🏷️ Categorização e tags
- ⚡ Sistema de prioridades (Baixa, Média, Alta, Urgente)
- 📅 Datas de vencimento
- 📝 Descrições detalhadas
- ✅ Marcação de conclusão

### 📁 Gerenciamento de Projetos
- 🎨 Projetos com cores personalizadas
- 📊 Acompanhamento de progresso
- 📈 Status de projeto (Planejamento, Ativo, Pausado, Concluído)
- 🔗 Associação de tarefas a projetos

### 📊 Dashboard e Analytics
- 📈 Estatísticas em tempo real
- 📊 Gráficos interativos (Pizza, Barras)
- 🔥 Sequência de dias (streak)
- 📉 Taxa de conclusão
- ⏱️ Análise de tempo estimado vs. real
- 📋 Distribuição por categoria e prioridade

### 🎨 Interface Moderna
- 🌙 Tema escuro moderno
- 🎯 Navegação intuitiva
- 📱 Design responsivo
- 🎨 Cores vibrantes e atraentes
- ✨ Animações suaves

---

## 🛠️ Tecnologias

- **Java 17** - Linguagem de programação
- **JavaFX 21** - Framework de interface gráfica
- **Maven** - Gerenciamento de dependências
- **Gson** - Serialização/deserialização JSON
- **CSS** - Estilização moderna da interface

---

## 📦 Instalação

### Pré-requisitos
- **Sistema Operacional**: Windows 10/11 (scripts otimizados para Windows)
- **Java JDK 17** ou superior
- **Maven 3.8+** (ou use os scripts de instalação automática)
- **Winget ou Chocolatey** (opcional, para instalação automática)

> 💡 **Dica**: Os scripts de instalação automática detectam e instalam automaticamente as dependências necessárias!

### 🚀 Instalação Automática (Recomendado)

#### Windows

**Opção 1: Script Automático (Instala tudo automaticamente)**
```powershell
# Execute o script que instala Java, Maven e executa a aplicação
.\instalar-e-executar.ps1
```

Este script irá:
- ✅ Verificar se Java e Maven estão instalados
- 📦 Instalar automaticamente via Winget ou Chocolatey (se disponível)
- 🔨 Compilar o projeto
- 🚀 Executar a aplicação

**Opção 2: Instalação Manual do Maven (se Java já estiver instalado)**
```powershell
.\instalar-maven.ps1
```

**Opção 3: Execução Rápida (se tudo já estiver instalado)**
```powershell
# PowerShell
.\executar.ps1

# Ou Batch
.\rodar.bat
```

### 📋 Instalação Manual

1. **Instale Java JDK 17+**
   - Download: https://adoptium.net/
   - Configure a variável de ambiente `JAVA_HOME`

2. **Instale Maven 3.8+**
   - Download: https://maven.apache.org/download.cgi
   - Configure a variável de ambiente `MAVEN_HOME`
   - Adicione `%MAVEN_HOME%\bin` ao PATH

3. **Clone o repositório**
```bash
git clone https://github.com/seu-usuario/productivity-hub.git
cd productivity-hub
```

4. **Compile o projeto**
```bash
mvn clean compile
```

5. **Execute a aplicação**
```bash
mvn javafx:run
```

### 📝 Scripts Disponíveis

| Script | Descrição | Quando Usar |
|--------|-----------|-------------|
| `instalar-e-executar.ps1` | Instala Java e Maven automaticamente e executa | Primeira vez ou se nada estiver instalado |
| `instalar-maven.ps1` | Instala apenas o Maven | Se Java já estiver instalado |
| `executar.ps1` | Executa a aplicação (PowerShell) | Quando tudo já estiver instalado |
| `rodar.bat` | Script batch para execução rápida | Execução rápida no Windows |
| `executar.bat` | Alternativa batch | Execução alternativa |

**Exemplo de uso:**
```powershell
# Primeira execução (instala tudo)
.\instalar-e-executar.ps1

# Execuções seguintes (já tem tudo instalado)
.\rodar.bat
```

---

## 🎮 Uso

### Primeiros Passos

1. **Inicie a aplicação** - O dashboard principal será exibido automaticamente

2. **Criar uma Tarefa**
   - Clique em "Tarefas" no menu superior
   - Clique em "+ Nova Tarefa"
   - Preencha os campos e salve

3. **Criar um Projeto**
   - Clique em "Projetos" no menu superior
   - Clique em "+ Novo Projeto"
   - Configure nome, descrição, cor e status

4. **Visualizar Analytics**
   - Clique em "Analytics" no menu superior
   - Explore os gráficos e estatísticas

### Dicas
- ✅ Marque tarefas como concluídas clicando no checkbox
- 🎨 Personalize projetos com cores hexadecimais
- 📊 Acompanhe seu progresso no dashboard
- 🔥 Mantenha sua sequência de dias ativa!

---

## 📸 Screenshots

### Dashboard Principal
O dashboard exibe estatísticas em tempo real, cards coloridos com métricas importantes e um resumo rápido das atividades.

### Gerenciamento de Tarefas
Interface intuitiva para criar, editar e gerenciar tarefas com sistema de prioridades e categorias.

### Analytics
Visualizações interativas com gráficos de pizza e barras mostrando distribuição de tarefas por status, categoria e prioridade.

---

## 📁 Estrutura do Projeto

```
productivity-hub/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/productivityhub/
│   │   │       ├── App.java                 # Classe principal
│   │   │       ├── controller/              # Controladores MVC
│   │   │       │   ├── MainController.java
│   │   │       │   ├── DashboardController.java
│   │   │       │   ├── TaskController.java
│   │   │       │   ├── ProjectController.java
│   │   │       │   └── AnalyticsController.java
│   │   │       ├── model/                   # Modelos de dados
│   │   │       │   ├── Task.java
│   │   │       │   ├── Project.java
│   │   │       │   └── Analytics.java
│   │   │       └── service/                 # Serviços
│   │   │           ├── DataService.java
│   │   │           ├── AnalyticsService.java
│   │   │           ├── LocalDateAdapter.java
│   │   │           └── LocalDateTimeAdapter.java
│   │   └── resources/
│   │       └── styles.css                   # Estilos CSS
├── data/                                     # Dados persistidos (JSON)
│   ├── tasks.json
│   └── projects.json
├── pom.xml                                   # Configuração Maven
├── instalar-e-executar.ps1                  # Script de instalação automática
├── instalar-maven.ps1                        # Script para instalar Maven
├── executar.ps1                              # Script de execução (PowerShell)
├── rodar.bat                                 # Script de execução (Batch)
├── executar.bat                              # Alternativa batch
├── INSTALACAO.md                             # Guia detalhado de instalação
├── LICENSE                                   # Licença MIT
└── README.md                                 # Este arquivo
```

---

## 🔧 Configuração Avançada

### Personalizar Cores
Edite o arquivo `src/main/resources/styles.css` para personalizar o tema da aplicação.

### Modificar Persistência
O `DataService` atualmente usa JSON. Você pode estender para usar banco de dados modificando a classe `DataService.java`.

### Solução de Problemas

**Erro: "mvn não é reconhecido"**
- Execute `.\instalar-maven.ps1` para instalar o Maven
- Ou reinicie o terminal após instalar manualmente

**Erro: "java não é reconhecido"**
- Instale o Java JDK 17+ de https://adoptium.net/
- Execute `.\instalar-e-executar.ps1` para instalação automática

**Aplicação não abre**
- Verifique se Java e Maven estão no PATH
- Reinicie o terminal após instalar dependências
- Execute `mvn clean compile` antes de `mvn javafx:run`

Para mais detalhes, consulte o arquivo `INSTALACAO.md`.

---

## 🤝 Contribuindo

Contribuições são bem-vindas! Sinta-se à vontade para:

1. Fazer um Fork do projeto
2. Criar uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abrir um Pull Request

---

## 📝 Licença

Este projeto está sob a licença MIT. Veja o arquivo `LICENSE` para mais detalhes.

---

## 👨‍💻 Autor

Desenvolvido com ❤️ para demonstrar habilidades em Java, JavaFX e desenvolvimento de interfaces modernas.

---

## 🌟 Destaques

- ✅ Interface moderna e intuitiva
- 📊 Analytics avançados com visualizações
- 💾 Persistência de dados local
- 🎨 Design responsivo e atraente
- 🔄 Arquitetura MVC bem estruturada
- 🚀 Scripts de instalação automática
- 📱 Pronto para destacar no LinkedIn e GitHub!

## 🎯 Início Rápido

```bash
# Windows - Instalação e execução automática
.\instalar-e-executar.ps1

# Ou se já tiver tudo instalado
.\rodar.bat
```

---

<div align="center">

**⭐ Se este projeto foi útil, considere dar uma estrela! ⭐**

Feito com Java ☕ e JavaFX 🎨

</div>

