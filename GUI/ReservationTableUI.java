package GUI;


import javax.swing.*;

import Model.Reservation;
import Util.UserSession;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ReservationTableUI extends JFrame {

    // เก็บ checkbox ที่สร้างในแต่ละชั้น
    private final Map<String, java.util.List<JCheckBox>> floorCheckBoxes = new HashMap<>();
    private final JPanel tablePanel = new JPanel();
    
    // เก็บทุก checkbox ที่ยังเลือกได้ (สีเขียว/enable)
    private final java.util.List<JCheckBox> selectableBoxes = new java.util.ArrayList<>();


    // เก็บสถานะจริงของการจอง
    private final Map<String, Boolean> bookingStatus = new HashMap<>();

    // เวลาที่เปิดให้จอง
    private final String[] times = {
            "09:00-10:00", "10:00-11:00", "11:00-12:00",
            "12:00-13:00", "13:00-14:00", "14:00-15:00", "15:00-16:00"
    };

    // ห้องแต่ละชั้น
    private final Map<String, String[]> floorRooms = new HashMap<>();

    public ReservationTableUI() {
        setTitle("ตารางการจอง");
        setSize(1100, 750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ห้อง
        floorRooms.put("ชั้น 1", new String[]{"S01","S02","S03","S04","S05"});
        floorRooms.put("ชั้น 2", new String[]{"L01","L02","L03","L04","L05"});

        // =================== Panel หลัก ===================
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(220, 235, 255)); // ฟ้าอ่อน
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(mainPanel, BorderLayout.CENTER);

        // -------- ปุ่มเมนูด้านบน --------
        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
        navPanel.setOpaque(false);

        JButton btnRoom = makeTopButton("ห้อง");
        JButton btnSchedule = makeTopButton("ตารางการจอง");
        JButton btnMyBooking = makeTopButton("การจองของฉัน");

        navPanel.add(btnRoom);
        navPanel.add(btnSchedule);
        navPanel.add(btnMyBooking);

        mainPanel.add(navPanel, BorderLayout.NORTH);

        btnMyBooking.addActionListener((ActionListener) new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        // เปิดหน้า HomeGUI
        MyReservationGUI MyReservationGUI = new MyReservationGUI();
        MyReservationGUI.setVisible(true);

        // ปิดหน้าปัจจุบัน (StatusGUI)
        dispose();
    }
});

    // ปุ่ม ห้อง
    btnRoom.addActionListener((ActionListener) new ActionListener() {
        public void actionPerformed(ActionEvent e){
            dispose();
            new RoomS().setVisible(true);
        }
    });

        // =================== ส่วนกลาง ===================
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setOpaque(false);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        
        // -------- ฟิลเตอร์วันที่ --------
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        filterPanel.setOpaque(false);

        JLabel lblDate = new JLabel("วันที่จอง");
        lblDate.setFont(new Font("tahoma", Font.PLAIN, 16));

        // วันที่จริง วันนี้/พรุ่งนี้
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        String[] dateOptions = {
                today.format(formatter),
                tomorrow.format(formatter)
        };

        JLabel lblFloor = new JLabel("เลือกชั้น");
        lblFloor.setFont(new Font("Tahoma", Font.PLAIN, 16));

        String[] floorOptions = {"ทุกชั้น", "ชั้น 1", "ชั้น 2"};
        JComboBox<String> cmbFloor = new JComboBox<>(floorOptions);
        cmbFloor.setFont(new Font("Tahoma", Font.PLAIN, 14));

        JButton btnReset = new JButton("รีเซ็ต");
        btnReset.setFont(new Font("Tahoma", Font.BOLD, 14));

        JComboBox<String> cmbDate = new JComboBox<>(dateOptions);
        cmbDate.setFont(new Font("Tahoma", Font.PLAIN, 14));
        filterPanel.add(lblDate);
        filterPanel.add(cmbDate);
        filterPanel.add(lblFloor);
        filterPanel.add(cmbFloor);
        filterPanel.add(btnReset);

        centerPanel.add(filterPanel, BorderLayout.NORTH);

        // -------- ตารางการจอง --------
        tablePanel.setLayout(new BoxLayout(tablePanel, BoxLayout.Y_AXIS));
        tablePanel.setOpaque(false);

        JScrollPane scrollPane = new JScrollPane(tablePanel);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        // -------- สถานะ + ปุ่มจอง --------
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);

        // Legend ด้านซ้าย
        JPanel legendPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        legendPanel.setOpaque(false);
        legendPanel.add(makeLegend("ว่าง (AVAILABLE)", Color.GREEN));
        legendPanel.add(makeLegend("จองแล้ว (OCCUPIED)", Color.RED));
        legendPanel.add(makeLegend("ห้องไม่เปิดใช้งาน (CLOSE)", Color.BLACK));

        // ปุ่มจอง ด้านขวา
        JButton btnReserve = new JButton("จอง");
        btnReserve.setBackground(new Color(51,102,255));
        btnReserve.setForeground(Color.WHITE);
        btnReserve.setFont(new Font("Tahoma", Font.BOLD, 18));
        btnReserve.setFocusPainted(false);
        btnReserve.setPreferredSize(new Dimension(120, 40));

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.setOpaque(false);
        rightPanel.add(btnReserve);

        bottomPanel.add(legendPanel, BorderLayout.WEST);
        bottomPanel.add(rightPanel, BorderLayout.EAST);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        // =================== Event ===================
        // โหลดตารางใหม่ตามชั้น
        Runnable updateTable = () -> {
            tablePanel.removeAll();
            floorCheckBoxes.clear();
            selectableBoxes.clear(); // << สำคัญ : ล้างทุกครั้งก่อนสร้างใหม่

            String selected = (String) cmbFloor.getSelectedItem();
            String selectedDate = (String) cmbDate.getSelectedItem();

            // โหลดสถานะเฉพาะ "วันที่ที่เลือก" ก่อนวาด
            reloadBookingStatus(selectedDate);

            if ("ทุกชั้น".equals(selected)) {
                for (String floor : floorRooms.keySet()) {
                    addFloorTable(tablePanel, floor, floorRooms.get(floor), times, true, selectedDate);
                }
            } else {
                addFloorTable(tablePanel, selected, floorRooms.get(selected), times, false, selectedDate);
            }

            tablePanel.revalidate();
            tablePanel.repaint();
        };

        cmbDate.addActionListener(e -> updateTable.run());
        cmbFloor.addActionListener(e -> updateTable.run());
        updateTable.run(); // โหลดครั้งแรก

        // ปุ่มรีเซ็ต → reset แค่ dropdown แต่ไม่รีข้อมูลจอง
        btnReset.addActionListener(e -> {
        cmbFloor.setSelectedItem("ทุกชั้น");
        updateTable.run();
        });

        // ปุ่มจอง
        btnReserve.addActionListener(e -> {
            boolean booked = false;

            String selectedDate = (String) cmbDate.getSelectedItem();
            String username;
            if (UserSession.getCurrentUser() != null) {
                username = UserSession.getCurrentUser().getUsername();
            } else {
                username = "UnknowUser";
            }

            // โหลดข้อมูลการจองทั้งหมดจากไฟล์
            java.util.List<Reservation> allBookings = Util.FileHandler.loadBookings();

            // นับจำนวนชั่วโมงที่ผู้ใช้นี้จองในวันเดียวกัน
            int bookedCount = (int) allBookings.stream()
                    .filter(r -> r.getUsername().equals(username) && r.getDate().equals(selectedDate))
                    .count();

            // ถ้ามากกว่า 2 แล้ว ห้ามจองเพิ่ม
            if (bookedCount >= 2) {
                JOptionPane.showMessageDialog(this,"คุณสามารถจองได้ไม่เกิน 2 ชั่วโมงต่อวัน!",
                "แจ้งเตือน", JOptionPane.WARNING_MESSAGE);
                return; // หยุดการทำงาน ไม่ให้จองต่อ
            }

            outer:
            for (String floor : floorCheckBoxes.keySet()) {
                java.util.List<JCheckBox> list = floorCheckBoxes.get(floor);
                String[] rooms = floorRooms.get(floor);

                for (int i = 0; i < list.size(); i++) {
                    JCheckBox cb = list.get(i);
                    if (cb.isSelected() && cb.isEnabled()) {

                        // ตรวจซ้ำอีกครั้งก่อนบันทึก (กันพลาด)
                        if (bookedCount >= 2) {
                            JOptionPane.showMessageDialog(this,
                                    "คุณจองครบ 2 ชั่วโมงแล้ว ไม่สามารถจองเพิ่มได้!",
                                    "แจ้งเตือน", JOptionPane.WARNING_MESSAGE);
                            break outer;
                        
                        }
                        int roomIndex = i / times.length;
                        int timeIndex = i % times.length;

                        String room = rooms[roomIndex];
                        String time = times[timeIndex];

                        Util.FileHandler.saveBookings(new Reservation(room, selectedDate, time, username));

                        cb.setBackground(Color.RED);
                        cb.setEnabled(false);
                        booked = true;

                        allBookings.add(new Reservation(room, selectedDate, time, username)); // อัปเดตในลิสต์ทันที
                        bookedCount++; // เพิ่มตัวนับทันที
                        break outer; // single-select เจออันเดียวก็พอ ออกได้เลย
                    }
                }
            }
            UIManager.put("OptionPane.messageFont", new Font("Tahoma", Font.PLAIN, 16));

            if (booked) {
                JOptionPane.showMessageDialog(this, "ทำการจองเรียบร้อยแล้ว",
                        "สถานะ", JOptionPane.INFORMATION_MESSAGE);
                updateTable.run();
            } else {
                JOptionPane.showMessageDialog(this, "กรุณาเลือกห้องก่อนทำการจอง",
                        "แจ้งเตือน", JOptionPane.WARNING_MESSAGE);
            }
        });
    }

    // ====== สร้างปุ่มบน ======
    private JButton makeTopButton(String text) {
        JButton btn = new JButton(text);
        btn.setBackground(new Color(51, 102, 255));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Tahoma", Font.BOLD, 16));
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(160, 45));
        return btn;
    }

    // ====== Cell ตาราง ======
    private JPanel makeCell(String text, Color bg, boolean isHeader) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(bg);
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("Tahoma", isHeader ? Font.BOLD : Font.PLAIN, isHeader ? 14 : 12));
        panel.add(label, BorderLayout.CENTER);
        return panel;
    }

    // ====== Legend ======
    private JPanel makeLegend(String text, Color color) {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));
        p.setOpaque(false);
        JPanel box = new JPanel();
        box.setBackground(color);
        box.setPreferredSize(new Dimension(30, 20));
        p.add(box);
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Tahoma", Font.PLAIN, 14));
        p.add(lbl);
        return p;
    }

    // ====== โหลดสถานะการจองตามวันที่ที่เลือก ======
    private void reloadBookingStatus(String selectedDate) {
        bookingStatus.clear();
        java.util.List<Reservation> all = Util.FileHandler.loadBookings();
        for (Reservation reservation : all) {
            if (selectedDate.equals(reservation.getDate())) {
                // key แยกวันที่ + ห้อง + ช่วงเวลา
                String key = reservation.getDate() + "#" + reservation.getRoom() + "#" + reservation.getTime();
                bookingStatus.put(key, true);
            }
        }
    }

    // ====== เพิ่มตารางของแต่ละชั้น ======
    private void addFloorTable(JPanel tablePanel, String floorName, String[] rooms, String[] times, boolean limitTo3, String selectedDate) {
        JLabel floorLabel = new JLabel(floorName);
        floorLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
        floorLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        tablePanel.add(floorLabel);

        int maxRooms = limitTo3 ? Math.min(3, rooms.length) : rooms.length;

        JPanel gridPanel = new JPanel(new GridLayout(maxRooms+1, times.length+1, 1, 1));
        gridPanel.setBackground(Color.BLACK);

        // header
        gridPanel.add(makeCell("ห้อง", Color.LIGHT_GRAY, true));
        for (String t : times) {
            gridPanel.add(makeCell(t, Color.LIGHT_GRAY, true));
        }

        // checkbox ของชั้นนี้
        java.util.List<JCheckBox> list = new ArrayList<>();

        for (int i=0; i<maxRooms; i++) {
            String r = rooms[i];
            gridPanel.add(makeCell(r, Color.LIGHT_GRAY, true));

            for (String time : times) {
                String key = selectedDate + "#" + r + "#" + time;

                JCheckBox cb = new JCheckBox();
                cb.setHorizontalAlignment(SwingConstants.CENTER);

                // ถ้ามีสถานะเก็บไว้แล้ว
                if (bookingStatus.getOrDefault(key, false)) {
                    cb.setBackground(Color.RED);
                    cb.setEnabled(false);
                } else {
                    cb.setBackground(Color.GREEN);
                    cb.setEnabled(true);

                    // เพิ่ม Checkbox ที่เลือกได้ลงในลิสต์รวม
                    selectableBoxes.add(cb);

                    // ให้เลือกได้ทีละช่องทั่วทั้งตาราง
                    cb.addActionListener(ev -> {
                        if (cb.isSelected()) {
                            for(JCheckBox other : selectableBoxes){
                                if (other != cb) {
                                    other.setSelected(false);
                                }
                            }
                        }
                    });
                }

                gridPanel.add(cb);
                list.add(cb);
            }
        }

        floorCheckBoxes.put(floorName, list);

        tablePanel.add(gridPanel);
        tablePanel.add(Box.createVerticalStrut(20));
    }
    public static void main(String[] args) {
        new ReservationTableUI().setVisible(true);
    } 
}
