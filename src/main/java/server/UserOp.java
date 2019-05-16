package server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface UserOp extends Remote {
    boolean regeister(String name,String account,String password) throws RemoteException;
    User Login(String account,String password) throws RemoteException;
    public boolean editInfo(User user)throws RemoteException;
}
