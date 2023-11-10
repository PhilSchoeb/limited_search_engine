public class MotFrequence implements Cloneable{
    private String mot;
    private int frequence;
    public MotFrequence(){
        mot = "";
        frequence = 0;
    }
    public MotFrequence(String mot, int frequence){
        this.mot = mot;
        this.frequence = frequence;
    }
    public String getMot(){
        return(mot);
    }
    public int getFrequence(){
        return(frequence);
    }
    public void setMot(String mot){
        this.mot = mot;
    }
    public void setFrequence(int frequence){
        this.frequence = frequence;
    }

    public void print(){
        System.out.println("(" + mot + " : " + frequence + ")");
    }
    @Override
    public String toString(){
        String message = "(" + mot + " : " + Integer.toString(frequence) + ")";
        return message;
    }
    public Object clone() throws CloneNotSupportedException{
        return super.clone();
    }
}