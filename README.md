#  Openclassrooms JAVA Path
## Project 3: test your logic skills

### **Presentation**

This program is the 3rd project of the [Openclassrooms JAVA learning path](https://openclassrooms.com/en/paths/88-developpeur-dapplication-java).

It contents 2 guessing games:
* **The + / - game**: or how to break a secret code using + / - indications
* **The digital Mastermind**: a digital version of the success board game [Mastermind](https://en.wikipedia.org/wiki/Mastermind_(board_game))

Each game can be played in 3 modes:
* **Mode challenger**: the player tries to break the computer's code
* **Mode defender**: the computer tries to break the players'code
* **Mode duel**: it combines both mode challenger and mode defender. Computer and player plays one after the other. The first who find the secret code wins.


### **Compile and start the program**

This program has been developped in IntelliJ IDEA. You can easily clone it from GitHub in IntelliJ by using the following command: File>New>Project from version control>Git and entering the link of the repo https://github.com/JulienDeBerlin/OCJava_project3_guessingGames


### **Configuration**

A properties config file makes it possible to change some parameters of the games. You can set:
* the number of digits of the secret code (for each game), *[default value = 4]*
* the maximum number of guesses (for each game), *[default value = 4 for game +/- and 10 for Mastermind]*
* the number of values that each digit can take (only Mastermind - equivalent to the number of possible colors in the original Mastermind game). Please not that this value can't be less than 4, nor bigger than 10. *[default value = 4].*


### ** Development mode**
This program can be run in a developer mode. In this mode the secret code to be found (mode challenger and mode duel for each game) is displayed. That makes possible to check that the program runs properly.
The developer mode can be activated through the properties config file or alternatively by passing the argument "dev" while running the main. 





