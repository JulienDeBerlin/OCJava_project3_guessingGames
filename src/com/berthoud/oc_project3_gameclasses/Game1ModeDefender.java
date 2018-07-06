package com.berthoud.oc_project3_gameclasses;


/**
 * The program Game1ModeDefender implements the first game (+/-) in the mode defender: the user choose a code and the
 * computer tries to break it
 */

public class Game1ModeDefender extends Game1 {

// _____________________________________________________________________________________________________________________
    //INSTANCE FIELDS//
    /**
     * This field is need for the AI part of the program. It refers to the min and max possible value of each digit of the {@link #codeToBeFound)
     */
    private int[][] rangeCodeToBeFound = new int[2][getNbDigits()];// Array 1 = minValue, Array 2 = maxValue
   // Although the second part of the declaration is meant to be redundant by the IDE, the program throws a NullPointerException if I suppress it.
   // Same happens if I replace it by initRangeCodeToBeFound(). TBC why.

// _____________________________________________________________________________________________________________________
    //CONSTRUCTORS//

    /**
     * Constructor (constructors are chained)
     * @param nbDigits number of digits
     * @param maxGuesses max number of guesses allowed
     */
    public Game1ModeDefender(int nbDigits, int maxGuesses) {
        super(nbDigits, maxGuesses);
        rangeCodeToBeFound = initRangeCodeToBeFound();
    }

// _____________________________________________________________________________________________________________________
    //GETTERS//

    public int[][] getRangeCodeToBeFound() {
        return rangeCodeToBeFound;
    }

    public void setRangeCodeToBeFound(int[][] rangeCodeToBeFound) {
        this.rangeCodeToBeFound = rangeCodeToBeFound;
    }


    // _____________________________________________________________________________________________________________________
    //IMPLEMENTATION OF ABSTRACT METHODS//

    /**
     * This method is a wrapper-method that starts and executes the game until the end.
     * All the methods required for the execution of the game are called within this wrapper-method.
     * The implementation is different for each game and mode.
     */
    @Override
    public void play(){

        setNbGuesses(1);
        setCodeFound(false);

        System.out.printf("%S", ">>>>> The +/- game, mode defender <<<<<\n");

        MyTools.makeABreak(400);

        System.out.print("You choose a mystery code made of " + getNbDigits() + " digits. " +
                "Superbrain the computer will try to break it, with a maximum of " +getMaxGuesses()+ " guesses. " +
                "\nFor each guess please return a validation code indicating if the digits of the" +
                " mystery code are correct (=), bigger(>) or smaller(<).\n\n");

        System.out.print("Choose a secret code made of " + getNbDigits() + " digit: ");
        setCodeToBeFound(super.codeInputUser());

        System.out.print("\n");


        while ((super.getNbGuesses() <= super.getMaxGuesses()) && (!super.isCodeFound())) {


            guessValidationUnit();

        }

        messageEndOfTheGame();

        MyTools.makeABreak(800);

        endingMenu();
    }



// _____________________________________________________________________________________________________________________
    //LOCAL METHOD(S)//

    /**
     * The method is used for the AI part. Set the initial range of each digit of the code to be found to min =0, max =9
     * @return the instance field X
     * @see Game1ModeDefender#rangeCodeToBeFound   = X
     */
    private int[][] initRangeCodeToBeFound() {
        for (int x = 0; x < getNbDigits(); x++) {
            rangeCodeToBeFound[0][x] = 0;
            rangeCodeToBeFound[1][x] = 9;
        }
        return rangeCodeToBeFound;
    }

    /**
     * The method is used for the AI part. Restrict the range of each digit of the code to be found after each validation of a proposal code
     * @param range this is range before validation
     * @param proposal the proposal code
     * @param validation the validation code
     * @return the updated (restricted) range after validation
     * @see Game1ModeDefender#codeProposal
     * @see Game1ModeDefender#validation(int[], int[])
     * @see Game1ModeDefender#rangeCodeToBeFound
     *
     */
    private int[][] updateRangeCodeToBeFound(int[][] range, int[] proposal, String[] validation) {
        for (int x = 0; x < getNbDigits(); x++) {
            if (validation[x] == ">") {
                range[0][x] = proposal[x]+1;
            }
            if (validation[x] == "<") {
                range[1][x] = proposal[x]-1;
            }
        }
        return range;
    }


    /**
     * The method is used for the AI part. It updates the instance field {@link #codeProposal} used for the next guess of the computer
     * @param newRange   the output of {@link #updateRangeCodeToBeFound(int[][], int[], String[])}
     * @param proposal   the instance field {@link #codeProposal} to be updated
     * @param validation {@link #validation(int[], int[])}
     * @return  an updated instance field {@link #codeProposal}
     */
    private int[] newProposal(int[][] newRange, int[] proposal, String[] validation) {
        for (int x = 0; x < getNbDigits(); x++) {
            if (validation[x] != "=") {
                proposal[x] = (newRange[0][x] + newRange[1][x]) / 2;
            }
        }
        return proposal;
    }





    /**
     * This method displays the proposal code of the computer in the expected format
     * @param codeProposal
     */
    protected void displayProposalComputer(int[] codeProposal) {
        System.out.print("Superbrain's guess #" + getNbGuesses() + " ------------------------->  ");
        for (int x : codeProposal) {
            System.out.print (x + " ");
        }
        System.out.println();
    }


    /**
     * This method takes a guess and display the validation code
     */
    public void guessValidationUnit(){
        if (getNbGuesses() == 1) {
            setCodeProposal(randomCodeGenerator()); //1st answer of computer is random
        }

        displayProposalComputer(getCodeProposal());

        System.out.print("Enter the validation code (<, >, =): ---------->  ");

        String validationJoueur = scan.nextLine();
        System.out.print("\n");

        answerComputer(validationJoueur);
    }


    /**
     * This method is used for the AI part. It tests and inputs the validation code entered by the user and make an appropriate next guess
     * @param validationByUser
     * @see #updateRangeCodeToBeFound(int[][], int[], String[])
     * @see #newProposal(int[][], int[], String[])
     */
    protected void answerComputer(String validationByUser){
        String[] validationByComputerStringArray = super.validation(getCodeToBeFound(), getCodeProposal());
        String validationComputer = validationByComputerStringArray[0];
        for (int j = 1; j < getNbDigits(); j++) {
            validationComputer += validationByComputerStringArray[j];
        }

        while (!validationByUser.equals(validationComputer)) {
            System.out.println("Oops!... it looks like the validation code is wrong. Enter it again: ");
            validationByUser = scan.nextLine();
            System.out.print("\n");
        }

        rangeCodeToBeFound = updateRangeCodeToBeFound(rangeCodeToBeFound, getCodeProposal(), validationByComputerStringArray);
        setCodeProposal(newProposal(rangeCodeToBeFound, getCodeProposal(), validationByComputerStringArray));

        //   Conditions to exit the loop
        super.testIsCodeFound(validationByComputerStringArray);
        super.setNbGuesses(getNbGuesses()+1);

    }

}