package Util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Model.Reservation;
import Model.User;

/**
 * ที่จัดการ อ่าน/เขียนไฟล์ เก็บไว้ใน Users.txt, Bookings.txt
 */
public class FileHandler {
    private static final String File_Name = "Users.txt";


    /**
     * บันทึก User ใหม่ลงไฟล์ .txt
     * @param user User ที่จะบันทึก
     */
    public static void saveUsers(User user){
        File file = new File(File_Name);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file,true))) {
            writer.write(user.toFileString());
            writer.newLine();
            
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static List<User> loadUsers(){
        List<User> users = new ArrayList<>();
        File file = new File(File_Name);

        try (BufferedReader reader = new BufferedReader(new FileReader(file))){
            String line;
            while ((line = reader.readLine()) != null) {
                User user = User.FromFileString(line);
                if (user != null) {
                    users.add(user);
                }
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return users;
    }

    // ------------------- Booking -------------------

    private static final String Booking_Name = "Bookings.txt";

    /**
     * บันทึกข้อมูลการจองใหม่ลงไฟล์
     * 
     * @param reservation การจองที่จะบันท
     */
    public static void saveBookings(Reservation reservation){
        File file = new File(Booking_Name);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file,true))) {
            writer.write(reservation.toFileString());
            writer.newLine();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static List<Reservation> loadBookings(){
        List<Reservation> bookings = new ArrayList<>();
        File file = new File(Booking_Name);

        try (BufferedReader reader = new BufferedReader(new FileReader(file))){
            String line;
            while ((line = reader.readLine()) != null) {
                Reservation reservation = Reservation.fromFileString(line);
                if (reservation != null) {
                    bookings.add(reservation);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return bookings;
    }
}
