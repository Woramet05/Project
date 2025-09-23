package Service;

import java.util.ArrayList;
import java.util.List;
import Model.IUser;
import Model.User;
import Model.Admin;

/**
 * UserService ทกหน้าที่จัดการผู้ใช้ทั้งหมด
 * เก็บ User
 * ตรวจสอบการ Login
 * ตรวจสอบการ Register
 */
public class UserService {
    private List<IUser> users = new ArrayList<>();

    /**
     * สร้าง User และ Admin ตรงนี้ได้
     */
    public UserService() {
        users.add(new Admin("owen", "1234")); // Admin
    }

    /**
     * ตรวจสอบการ login
     * @param username ชื่อผู้ใช้
     * @param password รหัสผ่าน
     * @return true ถ้า username และ password ตรงกับ users ที่เก็บไว้, อื่นๆ false
     */
    public boolean login(String username, String password) {
        for (IUser user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    /**
     * การ register (เพิ่ม user เข้าไปใน List)
     * @param username ชื่อผู้ใช้
     * @param password รหัสผ่าน
     */
    public void register(String username, String password) {
        users.add(new User(username, password));
    }
}
