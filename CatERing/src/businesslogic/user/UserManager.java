package businesslogic.user;

public class UserManager {
    private User currentUser;

    public void fakeLogin() //TODO: bisogna implementare il login vero!
    {
        this.currentUser = new User();
    }

    public User getCurrentUser() {
        return this.currentUser;
    }
}
