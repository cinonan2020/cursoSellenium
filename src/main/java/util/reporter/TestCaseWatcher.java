package util.reporter;


import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import util.ConfigurationProperties;
import util.utilitariosIO.UtilitariosIO;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

public class TestCaseWatcher extends TestListenerAdapter{

    @Override
    public void onTestSuccess(ITestResult tr) {
    	
    	if(tr.getAttribute("message") == null){
    		ExtentUtil.fetchTest().pass("Passed");
    	}else{
    		ExtentUtil.fetchTest().pass("Passed: "+  tr.getAttribute("message"));
    	}
    }

    @Override
    public void onTestFailure(ITestResult tr) {
    	
		StringWriter sw = new StringWriter ();
		PrintWriter pw = new PrintWriter (sw);
		 tr.getThrowable().printStackTrace(pw);
    	    StackTraceElement[] stack = tr.getThrowable().getStackTrace();
    	    String exception = "<pre>";
    	    for (StackTraceElement s : stack) {
    	        exception = exception + s.toString() + "<br>";
    	    }
    	    exception =  exception + "</pre>";

    	if(tr.getAttribute("message") == null){
    		
    		if(tr.getThrowable() != null) {  
    			ExtentUtil.fetchTest().fail("Failed: "+" [-->Log Testng: "+tr.getThrowable()+""+exception);
    		}else {
    			ExtentUtil.fetchTest().fail("Failed: No lanzó ninguna excepción");
    		}
    	}else{
    		if(tr.getAttribute("message").equals("Fail")) {
    			ExtentUtil.fetchTest().fail("");
    		}else {
        		if(tr.getThrowable() != null) {
        			ExtentUtil.fetchTest().fail("Failed: "+  tr.getAttribute("message")+" [-->Log Testng: "+exception);
        		}else {
        			ExtentUtil.fetchTest().fail("Failed: "+  tr.getAttribute("message"));
        		}
    		}

    	}
    }

	@Override
	public void onTestSkipped(ITestResult tr) {
    	ExtentUtil.fetchTest().skip("Skipped");
	}

	@Override
    public void onStart(ITestContext testContext) {

		try {
			UtilitariosIO.limpiarDirectorio(System.getProperty("user.dir")+"\\report");
		} catch (IOException e) {
			e.printStackTrace();
		}
		UtilitariosIO.createDirectory("report");
		UtilitariosIO.createDirectory("report\\pantallazos");
		UtilitariosIO.createDirectory("report\\excelOutput");

		String path = "";
		path = System.getProperty("user.dir")+"\\report\\extentReport.html";
		ExtentUtil.createReporter(path);
		ConfigurationProperties.getConfigurationProperties();
    }

    @Override
    public void onFinish(ITestContext testContext) {
        ExtentUtil.saveReporter();
    }

    @Override
    public void onTestStart(ITestResult result) {
        ExtentUtil.createTest(result.getMethod().getMethodName(), result.getMethod().getDescription());
    }
}