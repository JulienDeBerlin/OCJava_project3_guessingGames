package com.berthoud.oc_project3_gameclasses;

/**
 * The program GameModeDuel makes possible to play either the " + / - game" or the digit Mastermind in a duel mode.
 * In this mode the player tries to break computer's code and the computer tries to break player's code, both game runs at the same time.
 * The first who breaks the other's code wins.
 */
public class GameModeDuel{


// _____________________________________________________________________________________________________________________
    //INSTANCE FIELDS//

    private Games gameA; // mode challenger
    private Games gameB; // mode defender


// _____________________________________________________________________________________________________________________
    //CONSTRUCTORS//

    /**
     * Constructor (constructors are chained)
     * @param nbDigits number of digits
     * @param maxGuesses max number of guesses allowed
     */
    public GameModeDuel(int nbDigits, int maxGuesses) {
        this.gameA = new Game1ModeChallenger(nbDigits, maxGuesses);
        this.gameB = new Game1ModeDefender(nbDigits, maxGuesses);
    }


    /**
     * Constructor
     * @param nbDigits number of digits of the code
     * @param maxGuesses max number of guesses allowed
     * @param nbVariations number of value possible for each digit. Min = 4 , Max = 10
     */
    public GameModeDuel(int nbDigits, int maxGuesses, int nbVariations) {
        this.gameA = new Game2ModeChallenger(nbDigits, maxGuesses, nbVariations);
        this.gameB = new Game2ModeDefender(nbDigits, maxGuesses, nbVariations);

    }


// _____________________________________________________________________________________________________________________
    //IMPLEMENTATION OF ABSTRACT METHODS//

    /**
     * This method is a wrapper-method that starts and executes the game until the end.
     * All the methods required for the execution of the game are called within this wrapper-method.
     * The implementation is different for each game and mode.
     */
    public void play() {

        System.out.printf("%S", ">>>>> The +/- game, mode duel <<<<<\n");

        MyTools.makeABreak(400);

        System.out.print("Ready to challenge Superbrain? \nTo start with, choose a mystery code made of "
                + gameA.getNbDigits() + " digits that Superbrain will try to break: ");
        gameB.setCodeToBeFound(gameB.codeInputUser());
        gameA.setCodeToBeFound(gameA.randomCodeGenerator());


        System.out.print("Perfect, let's get started!\n\n");

        while ((!gameA.isCodeFound()) && (!gameB.isCodeFound())) {

            // ModeChallenger
            System.out.print("Mode challenger: player against Superbrain\n");
            gameA.displayModeDev(gameA.getCodeToBeFound());

            gameA.guessValidationUnit();

            MyTools.makeABreak(800);

            if (gameA.isCodeFound()) {
                System.out.println("Great! You broke the code. Let's see if Superbrain can equalise ");
            } else System.out.println("Mode defender: Superbrain against player");


            // ModeDefender
           gameB.guessValidationUnit();
        }

        messageEndOfTheGame();

        gameB.setCodeFound(false);
        gameA.setCodeFound(false);
        gameB.setNbGuesses(1);
        gameA.setNbGuesses(1);



        gameA.endingMenu();

    }

    /**
     * This method displays the result of the game at the end of the game.
     * TThe implementation is different for each game and mode.
     */
    protected void messageEndOfTheGame() {
        if ((gameA.isCodeFound()) && (gameB.isCodeFound())) {
            System.out.printf("%S", "Dead heat!");
        }

        if ((gameA.isCodeFound()) && (!gameB.isCodeFound())){
            System.out.printf("%S", "You won!");
        }

        if ((!gameA.isCodeFound()) && (gameB.isCodeFound())){
            System.out.printf("%S", "Superbrain won!");
        }

    }
}


