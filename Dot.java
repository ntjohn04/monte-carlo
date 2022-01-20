import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;

public class Dot extends Group {
	
	public Sphere sphere;
	public boolean in;
	
	public Dot() {
	}
	
	public Dot(double x, double y, double z, boolean in) {

		this.in = in;
		
		sphere = new Sphere(15);
		
		sphere.setTranslateX(x);
		sphere.setTranslateY(y);
		sphere.setTranslateZ(z);
		
		PhongMaterial mat = new PhongMaterial();
		if (in == true) {
			mat.setDiffuseColor(Color.RED);
		}
		else {
			mat.setDiffuseColor(Color.BLUE);
		}
		sphere.setMaterial(mat);
		
		getChildren().addAll(sphere);
		
		if (MonteCarloDriver.disableBlue.isSelected() == true && in == false) {
			sphere.setDisable(true);
			sphere.setVisible(false);
		}
	}
	
	public Sphere getSphere() {
		return this.sphere;
	}
	
	public void toBack() {
		this.sphere.toBack();
	}
	
	
}
