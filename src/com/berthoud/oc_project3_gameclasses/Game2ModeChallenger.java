package com.berthoud.oc_project3_gameclasses;

 public class Game2ModeChallenger extends Game2 {

    public Game2ModeChallenger(int nbDigits, int maxGuesses, int nbVariations) {
        super(nbDigits, maxGuesses, nbVariations);
    }


    public void play() {

        // Génération d'un code aléatoire et initialisation de ce code sous forme d'un tableau
        setCodeToBeFound(super.generateurCode());

        // Saisie et validation code joueur
//        int nbGuesses = 0;

        displayModeDev(getCodeToBeFound());

        while ((super.getNbGuesses() <= super.getMaxGuesses()) && (!super.testAndGetCodeFound())) {
            System.out.println("Saisis ton code:");

            setCodeProposal(saisieCodeJoueur());


            super.validation(getCodeToBeFound(), getCodeProposal());


            super.printValidation();
            super.testAndGetCodeFound();
            setNbGuesses(getNbGuesses()+1);
        }

        // CE CODE DOIT ETRE ENCORE FACTORISE
        if (super.testAndGetCodeFound()) {
            System.out.println("Bravo, tu as trouvé la combinaison secrète!");
        } else {
            System.out.print("Tu as perdu! La combinaison était ");
            for (int digit : getCodeToBeFound()) {
                System.out.print(digit);
            }
            System.out.println();
        }
    }

     @Override
     protected void messageEndOfTheGame() {

     }
 }
