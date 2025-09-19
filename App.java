
import GUI.TestLoginForm;
import Service.UserService;

public class App {
    public static void main(String[] args) {
        UserService userService = new UserService();
        java.awt.EventQueue.invokeLater(() -> new TestLoginForm().setVisible(true));
    }
}
