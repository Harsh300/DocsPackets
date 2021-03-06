package main.java.csci2020u.sample;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;


/**
 * Created by rohil on 05/04/17.
 */
public class Server {
    public int port=333333;
    private ServerSocket ServerSocket = null;
    private ServerSocket ServerSocket2 = null;
    private ServerSocket ServerSocket3 = null;
    private ServerSocket ServerSocket4 = null;



    public Server(int port) throws IOException {
        this.port = port;
        ServerSocket = new ServerSocket(port);
        ServerSocket2 = new ServerSocket(8081);
        ServerSocket3 = new ServerSocket(8082);
        ServerSocket4 = new ServerSocket(8083);

    }


    public String handleRequests() throws IOException {
        Socket ClientSocket = ServerSocket.accept();
        ConnectionHandler CH = new ConnectionHandler("localhost",port,ClientSocket);
        String status = CH.handleRequest();
        Thread handlerThread = new Thread(CH);

        handlerThread.start();

        try
        {
            handlerThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return status;
    }
    public void handRequests2() throws IOException {
        Socket ClientSocket2 = ServerSocket2.accept();
        ConnectionHandler CH = new ConnectionHandler("localhost",8081,ClientSocket2);

        Thread handlerThread2 = new Thread(CH);
        CH.handleRequest2();
        handlerThread2.start();

        try {
            handlerThread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public String handleRequests3() throws IOException {
        Socket ClientSocket3 = ServerSocket3.accept();
        ConnectionHandler CH = new ConnectionHandler("localhost",8082,ClientSocket3);
        String status = CH.handleRequest();
        Thread handlerThread3 = new Thread(CH);
        handlerThread3.start();
        try {
            handlerThread3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return status;
    }

    public void handleRequests4() throws IOException {
        Socket ClientSocket4 = ServerSocket4.accept();
        ConnectionHandler CH = new ConnectionHandler("localhost",8083,ClientSocket4);

        Thread handlerThread4 = new Thread(CH);
        CH.handleRequest2();
        handlerThread4.start();

        try {
            handlerThread4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void handleRequests5() throws IOException {
        Socket ClientSocket4 = ServerSocket4.accept();
        ConnectionHandler CH = new ConnectionHandler("localhost",8083,ClientSocket4);

        Thread handlerThread5 = new Thread(CH);
        CH.handleRequest5();
        handlerThread5.start();

        try {
            handlerThread5.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws IOException {
        java.lang.System.out.println("Server");
        Server server = new Server(1201);
        if(server.handleRequests().equals("True")) {
            server.handRequests2();
            String hehe = server.handleRequests3();
            System.out.println(hehe);
            if(server.handleRequests3().equals("True"))
            {
                server.handleRequests4();
            }
            else
            {
                server.handleRequests5();
            }

        }
        else{
            System.out.println("incorrect pass");
        }
    }



}
