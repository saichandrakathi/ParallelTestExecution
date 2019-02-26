package com.app.webdriver.common.core.element;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.app.webdriver.common.driverprovider.DriverProvider;
import com.app.webdriver.common.logging.Log;



public class JavascriptActions {

	private final static int WEBDRIVER_WAIT_TIMEOUT_SEC = 15;
	  private final JavascriptExecutor js;
	  private final WebDriver driver;
	  private final By bannerNotificationContainerBy = By.cssSelector(
	      ".banner-notifications-placeholder");
	  private final By globalNavigationBy = By.cssSelector("#globalNavigation");

	  public JavascriptActions(WebDriver driver) {
	    this.js = (JavascriptExecutor) driver;
	    this.driver = driver;
	  }

	  public JavascriptActions() {
	    this.driver = DriverProvider.getActiveDriver();
	    this.js = (JavascriptExecutor) driver;
	  }

	  public void click(String cssSelector) {
	    js.executeScript("$('" + cssSelector + "').click()");
	  }

	  public void click(WebElement element) {
	    js.executeScript("$(arguments[0])[0].click()", element);
	  }

	  public void focus(String cssSelector) {
	    js.executeScript("$('" + cssSelector + "').focus()");
	  }

	  public void focus(WebElement element) {
	    js.executeScript("$(arguments[0]).focus()", element);
	  }

	  public Object execute(String script, WebElement element) {
	    return js.executeScript(script, element);
	  }

	  public Object execute(String script) {
	    // TODO: Get rid of this wait
	    try {
	      Object value = js.executeScript("return " + script);
	      Thread.sleep(1000);
	      return value;
	    } catch (InterruptedException e) {
	      Log.log("execute", e, false);
	      return null;
	    } catch (UnsupportedOperationException e) {
	      Log.log("execute", e, true);
	      return null;
	    }
	  }

	  public void mouseOver(WebElement element) {
	    js.executeScript("$(arguments[0]).mouseenter()", element);
	  }


	  public void scrollToBottom() {
	    js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
	  }

	  public void scrollDown(int pixels) {
	    js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
	  }

	  public void scrollToElement(By elementBy) {
	    scrollToElement(driver.findElement(elementBy));
	  }

	  public void scrollToElement(WebElement element) {
	    try {
	      js.executeScript(
	          "arguments[0].scrollIntoView();",
	          element
	      );
	    } catch (WebDriverException e) {
	    	Log.log("JSError", e.getMessage(), false);
	    }
	  }

	  public void scrollIntoView(By elementBy) {
	    scrollIntoView(driver.findElement(elementBy));
	  }

	  public void scrollIntoView(WebElement element) {
	    js.executeScript("arguments[0].scrollIntoView();", element);
	  }


	  public void scrollToSpecificElement(WebElement element) {
	    try {
	      js.executeScript("arguments[0].scrollIntoView(true);", element);
	    } catch (WebDriverException e) {
	    	Log.log("JSError", e.getMessage(), false);
	     
	    }
	  }

	  public void scrollToElement(WebElement element, int offset) {
	    int elementPosition = element.getLocation().getY() - offset;
	    js.executeScript("window.scroll(0,arguments[0])", elementPosition);
	  }

	  public void scrollToElementInModal(WebElement element, WebElement modal) {
	    int elementOffsetTop = Integer.parseInt(js.executeScript("return Math.round($(arguments[0]).offset().top)",
	                                                             element
	    ).toString());
	    int modalOffsetTop = Integer.parseInt(js.executeScript("return Math.round($(arguments[0]).offset().top)",
	                                                           modal
	    ).toString());
	    int scrollTop = elementOffsetTop - modalOffsetTop;

	    js.executeScript("$(arguments[0]).scrollTop(arguments[1])", modal, scrollTop);
	  }


	  public void scrollBy(int x, int y) {
	    js.executeScript("window.scrollBy(arguments[0], arguments[1])", x, y);
	  }

	  public String getCountry() {
	    waitForJavaScriptTruthy("Wikia.geo");
	    return js.executeScript("return Wikia.geo.getCountryCode();").toString();
	  }

	  public void waitForJavaScriptTruthy(final String script) {
	    driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
	    try {
	      new WebDriverWait(driver, WEBDRIVER_WAIT_TIMEOUT_SEC).until(new ExpectedCondition<Boolean>() {
	        public Boolean apply(WebDriver driver) {
	          try {
	            return (boolean) js.executeScript("return !!(" + script + ");");
	          } catch (WebDriverException e) {
	            Log.logError("waitForJavaScriptTruthy", e);
	            return false;
	          }
	        }
	      });
	    } finally {
	      driver.manage().timeouts().implicitlyWait(WEBDRIVER_WAIT_TIMEOUT_SEC, TimeUnit.MILLISECONDS);
	    }
	  }

	  public void changeElementOpacity(String selector, int value) {
	    js.executeScript("document.querySelector(arguments[0]).style.opacity = arguments[1];",
	                     selector,
	                     value
	    );
	  }

	  public String getWindowErrors() {
	    return js.executeScript("return window.errors || ''").toString();
	  }

	  public void addErrorListenerScript() {
	    js.executeScript(
	        "var script = document.createElement('script'); " + "script.innerHTML = 'window.onerror = "
	        + "function (e, u, l, c, errorObj) { window.errors = errorObj.stack }';"
	        + "document.querySelector('body').appendChild(script);");
	  }

	  public Long getCurrentPosition() {
	    return (Long) js.executeScript("return window.pageYOffset;");
	  }
	}

