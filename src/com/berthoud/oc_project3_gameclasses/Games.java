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


    private int nbDigits;
    private int maxGuesses;
    private int nbGuesses = 1;
    private boolean codeFound = false;
    private int [] codeToBeFound = new int [nbDigits];
    private int [] codeProposal = new int [nbDigits];
    public static Scanner scan = new Scanner(System.in);


// _____________________________________________________________________________________________________________________
    //CONSTRUCTORS//

    /**
     * Constructor
     * @param nbDigits
     * @param maxGuesses
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
    //GETTERS and SETTERS//

    protected int getNbDigits() {
        return nbDigits;
    }

    protected int getMaxGuesses() {
        return maxGuesses;
    }

    protected int getNbGuesses() {
        return nbGuesses;
    }

    protected boolean isCodeFound() {
        return codeFound;
    }

    protected int[] getCodeToBeFound() {
        return codeToBeFound;
    }

    protected int[] getCodeProposal() {
        return codeProposal;
    }

    protected void setCodeFound(boolean codeFound) {
        this.codeFound = codeFound;
    }

    protected void setNbGuesses(int nbGuesses) {
        this.nbGuesses = nbGuesses;
    }

    protected void setCodeToBeFound(int[] codeToBeFound) {
        this.codeToBeFound = codeToBeFound;
    }

    protected void setCodeProposal(int[] codeProposal) {
        this.codeProposal = codeProposal;
    }

    protected void setCodeProposal(int index, int value) { {
            this.codeProposal[index] = value;
        }
    }


    // _____________________________________________________________________________________________________________________
    //ABSTRACT METHODS//

    /**
     * This method is a wrapper-method that starts and executes the game until the end.
     * TThe implementation is different for each game and mode.
     */
    public abstract void play();


    /**
     * This method displays a message at the end of the game.
     * TThe implementation is different for each game and mode.
     */
    protected abstract void messageEndOfTheGame ();



// _____________________________________________________________________________________________________________________
    //CONCRETE METHODS//

    /**
     * This methods call the selection menu at the end of the game.
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
     * This methods displays the instance variable {@link #codeToBeFound) when developer mode is activated
     */
    protected void displayModeDev(int [] myIntArray) {
        if (Main.isDevMode()) {
            System.out.print("###### DEVELOPER MODE ###### \n###### Superbrain's secret code = ");
            for (int x : myIntArray) {
                System.out.print (x + " ");
            }
            System.out.println("######\n");
        }

    }

}
