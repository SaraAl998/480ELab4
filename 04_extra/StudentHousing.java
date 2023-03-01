import java.text.NumberFormat;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import java.lang.Math;
import javafx.scene.control.ListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.ArrayList;
import javafx.scene.layout.Pane;

/**
* 
* GUI for Off-campus Housing application
*      @author
*      @version
* 
* List of resources you used:
* 
*/
public class StudentHousing extends Application {
    
    private int noOfRooms;
    private HousemateList list;   // important variable to the model, keep track of all
                                // the info entered, modified and to display
    
    // WIDTH and HEIGHT of GUI stored as constants 
    private final int WIDTH = 400;  // arbitrary number: change and update comments
    private final int HEIGHT = 200;
    
    // visual components to COMPLETE, starting code example
    // to have partial code handler working
    
    private Label title = new Label("Housemates");
    
    private Button saveAndQuitButton  = new Button("Save and Quit");
    private Button listButton  = new Button("A List"); 
    
    private TextArea displayArea1  = new TextArea();  // bad name, but use in handler code
    private TextArea displayArea2;
    
/**************** NEW FINAL VARIABLES ADDED ********************************/
    private String[] labels = new String[]{"Room:", "Name:", "Month:", "Payments:"};
    

/*************** START METHOD **********************************************/
    @Override
    /** Initialises the screen 
    *  @param stage:   The scene's stage 
    */
    public void start(Stage stage) {
        
        noOfRooms = getNumberOfRooms(); // call private method below for window
        // that takes in number of rooms in house 
        
        // create the buttons for the rooms
        // and add them to the room_grid's children
        GridPane room_grid = new GridPane();
        ArrayList<Button> roomButtons = create_room_buttons(room_grid, noOfRooms);
        
        // create a VBox for housemate details textfields
        // and add labels, textfields and buttons inside hboxes
        // and store each button and userInputButtons
        VBox info_holder = new VBox(10);
        Button[] userInputButtons = create_user_input_section(info_holder);
        create_payment_view(info_holder); // adds a ListView of payments to the info_holder


        // create HBox for the title and the room_grid
        VBox grid_holder = new VBox();
        Font font = new Font("Calibri", 20); // set font of heading
        title.setFont(font);
        grid_holder.getChildren().addAll(title, room_grid);


        HBox root = new HBox(10);
        root.getChildren().addAll(grid_holder, info_holder);
        // create the scene
        Scene scene = new Scene(root, WIDTH, HEIGHT);
      
        // call private methods for button event handlers
        // you will need one for each button added: call and complete all the ones provided
        
        stage.setScene(scene);
        stage.setTitle("Off-campus Houseing Application");
        stage.show(); 
        
    }

/*************************************** NEW FUNCTIONS ADDED ****************************************/

    /**
    * Method to create a listview of payments
    * 
    */
    private void create_payment_view(VBox info_holder){
        ArrayList<Payment> p = new ArrayList<Payment>();
        ObservableList<Payment> observable_p = FXCollections.observableArrayList(p);
        ListView<Payment> payment_view = new ListView<Payment>();
        payment_view.setItems(observable_p);
        info_holder.getChildren().add(payment_view);
    }
    

    /**
    * Method to create a button for each room in the house
    * @return array of room buttons
    */
    private ArrayList<Button> create_room_buttons(GridPane room_grid, int noOfRooms){
        ArrayList<Button> roomButtons = new ArrayList<Button>(noOfRooms);
        int squareRoot = (int)Math.pow(noOfRooms, .5);
        int remainder = noOfRooms - (int)Math.pow(squareRoot,2);
        System.out.println(remainder);
        int roomNum = 1;
        for(int i=0; i<squareRoot;i++){
            for(int j = 0; j < squareRoot;j++){
            //String room_num = Integer.toString(i+1);
            Button room_i = new Button("" + roomNum);
            room_i.setOnAction(e -> buttonPressed(room_i));
            roomNum++;
            room_i.setBackground(new Background(new BackgroundFill(Color.GREENYELLOW, new CornerRadii(10), Insets.EMPTY)));
            room_grid.add(room_i, j,i);
            roomButtons.add(room_i);
            }
        }
        for(int i=0; i < remainder; i++){
            Button room_i = new Button("" + roomNum);
            roomNum++;
            room_i.setBackground(new Background(new BackgroundFill(Color.GREENYELLOW, new CornerRadii(10), Insets.EMPTY)));
            room_grid.add(room_i, i, squareRoot);
            roomButtons.add(room_i);
        }

        return roomButtons;
    }

    private void buttonPressed(Button button)
    {
        t.setEditable(true);
    }


    /**
    * Method to create text fields for user input
    * @return array of buttons to add user input from corresponding textfields
    */
    private Button[] create_user_input_section(VBox info_holder){
        Button[] add_buttons = new Button[(labels.length)];
        int buttonNum = 0;
        for(String s: labels){
            Label l = new Label(s);
            TextField t = new TextField("");
            t.setEditable(false);
            add_buttons[buttonNum] = new Button("+");
            
            HBox h = new HBox();
            h.getChildren().addAll(l, t, add_buttons[buttonNum]);
            buttonNum++;
            info_holder.getChildren().add(h);
        }

        return add_buttons;
    }

/*************************************** FUNCTIONS GIVEN TO US ********************************************************/

    /**
    * Method to request number of house rooms from the user
    * @return number of rooms
    */
    private int getNumberOfRooms() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setHeaderText("How many rooms are in the house?");
        dialog.setTitle("House Attributes");
        
        String response = dialog.showAndWait().get(); 
        return Integer.parseInt(response);  
    }
    
    /**
    * Method to initialize housemate list from file
    * Need to be called at the beginning of application to implement
    * save to file feature
    */
    private void initFromFile() {
        list  = new HousemateList(noOfRooms);   
        HousemateFileHandler.readRecords(list);
    }
    
    /**
    * Method to display housemate list in text area
    * Need to be called and completed 
    */
    public void displayHandler() {
        // A message for empty house should be displayed when appropriate
        // so user get some feedback 
        if (!list.isEmpty()) {
            // header
            displayArea1.setText("Room" +  "\t" +  "Name" +  "\n");
            // rest of info missing...
            displayArea1.appendText("To be completed \n");
        }
    }
    
    
    /**
    * Method to display payment list in text are for a particular room:
    *          !!! right now hard-coded for room 1 !!!!
    * Need be called, modified and completed to handle errors
    */
    private void listPaymentHandler() {  
        // List payments for hard-coded room 1
        // Instead of 1 should be replaced by a variable connected to a widget
        Housemate t =  list.search(1);   
        
        PaymentList p  = t.getPayments();
        if(t.getPayments().getTotal() == 0) {
            displayArea2.setText("No payments made for this housemate");
        } 
        else {  
            //The NumberFormat class is similar to the DecimalFormat class that we used previously.
            //The getCurrencyInstance method of this class reads the system values to find out 
            //which country we are in, then uses the correct currency symbol 
            NumberFormat nf =  NumberFormat.getCurrencyInstance();
            String s;
            displayArea2.setText("Month" +  "\t\t" +  "Amount" +  "\n");
            for (int i =  1; i <=  p.getTotal(); i++  ) {
                s =  nf.format(p.getPayment(i).getAmount());
                displayArea2.appendText("" + p.getPayment(i).getMonth() +  "\t\t\t" + s + "\n");
            } 
            displayArea2.appendText("\n" + "Total paid so far :   " + 				
            nf.format(p.calculateTotalPaid()));
        }
    }
    
    private void saveAndQuitHandler() {
        HousemateFileHandler.saveRecords(noOfRooms, list);
        Platform.exit();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
}

