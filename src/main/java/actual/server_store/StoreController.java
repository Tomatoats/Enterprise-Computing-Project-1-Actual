package actual.server_store;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import static actual.server_store.StoreApplication.inventory;



public class StoreController {
    ArrayList<String> cart = new ArrayList<>();
    ArrayList<String> transactionCart = new ArrayList<>();
    double subTotal;
    double priceHolder;
    @FXML
    private Button ButtonAdd;

    @FXML
    private Button ButtonCheckOut;

    @FXML
    private Button ButtonEmpty;

    @FXML
    private Button ButtonExit;

    @FXML
    private Button ButtonFindItem;

    @FXML
    private Button ButtonView;

    @FXML
    private TextField FieldDetails;

    @FXML
    private TextField FieldItem;

    @FXML
    private TextField FieldQuantity;

    @FXML
    private TextField FieldSubtotal;

    @FXML
    private Label LabelDetails;

    @FXML
    private Label LabelID;

    @FXML
    private Label LabelQuantity;

    @FXML
    private Label LabelSubtotal;

    @FXML
    void AddItemPressed(javafx.event.ActionEvent actionEvent) {
        String id = FieldItem.getText();
        addItem();
        StoreApplication.getScene("Confirmed","Confirmed");
        String temp = "  Item # ";
        temp = temp.concat(id);
        temp = temp.concat(" Accepted - Added into Cart");
        LabelItemConfirmed.setText(temp);


    }

    @FXML
    void Exit(javafx.event.ActionEvent actionEvent) {
            Stage stage = (Stage) ButtonExit.getScene().getWindow();
            stage.close();
    }
    @FXML
    void FindItemPressed(javafx.event.ActionEvent actionEvent) {
            findItem();
            //FieldDetails.setText("You've done it!");
    }
    @FXML
    void ViewItemPressed(javafx.event.ActionEvent actionEvent) {
        showItems();
    }


    @FXML
    void CheckOutPressed(javafx.event.ActionEvent actionEvent) {
        checkOut();
    }

    @FXML
    void EmptyButtonPressed(javafx.event.ActionEvent actionEvent) {
        StoreApplication.getScene("are you sure", "Are you Sure?");
        //Empty();

    }
    @FXML
    private Button ErrorOutOfStockButtonOK;

    @FXML
    void OutOfStockPressed(javafx.event.ActionEvent actionEvent) {
        Stage stage = (Stage) ErrorOutOfStockButtonOK.getScene().getWindow();
        stage.close();

    }
    @FXML
    private Button ErrorNoItemButtonOK;

    @FXML
    private Label ErrorNoItemLabelNotFound;

    @FXML
    void NoItemPressed(javafx.event.ActionEvent actionEvent) {
        Stage stage = (Stage) ErrorNoItemButtonOK.getScene().getWindow();
        stage.close();

    }
    @FXML
    private Label LabelItemConfirmed;

    @FXML
    private Button buttonOkay;

    @FXML
    void buttonOkayPressed(javafx.event.ActionEvent actionEvent) {
        //confirmed
        Stage stage = (Stage) buttonOkay.getScene().getWindow();
        stage.close();

    }

    @FXML
    private Button ViewCartButton;

    @FXML
    private Label ViewLabel;

    @FXML
    void ViewCartPressed(javafx.event.ActionEvent actionEvent) {
        Stage stage = (Stage) ViewCartButton.getScene().getWindow();
        stage.close();

    }
    @FXML
    private Button CheckOutButton;

    @FXML
    public Label CheckOutLabel;

    @FXML
    void CheckOutWindowPressed(javafx.event.ActionEvent actionEvent) {
        Stage stage = (Stage) CheckOutButton.getScene().getWindow();
        stage.close();

    }

    @FXML
    private Button AreYouSureNo;

    @FXML
    private Button AreYouSureYes;

    @FXML
    void NoPressed(javafx.event.ActionEvent actionEvent) {
        Stage stage = (Stage) AreYouSureNo.getScene().getWindow();
        stage.close();

    }

    @FXML
    void YesPressed(javafx.event.ActionEvent actionEvent) {
        Stage stage = (Stage) AreYouSureYes.getScene().getWindow();
        stage.close();
        StoreApplication.getScene("cartEmptied", "Cart Emptied !");

        Empty();

    }
    @FXML
    private Button cartEmptiedButton;

    @FXML
    void cartEmptiedPressed(javafx.event.ActionEvent actionEvent) {
        Stage stage = (Stage) cartEmptiedButton.getScene().getWindow();
        stage.close();

    }
    @FXML
    private Label NotEnoughLabel;

    @FXML
    private Button notEnoughButton;

    @FXML
    void notEnoughPressed(javafx.event.ActionEvent actionEvent) {
        Stage stage = (Stage) notEnoughButton.getScene().getWindow();
        stage.close();

    }



    void findItem(){
        int number = IDRegex();
     if (number != -1){

         String id = (FieldItem.getText());
         int quantity = Integer.parseInt(FieldQuantity.getText());
         //System.out.println(id + quantity);
         double discount = discountCalc(quantity);
         double price = (quantity * inventory.get(number).getPrice()) * discount;
         String temp = String.valueOf(BigDecimal.valueOf(price).setScale(2,RoundingMode.HALF_UP));
         double priceActual = Double.parseDouble(temp);
         String showDiscount = showDisc(discount);
         String toDisplay =  inventory.get(number).getId() + " " + inventory.get(number).getName() + " " +"$" + inventory.get(number).getPrice() + " " + quantity + " " + showDiscount + "% " + priceActual;
         FieldDetails.setText(toDisplay);
         priceHolder=priceActual;
         ButtonAdd.setDisable(false);
         ButtonFindItem.setDisable(true);
         //FieldSubtotal.setText(String.valueOf(subTotal));

     }
     else {
         //TODO: Error, not a proper ID
         FieldItem.clear();
         FieldDetails.clear();
         FieldQuantity.clear();
         StoreApplication.getScene("notEnough", "Sorry - Not Enough!");
     }


    }


    //public void FindItemPressed(javafx.event.ActionEvent actionEvent) {
    //}

    int IDRegex(){
        String temp;
        boolean flag = false;
        boolean finalFlag = false;
        int index = -1;
        try {
            temp = FieldItem.getText();
            //System.out.println(temp);
            for (Item items : inventory) {

                //System.out.println(inventory.indexOf(items) + " " +  inventory.get(inventory.indexOf(items)).getId());
                //System.out.println(inventory.get(inventory.indexOf(items)).getId() + "= " + temp + inventory.get(inventory.indexOf(items)).getId().equals(temp));
                if (inventory.get(inventory.indexOf(items)).getId().equals(temp)) {

                    index = inventory.indexOf(items);
                    //System.out.println(index);
                    if (inventory.get(index).getHave_Any()){
                        finalFlag = quantityRegex(inventory.get(index).getQuantity());
                        if (finalFlag = true){
                            return index;
                        }
                        else {
                            index = -1;
                            return index;
                        }
                    }
                    else {
                        String id = FieldItem.getText();
                        FieldItem.clear();
                        FieldQuantity.clear();
                        FieldDetails.clear();
                        StoreApplication.getScene("out of stock","Error - Out Of Stock");
                        String temp1 = "Item ID #";
                        temp1 = temp1.concat(id);
                        temp1 = temp1.concat(" not in file");
                        System.out.println(temp1);
                        ErrorNoItemLabelNotFound.setText(temp1);
                    }
                    break;
                }
            }

        }
        catch (Exception e){
            //TODO: Error, not a number
            return -1;
        }

        return index;
    }

    boolean quantityRegex(int number){
        int temp;
        boolean flag = false;
        try {
            temp = Integer.parseInt(FieldQuantity.getText());
            if (temp <= inventory.get(number).getQuantity()){
                flag = true;
                //System.out.println(flag + "part 1");
            }
            else {
                //TODO: Error, not enough quantity
                int howMuch = inventory.get(number).getQuantity();
                FieldDetails.clear();
                StoreApplication.getScene("notEnough","Sorry - Not Enough!");
                String build = "Insufficient stock, only ";
                String temp1 = String.valueOf(howMuch);
                build.concat(temp1);
                build.concat(" on hand. Please Reduce the quantity.");
                System.out.println(build);
                NotEnoughLabel.setText(build);
                flag = false;
            }
        }
        catch (Exception e){
            //TODO: Error, not a number
            return flag;
        }
        return flag;
    }
    private double discountCalc(int quantity) {
        double discount = 1;
        if (quantity < 5){}
        else if (quantity >4 && quantity < 10) {
            discount = .9;
        } else if (quantity > 9 && quantity < 15) {
            discount = .85;
        } else if (quantity >= 15) {
            discount = .8;
        }
        return discount;


    }
    public String showDisc(double discount){
        String show = "0";
        if (discount == 1) {
        }
        else if (discount == 0.9) {
            show = "10";
        }
        else if (discount == 0.85) {
         show = "15";
        }
        else if (discount == 0.8) {
            show = "20";
        }
        return  show;
    }



    public void addItem(){
        cart.add(FieldDetails.getText());
        int i = cart.size();
        subTotal+=priceHolder;
        String sub = String.valueOf(BigDecimal.valueOf(subTotal).setScale(2,RoundingMode.HALF_UP));
        subTotal = Double.parseDouble(sub);
        FieldSubtotal.setText(String.valueOf(subTotal));
        FieldItem.clear();
        FieldQuantity.clear();
        FieldDetails.clear();
        String temp = "Enter ID for Item #";
        temp = temp.concat(String.valueOf(i+1));
        temp = temp.concat(":");
        LabelID.setText(temp);
        temp = "Enter Quantity for Item #";
        temp = temp.concat(String.valueOf(i+1));
        temp = temp.concat(":");
        LabelQuantity.setText(temp);
        temp = "Details for Item #";
        temp = temp.concat(String.valueOf(i+1));
        temp = temp.concat(":");
        LabelDetails.setText(temp);
        temp = "Subtotal for";
        temp = temp.concat(String.valueOf(i));
        temp = temp.concat(" item(s):");
        LabelSubtotal.setText(temp);
        temp = "Find Item #";
        temp = temp.concat(String.valueOf(i+1));
        ButtonFindItem.setText(temp);
        temp = "Add Item #";
        temp = temp.concat(String.valueOf(i+1));
        temp = temp.concat(" Into Cart");
        ButtonAdd.setText(temp);
        ButtonView.setDisable(false);
        ButtonCheckOut.setDisable(false);
        ButtonFindItem.setDisable(false);
        ButtonAdd.setDisable(true);


        //TODO: ADD a scene that shows (Item added to cart)
    }

    public void showItems(){
        String temp = "";
        int size = cart.size();
        Stage stage = new Stage();
        VBox layout = new VBox(size+1);
        //StackPane view = new StackPane();
        for (int i = 0; i< size; i++){
            temp =  temp.concat(String.valueOf(i+1));
            temp =  temp.concat(". ");
            temp =  temp.concat(cart.get(i));
            temp = temp.concat("\n");
            Label label = new Label(temp);
            layout.getChildren().add(label);

            System.out.println((i+1) + ". " + cart.get(i));
        }
        Button button = new Button();
        button.setText("Okay");
        button.setOnAction(value ->  {
            Stage curStage = (Stage) button.getScene().getWindow();
            curStage.close();
        });
        layout.getChildren().add(button);
        button.translateXProperty();
        //layout.setPrefSize(100,200);

        Scene scene = new Scene(layout);
        stage.setScene(scene);
        stage.setTitle("View Cart!");
        stage.show();


    }
    public void checkOut(){
        ArrayList<String> checkScene = new ArrayList<>();
        Date dt = new Date();
        String properDate = DateFormat.getDateTimeInstance(DateFormat.LONG,DateFormat.LONG).format(dt);
        properDate = properDate.replace(" "," ");
        System.out.println(properDate);
        checkScene.add(properDate);
        String temp = "Number of line items: ";
        temp = temp.concat(String.valueOf(cart.size()));
        checkScene.add(temp);
        temp = "Item# / ID / Title / Price / Qty / Disc % / Subtotal:";
        checkScene.add(temp);


        System.out.printf("Number of line items: %d\n", cart.size());
        System.out.println("Item# / ID / Title / Price / Qty / Disc % / Subtotal:\n");
        for (int i = 0; i < cart.size(); i++){
            System.out.println( (i+1) + " " + cart.get(i));
            temp = String.valueOf(i+1);
            temp =  temp.concat(". ");
            temp = temp.concat(cart.get(i));
            checkScene.add(temp);
        }
        temp = "Order Subtotal: ";
        temp = temp.concat(String.valueOf(BigDecimal.valueOf(subTotal).setScale(2,RoundingMode.HALF_UP)));
        checkScene.add(temp);
        System.out.println("Order Subtotal: " + subTotal);
        double taxRate = .06;
        System.out.println("Tax Rate: 6%");
        temp = "Tax Rate: 6%";
        checkScene.add(temp);
        double tax = subTotal * taxRate;
        temp = String.valueOf(tax);
        temp = "Tax Amount: $";
        temp = temp.concat(String.valueOf(BigDecimal.valueOf(tax).setScale(2,RoundingMode.HALF_UP)));
        System.out.println("Tax Amount: $" + BigDecimal.valueOf(tax).setScale(2, RoundingMode.HALF_UP));
        checkScene.add(temp);
        double total = (subTotal + tax);
        temp = "Order total: $";
        temp = temp.concat(String.valueOf(BigDecimal.valueOf(total).setScale(2,RoundingMode.HALF_UP)));
        System.out.println("Order total: $" +BigDecimal.valueOf(total).setScale(2, RoundingMode.HALF_UP));
        checkScene.add(temp);
        System.out.println("Thanks for Ordering at Nile Dot Com!");
        temp = "Thanks for Ordering at Nile Dot Com!";
        checkScene.add(temp);
        sceneCheckOut(checkScene);
        updateTransaction(properDate);
    }

    public void sceneCheckOut(ArrayList<String> checkScene){
        int size = checkScene.size();
        Stage stage = new Stage();
        VBox layout = new VBox(size+1);
        for (int i = 0; i < size; i++){
            Label label = new Label(checkScene.get(i));
            layout.getChildren().add(label);

        }
        Button button = new Button();
        button.setText("Okay");
        button.setOnAction(value ->  {
            Stage curStage = (Stage) button.getScene().getWindow();
            curStage.close();
        });
        layout.getChildren().add(button);
        //layout.setPrefSize(100,200);

        Scene scene = new Scene(layout);
        stage.setScene(scene);
        stage.setTitle("Final Checkout!");
        stage.show();


    }

    public void Empty(){
        Stage stage1 = (Stage) FieldSubtotal.getScene().getWindow();
        stage1.close();
        StoreApplication.getScene("mainGui", "Nile Dot Com");

        for (int i = 0; i< cart.size();i++){
            cart.remove(i);
        }
        System.out.println(cart.size());
        subTotal = 0;
        FieldQuantity.clear();
        FieldItem.clear();
        FieldDetails.clear();
        FieldSubtotal.clear();

    }

    public void updateTransaction(String properDate) {
        String transactionDate = timeConverter(properDate);
        //System.out.println(transactionDate);
        try{
            File file = new File("out\\transactions.csv");
            if (file.createNewFile()){
                System.out.println("file created!\n");
                PrintWriter out = new PrintWriter(new FileWriter(file, true));
                for (int i = 0; i < cart.size();i++){
                    String transactionID = makeTransactionCart(transactionDate,cart.get(i),properDate);
                    out.write(transactionID);
                    out.write("\n");

                }
                out.write("\n");
                out.close();

            }
            else {
                System.out.println("transaction file found! \n");
                //FileWriter out = new FileWriter("out\\transactions.csv");
                PrintWriter out = new PrintWriter(new FileWriter(file, true));
                for (int i = 0; i < cart.size();i++){
                    String transactionID = makeTransactionCart(transactionDate,cart.get(i),properDate);
                    out.write(transactionID);
                    out.write("\n");
                }
                out.write("\n");
                out.close();
            }
        }
        catch (Exception e) {

        }
    }
    private static String timeConverter(String myString) {
        String[] splitter = myString.split(" ", 7);
        String month = monthDecider(splitter[0]);
        splitter[1] =  splitter[1].replace(",", "");
        splitter[2] =   splitter[2].replace(",","");
        String time = timeDecider(splitter[3],splitter[4]);

        String numberDate = splitter[1];
        numberDate = numberDate.concat(month);
        numberDate = numberDate.concat(splitter[2]);
        numberDate =  numberDate.concat(time);
        return numberDate;
    }


    private static String monthDecider(String s) {
        String month = "00";
        switch (s){
            case "January":
                month = "01";
                break;
            case "February":
                month = "02";
                break;
            case "March":
                month = "03";
                break;
            case "April":
                month = "04";
                break;
            case "May":
                month = "05";
                break;
            case "June":
                month = "06";
                break;
            case "July":
                month = "07";
                break;
            case "August":
                month = "08";
                break;
            case "September":
                month = "09";
                break;
            case "October":
                month = "10";
                break;
            case "November":
                month = "11";
                break;
            case "December":
                month = "12";
                break;
        }
        return month;
    }
    private static String timeDecider(String s, String s1) {
        String sHour = "0";
        String[] splitted = s.split(":", 3);
        for (int i = 0; i < splitted.length;i++){
        }
        char[] seconds = splitted[2].toCharArray();
        String stringSeconds = String.valueOf(seconds[0]);
        stringSeconds = stringSeconds.concat(String.valueOf(seconds[1]));
        int hour = Integer.parseInt(splitted[0]);
        if (s1.contains("PM")){
            hour+= 12;
            sHour = String.valueOf(hour);
        }
        else if (hour < 10){
            sHour =  sHour.concat(String.valueOf(hour));

        }
        String time = sHour;
        time = time.concat(splitted[1]);
        time = time.concat(stringSeconds);

        return  time;
    }
    public   String makeTransactionCart(String transactionDate, String item, String properDate){
        String temp = transactionDate;
       String idSplit[] = item.split(" ",2);
       String nameSplit[] = idSplit[1].split(" \\$",2);
       String priceSplit[] = nameSplit[1].split(" ",4);
       priceSplit[2] = priceSplit[2].replace("%", "");
       int discount = Integer.valueOf(priceSplit[2]);
       String strDiscount = showDiscTrans(discount);

       temp = temp.concat(", ");
       temp = temp.concat(idSplit[0]);
       temp = temp.concat(", ");
       temp = temp.concat(nameSplit[0]);
       temp = temp.concat(", ");
       temp = temp.concat(priceSplit[0]);
       temp = temp.concat(", ");
       temp = temp.concat(priceSplit[1]);
       temp = temp.concat(", ");
       temp = temp.concat(strDiscount);
       temp = temp.concat(", $");
       temp = temp.concat(priceSplit[3]);
       temp = temp.concat(", ");
       temp = temp.concat(properDate);

        return temp;
        }

        public String showDiscTrans(int discount){
        String str = "";
        switch (discount){
            case 0:
                str = "0.0";
                break;
            case 10:
                str = "0.1";
                break;
            case 15:
                str = "0.15";
                break;
            case 20:
                str = "0.2";
                break;
        }
        return str;
        }
    }





