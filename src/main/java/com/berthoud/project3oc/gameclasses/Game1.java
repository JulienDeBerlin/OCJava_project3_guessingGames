package com.berthoud.project3oc.gameclasses;


import com.berthoud.project3oc.menu.Main;

import java.util.Arrays;


/**
 * Abstract class for "Game +/- ". It contains all the instance fields and methods required for all modes of Game1
 */
public abstract class Game1 extends Games {


// _____________________________________________________________________________________________________________________
    //CONSTRUCTORS//

    /**
     * Constructor (constructors are chained)
     *
     * @param nbDigits   number of digits
     * @param maxGuesses max number of guesses allowed
     */
    protected Game1(int nbDigits, int maxGuesses) {
        super(nbDigits, maxGuesses);
    }


// _____________________________________________________________________________________________________________________
    //METHODS (SHARED BY ALL MODES)//


    /**
     * This methods compares codeTobeFound to codeProposal and return a +/- validation code
     *
     * @param codeToBeFound the mystery code
     * @param codeProposal  the code attempt made by the challenger
     * @return +/- validation code
     */
    protected String[] validation(int[] codeToBeFound, int[] codeProposal) {
        if ((codeToBeFound.length) != (codeProposal.length)) {
            System.out.println("Input arrays must have same length!");
            return null;

        } else {
            String[] validation = new String[getNbDigits()];
            for (int i = 0; i < getNbDigits(); i++) {
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
     *
     * @return the random code
     */
    @Override
    protected int[] randomCodeGenerator() {
        int[] randomCode = new int[getNbDigits()];
        for (int i = 0; i < getNbDigits(); i++) {
            randomCode[i] = (int) (10 * Math.random());
        }

        Main.logger.debug("random code = " + Arrays.toString(randomCode));
        return randomCode;
    }


    /**
     * This method inputs the code entered by the player on the keyboard, tests that the code is only made of digits
     * and that the length of the input is identical to the value of instance field {@link #nbDigits} and returns the valid code.
     *
     * @return a code entered by the player made of X digits, X being equal to value of {@link #nbDigits}
     */
    @Override
    protected int[] codeInputUser() {
        String inputUser = scan.nextLine();
        Main.logger.debug("User input: " + inputUser);

        while ((inputUser.length() != getNbDigits()) || (!MyTools.isMyStringAnInt(inputUser))) {
            System.out.println("What do you mean? Please enter a combination of " + getNbDigits() + " digits.");
            Main.logger.debug("Entry not valid.");
            inputUser = scan.nextLine();
            Main.logger.debug("New user input: " + inputUser);
        }

        // Conversion String into int array
        int[] codeInputUser = new int[getNbDigits()];
        for (int j = 0; j < getNbDigits(); j++) {
            codeInputUser[j] = Character.getNumericValue(inputUser.charAt(j));
        }
        return codeInputUser;

    }


    /**
     * This method sets the instance field {@link #isCodeFound} to true if the validation code is only made of = signs
     *
     * @param validation the ouput of the method {@link #validation(int[], int[])}
     */
    void testIsCodeFound(String[] validation) {
        int k = 0;
        for (String elementValidation : validation) {
            if ((elementValidation.equals(">")) || (elementValidation.equals("<"))) {
                k++;
            }
        }
        if (k == 0) {
            setCodeFound(true);
        }

    }


// _____________________________________________________________________________________________________________________


}

