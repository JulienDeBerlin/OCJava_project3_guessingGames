package com.berthoud.oc_project3_gameclasses;


/**
 * The program Game1ModeChallenger implements the Mastermind in the mode challenger: the user tries to break the computer's code
 */
 public class Game2ModeChallenger extends Game2 {


// _____________________________________________________________________________________________________________________
    //CONSTRUCTORS//

    /**
     * Constructor
     * @param nbDigits number of digits of the code
     * @param maxGuesses max number of guesses allowed
     * @param nbVariations number of value possible for each digit. Min = 4 , Max = 10
     */
    public Game2ModeChallenger(int nbDigits, int maxGuesses, int nbVariations) {
        super(nbDigits, maxGuesses, nbVariations);
    }


// _____________________________________________________________________________________________________________________
    //IMPLEMENTATION OF ABSTRACT METHODS//

    /**
     * This method is a wrapper-method that starts and executes the game until the end.
     * All the methods required for the execution of the game are called within this wrapper-method.
     * The implementation is different for each game and mode.
     */
    @Override
    public void play() {

        setNbGuesses(1);
        setCodeFound(false);

        setCodeToBeFound(super.randomCodeGenerator());

        System.out.printf("%S", ">>>>> The digit Mastermind, mode challenger <<<<<\n");

        MyTools.makeABreak(400);

        System.out.println("The goal is to find out the secret combination made of " + getNbDigits() + " digits, " +
                "given that each digit can take one of "+ getNbVariations() + " possible values.\n" +
                "You have a maximum of " + getMaxGuesses() + " guesses. For each guess the computer will return two values:\n" +
                "- the number of digits found (right value and right position)\n" +
                "- the number of digits present (right value but wrong position)\n");


        displayModeDev(getCodeToBeFound());

        while ((super.getNbGuesses() <= super.getMaxGuesses()) && (!super.testIsCodeFound())) {
          guessValidationUnit();

        }

        messageEndOfTheGame();

        MyTools.makeABreak(800);

        endingMenu();

    }


    /**
     * This method displays the result of the game at the end of the game.
     * TThe implementation is different for each game and mode.
     */
     @Override
     protected void messageEndOfTheGame() {
         if (this.isCodeFound()) {
             System.out.print("Well done!!!! You found out the secret combination!");
         } else {
             System.out.print("Sorry, you didn't make it this time... The combination was: ");
             for (int digit : getCodeToBeFound()) {
                 System.out.print(digit + " ");
             }
         }

     }


// _____________________________________________________________________________________________________________________
    //LOCAL METHOD(S)//

    /**
     * This method takes a guess and displays the validation code
     */
    public void guessValidationUnit() {
        System.out.print("Guess #" + getNbGuesses() + ". Enter your proposal: ");

        setCodeProposal(codeInputUser());


        super.validation(getCodeToBeFound(), getCodeProposal());

        System.out.println("----------->"+ getDigitsFound() + " digit(s) found and " + getDigitsPresent() + " digit(s) present");

        super.testIsCodeFound();
        super.setNbGuesses(getNbGuesses()+1);

    }



 }
