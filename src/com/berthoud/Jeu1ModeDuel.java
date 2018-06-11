package com.berthoud;

import java.util.Scanner;

public class Jeu1ModeDuel  {
    private Jeu1ModeAttaque partie1; //JoueurAttaque
    private Jeu1ModeDefense partie2; //OrdinateurAttaque


    public Jeu1ModeDuel(int pNombreCases, int pNombreEssaisMax) {
        this.partie1 = new Jeu1ModeAttaque(pNombreCases, pNombreEssaisMax);
        this.partie2 = new Jeu1ModeDefense(pNombreCases, pNombreEssaisMax);
    }


    public void play() {

        System.out.println("Prêt à défier Superbrain? \nPour commencer, saisis le code secret à "
                + partie1.nombreCases + " chiffres que Superbrain devra deviner:");
        partie2.codeMysterieux = partie2.saisieCodeJoueur();
        partie1.codeMysterieux = partie1.generateurCode();


        System.out.println("Parfait, c'est parti! A toi de jouer ton premier coup en essayant de deviner le code secret de Superbrain ");

        while ((!partie1.isCodeFound) && (!partie2.isCodeFound)) {

            // Mode joueur attaque Superbrain
            System.out.println("Partie 1: joueur contre Superbrain");
            partie1.tentativeCode = partie1.saisieCodeJoueur();
            String[] validationTentativeJoueur = partie1.validation(partie1.codeMysterieux, partie1.tentativeCode);
            System.out.println(partie1.arrayToString(validationTentativeJoueur));

            partie1.isCodeFound(validationTentativeJoueur);
            partie1.increaseNombreEssais();

            if (partie1.isCodeFound) {
                System.out.println("Bravo! Voyons si Superbrain peut égaliser. ");
            } else System.out.println("A Superbrain de jouer! Que dis-tu de: ");


            // Mode Superbrain attaque joueur
            if (partie2.nombreEssais == 0) {
                partie2.tentativeCode = partie2.generateurCode(); //1ere réponse de l'ordinateur = chiffre aléatoire
            }

            partie2.displayTentativeCodeOrdinateur(partie2.tentativeCode);

            System.out.println("A toi de saisir le code de validation (+/-)");

            Scanner scan = new Scanner(System.in);
            String validationJoueur = scan.nextLine();

            partie2.reponseValidationJoueur(validationJoueur);
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


