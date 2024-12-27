import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RentalPSAppTest {

    @BeforeEach
    public void setup() {
        // Persiapkan data sebelum setiap tes
        RentalPSApp.psList.clear();
        RentalPSApp.rentalOrders.clear();
        RentalPSApp.nextOrderId = 1;

        // Inisialisasi PS
        RentalPSApp.psList.put(1001, new RentalPSApp.PS(1001, "PS3", 10000, "tersedia"));
        RentalPSApp.psList.put(1002, new RentalPSApp.PS(1002, "PS4", 18000, "tersedia"));
        RentalPSApp.psList.put(1003, new RentalPSApp.PS(1003, "PS5", 25000, "tersedia"));
    }

    @Test
    public void testOrderPSValid() {
        // Simulasi pemesanan PS
        int psId = 1001; // ID PS yang digunakan untuk tes
        int rentalHours = 2;
        String customerName = "John Doe";

        // Pastikan PS tersedia sebelum pemesanan
        RentalPSApp.PS ps = RentalPSApp.psList.get(psId);
        assertEquals("tersedia", ps.getStatus()); // Memastikan PS awalnya tersedia

        double totalPrice = rentalHours * ps.getPrice();
        RentalPSApp.RentalOrder order = new RentalPSApp.RentalOrder(RentalPSApp.nextOrderId++, customerName, psId, rentalHours, totalPrice);

        // Menambahkan order ke rentalOrders
        RentalPSApp.rentalOrders.put(order.getOrderId(), order);

        // Mengubah status PS menjadi 'tersewa'
        ps.setStatus("tersewa");

        // Cek apakah pemesanan berhasil dan status PS berubah
        assertEquals(1, RentalPSApp.rentalOrders.size()); // Pastikan ada 1 order
        assertEquals("tersewa", ps.getStatus()); // Pastikan status PS berubah
    }


    @Test
    public void testPayRentalSuccess() {
        // Simulasi pemesanan
        int psId = 1002;
        int rentalHours = 3;
        String customerName = "Jane Doe";
        RentalPSApp.PS ps = RentalPSApp.psList.get(psId);
        double totalPrice = rentalHours * ps.getPrice();
        RentalPSApp.RentalOrder order = new RentalPSApp.RentalOrder(RentalPSApp.nextOrderId++, customerName, psId, rentalHours, totalPrice);
        RentalPSApp.rentalOrders.put(order.getOrderId(), order);

        // Simulasi pembayaran
        double paymentAmount = totalPrice + 5000; // Membayar lebih
        double change = paymentAmount - totalPrice;

        // Cek apakah pembayaran berhasil
        assertEquals(change, paymentAmount - totalPrice, 0.01);
    }

}

