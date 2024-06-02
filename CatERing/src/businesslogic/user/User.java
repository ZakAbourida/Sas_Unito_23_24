package businesslogic.user;

public class User {
    private User user;
    private String name;

    public boolean isChef() {
        return user instanceof Chef;
    }
    public boolean isOrganiser() {
        return user instanceof Organiser;
    }
    public boolean isCook() {
        return user instanceof Cook;
    }
    public String getUserName() {
        return this.name;
    }
}
