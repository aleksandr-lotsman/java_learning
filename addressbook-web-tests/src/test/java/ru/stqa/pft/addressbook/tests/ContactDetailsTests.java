package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactDetailsTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        app.goTo().homePage();
        if (app.contact().list().size() == 0){
            app.contact().createWithoutGroup(new ContactData()
                    .withFirstName("firstName")
                    .withLastName("lastName")
                    .withAddress("vul. Ivanova")
                    .withEmail("123@gmail.com")
                    .withHomePhoneNumber("111")
                    .withMobilePhoneNumber("222"));
        }
    }

    @Test
    public void testContactAddresses() {
        app.goTo().homePage();
        ContactData contact = app.contact().all().iterator().next();
        ContactData contactInfoFromDetailsPage = app.contact().infoFromDetailsPage(contact);
        ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);
        assertThat(cleaned(contactInfoFromDetailsPage.getAllData()), equalTo(merged(contactInfoFromEditForm)));
    }

    public String merged (ContactData contact) {
        return Arrays.asList(
                contact.getFirstName(), contact.getLastName(), contact.getAddress(),
                contact.getHomePhoneNumber(), contact.getMobilePhoneNumber(),
                contact.getEmail()).stream()
                .map(ContactDetailsTests::cleaned)
                .collect(Collectors.joining());
    }

    public static String cleaned(String contactData) {
        return contactData
                .replaceAll("H:", "").replaceAll("M:", "").replaceAll("[()]", "")
                .replaceAll("\n", "").replaceAll("\\s", "");
    }
}
