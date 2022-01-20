import java.util.Random;

import org.nfunk.jep.*;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class DotList extends Group {
	
	public static int count = -1;
	
	public static Dot[] dotList;
	
	Random rand = new Random();
	
	Rectangle box;
	Text ratioText;
	
	StackPane pane;
	
	public DotList(int smpCnt) {
		dotList = new Dot[smpCnt];
		for (int i = 0; i < smpCnt; i++) {
			dotList[i] = new Dot();
		}
	}
	
	public void play(int smpCnt, String lBound1, String lBound2, String lBound3, String uBound1, String uBound2, String uBound3, String type) {
		
		double posx;
		double posy;
		double posz;
		
		double inCnt = 0;
		double outCnt = 0;
		
		JEP parseLB1 = new JEP();
		JEP parseLB2 = new JEP();
		JEP parseLB3 = new JEP();
		
		JEP parseUB1 = new JEP();
		JEP parseUB2 = new JEP();
		JEP parseUB3 = new JEP();
		
		parseLB1.addStandardFunctions();
		parseUB1.addStandardFunctions();
		
		parseLB2.addStandardFunctions();
		parseUB2.addStandardFunctions();
		
		parseLB3.addStandardFunctions();
		parseUB3.addStandardFunctions();
		
		for (int i = 0; i < smpCnt; i++) {
			
			posx = rand.nextInt(1001)-500;
			posy = rand.nextInt(1001)-500;
			posz = rand.nextInt(1001)-500;
			
			String tlBound1 = lBound1.replace("y", String.valueOf(posy)).replace("z", String.valueOf(posz)).replace("count", String.valueOf(count));
			String tuBound1 = uBound1.replace("y", String.valueOf(posy)).replace("z", String.valueOf(posz)).replace("count", String.valueOf(count));
			
			String tlBound2 = lBound2.replace("x", String.valueOf(posx)).replace("z", String.valueOf(posz)).replace("count", String.valueOf(count));
			String tuBound2 = uBound2.replace("x", String.valueOf(posx)).replace("z", String.valueOf(posz)).replace("count", String.valueOf(count));
			
			String tlBound3 = lBound3.replace("x", String.valueOf(posx)).replace("y", String.valueOf(posy)).replace("count", String.valueOf(count));
			String tuBound3 = uBound3.replace("x", String.valueOf(posx)).replace("y", String.valueOf(posy)).replace("count", String.valueOf(count));
			
			System.out.println(tuBound2);
			
			parseLB1.parseExpression(tlBound1);
			parseUB1.parseExpression(tuBound1);
			
			parseLB2.parseExpression(tlBound2);
			parseUB2.parseExpression(tuBound2);
			
			parseLB3.parseExpression(tlBound3);
			parseUB3.parseExpression(tuBound3);
			
			double LB1 = parseLB1.getValue();
			double UB1 = parseUB1.getValue();
			
			double LB2 = parseLB2.getValue();
			double UB2 = parseUB2.getValue();
			
			double LB3 = parseLB3.getValue();
			double UB3 = parseUB3.getValue();
			
			System.out.println(UB2);
			System.out.println();
			
			
			if (posx > LB1 && posx < UB1 && posy > LB2 && posy < UB2 && posz > LB3 && posz < UB3) {
				
				addDot(posx+500, posy+500, posz+500, true);
				inCnt++;
				MonteCarloDriver.dotGroup.getChildren().addAll(dotList[count].getSphere());
				dotList[count].getSphere().toBack();
			}
			else {
				addDot(posx+500, posy+500, posz+500, false);
				outCnt++;
				MonteCarloDriver.dotGroup.getChildren().addAll(dotList[count].getSphere());
				dotList[count].getSphere().toBack();
			}
		}
		
		double ratioArea = (double)Math.round(8000*(inCnt/(inCnt+outCnt)))/1000;
		String ratioString = String.valueOf(ratioArea);
		
		box = new Rectangle(300, 100);
		
		
		ratioText = new Text(ratioString);

		//ratioText.setLayoutX(500);
		//ratioText.setLayoutY(150);
		ratioText.setScaleX(5);
		ratioText.setScaleY(5);
		ratioText.setTextAlignment(TextAlignment.CENTER);
		ratioText.setStroke(Color.ALICEBLUE);
		
		pane = new StackPane();
		pane.setLayoutX(350);
		pane.setLayoutY(25);
		pane.setAlignment(Pos.CENTER);
		pane.getChildren().addAll(box, ratioText);
		
		MonteCarloDriver.root.getChildren().addAll(pane);
	}
	
	public void addDot(double x, double y, double z, boolean in) {
		count++;
		dotList[count] = new Dot(x, y, z, in);
	}
}
