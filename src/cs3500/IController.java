package cs3500;

public interface IController {

  void playAnimation(IAnimation a, String viewType, int tempo);

  void handleButtonClick(int x, int y);

  void changeSpeed(String type);

  boolean getPaused();

  void setPaused();

}
