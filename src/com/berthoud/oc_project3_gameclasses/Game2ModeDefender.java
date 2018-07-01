package com.berthoud.oc_project3_gameclasses;

import java.util.Arrays;

public class Game2ModeDefender extends Game2 {

    protected byte [][] poolCombinaisons;
    protected int nbCombinaisons;

    public Game2ModeDefender(int nbDigits, int maxGuesses, int nbVariations) {
        super(nbDigits, maxGuesses, nbVariations);
        this.poolCombinaisons=null;
        this.nbCombinaisons=0;
    }

    // il faudrait faire en sorte que l'on ne puisse pas passer un nb de couleurs supérieur à 10

    public void play() {

        System.out.println("Choisis une combinaison de " + getNbDigits() + " chiffres compris entre 0 et " + (getNbVariations() -1));
        setCodeToBeFound(super.saisieCodeJoueur());

        setCodeProposal(super.generateurCode()); //1ere réponse de l'ordinateur = chiffre aléatoire


        // création d'un pool de l'ensemble des combinaisons possibles sous la forme d'un tableau multidimensionnel de bytes
        // chaque ligne inclut une combinaison + 2 bytes pour validation (bien placés/digitsPresent)


        creationPoolCombinaisons();

//        nbCombinaisons = (int) Math.pow(nbVariations, nbDigits);
//        poolCombinaisons = new byte[nbCombinaisons][nbDigits];
//
//
//        for (int i = 0; i < (nbDigits); i++) {
//            poolCombinaisons[0][i] = 0;
//        }
//
//        for (int j = 1; j < nbCombinaisons ; j++) {
//            poolCombinaisons[j] = remplissagePoolCombinaisons(poolCombinaisons [j-1]);
//
//        }

//        // impression du pool de combis
//        for (int i = 0; i<nbCombinaisons; i++){
//            for (int k =0 ; k<nbDigits; k++){
//                System.out.print(poolCombinaisons[i][k] + " ");
//            }
//            System.out.println();
//        }
        //*****************************************************************//


        while ((super.getNbGuesses() <= super.getMaxGuesses()) && (!super.testAndGetCodeFound())) {

            // affichage du code sous forme d'une ligne:
            displayTentativeCodeOrdinateur(getCodeProposal());

            // saisie validation par le joueur:
            saisieValidationJoueur();

            super.testAndGetCodeFound();
            if(!super.testAndGetCodeFound()) {
                // Fait une réponse en retour à la validation joueur
                reponseValidationJoueur();
            }


            setNbGuesses(getNbGuesses()+1);

        }

        // CE CODE DOIT ETRE ENCORE FACTORISE
        if (super.testAndGetCodeFound()) {
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


        int [][]testPoolCombi = new int [nbCombinaisons][2];

        // Fixer la les valeurs bienplacé et présente pour pouvoir les comparer ensuite aux retours de chaque combi:
        int [] validation = new int[2];
        validation [0] = super.validation(getCodeToBeFound(), getCodeProposal())[0];
        validation [1] = super.validation(getCodeToBeFound(), getCodeProposal())[1];

        byte [] tentativeCodeByte =new byte [getNbDigits()];
        for (int i = 0; i< getNbDigits(); i++){
            tentativeCodeByte[i] = (byte)(getCodeProposal()[i]);
        }


        for (int i=0; i<nbCombinaisons; i++){
           testPoolCombi[i] = validation(poolCombinaisons[i], getCodeProposal());
            if (Arrays.equals(poolCombinaisons[i], tentativeCodeByte)) {
                poolCombinaisons [i][0] = -1;
            }
           if (!Arrays.equals(testPoolCombi[i], (validation))){
               poolCombinaisons [i][0] = -1;
           }
        }
//        System.out.println("validation[0] = " + validation[0] + " et validation[1] = " + validation[1]);
//
//        System.out.println("Liste des retours pour chaque combi");
//        for (int i = 0; i<nbCombinaisons; i++){
//            for (int k=0; k<2; k++){
//                System.out.print( testPoolCombi[i][k] + " ");
//            }        System.out.println();
//
//        }

        // impression du pool de combis
//        for (int i = 0; i<nbCombinaisons; i++){
//            for (int k =0 ; k<nbDigits; k++){
//                System.out.print(poolCombinaisons[i][k] + " ");
//            }
//            System.out.println();
//        }


        // La prochaine tentative de code sera la premiere des combis restantes
        // qui ne comment pas par -1

        for (int i =0; i<nbCombinaisons; i++){
            if (poolCombinaisons[i][0] != -1){
                for (int k = 0; k < getNbDigits(); k++){
                    setCodeProposal(k, (int)(poolCombinaisons [i][k]));
                }break;
            }
        }


        super.testAndGetCodeFound();

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
        nbCombinaisons = (int) Math.pow(getNbVariations(), getNbDigits());
        poolCombinaisons = new byte[nbCombinaisons][getNbDigits()];


        for (int i = 0; i < (getNbDigits()); i++) {
            poolCombinaisons[0][i] = 0;
        }

        for (int j = 1; j < nbCombinaisons ; j++) {
            poolCombinaisons[j] = remplissagePoolCombinaisons(poolCombinaisons [j-1]);

        }
    }

    @Override
    protected void messageEndOfTheGame() {

    }
}