package Util;

import Model.User;

/**
 * Class UserSession ใช้เก็บสถานะของผู้ใช้ที่ Login อยู่ในระบบ
 * สามารถเรียกใช้จากทุกหน้า GUI ได้โดยตรง
 */
public class UserSession {

    // ตัวแปรเก็บผู้ใช้ที่ Login อยู่ปัจจุบัน
    private static User currentUser;
    
    /**
     * คืนค่าผู้ใช้ที่ Login (เรียนกตอน Login สำเร็จ)
     * @param user ผู้ใช้ที่ Login
     */
    public static void setCurrentUser(User user){
        currentUser = user;
    }
    
    /**
     * คืนค่าผู้ใช้ที่ Login อยู่
     * @return User ปัจจุบัน หรือ null ถ้ายังไม่มีใคร Login
     */
    public static User getCurrentUser(){
        return currentUser;
    }

    public static boolean isAdmin() {
        return getCurrentUser() != null && "admin".equalsIgnoreCase(getCurrentUser().getRole());
    }

    /**
     * ล้างข้อมูลผู้ใช้ (เรียกตอนออกจากระบบ)
     */
    public static void clear(){
        currentUser = null;
    }
}
