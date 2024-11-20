package chatsystem.Interface;

public class View implements ContactList.Observer{

    public static void initialize(){
        System.out.println("CHATSYSTEM APPLICATION");
        View view = new View();
        ContactList.getInstance().addObserver(view);
    }
    void displayContactList(){
        System.out.println();
        System.out.println("CONTACT LIST : ");
        for(Contact contact : ContactList.getInstance().getContacts()){
            System.out.println("   "+contact);
        }
        System.out.println();
    }

    @Override
    public void newContactAdded(Contact contact) {
        System.out.println("[VIEW] New contact: "+ contact.username());
        displayContactList();
    }
}
