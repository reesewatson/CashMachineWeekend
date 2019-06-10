package rocks.zipcode.atm;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import rocks.zipcode.atm.bank.Bank;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author ZipCodeWilmington
 */
public class CashMachineApp extends Application {

    private TextField field = new TextField();
    private CashMachine cashMachine = new CashMachine(new Bank());
    private Scene original, newAcc, accManagement, login;

    private Scene createContent(Stage stage) {
        VBox vbox = new VBox(10);
        vbox.setPrefSize(600, 600);

        TextArea areaInfo = new TextArea();

        Button btnSubmit = new Button("Set Account ID");
        btnSubmit.setOnAction(e -> {
            int id = Integer.parseInt(field.getText());
            cashMachine.login(id);

            areaInfo.setText(cashMachine.toString());
        });

        Button btnDeposit = new Button("Deposit");
        btnDeposit.setOnAction(e -> {
            Double amount = Double.parseDouble(field.getText());
            cashMachine.deposit(amount);

            areaInfo.setText(cashMachine.toString());
        });

        Button btnWithdraw = new Button("Withdraw");
        btnWithdraw.setOnAction(e -> {
            Double amount = Double.parseDouble(field.getText());
            cashMachine.withdraw(amount);

            areaInfo.setText(cashMachine.toString());
        });

        Button btnExit = new Button("Exit");
        btnExit.setOnAction(e -> {
            cashMachine.exit();

            areaInfo.setText(cashMachine.toString());
        });

        Button btnNewAcc = new Button("New Account");
        btnNewAcc.setOnAction(e -> {
            stage.setTitle("New Account");
            stage.setScene(newAcc);
        });

        FlowPane flowpane = new FlowPane();

        flowpane.getChildren().add(btnSubmit);
        flowpane.getChildren().add(btnDeposit);
        flowpane.getChildren().add(btnWithdraw);
        flowpane.getChildren().add(btnExit);
        flowpane.getChildren().add(btnNewAcc);
        vbox.getChildren().addAll(field, flowpane, areaInfo);
        return new Scene(vbox);
    }

    private Scene accountManagement(Stage stage){
        BorderPane encase = new BorderPane();
        encase.setPadding(new Insets(20d));
        VBox labels = new VBox(10);
        VBox buttons = new VBox(10);
        HBox fieldsAndLabels = new HBox();

        VBox allBox = new VBox();

        VBox info = new VBox();

        Label accInfo = new Label("Account Information");
        accInfo.setFont(new Font(14));

        TextArea pane = new TextArea();
        pane.setMaxWidth(250);
        pane.setMaxHeight(120);
//        pane.setDisable(true);
        allBox.setAlignment(Pos.CENTER);

        info.getChildren().addAll(accInfo, pane);
        info.setAlignment(Pos.CENTER);
        info.setPadding(new Insets(15,0,0,0));

        Label heading = new Label("ZipCloudBanking");
        encase.setTop(heading);
        heading.setPadding(new Insets(0,0,12,0));
        heading.setFont(new Font(20));
        encase.setAlignment(heading, Pos.CENTER);

        TextField txtBal = new TextField();
        txtBal.setDisable(true);
        TextField txtWithdraw = new TextField();
        TextField txtDeposit= new TextField();

        Button btnCheckBal = new Button("Check Balance");
        btnCheckBal.setOnAction(e -> {
            pane.setText(cashMachine.toString());
        });

        Button btnMakeWithdraw = new Button("Make Withdraw");
        btnMakeWithdraw.setOnAction(e -> {
            Double amount = Double.parseDouble(txtWithdraw.getText());
            cashMachine.withdraw(amount);

            pane.setText(cashMachine.toString());
        });

        Button btnMakeDeposit = new Button("Make Deposit");
        btnMakeDeposit.setOnAction(e -> {
            Double amount = Double.parseDouble(txtDeposit.getText());
            cashMachine.deposit(amount);

            pane.setText(cashMachine.toString());
        });

        Button btnLogout = new Button("Log Out");
        btnLogout.setOnAction(e -> {
            cashMachine.exit();
            pane.setText(cashMachine.toString());
            stage.setScene(login);
            stage.setTitle("ZipCloud Banking");
        });


        encase.setBottom(btnLogout);
        encase.setAlignment(btnLogout, Pos.CENTER);


        buttons.getChildren().addAll(btnCheckBal, btnMakeDeposit, btnMakeWithdraw);
        labels.getChildren().addAll(txtBal, txtDeposit, txtWithdraw);
        fieldsAndLabels.getChildren().addAll(buttons, labels);
        allBox.getChildren().addAll(fieldsAndLabels,info);
        encase.setCenter(allBox);
        return new Scene(encase);
    }

    private Scene createLogin(Stage stage){
        stage.setTitle("ZipCloud Banking");
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("Welcome ZipCoder!");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        Label userName = new Label("Account ID:");
        grid.add(userName, 0, 1);

        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 1);

        Label pw = new Label("PIN:");
        grid.add(pw, 0, 2);

        PasswordField pwBox = new PasswordField();
        grid.add(pwBox, 1, 2);

        Button login = new Button("Login");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(login);
        grid.add(hbBtn, 1, 4);

        final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 6);

        login.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                int pwd;
                try {
                    pwd = Integer.parseInt(pwBox.getText());
                } catch (Exception ex) {
                    pwd = 0;
                }
                int username;
                try {
                    username = Integer.parseInt(userTextField.getText());
                } catch (Exception ex) {
                    username = 0;
                }
                if(pwd != 0 && cashMachine.checkAccounts(username)) {
                    stage.setScene(accManagement);
                    stage.setTitle("Account Management");
                    cashMachine.login(username);
                    stage.setScene(accManagement);
                }

                actiontarget.setFill(Color.PLUM);
                actiontarget.setText("Enter username and password");

            }
        });

        Button newacc = new Button("New Account");
        HBox hbBtn2 = new HBox(10);
        hbBtn2.setAlignment(Pos.BOTTOM_LEFT);
        hbBtn2.getChildren().add(newacc);
        grid.add(hbBtn2, 0, 4);

        final Text actiontarget2 = new Text();
        grid.add(actiontarget2, 1, 6);

        newacc.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                stage.setScene(newAcc);
            }
        });

//        Scene scene = new Scene(grid, 300, 275);
//        stage.setScene(scene);
//        stage.show();
        return new Scene(grid, 300, 275);
    }

    private Scene createNewAccount(Stage stage){
        VBox mainVertical = new VBox(10);
        mainVertical.setPadding(new Insets(15));
        HBox fieldsAndLabels = new HBox();
        VBox labels = new VBox();
        VBox fields = new VBox();

        Label premium = new Label("See your local branch to start a premium account.");

        Label lblID = new Label("ID:");
        TextField txtFieldID = new TextField ();

        Label lblName = new Label("Name:");
        TextField txtFieldName = new TextField ();

        Label lblEmail = new Label("E-mail Address:");
        TextField txtFieldEmail = new TextField ();

        labels.getChildren().addAll(lblID, lblName, lblEmail);
        labels.setAlignment(Pos.CENTER_RIGHT);
        labels.setSpacing(7 + 5);
        fields.getChildren().addAll(txtFieldID, txtFieldName, txtFieldEmail);
        fields.setSpacing(5);

        fieldsAndLabels.getChildren().addAll(labels, fields);
        fieldsAndLabels.setSpacing(20);

        Button btnCancel = new Button("Cancel");
        btnCancel.setOnAction(e -> {
            stage.setTitle("ZipCloud Banking");
            stage.setScene(login);
        });

        Button btnOk = new Button("OK");
        btnOk.setOnAction(e -> {
            int id;
            try {
                id = Integer.parseInt(txtFieldID.getText());
            } catch (Exception ex) {
                id = 0;
            }
            String name = txtFieldName.getText();
            String email = txtFieldEmail.getText();
            if(id != 0 && !name.equals("") && !email.equals("")) {
                cashMachine.newAccount(id, name, email);
                stage.setTitle("Account Management");
                cashMachine.login(id);
                stage.setScene(accManagement);
            }
        });

        HBox butts = new HBox(10);
        butts.getChildren().addAll(btnCancel, btnOk);
        butts.setAlignment(Pos.CENTER);

        fieldsAndLabels.getChildren().addAll(butts);
        mainVertical.getChildren().addAll(fieldsAndLabels, butts, premium);
        return new Scene(mainVertical);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Account Management");
        stage.setResizable(false);

        login = createLogin(stage);
        original = createContent(stage);
        newAcc = createNewAccount(stage);
        accManagement = accountManagement(stage);

        stage.setScene(login);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
