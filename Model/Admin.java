package Model;
/**
 * Class Admin ใช้แทนผู้ดูแลระบบ
 * เก็บข้อมูล username, password และ role = "admin"
 */
public class Admin implements IUser {
    private String username;
    private String password;
    private String role;

    /**
     * สร้างออบเจ็กต์ Admin ใหม่
     * @param username ชื่อผู้ใช้
     * @param password รหัสผ่าน
     */
    public Admin(String username, String password) {
        this.username = username;
        this.password = password;
        this.role = "admin";
    }

    /**
     * คืนค่า username
     * @return username
     */
    @Override
    public String getUsername() { return username; }

    /**
     * คืนค่า password
     * @return password
     */
    @Override
    public String getPassword() { return password; }

    /**
     * คืนค่า role (admin)
     * @return "admin"
     */
    @Override
    public String getRole() { return role; }
}
