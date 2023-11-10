import java.io.File;

public class Triable {
    public String[] triAlphabet(String[] tab, int startIndex){
        if ( startIndex >= tab.length - 1 )
            return tab;
// Trouver min
        int minIndex = minAlphabet(tab,startIndex);
// Swap
        tab = (String[]) swap(tab, startIndex, minIndex);
// Récursion
        return(triAlphabet(tab, startIndex + 1));
    }
    public File[] triAlphabet(File[] tab, int startIndex){
        if ( startIndex >= tab.length - 1 )
            return tab;
// Trouver min
        int minIndex = minFile(tab,startIndex);
// Swap
        tab = (File[])swap(tab, startIndex, minIndex);
// Récursion
        return(triAlphabet(tab, startIndex + 1));
    }

    public int[] triNombre(int[] tab, int startIndex){
        if ( startIndex >= tab.length - 1 )
            return tab;
// Trouver min
        int minIndex = minNombre(tab,startIndex);
// Swap
        tab = swap( tab, startIndex, minIndex);
// Récursion
        return(triNombre(tab, startIndex + 1));
    }

    private int minNombre(int[] tab, int startIndex) {
        int minReste;
        if (startIndex >= tab.length - 1)
            return startIndex;
// trouver min pour le reste
        minReste = minNombre(tab, startIndex + 1);
// choisir entre startIndex et minReste
        if (tab[startIndex] < tab[minReste]) {
            return startIndex;
        } else return minReste;
    }
    private int minFile(File[] tab, int startIndex){
        int minReste;
        if (startIndex >= tab.length - 1)
            return startIndex;
// trouver min pour le reste
        minReste = minFile(tab, startIndex + 1);
// choisir entre startIndex et minReste
        if (tab[startIndex].getName().compareTo(tab[minReste].getName()) < 0) {
            return startIndex;
        } else return minReste;
    }
    private int minAlphabet(String[] tab, int startIndex) {
        int minReste;
        if (startIndex >= tab.length - 1)
            return startIndex;
// trouver min pour le reste
        minReste = minAlphabet(tab, startIndex + 1);
// choisir entre startIndex et minReste
        if (tab[startIndex].compareTo(tab[minReste]) < 0) {
            return startIndex;
        } else return minReste;
    }
    public Object[] swap(Object[] tab, int index1, int index2){
        Object temp = tab[index1];
        tab[index1] = tab[index2];
        tab[index2] = temp;
        return tab;
    }

    public int[] swap(int[] tab, int index1, int index2){
        int temp = tab[index1];
        tab[index1] = tab[index2];
        tab[index2] = temp;
        return tab;
    }


}