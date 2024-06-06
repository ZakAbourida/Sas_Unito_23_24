package catering.businesslogic;

import catering.businesslogic.SummarySheet.SummarySheet;
import catering.businesslogic.SummarySheet.SummarySheetManager;
import catering.businesslogic.Turn.Turn;
import catering.businesslogic.event.EventManager;
import catering.businesslogic.menu.MenuManager;
import catering.businesslogic.recipe.Recipe;
import catering.businesslogic.recipe.RecipeManager;
import catering.businesslogic.user.User;
import catering.businesslogic.user.UserManager;
import catering.persistence.MenuPersistence;
import catering.persistence.SheetPersistence;

import java.util.List;

public class CatERing {
    private static CatERing singleInstance;

    public static CatERing getInstance() {
        if (singleInstance == null) {
            singleInstance = new CatERing();
        }
        return singleInstance;
    }

    private MenuManager menuMgr;
    private RecipeManager recipeMgr;
    private UserManager userMgr;
    private EventManager eventMgr;
    private SummarySheetManager sheetMgr;

    private MenuPersistence menuPersistence;
    private SheetPersistence sheetPersistence;

    private CatERing() {
        menuMgr = new MenuManager();
        recipeMgr = new RecipeManager();
        userMgr = new UserManager();
        eventMgr = new EventManager();
        menuPersistence = new MenuPersistence();
        sheetMgr = new SummarySheetManager();
        sheetPersistence = new SheetPersistence();
        menuMgr.addEventReceiver(menuPersistence);
        sheetMgr.addReceiver(sheetPersistence);
    }

    public MenuManager getMenuManager() {
        return menuMgr;
    }

    public RecipeManager getRecipeManager() {
        return recipeMgr;
    }

    public UserManager getUserManager() {
        return userMgr;
    }

    public EventManager getEventManager() {
        return eventMgr;
    }

    public SummarySheetManager getSummarySheetManager() {
        return sheetMgr;
    }

    public List<SummarySheet> loadAllSummarySheets() {
        return SummarySheet.loadAllSummarySheets();
    }

    public List<SummarySheet> loadAllSummarySheetsForService(int serviceId) {
        return SummarySheet.loadAllSummarySheetsForService(serviceId);
    }

    public List<User> loadAllUsers() {
        return User.loadAllUsers();
    }

    public List<Turn> loadAllTurn() {
        return Turn.loadAllTurns();
    }
    public List<Recipe> loadAllRecipes() {
        return Recipe.loadAllRecipes();
    }
}