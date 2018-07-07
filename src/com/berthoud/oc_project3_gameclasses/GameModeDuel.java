package com.berthoud.oc_project3_gameclasses;

import com.berthoud.oc_project3_menu.Main;

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

        if(Main.getChoiceGame().equals("1")){
            System.out.printf("%S", ">>>>> The +/- game, mode duel <<<<<\n");
        }else{
            System.out.printf("%S", ">>>>> The digit Mastermind, mode duel <<<<<\n");
        }

        gameA.setCodeToBeFound(gameA.randomCodeGenerator());

        gameA.displayModeDev(gameA.getCodeToBeFound());


        while ((!gameA.isCodeFound()) && (!gameB.isCodeFound())) {

            // ModeChallenger
            System.out.print("Mode challenger: player tries to guess Superbrain's combination\n");
            gameA.guessValidationUnit();

            MyTools.makeABreak(800);

            if (gameA.isCodeFound()) {
                System.out.println("Great! You found the code. Let's see if Superbrain can equalise ");
            } else System.out.println("Mode defender: Superbrain tries to guess your combination ");


            // ModeDefender

            if (gameB.getNbGuesses() ==1){
                if(Main.getChoiceGame().equals("1")){
                    System.out.print("To start with, choose the mystery code made of "
                            + gameA.getNbDigits() + " digits\nthat Superbrain will try to break ------------->  ");
                }else{
                    System.out.print("To start with, choose the combination of \n"
                            + gameA.getNbDigits() + " digits from 0 to "+ (gameA.getNbVariations()-1)+ " that Superbrain will try to break: ");
                }

                gameB.setCodeToBeFound(gameB.codeInputUser());
                System.out.println();
            }

            MyTools.makeABreak(300);
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
    private void messageEndOfTheGame() {
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


