package com.berthoud.project3oc.gameclasses;
import com.berthoud.project3oc.Duration;
import com.berthoud.project3oc.MyTools;


/**
 * The program Game1ModeChallenger implements the first game (+/-) in the mode challenger: the user tries to break the computer's code
 */

public class Game1ModeChallenger extends Game1 implements Duration {


    private int startTime;

    private int endTime;

// _____________________________________________________________________________________________________________________
    //CONSTRUCTORS//

    /**
     * Constructor (constructors are chained)
     *
     * @param nbDigits   number of digits
     * @param maxGuesses max number of guesses allowed
     */
    public Game1ModeChallenger(int nbDigits, int maxGuesses) {
        super(nbDigits, maxGuesses);
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

        startTime = (int) System.currentTimeMillis();

        setCodeFound(false);

        setCodeToBeFound(randomCodeGenerator());

        System.out.printf("%S", ">>>>> The +/- game, mode challenger <<<<<\n");

        MyTools.makeABreak(400);

        System.out.println("Your goal is to find a mystery code made of " + getNbDigits() + " digits. " +
                "You have a maximum of " + getMaxGuesses() + " guesses. \nFor each guess the computer will return a validation code indicating if the digits of the" +
                " mystery code are bigger(+) or smaller(-) than your proposal.\n");

        displayModeDev(getCodeToBeFound());

        while ((getNbGuesses() <= getMaxGuesses()) && (!isCodeFound())) {

            guessValidationUnit();
        }

        endTime = (int) System.currentTimeMillis();

        duration();

        messageEndOfTheGame();

        MyTools.makeABreak(800);

        endingMenu();

    }

    /**
     * This method displays the result of the game at the end of the game.
     * The implementation is different for each game and mode.
     */
    @Override
    void messageEndOfTheGame() {

        if (this.isCodeFound()) {
            System.out.print("Congratulations!!!! You found out the mystery code in " + duration() + " seconds.");
        } else {
            System.out.print("It looks like you couldn't find out the mystery code... It was: ");
            for (int digit : getCodeToBeFound()) {
                System.out.print(digit + " ");
            }
        }
    }

    @Override
    public int duration() {

        return ((endTime-startTime) /1000);
    }

    // _____________________________________________________________________________________________________________________
    //LOCAL METHOD(S)//

    /**
     * This method takes a guess and displays the validation code
     */
    protected void guessValidationUnit() {
        System.out.print("Your guess #" + getNbGuesses() + ". Enter your proposal: ");

        setCodeProposal(codeInputUser());
        String[] validation = validation(getCodeToBeFound(), getCodeProposal());

        MyTools.makeABreak(300);

        System.out.println("Validation -----------------------> " + MyTools.arrayToString(validation) + "\n");

        testIsCodeFound(validation);
        incrementNbGuesses();

    }



}

