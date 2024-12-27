import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.*;

public class RentalPSApp {

    private static JFrame frame;
    private static JPanel panel;
    static JTextField tfCustomerName;
    static JTextField tfRentalHours;
    static JTextField tfPaymentAmount;
    static JTextField tfPsId;
    private static JButton btnAdd, btnShow, btnOrder, btnPay, btnDelete, btnExit;
    private static JTable tableOrders;
    private static DefaultTableModel tableModel;
    public static Map<Integer, PS> psList = new HashMap<>();
    public static Map<Integer, RentalOrder> rentalOrders = new HashMap<>();
    public static int nextOrderId = 1;

    public static void main(String[] args) {
        // Initial setup for JFrame
        frame = new JFrame("Rental PS");
        frame.setSize(600, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Initialize PS list (PS3, PS4, PS5)
        psList.put(1001, new PS(1001, "PS3", 10000, "tersedia"));
        psList.put(1002, new PS(1002, "PS4", 18000, "tersedia"));
        psList.put(1003, new PS(1003, "PS5", 25000, "tersedia"));

        // Initialize Table Model for orders
        String[] columns = {"Nama Penyewa", "ID PS", "Jam Sewa", "Total Harga"};
        tableModel = new DefaultTableModel(columns, 0);
        tableOrders = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tableOrders);

        // Input fields
        tfCustomerName = new JTextField(20);
        tfRentalHours = new JTextField(20);
        tfPaymentAmount = new JTextField(20);
        tfPsId = new JTextField(20);

        // Buttons
        btnAdd = new JButton("Tambah PS");
        btnShow = new JButton("Tampilkan PS");
        btnOrder = new JButton("Pesan PS");
        btnPay = new JButton("Bayar Sewa");
        btnDelete = new JButton("Hapus Riwayat");
        btnExit = new JButton("Keluar");

        panel.add(new JLabel("Nama Pemesan:"));
        panel.add(tfCustomerName);
        panel.add(new JLabel("Jam Sewa:"));
        panel.add(tfRentalHours);
        panel.add(btnOrder);
        panel.add(btnShow);
        panel.add(new JLabel("Tabel Pemesanan:"));
        panel.add(scrollPane);
        panel.add(new JLabel("ID PS (untuk Bayar):"));
        panel.add(tfPsId);
        panel.add(new JLabel("Jumlah Pembayaran:"));
        panel.add(tfPaymentAmount);
        panel.add(btnPay);
        panel.add(btnDelete);
        panel.add(btnExit);

        frame.add(panel);
        frame.setVisible(true);

        // Button actions
        btnShow.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showPS();
            }
        });

        btnOrder.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                orderPS();
            }
        });

        btnPay.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                payRental();
            }
        });

        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteHistory();
            }
        });

        btnExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                exitProgram();
            }
        });
    }

    // Method to show available PS
    private static void showPS() {
        StringBuilder psListStr = new StringBuilder("Daftar PS yang tersedia:\n");
        for (PS ps : psList.values()) {
            psListStr.append("ID: " + ps.getId() + ", Nama: " + ps.getName() + ", Harga per Jam: " + ps.getPrice() + ", Status: " + ps.getStatus() + "\n");
        }
        JOptionPane.showMessageDialog(frame, psListStr.toString(), "Daftar PS", JOptionPane.INFORMATION_MESSAGE);
    }

    // Method to order a PS
    static void orderPS() {
        String customerName = tfCustomerName.getText().trim();
        String rentalHoursStr = tfRentalHours.getText().trim();

        if (customerName.isEmpty() || rentalHoursStr.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Nama Pemesan dan Jam Sewa tidak boleh kosong!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int rentalHours = Integer.parseInt(rentalHoursStr);
            if (rentalHours <= 0) {
                JOptionPane.showMessageDialog(frame, "Jam Sewa harus lebih besar dari 0!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Display PS selection dialog
            String[] psIds = {"1001 - PS3", "1002 - PS4", "1003 - PS5"};
            String psChoice = (String) JOptionPane.showInputDialog(frame, "Pilih PS yang akan disewa", "Pilih PS",
                    JOptionPane.QUESTION_MESSAGE, null, psIds, psIds[0]);

            if (psChoice == null) return;

            // Extract PS ID from selected choice
            int psId = Integer.parseInt(psChoice.split(" - ")[0]);
            PS ps = psList.get(psId);
            if (ps.getStatus().equals("tersewa")) {
                JOptionPane.showMessageDialog(frame, "PS ini sudah disewa!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Create rental order
            double totalPrice = rentalHours * ps.getPrice();
            RentalOrder newOrder = new RentalOrder(nextOrderId++, customerName, psId, rentalHours, totalPrice);
            rentalOrders.put(newOrder.getOrderId(), newOrder);

            // Update PS status to "tersewa"
            ps.setStatus("tersewa");

            // Update table
            tableModel.addRow(new Object[]{customerName, psId, rentalHours, totalPrice});

            tfCustomerName.setText("");
            tfRentalHours.setText("");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Jam Sewa harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to process payment
    public static void payRental() {
        String paymentStr = tfPaymentAmount.getText().trim();

        if (paymentStr.isEmpty() || tfPsId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "ID PS dan Jumlah Pembayaran tidak boleh kosong!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            double paymentAmount = Double.parseDouble(paymentStr);
            int psId = Integer.parseInt(tfPsId.getText().trim());

            RentalOrder order = rentalOrders.values().stream()
                    .filter(o -> o.getPsId() == psId)
                    .findFirst()
                    .orElse(null);

            if (order != null) {
                double change = paymentAmount - order.getTotalPrice();
                if (change >= 0) {
                    JOptionPane.showMessageDialog(frame, "Pembayaran berhasil! Kembalian: " + change, "Pembayaran", JOptionPane.INFORMATION_MESSAGE);

                    // PS will remain "tersewa" even after payment
                    // No change in PS status here

                } else {
                    JOptionPane.showMessageDialog(frame, "Jumlah pembayaran kurang!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Pesanan dengan ID PS tersebut tidak ditemukan!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "ID PS dan Jumlah Pembayaran harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to delete rental history
    public static void deleteHistory() {
        String orderIdStr = JOptionPane.showInputDialog(frame, "Masukkan ID PS yang ingin dihapus dari riwayat:");
        if (orderIdStr != null && !orderIdStr.trim().isEmpty()) {
            try {
                int psId = Integer.parseInt(orderIdStr);
                PS psToDelete = psList.get(psId);
                if (psToDelete != null && psToDelete.getStatus().equals("tersewa")) {
                    // Set PS back to available
                    psToDelete.setStatus("tersedia");

                    // Remove corresponding rental order
                    rentalOrders.values().removeIf(order -> order.getPsId() == psId);

                    // Update table (remove paid order)
                    for (int i = 0; i < tableModel.getRowCount(); i++) {
                        if (tableModel.getValueAt(i, 1).equals(psId)) {
                            tableModel.removeRow(i);
                            break;
                        }
                    }

                    JOptionPane.showMessageDialog(frame, "PS dengan ID " + psId + " telah dihapus dari riwayat.", "Riwayat Dihapus", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(frame, "PS tidak ditemukan atau tidak tersewa.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "ID PS harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Method to exit the program
    private static void exitProgram() {
        System.exit(0);
    }

    // PS class (PlayStation)
    static class PS {
        private int id;
        private String name;
        private double price;
        private String status;

        public PS(int id, String name, double price, String status) {
            this.id = id;
            this.name = name;
            this.price = price;
            this.status = status;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public double getPrice() {
            return price;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

    static class RentalOrder {
        private int orderId;
        String customerName;
        int psId;
        private int rentalHours;
        double totalPrice;

        public RentalOrder(int orderId, String customerName, int psId, int rentalHours, double totalPrice) {
            this.orderId = orderId;
            this.customerName = customerName;
            this.psId = psId;
            this.rentalHours = rentalHours;
            this.totalPrice = totalPrice;
        }

        public int getOrderId() {
            return orderId;
        }

        public int getPsId() {
            return psId;
        }

        public double getTotalPrice() {
            return totalPrice;
        }

    }
}
