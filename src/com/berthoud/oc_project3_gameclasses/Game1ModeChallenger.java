package com.berthoud.oc_project3_gameclasses;


public class Game1ModeChallenger extends Game1 {

// _____________________________________________________________________________________________________________________
    //CONSTRUCTORS//

    public Game1ModeChallenger(int nbDigits, int maxGuesses) {
        super(nbDigits, maxGuesses);
    }

// _____________________________________________________________________________________________________________________
    //IMPLEMENTATION OF ABSTRACT METHODS//

    @Override
    public void play() {

        setNbGuesses(1);
        setCodeFound(false);

        setCodeToBeFound(super.randomCodeGenerator());

        System.out.println(">>>>> The +/- game, mode challenger <<<<<");

        MyTools.makeABreak(400);

        System.out.println("Your goal is to find a mystery code made of " + getNbDigits() + " digits. " +
                "You have a maximum of " + getMaxGuesses() + " guesses. \nFor each guess the computer will return a validation code indicating if the digits of the" +
                " mystery code are bigger(>) or smaller(<) than your proposal.\n");

        displayModeDev(getCodeToBeFound());

        while ((super.getNbGuesses() <= super.getMaxGuesses()) && (!super.isCodeFound())) {

            guessValidationUnit();
        }

        messageEndOfTheGame();

        MyTools.makeABreak(800);

        endingMenu();

    }


    @Override
    protected void messageEndOfTheGame() {

        if (this.isCodeFound()) {
            System.out.print("Congratulations!!!! You found out the mystery code!");
        } else {
            System.out.print("It looks like you couldn't find out the mystery code... It was: ");
            for (int digit : getCodeToBeFound()) {
                System.out.print(digit + " ");
            }
        }
    }


// _____________________________________________________________________________________________________________________
    //LOCAL METHOD(S)//

    /**
     * This methods takes a guess and display the validation code
     */
    public void guessValidationUnit() {
        System.out.print("Guess #" + getNbGuesses() + ". Enter your proposal: ");

        setCodeProposal(super.codeInputUser());
        String[] validation = super.validation(getCodeToBeFound(), getCodeProposal());

        MyTools.makeABreak(500);

        System.out.println("Validation ------------------> " + arrayToString(validation) + "\n");

        super.testIsCodeFound(validation);
        super.setNbGuesses(getNbGuesses()+1);

    }
}



