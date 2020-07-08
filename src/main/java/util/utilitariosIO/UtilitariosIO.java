package util.utilitariosIO;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.Base64;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

public class UtilitariosIO {

	/**
	 * @param path este parámetro corresponde al path del archivo css
	 * @return devuelve el archivo .css en forma de un String
	 * @throws IOException en caso no exista el archivo
	 */
	 public static String retornarCustomizeCssAsString (String path) throws IOException{


			BufferedReader br = new BufferedReader(new FileReader(new File(path)));
			String line;
			StringBuilder sb = new StringBuilder();
			
			while((line=br.readLine())!= null){ 
			    sb.append(line.trim());
			}	
			br.close();
			return sb.toString();
	 }

	  public static String readAll(Reader rd) throws IOException {
		    StringBuilder sb = new StringBuilder();
		    int cp;
		    while ((cp = rd.read()) != -1) {
		      sb.append((char) cp);
		    }
		    return sb.toString();
	 }
	  public static String desencriptarBase64 (String cadena){
		  String decodificar = "";
		  byte[] bytes;
			try {
				  bytes = cadena.getBytes("UTF-8");							  
				  byte[] decoded = Base64.getDecoder().decode(bytes);
				  decodificar = new String (decoded,"UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			  
		  return decodificar;	  
	  }
	  public static String devolverNombreCSV(String nombre_archivo) { 
		  String path = "";
		  path = System.getProperty("user.dir")+"\\src\\main\\resources\\csv\\"+nombre_archivo+".csv";

		  
		  return path;
	  }
	  public static void escribirCSV(HashMap <String,Object> mapa,String nombreCSV) throws IOException {
		    CSVWriter writer = new CSVWriter(new FileWriter(nombreCSV));
		    for (Entry<String, Object> e : mapa.entrySet()) {
		    	
				String llave = e.getKey();
		    	String valor = e.getValue().toString(); 
				String []filaCSV = {llave,valor} ;
				writer.writeNext(filaCSV);	
		    	
		    }
		    writer.close();
		   
	  }
	  
	  public static void leerCSV(List<HashMap<String, String>> listadoMapa , String nombreCSV) throws IOException {
		     Reader reader = new FileReader(nombreCSV);
			 CSVReader csvReader = new CSVReader(reader);
			 Iterator iterator = csvReader.iterator();
			 HashMap<String, String> campos = new HashMap<String, String>() ;
			 while (iterator.hasNext()) {
				 String []lista = (String[]) iterator.next();
				 //Map<String, Object> fila = new HashMap<String, Object>();
				 //fila.put(, );
				 campos.put(lista[0], lista[1]);
			 }
			 listadoMapa.add(campos);
			 csvReader.close();
	  }

	  public static void createDirectory(String pathName){
		  File carpeta = new File(pathName);

		  if (!carpeta.exists()) {
				  carpeta.mkdir();
		  }

	  }

	public static void limpiarDirectorio(String folderPathName) throws IOException {
		File carpeta = new File(folderPathName);

		if (carpeta.exists()) {
			FileUtils.cleanDirectory(carpeta);
		}

	}
	public  static String comparaCadenas(String actual, String esperada){
	 	actual = actual.replaceAll(" ","");
		esperada = esperada.replaceAll(" ","");
		String mensaje="";
		if(actual.compareTo(esperada)!=0){
			mensaje = "Actual: "+actual+"<br>"+"Esperado: "+esperada+"<br>";
		}
		return mensaje;
	}
}
