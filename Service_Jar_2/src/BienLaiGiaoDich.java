
import java.io.Serializable;
import java.sql.Timestamp;

public class BienLaiGiaoDich implements Serializable {

    public long magiaodich;
    public Timestamp ngaygiaodich;
    public long sotaikhoan;
    public String hoten;
    public long sodienthoai;
    public long sotiengiaodich;
    public String ghichu;

    public BienLaiGiaoDich() {
    }

    public BienLaiGiaoDich(long magiaodich, Timestamp ngaygiaodich, long sotaikhoan, String hoten, long sodienthoai, long sotiengiaodich, String ghichu) {
        this.magiaodich = magiaodich;
        this.ngaygiaodich = ngaygiaodich;
        this.sotaikhoan = sotaikhoan;
        this.hoten = hoten;
        this.sodienthoai = sodienthoai;
        this.sotiengiaodich = sotiengiaodich;
        this.ghichu = ghichu;
    }
}
