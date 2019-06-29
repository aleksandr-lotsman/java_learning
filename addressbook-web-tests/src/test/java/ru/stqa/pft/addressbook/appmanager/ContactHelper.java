package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.ArrayList;
import java.util.List;

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
        type(By.name("email"), contactData.getEmail());
        type(By.name("home"), contactData.getHomePhoneNumber());
        type(By.name("mobile"), contactData.getMobilePhoneNumber());
        type(By.name("company"), contactData.getCompany());
        attach(By.name("photo"), contactData.getPhoto());

        if (creation){
            if (contactData.getGroup() != null) {
                new Select(driver.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroup());
            }
         }else{
            Assert.assertFalse(isElementPresent(By.name("new_group")));
        }
    }

    public void fillContactFormWithutGroup(ContactData contactData) {
        type(By.name("firstname"), contactData.getFirstName());
        type(By.name("lastname"), contactData.getLastName());
        type(By.name("address"), contactData.getAddress());
        type(By.name("email"), contactData.getEmail());
        type(By.name("home"), contactData.getHomePhoneNumber());
        type(By.name("mobile"), contactData.getMobilePhoneNumber());
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

    public void createWithoutGroup(ContactData contact) {
        initContactCreation();
        fillContactFormWithutGroup(contact);
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
            String allPhones = element.findElement(By.xpath("td[6]")).getText();
            String address = element.findElement(By.xpath("td[4]")).getText();
            String email = element.findElement(By.xpath("td[5]")).getText();
            int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
            contactCache.add(new ContactData().withId(id).withFirstName(firstName).withLastName(lastName)
            .withAllPhones(allPhones).withAddress(address).withEmail(email));
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


    public ContactData infoFromEditForm(ContactData contact) {
        initContactModificationById(contact.getId());
        String firstName = driver.findElement(By.name("firstname")).getAttribute("value");
        String lastName = driver.findElement(By.name("lastname")).getAttribute("value");
        String address = driver.findElement(By.name("address")).getText();
        String email = driver.findElement(By.name("email")).getAttribute("value");
        String homePhone = driver.findElement(By.name("home")).getAttribute("value");
        String mobilePhone = driver.findElement(By.name("mobile")).getAttribute("value");
        driver.navigate().back();
        return new ContactData()
                .withId(contact.getId())
                .withFirstName(firstName)
                .withLastName(lastName)
                .withAddress(address)
                .withEmail(email)
                .withHomePhoneNumber(homePhone)
                .withMobilePhoneNumber(mobilePhone);
    }

    public ContactData infoFromDetailsPage(ContactData contact) {
        openDetailsPageById(contact.getId());
        String allData = driver.findElement(By.id("content")).getText();
        driver.navigate().back();
        return new ContactData()
                .withAllData(allData);
    }

    private void openDetailsPageById(int id) {
        click(By.xpath("//a[contains(@href, 'id="+ id +"')]//img"));
    }

    public void addContactToGroup(ContactData contact, GroupData group) {
        selectContactById(contact.getId());
        selectGroupAndAddTo(group.getId());
    }

    private void selectGroupAndAddTo(int id) {
       Select group = new Select(driver.findElement(By.name("to_group")));
       group.selectByValue(String.valueOf(id));
       click(By.name("add"));
    }

    public void filterListByGroup(int id) {
        Select group = new Select(driver.findElement(By.name("group")));
        group.selectByValue(String.valueOf(id));
    }
}
