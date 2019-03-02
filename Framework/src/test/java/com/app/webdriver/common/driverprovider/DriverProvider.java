package com.app.webdriver.common.driverprovider;

import java.util.ArrayList;
import java.util.List;

import com.app.webdriver.common.core.APPWebDriver;
import com.app.webdriver.common.core.configuration.Configuration;
import com.app.webdriver.common.core.drivers.Browser;
import com.app.webdriver.common.logging.Log;


public class DriverProvider {
	private static List<ThreadLocal<APPWebDriver>> drivers = new ArrayList<ThreadLocal<APPWebDriver>> ();
	public static ThreadLocal<Integer> ACTIVE_BROWSER_INDEX = new ThreadLocal<Integer>();
	  private DriverProvider() {}
	 
	  private static void newInstance() {
		 
	    drivers.add(ThreadLocal.withInitial(() -> Browser.lookup(Configuration.getBrowser()).getInstance()));
	  }

	  private synchronized static APPWebDriver getBrowserDriver(int index) {
	    for (; drivers.size() <= index; ) {
	      newInstance();
	    }

	    return drivers.get(index).get();
	  }

	  public synchronized static APPWebDriver getActiveDriver() {
	    return getBrowserDriver(ACTIVE_BROWSER_INDEX.get());
	  }

	  public static APPWebDriver switchActiveWindow(int index) {
	    ACTIVE_BROWSER_INDEX.set(index);
	    return getActiveDriver();
	  }

	  public synchronized static void close() {
	    for (ThreadLocal<APPWebDriver> webDriver : drivers) {
	      if (webDriver != null) {
	        try {
	          String path = System.getenv("PATH");
	          System.out.println(path);
	          webDriver.get().quit();
	        } catch (UnsatisfiedLinkError | NoClassDefFoundError | NullPointerException e) {

	          Log.log("Closing Browser", e, true);
	        }
	      }
	    }
	    drivers.clear();
	    ACTIVE_BROWSER_INDEX.set(0);
	  }
	}

