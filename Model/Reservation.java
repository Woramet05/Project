package Model;
/**
 * ใช้เก็บข้อมูลการจองห้องของผู้ใช้
 * โดยมีข้อมูล ห้อง, วันที่, เวลา, ชื่อผู้ใช้
 */
public class Reservation {
    private String room;
    private String date;
    private String time;
    private String username;

    /**
     * สร้างอ็อบเจกต์ Reservation ใหม่
     * @param room
     * @param date
     * @param time
     * @param username
     */

    public Reservation(String room, String date, String time, String username){
        this.room = room;
        this.date = date;
        this.time = time;
        this.username = username;
    }

    // ---------- Get / Set ----------

    public String getRoom() { return room; }

    public void setRoom(String room) { this.room = room; }

    public String getDate() { return date; }

    public void setDate(String date) { this.date = date; }

    public String getTime() { return time; }

    public void setTime(String time) { this.time = time; }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    /**
     * แปลง Reservation เป็นข้อความ 1 บรรทัด
     * @return room, date, time, username
     */
    public String toFileString(){
        return room + "|" + date + "|" + time + "|" + username;
    }

    public static Reservation fromFileString(String line){
        String[] parts = line.split("\\|");
        if (parts.length != 4) {
            return null;
        }

        return new Reservation(parts[0], parts[1], parts[2], parts[3]);
    }
}
