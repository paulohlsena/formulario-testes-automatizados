package org.example;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FormularioTest {

    private WebDriver driver;
    private WebDriverWait wait;
    private ExtentReports extent;
    private ExtentTest test;

    @BeforeAll
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\paulo\\Desktop\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");

        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.manage().window().maximize();
        driver.get("https://paulohlsena.github.io/formulario-testes/");

        ExtentSparkReporter spark = new ExtentSparkReporter("target/relatorio_teste.html");
        extent = new ExtentReports();
        extent.attachReporter(spark);
    }

    @AfterAll
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
        extent.flush();
    }

    @BeforeEach
    public void recarregarPagina() {
        driver.navigate().refresh();
    }

    private void preencherFormulario(String nome, String email, String nascimento, String senha, String confirmarSenha, boolean aceitarTermos) {
        driver.findElement(By.id("name")).sendKeys(nome);
        driver.findElement(By.id("email")).sendKeys(email);
        driver.findElement(By.id("birthDate")).sendKeys(nascimento);
        driver.findElement(By.id("password")).sendKeys(senha);
        driver.findElement(By.id("passwordConfirm")).sendKeys(confirmarSenha);
        if (aceitarTermos) {
            driver.findElement(By.id("terms")).click();
        }
    }

    private void capturarScreenshot(String nomeArquivo, ExtentTest test) {
        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String caminho = "target/screenshots/" + nomeArquivo + ".png";
        try {
            Files.createDirectories(Paths.get("target/screenshots"));
            Files.copy(src.toPath(), Paths.get(caminho));
            test.addScreenCaptureFromPath(caminho);
        } catch (IOException e) {
            test.warning("❗ Erro ao capturar screenshot: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("✅ DADO que eu entrei no formulário de login - cadastro com sucesso")
    public void testCadastroComSucesso() {
        test = extent.createTest("Cadastro com sucesso");

        try {
            preencherFormulario("João da Silva", "joao@email.com", "01/01/1990", "senha123", "senha123", true);
            test.info("QUANDO preencho o formulario corretamente");
            driver.findElement(By.cssSelector("button[type='submit']")).click();

            WebElement mensagem = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("mensagem")));
            wait.until(ExpectedConditions.textToBePresentInElement(mensagem, "Cadastro realizado com sucesso!"));

            Assertions.assertEquals("Cadastro realizado com sucesso!", mensagem.getText());
            test.pass("ENTAO aparece a mensagem de sucesso");

            capturarScreenshot("cadastro_sucesso", test);

        } catch (Exception e) {
            capturarScreenshot("erro_cadastro_sucesso", test);
            test.fail("❌ Teste falhou: " + e.getMessage());
            Assertions.fail(e);
        }
    }

    @Test
    @DisplayName("🧒 Não deve permitir cadastro de menor de idade")
    public void testCadastroMenorDeIdade() {
        test = extent.createTest("Cadastro de menor de idade");

        try {
            preencherFormulario("Maria Jovem", "maria@email.com", "01/05/2010", "senha123", "senha123", true);
            driver.findElement(By.cssSelector("button[type='submit']")).click();

            WebElement erro = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("mensagem")));
            wait.until(ExpectedConditions.textToBePresentInElement(erro, "Você precisa ter pelo menos 18 anos."));

            Assertions.assertTrue(erro.getText().contains("Você precisa ter pelo menos 18 anos."));
            test.pass("✅ Validacao de idade realizada com sucesso");
            capturarScreenshot("menor_idade", test);

        } catch (Exception e) {
            capturarScreenshot("erro_menor_idade", test);
            test.fail("❌ Teste falhou: " + e.getMessage());
            Assertions.fail(e);
        }
    }

    @Test
    @DisplayName("🔐 Não deve permitir cadastro com senhas diferentes")
    public void testCadastroSenhasDiferentes() {
        test = extent.createTest("Cadastro com senhas diferentes");

        try {
            preencherFormulario("Carlos Confuso", "carlos@email.com", "15/08/1995", "senha123", "senha456", true);
            driver.findElement(By.cssSelector("button[type='submit']")).click();

            WebElement erro = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("mensagem")));
            wait.until(ExpectedConditions.textToBePresentInElement(erro, "As senhas não coincidem."));

            Assertions.assertTrue(erro.getText().contains("As senhas não coincidem."));
            test.pass("✅ Validacao de senhas diferentes realizada");
            capturarScreenshot("senhas_diferentes", test);

        } catch (Exception e) {
            capturarScreenshot("erro_senhas_diferentes", test);
            test.fail("❌ Teste falhou: " + e.getMessage());
            Assertions.fail(e);
        }
    }

    @Test
    @DisplayName("📜 Não deve permitir cadastro sem aceitar os termos")
    public void testCadastroSemAceitarTermos() {
        test = extent.createTest("Cadastro sem aceitar termos");

        try {
            preencherFormulario("Lucas Livre", "lucas@email.com", "30/12/1992", "senha123", "senha123", false);
            driver.findElement(By.cssSelector("button[type='submit']")).click();

            WebElement erro = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("mensagem")));
            wait.until(ExpectedConditions.textToBePresentInElement(erro, "Você deve aceitar os termos."));

            Assertions.assertTrue(erro.getText().contains("Você deve aceitar os termos."));
            test.pass("✅ Validacao de termos nao aceitos realizada");
            capturarScreenshot("termos_nao_aceitos", test);

        } catch (Exception e) {
            capturarScreenshot("erro_termos_nao_aceitos", test);
            test.fail("❌ Teste falhou: " + e.getMessage());
            Assertions.fail(e);
        }
    }
}
