package com.berthoud.project3oc.gameclasses;
import com.berthoud.project3oc.menu.Main;

import java.util.Arrays;


/**
 * The program Game2ModeDefender implements the Mastermind in the mode defender: the computer tries to break the player's code
 */
public class Game2ModeDefender extends Game2 {



    /**
     * This field is required for the AI part. This byte array stores all the combinations possible
     * based on {@link #nbVariations} and {@link #nbDigits}
     */
    private byte[][] poolCombinations;


    /**
     * This is the number of combinations
     */
    private int nbCombinations;


    // _____________________________________________________________________________________________________________________
    //CONSTRUCTORS//
    public Game2ModeDefender(int nbDigits, int maxGuesses, int nbVariations) {
        super(nbDigits, maxGuesses, nbVariations);
        this.poolCombinations = null;
        this.nbCombinations = 0;
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

        System.out.printf("%S", ">>>>> The digit Mastermind, mode defender <<<<<\n");

        MyTools.makeABreak(400);

        System.out.print("Choose a combination of " + getNbDigits() + " digits from 0 to " + (getNbVariations() - 1) + ". " +
                "Superbrain the computer will try to find it, with a maximum of " + getMaxGuesses() + " guesses. " +
                "\nFor each of Superbrain's guess please enter:\n" +
                "- the number of digits found (right value and right position)\n" +
                "- the number of digits present (right value but wrong position)\n\n");

        System.out.print("Please enter your secret combination: ");
        setCodeToBeFound(codeInputUser());


        while ((getNbGuesses() <= getMaxGuesses()) && (!testIsCodeFound())) {

            guessValidationUnit();

        }

        messageEndOfTheGame();

        MyTools.makeABreak(800);

        endingMenu();

    }


    /**
     * This method takes a guess and display the validation code. For more information about the AI:
     *
     * @see #answerComputer()
     * @see #creationPoolCombinations()
     */
    @Override
    protected void guessValidationUnit() {

        if (getNbGuesses() == 1) {
            setCodeProposal(randomCodeGenerator()); //1st answer of computer is random
            creationPoolCombinations();

        }

        displayProposalComputer(getCodeProposal());

        inputValidation();

        testIsCodeFound();
        if (!testIsCodeFound()) {
            answerComputer();
        }

        setNbGuesses(getNbGuesses() + 1);
    }


// _____________________________________________________________________________________________________________________
    //LOCAL METHOD(S)//

    /**
     * This method displays the proposal code of the computer in the expected format
     *
     * @param codeProposal the guess as an int array
     */
    private void displayProposalComputer(int[] codeProposal) {
        String[] codeProposalStringArray = new String[getNbDigits()];
        for (int x = 0; x < getNbDigits(); x++) {
            codeProposalStringArray[x] = String.valueOf(codeProposal[x]);
        }
        System.out.print("Superbrain's guess #" + getNbGuesses() + " --------------------------------> ");
        System.out.println(MyTools.arrayToString(codeProposalStringArray));

    }


    /**
     * This method inputs and tests the validation code entered by the user.
     */
    private void inputValidation() {

        int[] validationComputer = validation(getCodeToBeFound(), getCodeProposal());

        //*******************
        System.out.print("Enter the number of digits found = ");
        String digitsFoundPlayerString = scan.nextLine();
        Main.logger.debug("Input user: number of digits found = "+ digitsFoundPlayerString);

        while (!MyTools.isMyStringAnInt(digitsFoundPlayerString)) {
            System.out.println("Oops, wrong entry. Enter again the number of digits found:");
            Main.logger.debug("Entry not valid.");

            digitsFoundPlayerString = scan.nextLine();
            Main.logger.debug("New input user: number of digits found = "+ digitsFoundPlayerString);

        }

        int digitsFoundPlayerInt = Integer.parseInt(digitsFoundPlayerString);

        while (digitsFoundPlayerInt != validationComputer[0]) {
            System.out.println("Oops, wrong entry. Enter again the number of digits found:");
            Main.logger.debug("Entry not valid.");

            digitsFoundPlayerInt = scan.nextInt();
            scan.nextLine();
            Main.logger.debug("New input user: number of digits found = "+ digitsFoundPlayerString);

        }

        //********************
        System.out.print("Enter the number of digits present = ");
        String digitsPresentPlayerString = scan.nextLine();
        Main.logger.debug("Input user: number of digits present = "+ digitsPresentPlayerString);


        while (!MyTools.isMyStringAnInt(digitsPresentPlayerString)) {
            System.out.println("Oops, wrong entry. Enter again the number of digits present:");
            Main.logger.debug("Entry not valid.");

            digitsPresentPlayerString = scan.nextLine();
            Main.logger.debug("New input user: number of digits present = "+ digitsPresentPlayerString);

        }

        int digitsPresentPlayerInt = Integer.parseInt(digitsPresentPlayerString);

        while (digitsPresentPlayerInt != validationComputer[1]) {
            System.out.println("Oops, wrong entry. Enter again the number of digits present:");
            Main.logger.debug("Entry not valid.");

            digitsPresentPlayerInt = scan.nextInt();
            scan.nextLine();
            Main.logger.debug("New input user: number of digits present = "+ digitsPresentPlayerString);

        }

        System.out.println();

    }


    /**
     * This method is used for the AI part. It generates an appropriate next guess based on the validation of the previous guess.
     * It is inspired by <a href="https://en.wikipedia.org/wiki/Mastermind_(board_game)">Knuth's algorithm</a>.
     * The algorithm here works as follows:
     * <p>
     * 1. creation of a pool of all possible combinations, stored as a multidimensional byte array
     * {@link #creationPoolCombinations()}
     * {@link #fillingPoolCombinations(byte[])}
     * <p>
     * 2. Computer makes a 1st guess and receives a validation answer by user
     * {@link #inputValidation()}
     * <p>
     * 3. the next step is to check which validation this same guess would have return for each combination of the pool.
     * <p>
     * 4. all these hypothetical validations are compared to the original user validation. If they are different, it means that
     * the hypothetical combination that returned the validation can not be the right combination. It is also put aside from the pool
     * of combination. To identify the combinations put aside the program overides their first digit with the value -1
     * <p>
     * 5. For the next guess, the program takes the first combination of the pool which do not start with -1
     *
     */
    private void answerComputer() {

        // 3. the next step is to check which validation the  computer's guess would have return for each combination of the pool.

        int[][] poolValidations = new int[nbCombinations][2];

        int[] validation = new int[2];
        validation[0] = super.validation(getCodeToBeFound(), getCodeProposal())[0];
        validation[1] = super.validation(getCodeToBeFound(), getCodeProposal())[1];

        byte[] tentativeCodeByte = new byte[getNbDigits()];
        for (int i = 0; i < getNbDigits(); i++) {
            tentativeCodeByte[i] = (byte) (getCodeProposal()[i]);
        }


        //  4. all these hypothetical validations are compared to the original user validation. If they are different, it means that
        // the hypothetical combination that returned the validation can not be the right combination. It is also put aside from the pool
        // combination. To identify the combinations put aside the program overrides their first digit with the value -1

        for (int i = 0; i < nbCombinations; i++) {
            poolValidations[i] = validation(poolCombinations[i], getCodeProposal());
            if (Arrays.equals(poolCombinations[i], tentativeCodeByte)) {
                poolCombinations[i][0] = -1;
            }
            if (!Arrays.equals(poolValidations[i], (validation))) {
                poolCombinations[i][0] = -1;
            }
        }

        // 5. For the next guess, the program takes the first combination of the pool which do not start with -1
        for (int i = 0; i < nbCombinations; i++) {
            if (poolCombinations[i][0] != -1) {
                for (int k = 0; k < getNbDigits(); k++) {
                    setCodeProposal(k, (int) (poolCombinations[i][k]));
                }
                break;
            }
        }

        testIsCodeFound();

    }


    /**
     * This method is used for the AI part. It creates a set of all possible combinations, stored as an array of byte arrays.
     * The algorithm for filling in the array is based on recursivity:
     * 1. assignment of values for the 1st byte array (= 1st combination)
     * 2. the values of byte array nr. N is based on the values of byte array nr. N-1.
     * A separate method {@link #fillingPoolCombinations(byte[])}takes byte array nr. N and gives back byte array nr. N+1
     */
    private void creationPoolCombinations() {
        nbCombinations = (int) Math.pow(getNbVariations(), getNbDigits());
        poolCombinations = new byte[nbCombinations][getNbDigits()];

        // 1. assignment of values for the 1st byte array (= 1st combination)
        for (int i = 0; i < (getNbDigits()); i++) {
            poolCombinations[0][i] = 0;
        }

        // 2. the values of byte array nr. N is based on the values of byte array nr. N-1
        for (int j = 1; j < nbCombinations; j++) {
            poolCombinations[j] = fillingPoolCombinations(poolCombinations[j - 1]);

        }
    }


    /**
     * This method is used for the AI part. It is used for creating {@link #creationPoolCombinations()}, a multidimentional byte array
     * storing all the combinations possible.
     *
     * @param tab the method is based on recursivity. It takes one combination stored as a byte array
     *            and returns the next combination stored as a byte array.
     * @return the next combination of the pool
     */
    private byte[] fillingPoolCombinations(byte[] tab) {
        byte[] nextTab = Arrays.copyOf(tab, tab.length);

        for (int i = tab.length - 1; i >= 0; i--) {

            if (tab[i] != getNbVariations() - 1) {
                nextTab[i] = (byte) (tab[i] + 1);
                break;
            } else nextTab[i] = 0;

        }

        return nextTab;
    }

}