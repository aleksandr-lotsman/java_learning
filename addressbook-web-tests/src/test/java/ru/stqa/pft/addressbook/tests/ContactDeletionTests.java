package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;


public class ContactDeletionTests extends TestBase{


  @Test
  public void testContactDeletionTests() throws Exception {
    selectContact();
    deleteSelectedContact();
    confirmDeletion();
  }
}
