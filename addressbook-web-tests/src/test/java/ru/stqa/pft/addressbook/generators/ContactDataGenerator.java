package ru.stqa.pft.addressbook.generators;

import ru.stqa.pft.addressbook.model.ContactData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class ContactDataGenerator {
    public static void main (String[] args) throws IOException {
        int count = Integer.parseInt(args[0]);
        File file = new File(args[1]);

        List<ContactData> contacts = generateContacts(count);
        save(contacts, file);
    }

    private static void save(List<ContactData> contacts, File file) throws IOException {
        System.out.println(new File(".").getAbsolutePath());
        Writer writer = new FileWriter(file);
        for (ContactData contact : contacts){
            writer.write(String.format("%s;%s;%s;%s;%s;%s\n", contact.getFirstName(),
                    contact.getLastName(), contact.getAddress(), contact.getEmail(),
                    contact.getHomePhoneNumber(), contact.getMobilePhoneNumber()));
        }
        writer.close();
    }

    private static List<ContactData> generateContacts(int count) {
        List<ContactData> contacts = new ArrayList<>();
        for (int i = 0; i < count; i++){
            contacts.add(new ContactData()
                    .withFirstName(String.format("First %s", i))
                    .withLastName(String.format("Last %s", i))
                    .withAddress(String.format("vul.Some %s", i))
                    .withEmail(String.format("%1$s%1$s%1$s@mail.ru", i))
                    .withHomePhoneNumber(String.format("%1$s%1$s%1$s", i))
                    .withMobilePhoneNumber(String.format("%1$s%1$s%1$s", i+1)));
        }
        return contacts;
    }
}
