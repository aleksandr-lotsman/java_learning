package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactRemoveFromGroupTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions(){
        if (app.db().groups().size() == 0) {
            app.goTo().groupPage();
            app.group().create(new GroupData().withName("test1"));
        }

        if (app.db().groups().size() == 0) {
            app.contact().create(new ContactData() .withFirstName("firstName1")
                    .withLastName("lastName1")
                    .withAddress("vul.Ivanova 1")
                    .withEmail("000@mail.com")
                    .withCompany("Nix"), true);
        }
    }

    @Test
    public void testRemoveContactFromGroup () {
        //TODO it doesn't work for now
        Contacts contacts = app.db().contacts();
        GroupData group = app.db().groups().iterator().next();
        ContactData contactToRemove = contacts.iterator().next();

        app.goTo().homePage();
        app.contact().showAllContacts();
        app.contact().filterListByGroup(group.getId());
        app.contact().removeContactFromGroup(contactToRemove);

        assertThat(app.db().getContactById(contactToRemove.getId()).getGroups().contains(group), equalTo(false));

    }
}
