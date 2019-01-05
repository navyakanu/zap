package zap;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.zaproxy.clientapi.core.ApiResponse;
import org.zaproxy.clientapi.core.ClientApi;
import org.zaproxy.clientapi.core.ClientApiException;
import org.zaproxy.clientapi.gen.Core;

import org.zaproxy.clientapi.core.ApiResponseElement;

public class ZapScan {
		
	    private static String ZAP_ADDRESS = null;
	    private static int ZAP_PORT;
	    private static String ZAP_API_KEY = null; 
	    private static String TARGET = null;
	    ClientApi clientApi = null;
	
		public ZapScan(){
			try{
					Properties zapproperties = new Properties();
					FileInputStream inputFile = new FileInputStream(System.getProperty("user.dir")+"/zap_property.properties");
					zapproperties.load(inputFile);
					ZAP_ADDRESS = zapproperties.getProperty("ZAP_ADDRESS");
					ZAP_PORT = Integer.parseInt(zapproperties.getProperty("ZAP_PORT"));
					ZAP_API_KEY = zapproperties.getProperty("ZAP_API_KEY");
					TARGET = zapproperties.getProperty("TARGET");
					clientApi  = new ClientApi(ZAP_ADDRESS, ZAP_PORT, ZAP_API_KEY);
				}catch(FileNotFoundException e){
						e.printStackTrace();
				}catch(IOException e){
					e.printStackTrace();
				}catch(Exception e){
					e.printStackTrace();
				}
	    }
		
	    public void spider(){
	    	//spider scan
		     try {
		    	System.out.println("--spider scan--");
		    	ApiResponse resp=clientApi.spider.scan(TARGET,null,null,null, null);
		    	String scanid;
	            int progress;
	            // The scan now returns a scan id to support concurrent scanning
	            scanid = ((ApiResponseElement) resp).getValue();
	           // Poll the status until it completes
	            while (true) {
	            	Thread.sleep(500);
	                progress = Integer.parseInt(((ApiResponseElement) clientApi.spider.status(scanid)).getValue());
	                System.out.println("Spider progress : " + progress + "%");
	                if (progress >= 100) {
	                    break;
	                }
	            }
	            System.out.println("Spider complete");
			} catch (ClientApiException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	    
	    public void passive_scan(){
	    	 //pscan
		     try {
			    	System.out.println("--pscan--");
			    	clientApi.pscan.scanOnlyInScope();
		            System.out.println("pscan complete");
				} catch (ClientApiException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    }
	    
	    public void scan_reports(){
	    	//reports
	    	Core core = new Core(clientApi);
	        System.out.println("XML report output");
	        String alerts_report;
			try {
				alerts_report = new String(core.htmlreport());
				System.out.println(alerts_report);
			} catch (ClientApiException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	    }
	    
	    public void terminate_zap(){
	    	 //terminate
	 	   try {
	 		   		clientApi.core.shutdown();
	 	   			} catch (ClientApiException e) {
	 	   				// TODO Auto-generated catch block
	 	   				e.printStackTrace();
	 	   			} 
	    }
	    
	    public static void main(String[] args) {

	    		ZapScan zs = new ZapScan();
		 	    zs.spider();
		 	    zs.passive_scan();
		 	    zs.scan_reports();
		 	    zs.terminate_zap();
	   	 }
}



