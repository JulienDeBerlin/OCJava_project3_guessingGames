package com.berthoud;


public class Game1ModeDuel extends Game1 {
    private Game1ChallengerIsComputer gameA;
    private Game1ChallengerIsUser gameB;


    public Game1ModeDuel(int nbDigits, int maxGuesses) {
        this.gameB = new Game1ChallengerIsUser(nbDigits, maxGuesses);
        this.gameA = new Game1ChallengerIsComputer(nbDigits, maxGuesses);
    }


    /**
     * This method sets the flow of the game
     */
    public void play() {

        System.out.println(">>>>>  The +/- game, mode duel <<<<<" );

        MyTools.makeABreak(400);

        System.out.print("Ready to challenge Superbrain? \nTo start with, choose a mystery code made of "
                + gameB.nbDigits + " digits that Superbrain will try to break: ");
        gameA.codeToBeFound = gameA.codeInputUser();
        gameB.codeToBeFound = gameB.randomCodeGenerator();


        System.out.print("Perfect, let's get started!\n\n");

        while ((!gameB.isCodeFound) && (!gameA.isCodeFound)) {

            // Game1ChallengerIsUser
            System.out.print("Mode challenger: player against Superbrain\n");

            gameB.guessValidationUnit();

            MyTools.makeABreak(800);

            if (gameB.isCodeFound) {
                System.out.println("Great! You broke the code. Let's see if Superbrain can equalise ");
            } else System.out.println("Mode defender: Superbrain against player");


            // Game1ChallengerIsComputer
           gameA.guessValidationUnit();
        }

        messageEndOfTheGame();

        gameA.isCodeFound = false;
        gameB.isCodeFound = false;
        gameA.nbGuesses = 1;
        gameB.nbGuesses= 1;

        endingMenu();

    }


    @Override
    protected void messageEndOfTheGame() {
        if ((gameB.isCodeFound) && (gameA.isCodeFound)) {
            System.out.println("Dead heat!");
        }

        if ((gameB.isCodeFound) && (!gameA.isCodeFound)){
            System.out.println("You won!");
        }

        if ((!gameB.isCodeFound) && (gameA.isCodeFound)){
            System.out.println("Superbrain won!");
        }

    }


}


