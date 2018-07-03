package com.berthoud.oc_project3_gameclasses;


/**
 *  Abstract class for "Game +/- ". It contains all the instance fields and methods required for all modes
 */
public abstract class Game1 extends Games{

// _____________________________________________________________________________________________________________________
    //INSTANCE FIELDS//



// _____________________________________________________________________________________________________________________
    //CONSTRUCTORS//

    /**
     * Constructor (constructors are chained)
     * @param nbDigits number of digits
     * @param maxGuesses max number of guesses allowed
     */
    protected Game1(int nbDigits, int maxGuesses) {
        super(nbDigits, maxGuesses);
    }


    /**
     * Default constructor
     */
    protected Game1() {
    }


// _____________________________________________________________________________________________________________________
    //GETTERS//



// _____________________________________________________________________________________________________________________
    //METHODS (SHARED BY ALL MODES)//


    /**
     * This method inputs the code entered by the player on the keyboard, tests that the code is only made of digits
     * and that the length of the input is identical to the value of instance field {@link #nbDigits} and returns the valid code
     * @return a code entered by the player made of X digits, X being equal to value of {@link #nbDigits}
     */
    protected int[] codeInputUser() {

        // input
        String inputUser = scan.nextLine();

        //test
        while ((inputUser.length() != getNbDigits()) || (!MyTools.isMyStringAnInt(inputUser))) {
            System.out.print("What do you mean? Please enter a combination of "  + getNbDigits() + " digits: ");
            inputUser = scan.nextLine();
        }

        // Conversion String into int array
        int[] codeInputUser = new int[getNbDigits()];
        for (int j = 0; j < getNbDigits(); j++) {
            codeInputUser[j] = Character.getNumericValue(inputUser.charAt(j));
        }
        return codeInputUser;
    }


    /**
     *This methods compares codeTobeFound to codeProposal and return a +/- validation code
     * @param codeToBeFound the mystery code
     * @param codeProposal the code attempt made by the challenger
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
     * @return the random code
     */
    protected int[] randomCodeGenerator() {
        int[] randomCode = new int[getNbDigits()];
        for (int i = 0; i < getNbDigits(); i++) {
            randomCode[i] = (int) (10 * Math.random());
        }
        return randomCode;
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
           setCodeFound(true);
        }

    }

    /**
     * This methods increments the instance field {@link #nbGuesses}
      * @return the increased instance field {@link #nbGuesses}
     */
    protected int increaseNbGuesses(){
        setNbGuesses(getNbGuesses()+1);
        return this.getNbGuesses();
    }


// _____________________________________________________________________________________________________________________


}

