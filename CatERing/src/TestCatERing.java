import businesslogic.CatERing;
import businesslogic.UseCaseLogicException;
import businesslogic.menu.Menu;

public class TestCatERing {
    public static void main(String[] args) {
        try {
            CatERing.getInstance().getUserManager().fakeLogin();
            Menu m = CatERing.getInstance().getMenuManager().createMenu("Prova menu");
            System.out.println(m.toString());
        } catch (UseCaseLogicException e) {
            System.out.println("Errore di logica nello use case");
        }
    }
}
