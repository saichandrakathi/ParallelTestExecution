package com.app.webdriver.common.core;

import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.app.webdriver.common.logging.Log;

public class AlertHandler {
	/**
	   * This method is used check whether alert is present
	   * @param  driver   WebDriver   Active webdriver
	   * @return   		  true if alert is present, otherwise false
	   */
	public static boolean isAlertPresent(WebDriver driver) {
	    try {
	      driver.switchTo().alert();
	      return true;
	    } catch (NoAlertPresentException e) {
	      Log.info("NO ALERT PRESENT");
	      return false;
	    }
	  }
	/**
	   * This method is to accept alert
	   * @param  driver   WebDriver    Active webdriver
	   * @return   		  nothing
	   */
	  public static void acceptPopupWindow(WebDriver driver) {
	    driver.switchTo().alert().accept();
	  }

	  /**
	   * This method is to accept alert
	   * @param  driver   WebDriver  Active webdriver
	   * @param  timeout  int        timeout when searching for alert
	   * @return   		  nothing
	   */
	  public static void acceptPopupWindow(WebDriver driver, int timeout) {
	    new WebDriverWait(driver, timeout).until(new ExpectedCondition<Boolean>() {
	      public Boolean apply(WebDriver webDriver) {
	        if (isAlertPresent(webDriver)) {
	          webDriver.switchTo().alert().accept();
	          return true;
	        } else {
	          return false;
	        }
	      }
	    });
	  }
	  /**
	   * This method is to dismiss alert
	   * @param  driver   WebDriver  Active webdriver
	   * @return   		  nothing
	   */
	  public static void dismissPopupWindow(WebDriver driver) {
	    driver.switchTo().alert().dismiss();
	  }
}
