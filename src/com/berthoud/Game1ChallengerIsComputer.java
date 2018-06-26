package com.berthoud;

import java.util.Scanner;

public class Game1ChallengerIsComputer extends Game1 {

    private int[][] rangeCodeToBeFound = new int[2][nbDigits];// Array 1 = minValue, Array 2 = maxValue


    public Game1ChallengerIsComputer(int pNombreCases, int pNombreEssaisMax) {
        super(pNombreCases, pNombreEssaisMax);
        rangeCodeToBeFound = initRangeCodeToBeFound();
    }

    /**
     * This method sets the flow of the game
     */
    public void play() {

        System.out.println("Choisis une combinaison de " + nbDigits + " chiffres.");
        codeToBeFound = super.codeInputUser();

        codeProposal = super.randomCodeGenerator(); //1ere réponse de l'ordinateur = chiffre aléatoire

        while ((super.nbGuesses <= super.maxGuesses) && (!super.isCodeFound)) {

            // affichage du code sous forme d'une ligne:
            displayProposalComputer(codeProposal);

            // saisie validation (+/-) par le joueur:
            System.out.println("A toi de saisir le code de validation (+/-)");

            String validationJoueur = scan.nextLine();

            // vérifie que la validation (+/-) entrée par le joueur est bien correcte et ensuite fait une réponse en retour
            answerComputer(validationJoueur);

        }

        // CE CODE DOIT ETRE ENCORE FACTORISE
        if (super.isCodeFound) {
            System.out.println("Superbrain a gagné!");
        } else {
            System.out.println("Tu as vaincu Superbrain!");
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
     * This methods displays the proposal code of the computer in the expected format
     * @param tentativeCode
     */
    protected void displayProposalComputer(int[] tentativeCode) {
         System.out.print("Superbrain te répond: ");
        for (int x : tentativeCode) {
            System.out.print (x + " ");
        }
        System.out.println();
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
                System.out.println("Oups... on dirait que le code (+/-) est erroné. Saisi le à nouveau.");
                Scanner scan = new Scanner(System.in);
                validationByUser = scan.nextLine();
            }

            rangeCodeToBeFound = updateRangeCodeToBeFound(rangeCodeToBeFound, codeProposal, validationByComputerStringArray);
            codeProposal = newProposal(rangeCodeToBeFound, codeProposal, validationByComputerStringArray);

            //   Définition des conditions de sortie de boucle
            super.testIsCodeFound(validationByComputerStringArray);
            super.nbGuesses++;
    }

}