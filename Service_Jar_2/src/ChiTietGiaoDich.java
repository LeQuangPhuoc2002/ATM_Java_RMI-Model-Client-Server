
import java.io.*;
import java.sql.Timestamp;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Admin
 */
public class ChiTietGiaoDich implements Serializable {
    public long magiaodich;
    public Timestamp ngaygiaodich;
    public long sotiengiaodich;
    public String ghichu;
    public long sotaikhoan;

    public ChiTietGiaoDich() {
    }

    public ChiTietGiaoDich(long magiaodich, Timestamp ngaygiaodich, long sotiengiaodich, String ghichu, long sotaikhoan) {
        this.magiaodich = magiaodich;
        this.ngaygiaodich = ngaygiaodich;
        this.sotiengiaodich = sotiengiaodich;
        this.ghichu = ghichu;
        this.sotaikhoan = sotaikhoan;
    }
}
