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
import javafx.scene.control.ComboBox;
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
import javafx.geometry.Pos;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.FlowPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import javafx.scene.layout.BorderPane;
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
    private final int WIDTH = 600;  // arbitrary number: change and update comments
    private final int HEIGHT = 500;
    
    // visual components to COMPLETE, starting code example
    // to have partial code handler working
    
    private Label title = new Label("Select a room: ");
    
    private Button saveAndQuitButton  = new Button("Save and Quit");
    private Button listButton  = new Button("A List"); 
    
    private TextArea displayArea1  = new TextArea();  // bad name, but use in handler code
    private TextArea displayArea2;
    
/**************** NEW FINAL VARIABLES ADDED ********************************/
    private String[] labels = new String[]{"Name:       ", "Month:      ", "Amount:   "};
    private int currRoomNum = 0;
    private VBox info_holder;
    private ArrayList<Button> roomButtonList = new ArrayList<Button>();
    private ComboBox combo_box = new ComboBox();
    private static String curr_month = "";
    private final int MAX_ROOMS = 35; //max no. of rooms allowed if there is no prior saved info
    private final int MAX_ROOMS_FROM_SAVED_STATE = 100; //max no. of rooms that can be collected prior saved info
/*************** START METHOD **********************************************/
    @Override
    /** Initialises the screen 
    *  @param stage:   The scene's stage 
    */
    public void start(Stage stage) {

        noOfRooms = getNumberOfRooms(); // call private method below for window
        get_saved_info();

        // create a VBox to display requested information
        // display a list of the housemates by default
        info_holder = new VBox(10);
        create_housemate_view(); // adds a ListView of housemates to the info_holder

        title.setFont(new Font("Arial", 20)); // create title
        BorderPane imgPane = create_img_pane(); //add decoration
        /*create a key explaining 
        * what the colors of the room buttons mean*/
        VBox key_holder = create_key();
        // create a Pane for the buttons for each room
        FlowPane room_grid = new FlowPane();
        create_room_buttons(room_grid, noOfRooms);
        recolor_room_buttons(); // recolors buttons based on information from the saved state (if any)
        room_grid.setPrefWrapLength(210);

        // Save current state of application before quiting
        BorderPane saveAndQuitButtonPane = create_save_and_quit_button();


        // create VBox for the title, decoration, key, room_grid, and saveAndQuitButton
        VBox grid_holder = new VBox();
        grid_holder.setSpacing(15);
        grid_holder.getChildren().addAll(title, imgPane, key_holder, room_grid, saveAndQuitButtonPane);
        

        HBox root = create_root(grid_holder);

        // create the scene
        Scene scene = new Scene(root, WIDTH, HEIGHT);
      
        // call private methods for button event handlers ---> DONE IN HELPER METHODS BELOW
        // you will need one for each button added: call and complete all the ones provided
        stage.setResizable(false); // TO AVOID EXTRA CODE FOR RESIZING GRACEFULLY
        stage.setScene(scene);
        stage.setTitle("Off-campus Housing Application");
        stage.show(); 
        
    }

/*////////////////////////////////////////////// NEW FUNCTIONS ADDED /////////////////////////////////////////*/

/*************************************** METHODS TO CREATE DEFAULT VIEW ****************************************/

    
    /*
    * Method to get the housemate list from a saved file (if any)
    * @return void
    */
    private void get_saved_info(){

        // cap on the number of rooms
        if(noOfRooms > MAX_ROOMS)
        {
            noOfRooms = MAX_ROOMS;
        }

        // that takes in number of rooms in house
        initFromFile(MAX_ROOMS_FROM_SAVED_STATE);
        int max = 1; // at least one room
        int curr_room=0;
        for(Housemate h: list.getHousemateList())
        {
            curr_room = h.getRoom();
            if(curr_room > max) {max = curr_room;}
        }

        // reset no. of rooms if saved state has more rooms than input
        if (max > noOfRooms){
            noOfRooms = max;
        }

        //initFromFile(noOfRooms);
    }


    /*
    * Method to create the root
    * @return an HBox
    */
    private BorderPane create_img_pane(){
        // load the image
        Image image = new Image("house2.png");
        // simple displays ImageView the image as is
        ImageView iv1 = new ImageView();
        iv1.setImage(image);
        iv1.setFitHeight(100);
        iv1.setFitWidth(100);
        BorderPane imgPane = new BorderPane();
        imgPane.setPrefSize(100,100);
        imgPane.setCenter(iv1);
        
        return imgPane;
    }

    /*
    * Method to create the save and quit button in a BorderPane
    * @return an HBox
    */
    private BorderPane create_save_and_quit_button(){
        Button saveAndQuitButton = new Button("Save and Quit");
        saveAndQuitButton.setOnAction(e -> saveAndQuitHandler());
        BorderPane saveAndQuitButtonPane = new BorderPane();
        saveAndQuitButtonPane.setPrefSize(100,50);
        saveAndQuitButtonPane.setCenter(saveAndQuitButton);
        
        return saveAndQuitButtonPane;
    }


    /*
    * Method to create the root
    * @return an HBox
    */
    private HBox create_root(VBox grid_holder){
       HBox root = new HBox(10);
       root.setSpacing(10);
       root.setPadding(new Insets(5, 15, 5, 15)); 
       root.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, new CornerRadii(0), new Insets(0))));
       root.getChildren().addAll(grid_holder, info_holder);
       grid_holder.setMinHeight(root.getHeight()*0.8);
       grid_holder.setMinWidth(root.getWidth()*0.8);
       
       return root;
   }


    /*
    * Method to recolor buttons based on occupancy
    * @return void
    */
    private VBox create_key(){
        Label l1 = new Label("Occupied");
        l1.setFont(new Font("Arial", 20));
        l1.setBackground(new Background(new BackgroundFill(Color.GRAY, new CornerRadii(0), new Insets(1))));

        Label l2 = new Label("Unoccupied");
        l2.setFont(new Font("Arial", 20));
        l2.setBackground(new Background(new BackgroundFill(Color.ORCHID, new CornerRadii(0), new Insets(1))));

        VBox key_holder = new VBox();
        key_holder.getChildren().addAll(l1, l2);

        return key_holder;
    }


    /**
    * Method to create a button for each room in the house
    * @return array of room buttons
    */
    private void create_room_buttons(FlowPane room_grid, int noOfRooms){
        int roomNum = 1;
        for(int i=0; i<noOfRooms;i++){
            Button room_i = new Button("" + roomNum);
            room_i.setPrefSize(Math.max(400/noOfRooms, 30),Math.max(400/noOfRooms, 30));
            room_i.setOnAction(e -> {currRoomNum = Integer.parseInt(room_i.getText()); 
                unselect_all_room_buttons(); 
                room_i.setStyle("-fx-border-color: black;"); 
                populateRoomInfo();});
            roomNum++;
            room_i.setBackground(new Background(new BackgroundFill(Color.ORCHID, new CornerRadii(10), Insets.EMPTY)));
            room_grid.getChildren().add(room_i);
            roomButtonList.add(room_i);
        }
    }

    private void unselect_all_room_buttons(){
        for(Button button:roomButtonList){
            button.setStyle("--fx-background-color: transparent");
        }
    }

     /*
    * Method to recolor buttons based on occupancy
    * @return void
    */
    private void recolor_room_buttons(){
        int roomNum = 1;
        for(int i=0; i<noOfRooms;i++){
            Button room_i = roomButtonList.get(i);
            if (list.search(roomNum) != null){
            room_i.setBackground(new Background(new BackgroundFill(Color.GRAY, new CornerRadii(10), Insets.EMPTY)));
            }
            
            roomNum++;
        }
        roomNum=0;
    }


    /**
    * Method to create a listview of all the housemates
    * 
    */
    private void create_housemate_view(){
        ArrayList<Housemate> h = HousemateList.getHousemateList();
        ObservableList<Housemate> observable_housemateList = FXCollections.observableArrayList(h);
        ListView<Housemate> housemate_view = new ListView<Housemate>();
        housemate_view.setItems(observable_housemateList);
        TableView<Housemate> table = new TableView<Housemate>();
        table.setItems(observable_housemateList);
        TableColumn<Housemate, Integer> roomNumCol = new TableColumn<Housemate, Integer>("Room Number");
        roomNumCol.setCellValueFactory(new PropertyValueFactory("room"));
        TableColumn<Housemate, String> nameCol = new TableColumn<Housemate, String>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory("name"));
        table.getColumns().setAll(roomNumCol, nameCol);
        info_holder.getChildren().add(table);
    }




/************************************ METHODS TO DISPLAY A SPECIFIC ROOM'S INFO ******************************/

    /**
    * Handler for the buttons corresponding to each room
    * @return void
    */
    private void populateRoomInfo(){

        info_holder.getChildren().clear();
        //int roomNum = currRoomNum;

        GridPane textPane = new GridPane();
        VBox textBox = new VBox();
        ArrayList<TextField> textfields = create_user_input_section(textBox);
        GridPane.setConstraints(textBox, 0, 0);
        Label numLabel = new Label("   Room "+currRoomNum);
        numLabel.setFont(new Font("Arial", 30));
        GridPane.setConstraints(numLabel, 10, 0);
        textPane.getChildren().addAll(textBox, numLabel);
        info_holder.getChildren().add(textPane);

        Button[] nav_buttons = create_room_nav_buttons(textfields);

        Housemate h = list.search(currRoomNum);
        if (h!=null){create_payment_view(h);}
    }



    /**
    * Method to create buttons for the following functionality:
    * 1. Update (adding a new roommate using the "name" Textfield)
    * 2. Exit (leave the specific room's info and go back to default view of the housemate list)
    * 3. Make Payment
    * 4. Remove existing housemate from the room
    * @return an array of buttons
    */
    private Button[] create_room_nav_buttons(ArrayList<TextField> textfields){
        Button[] nav_buttons = new Button[3];
        HBox nav_button_holder = new HBox();

        Button updateButton = new Button("Update");
        updateButton.setOnAction( e -> updateRoomInfo(textfields));
        nav_buttons[0]= updateButton;

        Button exitRoomButton = new Button("Exit Room");
        exitRoomButton.setOnAction( e -> exit_room());
        nav_buttons[1]= exitRoomButton;

        nav_button_holder.getChildren().addAll(updateButton, exitRoomButton);

        if (list.search(currRoomNum)!=null){
            Button removeHousemateButton = new Button("Remove Housemate");
            removeHousemateButton.setOnAction(e -> removeHousemate());
            nav_button_holder.getChildren().add(removeHousemateButton);

            Button makePaymentButton = new Button("Make Payment");
            String month = "";
            double amount = 0;
            makePaymentButton.setOnAction(e -> {makePayment(textfields);});
            nav_button_holder.getChildren().add(makePaymentButton);
        }

        info_holder.getChildren().add(nav_button_holder);
        return nav_buttons;

    }


    /**
    * Method to create text fields for user input
    * @return array of buttons to add user input from corresponding textfields
    */
    private ArrayList<TextField> create_user_input_section(VBox textBox){
        ArrayList<TextField> textfields = new ArrayList<TextField>(labels.length);
        int buttonNum = 0;
        Housemate currHousemate = list.search(currRoomNum);
        for(String s: labels){
            Label l = new Label(s);
            TextField t = new TextField("");
            if (s.equals("Name:       ")){
                if (currHousemate==null){t.setText("Unoccupied Room");}
                else{t.setText(currHousemate.getName());t.setEditable(false);}
            } 
            if (!(s.equals("Month:      "))){textfields.add(t);}
            else{combo_box = create_month_menu();}

            HBox label_and_textfield_holder = new HBox();
            if (!s.equals("Month:      ")){label_and_textfield_holder.getChildren().addAll(l, t);}
            else{label_and_textfield_holder.getChildren().addAll(l, combo_box);}
            buttonNum++;
            textBox.getChildren().add(label_and_textfield_holder);
            label_and_textfield_holder.setAlignment(Pos.TOP_LEFT);
        }

        return textfields;
    }

    /*
    * Method to create the menu of months
    * @return a ComboBox object 
    */
    private ComboBox create_month_menu(){
        String[] months =
        { "January", "February", "March", "April", "May", "June", 
        "July", "August", "September", "October", "November", "December"};

   // Create a combo box
   ComboBox combo_box = new ComboBox(FXCollections.observableArrayList(months));
   combo_box.valueProperty().addListener(new ChangeListener<String>() {
       @Override public void changed(ObservableValue ov, String t, String t1) {
           curr_month=t1;
       }    
   });
   return combo_box;
   }

   /**
    * Method to create a listview of payments
    * 
    */
    private void create_payment_view(Housemate h){
        ArrayList<Payment> p = h.getPayments().getPaymentList();
        ObservableList<Payment> observable_p = FXCollections.observableArrayList(p);
        TableView<Payment> paymentTable = new TableView<Payment>();
        paymentTable.setItems(observable_p);
        TableColumn<Payment, String> monthCol = new TableColumn<Payment, String>("Month");
        monthCol.setCellValueFactory(new PropertyValueFactory("month"));
        TableColumn<Payment, Integer> amountCol = new TableColumn<Payment, Integer>("Amount");
        amountCol.setCellValueFactory(new PropertyValueFactory("amount"));
        paymentTable.getColumns().setAll(monthCol, amountCol);
        info_holder.getChildren().add(paymentTable);
    }

/********************************* METHODS FOR FUNCTIONALITY FOR SPECIFIC ROOMS *****************************/

private void updateRoomInfo(ArrayList<TextField> textfields){
    String name = textfields.get(0).getText();
    if (!name.equals("Unoccupied Room")){
        Housemate newHousemate = new Housemate(name, currRoomNum);
        list.addHousemate(newHousemate);
        Button room_button = roomButtonList.get(currRoomNum-1);
        room_button.setBackground(new Background(new BackgroundFill(Color.GRAY, new CornerRadii(10), Insets.EMPTY)));
    }
    populateRoomInfo();
}

private void exit_room(){
    //info_holder = new VBox();
    info_holder.getChildren().clear();
    create_housemate_view();
    unselect_all_room_buttons();
}

private void removeHousemate(){
    list.removeHousemate(currRoomNum);
    populateRoomInfo();
    Button roomButton = roomButtonList.get(currRoomNum-1);
    roomButton.setBackground(new Background(new BackgroundFill(Color.ORCHID, new CornerRadii(10), Insets.EMPTY)));
}

private void makePayment(ArrayList<TextField> textfields){
    /* System.out.println("BEFORE");
    for(int i = 1; i <=noOfRooms; i++){
    Housemate h = list.getHousemate(i);
    if (h!=null){
        System.out.println(h.getPayments());
    }
   } */
    if ((textfields.get(1).getText() != "") && !curr_month.equals("")){
        //makePaymentButton.setDisable(false);
        String month = curr_month;
        curr_month="";
        double amount = Double.parseDouble(textfields.get(1).getText());
        if (amount>0){
            Payment p = new Payment(month, amount);
            Housemate h = list.search(currRoomNum);
            h.makePayment(p);
            populateRoomInfo();
        }
    }

    /* System.out.println("AFTER");
    for(int i = 1; i <=noOfRooms; i++){
    Housemate h = list.getHousemate(i);
        if (h!=null){
            System.out.println(h.getPayments());
        }
    } */
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
    private void initFromFile(int capacity) {
        list  = new HousemateList(capacity);   
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

