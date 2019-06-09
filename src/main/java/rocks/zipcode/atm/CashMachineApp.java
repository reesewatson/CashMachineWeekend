package rocks.zipcode.atm;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import rocks.zipcode.atm.bank.Bank;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.layout.FlowPane;

import java.awt.*;

/**
 * @author ZipCodeWilmington
 */
public class CashMachineApp extends Application {

    private TextField field = new TextField();
    private CashMachine cashMachine = new CashMachine(new Bank());
    private Scene main, newAcc, test;

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
            int amount = Integer.parseInt(field.getText());
            cashMachine.deposit(amount);

            areaInfo.setText(cashMachine.toString());
        });

        Button btnWithdraw = new Button("Withdraw");
        btnWithdraw.setOnAction(e -> {
            int amount = Integer.parseInt(field.getText());
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
        pane.setDisable(true);
        allBox.setAlignment(Pos.CENTER);

        info.getChildren().addAll(accInfo, pane);
        info.setAlignment(Pos.CENTER);
        info.setPadding(new Insets(15,0,0,0));

        Label heading = new Label("ZipCloudBanking");
        encase.setTop(heading);
        heading.setPadding(new Insets(0,0,12,0));
        heading.setFont(new Font(20));
        encase.setAlignment(heading, Pos.CENTER);

        Button btnCheckBal = new Button("Check Balance");
        btnCheckBal.setOnAction(e -> {
        });

        Button btnMakeWithdraw = new Button("Make Withdraw");
        btnMakeWithdraw.setOnAction(e -> {
        });

        Button btnMakeDeposit = new Button("Make Deposit");
        btnMakeDeposit.setOnAction(e -> {
        });

        Button btnLogout = new Button("Log Out");
        btnMakeDeposit.setOnAction(e -> {
        });

        TextField txtBal = new TextField();
        txtBal.setDisable(true);
        TextField txtWithdraw = new TextField();
        TextField txtDeposit= new TextField();

        encase.setBottom(btnLogout);
        encase.setAlignment(btnLogout, Pos.CENTER);


        buttons.getChildren().addAll(btnCheckBal, btnMakeDeposit, btnMakeWithdraw);
        labels.getChildren().addAll(txtBal, txtDeposit, txtWithdraw);
        fieldsAndLabels.getChildren().addAll(buttons, labels);
        allBox.getChildren().addAll(fieldsAndLabels,info);
        encase.setCenter(allBox);
        return new Scene(encase);
    }

    private Scene createNewAccount(Stage stage){
        VBox mainVertical = new VBox(10);
        mainVertical.setPadding(new Insets(15));
        HBox fieldsAndLabels = new HBox();
        VBox labels = new VBox();
        VBox fields = new VBox();

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
            stage.setTitle("Account Management");
            stage.setScene(main);
        });
        Button btnOk = new Button("OK");
        btnOk.setOnAction(e -> {
            int id;
            try {
                id = Integer.parseInt(txtFieldID.getText());
            } catch (Exception ex) {
                id = 0;
            }
            String name = txtFieldID.getText();
            String email = txtFieldID.getText();
            if(id != 0 && name!=null && !name.equals("")) {
                cashMachine.newAccount(id, name, email);
                stage.setTitle("Account Management");
                stage.setScene(main);
            }
        });

        HBox butts = new HBox(10);
        butts.getChildren().addAll(btnCancel, btnOk);
        butts.setAlignment(Pos.CENTER);

        fieldsAndLabels.getChildren().addAll(butts);
        mainVertical.getChildren().addAll(fieldsAndLabels, butts);
        return new Scene(mainVertical);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Account Management");
        stage.setResizable(false);
        main = createContent(stage);
        newAcc = createNewAccount(stage);
        test = accountManagement(stage);

        stage.setScene(test);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
