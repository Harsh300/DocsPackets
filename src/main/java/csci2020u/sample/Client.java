package main.java.csci2020u.sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.applet.Applet;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by rohil on 05/04/17.
 */
public class Client extends Application {
    Stage window;
    Scene mainScene, registerScene, loginScene, newDocScene, editorScene, afterLoginScene,LoadDocScene;
    private int port;
    private String hostname = "";
    private String name = "";
    private String password = "";
    @FXML
    private TextArea editor;

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        Label label1 = new Label("Welcome to the program");
        Label label2 = new Label("click the button to choose what you want! :)");
        Button registerButton = new Button("Register");
        registerButton.setOnAction(e -> window.setScene(registerScene));

        Button loginButton = new Button("Login");
        loginButton.setOnAction(e -> window.setScene(loginScene));

        VBox mainLayout = new VBox(20);
        mainLayout.getChildren().addAll(label1, label2, registerButton, loginButton);
        mainScene = new Scene(mainLayout, 300, 200);

        GridPane registerLayout = new GridPane();
        registerLayout.setPadding(new Insets(10, 10, 10, 10));
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
        passwordInput.setPromptText("password");
        GridPane.setConstraints(passwordInput, 1, 1);
        password = passwordInput.getText();


        Button signupButton = new Button("Sign up");
        GridPane.setConstraints(signupButton, 1, 3);
        Button returnButton = new Button("Return to last page");
        GridPane.setConstraints(returnButton, 2, 3);
        returnButton.setOnAction(e -> window.setScene(mainScene));
        registerLayout.getChildren().addAll(nameInput, nameLabel, passwordInput, passwordLabel, signupButton, returnButton);
        registerScene = new Scene(registerLayout, 400, 200);

        GridPane loginLayout = new GridPane();
        loginLayout.setPadding(new Insets(10, 10, 10, 10));
        loginLayout.setVgap(8);
        loginLayout.setHgap(10);

        GridPane newDocLayout = new GridPane();
        newDocLayout.setPadding(new Insets(10, 10, 10, 10));
        newDocLayout.setVgap(8);
        newDocLayout.setHgap(10);

        GridPane LoadDocLayout = new GridPane();
        LoadDocLayout.setPadding(new Insets(10, 10, 10, 10));
        LoadDocLayout.setVgap(8);
        LoadDocLayout.setHgap(10);

        GridPane afterLoginSceneLayout = new GridPane();
        afterLoginSceneLayout.setPadding(new Insets(10, 10, 10, 10));
        afterLoginSceneLayout.setVgap(8);
        afterLoginSceneLayout.setHgap(10);

        /*GridPane editorLayout = new GridPane();
        editorLayout.setPadding(new Insets(10, 10, 10, 10));
        editorLayout.setVgap(8);
        editorLayout.setHgap(10);*/

        /**************************SIGN UP SCREEN************************/
        Label name1Label = new Label("Username:");
        GridPane.setConstraints(name1Label, 0, 0);
        TextField name1Input = new TextField();
        name1Input.setPromptText("Username");
        GridPane.setConstraints(name1Input, 1, 0);

        Label newFileLabel = new Label("Enter File Name:");
        GridPane.setConstraints(newFileLabel, 0, 0);
        TextField newFileInput = new TextField();
        newFileInput.setPromptText("Enter File Name");
        GridPane.setConstraints(newFileInput, 1, 0);

        Label LoadFileLabel = new Label("Enter File Name:");
        GridPane.setConstraints(newFileLabel, 0, 0);
        TextField LoadFileInput = new TextField();
        LoadFileInput.setPromptText("Enter File Name");
        GridPane.setConstraints(LoadFileInput, 1, 0);


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
                login(name + " " + password);
                System.out.println(name + " " + password);

            }
        });

        Button newDocButton = new Button("New File");
        GridPane.setConstraints(newDocButton, 1, 2);
        newDocButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                window.setScene(newDocScene);
               /* name = newFileInput.getText();
                String path = "/home/harshan/Desktop/Server/" + name;
                File file = new File(path);
                String content = readFileContents(file);
                editor.setText(content);
                System.out.println(name); */

            }
        });

        Button makeNewDocButton = new Button("Enter");
        GridPane.setConstraints(newDocButton, 1, 2);
        newDocButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                name = newFileInput.getText();
               /* String path = "/home/harshan/Desktop/Server/" + name;
                File file = new File(path);
                String content = readFileContents(file);
                editor.setText(content);
                System.out.println(name);*/
                String command = "New " + name;

                try {
                    Socket clientSocket = new Socket(hostname,port);
                    PrintWriter printWriter = new PrintWriter(clientSocket.getOutputStream());
                    BufferedReader bw = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    printWriter.println(command);
                    String result = bw.readLine();
                    if(result.equals("True"))
                    {
                        //open a blank file in the editor
                    }
                    if(result.equals("False"))
                    {
                        Label FileFound = new Label("File with that name exists. Choose another name.");
                        GridPane.setConstraints(FileFound, 0, 4);
                        //print error message that reads "File with that name exists"
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        Button LoadDocButton = new Button("Enter");
        GridPane.setConstraints(newDocButton, 1, 2);
        newDocButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //Look for file in the server directory
                //if it exists use the code below to open
                // if is does not exist print error message


                name = LoadFileInput.getText();
                /*String path = "/home/harshan/Desktop/Server/" + name;
                File file = new File(path);
                String content = readFileContents(file);
                editor.setText(content);
                System.out.println(name);*/
                String command = "Load " + name;
                try {
                    Socket clientSocket = new Socket(hostname, port);
                    PrintWriter pw = new PrintWriter(clientSocket.getOutputStream());
                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    pw.println(command);
                    String result = in.readLine();
                    if(result.equals("True"))
                    {
                        //open editor with the file content
                    }
                    if(result.equals("False"))
                    {
                        Label FileNotFound = new Label("File with that name does not exist. Try again and check spelling.");
                        GridPane.setConstraints(FileNotFound, 0, 4);
                        // print error message that reads file not found
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                /*name = LoadFileInput.getText();
                String path = "/home/harshan/Desktop/Server/" + name;
                File file = new File(path);
                String content = readFileContents(file);
                editor.setText(content);
                System.out.println(name);*/

            }
        });

        Button return1Button = new Button("Return to last page");
        GridPane.setConstraints(return1Button, 2, 2);
        return1Button.setOnAction(e -> window.setScene(mainScene));

        loginLayout.getChildren().addAll(name1Input, name1Label, password1Input, password1Label, signinButton, return1Button);
        loginScene = new Scene(loginLayout, 400, 200);


        newDocLayout.getChildren().addAll(newFileInput, newFileLabel, makeNewDocButton);
        newDocScene = new Scene(newDocLayout, 400, 200);

        LoadDocLayout.getChildren().addAll(LoadFileInput, LoadFileLabel, LoadDocButton);
        LoadDocScene = new Scene(LoadDocLayout, 400, 200);

        Button LoadFileButton = new Button("Load File");
        GridPane.setConstraints(LoadFileButton, 1, 2);
        newDocButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                name = newFileInput.getText();
                String path = "/home/harshan/Desktop/Server/" + name;
                File file = new File(path);
                String content = readFileContents(file);
                editor.setText(content);
                System.out.println(name);

            }
        });

        afterLoginSceneLayout.getChildren().addAll(newDocButton,LoadFileButton,LoadFileInput,LoadFileLabel);
        afterLoginScene = new Scene(afterLoginSceneLayout,400,200);

       /* editorLayout.getChildren().addAll(editor);
        editorScene = new Scene(editorLayout, 400, 200); */

        window.setScene(mainScene);
        window.setTitle("Project");
        window.show();
    }


    public void Client(String ip, int port) {
        this.hostname = ip;
        this.port = port;
    }

    public void login(String msg) {
        try {
            Socket socket = new Socket(this.hostname, this.port);
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out.println(msg);
            String result = in.readLine();
            if (result.equals("True")) {
                Button newDocButton = new Button("New document");
                newDocButton.setOnAction(e -> window.setScene(newDocScene));
            }
            if (result.equals("False")) {
                Label incorrectInfo = new Label("Incorrect password or username. Please try again.");
                GridPane.setConstraints(incorrectInfo, 0, 4);
                /*Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setHeaderText("Error");
                alert.setContentText("Incorrect username or password");

                alert.showAndWait();*/
            }
            out.flush();
            out.close();
            in.close();
            //socket.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        Client.Client("10.160.60.30",8080);
        launch(args);

    }
}
