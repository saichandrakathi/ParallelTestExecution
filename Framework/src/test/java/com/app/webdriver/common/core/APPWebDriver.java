package com.app.webdriver.common.core;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;


public class APPWebDriver extends EventFiringWebDriver {

	  private WebDriver webDriver;

	  public APPWebDriver(WebDriver webdriver) {
	    super(webdriver);
	    this.webDriver = webdriver;
	  }

	  

	  public boolean isChrome() {
	    return webDriver instanceof ChromeDriver;
	  }

	
	  @Override
	  public void quit() {
	    super.quit();
	  }
	}