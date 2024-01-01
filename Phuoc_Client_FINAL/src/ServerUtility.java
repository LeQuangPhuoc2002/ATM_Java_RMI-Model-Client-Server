
import java.rmi.Naming;

public class ServerUtility {

    public static ITinhToan getServerInstance() throws Exception {
        return (ITinhToan) Naming.lookup("rmi://" + SharedVariables.ip + "/TinhToan");
    }
}
