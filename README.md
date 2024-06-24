# StudentHousing

StudentHousing is a simple GUI program for recording payment information for the tenants of a rented house.

## Description

StudentHousing can be used to record whether a room in the house is occupied or not, and who is renting an occupied room. For rented rooms, this program further provides the functionality of adding and storing thr month and amount of rent payments for each tenant.
StudentHousing is a GUI built using javafx, with a backend API written in java. The Housemate and Payment classes provide objects to store tenant and payment options respectively. The HousemateList and PaymentList objects help store the Housemate and Payment objects in chronological order. These four classes form the API behind StudentHousing. 

## Dependencies

The following is a complete list of java and javafx packages, used by some or all of the classes mentioned above:

#### Java Packages
1. java.lang.Math;
2. java.util.ArrayList;
3. java.text.NumberFormat;

#### Javafx Packages
4. javafx.application.Application;
5. javafx.application.Platform;
6. javafx.geometry.Insets;
7. javafx.geometry.Pos;
8. javafx.scene.Scene;
9.  javafx.scene.control.Button;
10. javafx.scene.control.Label;
11. javafx.scene.control.TextArea;
12. javafx.scene.control.TextField;
13. javafx.scene.layout.Background;
14. javafx.scene.layout.BackgroundFill;
15. javafx.scene.layout.Border;
16. javafx.scene.layout.BorderStroke;
17. javafx.scene.layout.BorderStrokeStyle;
18. javafx.scene.layout.BorderWidths;
19. javafx.scene.layout.CornerRadii;
20. javafx.scene.layout.HBox;
21. javafx.scene.layout.VBox;
22. javafx.scene.control.ComboBox;
23. javafx.scene.paint.Color;
24. javafx.scene.text.Font;
25. javafx.stage.Stage;
26. javafx.scene.control.TextInputDialog;
27. javafx.scene.layout.GridPane;
28. javafx.scene.control.ListView;
29. javafx.collections.FXCollections;
30. javafx.collections.ObservableList;
31. javafx.scene.layout.Pane;
32. javafx.geometry.Pos;
33. javafx.scene.control.TableColumn;
34. javafx.scene.control.TableView;
35. javafx.beans.property.SimpleStringProperty;
36. javafx.scene.control.cell.PropertyValueFactory;
37. javafx.scene.layout.FlowPane;
38. javafx.scene.image.Image;
39. javafx.scene.image.ImageView;
40. javafx.beans.value.ChangeListener;
41. javafx.beans.value.ObservableValue;
42. import javafx.scene.layout.BorderPane;


## How to run

Step 1: Navigate to the directory where all the .java files mentioned in the Description section are located, including the GUI code StudentHousing.java.

Step 2: Compile using the following command, replacing javafx_packages_dir with the path to the directory of the javafx .jar files:

```bash
javac --module-path "javafx_packages_dir" --add-modules javafx.controls *.java
```

Step 3: Run using the following command, again replacing javafx_packages_dir with the appropriate path:

```bash
java --module-path "javafx_packages_dir" --add-modules javafx.controls StudentHousing
```

## Usage

#### 1. Adding Housemates

Enter the name of the new housemate.

![Alt text](Screenshot%20(59).png)

Then press the Update button.

![Alt text](Screenshot%20(60).png)

#### 2. Viewing a list of the Housemates present in the house

This is the default view when the application is started.

![Alt text](Screenshot%20(64).png)

#### 3. Adding Payments

Choose the month and enter the amount.

![Alt text](Screenshot%20(62).png)

Then press the "Make Payment" button.

![Alt text](Screenshot%20(63).png)

## Acknowledgements

In addition to the main writers of the code Lucca Figlioli and Sara Alam, the following contributors provided significant support for the project:

1. Professor Elodie Fourquet who taught important concepts regarding GUI development
2. TA Gabby Laines who helped further reinforce core concepts
3. All the fellow students in COSC 480E whose active participation during lessons helped clarify ideas.

## License

[MIT](https://choosealicense.com/licenses/mit/)
