import GUI.LoginForm;
import GUI.RoomS;
import Service.UserService;

public class App {
    public static void main(String[] args) {
        UserService userService = new UserService();
        new LoginForm(userService).setVisible(true);
        //new RoomS().setVisible(true);
    }
}
