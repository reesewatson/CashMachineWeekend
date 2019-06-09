package rocks.zipcode.atm;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import rocks.zipcode.atm.bank.Bank;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.layout.FlowPane;

/**
 * @author ZipCodeWilmington
 */
public class CashMachineApp extends Application {

    private TextField field = new TextField();
    private CashMachine cashMachine = new CashMachine(new Bank());
    private Scene main, newAcc;

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

    private Scene createNewAccount(Stage stage){
        VBox vbox = new VBox(10);
        vbox.setPrefSize(600, 600);

        Label lblID = new Label("ID:");
        TextField txtFieldID = new TextField ();
        HBox hbID = new HBox();
        hbID.getChildren().addAll(lblID, txtFieldID);
        hbID.setSpacing(10);

        Label lblName = new Label("Name:");
        TextField txtFieldName = new TextField ();
        HBox hbName = new HBox();
        hbName.getChildren().addAll(lblName, txtFieldName);
        hbName.setSpacing(10);

        Label lblEmail = new Label("E-mail Address:");
        TextField txtFieldEmail = new TextField ();
        HBox hbEmail = new HBox();
        hbEmail.getChildren().addAll(lblEmail, txtFieldEmail);
        hbEmail.setSpacing(10);

        Button btnCancel = new Button("Cancel");
        btnCancel.setOnAction(e -> {
            stage.setTitle("Account Management");
            stage.setScene(main);
        });
        Button btnOk = new Button("OK");
        btnOk.setOnAction(e -> {
            stage.setTitle("Account Management");
            stage.setScene(main);
        });
        HBox butts = new HBox(10);
        butts.getChildren().addAll(btnCancel, btnOk);

        vbox.getChildren().addAll(hbID, hbName, hbEmail, butts);
        return new Scene(vbox);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Account Management");
        main = createContent(stage);
        newAcc = createNewAccount(stage);

        stage.setScene(main);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
