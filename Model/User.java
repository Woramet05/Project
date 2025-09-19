package Model;

public class User implements IUser {
    private String username;
    private String password;
    private String role; // "user" or "admin"

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.role = "user";
    }

    @Override
    public String getUsername() { return username; }

    @Override
    public String getPassword() { return password; }

    @Override
    public String getRole() { return role; }
}
