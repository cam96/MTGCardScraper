import java.util.LinkedList;
import java.util.List;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.gargoylesoftware.htmlunit.javascript.background.JavaScriptExecutor;

public class SeleniumHelper {
	public static List<String> getOptionsFromSelectControl(WebDriver driver, By selectBy) {
		WebElement element = driver.findElement(By.id("ctl00_ctl00_MainContent_Content_SearchControls_setAddText"));
        List<WebElement> optionsElementList = element.findElements(By.tagName("option"));
        List<String> optionsTextList = new LinkedList<String>();
        
        for (WebElement currElement : optionsElementList) {
        	optionsTextList.add(currElement.getText());
        }
        
        return optionsTextList;
	}
	
	public static List<String> getAllHrefOnPage(WebDriver driver, By selectBy) {
		List<WebElement> tagAnchors = driver.findElements(selectBy);
		List<String> links = new LinkedList<String>();
	
        for (WebElement tagAnchor: tagAnchors) {
       		String href = tagAnchor.getAttribute("href");
       		
       		if (!links.contains(href)) {
       			links.add(href);
       		}
       	}
        
        return links;
	}
	
	public static String getInnerHTMLForElement(WebDriver driver, WebElement webElement) {
		String innerHTML = "";
		
		if (driver instanceof JavascriptExecutor) {
		    innerHTML = (String)((JavascriptExecutor)driver)
		    				.executeScript(
		    					"return arguments[0].innerHTML;", webElement
		    				);
		}
		
		return innerHTML;
	}
	
	public static void setInnerHTMLToText(WebDriver driver, WebElement webElement, String text) {
		if (driver instanceof JavascriptExecutor) {
		    ((JavascriptExecutor)driver).executeScript(
		    	"arguments[0].innerHTML = arguments[1];", webElement, text);
		}
	}
	
	public static String getTextFromElement(WebDriver driver, By selectBy) {
		String elementValue = "";
		
		try {
			WebElement webElement = driver.findElement(selectBy);
			
			if (webElement.getTagName().equals("div")) {
				elementValue += getTextFromChildrenOfWebElement(driver, webElement);
			}
			else {
				elementValue = webElement.getText();
			}
		}
		catch(NoSuchElementException e) {
			return elementValue = "";
		}
		
		return elementValue;
	}
	
	private static String getTextFromChildrenOfWebElementR(WebDriver driver, WebElement webElement, List<WebElement> children, int index) {
		String buffer = "";
		
		if (index < children.size()) {
			WebElement child = children.get(index);
			String tag = child.getTagName();
				
			if (tag.equals("img")) {
				String alt = child.getAttribute("alt");
				setInnerHTMLToText(driver, child, alt);
				return buffer + getTextFromChildrenOfWebElementR(driver, child, children, index+1);
			}
			else if (tag.equals("div")) {
				return buffer + getTextFromChildrenOfWebElementR(driver, child, child.findElements(By.xpath("./*")), 0) + child.getText();
			}
			else {
				return "";
			}
		}
		else {
			return "";
		}
	}
	
	public static String getTextFromChildrenOfWebElement(WebDriver driver, WebElement webElement) {
		List<WebElement> children = webElement.findElements(By.xpath("./*"));
		String buffer = "";
		int index = 0;
		
		if (children.size() > 0) {
			for (WebElement child : children) {
				buffer += getTextFromChildrenOfWebElementR(driver, webElement, children, children.indexOf(child)) + " ";
			}
		}
		else {
			buffer = webElement.getText();
		}
		
		return buffer;
	}
	
	public static int getIntFromElement(WebDriver driver, By selectBy) {
		String elementValue = "";
		int intFromElement = -1;
		
		try {
			WebElement webElement = driver.findElement(selectBy);
			elementValue = webElement.getText();
			intFromElement = Integer.parseInt(elementValue);
		}
		catch(NoSuchElementException e) {
			return -1;
		}
		catch(NumberFormatException e) {
			return -1;
		}
		
		return intFromElement;
	}
}
