# 🧪 Projeto de Testes Automatizados com Selenium, Cucumber e ExtentReports

Este projeto tem como objetivo a automação de testes funcionais de um formulário web utilizando **Selenium WebDriver**, **JUnit 4**, **Cucumber** e **ExtentReports** para geração de relatórios detalhados.

O projeto cobre diferentes cenários de teste com foco na validação de um formulário de cadastro de usuário, realizando ações como: validação de idade mínima, correspondência de senhas, e aceitação dos termos de uso.


## ✅ Funcionalidades


- **Automação de Testes Funcionais**: Teste de diferentes cenários de formulário, incluindo sucesso e falhas de validação.
- **Testes Independentes**: O projeto é estruturado de forma que o Selenium abre o navegador 4 vezes para realizar os testes de forma independente.
- **Geração de Relatórios**: Relatórios detalhados em formato HTML gerados automaticamente pelo **ExtentReports**, incluindo capturas de tela em caso de falhas.
- **Estrutura Gherkin com Cucumber**: Definição dos testes de forma legível e compreensível, utilizando o modelo Gherkin no Cucumber.
- **Execução com JUnit 4 e Maven**: Testes organizados e executados através de **JUnit 4** com suporte para **Maven**.

---

## 🛠️ Tecnologias Utilizadas

- [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [JUnit 4](https://junit.org/junit4/) (Framework de teste unitário para Java)
- [Cucumber](https://cucumber.io/) (Framework para testes comportamentais BDD)
- [Selenium WebDriver](https://www.selenium.dev/) (Para controle do navegador)
- [Extent Reports](https://extentreports.com/) (Para geração de relatórios personalizados)
- [Maven](https://maven.apache.org/) (Gerenciamento de dependências e build)
- [WebDriverManager](https://bonigarcia.dev/webdrivermanager/) (Gerenciamento automático do ChromeDriver)
- [Google Chrome](https://www.google.com/chrome/) (Navegador utilizado para os testes)
- [Git](https://git-scm.com/) (Sistema de controle de versão)
- [GitHub Pages](https://pages.github.com/) (Plataforma de hospedagem do formulário web testado)
- [HTML5, CSS3, JavaScript, Bootstrap](https://getbootstrap.com/) (Tecnologias da página web do formulário)

---

## 🚀 Como Executar o Projeto

### 1. Clone o repositório

```bash
git clone https://github.com/paulohlsena/formulario-testes-automatizados.git
cd formulario-testes-automatizados
```

### 2. Instale as dependências

```bash
mvn install
```

### 3. Execute os testes

```bash
mvn test
```

---

## 📊 Relatórios de Teste

Após a execução dos testes, dois tipos de relatórios serão gerados:

- **Relatório do Cucumber:** Um relatório padrão do Cucumber, este relatório fornece uma visão geral dos cenários executados, diretamente ligados à linguagem Gherkin.

```
target/cucumber-reports.html
```
- **Relatório do Extent Reports:** Um relatório HTML mais detalhado, este relatório detalha cada cenário e seus passos individuais, além de permitir a visualização de logs e informações adicionais. Cada passo dentro de um cenário é reportado individualmente.

```
target/extent-report.html
```

Para visualizá-lo, basta abrir o arquivo `.html` no navegador.

---

## 📁 Estrutura do Projeto

```
formulario-testes-automatizados/
│
├── src/
│   └── test/
│       └── java/
│           ├── runner/
│           │   └── RunnerTest.java (Responsável por executar os testes Cucumber)
│           └── steps/
│               └── FormSteps.java (Definições dos passos dos cenários)
│           └── utils/
│               └── ExtentReportHooks.java (Configuração do Extent Reports)
│       └── resources/
│           └── features/
│               └── formulario.feature (Arquivo contendo os cenários de teste em Gherkin)
├── target/
│   ├── cucumber-reports/
│   │   └── ... (Relatórios do Cucumber)
│   └── extent-report.html (Relatório do Extent Reports)
├── pom.xml
└── .gitignore
