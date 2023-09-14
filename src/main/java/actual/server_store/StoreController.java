package actual.server_store;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;

import static actual.server_store.StoreApplication.inventory;

public class StoreController {
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
    void Exit(javafx.event.ActionEvent actionEvent) {
            Stage stage = (Stage) ButtonExit.getScene().getWindow();
            stage.close();
    }
    @FXML
    void FindItemPressed(javafx.event.ActionEvent actionEvent) {
            findItem();
            //FieldDetails.setText("You've done it!");
    }
    void findItem(){
        int number = IDRegex();
     if (number != -1){

         String id = (FieldItem.getText());
         int quantity = Integer.parseInt(FieldQuantity.getText());
         System.out.println(id + quantity);
         int discount = 0;
         double priceActual = 6.95;
         String toDisplay =  inventory.get(number).getId() + " " + inventory.get(number).getName() + " " +"$" + inventory.get(number).getPrice() + " " + quantity + " " + discount + "% " + priceActual;

         FieldDetails.setText(toDisplay);
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
            System.out.println(temp);
            for (Item items : inventory) {

                System.out.println(inventory.indexOf(items) + " " +  inventory.get(inventory.indexOf(items)).getId());
                System.out.println(inventory.get(inventory.indexOf(items)).getId() + "= " + temp + inventory.get(inventory.indexOf(items)).getId().equals(temp));
                if (inventory.get(inventory.indexOf(items)).getId().equals(temp)) {

                    index = inventory.indexOf(items);
                    System.out.println(index);
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
                System.out.println(flag + "part 1");
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



}