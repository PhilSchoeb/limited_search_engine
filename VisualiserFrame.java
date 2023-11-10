import javax.swing.*;
import java.awt.*;
// frame de base qui va aller sous l'interface de Interface

public class VisualiserFrame extends JFrame{
    private final JScrollPane visualScroll;
    private JTextArea visualTextArea;
    private final JButton returnButton;
    private final JScrollPane scrollyScroll;
    public VisualiserFrame(String text){

        this.setTitle("Visualiser les données");

        // Initialiser les Panels
        JPanel visualPanel = new JPanel();
        JPanel buttonPanel = new JPanel();
        visualPanel.setPreferredSize(new Dimension(600,450));
        buttonPanel.setPreferredSize(new Dimension(600,50));
        visualPanel.setBackground(Color.white);
        buttonPanel.setBackground(Color.white);

        this.add(visualPanel,"Center");
        this.add(buttonPanel,"South");

        // Initialiser le TextArea

        this.visualTextArea = new JTextArea(text);
        this.visualTextArea.setEditable(false);
        this.visualScroll = new JScrollPane(this.visualTextArea);
        scrollyScroll = new JScrollPane(this.visualTextArea);
        getContentPane().add(scrollyScroll);

        this.returnButton = new JButton("Return");
        this.returnButton.addActionListener(e -> {this.dispose();});
        buttonPanel.add(this.returnButton);

    }
    public void setTextArea(String t){
        this.visualTextArea.setText(t);
    }
}