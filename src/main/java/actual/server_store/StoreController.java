package actual.server_store;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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
        addItem();
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
        Empty();

    }

    void findItem(){
        int number = IDRegex();
     if (number != -1){

         String id = (FieldItem.getText());
         int quantity = Integer.parseInt(FieldQuantity.getText());
         //System.out.println(id + quantity);
         double discount = discountCalc(quantity);
         double priceActual = (quantity * inventory.get(number).getPrice()) * discount;
         String showDiscount = showDisc(discount);
         String toDisplay =  inventory.get(number).getId() + " " + inventory.get(number).getName() + " " +"$" + inventory.get(number).getPrice() + " " + quantity + " " + showDiscount + "% " + priceActual;
         FieldDetails.setText(toDisplay);
         priceHolder=priceActual;
         FieldSubtotal.setText(String.valueOf(subTotal));

     }
     else {
         //TODO: Error, not a proper ID
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
                        return index;
                    }
                    else {
                        //TODO: Error, none in inventory
                    }
                    break;
                }
            }
            //int index = inventory.indexOf(inventory.i = temp);
            //boolean index = inventory.equals(temp);
            //System.out.println(index);
            //flag = inventory.contains(inventory.indexOf(temp));
            //if  (inventory.get(inventory.indexOf(temp)).getHave_Any()) {
              //  finalFlag = quantityRegex(inventory.indexOf(temp));
                //System.out.println(finalFlag);
            //}
            //else{
                //TODO: Error, don't have any
            //}

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

        subTotal+=priceHolder;
        //TODO: ADD a scene that shows (Item added to cart)
    }

    public void showItems(){
        for (int i = 0; i< cart.size(); i++){

            System.out.println((i+1) + ". " + cart.get(i));
        }

    }
    public void checkOut(){

        Date dt = new Date();
        String properDate = DateFormat.getDateTimeInstance(DateFormat.LONG,DateFormat.LONG).format(dt);
        properDate = properDate.replace(" "," ");
        System.out.println(properDate);


        System.out.printf("Number of line items: %d\n", cart.size());
        System.out.println("Item# / ID / Title / Price / Qty / Disc % / Subtotal:\n");
        for (int i = 0; i < cart.size(); i++){
            System.out.println( (i+1) + " " + cart.get(i));
        }
        System.out.println("Order Subtotal: " + subTotal);
        double taxRate = .06;
        System.out.println("Tax Rate: 6%");
        double tax = subTotal * taxRate;
        System.out.println("Tax Amount: $" + BigDecimal.valueOf(tax).setScale(2, RoundingMode.HALF_UP));
        double total = (subTotal + tax);
        System.out.println("Order total: $" +BigDecimal.valueOf(total).setScale(2, RoundingMode.HALF_UP));
        System.out.println("Thanks for Ordering at Nile Dot Com!");
        updateTransaction(properDate);
    }


    public void Empty(){
        cart.clear();
        subTotal = 0;

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

                    //out.write(transactionDate);
                    //out.write(", ");
                    //out.write(cart.get(i));
                    //out.write(", ");
                    //out.write(properDate);
                    //out.write("\n");
                }
                out.write("\n\n");
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
                    //out.write(transactionDate);
                    //out.write(", ");
                    //out.write(cart.get(i));
                    //out.write(", ");
                    //out.write(properDate);
                    //out.write("\n");
                }
                out.write("\n\n");
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





