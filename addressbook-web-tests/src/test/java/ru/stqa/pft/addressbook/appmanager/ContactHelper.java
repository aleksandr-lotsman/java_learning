package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ContactHelper extends HelperBase{

    public ContactHelper(WebDriver driver) {
        super(driver);
    }


    public void submitContactForm() {
        click(By.name("submit"));
    }

    public void fillContactForm(ContactData contactData, boolean creation) {
        type(By.name("firstname"), contactData.getFirstName());
        type(By.name("lastname"), contactData.getLastName());
        type(By.name("address"), contactData.getAddress());
        type(By.name("home"), contactData.getHomePhoneNumber());
        type(By.name("mobile"), contactData.getMobilePhoneNumber());

        if (creation){
            new Select(driver.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroup());
        }else{
            Assert.assertFalse(isElementPresent(By.name("new_group")));
        }
    }
    public int count() {
        return driver.findElements(By.name("selected[]")).size();
    }

    public void initContactCreation() {
       click(By.linkText("add new"));
    }

    public void confirmDeletion() {
        confirmPopup();
    }

    public void deleteSelectedContact() {
       click(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Select all'])[1]/following::input[2]"));
    }

    public void selectContactById(int id) {
        driver.findElement(By.cssSelector("input[value='" + id + "']")).click();
    }

    public void submitContactModification() {
        click(By.name("update"));
    }

    public void initContactModificationById(int id) {
        click(By.xpath("//input[@value="+ id + "]/../../td[8]//a[1]"));
    }

    public void create(ContactData contact, boolean param) {
        initContactCreation();
        fillContactForm(contact, param);
        submitContactForm();
        contactCache = null;
        returnToHomePage();
    }

    public void returnToHomePage() {
        if(isElementPresent(By.id("maintable"))){
            return;
        }
        click(By.linkText("home page"));
    }

    public boolean isThereAContact() {
        return isElementPresent(By.name("selected[]"));
    }

    public List<ContactData> list() {
        List<ContactData> contacts = new ArrayList<ContactData>();
        List<WebElement> elements = driver.findElements(By.xpath("//tr[@name='entry']"));
        for (WebElement element : elements) {
            String firstName = element.findElement(By.xpath("td[3]")).getText();
            String lastName = element.findElement(By.xpath("td[2]")).getText();
            int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
            contacts.add(new ContactData().withId(id).withFirstName(firstName).withLastName(lastName));
        }
        return contacts;
    }

    private Contacts contactCache = null;

    public Contacts all() {
        if (contactCache != null){
           return new Contacts(contactCache);
        }
        contactCache = new Contacts();
        List<WebElement> elements = driver.findElements(By.xpath("//tr[@name='entry']"));
        for (WebElement element : elements) {
            String firstName = element.findElement(By.xpath("td[3]")).getText();
            String lastName = element.findElement(By.xpath("td[2]")).getText();
            int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
            contactCache.add(new ContactData().withId(id).withFirstName(firstName).withLastName(lastName));
        }
        return new Contacts(contactCache);
    }


    public void modify(ContactData contact) {
        selectContactById(contact.getId());
        initContactModificationById(contact.getId());
        fillContactForm(contact, false);
        submitContactModification();
        contactCache = null;
        returnToHomePage();
    }

    public void delete(ContactData contact) {
        selectContactById(contact.getId());
        deleteSelectedContact();
        confirmDeletion();
        contactCache = null;
    }


}
