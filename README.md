#Program rental ps

# Deskripsi Program "Rental PS"
Program ini adalah aplikasi GUI (Graphical User Interface) berbasis Java menggunakan Swing yang dirancang untuk mengelola penyewaan PlayStation (PS). Program ini memungkinkan pengguna untuk menampilkan daftar PS yang tersedia, memesan PS, melakukan pembayaran, menghapus riwayat pesanan, dan keluar dari aplikasi.

# Fitur Utama
Menampilkan Daftar PS - Pengguna dapat melihat daftar PS yang tersedia lengkap dengan ID, nama, harga per jam, dan status ketersediaan.
Pemesanan PS - Pengguna dapat memesan PS berdasarkan ID dengan menentukan nama pemesan dan durasi penyewaan.
Pembayaran - Pengguna dapat membayar pesanan dengan sistem validasi pembayaran dan menghitung kembalian.
Hapus Riwayat Pesanan - Pengguna dapat menghapus riwayat pemesanan dan mengembalikan status PS menjadi tersedia.
Keluar dari Program - Pengguna dapat keluar dari aplikasi dengan aman.

Bahasa Pemrograman: Java
GUI: Swing (JFrame, JPanel, JButton, JTable, JTextField, JLabel, JOptionPane)

# Struktur Program

1. Kelas Utama: RentalPSApp

Mengelola GUI dan logika utama aplikasi.

Mengatur frame, panel, input field, tombol, dan tabel.

Menyediakan fungsi untuk menampilkan PS, memesan, membayar, dan menghapus riwayat.

2. Kelas PS

Merepresentasikan data PS dengan atribut:

ID

Nama

Harga per jam

Status (tersedia/tersewa)

3. Kelas RentalOrder

Merepresentasikan data pemesanan dengan atribut:

ID pesanan

Nama pemesan

ID PS yang disewa

Durasi penyewaan

Total harga

Fungsi Utama

showPS(): Menampilkan daftar PS yang tersedia dalam format dialog box.

orderPS(): Memproses pemesanan PS dengan validasi input dan menghitung total harga, mengupdate status PS menjadi "tersewa."

payRental(): Memproses pembayaran dan menghitung kembalian.

deleteHistory(): Menghapus riwayat pemesanan dan mengubah status PS kembali ke "tersedia."

exitProgram(): Menutup aplikasi.

# Alur Program

Inisialisasi Data: Memuat daftar PS yang tersedia.

Interaksi Pengguna: Input nama pemesan dan durasi sewa, pilih PS yang ingin disewa, menampilkan data pesanan di tabel.

Pembayaran: Input jumlah pembayaran dan memproses transaksi, penghapusan Riwayat, menghapus pesanan yang dipilih dan memperbarui status PS.

Keluar: Mengakhiri program dengan aman.

Validasi Input: Memastikan semua input yang diperlukan tidak kosong, menangani kesalahan format angka pada input durasi dan jumlah pembayaran, memastikan durasi penyewaan lebih besar dari 0, memeriksa ketersediaan PS sebelum disewa.

# Catatan Penting

Program menggunakan tabel (JTable) untuk menampilkan pesanan aktif.

Sistem validasi ketat untuk memastikan data input benar dan logis.

PS yang sudah disewa tidak dapat dipesan lagi sebelum statusnya dikembalikan.

Program ini hanya menyimpan data selama aplikasi berjalan (tidak ada database).
