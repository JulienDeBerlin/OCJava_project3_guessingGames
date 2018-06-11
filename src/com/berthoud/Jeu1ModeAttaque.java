package com.berthoud;

public class Jeu1ModeAttaque extends Jeu1 {

    public Jeu1ModeAttaque(int pNombreCases, int pNombreEssaisMax) {
        super(pNombreCases, pNombreEssaisMax);
        super.mode = "mode attaque";
    }

    public void play() {
        // Génération d'un code aléatoire et initialisation de ce code sous forme d'un tableau
        codeMysterieux = super.generateurCode();

        // Saisie et validation code joueur
//        int nombreEssais = 0;

        while ((super.nombreEssais < super.nombreEssaisMax) && (!super.isCodeFound)) {
            System.out.println("Saisis ton code:");

            tentativeCode = super.saisieCodeJoueur();
            String [] validation = super.validation(codeMysterieux, tentativeCode);
            System.out.println(arrayToString(validation));

            super.isCodeFound(validation);
            super.nombreEssais++;
        }

        // CE CODE DOIT ETRE ENCORE FACTORISE
        if (super.isCodeFound) {
            System.out.println("Bravo, tu as trouvé la combinaison secrète!");
        } else {
            System.out.println("Tu as perdu!");
        }
    }

}
