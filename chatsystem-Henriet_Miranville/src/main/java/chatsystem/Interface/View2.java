package chatsystem.Interface;

public class View2 implements UserTable.Observer{
    public static void initialize(){
        View2 view2 = new View2();
        UserTable.getInstance().addObserver(view2);
    }
    void displayUserList(){
        System.out.println();
        System.out.println("USER TABLE : ");
        for( User u : UserTable.getTable()){
            System.out.println("   "+u.getContact()+" "+ u.getIp_address());
        }
        System.out.println();
    }

    @Override
    public void newUserAdded(User u) {

        System.out.println("[VIEW] New user: "+ u.getContact().username()+", IP :"+u.getIp_address());

        displayUserList();
    }
}
