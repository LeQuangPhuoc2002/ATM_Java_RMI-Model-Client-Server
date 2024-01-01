import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.rmi.*;
import java.sql.Date;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Admin
 */
public interface ITinhToan extends Remote {

    public int KTDangNhap(long stk, long mk) throws RemoteException;

    public int DangXuat(long stk) throws RemoteException;

    public int SetStatus(long stk, int status) throws RemoteException;
    
    public boolean DangNhap(long stk, long mk) throws RemoteException;

    public int GetStatus(long stk) throws RemoteException;

    public int KTRutTien(long stk, Date ngaykt, long sotienrut) throws RemoteException;

    public int RutTien(long stk, long sotienrut) throws RemoteException;

    public String timKiemNguoiNhan(long stknhan) throws RemoteException;
    
    public int KTChuyenKhoan(long stk, Date ngaykt, long sotienchuyen) throws RemoteException;

    public int ChuyenKhoan(long stkchuyen, long stknhan, long sotienchuyen) throws RemoteException;

    public BigDecimal XemSoDu(long stk) throws RemoteException;

    public int DoiMatKhau(long stk, long mkcu, long mkmoi) throws RemoteException;

    public ArrayList xemLichSuGiaoDich(long stk) throws RemoteException;

    public int NapTien(long stk, long sotiennap) throws RemoteException;
    
    public ArrayList InBienLai(long stk) throws RemoteException;
}