package com.app.webdriver.common.core.imageutilities;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import com.app.webdriver.common.core.configuration.Configuration;
import com.app.webdriver.common.core.element.JavascriptActions;
import com.app.webdriver.common.logging.Log;


public class Shooter {
	  private int dpr;

	  public Shooter() {
	    
	  }

	  public void savePageScreenshot(String path, WebDriver driver) {
	    saveImageFile(capturePage(driver), path);
	  }

	  public File capturePage(WebDriver driver) {
	    return ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
	  }

	  public void saveImageFile(File imageFile, String path) {
		    Pattern pattern = Pattern.compile("/*.jpg|/*.png|/*.jpeg");
		    Matcher matcher = pattern.matcher(path);
		    String newPath = null;
		    if (!matcher.matches()) {
		      newPath = path + ".png";
		    }
		    try {
		      FileUtils.copyFile(imageFile, new File(newPath));
		    } catch (IOException e) {
		      throw new WebDriverException(e);
		    }
		  }
}
