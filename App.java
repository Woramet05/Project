import GUI.LoginForm;
import Service.UserService;

public class App {
    public static void main(String[] args) {
        UserService userService = new UserService();
        java.awt.EventQueue.invokeLater(() -> new LoginForm(userService).setVisible(true));
    }
}
