package report;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ReportManager {
	public static File REPORT_PATH = null;
	public static String TEST_PATH = null;

	@SuppressWarnings("unchecked")
	public void init(String reportName) {
		DateManager dateManager = new DateManager();
		DirectoryManager directoryManager = new DirectoryManager();
		File dir = directoryManager.createIfNotExist("report");
		REPORT_PATH = new File(dir, DirectoryManager.JSON_PATH);
		TEST_PATH = "report/test" + dateManager.getDirDate();
		
		dateManager.init();
		        
        JSONObject obj = new JSONObject();
        obj.put(ReportParams.TITLE, reportName);
        obj.put(ReportParams.PROJECT_NAME, Params.PROJECT_NAME);
        obj.put(ReportParams.DRIVER, Params.DRIVER);
        obj.put(ReportParams.EXECUTOR, Params.EXECUTE_BY);
        obj.put(ReportParams.EXECUTION_DATE, dateManager.getFullDate());
        obj.put(ReportParams.START_TIME, dateManager.getDate());
        obj.put(ReportParams.TOTAL_DURATION, "00:00:00");
        obj.put(ReportParams.TOTAL_TEST, 0);
        obj.put(ReportParams.TEST_PASSED, 0);
        obj.put(ReportParams.TEST_FAILED, 0);
        obj.put(ReportParams.TESTS, new JSONArray());
        
        saved(obj);	
	}
	
	@SuppressWarnings({ "unchecked" })
	public int registerTest(String name) {
		int index = 0;
		DateManager dateManager = new DateManager();
	      try {
	    	 JSONObject obj = getJson();
	         long totalTest = (java.lang.Long) obj.get(ReportParams.TOTAL_TEST);
	         JSONArray tests = (JSONArray) obj.get(ReportParams.TESTS);
	         index = tests.size();
	         
	         JSONObject test = new JSONObject();
	         test.put(ReportParams.TEST_NAME, name);
	         test.put(ReportParams.TEST_TIME, dateManager.getNowStr());
	         test.put(ReportParams.TEST_DURATION, "00:00:00");
	         test.put(ReportParams.TEST_DURATION_INT, 0);
	         test.put(ReportParams.TEST_STATUS, "");
	         test.put(ReportParams.TEST_EVENTS, new JSONArray());
	         tests.add(test);
	         
	         obj.put(ReportParams.TESTS, tests);
	         obj.put(ReportParams.TOTAL_TEST, totalTest + 1);
	         saved(obj);
	      } catch(Exception e) {
	         e.printStackTrace();
	      }
	      return index;
	}
	
	@SuppressWarnings({ "unchecked" })
	public void testPassed(int index) {
		DateManager dateManager = new DateManager();
	      try {
	    	 JSONObject obj = getJson();
	    	 
	         long testPassed = (java.lang.Long) obj.get(ReportParams.TEST_PASSED); 
	         JSONArray tests = (JSONArray) obj.get(ReportParams.TESTS);
	         
        	 JSONObject test = (JSONObject) tests.get(index);
	         JSONArray events = (JSONArray) test.get(ReportParams.TEST_EVENTS);
	         
        	 JSONObject event = (JSONObject) events.get(events.size() - 1);
	         long duration = (java.lang.Long) event.get(ReportParams.EVENT_DURATION); 
	         
        	 test.put(ReportParams.TEST_STATUS, ReportParams.PASSED);
        	 test.put(ReportParams.TEST_DURATION_INT, duration);
        	 test.put(ReportParams.TEST_DURATION, dateManager.timeFormatByMilis(duration));
	    
	         obj.put(ReportParams.TEST_PASSED, testPassed + 1);
	         saved(obj);
	      } catch(Exception e) {
	         e.printStackTrace();
	      }
	}
	
	@SuppressWarnings({ "unchecked" })
	public void testFailed(int index) {
		DateManager dateManager = new DateManager();
	      try {
	    	 JSONObject obj = getJson();
	    	 
	         long testFailed = (java.lang.Long) obj.get(ReportParams.TEST_FAILED); 
	         JSONArray tests = (JSONArray) obj.get(ReportParams.TESTS);
	         
	         JSONObject test = (JSONObject) tests.get(index);
	         JSONArray events = (JSONArray) test.get(ReportParams.TEST_EVENTS);
	         
        	 JSONObject event = (JSONObject) events.get(events.size() - 1);
	         long duration = (java.lang.Long) event.get(ReportParams.EVENT_DURATION); 
	         
        	 test.put(ReportParams.TEST_STATUS, ReportParams.FAILED);
        	 test.put(ReportParams.TEST_DURATION_INT, duration);
        	 test.put(ReportParams.TEST_DURATION, dateManager.timeFormatByMilis(duration));
	    
	         obj.put(ReportParams.TEST_FAILED, testFailed + 1);
	         saved(obj);
	      } catch(Exception e) {
	         e.printStackTrace();
	      }
	}
	
	@SuppressWarnings("unchecked")
	private void registerEvent(String name, String status, String screenShot, String message) {
		DateManager dateManager = new DateManager();
	      try {
	    	 JSONObject obj = getJson();
	         JSONArray tests = (JSONArray) obj.get(ReportParams.TESTS);
        	
	         JSONObject test = (JSONObject) tests.get(tests.size() - 1);
	         String time = (String) test.get(ReportParams.TEST_TIME);
	         JSONArray events = (JSONArray) test.get(ReportParams.TEST_EVENTS);
	         long milis = dateManager.getTimeFrom(time);

	         JSONObject event = new JSONObject();
	         event.put(ReportParams.EVENT_NAME, name);
	         event.put(ReportParams.EVENT_DURATION, milis);
	         event.put(ReportParams.EVENT_TIME, dateManager.timeFormatByMilis(milis));
	         event.put(ReportParams.EVENT_STATUS, status);
	         event.put(ReportParams.EVENT_SCREEN_SHOT, screenShot);
	         event.put(ReportParams.EVENT_MESSAGE, message);
	         events.add(event);
	         	         
	         obj.put("tests", tests);	         
	         saved(obj);
	      } catch(Exception e) {
	         e.printStackTrace();
	      }
	}
	
	public void registerEventWithScreenShot(String name, String status, String screenShot) {
		registerEvent(name, status, screenShot, "");
	}
	
	public void registerEventWithMessage(String name, String status, String message) {
		registerEvent(name, status, "", message);
	}
	
	public void registerEvent(String name, String status) {
		registerEvent(name, status, "", "");
	}
	
	@SuppressWarnings("unchecked")
	public void closeReport() {
		DateManager dateManager = new DateManager();
		try {
		    JSONObject obj = getJson();
			JSONArray tests = (JSONArray) obj.get(ReportParams.TESTS);
			
			long durationAcum = 0;
			for (int i = 0; i < tests.size(); i++) {
		        JSONObject test = (JSONObject) tests.get(i);
		        long duration = (java.lang.Long) test.get(ReportParams.TEST_DURATION_INT); 
	        	durationAcum += duration;
			}
		
		 	obj.put(ReportParams.TOTAL_DURATION, dateManager.timeFormatByMilis(durationAcum));
		    saved(obj);
		    		   
		    String template = "report-template.html";
		    DirectoryManager directoryManager = new DirectoryManager();
		    directoryManager.createDir(TEST_PATH);
		    directoryManager.copy("report/" + template, TEST_PATH + "/" + template);
		    
		    PrintWriter writer = new PrintWriter(TEST_PATH + "/report-json.js", "UTF-8");
		    writer.println("var Props = " + obj.toJSONString());
		    writer.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void saved(JSONObject obj) {
		FileWriter file = null;
		try {
	        file = new FileWriter(REPORT_PATH);
	        file.write(obj.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
	 
        } finally {
 
            try {
                file.flush();
                file.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
	}
	
	private JSONObject getJson() throws FileNotFoundException, IOException, ParseException {
		JSONParser parser = new JSONParser();
	    Object objParse = parser.parse(new FileReader(REPORT_PATH.getAbsolutePath()));
	    return (JSONObject) objParse;
	}

}
