
import java.rmi.*;
import java.rmi.registry.LocateRegistry;

public class MyServer {

    public static void main(String[] args) {
        try {
            TinhToan tt = new TinhToan();
            LocateRegistry.createRegistry(1099);
            Naming.bind("rmi://localhost/TinhToan", tt);
            System.out.print("Dang cho Client yeu cau: ");
        } catch (Exception tt) {
            System.out.print(tt);
        }
    }
}