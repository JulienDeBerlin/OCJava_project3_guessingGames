package com.berthoud.oc_project3_gameclasses;

import com.berthoud.oc_project3_menu.Main;

import java.util.Scanner;

/**
 * *  Abstract class for both games ("Game +/- " and Mastermind)
 * It contains all the instance fields and methods shared by both games
 */
public abstract class Games {

// _____________________________________________________________________________________________________________________
    //INSTANCE FIELDS//

    /**
     * number of digits
     */
    private int nbDigits;

    /**
     * max number of guesses allowed
     */
    private int maxGuesses;

    /**
     * Number of values that each digit can possibly take
     */
    private int nbVariations;

    /**
     * This is the mystery code to be found, created by the computer (mode challenger) or by the player (mode defender)
     */
    private int[] codeToBeFound = new int[nbDigits];

    /**
     * This is code attempt made by the player (mode challenger) or the computer (mode defender)
     */
    private int[] codeProposal = new int[nbDigits];


    /**
     * counts the number of guesses used by the challenger
     */
    private int nbGuesses = 1;


    /**
     * is true when the mystery code has been broken by the challenger
     */
    private boolean codeFound = false;

    /**
     * Object scanner, need for user inputs
     */
    public static Scanner scan = new Scanner(System.in);


// _____________________________________________________________________________________________________________________
    //CONSTRUCTORS//

    /**
     * Constructor for game 1
     *
     * @param nbDigits   number of digits
     * @param maxGuesses max number of guesses allowed
     */
    Games(int nbDigits, int maxGuesses) {
        this.nbDigits = nbDigits;
        this.maxGuesses = maxGuesses;
    }


    /**
     * Constructor for game 2
     *
     * @param nbDigits     number of digits
     * @param maxGuesses   max number of guesses allowed
     * @param nbVariations nb of values that each digit can take
     */
    Games(int nbDigits, int maxGuesses, int nbVariations) {
        this.nbDigits = nbDigits;
        this.maxGuesses = maxGuesses;
        this.nbVariations = nbVariations;
    }

    // _____________________________________________________________________________________________________________________
    //GETTERS and SETTERS (all package-private)//

    int getNbDigits() {
        return nbDigits;
    }

    int getMaxGuesses() {
        return maxGuesses;
    }

    int getNbGuesses() {
        return nbGuesses;
    }

    boolean isCodeFound() {
        return codeFound;
    }

    int[] getCodeToBeFound() {
        return codeToBeFound;
    }

    int[] getCodeProposal() {
        return codeProposal;
    }

    int getNbVariations() {
        return nbVariations;
    }

    void setCodeFound(boolean codeFound) {
        this.codeFound = codeFound;
    }

    void setNbGuesses(int nbGuesses) {
        this.nbGuesses = nbGuesses;
    }

    void setCodeToBeFound(int[] codeToBeFound) {
        this.codeToBeFound = codeToBeFound;
    }

    void setCodeProposal(int[] codeProposal) {
        this.codeProposal = codeProposal;
    }

    void setCodeProposal(int index, int value) {
        {
            this.codeProposal[index] = value;
        }
    }


// _____________________________________________________________________________________________________________________
    //ABSTRACT METHODS//

    /**
     * This method is a wrapper-method that starts and executes the game until the end.
     * All the methods required for the execution of the game are called within this wrapper-method.
     * The implementation is different for each game and mode.
     */
    public abstract void play();


    /**
     * This methods generates a random code made of X digits, X being equal to value of {@link #nbDigits}
     *
     * @return the random code
     */
    protected abstract int[] randomCodeGenerator();


    /**
     * This method takes a guess and display the validation code (in challenger mode),
     * generates a guess, takes a validation code and generate the next guess (in defender mode)
     */
    protected abstract void guessValidationUnit();


    /**
     * This method inputs the code entered by the player on the keyboard test its validity and return it
     *
     * @return the valid code entered by the player
     */
    protected abstract int[] codeInputUser();


// _____________________________________________________________________________________________________________________
    //CONCRETE METHODS//

    /**
     * This method calls the selection menu at the end of the game.
     */
    void endingMenu() {
        System.out.printf("\n\n%s\n%s\n%s\n%s\n\n%s", "Do you want to: ", "1: play again this game?", "2: come back to the game menu?",
                "3: stop loosing your time playing?", "Enter your selection: ");
        String selectorEnding = scan.nextLine();
        System.out.println("\n");


        while ((!selectorEnding.equals("1") && !selectorEnding.equals("2") && !selectorEnding.equals("3"))) {
            System.out.print("What do you mean? Select 1, 2 or 3: ");
            selectorEnding = scan.nextLine();
        }

        switch (selectorEnding) {
            case "1":
                Main.startTheGame(Main.getChoiceGame(), Main.getChoiceMode());
                break;
            case "2":
                Main.menu();
                break;
            case "3":
                System.out.println("Bye bye, hope to see you soon!");
                break;
        }

    }


    /**
     * This method displays the instance variable {@link #codeToBeFound} when the developer mode is activated
     *
     * @param codeToBeFound the mystery code
     */
    void displayModeDev(int[] codeToBeFound) {
        if (Main.isDevMode()) {
            System.out.print("###### DEVELOPER MODE ! Superbrain's secret code = ");
            for (int x : codeToBeFound) {
                System.out.print(x + " ");
            }
            System.out.println("######\n");
        }

    }


    /**
     * This method displays the result of the game at the end of the game.
     */
    protected void messageEndOfTheGame() {
        if (this.isCodeFound()) {
            System.out.print("Superbrain made it!");
        } else {
            System.out.print("How does it feel to defeat Superbrain? ");
        }
    }


}
