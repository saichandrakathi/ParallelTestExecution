package com.app.webdriver.common.driverprovider;

import java.util.ArrayList;
import java.util.List;

import com.app.webdriver.common.core.APPWebDriver;
import com.app.webdriver.common.core.configuration.Configuration;
import com.app.webdriver.common.core.drivers.Browser;
import com.app.webdriver.common.logging.Log;


public class DriverProvider {
	private static final List<APPWebDriver> drivers = new ArrayList<>();
	  private static int ACTIVE_BROWSER_INDEX = 0;

	  private DriverProvider() {}

	  private static void newInstance() {
	    drivers.add(Browser.lookup(Configuration.getBrowser()).getInstance());
	  }

	  private static APPWebDriver getBrowserDriver(int index) {
	    for (; drivers.size() <= index; ) {
	      newInstance();
	    }

	    return drivers.get(index);
	  }

	  public static APPWebDriver getActiveDriver() {
	    return getBrowserDriver(ACTIVE_BROWSER_INDEX);
	  }

	  public static APPWebDriver switchActiveWindow(int index) {
	    ACTIVE_BROWSER_INDEX = index;
	    return getActiveDriver();
	  }

	  public static void close() {
	    for (APPWebDriver webDriver : drivers) {
	      if (webDriver != null) {
	        try {
	          String path = System.getenv("PATH");
	          System.out.println(path);
	          webDriver.quit();
	        } catch (UnsatisfiedLinkError | NoClassDefFoundError | NullPointerException e) {

	          Log.log("Closing Browser", e, true);
	        }
	      }
	    }
	    drivers.clear();
	    ACTIVE_BROWSER_INDEX = 0;
	  }
	}

