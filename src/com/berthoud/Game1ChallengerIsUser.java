package com.berthoud;

public class Game1ChallengerIsUser extends Game1 {

    public Game1ChallengerIsUser(int nbDigits, int maxGuesses) {
        super(nbDigits, maxGuesses);
    }


    @Override
    public void play() {

        nbGuesses = 1;
        isCodeFound = false;

        codeToBeFound = super.randomCodeGenerator();

        System.out.println(">>>>> The +/- game, mode challenger <<<<<");

        MyTools.makeABreak(400);

        System.out.println("Your goal is to find a mystery code made of " + nbDigits + " digits. " +
                "You have a maximum of " + maxGuesses + " guesses. \nFor each guess the computer will return a validation code indicating if the digits of the" +
                " mystery code are bigger(>) or smaller(<) than your proposal.\n");

        while ((super.nbGuesses <= super.maxGuesses) && (!super.isCodeFound)) {

            guessValidationUnit();
        }

        messageEndOfTheGame();

        MyTools.makeABreak(800);

        endingMenu();


    }

        @Override
        protected void messageEndOfTheGame () {

            if (this.isCodeFound) {
                System.out.print("Congratulations!!!! You found out the mystery code!");
            } else {
                System.out.print("It looks like you couldn't find out the mystery code... It was: ");
                for (int digit : codeToBeFound) {
                    System.out.print(digit + " ");
                }
            }

        }


    /**
     * This methods takes a guess and display the validation code
     */
    public void guessValidationUnit() {
            System.out.print("Guess #" + nbGuesses + ". Enter your proposal: ");

            codeProposal = super.codeInputUser();
            String[] validation = super.validation(codeToBeFound, codeProposal);

            MyTools.makeABreak(500);

            System.out.println("Validation ------------------> " + arrayToString(validation) + "\n");

            super.testIsCodeFound(validation);
            super.nbGuesses++;

        }
    }



