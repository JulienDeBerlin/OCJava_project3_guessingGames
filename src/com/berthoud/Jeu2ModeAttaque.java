package com.berthoud;

public class Jeu2ModeAttaque extends Jeu2 {

    public Jeu2ModeAttaque(int pNombreCases, int pNombreEssaisMax, int pNombreCouleurs) {
        super(pNombreCases, pNombreEssaisMax, pNombreCouleurs);
        super.mode = "mode attaque";
    }


    public void play() {

        // Génération d'un code aléatoire et initialisation de ce code sous forme d'un tableau
        codeMysterieux = super.generateurCode();

        // Saisie et validation code joueur
//        int nbGuesses = 0;

        while ((super.nombreEssais <= super.nombreEssaisMax) && (!super.isCodeFound)) {
            System.out.println("Saisis ton code:");

            tentativeCode = super.saisieCodeJoueur();
            super.validation(codeMysterieux, tentativeCode);

            super.printValidation();
            super.isCodeFound();
            super.nombreEssais++;
        }

        // CE CODE DOIT ETRE ENCORE FACTORISE
        if (super.isCodeFound) {
            System.out.println("Bravo, tu as trouvé la combinaison secrète!");
        } else {
            System.out.print("Tu as perdu! La combinaison était ");
            for (int digit : codeMysterieux) {
                System.out.print(digit);
            }
            System.out.println();
        }
    }

}
