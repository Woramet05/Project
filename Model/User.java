package Model;
/**
 * Class User แทนผู้ใช้ทั่วไป
 * เก็บข้อมูล Username, Password และ Role = "user"
 */
public class User implements IUser {
    private String username;
    private String password;
    private String role;

    /**
     * สร้างออบเจ็กต์ User ใหม่
     * @param username ชื่อผู้ใช้
     * @param password รหัสผ่าน
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.role = "user";
    }

    /**
     * คืนค่า Username
     * @return username
     */
    @Override
    public String getUsername() { return username; }

    /**
     * คืนค่า Password
     * @return password
     */
    @Override
    public String getPassword() { return password; }

    /**
     * คืนค่า Role (user)
     * @return "user"
     */
    @Override
    public String getRole() { return role; }
}
