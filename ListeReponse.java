public class ListeReponse {
    Noeud premier;
    public void concat(ListeReponse l)
    {
        if (premier == null)
        {
            premier = l.premier;
            return;
        }
        Noeud n = premier;
        while (n.prochain != null) n=n.prochain;
        n.prochain = l.premier;
    }
    public void enlever(int v)
    {
        Noeud n = premier;
        if (premier == null) return;
        if (premier.getValeur().getFrequence() == v)
        {
            premier = premier.prochain;
            return;
        }
        while (n.prochain != null && n.prochain.getValeur().getFrequence() != v) n = n.prochain;
        if (n.prochain != null) n.prochain = n.prochain.prochain;
    }

    // quicksort() est une méthode pour trier une liste chainée de MotFrequence en ordre
    // décroissant selon les frequences
    public void quicksort() {
        if (premier == null) return;
        if (premier.prochain == null)
            return;
        MotFrequence pivot = premier.getValeur();
        this.enlever(pivot.getFrequence());
        ListeReponse l1 = new ListeReponse();
        ListeReponse l2 = new ListeReponse();
        this.separer(pivot.getFrequence(), l1, l2);
        l1.quicksort();
        l2.quicksort();
        premier = l1.premier;
        this.ajoutFin(pivot);
        this.concat(l2);
    }

    private void separer(int pivot, ListeReponse l1,
                         ListeReponse l2) {
        Noeud n = premier;
        while (n!=null) {
            if (n.getValeur().getFrequence() > pivot)
                l1.ajoutFin(n.valeur);
            else l2.ajoutFin(n.valeur);
            n=n.prochain;
        }
    }

    public void ajoutFin(Object v) {
        // cas 1: aucun autre élément ajouté
        if (premier == null) premier = new Noeud(v,null);
            // cas 2: il y a déjà des éléments
        else ajoutFin(premier, v);
    }
    public void ajoutFin(Noeud n, Object v) {
        if (n.prochain == null) n.prochain = new Noeud(v, null);
        else ajoutFin(n.prochain, v);
    }
    // Pour mettre un prochain horizontal au noeud n de valeur v.
    public void ajoutListeHorizontale(Noeud n, Object v){
        n.setProchainHorizontal(new Noeud(v, null));
    }
    public void print()
    {
        Noeud n = premier;
        while (n!=null)
        {
            System.out.print(n.valeur + "->");
            n = n.prochain;
        }
        System.out.println("null");
    }


    public String toString()
    {
        String listeString = "";
        Noeud n = premier;
        while (n!=null)
        {
            listeString += (n.valeur + "->");
            n = n.prochain;
        }
        listeString += ("null");
        return listeString;
    }
    // Méthode pour afficher la liste chainée horizontale d'index vertical i (de 0 à nbTextes)
    public void printHorizontal(int i){
        int combienNoeud = 0;
        Noeud n = premier;
        while (n!=null)
        {
            if (combienNoeud >= i){
                System.out.print(n.valeur.toString() + "->");
            }
            if(i == combienNoeud){
                n = n.prochainHorizontal;
                combienNoeud ++;
            }
            else{
                n = n.prochain;
                combienNoeud ++;
            }
        }
        System.out.println("null");
    }

    public String toStringHorizontal(int i){
        String listeString = "";
        int combienNoeud = 0;
        Noeud n = premier;
        while (n!=null)
        {
            if(combienNoeud >= i) {
                listeString += (n.valeur.toString() + "->");
            }
            if(i == combienNoeud){
                n = n.prochainHorizontal;
            }
            else{
                n = n.prochain;
            }
            combienNoeud ++;
        }
        listeString += ("null");
        return listeString;
    }
    // Pour avoir accès au noeud indice i de la liste vertical
    public Noeud trouverIndice(int i){
        Noeud n = premier;
        for (int j = 0; j < i; j++) {
            n = n.prochain;
        }
        return n;
    }
    // Pour avoir accès au noeud indice j de la liste en passant par le prochain horizontal du noeud indice i
    // Attention, pour avoir accès au 4e motFreq du 7e texte, i = 7 - 1 = 6 et j = (4 - 1) + i = 9 !!!
    public Noeud trouverIndiceHorizontal(int i, int j){
        Noeud n = premier;
        for (int k = 0; k < j; k++) {
            if (k == i) {
                n = n.prochainHorizontal;
            }
            else {
                n = n.prochain;
            }
        }
        return n;
    }

}


