package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;


public class ContactDeletionTests extends TestBase{

  @Test
  public void testContactDeletionTests() throws Exception {
    app.getContactHelper().selectContact();
    app.getContactHelper().deleteSelectedContact();
    app.getContactHelper().confirmDeletion();
  }
}
