package com.berthoud.oc_project3_menu;

import com.berthoud.oc_project3_gameclasses.*;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import java.util.Scanner;


public class Main {

    private static int nbDigitsGame1 = 4;
    private static int maxGuessesGame1 = 4;
    private static int nbDigitsGame2 = 4;
    private static int maxGuessesGame2 = 10;
    private static int nbVariationsGame2 = 6 ;
    private static boolean devMode = false;
    private static String choiceGame;
    private static String choiceMode;

    public static String getChoiceGame() {
        return choiceGame;
    }

    public static String getChoiceMode() {
        return choiceMode;
    }

    public static void main(String[] args) {

        try {
            Properties p = new Properties();
            InputStream inputStream = new FileInputStream("config.properties");
            p.load(inputStream);

            nbDigitsGame1 = Integer.parseInt(p.getProperty("nbDigitsGame1"));
            maxGuessesGame1 = Integer.parseInt(p.getProperty("maxGuessesGame1"));
            nbDigitsGame2 = Integer.parseInt(p.getProperty("nbDigitsGame2"));
            maxGuessesGame2 = Integer.parseInt(p.getProperty("maxGuessesGame2"));
            nbVariationsGame2 = Integer.parseInt(p.getProperty("nbVariationsGame2"));

            if (p.getProperty("devMode").equals("Y")) {
                devMode = true;
            }

        } catch (Exception e){
            // do not display anything. If an exception is thrown, the game will run with the default field values.
        }


        if (args.length>0){
            for (int x =0; x< args.length; x++){
                if(args[x].equals("dev")){
                    devMode = true;
                    break;
                }
            }
        }

        menu();

    }

    public static boolean isDevMode() {
        return devMode;
    }

    public static void menu() {


        if (devMode){
           System.out.println("\n###### DEVELOPER MODE ######\n");
        }


        System.out.printf("%S\n\n%s\n%s\n%s\n%s", "Hello and welcome!",
                "Which game would you like to play?",
                "1. Game +/- , find the secret combination using +/- indications",
                "2. Digit Mastermind, like the traditional Mastermind but with digits",
                "Enter your selection: ");
        Scanner scanner = new Scanner(System.in);
        choiceGame = scanner.nextLine();

        while (!choiceGame.equals("1") && !choiceGame.equals("2")){
            System.out.print("What do you mean? Please enter 1 or 2: ");
            choiceGame = scanner.nextLine();
        }


        System.out.printf("\n%s\n%s\n%s\n%s\n%s", "Select the game mode",
                "1. Mode challenger: try to break Superbrain's code",
                "2. Mode defender: you choose a code, Superbrain try to find it",
                "3. Mode duel: the first who breaks the other's code wins!",
                "Enter your selection: ");

        choiceMode = scanner.nextLine();

        while (!choiceMode.equals("1") && !choiceMode.equals("2") && !choiceMode.equals("3")){
            System.out.print("What do you mean? Please enter 1, 2 or 3: ");
            choiceMode = scanner.nextLine();
        }

        System.out.println("\n");

        startTheGame(choiceGame, choiceMode);

    }


    public static void startTheGame(String choiceGame, String choiceMode){
        if (choiceGame.equals("1")){
            switch (choiceMode) {
                case "1":
                    Game1ModeChallenger game1ModeChallenger = new Game1ModeChallenger(nbDigitsGame1, maxGuessesGame1);
                    game1ModeChallenger.play();
                    break;
                case "2":
                    Game1ModeDefender game1ModeDefender = new Game1ModeDefender(nbDigitsGame1, maxGuessesGame1);
                    game1ModeDefender.play();
                    break;
                case "3":
                    GameModeDuel gameModeDuel = new GameModeDuel(nbDigitsGame1, maxGuessesGame1);
                    gameModeDuel.play();
                    break;
            }
        }

        if (choiceGame.equals("2")){
            switch (choiceMode) {
                case "1":
                    Game2ModeChallenger game2ModeChallenger = new Game2ModeChallenger(nbDigitsGame2, maxGuessesGame2, nbVariationsGame2);
                    game2ModeChallenger.play();
                    break;
                case "2":
                    Game2ModeDefender game2ModeDefender = new Game2ModeDefender(nbDigitsGame2, maxGuessesGame2, nbVariationsGame2);
                    game2ModeDefender.play();
                    break;
                case "3":
                    GameModeDuel gameModeDuel = new GameModeDuel(nbDigitsGame2, maxGuessesGame2, nbVariationsGame2);
                    gameModeDuel.play();
                    break;
            }


        }
    }


}

