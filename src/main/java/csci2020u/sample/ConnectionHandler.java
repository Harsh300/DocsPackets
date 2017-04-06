package main.java.csci2020u.sample;

import java.awt.print.Printable;
import java.io.*;
import java.net.Socket;
import java.nio.Buffer;
import java.util.StringTokenizer;

/**
 * Created by rohil on 05/04/17.
 */
public class ConnectionHandler implements Runnable{
        private Socket socket = null;
        private String hostname = null;
        private int port;
        private Socket ClientSocket = null;

        public ConnectionHandler(String hostname, int port, Socket ClientSocket)
        {
            this.hostname = hostname;
            this.port = port;
            handleRequest();

        }

        public void handleRequest()
        {
            try
            {
                PrintWriter output = new PrintWriter(ClientSocket.getOutputStream());
                BufferedReader br = new BufferedReader(new InputStreamReader(ClientSocket.getInputStream()));
                String mainRequestLine = "";
                mainRequestLine = br.readLine();
                System.out.print(mainRequestLine.toString());
                br.close();
                StringTokenizer requestTokenizer = new StringTokenizer(mainRequestLine);

                String command = null;
                String Username = null;
                String Password = null;

                command = requestTokenizer.nextToken();
                Username = requestTokenizer.nextToken();
                Password = requestTokenizer.nextToken();

                if (command.equals("Login"))
                {
                    String path = "/home/harshan/Desktop/Server/LoginInfo";
                    File checkLoginFile = new File(path);
                    BufferedReader LoginBR = new BufferedReader(new FileReader(path));
                    String checkUsername = null;
                    String checkPassword = null;
                    String information = null;

                    try
                    {
                        information = LoginBR.readLine();
                        StringTokenizer usernameAndPasswordTokenizer = new StringTokenizer(information);
                        checkUsername = usernameAndPasswordTokenizer.nextToken();
                        checkPassword = usernameAndPasswordTokenizer.nextToken();
                        if (Username == checkUsername || Password == checkPassword)
                        {
                            PrintWriter out = new PrintWriter(ClientSocket.getOutputStream());
                            out.println("True");
                        }
                        else
                        {
                            PrintWriter out = new PrintWriter(ClientSocket.getOutputStream());
                            out.println("False");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if (command.equals("Register"))
                {
                    String information = null;
                    String checkUsername = null;
                    String path = "/home/harshan/Desktop/Server/LoginInfo";
                    File checkLoginFile = new File(path);
                    BufferedReader RegisterBR = new BufferedReader(new FileReader(path));
                    information = RegisterBR.readLine();
                    StringTokenizer usernameToken = new StringTokenizer(information);
                    checkUsername = usernameToken.nextToken();
                    if(Username == checkUsername)
                    {
                        PrintWriter out = new PrintWriter(ClientSocket.getOutputStream());
                        out.println("False");
                    }
                    else
                    {
                        PrintWriter out = new PrintWriter(ClientSocket.getOutputStream());
                        out.println("True");
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    @Override
    public void run() {

    }
}
