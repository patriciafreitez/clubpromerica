package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ComerciosPage extends configuracion.Base{

	By comerciosTitulo = By.xpath("//*[@class='current-item']");

	public ComerciosPage(WebDriver driver) {
		super(driver);
	}
	
	public String validacionComercio() {
		fWait(comerciosTitulo);
		String titulo = fiElement(comerciosTitulo).getText();
		return titulo;
	}

}
