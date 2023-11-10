import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
//import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Engin {
    private ListeReponse listeIndex;
    private ListeReponse listeIndexInv;

    public void indexer(String source) throws Exception {
        String[] fichiersTab = source.split("////");
        int nbTextes = fichiersTab.length;
        Triable t = new Triable();
        String[] fichiersTabTri = t.triAlphabet(fichiersTab, 0);
        listeIndex = new ListeReponse();
        String[][] textTabTotal = new String[nbTextes][];
        for (int i = 0; i < nbTextes; i++) {
            String[] textTabNonTri = readText(i, fichiersTabTri);
            String[] textTabTri = t.triAlphabet(textTabNonTri, 0);
            textTabTotal[i] = removeEmpty(textTabTri);
        }
        MotFrequence[][] motFreq = new MotFrequence[nbTextes][];
        for (int i = 0; i < nbTextes; i++) {
            motFreq[i] = formationMotFreq(textTabTotal[i]);
        }
        if (motFreq[0] != null) {
            for (int i = 0; i < nbTextes; i++) {
                File fileToRead = new File(fichiersTabTri[i]);
                listeIndex.ajoutFin(fileToRead.getName());
                listeIndex.ajoutListeHorizontale(listeIndex.trouverIndice(i), motFreq[i][0]);

                if (motFreq[i].length > 1) {
                    for (int j = 1; j < motFreq[i].length; j++) {
                        Noeud n = listeIndex.trouverIndiceHorizontal(i, j + i);
                        listeIndex.ajoutFin(n, motFreq[i][j]);
                    }
                }
            }
        }
    }

    public ListeReponse getListeIndex() {
        return listeIndex;
    }

    public void afficher() throws Exception {
        String message = "Voici la structure d'index\n";
        message += "La liste verticale :\n";
        message += listeIndex.toString() + "\n";
        int nbText = listeIndex.premier.longueurVerticale();
        for (int i = 0; i < nbText; i++) {
            message += "La liste horizontale numéro " + Integer.toString(i+1) + " :\n";
            message += listeIndex.toStringHorizontal(i) + "\n";
        }
        if(listeIndexInv != null){
            message += "\nVoici la structure d'index inversée\n";
            message += "La liste verticale :\n";
            message += listeIndexInv.toString() + "\n";
            int nbMots = listeIndexInv.premier.longueurVerticale();
            for(int i = 0; i < nbMots; i++){
                message += "La liste horizontale numéro " + Integer.toString(i+1) + " :\n";
                message += listeIndexInv.toStringHorizontal(i) + "\n";
            }
        }

        VisualiserFrame myVisual = new VisualiserFrame(message);
        myVisual.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        myVisual.setSize(600, 500);
        myVisual.setLocationRelativeTo(null);
        myVisual.setVisible(true);

    }
    private Noeud findWord (String mot, ListeReponse myList){
        Noeud n = myList.premier;
        //System.out.println((n.valeur.equals(mot)));
        while ( n.prochain != null ){
            if (n.valeur.equals(mot) != true){
                n=n.prochain;
            }
            else{
                break;
            }
        }
        return n;
    }
    // Rechercher liste
    private ListeReponse rechercherListe (String mot, ListeReponse myList){
        Noeud n = findWord(mot, myList);
        ListeReponse rechercherListeListe = new ListeReponse();
        rechercherListeListe.ajoutFin(n.valeur);
        rechercherListeListe.ajoutFin(n.prochainHorizontal.getValeur());
        n = n.prochainHorizontal;
        while( n.prochain != null ){
            rechercherListeListe.ajoutFin(n.prochain.getValeur());
            n = n.prochain;
        }

        return rechercherListeListe;
    }


    //  verifie si un document est deja dans la liste
    private boolean inTheList(String s, ListeReponse liste) {
        boolean isIn = false;
        Noeud n = liste.premier.prochain;
        while (n != null && n.getValeur().getMot().compareTo(s) != 0) {
            n = n.prochain;
        }
        if (n != null) {
            isIn = true;
        }
        return isIn;
    }



    private Noeud trouverValeurMotFreq(String s, ListeReponse liste) {
        Noeud p = liste.premier;
        if(p.getValeur().getMot().compareTo(s) == 0){
            return p;
        }
        p = p.prochain;
        while (p != null && (p.getValeur().getMot().compareTo(s) != 0)) {
            p = p.prochain;
        }
        return p;
    }
    private Noeud trouverValeur(String s, ListeReponse liste) {
        Noeud p = liste.premier;
        if(((String)p.valeur).compareTo(s) == 0){
            return p;
        }
        p = p.prochain;
        while (p != null && (((String)p.valeur).compareTo(s) != 0)) {
            p = p.prochain;
        }
        return p;
    }
    /*
    public ListeReponse rechercher(String requete){

        ListeReponse lrTri;
        String[] motsRequete = requete.split( " ");
        lrTri = rechercherListe(motsRequete[0], listeIndexInv);
        System.out.println(rechercherListe(motsRequete[1], listeIndexInv));
        int i = 1;
        while (i < motsRequete.length && rechercherListe(motsRequete[i], listeIndexInv) != null) {
            String mot = motsRequete[i];
            if (lrTri ==  null) {
                lrTri = rechercherListe(motsRequete[i], listeIndexInv);
            }
            else {
            //Noeud v = lrNonTri.premier.prochain;
                for (int j = 1; j < lrTri.premier.longueurVerticale(); j++) {
                // Créer une nouvelle liste chaînée pour chaque nouveau mot!!!!
                    ListeReponse temp;
                    temp = rechercherListe(mot, listeIndexInv);
                // :))))))) ///
                    System.out.println(j +"j");


                // FUSIONNER
                    Noeud z = temp.premier.prochain;
                    System.out.println("Anneeee");
                    System.out.println(z);
                    System.out.println(temp.premier.longueurVerticale());
                // parcourir la liste temp du nouveau mot de la requete
                    for (int l = 1; l < temp.premier.longueurVerticale(); l++) {

                    // Cas 1 document existe deja

                        if (inTheList(z.getValeur().getMot(), lrTri)) {
                            //System.out.println("Allo");
                            System.out.println(z.getValeur().getMot() + "ici");
                            System.out.println(lrTri + "ici 2");
                            Noeud n = trouverValeur(z.getValeur().getMot(), lrTri);

                            System.out.println(n.getValeur().getFrequence());
                            System.out.println(z.getValeur().getFrequence());
                            int b = n.getValeur().getFrequence() + z.getValeur().getFrequence();
                            System.out.println(b);
                            n.getValeur().setFrequence(b);


                    }

                    // Cas 2 Document existe pas deja
                        else {
                            lrTri.ajoutFin(z);

                        }
                        z = z.prochain;

                    }
                }}
                i++;
            }
            lrTri.premier = lrTri.premier.prochain;
            if (lrTri != null){
                lrTri.quicksort();
            }
            System.out.println(lrTri);
        return lrTri;

    }

     */

    public ListeReponse rechercher(String requete) {
        ListeReponse lr = new ListeReponse();
        String[] motsRequete = requete.split(" ");
        if (motsRequete.length == 0 || requete == null) {
            return lr;
        }
        if (motsRequete.length == 1) {
            Noeud noeudMot = trouverValeur(motsRequete[0], listeIndexInv);
            if (noeudMot != null) {
                lr.premier = noeudMot.prochainHorizontal;
            }
            lr.quicksort();
            return lr;
        }
        // premierMotPresent est l'indice du premier mot de motsRequete
        int premierMotPresent = 0;
        for (int i = 0; i < motsRequete.length; i++){
            Noeud noeudMot = trouverValeur(motsRequete[i], listeIndexInv);
            if (noeudMot != null) {
                lr.premier = noeudMot.copyList(noeudMot.prochainHorizontal);
                //lr.premier = noeudMot.copySansHorizontal(noeudMot.prochainHorizontal);
                break;
            }
            premierMotPresent = i+1;
        }

        // Cas aucun mot recherché n'est présent.
        if (premierMotPresent == motsRequete.length){
            return lr;
        }
        // Cas seul mot présent est le dernier.
        if (premierMotPresent == motsRequete.length - 1){
            lr.quicksort();
            return lr;
        }
        System.out.println(lr.toString());

        for (int i = premierMotPresent + 1; i < motsRequete.length; i++){
            Noeud noeudProchainMot = trouverValeur(motsRequete[i], listeIndexInv);
            if (noeudProchainMot == null){
                continue;
            }
            Noeud noeudPremierDoc = noeudProchainMot.prochainHorizontal;
            int nbDocuments = noeudProchainMot.prochainHorizontal.longueurVerticale();
            for (int j = 0; j < nbDocuments; j++){
                //System.out.println(lr.toString());
                Noeud noeudDoc = trouverValeurMotFreq(noeudPremierDoc.getValeur().getMot(), lr);
                if (noeudDoc == null){
                    MotFrequence mf = new MotFrequence(noeudPremierDoc.getValeur().getMot(), noeudPremierDoc.getValeur().getFrequence());
                    //Noeud nouveauNoeud = new Noeud(mf, null);
                    //System.out.println(nouveauNoeud.toString());
                    lr.ajoutFin(mf);
                }
                else{
                    int noouvelleFreq = noeudDoc.getValeur().getFrequence() + noeudPremierDoc.getValeur().getFrequence();
                    noeudDoc.getValeur().setFrequence(noouvelleFreq);
                }
                noeudPremierDoc = noeudPremierDoc.prochain;
            }
        }
        System.out.println(lr.toString());
        lr.quicksort();
        //System.out.println(lr.toString());
        return lr;
    }

    // Comme s est en ordre alphabétique, on sait que les textes vides sont au début
    public String[] removeEmpty(String[] s) {
        if (s[0] != "") {
            return s;
        } else {
            return removeEmpty(Arrays.copyOfRange(s, 1, s.length));
        }
    }

    public String[] readText(int i, String[] selectedTextsTri) {
        File fileToRead = new File(selectedTextsTri[i]);
        String text = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileToRead));
            String line = null;

            while ((line = br.readLine()) != null) {
                text =text + " " + line;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            Pattern pat = Pattern.compile("[a-zA-Z]+");
            Matcher matcher = pat.matcher(text);
            ArrayList<String> textArray = new ArrayList<String>();
            while (matcher.find()) {
                String textSansPonc = matcher.group();
                textArray.add(textSansPonc);
            }

            String[] textTab = new String[textArray.size()];
            for (int j = 0; j < textTab.length; j++) {
                textTab[j] = textArray.get(j);
            }

            return textTab;
        }
    }

    public MotFrequence[] formationMotFreq(String[] s) {
        int nbMotsDiff = nbMotsDiff(s);
        MotFrequence[] motFreq = new MotFrequence[nbMotsDiff];
        for (int i = 0; i < nbMotsDiff; i++) {
            motFreq[i] = new MotFrequence();
        }
        motFreq[0] = new MotFrequence();
        if (s[0] == null) {return null;}
        int indMF = 0;
        motFreq[0].setMot(s[0]);
        motFreq[0].setFrequence(1);
        if (s.length > 1) {
            for (int i = 1; i < s.length; i++) {
                if (s[i].compareTo(s[i - 1]) == 0) {
                    motFreq[indMF].setFrequence(motFreq[indMF].getFrequence() + 1);
                } else {
                    indMF += 1;

                    motFreq[indMF].setMot(s[i]);
                    motFreq[indMF].setFrequence(1);
                }
            }
        }
        return motFreq;
    }

    public int nbMotsDiff(String[] s) {
        int nb = 0;
        if (s.length == 0) {
            return 0;
        } else if (s.length == 1) {
            return 1;
        } else {
            nb = 1;
            for (int i = 1; i < s.length; i++) {
                if (s[i].compareTo(s[i - 1]) == 0) {
                    continue;
                } else {
                    nb += 1;
                }
            }
        }
        return nb;
    }

    public void indexerInv() throws Exception{
        int nbTextes = listeIndex.premier.longueurVerticale();
        listeIndexInv = new ListeReponse();
        for (int i = 0; i < nbTextes; i++) {
            Noeud texte = listeIndex.trouverIndice(i);
            int nbMots = texte.prochainHorizontal.longueurVerticale(); // +1 maybe
            for (int j = 0; j < nbMots; j++) {
                Noeud motFreq = listeIndex.trouverIndiceHorizontal(i, j + i + 1);
                String mot = motFreq.getValeur().getMot();
                int freqMot = motFreq.getValeur().getFrequence();
                MotFrequence docFreq = new MotFrequence((String) texte.valeur, freqMot);
                Noeud noeudMot = motPresent(mot);
                if (noeudMot.prochainHorizontal == null) {
                    noeudMot.setProchainHorizontal(new Noeud(docFreq, null));
                } else {
                    listeIndexInv.ajoutFin(noeudMot.prochainHorizontal, docFreq);
                }
            }
        }

    }

    public Noeud motPresent(String mot) {
        if (listeIndexInv.premier == null) {
            listeIndexInv.premier = new Noeud(mot, null);
            return listeIndexInv.premier;
        }
        Noeud n = listeIndexInv.premier;
        if (mot.compareTo((String) n.valeur) == 0) {
            return n;
        }
        for (int i = 0; i < listeIndexInv.premier.longueurVerticale(); i++) {
            if (n.prochain == null) {
                Noeud nouveauMot = new Noeud(mot, null);
                n.prochain = nouveauMot;
                return n.prochain;
            } else if (mot.compareTo((String) n.prochain.valeur) == 0) {
                return n.prochain;
            } else if (mot.compareTo((String) n.prochain.valeur) < 0) {
                Noeud nouveauMot = new Noeud(mot, n.prochain);
                n.prochain = nouveauMot;
                return n.prochain;
            } else {
                n = n.prochain;
            }
        }
        return null;
    }
}