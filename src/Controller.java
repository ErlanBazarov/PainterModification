import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class Controller {
  @FXML
  private Pane drawinPane;

  @FXML
  private Rectangle colorRectange;

  @FXML
  private Slider redSlider;

  @FXML
  private Slider greenSlider;

  @FXML
  private Slider blueSlider;

  @FXML
  private Slider alphaSlider;

  @FXML
  private TextField redTextField;

  @FXML
  private TextField greenTextField;

  @FXML
  private TextField blueTextField;

  @FXML
  private TextField alphaTextField;

  @FXML
  private RadioButton small;

  @FXML
  private ToggleGroup sizeToggleGroup;

  @FXML
  private RadioButton medium;

  @FXML
  private RadioButton large;

  @FXML
  private Button undo;

  @FXML
  private Button clear;

  private enum PenSize {
    SMALL(2),
    MEDIUM(4),
    LARGE(6);

    private final int radius;

    PenSize(int radius) {this.radius = radius;}

    public int getRadius() {return radius;}
  }

  private PenSize radius = PenSize.MEDIUM;
  private int red = 0;
  private int green = 0;
  private int blue = 0;
  private double alpha = 1f;
  private Color brushColor = Color.rgb(red, green, blue, alpha);

  public void initialize() {
    small.setUserData(PenSize.SMALL);
    medium.setUserData(PenSize.MEDIUM);
    large.setUserData(PenSize.LARGE);

    redTextField.textProperty().bind(redSlider.valueProperty().asString("%.0f"));
    greenTextField.textProperty().bind(greenSlider.valueProperty().asString("%.0f"));
    blueTextField.textProperty().bind(blueSlider.valueProperty().asString("%.0f"));
    alphaTextField.textProperty().bind(alphaSlider.valueProperty().asString("%.2f"));

    redSlider.valueProperty().addListener(
      new ChangeListener<Number>(){
        @Override
        public void changed(javafx.beans.value.ObservableValue<? extends Number> arg0, 
          Number oldValue, Number newValue) {
          red = newValue.intValue();
          brushColor = Color.rgb(red, green, blue, alpha);
          System.out.println(red);
          colorRectange.setFill(brushColor);
        };
      }
    );
    greenSlider.valueProperty().addListener(
      new ChangeListener<Number>(){
        @Override
        public void changed(javafx.beans.value.ObservableValue<? extends Number> arg0, 
          Number oldValue, Number newValue) {
          green = newValue.intValue();
          brushColor = Color.rgb(red, green, blue, alpha);
          colorRectange.setFill(brushColor);
        };
      }
    );
    blueSlider.valueProperty().addListener(
      new ChangeListener<Number>(){
        @Override
        public void changed(javafx.beans.value.ObservableValue<? extends Number> arg0, 
          Number oldValue, Number newValue) {
          blue = newValue.intValue();
          brushColor = Color.rgb(red, green, blue, alpha);
          colorRectange.setFill(brushColor);
        };
      }
    );
    alphaSlider.valueProperty().addListener(
      new ChangeListener<Number>(){
        @Override
        public void changed(javafx.beans.value.ObservableValue<? extends Number> arg0, 
          Number oldValue, Number newValue) {
          alpha = newValue.floatValue();
          brushColor = Color.rgb(red, green, blue, alpha);
          colorRectange.setFill(brushColor);
        };
      }
    );
  }

  @FXML
  void onMouseDragged(MouseEvent event) {
    Circle circle = new Circle(event.getX(), event.getY(), radius.getRadius(), brushColor);
    drawinPane.getChildren().add(circle);
  }

  @FXML
  void clearButtonClicked(MouseEvent event) {
    drawinPane.getChildren().clear();
  }

  @FXML
  void sizeButtonClicked(MouseEvent event) {
    radius = (PenSize) sizeToggleGroup.getSelectedToggle().getUserData();
  }

  @FXML
  void undoButtonClicked(MouseEvent event) {
    int size = drawinPane.getChildren().size();
    if (size > 0)
      drawinPane.getChildren().remove(size - 1);
  }


}
