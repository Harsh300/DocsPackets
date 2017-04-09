package main.java.csci2020u.sample;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;

/**
 * Created by rohil on 05/04/17.
 */
public class Server {
    public int port=1201;
    private ServerSocket ServerSocket = null;


    public Server(int port) throws IOException {
        this.port = port;
        ServerSocket = new ServerSocket(port);

    }


    public void handleRequests() throws IOException {
        Socket ClientSocket = ServerSocket.accept();
        ConnectionHandler CH = new ConnectionHandler("192.168.0.109",port,ClientSocket);

        Thread handlerThread = new Thread(CH);

        handlerThread.start();

        try
        {
            handlerThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws IOException {
        java.lang.System.out.println("Server");
        Server server = new Server(1201);
        server.handleRequests();
    }

}
