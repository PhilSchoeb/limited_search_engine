import javax.swing.*;

public class Main {

    public static void main(String[] args)
    {
        MainInterface mainInter = new MainInterface();
        mainInter.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainInter.setSize(500, 400);
        mainInter.setLocationRelativeTo(null);
        mainInter.setVisible(true);
    }
}