package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends configuracion.Base{
	
	By menuViajes = By.xpath("(//a[@title='Compras'])[1]");
	By menuViajeroPromerica = By.xpath("(//a[@title='Viajero Promerica'])[1]");
	By imagenPortada = By.xpath("//*[@id='imagen-portada']");
	By benefitslink = By.xpath("//*[@id='benefitslink']");
	By titlePicture2 = By.xpath("(//h4[@class='title-picture'])[2]");
	By solicitarTarjeta = By.xpath("//span[@class='mat-button-wrapper']");
	By menuContactenos = By.xpath("(//*[@title='Contáctenos'])[1]");
	
	By comercios = By.xpath("(//*[@class='with-subcategories'])[3]");
	By tecnologia = By.xpath("(//*[@title='Tecnología'])[1]");
	
	public HomePage(WebDriver driver) {
		super(driver);
	}

	public Boolean accesoNavegacion() {
		click(benefitslink);
		String currentWindow = getCurrentWindow();
		click(titlePicture2);
		changeTap(currentWindow);
		fWait(solicitarTarjeta);
		Boolean isDisplayed = fiElement(solicitarTarjeta).isDisplayed();

		// close window tap
		close();
		// return to driverBase
		setToDriver(currentWindow);
		
		return isDisplayed;
	}

	public void contactoMenu() {
		click(menuContactenos);		
	}

	public void tecnologia() {
		hover(comercios);
		sleep(1);
		click(tecnologia);		
	}
	
	public Boolean tomarCapturaPaginaInicial() {
		Boolean result = screenShot();
		return result;
	}
}
