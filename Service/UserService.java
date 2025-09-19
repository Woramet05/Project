package Service;

import java.util.ArrayList;
import java.util.List;
import Model.IUser;
import Model.User;
import Model.Admin;

public class UserService {
    private List<IUser> users = new ArrayList<>();

    public UserService() {
        // mock admin account
        users.add(new Admin("owen", "1234"));
    }

    public boolean login(String u, String p) {
        for (IUser user : users) {
            if (user.getUsername().equals(u) && user.getPassword().equals(p)) {
                return true;
            }
        }
        return false;
    }

    public void register(String u, String p) {
        users.add(new User(u, p));
    }
}
