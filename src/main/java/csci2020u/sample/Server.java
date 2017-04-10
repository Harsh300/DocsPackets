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



    public Server(int port) throws IOException {
        this.port = port;
        ServerSocket = new ServerSocket(port);
        ServerSocket2 = new ServerSocket(8081);
        ServerSocket3 = new ServerSocket(8082);

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
    public void handleRequests3() throws IOException {
        Socket ClientSocket3 = ServerSocket3.accept();
        ConnectionHandler CH = new ConnectionHandler("localhost",8082,ClientSocket3);
        CH.handleRequest();
        Thread handlerThread3 = new Thread(CH);
        CH.handleRequest3();
        handlerThread3.start();
        try {
            handlerThread3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
    public static void main(String[] args) throws IOException {
        java.lang.System.out.println("Server");
        Server server = new Server(8080);
        if(server.handleRequests().equals("True")) {
            server.handRequests2();
            server.handleRequests3();

        }
        else{
            System.out.println("incorrect pass");
        }
    }



}
