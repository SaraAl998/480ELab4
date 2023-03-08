import java.text.NumberFormat;
import java.util.Optional;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;

/**
* GUI for Off-campus Housing application
*/
public class StudentHousing_ToReview extends Application {
 
    // WIDTH and HEIGHT of GUI stored as constants 
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    private static final int MIN_WIDTH = 600;
    private static final int MIN_HEIGHT = 500;

    private static final String[] MONTHS = {
        "Jan", "Feb", "Mar", "Apr", "May", "Jun",
        "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
    };
    
    private static final Background buttonBackground =
            new Background(new BackgroundFill(Color.LIGHTYELLOW, 
				new CornerRadii(10), Insets.EMPTY));

    // the attributes
    private int noOfRooms;
    private HousemateList list;

    // visual components
    private Label headingLabel = new Label("Off-campus Housing Application");

    private ComboBox roomCombo1 =  new ComboBox();
    private TextField nameField =  new TextField();
    
    private Button addButton = new Button("Add Housemate");
    private Button displayButton =  new  Button("Display Housemates");
    private Button removeButton  = new Button("Remove Housemate");
    private Button saveButton  = new Button("Save Housemates");

    private TextArea displayArea1  = new TextArea();

    private ComboBox roomCombo2 =  new ComboBox();
    private ComboBox monthCombo  = new ComboBox();
    private TextField amountField =  new TextField();

    private Button paymentButton  = new Button("Make Payment");
    private Button listButton  = new Button("List Payments"); 
    private TextArea displayArea2 =  new TextArea();
    
    private Button quitButton  = new Button("Quit");

    @Override
    /** Initialises the screen 
    *  @param stage:   The scene's stage 
    */
    public void start(Stage stage) {     
        
        initHouse();
        buildComboBoxes();

        Label roomLabel1 = new Label("Room");
        Label nameLabel = new Label("Name"); 

        // create four HBoxes
        HBox roomDetails = new HBox (10);
        HBox housemateButtons = new HBox(10);  

        // add components to HBoxes
        roomDetails.getChildren().addAll(roomLabel1, roomCombo1, 
                                         nameLabel, nameField);
        
        housemateButtons.getChildren().addAll(addButton, displayButton, removeButton, 
                                                saveButton);

        Label roomLabel2 = new Label("Room");
        Label monthLabel = new Label("Month");
        Label amountLabel = new Label("Amount");

        HBox paymentDetails = new HBox(10);
        HBox paymentButtons = new HBox(10);
                                        
        paymentDetails.getChildren().addAll(roomLabel2, roomCombo2, 
                                            monthLabel, monthCombo, 
                                            amountLabel, amountField);

        paymentButtons.getChildren().addAll(paymentButton, listButton);


        displayArea1.setEditable(false);
        displayArea2.setEditable(false);
        
        // create VBoxes
        VBox root = new VBox(10);

        // add all components to VBox
        root.getChildren().addAll(headingLabel, 
                          roomDetails, housemateButtons, displayArea1,
                          paymentDetails, paymentButtons, displayArea2, quitButton);
                                  
     
        // create the scene
        Scene scene = new Scene(root, Color.LIGHTBLUE);
        
        // not great, but maintain some consistency in sizing 
        displayArea1.setMaxSize(WIDTH - 80, HEIGHT/5);
        displayArea2.setMaxSize(WIDTH - 80, HEIGHT/5);
        
        stage.setWidth(WIDTH);
        stage.setHeight(HEIGHT);    
      
        stage.setMinWidth(MIN_WIDTH);
        stage.setMinHeight(MIN_HEIGHT);

        customiseStyle(root);  // try without to see diffference

        setHandlers();
        HBox[] hBoxes = {roomDetails, housemateButtons,
            paymentDetails, paymentButtons};
        VBox[] vBoxes = {root};
        setAlignments(hBoxes, vBoxes, Pos.CENTER);


        // configure the stage and make the stage visible
        stage.setScene(scene);
        stage.setTitle("Off-campus Houseing Application");

        stage.show(); 
    }
    
    private void initHouse() {
        noOfRooms = getNumberOfRooms(); 
        // initialise housemate list
        list  = new HousemateList(noOfRooms);   
        HousemateFileHandler.readRecords(list);
    }

    private void buildComboBoxes() {

        for (int i = 1; i <= noOfRooms; i++ ) {
            roomCombo1.getItems().add(i);
            roomCombo2.getItems().add(i);
        }
        roomCombo1.setValue("1");
        roomCombo2.setValue("1");

        for (int i = 0; i < MONTHS.length; i++ ) {
            monthCombo.getItems().add(MONTHS[i]);
        }
        monthCombo.setValue(MONTHS[0]);
    }
       

    private void setAlignments(HBox[] hb, VBox[] vb, Pos pos) {

        for (int i=0; i<hb.length; i++) {
           hb[i].setAlignment(pos);
        }
        for (int i=0; i<vb.length; i++) {
            vb[i].setAlignment(pos);
         }
    }

    /**
    * Method to request number of house rooms from the user
    * @return number of rooms if valid int entered and 
    * OK button pressed
    */
    private int getNumberOfRooms() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setHeaderText("How many rooms?");
        dialog.setTitle("Room Information Request");
        
        Optional<String> result = dialog.showAndWait();
        return getValidPositiveIntegerOnOK(result);
    }

    /**
    * Method to return user positive int after pressing OK on a TextInputDialog
    * @return positive integer as parsed or 
    * close the application for bad input or Cancel pressed
    */
    private int getValidPositiveIntegerOnOK(Optional<String> result) {
        if (result.isPresent()) {
            String response = result.get();
            int num = getPositiveInteger(response);
            if (num != -1) return num;
        } 

        // Cancel Pressed or invalid int
        System.out.println("A positive integer should have been entered");
        System.out.println("Goodbye");
        Platform.exit();
        return -1; 
    }

    /**
    * Method to return a positive int from the String passed
    * @return positive integer or 
    * -1 for String that aren't positive int 
    */
    private int getPositiveInteger(String aString) {
        if (aString.matches("^[+]?\\d+$")) {
                return Integer.parseInt(aString);
        }
        return -1;
    }

    // customise the visual components
    private void customiseStyle(VBox aRoot) {
       
        // set font of heading
        Font font = new Font("Calibri", 40);
        headingLabel.setFont(font);

        // customise the VBox border and background
        BorderStroke style = new BorderStroke(
            Color.BLACK, BorderStrokeStyle.SOLID, 
            new CornerRadii(0), new BorderWidths(2));
        aRoot.setBorder(new Border(style));
        aRoot.setBackground(Background.EMPTY);

        roomCombo1.setBackground(buttonBackground);
        roomCombo2.setBackground(buttonBackground);
        monthCombo.setBackground(buttonBackground);

        // customise buttons
        addButton.setBackground(buttonBackground);
        displayButton.setBackground(buttonBackground);	
        removeButton.setBackground(buttonBackground);
        saveButton.setBackground(buttonBackground);
        paymentButton.setBackground(buttonBackground);
        listButton.setBackground(buttonBackground);
        quitButton.setBackground(buttonBackground);
    }

    private void setHandlers() {

        addButton.setOnAction( e -> addHandler());
        removeButton.setOnAction( e -> removeHandler());
        displayButton.setOnAction( e -> displayHousematesHandler());
        saveButton.setOnAction( e -> saveHandler());

        paymentButton.setOnAction( e -> paymentHandler());
        listButton.setOnAction( e -> displayPaymentsHandler());
        quitButton.setOnAction( e -> Platform.exit());
    }


    // event handler methods
    private void addHandler() {
        int roomNumber =  Integer.parseInt(roomCombo1.getValue().toString());
        //System.out.println("DEBUG: Adding " + roomNumber);
        String nameEntered =  nameField.getText();
        // check for errors
        if ( nameEntered.length()== 0) {
            displayArea1.setText ("A name must be entered");
        } 
        else if (list.search(roomNumber) !=  null) {
            displayArea1.setText("Room number " +  roomNumber  + " is occupied");
        }
        else { // ok to add a Housemate
            Housemate t =  new Housemate(nameEntered, roomNumber);
            list.addHousemate(t);
            nameField.setText("");
            displayArea1.setText("New housemate in room " 	
                   +  roomNumber +  " successfully added");

            roomCombo1.setValue((roomNumber+1)%roomNumber);
        }
    }
    
    private void displayHousematesHandler() {
        int i;
        if (list.isEmpty()) { // no rooms to display
            displayArea1.setText("All rooms are empty");
        } 
        else { // display rooms
            displayArea1.setText("Room" +  "\t" +  "Name" +  "\n");
            for(i = 1; i <=  list.getTotal(); i++ ) {
                displayArea1.appendText(list.getHousemate(i).getRoom() 
                + "\t\t" 
                + list.getHousemate(i).getName() + "\n");
            }
        }
    }
    
    private void removeHandler() {
        int roomNumber =  Integer.parseInt(roomCombo1.getValue().toString());
        //System.out.println("DEBUG: Room " + roomNumber + " to empty");
        // check for errors
        if (list.search(roomNumber) == null) {
            displayArea1.setText("Room number " +  roomNumber +  " is empty");
        } 
        else { // ok to remove Housemate 
            list.removeHousemate(roomNumber);
            displayArea1.setText("Housemate removed from room " + roomNumber);
        }
    }
    
    private void paymentHandler() {
        int roomNumber =  Integer.parseInt(roomCombo2.getValue().toString());
        String monthEntered = monthCombo.getValue().toString();
        String amountEntered = amountField.getText();

        // check for errors
        if (amountEntered.length()== 0) {
            displayArea2.setText("An amount must all be entered");
        } 
        else if (list.search(roomNumber) == null) {
            displayArea2.setText("Room number " +  roomNumber +  " is empty");
        } 
        else { // ok to process payment
            Payment p =  new Payment(monthEntered, Double.parseDouble(amountEntered));
            list.search(roomNumber).makePayment(p);
            displayArea2.setText("Payment recorded");
        }
    }
    
    private void displayPaymentsHandler() {
        
        int roomNumber =  Integer.parseInt(roomCombo2.getValue().toString());

        // check for errors (we assume payment is a double)
        if (list.search(roomNumber) == null) {
            displayArea2.setText("Room number " + roomNumber 
                            + " is empty");
        } 
        else { // ok to list payments
            
            Housemate t =  list.search(roomNumber);
            PaymentList p  = t.getPayments();
            if (t.getPayments().getTotal() == 0) {
                displayArea2.setText("No payments made for this housemate");
            } 
            else {  
                /* The NumberFormat class is similar to the DecimalFormat class 
                that we used previously.
                The getCurrencyInstance method of this class reads the system 
                values to find out which country we are in, then uses the 
                correct currency symbol */
                NumberFormat nf =  NumberFormat.getCurrencyInstance();
                String s;
                displayArea2.setText("Month" +  "\t\t" +  "Amount" +  "\n");
                for (int i =  1; i <=  p.getTotal(); i++  ) {
                    s =  nf.format(p.getPayment(i).getAmount());
                    displayArea2.appendText("" + p.getPayment(i).getMonth() 
                             +  "\t\t\t" + s + "\n");
                } 
                displayArea2.appendText("\n" + "Total paid by " + 
                        t.getName() + " so far :   " + 				
                nf.format(p.calculateTotalPaid()));
                amountField.setText("");
            } 
        }
    }
    
    private void saveHandler() {
        HousemateFileHandler.saveRecords(noOfRooms, list);
        displayArea1.setText("Housemates saved to file");
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
}