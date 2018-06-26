package com.berthoud;

public class Game1ChallengerIsUser extends Game1 {

    public Game1ChallengerIsUser(int nbDigits, int maxGuesses) {
        super(nbDigits, maxGuesses);
    }

    /**
     * This method organizes the flow of the game
     */
    public void play() {
        codeToBeFound = super.randomCodeGenerator();

        System.out.println(">>>>> Let's get started with the +/- game! <<<<<" );

        makeABreak(600);

        System.out.println("Your goal is to find a mystery code made of " + nbDigits + " digits. " +
                "You have a maximum of " +maxGuesses+ " guesses. \nFor each guess the computer will return a validation code indicating if the digits of the" +
                " mystery code are bigger(>) or smaller(<) than your proposal.\n");

        while ((super.nbGuesses <= super.maxGuesses) && (!super.isCodeFound)) {
            System.out.print("Guess #" + nbGuesses + ". Enter your proposal: ");

            codeProposal = super.codeInputUser();
            String[] validation = super.validation(codeToBeFound, codeProposal);

            makeABreak(200);

            System.out.println("Validation ------------------> "+ arrayToString(validation) + "\n");

            super.testIsCodeFound(validation);
            super.nbGuesses++;
        }

        endOfTheGame();


    }
}
