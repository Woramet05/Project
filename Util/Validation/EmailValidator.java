package Util.Validation;

/**
 * ที่ตรวจสอบความถูกต้องของ Email
 */
public class EmailValidator {

    /**
     * Email ต้องไม่เป็น null และ ลงท้ายด้วย @ku.th
     * @param email
     * @return true ถ้าตรงตามเงื่อนไข, false ถ้าไม่ตรง
     */
    public static boolean validate(String email){
        return email != null && email.endsWith("@gmail.com");
    }
}
