package chatsystem.Tests;

import chatsystem.Core.Exceptions.ContactAlreadySaved;
import chatsystem.Interface.ContactList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ContactListTest {

    @BeforeEach
    public void clearContactList(){
        ContactList.getInstance().clearList();
    }
    @Test
    void contactAddTest() throws ContactAlreadySaved {
        ContactList contacts = ContactList.getInstance();

        assert !contacts.hasUsername("flo");
        contacts.addUser("flo");
        assert contacts.hasUsername("flo");
        assert !contacts.hasUsername("bap");

        assert !contacts.hasUsername("bap");
        contacts.addUser("bap");
        assert contacts.hasUsername("flo");
        assert contacts.hasUsername("bap");
    }

    @Test
    void contactAlreadyInList() throws ContactAlreadySaved{
        ContactList contacts = ContactList.getInstance();
        contacts.addUser("flo");
        assert contacts.hasUsername("flo");

        try{
            contacts.addUser("flo");
            throw new RuntimeException("Expected ContactAlreadySaved exception");

        }catch(ContactAlreadySaved e){

        }

    }
}
