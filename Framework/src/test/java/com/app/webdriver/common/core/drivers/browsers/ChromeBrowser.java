package com.app.webdriver.common.core.drivers.browsers;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.app.webdriver.common.core.APPWebDriver;
import com.app.webdriver.common.core.ExtHelper;
import com.app.webdriver.common.core.configuration.Configuration;
import com.app.webdriver.common.core.drivers.BrowserAbstract;
import com.app.webdriver.common.logging.Log;


public class ChromeBrowser extends BrowserAbstract {

	  private static final String CHROMEDRIVER_PATH_FORMAT = "ChromeDriver/chromedriver_%s";
	  private static final String CHROMEDRIVER_PATH_MAC = String.format(CHROMEDRIVER_PATH_FORMAT,
	                                                                    "mac64/chromedriver"
	  );
	  private static final String CHROMEDRIVER_PATH_LINUX = String.format(CHROMEDRIVER_PATH_FORMAT,
	                                                                      "linux64/chromedriver"
	  );
	  private static final String CHROMEDRIVER_PATH_WINDOWS = String.format(CHROMEDRIVER_PATH_FORMAT,
	                                                                        "win32/chromedriver.exe"
	  );
	  private ChromeOptions chromeOptions = new ChromeOptions();
	  private boolean useMobile = "CHROMEMOBILEMERCURY".equals(Configuration.getBrowser());

	  @Override
	  public void setOptions() {
	    String chromeBinaryPath = "";
	    String osName = System.getProperty("os.name").toUpperCase();
	   // Emulator emulator = Configuration.getEmulator();

	    if (osName.contains("WINDOWS")) {
	      chromeBinaryPath = CHROMEDRIVER_PATH_WINDOWS;
	    } else if (osName.contains("MAC")) {
	      chromeBinaryPath = CHROMEDRIVER_PATH_MAC;
	    } else if (osName.contains("LINUX")) {
	      chromeBinaryPath = CHROMEDRIVER_PATH_LINUX;
	    }

	    File chromedriver = new File(ClassLoader.getSystemResource(chromeBinaryPath).getPath());

	    // set application user permissions to 455
	    chromedriver.setExecutable(true);

	    System.setProperty("webdriver.chrome.driver", chromedriver.getPath());
	    Log.info("Using chromedriver: ", chromedriver.getPath());

	    chromeOptions.addArguments("--start-maximized");
	    chromeOptions.addArguments("disable-notifications");
	    chromeOptions.addArguments("process-per-site");
	    chromeOptions.addArguments("dns-prefetch-disable");
	    chromeOptions.addArguments("allow-running-insecure-content");

	  }

	  @Override
	  public APPWebDriver create() {
	    caps.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
	    
	    return new APPWebDriver(new ChromeDriver(chromeOptions));
	  }

	  @Override
	  public void addExtension(String extensionName) {
	    chromeOptions.addExtensions(ExtHelper.findExtension(extensionName, "crx"));
	  }
	}

