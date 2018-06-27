package com.berthoud;

import java.util.Scanner;


public class Main {


    public static void main(String[] args) {

        menu();

    }


    public static void menu(){

        int nbDigits = 4;
        int maxGuesses = 4;
        int nbOptions = 6; // equivalent of number of colors for Mastermind


        System.out.printf("%S\n\n%s\n%s\n%s\n%s", "Hello and welcome!",
                "Which game would you like to play?",
                "1. Game +/- , find the secret combination using +/- indications",
                "2. Digit Mastermind, like the traditional Mastermind but with digits",
                "Enter your selection: ");
        Scanner scanner = new Scanner(System.in);
        String choicePlayer1;
        choicePlayer1 = scanner.nextLine();

        while (!choicePlayer1.equals("1") && !choicePlayer1.equals("2")){
            System.out.print("What do you mean? Please enter 1 or 2: ");
            choicePlayer1 = scanner.nextLine();
        }


        System.out.printf("\n%s\n%s\n%s\n%s\n%s", "Select the game mode",
                "1. Mode challenger: try to break Superbrain's code",
                "2. Mode defender: you choose a code, Superbrain try to find it",
                "3. Mode duel: the first who breaks the other's code wins!",
                "Enter your selection: ");

        String choicePlayer2;
        choicePlayer2 = scanner.nextLine();

        while (!choicePlayer2.equals("1") && !choicePlayer2.equals("2") && !choicePlayer2.equals("3")){
            System.out.print("What do you mean? Please enter 1, 2 or 3: ");
            choicePlayer2 = scanner.nextLine();
        }

        System.out.println("\n");

        if (choicePlayer1.equals("1")){
            switch (choicePlayer2) {
                case "1":
                    Game1ChallengerIsUser Jeu1Mode1 = new Game1ChallengerIsUser(nbDigits, maxGuesses);
                    Jeu1Mode1.play();
                    break;
                case "2":
                    Game1ChallengerIsComputer Jeu1Mode2 = new Game1ChallengerIsComputer(nbDigits, maxGuesses);
                    Jeu1Mode2.play();
                    break;
                case "3":
                    Game1ModeDuel Jeu1Mode3 = new Game1ModeDuel(nbDigits, maxGuesses);
                    Jeu1Mode3.play();
                    break;
            }
        }

        if (choicePlayer1.equals("2")){
            switch (choicePlayer2) {
                case "1":
                    Jeu2ModeAttaque Jeu2Mode1 = new Jeu2ModeAttaque(nbDigits, maxGuesses, nbOptions);
                    Jeu2Mode1.play();
                    break;
                case "2":
                    Jeu2ModeDefense Jeu2Mode2 = new Jeu2ModeDefense(nbDigits, maxGuesses, nbOptions);
                    Jeu2Mode2.play();
                    break;
                case "3":
                    Jeu2ModeDuel Jeu2Mode3 = new Jeu2ModeDuel(nbDigits, maxGuesses, nbOptions);
                    Jeu2Mode3.play();
                    break;
            }

        }


    }


}

