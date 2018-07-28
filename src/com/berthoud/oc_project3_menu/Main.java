package com.berthoud.oc_project3_menu;

import com.berthoud.oc_project3_gameclasses.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.util.Properties;
import java.util.Scanner;


/**
 * This program implements two guessing games: Game 1 (+/-) and Game 2 (Mastermind). Each game can be played in 3 modes
 * challenger, defender and duel. For more details on the specific games:
 *
 * @author Julien Berthoud
 * @see Games
 * @see Game1
 * @see Game2
 * @see GameModeDuel
 * @see Game1ModeChallenger
 * @see Game1ModeDefender
 * @see Game2ModeChallenger
 * @see Game2ModeDefender
 */
public class Main {
    public static Logger logger = LogManager.getLogger();

    // Default game parameters in case the configuration through the config.properties file doesn't work.
    private static int nbDigitsGame1 = 4;
    private static int maxGuessesGame1 = 4;
    private static int nbDigitsGame2 = 4;
    private static int maxGuessesGame2 = 10;
    private static int nbVariationsGame2 = 6; // min = 4, max = 10

    /**
     * All games can be executed in a mode developer, in which the code to be found is displayed. It makes possible to check
     * if everything works properly. This mode can be activated through the config properties file or by entering the parameter "dev"
     * in the command line
     */
    private static boolean devMode = false;

    private static String choiceGame;
    private static String choiceMode;


// _____________________________________________________________________________________________________________________
    //GETTERS//

    public static boolean isDevMode() {
        return devMode;
    }

    public static String getChoiceGame() {
        return choiceGame;
    }

    public static String getChoiceMode() {
        return choiceMode;
    }


// _____________________________________________________________________________________________________________________
    //MAIN//


    /**
     * The main method loads the configuration parameters from the config.properties file and calls the menu
     *
     * @param args takes the argument "dev" to launch the developer mode from the command line.
     *             The developer mode can also be activated
     *             through the config.properties file.
     */
    public static void main(String[] args) {


        // Activation of the developer mode through the command line
        if (args.length > 0) {
            for (String arg : args) {
                if (arg.equals("dev")) {
                    devMode = true;
                    logger.info("Developer mode activated through command line");
                    break;
                }
            }
        }

        loadConfig();

        menu();

        startTheGame(choiceGame, choiceMode);

    }


// _____________________________________________________________________________________________________________________
    //STATIC METHODS//

    /**
     * The method implements the menu, which makes possible for the user to select a game and a mode.
     */
    public static void menu() {

        if (devMode) {
            System.out.println("\n###### DEVELOPER MODE ######\n");
        }


        System.out.printf("%S\n\n%s\n%s\n%s\n%s", "Hello and welcome!",
                "Which game would you like to play?",
                "1. Game +/- , find the secret combination using +/- indications",
                "2. Digit Mastermind, like the traditional Mastermind but with digits",
                "Enter your selection: ");
        Scanner scanner = new Scanner(System.in);
        choiceGame = scanner.nextLine();
        logger.debug("choiceGame = " + choiceGame);

        while (!choiceGame.equals("1") && !choiceGame.equals("2")) {
            System.out.print("What do you mean? Please enter 1 or 2: ");
            logger.debug("Entry not valid.");
            choiceGame = scanner.nextLine();
            logger.debug("New entry = "+ choiceGame);
        }


        System.out.printf("\n%s\n%s\n%s\n%s\n%s", "Select the game mode",
                "1. Mode challenger: try to break Superbrain's code",
                "2. Mode defender: you choose a code, Superbrain try to find it",
                "3. Mode duel: the first who breaks the other's code wins!",
                "Enter your selection: ");

        choiceMode = scanner.nextLine();
        logger.debug("choiceMode = " + choiceMode);

        while (!choiceMode.equals("1") && !choiceMode.equals("2") && !choiceMode.equals("3")) {
            System.out.print("What do you mean? Please enter 1, 2 or 3: ");
            logger.debug("Entry not valid.");
            choiceMode = scanner.nextLine();
            logger.debug("New entry = "+ choiceMode);

        }

        System.out.println("\n");


    }


    /**
     * The method instantiate a game-instance, according to the selection game/mode made by the player and start the game through
     * the method {@link Games#play()}.
     *
     * @param choiceGame Game 1 (+/-) or 2 (Digit Mastermind)
     * @param choiceMode Mode challenger, defender or duel
     */
    public static void startTheGame(String choiceGame, String choiceMode) {
        if (choiceGame.equals("1")) {
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

        if (choiceGame.equals("2")) {
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

    /**
     * This method loads the config file
     */
    public static void loadConfig(){

        Properties p = new Properties();

        try {
            InputStream inputStream = ClassLoader.getSystemResourceAsStream("config.properties");
            p.load(inputStream);

            if (Integer.parseInt(p.getProperty("nbDigitsGame1")) > 0) {
                nbDigitsGame1 = Integer.parseInt(p.getProperty("nbDigitsGame1"));
            }

            if (Integer.parseInt(p.getProperty("maxGuessesGame1")) > 0) {
                maxGuessesGame1 = Integer.parseInt(p.getProperty("maxGuessesGame1"));
            }

            if (Integer.parseInt(p.getProperty("nbDigitsGame2")) > 0) {
                nbDigitsGame2 = Integer.parseInt(p.getProperty("nbDigitsGame2"));
            }

            if (Integer.parseInt(p.getProperty("maxGuessesGame2")) > 0) {
                maxGuessesGame2 = Integer.parseInt(p.getProperty("maxGuessesGame2"));
            }

            if (Integer.parseInt(p.getProperty("nbVariationsGame2")) > 3 && (Integer.parseInt(p.getProperty("nbVariationsGame2")) < 11)) {
                nbVariationsGame2 = Integer.parseInt(p.getProperty("nbVariationsGame2"));
            }

            logger.info("config file loaded");

            // Activation of the developer mode through the config.properties file
            if (p.getProperty("devMode").equals("Y")) {
                devMode = true;
                logger.info("Developer mode activated through config file");
            }


        } catch (Exception e) {
            System.out.println("The config properties file is missing or can not be imported correctly. " +
                    "The program will be executed with the default parameters.\n\n");
            logger.error("Error, config file is missing.", e);
        }


    }


}

