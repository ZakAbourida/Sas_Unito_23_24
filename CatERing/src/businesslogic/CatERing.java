package businesslogic;

import businesslogic.menu.MenuManager;
import businesslogic.recipe.RecipeBookManager;
import businesslogic.user.UserManager;

public class CatERing {
    private static CatERing singleInstance;

    public static CatERing getInstance() {
        if (singleInstance == null) {
            singleInstance = new CatERing();
        }
        return singleInstance;
    }

    private MenuManager menuMgr;
    private RecipeBookManager recipeMgr;
    private UserManager userMgr;

    private CatERing() {
        menuMgr = new MenuManager();
        recipeMgr = new RecipeBookManager();
        userMgr = new UserManager();
    }


    public MenuManager getMenuManager() {
        return menuMgr;
    }

    public RecipeBookManager getRecipeManager() {
        return recipeMgr;
    }

    public UserManager getUserManager() {
        return userMgr;
    }
}
