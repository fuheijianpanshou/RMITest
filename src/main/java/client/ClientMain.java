package client;

import server.User;
import server.UserOp;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class ClientMain {
    public static void main(String[] args) {
        try {
            UserOp userOp =(UserOp) Naming.lookup("rmi://127.0.0.1:9895/UserOp");
            new exc(userOp).run();
            //System.out.println(serverFuncation.hellowWorld());
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
    static class exc implements Runnable{
        UserOp userOp;
        int flag=0;
        int loflag=0;
        public exc(UserOp userOp){
            this.userOp=userOp;
        }

        public void run() {
            User user=new User();
            Scanner scanner=new Scanner(System.in);
            int n=0;
            System.out.println("注册输入1");
            n=scanner.nextInt();
            if (n!=1){
                flag=1;
            }
            while (true){
                if(flag==0){
                    System.out.println("用户注册");
                    System.out.println("输入姓名：");
                    String userName = scanner.next();
                    System.out.println("请输入账号：");
                    String userAccount=scanner.next();
                    System.out.println("请输入密码：");
                    String password=scanner.next();
                    if(userAccount.length()>0&&userName.length()>0&&password.length()>0){
                        try {
                            boolean resuccess=userOp.regeister(userName,userAccount,password);
                            if(resuccess){
                                System.out.println("注册成功！");
                                flag=1;
                                continue;
                            }else{
                                System.out.println("注册失败！");
                            }
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                }else {
                    if (loflag==0){
                        System.out.println("请输入账号：");
                        String account=scanner.next();
                        System.out.println("请输入密码：");
                        String password=scanner.next();
                        try {
                            user=userOp.Login(account,password);
                            if (user.isLoginSuccess()){
                                System.out.println("登录成功！");
                                loflag=1;
                            }else {
                                System.err.println(user.getMassage());
                            }
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }else {
                        System.out.println(user.getUserInfo());
                        System.out.println("请填信息：");
                        String info=scanner.next();
                        user.setUserInfo(info);
                        try {
                            boolean is=userOp.editInfo(user);
                            if (is){
                                System.out.println("修改成功");
                                break;
                            }else {
                                System.out.println("修改失败");
                            }
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }


                }
            }


        }
    }
}
