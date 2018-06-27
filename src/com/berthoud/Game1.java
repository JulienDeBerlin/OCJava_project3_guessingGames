package com.berthoud;

import java.util.Scanner;


/**
 *  Abstract class for game "+/-. It contains all the instance fields and methods required for both mode "challenger is computer" and "challenger is user"
 */
public abstract class Game1 {
    protected int nbDigits;
    protected int maxGuesses;
    protected int nbGuesses = 1;
    protected boolean isCodeFound = false;
    protected int [] codeToBeFound = new int [nbDigits];
    protected int [] codeProposal = new int [nbDigits];
    protected static Scanner scan = new Scanner(System.in);

    /**
     * Constructor
     * @param nbDigits
     * @param maxGuesses
     */
    protected Game1(int nbDigits, int maxGuesses) {
        this.nbDigits = nbDigits;
        this.maxGuesses = maxGuesses;
    }

    // default constructor
    public Game1() {
    }

    /**
     * This method sets the flow of the game
     */
    public abstract void play();


    /**
     * This method organizes the end of the game. User (1) wwins or looses AND (2) repeat the same game, play another game or quit
     */
    protected abstract void messageEndOfTheGame ();


    /**
     * This method inputs the code entered by the player on the keyboard, tests that the code is only made of digits
     * and that the length of the input is identical to the value of instance field {@link #nbDigits} and returns the valid code
     * @return a code entered by the player made of X digits, X being equal to value of {@link #nbDigits}
     */
    protected int[] codeInputUser() {

        // input
        String inputUser = scan.nextLine();

        //test
        while ((inputUser.length() != nbDigits) || (!MyTools.isMyStringAnInt(inputUser))) {
            System.out.print("What do you mean? Please enter a combination of "  + nbDigits + " digits: ");
            inputUser = scan.nextLine();
        }

        // Conversion String into int array
        int[] codeInputUser = new int[nbDigits];
        for (int j = 0; j < nbDigits; j++) {
            codeInputUser[j] = Character.getNumericValue(inputUser.charAt(j));
        }
        return codeInputUser;
    }


    /**
     *This methods compares codeTobeFound to codeProposal and return a +/- validation code
     * @param codeToBeFound
     * @param codeProposal
     * @return +/- validation code
     */
    protected String[] validation(int[] codeToBeFound, int[] codeProposal) {
        if ((codeToBeFound.length) != (codeProposal.length)) {
            System.out.println("Input arrays must have same length!");
            return null;

        } else {
            String[] validation = new String[nbDigits];
            for (int i = 0; i < nbDigits; i++) {
                if (codeToBeFound[i] > codeProposal[i]) {
                    validation[i] = ">";
                } else if (codeToBeFound[i] < codeProposal[i]) {
                    validation[i] = "<";
                } else {
                    validation[i] = "=";
                }
            }
            return validation;
        }
    }



    /**
     * This methods generates a random code made of X digits, X being equal to value of {@link #nbDigits}
     * @return
     */
    protected int[] randomCodeGenerator() {
        int[] randomCode = new int[nbDigits];
        for (int i = 0; i < nbDigits; i++) {
            randomCode[i] = (int) (10 * Math.random());
        }
        return randomCode;
    }


    /**
     * This method converts a String array to a String and add a space between each element
     * @param myArray
     * @return the String made of all the elements of the String array, with space between each element
     */
    protected String arrayToString(String myArray[]) {
        String myString = myArray[0];
        for (int i = 1; i < (nbDigits); i++) {
            myString += (' ' + myArray[i]);
        }
        return myString;
    }


    /**
     * This method sets the instance field {@link #isCodeFound} to true if the validation code is only made of = signs
      * @param validation
     *          the ouput of the method {@link #validation(int[], int[])}
     */
    protected void testIsCodeFound(String [] validation) {
        int k = 0;
        for (String elementValidation : validation) {
            if ((elementValidation == ">") || (elementValidation == "<")) {
                k++;
            }
        }
        if (k == 0) {
           isCodeFound = true;
        }

    }

    /**
     * This methods increments the instance field {@link #nbGuesses}
      * @return the increased instance field {@link #nbGuesses}
     */
    protected int increaseNbGuesses(){
        this.nbGuesses++;
        return this.nbGuesses;
    }


    protected void endingMenu() {
        System.out.printf("\n\n%s\n%s\n%s\n%s\n\n%s", "Do you want to: ", "1: play again this game?", "2: come back to the game menu?",
                "3: stop loosing you time playing?", "Enter your selection: ");
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
            case "3":
                System.out.println("Bye bye, hope to see you soon!");

                break;
        }

    }


}

