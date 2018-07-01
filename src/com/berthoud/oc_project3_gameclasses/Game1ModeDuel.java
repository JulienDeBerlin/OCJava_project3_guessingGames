package com.berthoud.oc_project3_gameclasses;


public class Game1ModeDuel extends Game1{

// _____________________________________________________________________________________________________________________
    //INSTANCE FIELDS//

    private Game1ModeDefender gameA;
    private Game1ModeChallenger gameB;


// _____________________________________________________________________________________________________________________
    //CONSTRUCTORS//
    public Game1ModeDuel(int nbDigits, int maxGuesses) {
        this.gameB = new Game1ModeChallenger(nbDigits, maxGuesses);
        this.gameA = new Game1ModeDefender(nbDigits, maxGuesses);
    }


// _____________________________________________________________________________________________________________________
    //IMPLEMENTATION OF ABSTRACT METHODS//

    @Override
    public void play() {

        System.out.println(">>>>>  The +/- game, mode duel <<<<<" );

        MyTools.makeABreak(400);

        System.out.print("Ready to challenge Superbrain? \nTo start with, choose a mystery code made of "
                + gameB.getNbDigits() + " digits that Superbrain will try to break: ");
        gameA.setCodeToBeFound(gameA.codeInputUser());
        gameB.setCodeToBeFound(gameB.randomCodeGenerator());


        System.out.print("Perfect, let's get started!\n\n");

        while ((!gameB.isCodeFound()) && (!gameA.isCodeFound())) {

            // Game1ModeChallenger
            System.out.print("Mode challenger: player against Superbrain\n");
            displayModeDev(gameB.getCodeToBeFound());

            gameB.guessValidationUnit();

            MyTools.makeABreak(800);

            if (gameB.isCodeFound()) {
                System.out.println("Great! You broke the code. Let's see if Superbrain can equalise ");
            } else System.out.println("Mode defender: Superbrain against player");


            // Game1ModeDefender
           gameA.guessValidationUnit();
        }

        messageEndOfTheGame();

        gameA.setCodeFound(false);
        gameB.setCodeFound(false);
        gameA.setNbGuesses(1);
        gameB.setNbGuesses(1);

        endingMenu();

    }


    @Override
    protected void messageEndOfTheGame() {
        if ((gameB.isCodeFound()) && (gameA.isCodeFound())) {
            System.out.printf("%S", "Dead heat!");
        }

        if ((gameB.isCodeFound()) && (!gameA.isCodeFound())){
            System.out.printf("%S", "You won!");
        }

        if ((!gameB.isCodeFound()) && (gameA.isCodeFound())){
            System.out.printf("%S", "Superbrain won!");
        }

    }
}


