import org.nfunk.jep.*;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

public class MonteCarloDriver extends Application {
	
	//camera
	PerspectiveCamera cam = new PerspectiveCamera();
	Translate pivot = new Translate();
	Rotate yRotate = new Rotate(0, Rotate.Y_AXIS);
	
	//Cartesian bounds
	public TextField xLowbnd;
	public TextField xUppbnd;
	public TextField yLowbnd;
	public TextField yUppbnd;
	public TextField zLowbnd;
	public TextField zUppbnd;
	
	//polar bounds
	public TextField rLowbnd;
	public TextField rUppbnd;
	
	public TextField thetaLowbnd;
	public TextField thetaUppbnd;
	
	public TextField hLowbnd;
	public TextField hUppbnd;
	
	//spherical bounds
	public TextField rhoLowbnd;
	public TextField rhoUppbnd;
	
	public TextField chiLowbnd;
	public TextField chiUppbnd;
	
	public TextField phiLowbnd;
	public TextField phiUppbnd;
	
	public GridPane fieldGridCartesian;
	
	//bounds selection
	RadioButton cartesianCords = new RadioButton("Cartesian Bounds");
	RadioButton polarCords = new RadioButton("Polar Bounds");
	RadioButton sphericalCords = new RadioButton("Spherical Bounds");
	
	public ToggleGroup radioToggleGroup = new ToggleGroup();
	
	//interface
	public Button playBut;
	public Button resetBut;
	
	public TextField monteCount;
	
	public static CheckBox disableBlue;
	
	DotList dots;
	
	public static Group root;
	
	public static Group dotGroup;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		
		disableBlue = new CheckBox("Disable Outside Dots");
		disableBlue.setOnAction(this::processDisableBlue);
		
		playBut = new Button("Play");
		playBut.setOnAction(this::processPlayBut);
		resetBut = new Button("Reset");
		resetBut.setOnAction(this::processResetBut);
		resetBut.setDisable(true);
		
		monteCount = new TextField("Sample Count");
		monteCount.setOnMouseClicked(this::processTextFieldClick);
		
		xLowbnd = new TextField("X Lower");
		xLowbnd.setOnMouseClicked(this::processTextFieldClick);
		xUppbnd = new TextField("X Upper");
		xUppbnd.setOnMouseClicked(this::processTextFieldClick);
		
		yLowbnd = new TextField("Y Lower");
		yLowbnd.setOnMouseClicked(this::processTextFieldClick);
		yUppbnd = new TextField("Y Upper");
		yUppbnd.setOnMouseClicked(this::processTextFieldClick);
		
		zLowbnd = new TextField("Z Lower");
		zLowbnd.setOnMouseClicked(this::processTextFieldClick);
		zUppbnd = new TextField("Z Upper");
		zUppbnd.setOnMouseClicked(this::processTextFieldClick);
		
		fieldGridCartesian = new GridPane();
		fieldGridCartesian.addRow(0, xLowbnd, xUppbnd);
		fieldGridCartesian.addRow(1, yLowbnd, yUppbnd);
		fieldGridCartesian.addRow(2, zLowbnd, zUppbnd);
		fieldGridCartesian.setLayoutX(700);
		
		cartesianCords.setSelected(true);
		
		cartesianCords.setToggleGroup(radioToggleGroup);
		polarCords.setToggleGroup(radioToggleGroup);
		sphericalCords.setToggleGroup(radioToggleGroup);
		
		cartesianCords.setOnAction(this::processCartesianCords);
		polarCords.setOnAction(this::processPolarCords);
		sphericalCords.setOnAction(this::processSphericalCords);
		
		HBox buttons = new HBox(playBut, resetBut);
		
		VBox interfaceLeft = new VBox(buttons, cartesianCords, polarCords, sphericalCords, monteCount, disableBlue);
		
		dotGroup = new Group();
		//dotGroup.setRotationAxis(new Point3D(0, 1, 0));
		
		root = new Group(interfaceLeft, fieldGridCartesian, dotGroup);

		Scene scene = new Scene(root, 1000, 1000);
		scene.setCamera(cam);
		scene.setOnScroll(this::processScroll);
		
		primaryStage.setTitle("MonteCarlo Calculator");
		primaryStage.setScene(scene);
		primaryStage.setResizable(true);
		primaryStage.show();

	}
	
	public void processScroll(ScrollEvent event) {
		dotGroup.getTransforms().addAll(
			new Rotate(10, 500, 0, 500, Rotate.Y_AXIS)
		);
		
	}
	
	public void processDisableBlue(ActionEvent event) {
		if (disableBlue.isSelected() == true) {
			for (int i = 0; i <= dots.count; i++) {
				if (dots.dotList[i].in == false) dots.dotList[i].sphere.setVisible(false);
			}
		}
		
		if (disableBlue.isSelected() == false) {
			for (int i = 0; i <= dots.count; i++) {
				if (dots.dotList[i].in == false) dots.dotList[i].sphere.setVisible(true);
			}
		}
	}
	
	public void processPlayBut(ActionEvent event) {
		xLowbnd.setDisable(true);
		xUppbnd.setDisable(true);
		yLowbnd.setDisable(true);
		yUppbnd.setDisable(true);
		zLowbnd.setDisable(true);
		zUppbnd.setDisable(true);
		
		int smpCnt = Integer.parseInt(monteCount.getText());
		
		dots = new DotList(smpCnt);
		
		if (cartesianCords.isSelected()) {
			String lBound1 = xLowbnd.getText();
			String lBound2 = yLowbnd.getText();
			String lBound3 = zLowbnd.getText();
		
			String uBound1 = xUppbnd.getText();
			String uBound2 = yUppbnd.getText();
			String uBound3 = zUppbnd.getText();
			
			dots.play(smpCnt, lBound1, lBound2, lBound3, uBound1, uBound2, uBound3, "Cartesian");
		}
		
		playBut.setDisable(true);
		resetBut.setDisable(false);
	}
	
	public void processResetBut(ActionEvent event) {
		xLowbnd.setDisable(false);
		xUppbnd.setDisable(false);
		yLowbnd.setDisable(false);
		yUppbnd.setDisable(false);
		zLowbnd.setDisable(false);
		zUppbnd.setDisable(false);
		
		for (int i = 0; i <= dots.count; i++) {
			dots.dotList[i].sphere.setVisible(false);
			dots.dotList[i].sphere.setDisable(true);
		}
		
		dots.box.setDisable(true);
		dots.box.setVisible(false);
		
		dots.ratioText.setDisable(true);
		dots.ratioText.setVisible(false);
		
		dots.count = -1;
		
		playBut.setDisable(false);
		
	}
	
	public void processCartesianCords(ActionEvent event) {

	}
	
	public void processPolarCords(ActionEvent event) {
		
	}

	public void processSphericalCords(ActionEvent event) {
	
	}
	
	public void processTextFieldClick(MouseEvent event) {
		((TextField) event.getSource()).selectAll();
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}

}
