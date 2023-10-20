package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ContactoPage extends configuracion.Base {
	
	By fullName = By.xpath("//*[@id='FullName']");
	By email = By.xpath("//*[@id='Email']");
	By comentario = By.xpath("//*[@id='Enquiry']");
	By botonEnviar = By.name("send-email");
	By succes = By.id("dialog-notifications-success");
	By result = By.xpath("//div[@class='result']");

	


	public ContactoPage(WebDriver driver) {
		super(driver);
	}
	
	public String formulario() {
		type("Patricia Freitez", fullName);
		type("patricia@gmail.com", email);
		type("Por favor contactarme a mi correo", comentario);
		click(botonEnviar);
		fWait(succes);
		String exito = fiElement(result).getText();
		return exito;
	}

}
