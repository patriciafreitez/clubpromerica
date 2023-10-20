package test;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import page.ComerciosPage;
import page.ContactoPage;
import page.HomePage;
import report.ReportManager;

public class test {
	
	private static WebDriver driver;
	HomePage homePage;
	ContactoPage contactoPage;
	ComerciosPage comerciosPage;
	static ReportManager reportManager = new ReportManager();
	
	@BeforeClass
	public static void init() {
		reportManager.init("Club Promerica");				
	}
	
	@Before
	public void setUp() throws Exception {
		WebDriverManager.chromedriver().clearDriverCache().setup();
		WebDriverManager.chromedriver().clearResolutionCache().setup();
		
		System.setProperty("webdriver.chrome.driver","src/test/resources/drivers/chromedrivertest.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		homePage = new HomePage(driver);
		homePage.visit("https://www.clubpromerica.com/costarica/");	
	}
	
	@After
	public void tearDown() throws Exception {
		driver.close();
	}	
	
	@Test
	public void test1() {
		int test = reportManager.registerTest("Acceso y Navegacion");
		
		Boolean result = homePage.accesoNavegacion();
		
		report.AssertUtil.assertEquals(result, test);
	}

	@Test
	public void test2() {
		int test = reportManager.registerTest("Iteracion con elementos");

		homePage.contactoMenu();
		contactoPage = new ContactoPage(driver);
		String result = contactoPage.formulario();
		
		report.AssertUtil.assertEquals(result, "Su comentario ha sido enviado con éxito al propietario de la tienda.", test);
	}

	@Test
	public void test3() {
		int test = reportManager.registerTest("Validacion");

		homePage.tecnologia();
		comerciosPage = new ComerciosPage(driver);
		String result = comerciosPage.validacionComercio();	
		
		report.AssertUtil.assertEquals(result, "Tecnología", test);
	}
	
	@Test
	public void test4() {
		int test = reportManager.registerTest("Captura de pantalla y reportes");

		Boolean result = homePage.tomarCapturaPaginaInicial();
		
		report.AssertUtil.assertEquals(result, test);
	}

	@AfterClass
	public static void afterAll() throws Exception {
		reportManager.closeReport();
		driver.quit();
	}

}
