package com.berthoud.oc_project3_gameclasses;


import java.util.Arrays;


/**
 * The program Game2ModeDefender implements the Mastermind in the mode defender: the computer tries to break the player's code
 */
public class Game2ModeDefender extends Game2 {

    /**
     * This field is required for the AI part. This byte array stores all the combinaisons possible
     * based on {@link #nbVariations} and {@link #nbDigits}
     */
    protected byte [][] poolCombinations;


    /**
     * This is the number of combinations
     */
    protected int nbCombinations;


// _____________________________________________________________________________________________________________________
    //CONSTRUCTORS//
    public Game2ModeDefender(int nbDigits, int maxGuesses, int nbVariations) {
        super(nbDigits, maxGuesses, nbVariations);
        this.poolCombinations =null;
        this.nbCombinations =0;
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

        System.out.print("Choose a combination of " + getNbDigits() + " digits from 0 to " + (getNbVariations() -1) + ". " +
                "Superbrain the computer will try to find it, with a maximum of " +getMaxGuesses()+ " guesses. " +
                "\nFor each of Superbrain's guess please enter:\n" +
                "- the number of digits found (right value and right position)\n" +
                "- the number of digits present (right value but wrong position)\n\n");

        System.out.print("Please enter your secret combination: ");
        setCodeToBeFound(super.codeInputUser());


        while ((super.getNbGuesses() <= super.getMaxGuesses()) && (!super.testIsCodeFound())) {

            guessValidationUnit();

        }

        messageEndOfTheGame();

        MyTools.makeABreak(800);

        endingMenu();

    }


    /**
     * This method displays the proposal code of the computer in the expected format
     * @param codeProposal
     */
    protected void displayProposalComputer(int[] codeProposal) {
        String[] codeProposalStringArray = new String[getNbDigits()];
        for (int x = 0; x < getNbDigits(); x++) {
            codeProposalStringArray[x] = String.valueOf(codeProposal[x]);
        }
        System.out.print ("Superbrain's guess #" + getNbGuesses() + " --------------------------------> ");
        System.out.println(MyTools.arrayToString(codeProposalStringArray));

    }


    /**
     * This method is used for the AI part. It tests and inputs the validation code entered by the user and makes an appropriate next guess
     * Description of the algorithm
     */
    protected void answerComputer() {

        // Réduction du pool de combinaisons possibles à celles qui auraient donné le même résultat que celui obtenu
        // Pour ce faire, création dún tableau de bytes dans lequel on va enregsitrer le résultat que donnerait chaque combi
        // si c'était la bonne, ce résulat est ensuite comparé au résultat obtenu. Si il différe, le premier chiffre de
        // la combi est changé en "-1"


        int [][]testPoolCombi = new int [nbCombinations][2];

        // Fixer la les valeurs bienplacé et présente pour pouvoir les comparer ensuite aux retours de chaque combi:
        int [] validation = new int[2];
        validation [0] = super.validation(getCodeToBeFound(), getCodeProposal())[0];
        validation [1] = super.validation(getCodeToBeFound(), getCodeProposal())[1];

        byte [] tentativeCodeByte =new byte [getNbDigits()];
        for (int i = 0; i< getNbDigits(); i++){
            tentativeCodeByte[i] = (byte)(getCodeProposal()[i]);
        }


        for (int i = 0; i< nbCombinations; i++){
           testPoolCombi[i] = validation(poolCombinations[i], getCodeProposal());
            if (Arrays.equals(poolCombinations[i], tentativeCodeByte)) {
                poolCombinations[i][0] = -1;
            }
           if (!Arrays.equals(testPoolCombi[i], (validation))){
               poolCombinations[i][0] = -1;
           }
        }


        for (int i = 0; i< nbCombinations; i++){
            if (poolCombinations[i][0] != -1){
                for (int k = 0; k < getNbDigits(); k++){
                    setCodeProposal(k, (int)(poolCombinations[i][k]));
                }break;
            }
        }

        super.testIsCodeFound();

        }


    protected void saisieValidationJoueur() {

        //Validation ordi pour vérifier entrées du joueur
        int[] validationOrdi = super.validation(getCodeToBeFound(), getCodeProposal());

        int bienPlaceJoueur;
        int couleursOKJoueur;

        System.out.print("Nombre de couleurs bien placées = ");
        bienPlaceJoueur = scan.nextInt();

        while (bienPlaceJoueur != validationOrdi[0]) {
            System.out.println("Saisie incorrecte. Entre à nouveau le nb de couleurs bien placées:");
            bienPlaceJoueur = scan.nextInt();
        }

        System.out.print("Nombre de couleurs présentes = ");
        couleursOKJoueur = scan.nextInt();

        while (couleursOKJoueur != validationOrdi[1]) {
            System.out.println("Saisie incorrecte. Entre à nouveau le nb de couleurs présentes:");
            couleursOKJoueur = scan.nextInt();
        }

        System.out.println();


    }

    private byte[] remplissagePoolCombinaisons(byte[] tab) {
        byte[] nextTab = Arrays.copyOf(tab, tab.length);

        for ( int i = tab.length-1; i >= 0 ; i--) {

            if (tab[i] != getNbVariations() -1) {
                nextTab[i] = (byte) (tab[i] + 1);
                break;
            } else nextTab[i] = 0;

        }

        return nextTab;
    }

    public void creationPoolCombinaisons(){
        nbCombinations = (int) Math.pow(getNbVariations(), getNbDigits());
        poolCombinations = new byte[nbCombinations][getNbDigits()];


        for (int i = 0; i < (getNbDigits()); i++) {
            poolCombinations[0][i] = 0;
        }

        for (int j = 1; j < nbCombinations; j++) {
            poolCombinations[j] = remplissagePoolCombinaisons(poolCombinations[j-1]);

        }
    }


    @Override
    protected void guessValidationUnit() {

        if (getNbGuesses() == 1) {
            setCodeProposal(randomCodeGenerator()); //1st answer of computer is random
            creationPoolCombinaisons();

        }

        // affichage du code sous forme d'une ligne:
        displayProposalComputer(getCodeProposal());

        // saisie validation par le joueur:
        saisieValidationJoueur();

        super.testIsCodeFound();
        if(!super.testIsCodeFound()) {
            // Fait une réponse en retour à la validation joueur
            answerComputer();
        }


        setNbGuesses(getNbGuesses()+1);
    }
}