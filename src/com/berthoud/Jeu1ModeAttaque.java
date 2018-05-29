package com.berthoud;


public class Jeu1ModeAttaque extends Jeu1 {

    public Jeu1ModeAttaque(int pNombreCases, int pNombreEssaisMax) {
        super(pNombreCases, pNombreEssaisMax);
    }

    public void play() {
        // Génération d'un code aléatoire et initialisation de ce code sous forme d'un tableau
        int [] codeMysterieux = super.generateurCode();

        // Saisie et validation code joueur
        int nombreEssais = 0;

        while ((nombreEssais < super.nombreEssaisMax) && (super.isCodeFound == false)) {

            nombreEssais++;

            System.out.println("Saisis ton code:");

            int [] tentativeJoueur = super.saisieCodeJoueur();
            String [] validation = super.validation(codeMysterieux, tentativeJoueur);
            System.out.println(arrayToString(validation));


//             Définition des conditions de sortie de boucle
//            int k = 0;
//            for (String elementValidation : validation) {
//                if ((elementValidation == ">") || (elementValidation == "<")){
//                    k++;}
//            }
//            if (k == 0) {
//                isCodeFound = true;
//            }

            super.isCodeFound(validation);
        }

        // CE CODE DOIT ETRE ENCORE FACTORISE
        if (super.isCodeFound == true) {
            System.out.println("Bravo, tu as trouvé la combinaison secrète!");
        } else {
            System.out.println("Tu as perdu!");
        }
    }

}
