package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactModificationTests extends TestBase {

    @Test
    public void testContactModificationTests(){
        if (! app.getContactHelper().isThereAContact()){
            app.getContactHelper().createContact(new ContactData("firstName1", "lastName1",
                    "address1", "phone1", "phone2", "test1"), true);
        }
        app.getContactHelper().selectContact();
        app.getContactHelper().initContactModification();
        app.getContactHelper().fillContactForm(new ContactData("firstName1", "lastName1",
                "address1", "phone1", "phone2", null), false);
        app.getContactHelper().submitContactModification();
    }
}
