import Controller.AllChecks;
import Controller.CreateFile;
import View.View;


public class Program {
    public static void main(String[] args) {
        AllChecks checks = new AllChecks();
        View view = new View();
        CreateFile create = new CreateFile(checks, view);

        create.start();
    }
}
