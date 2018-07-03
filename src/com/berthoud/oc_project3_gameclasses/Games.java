package com.berthoud.oc_project3_gameclasses;
import com.berthoud.oc_project3_menu.Main;
import java.util.Scanner;

/**
 *  *  Abstract class for both games ("Game +/- " and Mastermind)
 *  It contains all the instance fields and methods shared by both games
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
     * This is the mystery code to be found, created by the computer (mode challenger) or by the player (mode defender)
     */
    private int [] codeToBeFound = new int [nbDigits];

    /**
     * This is code attempt made by the player (mode challenger) or the computer (mode defender)
     */
    private int [] codeProposal = new int [nbDigits];


    /**
     * counts the number of guesses used by the challenger
     */
    private int nbGuesses = 1;

    /**
     * is true when  the mystery code has been broken by the challenger
     */
    private boolean codeFound = false;

    /**
     * Object scanner, need for user inputs
     */
    public static Scanner scan = new Scanner(System.in);


// _____________________________________________________________________________________________________________________
    //CONSTRUCTORS//

    /**
     * Constructor
     * @param nbDigits number of digits
     * @param maxGuesses max number of guesses allowed
     */
    protected Games(int nbDigits, int maxGuesses) {
        this.nbDigits = nbDigits;
        this.maxGuesses = maxGuesses;
    }

    /**
     * Default constructor
     */
    protected Games() {
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

    void setCodeProposal(int index, int value) { {
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
     * This method displays the result of the game at the end of the game.
     * TThe implementation is different for each game and mode.
     */
    protected abstract void messageEndOfTheGame ();



// _____________________________________________________________________________________________________________________
    //CONCRETE METHODS//

    /**
     * This method calls the selection menu at the end of the game.
     */
    protected void endingMenu() {
        System.out.printf("\n\n%s\n%s\n%s\n%s\n\n%s", "Do you want to: ", "1: play again this game?", "2: come back to the game menu?",
                "3: stop loosing your time playing?", "Enter your selection: ");
        String selectorEnding = scan.nextLine();
        System.out.println("\n");


        while ((!MyTools.isMyStringAnInt(selectorEnding)) || (Integer.parseInt(selectorEnding) < 1) || (Integer.parseInt(selectorEnding) > 3)) {
            System.out.print("What do you mean? Select 1, 2 or 3: ");
            selectorEnding = scan.nextLine();
        }

        switch (selectorEnding) {
            case "1":
                play();
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
     * This method displays the instance variable {@link #codeToBeFound) when the developer mode is activated
     * @param codeToBeFound
     */
    protected void displayModeDev(int [] codeToBeFound) {
        if (Main.isDevMode()) {
            System.out.print("###### DEVELOPER MODE ###### \n###### Superbrain's secret code = ");
            for (int x : codeToBeFound) {
                System.out.print (x + " ");
            }
            System.out.println("######\n");
        }

    }

}
