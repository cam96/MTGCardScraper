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
	
	public static String setTagsAroundAllTaglessText(WebDriver driver, WebElement webElement) {
		String innerHTML = "";
		
		if (driver instanceof JavascriptExecutor) {
		    ((JavascriptExecutor)driver).executeScript(
		    	"var innerHTML = arguments[0].innerHTML;" +
		    	"var currText = \"\"" +
		    	"for (var index = 0; index < innerHTML.length; index++) {" + 
		    		"currText = currText + innerHTML[index]" + 
		    		"if (innerHTML[index] == \"<\\\")" +
		    			"" +
		    			"" +
		    			"" +
		    			"" +
		    			"" +
		    	"}" +
		    	"",
		    webElement);
		}
		
		return innerHTML;
	}
	
	private boolean isHTMLTag(String tag) {
		return tag.equals("<img>")  || tag.equals("<div>")  || tag.equals("<p>")     || tag.equals("<html>")  ||
			   tag.equals("<body>") || tag.equals("<head>") || tag.equals("<title>") || tag.equals("<aside>");
	}
	
	public static String getTextFromElement(WebDriver driver, By selectBy) {
		String elementValue = "";
		
		try {
			WebElement webElement = driver.findElement(selectBy);
			
			if (webElement.getTagName().equals("div")) {
				System.out.println(getInnerHTMLForElement(driver, webElement));
//				elementValue += getTextFromChildrenOfWebElement(webElement);
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
	
	private static String getTextFromChildrenOfWebElementR(WebElement webElement, List<WebElement> children, int index) {
		String buffer = "";
		
		if (index < children.size()) {
			WebElement child = children.get(index);
			String tag = child.getTagName();
			String text = "";
				
			if (tag.equals("img")) {
				String alt = child.getAttribute("alt");
				return buffer + alt + getTextFromChildrenOfWebElementR(child, children, index+1);
			}
			else {
				text = child.getText();
				return buffer + text + '\n' + getTextFromChildrenOfWebElementR(child, child.findElements(By.xpath("./*")), 0);
			}
		}
		else {
			return "";
		}
	}
	
	public static String getTextFromChildrenOfWebElement(WebElement webElement) {
		List<WebElement> children = webElement.findElements(By.xpath("./*"));
		String buffer = "";
		
		if (children.size() > 0) {
			for (WebElement child : children) {
				buffer += getTextFromChildrenOfWebElementR(webElement, children, children.indexOf(child));
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
