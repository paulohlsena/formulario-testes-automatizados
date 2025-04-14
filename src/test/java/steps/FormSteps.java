package steps;

import com.aventstack.extentreports.Status;
import io.cucumber.java.*;
import io.cucumber.java.pt.*;
import utils.ExtentReportHooks;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import com.aventstack.extentreports.ExtentTest;

import java.time.Duration;

import static org.junit.Assert.*;

public class FormSteps {

    private static WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get("https://paulohlsena.github.io/formulario-testes/");
        ExtentReportHooks.log(Status.INFO, "Navegador iniciado e formulário carregado");
    }

    @After
    public void teardown(Scenario scenario) {
        if (scenario.isFailed()) {
            ExtentReportHooks.log(Status.FAIL, "Teste falhou: " + scenario.getName());
        } else {
            ExtentReportHooks.log(Status.PASS, "Teste passou: " + scenario.getName());
        }
        driver.quit();
    }

    @Dado("que o usuário acessa o formulário")
    public void que_usuario_acessa_o_formulario() {
        driver.navigate().refresh();
        ExtentReportHooks.log(Status.INFO, "Usuario acessou o formulario");
    }

    @Quando("ele preenche os dados corretamente")
    public void preenche_dados_corretamente() {
        preencherFormulario("João da Silva", "joao@email.com", "01/01/1990", "senha123", "senha123");
        ExtentReportHooks.log(Status.INFO, "Dados validos preenchidos");
    }

    @Quando("ele preenche os dados com idade inferior a 18 anos")
    public void preenche_menor_idade() {
        preencherFormulario("Maria Jovem", "maria@email.com", "01/01/2010", "senha123", "senha123");
        ExtentReportHooks.log(Status.INFO, "Preenchido com menor de idade");
    }

    @Quando("ele preenche senhas diferentes")
    public void preenche_senhas_diferentes() {
        preencherFormulario("Carlos Confuso", "carlos@email.com", "15/08/1995", "senha123", "senha456");
        ExtentReportHooks.log(Status.INFO, "Preenchido com senhas diferentes");
    }

    @E("aceita os termos")
    public void aceita_termos() {
        driver.findElement(By.id("terms")).click();
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        ExtentReportHooks.log(Status.INFO, "Aceitou os termos e submeteu");
    }

    @E("não aceita os termos")
    public void nao_aceita_termos() {
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        ExtentReportHooks.log(Status.INFO, "Não aceitou os termos e submeteu");
    }

    @Entao("o sistema exibe a mensagem {string}")
    public void sistema_exibe_mensagem(String mensagemEsperada) {
        WebElement mensagem = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("mensagem")));
        assertEquals(mensagemEsperada, mensagem.getText());
        ExtentReportHooks.log(Status.INFO, "Mensagem validada: " + mensagemEsperada);
    }

    public static WebDriver getDriver() {
        return driver;
    }

    private void preencherFormulario(String nome, String email, String nascimento, String senha, String confirmarSenha) {
        driver.findElement(By.id("name")).sendKeys(nome);
        driver.findElement(By.id("email")).sendKeys(email);
        driver.findElement(By.id("birthDate")).sendKeys(nascimento);
        driver.findElement(By.id("password")).sendKeys(senha);
        driver.findElement(By.id("passwordConfirm")).sendKeys(confirmarSenha);
    }
}