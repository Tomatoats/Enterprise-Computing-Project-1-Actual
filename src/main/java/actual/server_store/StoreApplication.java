package actual.server_store;

/* Name:  Alexys Veloz
 Course: CNT 4714 – Fall 2023
 Assignment title: Project 1 – Event-driven Enterprise Simulation
 Date: Sunday September 17, 2023
*/

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.io.File;

public class StoreApplication extends Application {
    static ArrayList<Item> inventory = new ArrayList<>();
     static Map<String,Scene> sceneMap = new HashMap<>();
    @Override
    public void start(Stage stage) throws IOException {
        addScenes();
        Scene scene = sceneMap.get("mainGui");
        //FXMLLoader fxmlLoader = new FXMLLoader(StoreApplication.class.getResource("mainGui.fxml"));
        //Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("DainTree.com - Fall 2023");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("in\\inventory.csv");
        Scanner scan = new Scanner(file);
        scan.useDelimiter("\n");
        String str;
        while (scan.hasNextLine()){
            str = scan.nextLine();
            String[] ArrayofString = str.split(", ", 5);

            Item item = new Item(ArrayofString[0],ArrayofString[1], Boolean.parseBoolean(ArrayofString[2]),Integer.parseInt(ArrayofString[3]), Double.parseDouble(ArrayofString[4]));
            inventory.add(item);
        }
        //System.out.println(inventory.get(5).getPrice());
        //System.out.println(inventory.get(5).getPrice() + 2);
        //System.out.println(inventory.get(5).getHave_Any());
        //System.out.println(inventory.get(6).getHave_Any());
        //System.out.println(inventory.size() + "with last item being " + inventory.get(inventory.size() -1).getName());

        launch();
    }
    public void addScenes() throws IOException {
        //create a map and add all fxml files to it

        Parent add =  FXMLLoader.load(Objects.requireNonNull(getClass().getResource("mainGui.fxml")));
        Scene toAdd = new Scene(add);
        sceneMap.put("mainGui",toAdd);
        add =  FXMLLoader.load(Objects.requireNonNull(getClass().getResource("are you sure.fxml")));
        toAdd = new Scene(add);
        sceneMap.put("are you sure",toAdd);
        add =  FXMLLoader.load(Objects.requireNonNull(getClass().getResource("cartEmptied.fxml")));
        toAdd = new Scene(add);
        sceneMap.put("cartEmptied",toAdd);
        add =  FXMLLoader.load(Objects.requireNonNull(getClass().getResource("checkOut.fxml")));
        toAdd = new Scene(add);
        sceneMap.put("checkOut",toAdd);
        add =  FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Confirmed.fxml")));
        toAdd = new Scene(add);
        sceneMap.put("Confirmed",toAdd);
        add =  FXMLLoader.load(Objects.requireNonNull(getClass().getResource("ErrorNotFound.fxml")));
        toAdd = new Scene(add);
        sceneMap.put("ErrorNotFound",toAdd);
        add =  FXMLLoader.load(Objects.requireNonNull(getClass().getResource("notEnough.fxml")));
        toAdd = new Scene(add);
        sceneMap.put("notEnough",toAdd);
        add =  FXMLLoader.load(Objects.requireNonNull(getClass().getResource("out of stock Error.fxml")));
        toAdd = new Scene(add);
        sceneMap.put("out of stock",toAdd);
        add =  FXMLLoader.load(Objects.requireNonNull(getClass().getResource("viewCart.fxml")));
        toAdd = new Scene(add);
        sceneMap.put("viewCart",toAdd);

    }
    public static void getScene(String key, String title){
        Scene scene = sceneMap.get(key);
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();

        }
    }
