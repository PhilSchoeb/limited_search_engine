import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
public class MainInterface extends JFrame implements ActionListener{
    private  JFileChooser myFileChooser;
    private final JButton selectFileButton, searchButton, indexButton, visualizeButton;
    private final JPopupMenu selectionPopMenu;
    private final JMenuItem selectFichier, selectDirectory;
    private final JScrollPane scrollMe;
    private JTextArea showMeTextArea;
    protected ArrayList<File> arrayFichier;
    protected Engin myEngine = new Engin();
    public MainInterface(){

        // Initialiser les Panels
        JPanel down = new JPanel();
        JPanel up = new JPanel();
        up.setPreferredSize(new Dimension(500, 320));
        down.setPreferredSize(new Dimension(500,50));
        this.add(up, "Center");
        this.add(down, "South");


        // Initialiser les variables
        this.arrayFichier = new ArrayList<File>();
        this.myFileChooser = new JFileChooser();

        this.selectFileButton = new JButton("Sélectionner");
        this.searchButton = new JButton("Rechercher");
        this.indexButton = new JButton("Indexation");
        this.visualizeButton = new JButton("Visualiser");

        //this.indexPopMenu = new JPopupMenu("Inverser");
        this.selectionPopMenu = new JPopupMenu("Sélectionner");

        //this.indexText = new JMenuItem("Index");
        //this.inverseIndexText = new JMenuItem("Inverse Index");
        this.selectFichier = new JMenuItem("Fichier");
        this.selectDirectory = new JMenuItem(("Directory"));

        initiateTextArea();
        up.add(this.showMeTextArea);
        scrollMe = new JScrollPane(showMeTextArea);
        getContentPane().add(scrollMe);

        // Faire la gestion du Layout
        down.setLayout(new FlowLayout(FlowLayout.CENTER));
        down.setBackground(Color.white);
        up.setBackground(Color.white);

        //this.indexPopMenu.add(this.indexText);
        //this.indexPopMenu.add(this.inverseIndexText);
        this.selectionPopMenu.add(this.selectFichier);
        this.selectionPopMenu.add(this.selectDirectory);

        down.add(this.selectFileButton);
        down.add(this.indexButton);
        down.add(this.searchButton);
        down.add(this.visualizeButton);

        addMyListeners();
        addTextInfo();
    }

    /**
     * Ajoute les messages informatifs qui s'affichent lorsque l'utilisateur survole une composante
     */
    public void addTextInfo(){
        this.selectFileButton.setToolTipText("Sélectionner les fichiers de texte à indexer.");
        this.indexButton.setToolTipText("Lancer les conversion possible de l’index");
        this.searchButton.setToolTipText("Lancer une recherche");
        this.visualizeButton.setToolTipText("Visualiser la structure de donnée");
    }

    /**
     * initiateTextArea initie l'ensemble des composantes reliées à la variable showMeTextArea
     */
    public void initiateTextArea(){
        this.showMeTextArea = new JTextArea();
        this.showMeTextArea.setLineWrap(true);
        this.showMeTextArea.setEditable(false);
    }

    /**
     * Permet le fonctionnement du menu déroulant pour choisir le type d'indexion
     */


    /**
     * Permet le fonctionnement du menu déroulant pour choisir le type de sélection de fichier
     */
    public void popTheMenuFile(){
        this.selectionPopMenu.show(this.selectFileButton,0,selectFileButton.getHeight());
    }

    /**
     * Fait apparaître un message de confirmation selon la commande de l'utilisateur
     * @param message: Énoncé devant être validé par l'utilisateur
     * @return L'équivalence booléan de la réponse de l'utilisateur
     */
    public int popUpValidation(String message){
        return JOptionPane.showConfirmDialog(this, message,
                "Valider l'indexation", JOptionPane.YES_NO_OPTION);
    }

    /**
     * Permet l'extraction du fichier de la varaible JFileChooser
     * @return Le fichier sélectionné par l'utilisateur
     */
    public File getMyDirectory(boolean isDir) {
        myFileChooser = new JFileChooser();
        myFileChooser.setDialogTitle("Sélectionner un fichier");
        myFileChooser.setCurrentDirectory(new File("."));
        if(isDir){myFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);}
        myFileChooser.showOpenDialog(this);
        return myFileChooser.getSelectedFile();
    }

    /**
     * Ajoute des ActionListener sur les composantes pertinentes
     */
    public void addMyListeners(){
        this.selectFileButton.addActionListener(this);
        this.searchButton.addActionListener(this);
        //this.indexText.addActionListener(this);
        //this.inverseIndexText.addActionListener(this);
        this.indexButton.addActionListener(this);
        this.visualizeButton.addActionListener(this);
        this.selectFichier.addActionListener(this);
        this.selectDirectory.addActionListener(this);
    }

    /**
     * Visualiser les données de stockage
     */
    public void visualizeData() {
        try{
            myEngine.afficher();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    /*public Object getOptionToVisualize(){
        JPanel panel = new JPanel(new GridBagLayout());
        Object[] optionToVisualize = {"Par mot", "Par document"};
        JComboBox<Object> selectVisual = new JComboBox<Object>(optionToVisualize);
        selectVisual.setSelectedIndex(1);
        Object selectedOption = selectVisual.getSelectedItem();
        JOptionPane.showConfirmDialog(this, selectVisual,
                "Sélectionner l'élément à visualiser", JOptionPane.OK_CANCEL_OPTION);
        panel.add(selectVisual);
        return selectVisual.getSelectedItem();
    }*/

    /**
     * Fait la gestion des events
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == this.selectFileButton){
            popTheMenuFile();
        }
        if(e.getSource() == this.selectDirectory){

            try{
                File myFile = getMyDirectory(true);
                File[] filesInDirectory = myFile.listFiles();
                for(File i: filesInDirectory){
                    this.showMeTextArea.append(i.getName() + "\n");
                    this.arrayFichier.add(i);
                }
            } catch (NullPointerException myExcept){

            }

        } else if (e.getSource() == this.selectFichier) {

            try{
                File myFile = getMyDirectory(false);
                this.showMeTextArea.append(myFile.getName() + "\n");
                this.arrayFichier.add(myFile);
            } catch (NullPointerException myExcept){

            }
        }
        if (e.getSource() == this.indexButton) {
            int response = popUpValidation("Désirez-vous continuer vers l'indexation ?");
            if(response == JOptionPane.YES_OPTION){
                int nbFichiers = arrayFichier.size();

                // On doit changer le format des fichiers vers un String
                String fichiers = "";
                for (int i = 0; i < arrayFichier.size(); i++){
                    fichiers += arrayFichier.get(i).toString();
                    if(i != arrayFichier.size() - 1){
                        fichiers += "////";
                    }}
                try{
                    myEngine.indexer(fichiers);
                    myEngine.indexerInv();
                }catch(Exception exception){
                    System.out.println(exception.getMessage());
                }

            }


        } else if (e.getSource() == this.searchButton) {
            if(!arrayFichier.isEmpty()){
                String input = JOptionPane.showInputDialog(this,
                        "Entrez les termes de votre recherche");
                ListeReponse lr = new ListeReponse();
                if (input != null){
                    lr = myEngine.rechercher(input);
                    VisualiserFrame myVisual = new VisualiserFrame(lr.toString());
                    myVisual.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                    myVisual.setSize(600, 500);
                    myVisual.setLocationRelativeTo(null);
                    myVisual.setVisible(true);
                }
            }
            else {
                JOptionPane.showMessageDialog(this,"Vous devez entrer des fichiers avant d'effectuer la recherche.");
            }


        } else if (e.getSource() == this.visualizeButton) {
            visualizeData();
        }
    }
}

