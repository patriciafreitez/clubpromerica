package report;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class DirectoryManager {
	private static String PATH = "";
	public static String JSON_PATH = "json-build.json";
	public static File REPORT_PATH = null;

	public Boolean exist(String dir) {
		File directory = new File(PATH + dir);
		return directory.exists();
	}
	
	public void createDir(String dir) {
		File directory = new File(PATH + dir);
		directory.mkdir();
	}
	
	public Boolean exist(File directory) {
		return directory.exists();
	}
	
	public void createDir(File directory) {
		directory.mkdir();
	}
	
	public File createIfNotExist(String dir) {
		File directory = new File(PATH + dir);
		if (exist(directory)) {
			createDir(directory);
		}
		return directory;
	}
	
	public void copy(String source, String dest) {
		File src = new File(source);
	    File destination = new File(dest);
	    try {
	    	FileUtils.copyFile(src, destination);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
