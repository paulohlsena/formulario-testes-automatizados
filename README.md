# 🧪 Projeto de Testes Automatizados com Selenium e JUnit

Este projeto tem como objetivo a automação de testes funcionais de um formulário web utilizando **Selenium WebDriver** e **JUnit**, com geração de relatórios personalizados através do **ExtentReports**.

---

## ✅ Funcionalidades

- Preenchimento automático de formulários
- Validação de campos obrigatórios e mensagens de erro
- Captura de prints em caso de falhas
- Geração de relatórios detalhados em HTML
- Organização de testes em estrutura de projeto Maven

---

## 🛠️ Tecnologias Utilizadas

- [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [JUnit 5](https://junit.org/junit5/)
- [Selenium WebDriver](https://www.selenium.dev/)
- [ExtentReports](https://extentreports.com/)
- [Maven](https://maven.apache.org/)
- [Google ChromeDriver](https://sites.google.com/a/chromium.org/chromedriver/)
- [Git](https://git-scm.com/)

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

Após a execução dos testes, um relatório é gerado automaticamente no caminho:

```
target/relatorio_teste.html
```

Para visualizá-lo, basta abrir o arquivo `.html` no navegador.

---

## 📁 Estrutura do Projeto

```
testeformulario/
│
├── src/
│   └── test/
│       └── java/
│           └── org.example/
│               └── FormularioTest.java
├── target/
│   └── relatorio_teste.html
├── pom.xml
└── .gitignore
