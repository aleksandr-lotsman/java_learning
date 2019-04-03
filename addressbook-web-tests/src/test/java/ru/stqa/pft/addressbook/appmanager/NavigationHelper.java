package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class NavigationHelper extends HelperBase {

    public NavigationHelper(WebDriver driver) {
        super(driver);
    }

    public void gotoGroupPage() {
        if(isElementPresent(By.tagName("h1"))
                && driver.findElement(By.tagName("h1")).getText().equals("Groups")
                && isElementPresent(By.name("new"))) {
            return;
        }
        click(By.linkText("groups"));
    }

	public void gotoHomePage() {
		if(isElementPresent(By.xpath("//input[@value='Send e-Mail']"))
				&& driver.findElement(By.tagName("form")).getAttribute("name").equals("MainForm"))
		{
			return;
		}
		click(By.linkText("home"));
	}
}
