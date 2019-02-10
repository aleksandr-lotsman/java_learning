package ru.stqa.pft.addressbook.tests;


import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase{

  @Test
  public void testContactCreationTests() throws Exception {
    initContactCreation();
    fillContactForm(new ContactData("firstName1", "lastName1", "address1", "phone1", "phone2"));
    submitContactForm();
    returtnToHomePage();
  }

}
