Ce programme est un prototype fonctionnel d'engin de recherche pour le cours IFT1025 à l'UdeM.

GUIDE D'UTILISATION :

D'abord, il faut charger les différents fichiers qui formeront les résultats de recherche. Pour ce faire, cliquer sur Sélectionner, puis 
Fichier ou Directory dépendamment de se que vous voulez charger.

Ensuite, il faut indexer les fichiers en cliquant sur Indexation. Cela construit la structure d'index et la structure d'index inversée.

À partir de ce moment, vous pouvez voir les deux structures en cliquant sur Visualiser quand vous voulez.

Finalement, pour faire votre recherche, vous cliquez sur Rechercher et vous entrez les termes de votre recherche. Comme résultat, vous 
verrez alors une liste chainée qui contient tous les fichiers qui ont des mots en commun avec votre recherche. L'ordre de cette liste est
important, plus un fichier est au début de la liste, plus haut est son score par rapport à votre recherche. Les noeuds de la liste auront
la forme suivante : (texte.txt : k) où texte.txt est le nom d'un fichier et k est le nombre de fois qu'un terme de votre recherche
apparait dans ce fichier.

LIMITATIONS DU PROGRAMME :

Sélection des fichiers :
- Il n'est pas possible d'enlever un fichier de la sélection.
- Ne pas charger plusieurs fois le même fichier car cela mènera à des problèmes.

Recherche :
- La recherche tient compte des majuscules : "Allo" est différent de "allo".
- La recherche tient seulement compte des mots uniquement composés des 26 lettres standards (pas de caractères spéciaux).

DÉTAILS SUR L'INDEXATION :

Soient les trois fichiers : fichier1.txt, fichier2.txt et fichier3.txt. Supposons que ces fichiers contiennent les mots suivants :
fichier1.txt : A A A B B C.
fichier2.txt : A B B B C C.
fichier3.txt : A A B C C C.

L'indexation construit deux structures : index et index inversé. 
Voici la structure **index** dans cette situation :

Liste chainée verticale : 
fichier1.txt -> fichier2.txt -> fichier3.txt

Listes chainées horizontales : 
fichier1.txt -> (A : 3) -> (B : 2) -> (C : 1)
fichier2.txt -> (A : 1) -> (B : 3) -> (C : 2)
fichier3.txt -> (A : 2) -> (B : 1) -> (C : 3)

Important de noter que les listes chainées horizontales partent toutes de la liste chainée verticale.

Voici la structure **index inversée** dans cette situation :

Liste chainée verticale :
A -> B -> C

Listes chainées horizontales :
A -> (fichier1.txt : 3) -> (fichier2.txt : 1) -> (fichier3.txt : 2)
B -> (fichier1.txt : 2) -> (fichier2.txt : 3) -> (fichier3.txt : 1)
C -> (fichier1.txt : 1) -> (fichier2.txt : 2) -> (fichier3.txt : 3)

Ici aussi, les listes chainées horizontales partent toutes de la liste chainée verticale.

Auteurs : Philippe Schoeb, Joseph Descarreaux et Anne Cléroux 
18.12.2022



