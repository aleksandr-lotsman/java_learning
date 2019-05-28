package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactPhoneTests extends TestBase {


	@BeforeMethod
	public void ensurePreconditions() {
		app.goTo().homePage();
		if (app.contact().list().size() == 0){
			app.contact().create(new ContactData().withFirstName("firstName").withLastName("lastName").withGroup("test1").withHomePhoneNumber("111")
					.withMobilePhoneNumber("222"), true);
		}
	}


	@Test
	public void testContactPhones() {
		app.goTo().homePage();
		ContactData contact = app.contact().all().iterator().next();
		ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);

		assertThat(contact.getHomePhoneNumber(), equalTo(cleaned(contactInfoFromEditForm.getHomePhoneNumber())));
		assertThat(contact.getMobilePhoneNumber(), equalTo(cleaned(contactInfoFromEditForm.getMobilePhoneNumber())));
	}

	public String cleaned(String phone) {
		return phone.replaceAll("\\s", "").replaceAll("[-()]", "");
	}
}
