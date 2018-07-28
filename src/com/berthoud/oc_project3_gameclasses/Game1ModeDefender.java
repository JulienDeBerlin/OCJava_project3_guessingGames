package com.berthoud.oc_project3_gameclasses;


import com.berthoud.oc_project3_menu.Main;

/**
 * The program Game1ModeDefender implements the first game (+/-) in the mode defender: the user chooses a code and the
 * computer tries to break it
 */

public class Game1ModeDefender extends Game1 {


// _____________________________________________________________________________________________________________________
    //INSTANCE FIELDS//
    /**
     * This field is need for the AI part of the program. It refers to the min and max possible value of each digit of the
     * {@link #codeToBeFound}
     */
    private int[][] rangeCodeToBeFound ;// Array 1 = minValue, Array 2 = maxValue
    // Although the second part of the declaration is meant to be redundant by the IDE, the program throws a NullPointerException if I suppress it.
    // Same happens if I replace it by initRangeCodeToBeFound(). Why?
    // UPDATE 17.17.18: initialisation has to happen somewhere, or in the declaration or in the method initRangeCodeToBeFound()


    private String[] validationByComputerStringArray;


// _____________________________________________________________________________________________________________________
    //CONSTRUCTORS//

    /**
     * Constructor (constructors are chained)
     *
     * @param nbDigits   number of digits
     * @param maxGuesses max number of guesses allowed
     */
    public Game1ModeDefender(int nbDigits, int maxGuesses) {
        super(nbDigits, maxGuesses);
        this.rangeCodeToBeFound = initRangeCodeToBeFound();
    }

// _____________________________________________________________________________________________________________________
    //GETTERS//


// _____________________________________________________________________________________________________________________
    //IMPLEMENTATION OF ABSTRACT METHODS//

    /**
     * This method is a wrapper-method that starts and executes the game until the end.
     * All the methods required for the execution of the game are called within this wrapper-method.
     * The implementation is different for each game and mode.
     */
    @Override
    public void play() {
        Main.logger.trace("Entering play()");

        setNbGuesses(1);
        setCodeFound(false);

        System.out.printf("%S", ">>>>> The +/- game, mode defender <<<<<\n");

        MyTools.makeABreak(400);

        System.out.print("You choose a mystery code made of " + getNbDigits() + " digits. " +
                "Superbrain the computer will try to break it, with a maximum of " + getMaxGuesses() + " guesses. " +
                "\nFor each guess please return a validation code indicating if the digits of the" +
                " mystery code are correct (=), bigger(>) or smaller(<).\n\n");

        System.out.print("Choose a secret code made of " + getNbDigits() + " digit: ");
        setCodeToBeFound(codeInputUser());

        System.out.print("\n");

        while ((getNbGuesses() <= getMaxGuesses()) && (!isCodeFound())) {

            guessValidationUnit();

        }

        messageEndOfTheGame();

        MyTools.makeABreak(800);

        endingMenu();
    }


    /**
     * This method takes a guess and display the validation code
     */
    @Override
    public void guessValidationUnit() {
        Main.logger.trace("Entering guessValidationUnit()");
        if (getNbGuesses() == 1) {
            setCodeProposal(randomCodeGenerator()); //1st answer of computer is random
        }

        displayProposalComputer(getCodeProposal());

        inputValidation();
        setNewProposal();

    }


// _____________________________________________________________________________________________________________________
    //LOCAL METHOD(S)//

    /**
     * The method is used for the AI part. Set the initial range of each digit of the code to be found to min =0, max =9
     *
     * @return the instance field X
     * @see Game1ModeDefender#rangeCodeToBeFound   = X
     */
    private int[][] initRangeCodeToBeFound() {
        int [] [] rangeCodeToBeFound = new int[2][getNbDigits()];
        for (int x = 0; x < getNbDigits(); x++) {
            rangeCodeToBeFound[0][x] = 0;
            rangeCodeToBeFound[1][x] = 9;
        }
        return rangeCodeToBeFound;

    }


    /**
     * This method displays the proposal code of the computer in the expected format
     *
     * @param codeProposal guess attempt
     */
    private void displayProposalComputer(int[] codeProposal) {
        System.out.print("Superbrain's guess #" + getNbGuesses() + " ------------------------->  ");
        for (int x : codeProposal) {
            System.out.print(x + " ");
        }
        System.out.println();
    }


    /**
     * This method is used for the AI part. It inputs and tests the validation code entered by the user and make an appropriate next guess
     *
     * @see #updateRangeCodeToBeFound(int[][], int[], String[])
     * @see #newProposal(int[][], int[], String[])
     */
    private void inputValidation() {
        Main.logger.trace("Entering inputValidation()");

        // PART 1: INPUT AND TEST

        System.out.print("Enter the validation code (<, >, =): ---------->  ");

        String validationByUser = scan.nextLine();
        Main.logger.debug("Validation code = " + validationByUser);
        System.out.print("\n");

        validationByComputerStringArray = validation(getCodeToBeFound(), getCodeProposal());
        String validationByComputer = validationByComputerStringArray[0];
        for (int j = 1; j < getNbDigits(); j++) {
            validationByComputer += validationByComputerStringArray[j];
        }

        while (!validationByUser.equals(validationByComputer)) {
            System.out.println("Oops!... it looks like the validation code is wrong. Enter it again: ");
            Main.logger.debug("Entry not valid.");
            validationByUser = scan.nextLine();
            Main.logger.debug("New validation code = " + validationByUser);

            System.out.print("\n");
        }

    }

        // PART 2: NEW GUESS

        private void setNewProposal() {


            rangeCodeToBeFound = updateRangeCodeToBeFound(rangeCodeToBeFound, getCodeProposal(), validationByComputerStringArray);

            setCodeProposal(newProposal(rangeCodeToBeFound, getCodeProposal(), validationByComputerStringArray));

            //   Conditions to exit the loop
            testIsCodeFound(validationByComputerStringArray);
            setNbGuesses(getNbGuesses() + 1);
        }

        // HELP! I TRIED TO SPLIT THIS METHOD IN 2 METHODS (PART 1 AND PART 2), BUT THEN IT DIDN'T WORK ANYMORE. CAN'T UNDERSTAND WHY!
       /*
       Also, if  String [] validationByComputerStringArray =  super.validation(getCodeToBeFound(), getCodeProposal());
       Why is "returns validationByComputerStringArray" different than "return super.validation(getCodeToBeFound(), getCodeProposal())"
        */




    /**
     * The method is used for the AI part. Restrict the range of each digit of the code to be found after each validation of a proposal code
     *
     * @param range      this is range before validation
     * @param proposal   the proposal code
     * @param validation the validation code
     * @return the updated (restricted) range after validation
     * @see Game1ModeDefender#codeProposal
     * @see Game1ModeDefender#validation(int[], int[])
     * @see Game1ModeDefender#rangeCodeToBeFound
     */
    private int[][] updateRangeCodeToBeFound(int[][] range, int[] proposal, String[] validation) {
        for (int x = 0; x < getNbDigits(); x++) {
            if (validation[x].equals(">")) {
                range[0][x] = proposal[x] + 1;
            }
            if (validation[x].equals("<")) {
                range[1][x] = proposal[x] - 1;
            }
        }
        return range;
    }


    /**
     * The method is used for the AI part. It updates the instance field {@link #codeProposal} used for the next guess of the computer
     *
     * @param newRange   the output of {@link #updateRangeCodeToBeFound(int[][], int[], String[])}
     * @param proposal   the instance field {@link #codeProposal} to be updated
     * @param validation {@link #validation(int[], int[])}
     * @return an updated instance field {@link #codeProposal}
     */
    private int[] newProposal(int[][] newRange, int[] proposal, String[] validation) {
        for (int x = 0; x < getNbDigits(); x++) {
            if (!validation[x].equals("=")) {
                proposal[x] = (newRange[0][x] + newRange[1][x]) / 2;
            }
        }
        return proposal;
    }

}