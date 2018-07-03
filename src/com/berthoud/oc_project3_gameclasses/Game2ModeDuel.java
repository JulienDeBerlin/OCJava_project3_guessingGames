package com.berthoud.oc_project3_gameclasses;


public class Game2ModeDuel extends Game2 {
    private Game2ModeChallenger partie1;
    private Game2ModeDefender partie2;


    public Game2ModeDuel(int nbDigits, int maxGuesses, int nbVariations) {
        this.partie1 = new Game2ModeChallenger(nbDigits, maxGuesses, nbVariations);
        this.partie2 = new Game2ModeDefender(nbDigits, maxGuesses, nbVariations);
    }




    public void play() {

        System.out.printf("%S", ">>>>> The digit Mastermind, mode duel <<<<<\n");


        System.out.print("Ready to challenge Superbrain? \nTo start with, choose a mystery code made of "
                + partie1.getNbDigits() + " digits that Superbrain will try to break: ");
        partie1.setCodeToBeFound( partie1.randomCodeGenerator());
        partie2.setCodeToBeFound(partie2.codeInputUser());


        System.out.println("Parfait, c'est parti! A toi de jouer ton premier coup en essayant de deviner le code secret de Superbrain ");

        displayModeDev(partie1.getCodeToBeFound());

        while ((!partie1.testIsCodeFound()) && (!partie2.testIsCodeFound())) {

            // Mode joueur attaque Superbrain
            System.out.println("Partie 1: joueur contre Superbrain");
            partie1.setCodeProposal( partie1.codeInputUser());
            partie1.validation(partie1.getCodeToBeFound(), partie1.getCodeProposal());
//            partie1.printValidation();
            partie1.testIsCodeFound();
            partie1.increaseNombreEssais();

            if (partie1.testIsCodeFound()) {
                System.out.println("Bravo! Voyons si Superbrain peut égaliser. ");
            } else System.out.println("A Superbrain de jouer! Que dis-tu de: ");


            // Mode Superbrain attaque joueur
            if (partie2.getNbGuesses() == 0) {
                partie2.setCodeProposal( partie2.randomCodeGenerator()); //1ere réponse de l'ordinateur = chiffre aléatoire
            }

            partie2.displayTentativeCodeOrdinateur(partie2.getCodeProposal());

            System.out.println("A toi de saisir le code de validation :");


            partie2.saisieValidationJoueur();
            partie2.testIsCodeFound();
            partie2.creationPoolCombinaisons();
            if(!partie2.testIsCodeFound()) {
                partie2.reponseValidationJoueur();
            }

            partie2.setNbGuesses(partie2.getNbGuesses()+1);
            partie2.increaseNombreEssais();
        }

        if ((partie1.testIsCodeFound()) && (partie2.testIsCodeFound())) {
            System.out.println("Ex Aequo!!!");
        }

        if ((partie1.testIsCodeFound()) && (!partie2.testIsCodeFound())){
            System.out.println("Tu as vaincu Superbrain!");
        }

        if ((!partie1.testIsCodeFound()) && (partie2.testIsCodeFound())){
            System.out.println("Tu as perdu!");
        }
    }


    @Override
    protected void messageEndOfTheGame() {

    }
}
