import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MastermindIO {
	private Scanner scanner = new Scanner(System.in);
	private MastermindGame mastermindGame = new MastermindGame();

	public String getHumanPlayerName() {
		String name = "";
		boolean valid = false;
		while (!valid) {
			System.out.print("Wat is je naam? : ");
			name = scanner.nextLine();
			if (name.length() < 2) {
				System.out.println("fout => een naam moet minstens 2 letters zijn!");
			} else {
				String notALetter = name.replaceAll("[a-zA-Z]", "");

				if (notALetter.length() >= 1) {
					System.out.println("Fout: '" + notALetter.substring(0, 1) + "' is geen letter!");
				} else {
					valid = true;
					System.out.println("Welkom " + adjustName(name) + "!");
				}
			}
		}
		return adjustName(name);
	}

	private String adjustName(String name) {
		return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
	}

	public String getHeadorTail() {
		String headOrTail = "";
		boolean valid = false;
		while (!valid) {
			System.out.println("Eerst doen we een TOSS om wie mag beginnen.");
			System.out.print("wil je kop of munt? ");
			headOrTail = scanner.nextLine().toLowerCase();
			if (headOrTail.equals("kop") || headOrTail.equals("munt")) {
				valid = true;
			} else {
				System.out.println("fout => " + headOrTail + " is geen geldige invoer!");
			}
		}
		return headOrTail;
	}

	public void getEntertoContinue() {
		System.out.println("[ENTER] om door te gaan.");
		scanner.nextLine();
	}

	public void showGame(HashMap<Integer, HashMap<String, ArrayList<Integer>>> guesses) {
		System.out.println("-------------------------------------\r\n" + "|       Mastermind speelbord        |\r\n"
				+ "-------------------------------------");

		for (Map.Entry<Integer, HashMap<String, ArrayList<Integer>>> attempt : guesses.entrySet()) {
			System.out.print("      " + (attempt.getKey() + 1) + ": ");
			HashMap<String, ArrayList<Integer>> attemptGuesses = attempt.getValue();
			for (Map.Entry<String, ArrayList<Integer>> guess : attemptGuesses.entrySet()) {
				System.out.print(guess.getKey());
				if (!guess.getValue().isEmpty()) {
					System.out.print(" zwart: " + guess.getValue().get(0) + " wit: " + guess.getValue().get(1));
				}
				System.out.println();
			}
		}

		System.out.println("-------------------------------------");
	}

	public String getGuess() {
		int codeLength = mastermindGame.getCodeLength();
		String guess = "";
		boolean valid = false;
		while (!valid) {
			System.out.print("raad de code : ");
			guess = scanner.nextLine();
			if (guess.length() != codeLength) {
				System.out.println("fout => lengte in ongeldig! Geef precies " + codeLength + " letters.");
			} else {
				String notValidCode = guess.replaceAll("[a-fA-F]", "");
				if (notValidCode.length() >= 1) {
					System.out.println("fout: '" + notValidCode.substring(0, 1) + "' is geen geldige letter!");
				} else {
					valid = true;
				}
			}
		}
		return guess;
	}

	public ArrayList<Integer> getBlackAndWhite() {
		int codeLength = mastermindGame.getCodeLength();
		ArrayList<Integer> pins = new ArrayList<>();
		System.out.print("Geef het aantal zwarte pins: ");
		String amountBlack = scanner.nextLine();
		int blacks = Integer.parseInt(amountBlack);

		if (blacks > codeLength || blacks < 0) {

		}

		pins.add(blacks);
		System.out.print("Geef het aantal witte pins: ");
		String amountWhite = scanner.nextLine();
		int whites = Integer.parseInt(amountWhite);
		pins.add(whites);
		return pins;
	}

	private int getBlackPins() {
		int blackPins = 0;
		boolean valid = false;
		while (!valid) {
			System.out.print("Geef het aantal zwarte pins op: ");
			if (scanner.hasNextInt()) {
				blackPins = scanner.nextInt();
				if (blackPins >= 0 && blackPins <= mastermindGame.getCodeLength()) {
					valid = true;
				} else {
					System.out.println(
							"fout => invoer is ongeldig! Geef een getal tussen 0 en " + mastermindGame.getCodeLength());
				}
			} else {
				System.out.println(
						"fout => invoer is ongeldig! Geef een getal tussen 0 en " + mastermindGame.getCodeLength());
				scanner.next();
			}
		}
		return blackPins;
	}

	private int getWhitePins() {
		int whitePins = 0;
		boolean valid = false;
		while (!valid) {
			System.out.print("Geef het aantal witte pins op: ");
			if (scanner.hasNextInt()) {
				whitePins = scanner.nextInt();
				if (whitePins >= 0 && whitePins <= mastermindGame.getCodeLength()) {
					valid = true;
				} else {
					System.out.println(
							"fout => invoer is ongeldig! Geef een getal tussen 0 en " + mastermindGame.getCodeLength());
				}
			} else {
				System.out.println(
						"fout => invoer is ongeldig! Geef een getal tussen 0 en " + mastermindGame.getCodeLength());
				scanner.next();
			}
		}
		return whitePins;
	}

	public ArrayList<Integer> getBlackAndWhitePins() {
		ArrayList<Integer> result = new ArrayList<>();
		boolean valid = false;
		int white = 0;
		while (!valid) {
			int black = getBlackPins();
			if (black == mastermindGame.getCodeLength()) {
				result.add(black);
				result.add(white);

				return result;
			}
			white = getWhitePins();
			if (black + white <= mastermindGame.getCodeLength()) {
				valid = true;
				result.add(black);
				result.add(white);
			} else {
				System.out.println(
						"fout => som van pins moet tussen 0 en " + mastermindGame.getCodeLength() + " liggen.");
			}
		}
		return result;
	}
}
