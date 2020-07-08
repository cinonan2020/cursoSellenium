package util.reporter;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;

public class GetScreenShot {

	static String bitmapPath = System.getProperty("user.dir")+"\\report\\pantallazos\\";




	//GetScreenShot.capturarPantallaBase64(CreateDriver.getInstance().getDriver(), "CP002.png")
    public static void capturarPantallaBase64(WebDriver driver) {
			String screenShotPath64;
			//Toma la foto
			try {
				screenShotPath64 = GetScreenShot.captureOKBase64(driver);
				Images.listadoImagenesTemporales.add(screenShotPath64);
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			//Agrega la foto al Test
			for (int i = 0; i<Images.listadoImagenesTemporales.size();i++ ){
				try {
					ExtentUtil.fetchTest().addScreenCaptureFromPath( Images.listadoImagenesTemporales.get(i).toString());
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			  }
			 Images.limpiarListadoImagenes();
    }
	public static String captureOKBase64(WebDriver driver) throws IOException
	{
		TakesScreenshot ts = (TakesScreenshot)driver;
		String screenshotBase64 = "data:image/png;base64,"+ts.getScreenshotAs(OutputType.BASE64);
		return screenshotBase64;
	}


	//GetScreenShot.capturarPantallaFile(CreateDriver.getInstance().getDriver(), "CP001.png")
	public static void capturarPantallaFile(WebDriver driver, String fileName) {
		//Agregando el código para guardar la imagen que grabó correctamente la solicitud.
		File pantallazo;
		//Toma la foto
		try {
			pantallazo = GetScreenShot.captureOKFile(driver);
			FileUtils.copyFile(pantallazo, new File (bitmapPath +fileName));
			Images.listadoImagenesTemporales.add(bitmapPath +fileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Agregar la foto al Test
		for (int i = 0; i<Images.listadoImagenesTemporales.size();i++ ){
			try {
				ExtentUtil.fetchTest().addScreenCaptureFromPath( Images.listadoImagenesTemporales.get(i));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Images.limpiarListadoImagenes();

	}

	//Capturar como imagen en el proyecto (en local)
	public static File captureOKFile(WebDriver driver) throws IOException
	{
		TakesScreenshot ts = (TakesScreenshot)driver;
		return ts.getScreenshotAs(OutputType.FILE);
	}

}