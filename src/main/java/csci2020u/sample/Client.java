package main.java.csci2020u.sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.applet.Applet;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by rohil on 05/04/17.
 */
public class Client extends Application{
    Stage window;
    Scene mainScene,registerScene, loginScene;
    private int port;
    private String hostname = "";
    private String name = "";
    private String password = "";

    @Override
    public void start(Stage primaryStage) throws Exception{
        window = primaryStage;
        Label label1 = new Label("Welcome to the program");
        Label label2 = new Label("click the button to choose what you want! :)");
        Button registerButton = new Button("Register");
        registerButton.setOnAction(e -> window.setScene(registerScene));

        Button loginButton = new Button("Login");
        loginButton.setOnAction(e -> window.setScene(loginScene));

        VBox mainLayout = new VBox(20);
        mainLayout.getChildren().addAll(label1,label2,registerButton,loginButton);
        mainScene = new Scene(mainLayout,300,200);

        GridPane registerLayout = new GridPane();
        registerLayout.setPadding(new Insets(10,10,10,10));
        registerLayout.setVgap(8);
        registerLayout.setHgap(10);

        Label nameLabel = new Label("Username:");
        GridPane.setConstraints(nameLabel,0,0);
        TextField nameInput = new TextField();
        nameInput.setPromptText("Username");
        GridPane.setConstraints(nameInput,1,0);


        Label passwordLabel = new Label("Password:");
        GridPane.setConstraints(passwordLabel,0,1);
        PasswordField passwordInput = new PasswordField();
        passwordInput.setPromptText("password");
        GridPane.setConstraints(passwordInput,1,1);
        password = passwordInput.getText();


        Button signupButton = new Button("Sign up");
        GridPane.setConstraints(signupButton,1,3);
        Button returnButton = new Button("Return to last page");
        GridPane.setConstraints(returnButton,2,3);
        returnButton.setOnAction(e -> window.setScene(mainScene));
        registerLayout.getChildren().addAll(nameInput,nameLabel,passwordInput,passwordLabel,signupButton,returnButton);
        registerScene = new Scene(registerLayout,400,200);

        GridPane loginLayout = new GridPane();
        loginLayout.setPadding(new Insets(10,10,10,10));
        loginLayout.setVgap(8);
        loginLayout.setHgap(10);

        /**************************SIGN UP SCREEN************************/
        Label name1Label = new Label("Username:");
        GridPane.setConstraints(name1Label,0,0);
        TextField name1Input = new TextField();
        name1Input.setPromptText("Username");
        GridPane.setConstraints(name1Input,1,0);


        Label password1Label = new Label("Password:");
        GridPane.setConstraints(password1Label,0,1);
        PasswordField password1Input = new PasswordField();
        password1Input.setPromptText("password");
        GridPane.setConstraints(password1Input,1,1);

        Button signinButton = new Button("Sign in");
        GridPane.setConstraints(signinButton,1,2);
        signinButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                name = name1Input.getText();
                password= password1Input.getText();
                login(name+" "+password);
                System.out.println(name+" "+password);

            }
        });

        Button return1Button = new Button("Return to last page");
        GridPane.setConstraints(return1Button,2,2);
        return1Button.setOnAction(e -> window.setScene(mainScene));

        loginLayout.getChildren().addAll(name1Input,name1Label,password1Input,password1Label,signinButton,return1Button);
        loginScene = new Scene(loginLayout,400,200);

        window.setScene(mainScene);
        window.setTitle("Project");
        window.show();
    }


    public void Client(String ip, int port)
    {
        this.hostname = ip;
        this.port = port;
    }

    public void login(String msg){
        try{
            Socket socket = new Socket(this.hostname,this.port);
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out.println(msg);
            out.flush();
            out.close();
            in.close();
            //socket.close();
        }catch (UnknownHostException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        Client Client = new Client();
        Client.Client("10.160.60.30",8080);
        launch(args);

    }
}
