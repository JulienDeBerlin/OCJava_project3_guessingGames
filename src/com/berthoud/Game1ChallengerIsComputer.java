package com.berthoud;

import java.util.Scanner;

public class Game1ChallengerIsComputer extends Game1 {

    private int[][] rangeCodeToBeFound = new int[2][nbDigits];// Array 1 = minValue, Array 2 = maxValue


    public Game1ChallengerIsComputer(int nbDigits, int maxGuesses) {
        super(nbDigits, maxGuesses);
        rangeCodeToBeFound = initRangeCodeToBeFound();
    }

    @Override
    public void play() {

        nbGuesses = 1;
        isCodeFound = false;

        System.out.print(">>>>> The +/- game, mode defender <<<<<\n" );

        MyTools.makeABreak(400);

        System.out.print("You choose a mystery code made of " + nbDigits + " digits. " +
                "Superbrain the computer will try to break it, with a maximum of " +maxGuesses+ " guesses. " +
                "\nFor each guess please return a validation code indicating if the digits of the" +
                " mystery code are correct (=), bigger(>) or smaller(<).\n\n");

        System.out.print("Choose a secret code made of " + nbDigits + " digit: ");
        codeToBeFound = super.codeInputUser();

        System.out.print("\n");


        while ((super.nbGuesses <= super.maxGuesses) && (!super.isCodeFound)) {


            guessValidationUnit();

        }

        messageEndOfTheGame();

        MyTools.makeABreak(800);

        endingMenu();
    }


    @Override
    protected void messageEndOfTheGame() {

        if (this.isCodeFound) {
            System.out.print("Superbrain made it!");
        } else {
            System.out.print("How does it feel to defeat Superbrain? ");
        }

    }


    /**
     * The methods is used for the AI part. Set the initial range of each digit of the code to be found to min =0, max =9
     * @return the instance field X
     * @see Game1ChallengerIsComputer#rangeCodeToBeFound   = X
     */
    private int[][] initRangeCodeToBeFound() {
        for (int x = 0; x < nbDigits; x++) {
            rangeCodeToBeFound[0][x] = 0;
            rangeCodeToBeFound[1][x] = 9;
        }
        return rangeCodeToBeFound;
    }

    /**
     * The methods is used for the AI part. Restrict the range of each digit of the code to be found after each validation of a proposal code
     * @param range this is range before validation
     * @param proposal the proposal code
     * @param validation the validation code
     * @return the updated (restricted) range after validation
     * @see Game1ChallengerIsComputer#codeProposal
     * @see Game1ChallengerIsComputer#validation(int[], int[])
     * @see Game1ChallengerIsComputer#rangeCodeToBeFound
     *
     */

    private int[][] updateRangeCodeToBeFound(int[][] range, int[] proposal, String[] validation) {
        for (int x = 0; x < nbDigits; x++) {
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
     * The methods is used for the AI part. It updates the instance field {@link #codeProposal} used for the next guess of the computer
     * @param newRange   the output of {@link #updateRangeCodeToBeFound(int[][], int[], String[])}
     * @param proposal   the instance field {@link #codeProposal} to be updated
     * @param validation {@link #validation(int[], int[])}
     * @return  an updated instance field {@link #codeProposal}
     */
    private int[] newProposal(int[][] newRange, int[] proposal, String[] validation) {
        for (int x = 0; x < nbDigits; x++) {
            if (validation[x] != "=") {
                proposal[x] = (newRange[0][x] + newRange[1][x]) / 2;
            }
        }
        return proposal;
    }


    /**
     * This methods is used for the AI part. It tests and inputs the validation code entered by the user and make an appropriate next guess
     * @param validationByUser
     * @see #updateRangeCodeToBeFound(int[][], int[], String[])
     * @see #newProposal(int[][], int[], String[])
     */
    protected void answerComputer(String validationByUser){
        String[] validationByComputerStringArray = super.validation(codeToBeFound, codeProposal);
            String validationComputer = validationByComputerStringArray[0];
            for (int j = 1; j < nbDigits; j++) {
                validationComputer += validationByComputerStringArray[j];
            }

            while (!validationByUser.equals(validationComputer)) {
                System.out.println("Oops!... it looks like the validation code is wrong. Enter it again: ");
                Scanner scan = new Scanner(System.in);
                validationByUser = scan.nextLine();
                System.out.print("\n");
            }

            rangeCodeToBeFound = updateRangeCodeToBeFound(rangeCodeToBeFound, codeProposal, validationByComputerStringArray);
            codeProposal = newProposal(rangeCodeToBeFound, codeProposal, validationByComputerStringArray);

            //   Conditions to exit the loop
            super.testIsCodeFound(validationByComputerStringArray);
            super.nbGuesses++;
    }


    /**
     * This methods displays the proposal code of the computer in the expected format
     * @param tentativeCode
     */
    protected void displayProposalComputer(int[] tentativeCode) {
        System.out.print("Superbrain's guess #" + nbGuesses + " ------------->  ");
        for (int x : tentativeCode) {
            System.out.print (x + " ");
        }
        System.out.println();
    }


    /**
     * This methods takes a guess and display the validation code
     */
    public void guessValidationUnit(){
        if (nbGuesses == 1) {
            codeProposal = randomCodeGenerator(); //1st answer of computer is random
        }

        displayProposalComputer(codeProposal);

        System.out.print("Enter the validation code (<, >, =):  ");

        String validationJoueur = scan.nextLine();
        System.out.print("\n");

        answerComputer(validationJoueur);
    }

}