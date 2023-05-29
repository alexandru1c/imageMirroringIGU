package application;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
		
		
    			
        primaryStage.setTitle("JavaFX App");
        
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("BMP Files", "*.bmp"));

        ImageView imageView = new ImageView();
        ImageView imageView2 = new ImageView();


        Button uploadButton = new Button("Upload BMP");
        Button buttonTransform = new Button("Transform");
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


        RadioButton radioButton = new RadioButton("Enable random ratio");
        ToggleButton toggleButton = new ToggleButton("I agree to the Terms and Conditions.");
        CheckBox checkBox = new CheckBox("Terms Agreed");
        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        choiceBox.getItems().addAll("1 copy", "2 copies", "3 copies");
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
        radioButton.setOnAction(e -> showAlert("Random ratio enabled"));
        toggleButton.setOnAction(e -> {checkBox.setSelected(checkBox.isSelected() ? false : true );
         
        });
        checkBox.setOnAction(e -> System.out.println("Checkbox selected: " + checkBox.isSelected()));
        choiceBox.setOnAction(e -> System.out.println("Choice selected: " + choiceBox.getValue()));
        textField.setOnAction(e -> System.out.println("Text entered: " + textField.getText()));
        passwordField.setOnAction(e -> System.out.println("Password entered: " + passwordField.getText()));
        

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
        Button backButton = new Button("Back to Main Scene");
        backButton.setOnAction(e -> {
            // Switch back to the main scene
            primaryStage.setScene(mainScene);
        });
        Label label = new Label("Label");
        ScrollBar scrollBar = new ScrollBar();
        ScrollPane scrollPane = new ScrollPane();
        ListView<String> listView = new ListView<>();
        listView.getItems().addAll("Item 1", "Item 2", "Item 3");
        TableView<String> tableView = new TableView<>();
        tableView.getItems().addAll("Item 1", "Item 2", "Item 3");
        TreeView<String> treeView = new TreeView<>();
        TreeItem<String> rootItem = new TreeItem<>("Root");
        treeView.setRoot(rootItem);
        TreeTableColumn<String, String> column = new TreeTableColumn<>("Column");
        TreeTableView<String> treeTableView = new TreeTableView<>(rootItem);
        treeTableView.getColumns().add(column);
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll("Item 1", "Item 2", "Item 3");
        Separator separator = new Separator();
        Slider slider = new Slider();
        ProgressBar progressBar = new ProgressBar();
        ProgressIndicator progressIndicator = new ProgressIndicator();
        Hyperlink hyperlink = new Hyperlink("Hyperlink");
        Tooltip tooltip = new Tooltip("Tooltip");
        
        scrollBar.setOnScroll(e -> System.out.println("Scrolling: " + scrollBar.getValue()));
        listView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> System.out.println("List View item selected: " + newValue));
        tableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> System.out.println("Table View item selected: " + newValue));
        treeView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> System.out.println("Tree View item selected: " + newValue));
        comboBox.setOnAction(e -> System.out.println("Combo Box selected: " + comboBox.getValue()));
        slider.setOnMouseReleased(e -> System.out.println("Slider value: " + slider.getValue()));
        progressBar.setProgress(0.5);
        progressIndicator.setProgress(0.6);
        hyperlink.setOnAction(e -> System.out.println("Hyperlink clicked"));
        Tooltip.install(label, tooltip);

        // Create layout for the second scene
        VBox secondLayout = new VBox(10);
        secondLayout.setPadding(new Insets(10));
        secondLayout.getChildren().addAll(backButton, scrollBar,
                scrollPane, listView, tableView, treeView, treeTableView, comboBox, separator, slider, progressBar,
                progressIndicator, hyperlink);

        // Create the second scene
        Scene secondScene = new Scene(secondLayout, 1000, 700);
        
        return secondScene;
    }
    
    private void showAlert(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
