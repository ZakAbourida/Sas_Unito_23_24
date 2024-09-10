package catering.businesslogic.user;

/**
 * Manages user-related operations in the business logic layer.
 * <p>
 * This class handles user login and provides access to the currently logged-in user.
 * </p>
 */
public class UserManager {
    private User currentUser;

    /**
     * Simulates a user login by loading a user based on the provided username.
     * <p>
     * This method is intended for testing purposes and sets the current user
     * based on the given username.
     * </p>
     *
     * @param username The username of the user to be loaded and set as current user.
     */
    public void fakeLogin(String username) {
        this.currentUser = User.loadUser(username);
    }

    /**
     * Returns the currently logged-in user.
     * <p>
     * This method provides access to the user who is currently logged in.
     * </p>
     *
     * @return The currently logged-in user.
     */
    public User getCurrentUser() {
        return this.currentUser;
    }
}
