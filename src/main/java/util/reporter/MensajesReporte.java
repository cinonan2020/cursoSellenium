package util.reporter;

import org.testng.Assert;
import org.testng.ITestResult;


public class MensajesReporte {
	  public static void cargarMensajeResultadoTestOk(ITestResult testResult , String mensaje){
			if(mensaje.length()!=0){
				testResult.setAttribute("message", mensaje);
			}
			Assert.assertTrue(true, mensaje);

	  }
	  public static void cargarMensajeResultadoTestNOk(ITestResult testResult , String mensaje ){
			testResult.setAttribute("message", mensaje);
			Assert.fail( mensaje);
	  }
	  
		public static void  stackTracePrintException(Exception e, ITestResult testResult) {
    	    StackTraceElement[] stack = e.getStackTrace();
    	    String exception = "<pre>";
    	    if(e.getMessage() !=null) {
    	    	exception = exception+e.getMessage()+"<br>";
    	    }
    	    for (StackTraceElement s : stack) {
    	        exception = exception + s.toString() + "<br>";
    	    }
    	    exception =  exception + "</pre>";
			Assert.fail(exception); 
			testResult.setAttribute("message",exception);
			e.printStackTrace();
			
		}
		
		public static String  stackTracePrintExceptionNodoHijo(Exception e) {
    	    StackTraceElement[] stack = e.getStackTrace();
    	    String exception = "<pre>";
    	    if(e.getMessage() !=null) {
    	    	exception = exception+e.getMessage()+"<br>";
    	    }
    	    for (StackTraceElement s : stack) {
    	        exception = exception + s.toString() + "<br>";
    	    }
    	    exception =  exception + "</pre>";
    	    return exception ;
		}
		public static void throwException(String mensaje) throws Exception {
			throw new Exception (mensaje); 
		}
	  
}
