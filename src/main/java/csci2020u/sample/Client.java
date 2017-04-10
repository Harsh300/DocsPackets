package main.java.csci2020u.sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.applet.Applet;
import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by rohil on 05/04/17.
 */
public class Client extends Application {
    Stage window;
    Scene mainScene, registerScene, loginScene, newDocScene, editorScene, afterLoginScene,LoadDocScene;
    public static int port;
    public static String hostname;
    private String name = "";
    private String password = "";
    private String fileName="";
    @FXML
    private TextArea editor;
    private Socket ClientSocket = null;

    @Override
    public void start(Stage primaryStage) throws Exception {

        window = primaryStage;
        GridPane mainLayout = new GridPane();
        mainLayout.setPadding(new Insets(10, 10, 10, 10));
        mainLayout.setVgap(8);
        mainLayout.setHgap(10);

        Label label1 = new Label("              Welcome to DOCS online editor");
        GridPane.setConstraints(label1, 0, 0);
        Label label2 = new Label("                      Log in in and begin editing");
        GridPane.setConstraints(label2, 0, 3);

        Label label3 = new Label("                      Sign up and log in next time");
        GridPane.setConstraints(label3, 0, 4);

        Button registerButton = new Button("Register");
        GridPane.setConstraints(registerButton, 0, 4);
        registerButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                window.setScene(registerScene);
                window.setTitle("Registration");
            }
        });
        Button loginButton = new Button("Login");
        GridPane.setConstraints(loginButton, 0, 3);
        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                window.setScene(loginScene);
                window.setTitle("Log in");
            }
        });
        mainLayout.getChildren().addAll(label1, label2, label3, registerButton, loginButton);
        mainScene = new Scene(mainLayout, 350, 200);
        window.setTitle("Main Menu");

        /**************************REGISTRATION SCREEN************************/

        GridPane registerLayout = new GridPane();
        registerLayout.setPadding(new Insets(30, 10, 10, 40));
        registerLayout.setVgap(8);
        registerLayout.setHgap(10);

        Label nameLabel = new Label("Username:");
        GridPane.setConstraints(nameLabel, 0, 0);
        TextField nameInput = new TextField();
        nameInput.setPromptText("Username");
        GridPane.setConstraints(nameInput, 1, 0);


        Label passwordLabel = new Label("Password:");
        GridPane.setConstraints(passwordLabel, 0, 1);
        PasswordField passwordInput = new PasswordField();
        passwordInput.setPromptText("Password");
        GridPane.setConstraints(passwordInput, 1, 1);
        password = passwordInput.getText();

        Label passwordLabel2 = new Label("Re-type:");
        GridPane.setConstraints(passwordLabel2, 0, 2);
        PasswordField passwordInput2 = new PasswordField();
        passwordInput2.setPromptText("Password");
        GridPane.setConstraints(passwordInput2, 1, 2);

        Button signupButton = new Button("Sign up");
        GridPane.setConstraints(signupButton, 1, 3);
        signupButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(passwordInput2.getText().equals(passwordInput.getText())) {
                    System.out.println("Passwords match");
                    password=passwordInput.getText();
                }
                else{
                    System.out.println("Password do not match");
                }
            }
        });
        Button returnButton = new Button("Return to last page");
        GridPane.setConstraints(returnButton, 1, 4);
        returnButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                window.setScene(mainScene);
                window.setTitle("Main Menu");
            }
        });
        registerLayout.getChildren().addAll(nameInput, nameLabel, passwordInput, passwordLabel,passwordLabel2,passwordInput2, signupButton, returnButton);
        registerScene = new Scene(registerLayout, 350, 200);

        /**************************LOG IN SCREEN************************/
        GridPane loginLayout = new GridPane();
        loginLayout.setPadding(new Insets(30, 10, 10, 40));
        loginLayout.setVgap(8);
        loginLayout.setHgap(10);


        Label name1Label = new Label("Username:");
        GridPane.setConstraints(name1Label, 0, 0);
        TextField name1Input = new TextField();
        name1Input.setPromptText("Username");
        GridPane.setConstraints(name1Input, 1, 0);

        Label password1Label = new Label("Password:");
        GridPane.setConstraints(password1Label, 0, 1);
        PasswordField password1Input = new PasswordField();
        password1Input.setPromptText("password");
        GridPane.setConstraints(password1Input, 1, 1);

        Button signinButton = new Button("Sign in");
        GridPane.setConstraints(signinButton, 1, 2);
        signinButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                name = name1Input.getText();
                password = password1Input.getText();
                try {
                    login("Login " + name + " " + password);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println(name + " " + password);

            }
        });
        Button returnLogin = new Button("Return to last page");
        GridPane.setConstraints(returnLogin, 1, 3);
        returnLogin.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                window.setScene(mainScene);
                window.setTitle("Main Menu");
            }
        });
        loginLayout.getChildren().addAll(name1Input, name1Label, password1Input, password1Label, signinButton, returnLogin);
        loginScene = new Scene(loginLayout, 350, 200);


        /**************************AFTER LOG IN SCREEN************************/
        GridPane afterLogin = new GridPane();
        afterLogin.setPadding(new Insets(10, 10, 10, 10));
        afterLogin.setVgap(8);
        afterLogin.setHgap(10);


        Button newDocButton = new Button("New File");
        GridPane.setConstraints(newDocButton, 1, 2);
        newDocButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                window.setScene(newDocScene);
                window.setTitle("File Selection");
               /* name = newFileInput.getText();
                String path = "/home/harshan/Desktop/Server/" + name;
                File file = new File(path);
                String content = readFileContents(file);
                editor.setText(content);
                System.out.println(name); */

            }
        });

        Button LoadFileButton = new Button("Load File");
        GridPane.setConstraints(LoadFileButton, 2, 2);
        LoadFileButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                window.setScene(LoadDocScene);

                /*
                name = newFileInput.getText();
                String path = "/home/harshan/Desktop/Server/" + name;
                File file = new File(path);
                String content = readFileContents(file);
                editor.setText(content);
                System.out.println(name);
                */
            }
        });

        Button returnLoadFile = new Button("Return to last page");
        GridPane.setConstraints(returnLoadFile, 3, 2);
        returnLoadFile.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                window.setScene(loginScene);
                window.setTitle("Log in");

            }
        });

        afterLogin.getChildren().addAll(returnLoadFile,newDocButton,LoadFileButton);
        afterLoginScene = new Scene(afterLogin, 350, 100);

        /**************************NEW FILE SCREEN************************/
        GridPane newFileLayout = new GridPane();
        newFileLayout.setPadding(new Insets(10, 10, 10, 10));
        newFileLayout.setVgap(8);
        newFileLayout.setHgap(10);

        Label fileNameLabel = new Label("Enter document name: ");
        GridPane.setConstraints(fileNameLabel, 0, 1);
        TextField fileNameField = new TextField();
        GridPane.setConstraints(fileNameField, 1, 1);

        Button fileNameButton = new Button("Next");
        GridPane.setConstraints(fileNameButton, 2, 1);
        fileNameButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                ;
                fileName=fileNameField.getText();
                try {
                    newfile(fileName);
                    window.setTitle(fileName);
                    window.setScene(editorScene);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        newFileLayout.getChildren().addAll(fileNameLabel,fileNameField,fileNameButton);
        newDocScene = new Scene(newFileLayout, 425, 100);

        /**************************OPEN FILE SCREEN************************/
        GridPane openFileLayout = new GridPane();
        openFileLayout.setPadding(new Insets(10, 10, 10, 10));
        openFileLayout.setVgap(8);
        openFileLayout.setHgap(10);

        Label openNameLabel = new Label("Enter document name: ");
        GridPane.setConstraints(openNameLabel, 0, 1);
        TextField openNameField = new TextField();
        GridPane.setConstraints(openNameField, 1, 1);

        Button openNameButton = new Button("Next");
        GridPane.setConstraints(openNameButton, 2, 1);
        openNameButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                fileName=openNameField.getText();
                System.out.println("IS IT???");
                try {
                    newfile(fileName);
                    window.setTitle(fileName);
                    window.setScene(editorScene);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        openFileLayout.getChildren().addAll(openNameLabel,openNameField,openNameButton);
        LoadDocScene = new Scene(openFileLayout, 425, 100);

        /****************************EDITOR SCREEN*****************************/
        GridPane editorLayout = new GridPane();
        editorLayout.setPadding(new Insets(20, 10, 10, 10));
        editorLayout.setVgap(8);
        editorLayout.setHgap(10);

        TextArea textArea = new TextArea();
        textArea.setPrefRowCount(20);
        editorLayout.getChildren().addAll(textArea);

        editorScene = new Scene(editorLayout,500,400);

        window.setScene(mainScene);
        window.show();
    }

    /**********************************************************************************************************************/
    public void Client(String ip, int gate) {
        hostname = ip;
        port = gate;
    }

    public void newfile(String msg) throws IOException {
        System.out.println("Creating new file");

        Socket socket3 = new Socket(hostname,8082);
        PrintWriter out3 = new PrintWriter(socket3.getOutputStream());
        BufferedReader in3 = new BufferedReader(new InputStreamReader(socket3.getInputStream()));
        String command = "new " + msg + " .txt";

        out3.println(command);
        out3.flush();
        out3.close();
        in3.close();
        socket3.close();

    }
    public void login(String msg) throws IOException {
        System.out.println("sending command to server "+hostname + port);
        try {
            Socket socket = new Socket(hostname, 8080);
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out.println(msg);
            out.flush();
            out.close();
            in.close();
            socket.close();

            String result = LoginResponse();
            //result = "True";
            System.out.println("result: "+result);
            if (result.equals("True")) {
                window.setScene(afterLoginScene);
                window.setTitle("File Selection");
            }
            if (result.equals("False")) {

                Label incorrectInfo = new Label("Incorrect password or username. Please try again.");
                GridPane.setConstraints(incorrectInfo, 0, 4);
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setHeaderText("Error");
                alert.setContentText("Incorrect username or password");

                alert.showAndWait();

            }

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String LoginResponse()
    {
        String message = "";
        System.out.println("Trying to read from the server "+hostname+"8081");
        try {
            Socket socket2 = new Socket(hostname,8081);
            PrintWriter out2 = new PrintWriter(socket2.getOutputStream());
            //System.out.println("Connected to the socket");
            BufferedReader in2 = new BufferedReader(new InputStreamReader(socket2.getInputStream()));
            //System.out.println("Connected to the socket2");
            //System.out.println(hostname + " " + port);
            message = in2.readLine();
            System.out.println(message);
            out2.flush();
            out2.close();
            in2.close();
            socket2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Server sent back: "+message);
        return message;
    }

    private String readFileContents(File file) {
        StringBuffer buffer = new StringBuffer();

        try {
            String line;
            BufferedReader input = new BufferedReader(new FileReader(file));
            while ((line = input.readLine()) != null) {
                buffer.append(line + "\n");
            }
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }

    public static void main(String[] args) throws IOException {
        Client Client = new Client();
        Client.Client("localhost",8080);
        launch(args);

    }
}
