package com.berthoud;

import java.util.Scanner;

public class Game1ModeDuel {
    private Game1ChallengerIsComputer gameA; //OrdinateurAttaque
    private Game1ChallengerIsUser gameB; //JoueurAttaque


    public Game1ModeDuel(int pNombreCases, int pNombreEssaisMax) {
        this.gameB = new Game1ChallengerIsUser(pNombreCases, pNombreEssaisMax);
        this.gameA = new Game1ChallengerIsComputer(pNombreCases, pNombreEssaisMax);
    }


    public void play() {

        System.out.println("Prêt à défier Superbrain? \nPour commencer, saisis le code secret à "
                + gameB.nbDigits + " chiffres que Superbrain devra deviner:");
        gameA.codeToBeFound = gameA.codeInputUser();
        gameB.codeToBeFound = gameB.randomCodeGenerator();


        System.out.println("Parfait, c'est parti! A toi de jouer ton premier coup en essayant de deviner le code secret de Superbrain ");

        while ((!gameB.isCodeFound) && (!gameA.isCodeFound)) {

            // Mode joueur attaque Superbrain
            System.out.println("Partie 1: joueur contre Superbrain");
            gameB.codeProposal = gameB.codeInputUser();
            String[] validationTentativeJoueur = gameB.validation(gameB.codeToBeFound, gameB.codeProposal);
            System.out.println(gameB.arrayToString(validationTentativeJoueur));

            gameB.testIsCodeFound(validationTentativeJoueur);
            gameB.increaseNbGuesses();

            if (gameB.isCodeFound) {
                System.out.println("Bravo! Voyons si Superbrain peut égaliser. ");
            } else System.out.println("A Superbrain de jouer! Que dis-tu de: ");


            // Mode Superbrain attaque joueur
            if (gameA.nbGuesses == 0) {
                gameA.codeProposal = gameA.randomCodeGenerator(); //1ere réponse de l'ordinateur = chiffre aléatoire
            }

            gameA.displayProposalComputer(gameA.codeProposal);

            System.out.println("A toi de saisir le code de validation (+/-)");

            Scanner scan = new Scanner(System.in);
            String validationJoueur = scan.nextLine();

            gameA.answerComputer(validationJoueur);
            gameA.increaseNbGuesses();
        }

        if ((gameB.isCodeFound) && (gameA.isCodeFound)) {
            System.out.println("Ex Aequo!!!");
        }

        if ((gameB.isCodeFound) && (!gameA.isCodeFound)){
            System.out.println("Tu as vaincu Superbrain!");
        }

        if ((!gameB.isCodeFound) && (gameA.isCodeFound)){
            System.out.println("Tu as perdu!");
        }
    }
}


