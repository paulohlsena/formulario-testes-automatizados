package utils;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import io.cucumber.java.*;
import org.openqa.selenium.*;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import steps.FormSteps;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

public class ExtentReportHooks {

    private static ExtentReports extent;
    private static ExtentTest feature;
    private static ExtentTest scenario;
    private static ThreadLocal<ExtentTest> stepLogger = new ThreadLocal<>();

    @BeforeAll
    public static void beforeAll() {
        ExtentSparkReporter spark = new ExtentSparkReporter("target/extent-report.html");
        spark.config().setTheme(Theme.DARK);
        spark.config().setDocumentTitle("Relatorio de Testes");
        spark.config().setReportName("Resultados dos Cenarios");

        extent = new ExtentReports();
        extent.attachReporter(spark);
        new File("target/screenshots").mkdirs();
    }

    @Before
    public void beforeScenario(Scenario sc) {
        String featureName = "Unknown Feature"; // Valor padrão caso o caminho seja nulo
        if (sc.getUri() != null && sc.getUri().getPath() != null) {
            featureName = sc.getUri().getPath().replaceAll(".*/", "").replace(".feature", "");
        }
        feature = extent.createTest("Feature: " + featureName);
        scenario = feature.createNode(sc.getName());
    }

    @AfterStep
    public void afterStep(Scenario sc) {
        stepLogger.set(scenario.createNode(sc.getName()));
        if (sc.isFailed()) {
            String screenshotPath = captureScreenshot(FormSteps.getDriver());
            stepLogger.get().fail("Passo falhou: " + sc.getStatus(),
                    MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
        } else {
            stepLogger.get().pass("Passo executado com sucesso");
        }
    }

    @After
    public void afterScenario(Scenario sc) {
        if (sc.isFailed()) {
            scenario.fail("Cenario falhou: " + sc.getName());
        } else {
            scenario.pass("Cenario passou: " + sc.getName());
        }
        scenario = null; // Reset scenario para o próximo
    }

    @AfterAll
    public static void afterAll() {
        if (extent != null) {
            extent.flush();
        }
    }

    private String captureScreenshot(WebDriver driver) {
        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String path = "target/screenshots/" + UUID.randomUUID() + ".png";
        File dest = new File(path);
        dest.getParentFile().mkdirs();
        try (FileOutputStream out = new FileOutputStream(dest)) {
            out.write(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }

    public static void log(Status status, String message) {
        if (scenario != null) {
            scenario.log(status, message);
        }
    }

    public static ExtentTest getScenario() {
        return scenario;
    }
}