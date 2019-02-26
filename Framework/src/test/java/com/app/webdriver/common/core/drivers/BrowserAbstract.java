package com.app.webdriver.common.core.drivers;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.app.webdriver.common.core.APPWebDriver;
import com.app.webdriver.common.core.configuration.Configuration;
import com.app.webdriver.common.testnglisteners.BrowserAndTestEventListener;



public abstract class BrowserAbstract {

	  protected DesiredCapabilities caps = new DesiredCapabilities();

	  /**
	   * Get a ready to work instance for chosen browser
	   */
	  public APPWebDriver getInstance() {
	    setOptions();
	    setExtensions();
	    setBrowserLogging(Level.SEVERE);
	    APPWebDriver webdriver = create();
	    setTimeputs(webdriver);
	    setListeners(webdriver);

	    return webdriver;
	  }

	  /**
	   * Set Browser specific options, before creating a working instance
	   */
	  public abstract void setOptions();

	  /**
	   * Create a working instance of a Browser
	   */
	  public abstract APPWebDriver create();

	  protected void setBrowserLogging(Level logLevel) {
	    LoggingPreferences loggingprefs = new LoggingPreferences();
	    loggingprefs.enable(LogType.BROWSER, logLevel);
	    caps.setCapability(CapabilityType.LOGGING_PREFS, loggingprefs);
	  }

	  protected void setTimeputs(WebDriver webDriver) {
	    webDriver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
	  }

	  protected void setListeners(APPWebDriver webDriver) {
	    webDriver.register(new BrowserAndTestEventListener());
	  }

	  /**
	   * Add browser extensions
	   */
	  public abstract void addExtension(String extensionName);

	  protected void setExtensions() {
	    for (String name : Configuration.getExtensions()) {
	      addExtension(name);
	    }
	  }

	  /**
	   * Set Proxy instance for a Browser instance
	   */
	  
	  
	}

