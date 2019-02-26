package com.app.webdriver.common.core;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.app.webdriver.common.core.exceptions.TestEnvInitFailedException;
import com.app.webdriver.common.logging.Log;


public class CommonUtils {
	private CommonUtils() {

	  }

	  public static void appendTextToFile(String filePath, String textToWrite) {
	    try {
	      FileWriter newFile = new FileWriter(filePath, true); 
	      BufferedWriter out = new BufferedWriter(newFile);
	      out.write(textToWrite);
	      out.newLine();
	      out.flush();
	      out.close();
	    } catch (IOException e) {
	      throw new TestEnvInitFailedException();
	    }
	  }

	  /**
	   * delete directory by path
	   */
	  public static void deleteDirectory(String dirName) {
	    try {
	      FileUtils.deleteDirectory(new File(dirName));
	    } catch (IOException e) {
	      Log.log("deleteDirectory", e, false);
	    }
	  }

	  /**
	   * creates directory based on given path
	   */
	  public static void createDirectory(String fileName) {
	    try {
	      new File(fileName).mkdir();
	      System.out.println("directory " + fileName + " created");
	    } catch (SecurityException e) {
	      throw new TestEnvInitFailedException();
	    }
	  }

	  public static String getAbsolutePathForFile(String relativePath) {
	    File fileCheck = new File(relativePath);
	    if (!fileCheck.isFile()) {
	      throw new TestEnvInitFailedException("file " + relativePath + " doesn't exists");
	    }
	    return fileCheck.getAbsolutePath();
	  }
}
