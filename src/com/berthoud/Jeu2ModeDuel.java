package com.berthoud;


public class Jeu2ModeDuel  {
    private Jeu2ModeAttaque partie1; //JoueurAttaque
    private Jeu2ModeDefense partie2; //OrdinateurAttaque


    public Jeu2ModeDuel(int pNombreCases, int pNombreEssaisMax, int pNomberCouleurs) {
        this.partie1 = new Jeu2ModeAttaque(pNombreCases, pNombreEssaisMax, pNomberCouleurs);
        this.partie2 = new Jeu2ModeDefense(pNombreCases, pNombreEssaisMax, pNomberCouleurs);
    }


    public void play() {

        System.out.println("Prêt à défier Superbrain? \nPour commencer, saisis la combinaison à "
                + partie1.nombreCases + " chiffres que Superbrain devra deviner:");
        partie1.codeMysterieux = partie1.generateurCode();
        partie2.codeMysterieux = partie2.saisieCodeJoueur();


        System.out.println("Parfait, c'est parti! A toi de jouer ton premier coup en essayant de deviner le code secret de Superbrain ");

        while ((!partie1.isCodeFound) && (!partie2.isCodeFound)) {

            // Mode joueur attaque Superbrain
            System.out.println("Partie 1: joueur contre Superbrain");
            partie1.tentativeCode = partie1.saisieCodeJoueur();
            partie1.validation(partie1.codeMysterieux, partie1.tentativeCode);
            partie1.printValidation();
            partie1.isCodeFound();
            partie1.increaseNombreEssais();

            if (partie1.isCodeFound) {
                System.out.println("Bravo! Voyons si Superbrain peut égaliser. ");
            } else System.out.println("A Superbrain de jouer! Que dis-tu de: ");


            // Mode Superbrain attaque joueur
            if (partie2.nombreEssais == 0) {
                partie2.tentativeCode = partie2.generateurCode(); //1ere réponse de l'ordinateur = chiffre aléatoire
            }

            partie2.displayTentativeCodeOrdinateur(partie2.tentativeCode);

            System.out.println("A toi de saisir le code de validation :");


            partie2.saisieValidationJoueur();
            partie2.isCodeFound();
            partie2.creationPoolCombinaisons();
            if(!partie2.isCodeFound) {
                partie2.reponseValidationJoueur();
            }

            partie2.nombreEssais++;
            partie2.increaseNombreEssais();
        }

        if ((partie1.isCodeFound) && (partie2.isCodeFound)) {
            System.out.println("Ex Aequo!!!");
        }

        if ((partie1.isCodeFound) && (!partie2.isCodeFound)){
            System.out.println("Tu as vaincu Superbrain!");
        }

        if ((!partie1.isCodeFound) && (partie2.isCodeFound)){
            System.out.println("Tu as perdu!");
        }
    }
}
