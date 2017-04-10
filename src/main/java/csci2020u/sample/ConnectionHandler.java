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

        }

        public String Login() throws FileNotFoundException {
            String path = "/home/rohil/Desktop/Server/LoginInfo";
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


                if (Username.equals(checkUsername) && Password.equals(checkPassword))
                {
                    //PrintWriter out = new PrintWriter(ClientSocket.getOutputStream());
                    return "True";
                    //out.println("True");

                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return "false";
        }

        public String FileExist(String path)
        {
            File file = new File(path);
            if(file.exists())
            {
                return "True";
            }
            else
            {
                return "False";
            }
        }
        public void handleRequest3() throws IOException {
            System.out.println("About to accept filename from client");
            PrintWriter output3 = new PrintWriter(ClientSocket.getOutputStream());
            BufferedReader br3 = new BufferedReader(new InputStreamReader(ClientSocket.getInputStream()));
            String mainRequestLine = "";
            mainRequestLine = br3.readLine();
            System.out.println(mainRequestLine.toString());
            output3.close();
            ClientSocket.close();
            br3.close();
        }

        public void handleRequest2() throws IOException {
            PrintWriter out2 = new PrintWriter(ClientSocket.getOutputStream());
            BufferedReader in2 = new BufferedReader(new InputStreamReader(ClientSocket.getInputStream()));
            out2.println("True");
            System.out.println("This Login is valid!");
            out2.close();
            ClientSocket.close();
            in2.close();
        }

        public void handleRequest4() throws IOException {
            PrintWriter out4 = new PrintWriter(ClientSocket.getOutputStream());
            BufferedReader in4 = new BufferedReader(new InputStreamReader(ClientSocket.getInputStream()));
            out4.println("True");
            System.out.println("Sent to client in response: "+ "False");
            out4.close();
            ClientSocket.close();
            in4.close();
        }

        public void handleRequest5() throws IOException {
            PrintWriter out4 = new PrintWriter(ClientSocket.getOutputStream());
            BufferedReader in4 = new BufferedReader(new InputStreamReader(ClientSocket.getInputStream()));
            out4.println("False");
            System.out.println("Sent to client in response: "+ "False");
            out4.close();
            ClientSocket.close();
            in4.close();
        }
        public String handleRequest()
        {   String loginStatus="False";
            try
            {
                PrintWriter output = new PrintWriter(ClientSocket.getOutputStream());
                BufferedReader br = new BufferedReader(new InputStreamReader(ClientSocket.getInputStream()));
                String mainRequestLine = "";
                mainRequestLine = br.readLine();
                System.out.println("Command sent to server by client: " + mainRequestLine.toString());

                output.close();
                ClientSocket.close();
                br.close();

                StringTokenizer requestTokenizer = new StringTokenizer(mainRequestLine);
                command = requestTokenizer.nextToken();
                Username = requestTokenizer.nextToken();
                Password = requestTokenizer.nextToken();

                if (command.equals("Login"))
                {
                    loginStatus = Login();
                }

                if (command.equals("Register"))
                {
                    String information = null;
                    String checkUsername = null;
                    String checkPassword = null;
                    String path = "/home/rohil/Desktop/Server/LoginInfo";
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
                if(command.equals("new")){
                    String fileName = Username;
                    String extention=Password;
                    String path="/home/rohil/Desktop/Server/SavedFiles/"+fileName+extention;
                    File file = new File(path);
                    file.createNewFile();
                    return "False";
                }
                if(command.equals("Load"))
                {

                    String Filename = Username;
                    String extention = Password;
                    String dir = "/home/rohil/Desktop/Server/SavedFiles/"+Filename+extention;
                    File file = new File(dir);
                    String FileStatus = FileExist(dir);
                    System.out.println("File does not exist");
                    return FileStatus;

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return loginStatus;
        }

    @Override
    public void run() {

    }
}
