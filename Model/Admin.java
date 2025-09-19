package Model;

public class Admin implements IUser {
    private String username;
    private String password;
    private String role;

    public Admin(String username, String password) {
        this.username = username;
        this.password = password;
        this.role = "admin";
    }

    @Override
    public String getUsername() { return username; }

    @Override
    public String getPassword() { return password; }

    @Override
    public String getRole() { return role; }
}
