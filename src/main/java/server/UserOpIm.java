package server;

import util.DBUtil;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserOpIm extends UnicastRemoteObject implements UserOp, Serializable {
    public UserOpIm()throws RemoteException {

    }

    public boolean regeister(String name, String account, String password) throws RemoteException{
        Connection connection= DBUtil.openDB();
        String sql="select * from User where useraccount=?";
        String sql2="insert into User(userName,userAccount,userPassword,userInfo) values(?,?,?,?)";
        try {
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
            PreparedStatement preparedStatement1=connection.prepareStatement(sql2);
            preparedStatement.setString(1,account);
            ResultSet resultSet=preparedStatement.executeQuery();
            if (resultSet.next()){
                return false;
            }else {
                preparedStatement1.setString(1,name);
                preparedStatement1.setString(2,account);
                preparedStatement1.setString(3,password);
                preparedStatement1.setString(4,"请完善信息！");
                int n=preparedStatement1.executeUpdate();
                if (n==1){
                    return true;
                }else {
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            DBUtil.closeDB(connection);
        }
        return false;
    }

    public User Login(String account, String password) throws RemoteException{
        Connection connection=DBUtil.openDB();
        String sql="select * from User where useraccount=?";
        try {
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,account);
            ResultSet resultSet=preparedStatement.executeQuery();
            User user=new User();
            user.setUserAccount(account);
            user.setPassword(password);
            if(resultSet.next()){
                user.setUserName(resultSet.getString(1));
                user.setUserInfo(resultSet.getString(4));
                String userPassword=resultSet.getString(3);
                if (userPassword.equals(password)){
                    user.setLoginSuccess(true);

                }else {
                    user.setLoginSuccess(false);
                    user.setMassage("密码错误！");
                }

            }else {
                user.setLoginSuccess(false);
                user.setMassage("账号不存在！");
            }
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtil.closeDB(connection);
        }
        return null;
    }

    public boolean editInfo(User user) throws RemoteException {
        Connection connection=DBUtil.openDB();
        String sql="update user set userInfo=? where userAccount=?";
        try {
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,user.getUserInfo());
            preparedStatement.setString(2,user.getUserAccount());
            int n=preparedStatement.executeUpdate();
            if (n>=1){
                return true;
            }else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtil.closeDB(connection);
        }
        return false;
    }


}
