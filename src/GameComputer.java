import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class GameComputer {
	private ArrayList<String> codes;
	private int attempt;
	private boolean guessed;
	private HashMap<Integer, HashMap<String, ArrayList<Integer>>> guesses;

	public GameComputer() {
		codes = createCodes();
		guesses = new HashMap<Integer, HashMap<String, ArrayList<Integer>>>();
	}

	public int getAttempt() {
		return attempt;
	}

	private void setAttempt(int attempt) {
		this.attempt = attempt;
	}

	public boolean isGuessed() {
		return guessed;
	}

	private void setGuessed(boolean guessed) {
		this.guessed = guessed;
	}

	private void clearGuesses() {
		guesses.clear();
	}

	private void resetCodes() {
		codes.clear();
		codes = createCodes();
	}

	public void reset() {
		setAttempt(0);
		clearGuesses();
		resetCodes();
		setGuessed(false);
	}

	public void play() {
		MastermindGame mastermindGame = new MastermindGame();
		MastermindIO mastermindIO = new MastermindIO();

		System.out.println("---------------------------------------------------------\r\n"
				+ "| De COMPUTER gaat nu raden.                            |\r\n" + "| Bedenk een code die uit "
				+ mastermindGame.getCodeLength() + " letters bestaat.            |\r\n"
				+ "| Mogelijke letters zijn: A, B, C, D, E, F              |\r\n"
				+ "| Geef na iedere poging het resultaat in aantal pins.   |\r\n"
				+ "| zwarte pins = letters op juiste positie.              |\r\n"
				+ "| witte pins = letters op verkeerde positie.            |\r\n"
				+ "---------------------------------------------------------");
		mastermindIO.getEntertoContinue();
		int maxTries = mastermindGame.getMaxTries();
		while (attempt < maxTries) {
			String guess = generateGuess(attempt == 0);
			saveCode(attempt, guess, new ArrayList<>());
			mastermindIO.showGame(guesses);
			ArrayList<Integer> result = mastermindIO.getBlackAndWhitePins();
			int black = result.get(0);
			int white = result.get(1);
			removeIncorrectCodes(guess, black, white);
			saveCode(attempt, guess, result);
			attempt++;
			if (black == mastermindGame.getCodeLength()) {
				System.out.println("geraden\r\n" + "de score van de computer is: " + attempt);
				setGuessed(true);
				return;
			} else if (codes.isEmpty()) {
				System.out.println(" X X X X X X X X X X X X\r\n" + "De computer geeft het op!\r\n"
						+ "Waarschijnlijk heb je ergens een fout getal ingevoerd.\r\n" + " X X X X X X X X X X X X");
				setGuessed(false);
				return;
			}
		}

	}

	private String generateGuess(boolean firstGuess) {
		Random random = new Random();
		if (firstGuess) {
			char[] code = new char[4];
			char repeatedLetter = (char) ('A' + random.nextInt(6));
			code[0] = repeatedLetter;
			code[1] = repeatedLetter;
			for (int i = 2; i < 4; i++) {
				code[i] = (char) ('A' + random.nextInt(6));
			}

			return new String(code);
		} else {
			return codes.get(random.nextInt(codes.size()));
		}
	}

	private ArrayList<String> createCodes() {
		ArrayList<String> newCodes = new ArrayList<>();
		for (char a = 'A'; a <= 'F'; a++) {
			for (char b = 'A'; b <= 'F'; b++) {
				for (char c = 'A'; c <= 'F'; c++) {
					for (char d = 'A'; d <= 'F'; d++) {
						newCodes.add("" + a + b + c + d);
					}
				}
			}
		}
		return newCodes;
	}

	private void removeIncorrectCodes(String guess, int blackPins, int whitePins) {
		ArrayList<String> newCodes = new ArrayList<>();
		for (String code : codes) {
			int black = 0;
			int white = 0;

			StringBuilder guessSB = new StringBuilder(guess);
			StringBuilder codeSB = new StringBuilder(code);
			for (int i = 0; i < codeSB.length(); i++) {

				if (guessSB.charAt(i) == codeSB.charAt(i)) {
					black++;
					guessSB.setCharAt(i, 'X');
					codeSB.setCharAt(i, 'X');
				}
			}

			for (int i = 0; i < codeSB.length(); i++) {
				if (codeSB.charAt(i) != 'X') {
					int index = guessSB.indexOf(Character.toString(codeSB.charAt(i)));
					if (index > -1) {
						white++;
						guessSB.setCharAt(index, 'X');
					}
				}
			}

			if (black == blackPins && white == whitePins) {
				newCodes.add(code);
			}
		}
		codes = newCodes;
	}

	private void saveCode(int attempt, String guess, ArrayList<Integer> result) {
		if (!guesses.containsKey(attempt)) {
			guesses.put(attempt, new HashMap<String, ArrayList<Integer>>());
		}
		guesses.get(attempt).put(guess, result);
	}
}
