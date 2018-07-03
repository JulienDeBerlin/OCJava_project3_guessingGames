package com.berthoud.oc_project3_gameclasses;

import java.util.Arrays;

public class Game2ModeDefender extends Game2 {

    protected byte [][] poolCombinations;
    protected int nbCombinations;

    public Game2ModeDefender(int nbDigits, int maxGuesses, int nbVariations) {
        super(nbDigits, maxGuesses, nbVariations);
        this.poolCombinations =null;
        this.nbCombinations =0;
    }

    // il faudrait faire en sorte que l'on ne puisse pas passer un nb de couleurs supérieur à 10

    public void play() {

        System.out.println("Choisis une combinaison de " + getNbDigits() + " chiffres compris entre 0 et " + (getNbVariations() -1));
        setCodeToBeFound(super.codeInputUser());

        setCodeProposal(super.randomCodeGenerator()); //1ere réponse de l'ordinateur = chiffre aléatoire


        // création d'un pool de l'ensemble des combinaisons possibles sous la forme d'un tableau multidimensionnel de bytes
        // chaque ligne inclut une combinaison + 2 bytes pour validation (bien placés/digitsPresent)


        creationPoolCombinaisons();


        while ((super.getNbGuesses() <= super.getMaxGuesses()) && (!super.testIsCodeFound())) {

            // affichage du code sous forme d'une ligne:
            displayTentativeCodeOrdinateur(getCodeProposal());

            // saisie validation par le joueur:
            saisieValidationJoueur();

            super.testIsCodeFound();
            if(!super.testIsCodeFound()) {
                // Fait une réponse en retour à la validation joueur
                reponseValidationJoueur();
            }


            setNbGuesses(getNbGuesses()+1);

        }

        // CE CODE DOIT ETRE ENCORE FACTORISE
        if (super.testIsCodeFound()) {
            System.out.println("Superbrain a gagné!");
        } else {
            System.out.println("Tu as vaincu Superbrain!");
        }

    }


    // affichage d'un int array code sous forme d'une ligne:
    protected void displayTentativeCodeOrdinateur(int[] tentativeCode) {
        String[] tentativeCodeStringArray = new String[getNbDigits()];
        for (int x = 0; x < getNbDigits(); x++) {
            tentativeCodeStringArray[x] = String.valueOf(tentativeCode[x]);
        }
        System.out.println("Superbrain te répond:");
        System.out.println(super.arrayToString(tentativeCodeStringArray));

    }


    protected void reponseValidationJoueur() {

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

    protected void creationPoolCombinaisons(){
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
    protected void messageEndOfTheGame() {

    }
}