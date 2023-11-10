public class Noeud extends Object{
    Object valeur;
    Noeud prochain;

    Noeud prochainHorizontal;

    public Noeud(Object v, Noeud p)
    {
        valeur = v;
        prochain = p;
        prochainHorizontal = null;
    }

    public MotFrequence getValeur()
    { return (MotFrequence) valeur; }

    public int longueurVerticale()
    {
        if (prochain == null) return 1;
        else return 1 + prochain.longueurVerticale();
    }
    public void setProchainHorizontal(Noeud prochainHorizontal){
        this.prochainHorizontal = prochainHorizontal;
    }
    public Noeud copySansHorizontal(Noeud n){
        if(n == null){
            return null;
        }
        Noeud noeudCopy = new Noeud(n.valeur, copySansHorizontal(n.prochain));
        return noeudCopy;
    }
    public Noeud copyList(Noeud n){
        ListeReponse lp = new ListeReponse();
        while(n != null){
            try{
                Object newValeur = ((MotFrequence)n.valeur).clone();
                lp.ajoutFin(newValeur);
            }catch(CloneNotSupportedException e){
                System.out.println(e.getMessage());
            }
            n = n.prochain;
        }
        return lp.premier;
    }
}
