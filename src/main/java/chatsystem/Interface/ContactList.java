package chatsystem.Interface;
import chatsystem.Core.Exceptions.ContactAlreadySaved;

import java.util.ArrayList;
import java.util.List;

public class ContactList {

    public interface Observer {
        void newContactAdded(Contact contact);
    }
    private static final ContactList INSTANCE = new ContactList();

    private static Contact self = null;

    public static ContactList getInstance(){
        return INSTANCE;
    }
    List<Contact> contacts = new ArrayList<>();
    List<Observer> observers = new ArrayList<>();
    private ContactList(){
    }
    
    public Contact getSelf() {
    	return self;
    }
    
    public void setSelf(String username) {
    	Contact c = new Contact(username);
    	self = c;
    }
    
    public synchronized void addObserver(Observer obs){
        observers.add(obs);
    }

    public synchronized Contact addUser(String username) throws ContactAlreadySaved{
        Contact contact;
        if (hasUsername(username)){
            throw new ContactAlreadySaved(username);
        }else{
            contact = new Contact(username);
            contacts.add(contact);
            for (Observer obs : observers){
                obs.newContactAdded(contact);
            }
        }
        return contact;
    }

    public synchronized boolean hasUsername(String username){
        for (Contact contact : contacts){
            if(contact.username().equals(username)){
                return true;
            }
        }
        return false;
    }

    public synchronized List<Contact> getContacts(){
        return new ArrayList<>(this.contacts);
    }

    /** FOR TESTS **/
    public synchronized void clearList(){
        this.contacts.clear();
    }
}
