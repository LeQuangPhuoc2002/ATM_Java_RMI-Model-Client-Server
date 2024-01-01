
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TinhToan extends UnicastRemoteObject implements ITinhToan {

    public TinhToan() throws RemoteException {
        CoSo.MoKetNoi();
    }

    public String timKiemNguoiNhan(long stknhan) throws RemoteException {
        try {
            String sql = "SELECT * FROM TaiKhoan WHERE SoTaiKhoan = ?";
            PreparedStatement cmd = CoSo.cn.prepareStatement(sql);
            cmd.setLong(1, stknhan);
            ResultSet rs = cmd.executeQuery();
            return rs.next() ? rs.getString("HoTen") : null;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.print("Lỗi ở Tìm Kiếm Người Nhận: " + e.getMessage());
            return null;
        }
    }

    public int SetStatus(long stk, int status) throws RemoteException {
        try {
            String sql = "UPDATE TaiKhoan SET Status = ? WHERE SoTaiKhoan = ?";
            PreparedStatement cmd = CoSo.cn.prepareStatement(sql);
            cmd.setInt(1, status);
            cmd.setLong(2, stk);
            int rs = cmd.executeUpdate();
            return rs; // 1 hoặc 0
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    public boolean DangNhap(long stk, long mk) throws RemoteException {
        try {
            String sql = "SELECT * FROM TaiKhoan WHERE SoTaiKhoan=? AND MatKhau=?";
            PreparedStatement cmd = CoSo.cn.prepareStatement(sql);
            cmd.setLong(1, stk);
            cmd.setLong(2, mk);
            ResultSet rs = cmd.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public int GetStatus(long stk) throws RemoteException {
        try {
            String sql = "SELECT * FROM TaiKhoan WHERE SoTaiKhoan = ?";
            PreparedStatement cmd = CoSo.cn.prepareStatement(sql);
            cmd.setLong(1, stk);
            ResultSet rs = cmd.executeQuery();
            if (rs.next()) {
                int status = rs.getInt("Status");
                return status;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -3;
    }

    public int KTDangNhap(long stk, long mk) throws RemoteException {
        try {
            if (GetStatus(stk) != 1) { // nếu đang sử dụng
                if (timKiemNguoiNhan(stk) != null) {
                    String sql = "select * from TaiKhoan where SoTaiKhoan=? and MatKhau=?";
                    PreparedStatement cmd = CoSo.cn.prepareStatement(sql);
                    cmd.setLong(1, stk);
                    cmd.setLong(2, mk);
                    ResultSet rs = cmd.executeQuery();
                    if (rs.next()) {
                        int i = rs.getInt("Status");
                        switch (i) {
                            case 0:
                                int update0 = SetStatus(stk, 1);
                                return (update0 == 1) ? 0 : -2; // thành công
                            case 1:
                                return 1; // đang được sử dụng
                            case -1:
                                return -1; // đã bị khóa
                        }
                    } else {
                        return -2; // đăng nhập sai mã PIN
                    }
                } else {
                    return -4;
                }
            } else {
                return 1; // đang được sử dụng
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("lỗi csdl rồi");
        return -3; // lỗi csdl
    }

    public int DangXuat(long stk) throws RemoteException {
        try {
            String sql = "UPDATE TaiKhoan SET Status = 0 WHERE SoTaiKhoan = ?";
            PreparedStatement cmd = CoSo.cn.prepareStatement(sql);
            cmd.setLong(1, stk);
            return cmd.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int KTRutTien(long stk, Date ngaykt, long sotienrut) throws RemoteException {
        try {
            String sql = "SELECT SUM(SoTienGiaoDich)+? AS TongTienTrongNgay FROM ChiTietGiaoDich WHERE CAST(NgayGiaoDich AS DATE) = ? AND GhiChu = ? AND SoTaiKhoan = ?";
            PreparedStatement cmd = CoSo.cn.prepareStatement(sql);
            cmd.setLong(1, sotienrut);
            cmd.setDate(2, ngaykt);
            cmd.setString(3, "Rút Tiền");
            cmd.setLong(4, stk);
            ResultSet rs = cmd.executeQuery();
            if (rs.next()) {
                long sotienruttrongngay = rs.getLong("TongTienTrongNgay");
                if (sotienruttrongngay <= 20000000) {
                    return 1;
                } else {
                    return 0;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int RutTien(long stk, long sotienrut) throws RemoteException {
        try {
            // Lấy ngày hiện tại
            LocalDate currentDate = LocalDate.now();

            // Chuyển đổi LocalDate thành java.sql.Date
            Date ngaykt = Date.valueOf(currentDate);
            int ktRutTien = KTRutTien(stk, ngaykt, sotienrut);
            switch (ktRutTien) {
                case 1:
                    if (sotienrut >= 50000 && sotienrut <= 10000000 && sotienrut % 50000 == 0) {
                        String sql = "SELECT * FROM TaiKhoan WHERE SoTaiKhoan = ? AND SoTien >= (? + 50000)";
                        PreparedStatement cmd = CoSo.cn.prepareStatement(sql);
                        cmd.setLong(1, stk);
                        cmd.setLong(2, sotienrut);
                        ResultSet rs = cmd.executeQuery();
                        if (rs.next()) { // Số dư lớn hơn số rút
                            Long sotien = rs.getLong("SoTien");
                            // Tiến hành rút
                            String sql1 = "Update TaiKhoan SET SoTien = ? WHERE SoTaiKhoan = ?";
                            PreparedStatement cmd1 = CoSo.cn.prepareStatement(sql1);
                            long soTienConLaiSauKhiRut = sotien - sotienrut;
                            cmd1.setLong(1, soTienConLaiSauKhiRut);
                            cmd1.setLong(2, stk);
                            int chay = cmd1.executeUpdate();
                            if (chay > 0) {
                                System.out.println("Update thành công stk rút (trừ tiền)");
                                // insert vào bảng chi tiết giao dịch
                                LocalDateTime currentDateTime = LocalDateTime.now();
                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
                                String TimeNow = currentDateTime.format(formatter);
                                String sql2 = "INSERT INTO ChiTietGiaoDich (NgayGiaoDich, SoTienGiaoDich, GhiChu, SoTaiKhoan) VALUES (?, ?, ?, ?)";
                                PreparedStatement cmd2 = CoSo.cn.prepareStatement(sql2);
                                cmd2.setString(1, TimeNow);
                                cmd2.setLong(2, sotienrut);
                                cmd2.setString(3, "Rút Tiền");
                                cmd2.setLong(4, stk);
                                int chay2 = cmd2.executeUpdate();
                                if (chay2 > 0) {
                                    System.out.println("Đã ghi lại lịch sử rút tiền");
                                    return 1;
                                } else {
                                    System.out.println("Không ghi được lịch sử giao dịch");
                                    return -2;
                                }
                            } else {
                                System.out.println("Có lỗi khi update Tài khoản rút tiền");
                                return -1;
                            }
                        } else {
                            return -3; // Số tài khoản bé hơn số rút
                        }
                    } else {
                        return -4;
                    }
                case 0:
                    return -5;
                default:
                    return -1;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.print("Lỗi ở Rút Tiền: " + e.getMessage());
            return -1; // Có lỗi vui lòng thử lại
        }
    }

    public int KTChuyenKhoan(long stk, Date ngaykt, long sotienchuyen) throws RemoteException {
        try {
            String sql = "SELECT SUM(SoTienGiaoDich)+? AS TongTienChuyenKhoan FROM ChiTietGiaoDich WHERE CAST(NgayGiaoDich AS DATE) = ? AND SoTaiKhoan = ? AND GhiChu like ?";
            PreparedStatement cmd = CoSo.cn.prepareStatement(sql);
            cmd.setLong(1, sotienchuyen);
            cmd.setDate(2, ngaykt);
            cmd.setLong(3, stk);
            cmd.setString(4, "%" + "Chuyển tiền" + "%");
            ResultSet rs1 = cmd.executeQuery();
            if (rs1.next()) {
                long sotienchuyentrongngay = rs1.getLong("TongTienChuyenKhoan");
                System.out.println("Số tiền đã chuyển trong ngày: " + sotienchuyentrongngay);
                if (sotienchuyentrongngay <= 500000000) {
                    System.out.println("Bé hơn");
                    return 1;
                } else {
                    System.out.println("Lớn hơn");
                    return 0; // lớn hơn 500000000/ngày
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1; // Có lỗi xảy ra
    }

    public int ChuyenKhoan(long stkchuyen, long stknhan, long sotienchuyen) throws RemoteException {
        try {
            if (GetStatus(stknhan) != -1) {
                LocalDate currentDate = LocalDate.now();
                Date ngaykt = Date.valueOf(currentDate);
                switch (KTChuyenKhoan(stkchuyen, ngaykt, sotienchuyen)) {
                    case 1:
                        String tenNguoiNhan = timKiemNguoiNhan(stknhan);
                        if (tenNguoiNhan != null) {
                            // Kiểm tra số tiền trong tài khoản người chuyển có lớn hơn số tiền muốn chuyển không
                            String sql = "SELECT * FROM TaiKhoan WHERE SoTaiKhoan = ?";
                            PreparedStatement cmd = CoSo.cn.prepareStatement(sql);
                            cmd.setLong(1, stkchuyen);
                            ResultSet rs = cmd.executeQuery();
                            if (!rs.next()) {
                                return -4;
                            }
                            BigDecimal soDuTaiKhoanChuyen = rs.getBigDecimal("SoTien");
                            BigDecimal soTienChuyenBigDecimal = BigDecimal.valueOf(sotienchuyen);
                            BigDecimal soDuTaiKhoanChuyenNew = soDuTaiKhoanChuyen.subtract(soTienChuyenBigDecimal);
                            if (soDuTaiKhoanChuyenNew.compareTo(BigDecimal.valueOf(50000)) >= 0) {
                                // 1. Trừ tiền người chuyển
                                String sql1 = "UPDATE TaiKhoan set SoTien = ? WHERE SoTaiKhoan = ?";
                                PreparedStatement cmd1 = CoSo.cn.prepareStatement(sql1);
                                cmd1.setBigDecimal(1, soDuTaiKhoanChuyenNew);
                                cmd1.setLong(2, stkchuyen);
                                if (cmd1.executeUpdate() > 0) { // nếu update thành công tài khoản người chuyển
                                    // Ghi Chú = Chuyen:stk_nhan
                                    LocalDateTime currentDateTime = LocalDateTime.now();
                                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
                                    String TimeNow = currentDateTime.format(formatter);
                                    String sql3 = "INSERT INTO ChiTietGiaoDich (NgayGiaoDich, SoTienGiaoDich, GhiChu, SoTaiKhoan) VALUES(?, ?, ?, ?)";
                                    PreparedStatement cmd3 = CoSo.cn.prepareStatement(sql3);
                                    cmd3.setString(1, TimeNow);
                                    cmd3.setLong(2, sotienchuyen);
                                    String ghichu = "Chuyển tiền đến: " + String.valueOf(stknhan); // Chuyển:stknhan
                                    cmd3.setString(3, ghichu);
                                    cmd3.setLong(4, stkchuyen);
                                    if (cmd3.executeUpdate() > 0) { // nếu insert thành công lịch sử giao dịch của người chuyển
                                        // 2. Update tiền người nhận
                                        String sql2 = "UPDATE TaiKhoan SET SoTien = SoTien+? WHERE SoTaiKhoan = ?";
                                        PreparedStatement cmd2 = CoSo.cn.prepareStatement(sql2);
                                        cmd2.setLong(1, sotienchuyen);
                                        cmd2.setLong(2, stknhan);
                                        if (cmd2.executeUpdate() > 0) { // nếu update thành công tài khoản người nhận
                                            String sql4 = "INSERT INTO ChiTietGiaoDich (NgayGiaoDich, SoTienGiaoDich, GhiChu, SoTaiKhoan) VALUES(?, ?, ?, ?)";
                                            PreparedStatement cmd4 = CoSo.cn.prepareStatement(sql4);
                                            cmd4.setString(1, TimeNow);
                                            cmd4.setLong(2, sotienchuyen);
                                            String ghichu1 = "Nhận tiền từ: " + String.valueOf(stkchuyen);
                                            cmd4.setString(3, ghichu1);
                                            cmd4.setLong(4, stknhan);
                                            if (cmd4.executeUpdate() > 0) {
                                                return 1;
                                            } else {
                                                return -2;
                                            }
                                        } else {
                                            return -1; // không update được vào taikhoan của người nhận tiền
                                        }
                                    } else {
                                        return -2; // không insert được vào chitietgiaodich của người chuyển tiền
                                    }
                                } else {
                                    return -1; // nếu update thất bại tài khoản người chuyển
                                }
                            } else {
                                return -3; // Số tài khoản người chuyển không đủ
                            }
                        } else {
                            return -4; // Không tìm thấy người nhận
                        }
                    case 0:
                        return -5; // lớn hơn 500000000/ngày
                    case -1:
                        System.out.println("Lỗi kt chuyển khoản: ");
                        return 0;
                }
            } else {
                return -6; // Tài khoản người nhận bị khóa
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.print("Lỗi ở Chuyển Khoản: " + e.getMessage());
        }
        return 0; // Có lỗi vui lòng thử lại
    }

    public BigDecimal XemSoDu(long stk) throws RemoteException {
        try {
            String sql = "SELECT SoTien FROM TaiKhoan WHERE SoTaiKhoan=?";
            PreparedStatement cmd = CoSo.cn.prepareStatement(sql);
            cmd.setLong(1, stk);
            ResultSet rs = cmd.executeQuery();
            if (rs.next()) {
                BigDecimal SoDu = rs.getBigDecimal("SoTien");
                return SoDu;
            } else {
                return BigDecimal.valueOf(-1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.print("Lỗi ở Xem Số Dư: " + e.getMessage());
            return BigDecimal.valueOf(-1);
        }
    }

    public int DoiMatKhau(long stk, long mkcu, long mkmoi) throws RemoteException {
        try {
            String sql = "UPDATE TaiKhoan SET MatKhau = ? WHERE SoTaiKhoan = ? AND MatKhau = ?";
            PreparedStatement cmd = CoSo.cn.prepareStatement(sql);
            cmd.setLong(1, mkmoi);
            cmd.setLong(2, stk);
            cmd.setLong(3, mkcu);
            int kt = cmd.executeUpdate();
            if (kt > 0) {
                return kt;
            } else {
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.print("Lỗi ở DoiMatKhau: " + e.getMessage());
            return 0;
        }
    }

    public ArrayList xemLichSuGiaoDich(long stk) throws RemoteException {
        ArrayList ds = new ArrayList();
        try {
            String sql = "SELECT * FROM ChiTietGiaoDich WHERE SoTaiKhoan = ?";
            PreparedStatement cmd = CoSo.cn.prepareStatement(sql);
            cmd.setLong(1, stk);
            ResultSet rs = cmd.executeQuery();
            while (rs.next()) {
                long magiaodich = rs.getLong("MaGiaoDich");
                Timestamp ngaygiaodich = rs.getTimestamp("NgayGiaoDich");
                long sotiengiaodich = rs.getLong("SoTienGiaoDich");
                String ghichu = rs.getString("GhiChu");
                ds.add(new ChiTietGiaoDich(magiaodich, ngaygiaodich, sotiengiaodich, ghichu, stk));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ds;
    }

    public int NapTien(long stk, long sotiennap) throws RemoteException {
        try {
            if (sotiennap % 50000 == 0) {
                String sql = "UPDATE TaiKhoan SET SoTien = SoTien+? WHERE SoTaiKhoan = ?";
                PreparedStatement cmd = CoSo.cn.prepareStatement(sql);
                cmd.setLong(1, sotiennap);
                cmd.setLong(2, stk);
                int kt = cmd.executeUpdate();
                if (kt > 0) { // Nạp tiền thành công vào tài khoản
                    LocalDateTime currentDateTime = LocalDateTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
                    String TimeNow = currentDateTime.format(formatter);
                    String sql1 = "INSERT INTO ChiTietGiaoDich (NgayGiaoDich, SoTienGiaoDich, GhiChu, SoTaiKhoan) VALUES(?, ?, ?, ?)";
                    PreparedStatement cmd1 = CoSo.cn.prepareStatement(sql1);
                    cmd1.setString(1, TimeNow);
                    cmd1.setLong(2, sotiennap);
                    String ghichu = "Nạp Tiền";
                    cmd1.setString(3, ghichu);
                    cmd1.setLong(4, stk);
                    if (cmd1.executeUpdate() > 0) {
                        return 1; // Nạp tiền và ghi lịch sử thành công
                    } else {
                        return -1; // Ghi lịch sử thất bại
                    }
                } else {
                    return -2; // Nạp tiền thất bại, có lỗi cơ sở dữ liệu
                }
            } else {
                return -4; // Số tiền nạp phải là bội số của 50000
            }
        } catch (Exception e) {
            e.printStackTrace();
            return -3; // Có lỗi kết nối csdl khi thực hiện giao dịch
        }
    }

    public ArrayList InBienLai(long stk) throws RemoteException {
        ArrayList bienlai = new ArrayList();
        try {
            String sql = "SELECT tk.SoTaiKhoan, tk.HoTen, tk.SoDienThoai, ct.MaGiaoDich, ct.NgayGiaoDich, ct.SoTienGiaoDich, ct.GhiChu FROM TaiKhoan tk LEFT JOIN ChiTietGiaoDich ct ON tk.SoTaiKhoan = ct.SoTaiKhoan WHERE ct.MaGiaoDich = (select MAX(MaGiaoDich) from ChiTietGiaoDich where SoTaiKhoan = ?) GROUP BY tk.SoTaiKhoan, tk.HoTen, tk.SoDienThoai, ct.MaGiaoDich, ct.NgayGiaoDich, ct.SoTienGiaoDich, ct.GhiChu";
            PreparedStatement cmd = CoSo.cn.prepareStatement(sql);
            cmd.setLong(1, stk);
            ResultSet rs = cmd.executeQuery();
            while (rs.next()) {
                long magiaodich = rs.getLong("MaGiaoDich");
                Timestamp ngaygiaodich = rs.getTimestamp("NgayGiaoDich");
                long sotaikhoan = rs.getLong("SoTaiKhoan");
                String hoten = rs.getString("HoTen");
                long sodienthoai = rs.getLong("SoDienThoai");
                long sotiengiaodich = rs.getLong("SoTienGiaoDich");
                String ghichu = rs.getString("GhiChu");
                bienlai.add(new BienLaiGiaoDich(magiaodich, ngaygiaodich, sotaikhoan, hoten, sodienthoai, sotiengiaodich, ghichu));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bienlai;
    }
}
