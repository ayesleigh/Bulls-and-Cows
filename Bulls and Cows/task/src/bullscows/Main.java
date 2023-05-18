package bullscows;

import java.util.*;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static String[] list  = "0123456789abcdefghijklmnopqrstuvwxyz".split("");
    static StringBuilder secretCode;
    static int bulls;
    static int cows;
    static int turnCount;

    public static void main(String[] args) {
        turnCount = 0;
        secretCode = new StringBuilder();

        System.out.println("Please, enter the secret code's length:");

        String userInputString = scanner.nextLine();
        boolean isNumeric = userInputString.chars().allMatch(Character::isDigit);

        int secretCodeLength;
        if (!isNumeric) {
            System.out.printf("Error: %s isn't a valid number.%n",
                    userInputString);
            return;
        } else {
            secretCodeLength = Integer.parseInt(userInputString);
        }

        if (secretCodeLength > list.length) {
            System.out.println("Error: maximum number of possible symbols in " +
                    "the code is 36 (0-9, a-z).");
            return;
        }

        if (secretCodeLength < 1) {
            System.out.println("Error: secret code length cannot be less than" +
                    " 1");
            return;
        }

        System.out.println("Input the number of possible symbols in the code:");
        userInputString = scanner.nextLine();
        isNumeric = userInputString.chars().allMatch(Character::isDigit);

        int numberOfPossibleSymbols;
        if (!isNumeric) {
            System.out.printf("Error: %s isn't a valid number.%n",
                    userInputString);
            return;
        } else {
            numberOfPossibleSymbols = Integer.parseInt(userInputString);
        }

        if (numberOfPossibleSymbols > list.length) {
            System.out.println("Error: maximum number of possible symbols in " +
                    "the code is 36 (0-9, a-z).");
            return;
        }

        if (numberOfPossibleSymbols < 1) {
            System.out.println("Error: number of possible symbols cannot be " +
                    "less than 1");
            return;
        }

        if (numberOfPossibleSymbols < secretCodeLength) {
            System.out.printf("Error: it's not possible to generate a code " +
                            "with a length of %s with %s unique symbols.%n",
                    secretCodeLength, numberOfPossibleSymbols);
            return;
        }

        generateSecretCode(secretCodeLength, numberOfPossibleSymbols);
//        System.out.println("Secret code is " + secretCode);

        System.out.println("Okay, let's start a game!");
        while (bulls != secretCodeLength) {
            turnCount++;
            System.out.printf("Turn %d:%n", turnCount);
            String userGuess;
            try {
                userGuess = scanner.next();
                if (userGuess.length() != secretCodeLength) {
                    throw new InputMismatchException();
                }
            } catch (InputMismatchException e) {
                System.out.printf("Error: please enter a guess with a length " +
                                "of %d%n", secretCodeLength);
                continue;
            }

            checkUserGuess(userGuess);
            showGrade();
        }

        System.out.println("Congratulations! You guessed the secret code.");
        scanner.close();
    }

    static void generateSecretCode(int secretCodeLength,
                                   int numberOfPossibleSymbols) {
        Random random = new Random();

        while (secretCode.length() < secretCodeLength) {
            int randomNumber = random.nextInt(numberOfPossibleSymbols);
            if (!secretCode.toString().contains(list[randomNumber])) {
                secretCode.append(list[randomNumber]);
            }
        }

        String result =
                "The secret is prepared: " + "*".repeat(secretCodeLength) + " (";
        if (numberOfPossibleSymbols < 11) {
            result += String.format("%s-%s).", list[0],
                    list[numberOfPossibleSymbols - 1]);
        } else if (numberOfPossibleSymbols == 11) {
            result += String.format("0-9, %s).", list[10]);
        } else {
            result += String.format("0-9, %s-%s).", list[10],
                    list[numberOfPossibleSymbols - 1]);
        }

        System.out.println(result);
    }

    static void checkUserGuess(String userGuess) {
        bulls = 0;
        cows = 0;

        if (userGuess.contentEquals(secretCode)) {
            bulls = secretCode.length();
        } else {
            for (int i = 0; i < secretCode.length(); i++) {
                if (userGuess.charAt(i) == secretCode.charAt(i)) {
                    bulls++;
                } else if (secretCode.toString().contains(String.valueOf(userGuess.charAt(i)))) {
                    cows++;
                }
            }
        }
    }

    static void showGrade() {
        StringBuilder result = new StringBuilder();
        result.append("Grade: ");

        String grade = "";

        if (bulls == 0 && cows == 0) {
            grade += "None";
        } else if (bulls == 0) {
            grade += cows;
            grade += cows == 1 ? " cow" : " cows";
        } else if (cows == 0) {
            grade += bulls;
            grade += bulls == 1 ? " bull" : " bulls";
        } else {
            grade += bulls;
            grade += bulls == 1 ? " bull" : " bulls";
            grade += " and ";
            grade += cows;
            grade += cows == 1 ? " cow" : " cows";
        }

        result.append(grade).append("%n");
        System.out.printf(String.valueOf(result));
    }
}
