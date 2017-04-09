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
        private String command = "";
        private String Username = "";
        private String Password = "";

        public ConnectionHandler(String hostname, int port, Socket ClientSocket)
        {
            this.hostname = hostname;
            this.port = port;
            this.ClientSocket=ClientSocket;
            handleRequest();

        }

        public void Login() throws FileNotFoundException {
            String path = "/home/harshan/Desktop/Server/LoginInfo";
            File checkLoginFile = new File(path);
            BufferedReader LoginBR = new BufferedReader(new FileReader(path));
            String checkUsername = "";
            String checkPassword = "";
            String information = "";

            try
            {
                information = LoginBR.readLine();
                StringTokenizer usernameAndPasswordTokenizer = new StringTokenizer(information);
                checkUsername = usernameAndPasswordTokenizer.nextToken();
                checkPassword = usernameAndPasswordTokenizer.nextToken();
                System.out.println(checkUsername);
                System.out.println(checkPassword);

                if (Username.equals(checkUsername) || Password.equals(checkPassword))
                {
                    PrintWriter out = new PrintWriter(ClientSocket.getOutputStream());
                    System.out.println("True");
                    out.println("True");

                }
                else
                {
                    PrintWriter out1 = new PrintWriter(ClientSocket.getOutputStream());
                    out1.println("False");
                    System.out.println("False");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void handleRequest()
        {
            try
            {
                PrintWriter output = new PrintWriter(ClientSocket.getOutputStream());
                BufferedReader br = new BufferedReader(new InputStreamReader(ClientSocket.getInputStream()));
                String mainRequestLine = "";
                mainRequestLine = br.readLine();
                System.out.println(mainRequestLine.toString());

                output.close();
                ClientSocket.close();
                br.close();

                StringTokenizer requestTokenizer = new StringTokenizer(mainRequestLine);
                command = requestTokenizer.nextToken();
                System.out.println(command);
                Username = requestTokenizer.nextToken();
                System.out.println(Username);
                Password = requestTokenizer.nextToken();
                System.out.println(Password);

                if (command.equals("Login"))
                {
                    Login();
                }

                if (command.equals("Register"))
                {
                    String information = null;
                    String checkUsername = null;
                    String checkPassword = null;
                    String path = "/home/harshan/Desktop/Server/LoginInfo";
                    File checkLoginFile = new File(path);
                    BufferedReader RegisterBR = new BufferedReader(new FileReader(path));
                    information = RegisterBR.readLine();
                    StringTokenizer usernameToken = new StringTokenizer(information);
                    checkUsername = usernameToken.nextToken();
                    checkPassword = usernameToken.nextToken();
                    if(Username.equals(checkUsername))
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
