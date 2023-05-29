package application;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Main extends Application {
	
	BufferedImage picture;
	BufferedImage pictureResult;
	BufferedImage pictureOrigin;
	int height;
	int width;	
	
    private ImageView imageView; // Declare as a field
    private ImageView imageView2; // Declare as a field
    private Image imageRes;

	
	private Scene mainScene; // Store the main scene as a field
	
	public void fileWrite(String name)throws IOException{
		if (name.isEmpty()) 
			name = "default";
	    String aux = "src/"+ name ;
	    aux=aux+".bmp";
	    
	    try {
	        File f = new File(
	            aux);
	        ImageIO.write(pictureResult, "bmp", f);
	    }
	    catch (IOException e) {
	        System.out.println("Error: " + e);
	    }
	}

    @Override
    public void start(Stage primaryStage) throws IOException {
    	
    	
    	    	
    	// Create UI controls for the main scene
        Button switchSceneButton = new Button("Change menu");
        switchSceneButton.setOnAction(e -> {
            // Switch to the second scene
            primaryStage.setScene(createSecondScene(primaryStage));
        });
		
		
    			
        primaryStage.setTitle("Cocosila Alexandru - 341A3");
        
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("BMP Files", "*.bmp"));

        ImageView imageView = new ImageView();
        ImageView imageView2 = new ImageView();


        Button uploadButton = new Button("Incarca imagine BMP");
        Button buttonTransform = new Button("Transforma");
        buttonTransform.setDisable(true);
        uploadButton.setOnAction(e -> {
        	File initialDirectory = new File("src");
            fileChooser.setInitialDirectory(initialDirectory);
            
            File file = fileChooser.showOpenDialog(primaryStage);
            if (file != null) {
                // Perform actions with the uploaded BMP file
                System.out.println("Uploaded file: " + file.getAbsolutePath());
                try {
					picture = ImageIO.read(file);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
                width = picture.getWidth();
        		height = picture.getHeight();
        		pictureResult = new BufferedImage(
        	            width, height, BufferedImage.TYPE_INT_RGB);
        		pictureOrigin = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        		
        	 Image image = SwingFXUtils.toFXImage(picture, null);
       		 imageView.setImage(image);
       		 buttonTransform.setDisable(false);
        		
        		for (int y = 0; y < height; y++) {
        	
                    for (int lx = 0, rx = width - 1; lx < width; lx++, rx--) {
                       
                        int p = picture.getRGB(lx, y);
                      
                        
      
                        pictureResult.setRGB(rx, y, p);
                    }
                }
        		
        		 
        		
        		// Create JavaFX Image and ImageView
//                Image image = SwingFXUtils.toFXImage(pictureResult, null);
//                localImageView = new ImageView(image);
        		imageRes = SwingFXUtils.toFXImage(pictureResult, null);

            }
        });
        
     

        // Create UI controls
        Label label = new Label("Nume fisier rezultat");
        Label labelPass = new Label("Parola:");


        RadioButton radioButton = new RadioButton("Activeaza random ratio (optional)");
        ToggleButton toggleButton = new ToggleButton("Accept Termenii si Conditiile.");
        CheckBox checkBox = new CheckBox("Termeni si Conditii acceptate.");
        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        choiceBox.getItems().addAll("1 copie", "2 copii", "3 copii");
        choiceBox.setValue("1 copy");
        TextField textField = new TextField();
        PasswordField passwordField = new PasswordField();
        

        // Set actions for UI controls
        buttonTransform.setOnAction(e -> {imageView2.setImage(imageRes);
        try {
			this.fileWrite(textField.getText().toString());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        });
        radioButton.setOnAction(e -> showAlert("Random ratio activat"));
        toggleButton.setOnAction(e -> {checkBox.setSelected(checkBox.isSelected() ? false : true );
         
        });
        checkBox.setOnAction(e -> System.out.println("Checkbox-ul este: " + (checkBox.isSelected()? "selectat" : "neselectat")));
        choiceBox.setOnAction(e -> System.out.println("Choicebox-ul este:  " + choiceBox.getValue()));
       
        

        // Create layout and add controls
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));
        vbox.getChildren().addAll(
        		switchSceneButton,uploadButton,label,textField,  labelPass, passwordField, radioButton, toggleButton, checkBox, choiceBox, buttonTransform, imageView, imageView2);
        		

        // Create scene and set it on the stage
        mainScene = new Scene(vbox, 1000, 700);  // Adjusted width and height
        primaryStage.setScene(mainScene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    
    private Scene createSecondScene(Stage primaryStage) {
    	
    	
        // Create UI controls for the second scene
        Button backButton = new Button("Inapoi");
        backButton.setOnAction(e -> {
            // Switch back to the main scene
            primaryStage.setScene(mainScene);
        });
        Label label = new Label("Aceasta este a doua scena. (hold for tooltip)");
        
     // Create the scroll bar
        ScrollBar scrollBar = new ScrollBar();

        // Encapsulate the scroll bar within a container
        StackPane scrollBarContainer = new StackPane();
        scrollBarContainer.getChildren().add(scrollBar);
        scrollBarContainer.setPrefWidth(700); // Set the desired width in pixels
        scrollBarContainer.setMinWidth(20);
        scrollBarContainer.setMaxWidth(20);
        scrollBarContainer.setMargin(scrollBar, new Insets(0, 0, 0, 40));
        
        
//        ScrollBar scrollBar = new ScrollBar();
//        scrollBar.setPrefWidth(1000);
        ScrollPane scrollPane = new ScrollPane();
        ListView<String> listView = new ListView<>();
        listView.getItems().addAll("Image Mirroring", "Transform Z", "Filter upwards");
        
        TableView<String> tableView = new TableView<>();
     // Create the table column
        TableColumn<String, String> tableColumn = new TableColumn<>("Numaratoare");

        // Define the cell value factory using a lambda expression
        tableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()));

        // Add the table column to the table view
        tableView.getColumns().add(tableColumn);
        tableView.getItems().addAll("Primul", "Al doilea", "Al treilea");
        
        TreeView<String> treeView = new TreeView<>();
        TreeItem<String> rootItem = new TreeItem<>("Radacina");
        treeView.setRoot(rootItem);
        
        TreeItem<String> childItem = new TreeItem<>("Copil");
        rootItem.getChildren().add(childItem);

        
        TreeTableColumn<String, String> column = new TreeTableColumn<>("Coloana");
        TreeTableView<String> treeTableView = new TreeTableView<>(rootItem);
        treeTableView.getColumns().add(column);
        
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll("no-ssh", "partially-ssh", "full-ssh");
        comboBox.setValue("Alege tipul de partitie");
        
        Separator separator = new Separator();
         
        Slider slider = new Slider();
        ProgressBar progressBar = new ProgressBar();
        ProgressIndicator progressIndicator = new ProgressIndicator();
        Hyperlink hyperlink = new Hyperlink("curs.upb.ro");
        Tooltip tooltip = new Tooltip("Tooltip");
        
        scrollBar.setOnScroll(e -> System.out.println("Se scrolleaza " + scrollBar.getValue()));
        listView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> System.out.println("Un element din lista a fost selectat: " + newValue));
        tableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> System.out.println("Un element din tabel a fost selectat: " + newValue));
        treeView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> System.out.println("Un element din tree a fost selectat " + newValue));
        
        slider.setOnMouseReleased(e -> {System.out.println("Slider value: " + slider.getValue());
        progressBar.setProgress(slider.getValue());

        });
        progressBar.setProgress(0.5);
        progressIndicator.setProgress(0.6);
        hyperlink.setOnAction(e -> System.out.println("Hyperlink clicked"));
        Tooltip.install(label, tooltip);

        // Create layout for the second scene
        VBox secondLayout = new VBox(10);
        secondLayout.setPadding(new Insets(10));
        secondLayout.getChildren().addAll(backButton,label, scrollBarContainer,
                listView, tableView, treeView, treeTableView, comboBox, separator, slider, progressBar,
                progressIndicator, hyperlink, scrollPane);

        // Create the second scene
        Scene secondScene = new Scene(secondLayout, 1000, 700);
        
        return secondScene;
    }
    
    private void showAlert(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Alerta");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
