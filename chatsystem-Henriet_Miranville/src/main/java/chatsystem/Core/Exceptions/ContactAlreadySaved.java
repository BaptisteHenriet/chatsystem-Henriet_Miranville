package chatsystem.Core.Exceptions;


public class ContactAlreadySaved extends Exception{

    private final String username;
    public ContactAlreadySaved(String username) {
        this.username = username;
    }

    @Override
    public String toString(){
        return "Contact already exists{" + "username = "+ username + '\''+"}";
    }
}
