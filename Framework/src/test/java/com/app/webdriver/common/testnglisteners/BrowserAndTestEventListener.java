package com.app.webdriver.common.testnglisteners;

import java.lang.reflect.Method;
import java.util.Date;

import org.joda.time.DateTime;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.SkipException;

import com.app.webdriver.common.core.APPWebDriver;
import com.app.webdriver.common.core.AlertHandler;
import com.app.webdriver.common.core.CommonUtils;
import com.app.webdriver.common.core.SelectorStack;
import com.app.webdriver.common.core.TestContext;
import com.app.webdriver.common.core.annotations.DontRun;
import com.app.webdriver.common.core.annotations.Execute;
import com.app.webdriver.common.core.configuration.Configuration;
import com.app.webdriver.common.core.element.JavascriptActions;
import com.app.webdriver.common.driverprovider.DriverProvider;
import com.app.webdriver.common.logging.Log;
import com.app.webdriver.common.logging.VelocityWrapper;


public class BrowserAndTestEventListener extends AbstractWebDriverEventListener
implements ITestListener {

private By lastFindBy;
private APPWebDriver driver;

@Override
public void beforeNavigateTo(String url, WebDriver driver) {
new JavascriptActions(driver).execute("window.stop()");

Log.ok("Navigate to", VelocityWrapper.fillLink(url, url));
Log.logJSError();
}

@Override
public void afterNavigateTo(String url, WebDriver driver) {
Method method = TestContext.getCurrentTestMethod();
if (method != null) {

  if (!AlertHandler.isAlertPresent(driver)) {
    String command = "Url after navigation";
    if (url.equals(driver.getCurrentUrl())) {
      Log.ok(command, VelocityWrapper.fillLink(driver.getCurrentUrl(), driver.getCurrentUrl()));
    } else {
      if (driver.getCurrentUrl().contains("data:text/html,chromewebdata ")) {
        driver.get(url);
        Log.warning(command, driver.getCurrentUrl());
      } else {
        Log.warning(command, driver.getCurrentUrl());
      }
    }
  } else {
    Log.warning("Url after navigation", "Unable to check URL after navigation - alert present");
  }

  boolean reload = false;

  if(reload){
    driver.navigate().refresh();
  }
}
Log.logJSError();
}

@Override
public void beforeFindBy(By by, WebElement element, WebDriver driver) {
lastFindBy = by;
SelectorStack.write(by);
if (element != null) {
  SelectorStack.contextWrite();
}
}

@Override
public void beforeClickOn(WebElement element, WebDriver driver) {
Log.logJSError();
}

@Override
public void afterClickOn(WebElement element, WebDriver driver) {
Log.info("click", lastFindBy.toString());
}

@Override
public void afterChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {
Log.info("ChangeValueOfField", lastFindBy.toString());
}

@Override
public void onTestStart(ITestResult result) {
Log.clearLogStack();
String testName = result.getName();
String className = result.getTestClass().getName();
System.out.println(className + " " + testName);
}

@Override
public void onTestSuccess(ITestResult result) {
Log.stop();
}

@Override
public void onTestFailure(ITestResult result) {
driver = DriverProvider.getActiveDriver();

if ("true".equals(Configuration.getLogEnabled())) {
  Log.logError("Test Failed", result.getThrowable());
  Log.logJSError();
  Log.stop();
}
}

@Override
public void onTestSkipped(ITestResult result) {
Method method = result.getMethod().getConstructorOrMethod().getMethod();
if (!Log.isTestStarted()) {
  Log.startTest(method);
}
if (method.isAnnotationPresent(DontRun.class)) {
  Log.ok("Test SKIPPED", result.getThrowable().getMessage());
  result.setStatus(ITestResult.SUCCESS);
  onTestSuccess(result);
} else {
  result.setStatus(ITestResult.FAILURE);
  if (result.getThrowable() == null) {
    result.setThrowable(new SkipException("TEST SKIPPED"));
  }
  onTestFailure(result);
}
if (Log.isTestStarted()) {
  Log.stop();
}
}

@Override
public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
}

@Override
public void onStart(ITestContext context) {
Log.startReport();
}

@Override
public void beforeNavigateBack(WebDriver driver) {
Log.ok("Navigate Back", "attempting to navigate back");
}

@Override
public void afterNavigateBack(WebDriver driver) {
Log.log("Navigate Back", "previous page loaded", true);
}

@Override
public void beforeNavigateForward(WebDriver driver) {
Log.ok("Navigate Froward", "attempting to navigate forward");
}

@Override
public void afterNavigateForward(WebDriver driver) {
Log.log("Navigate Froward", "forward page loaded", true);
}

@Override
public void onFinish(ITestContext context) {
CommonUtils.appendTextToFile(Log.LOG_PATH, "</body></html>");
}


{

}
}
