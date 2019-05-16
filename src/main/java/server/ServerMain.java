package server;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class ServerMain {
    public static void main(String[] args) {
        UserOp userOp=null;
        try {
            userOp=new UserOpIm();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        try {
            LocateRegistry.createRegistry(9895);
            try {
                Naming.bind("rmi://127.0.0.1:9895/UserOp",userOp);
               // Naming.bind("rmi://127.0.0.1:9895/ServerFuncation",serverFuncation);
            } catch (AlreadyBoundException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
