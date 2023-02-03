import java.util.HashMap;
import java.util.Random;
import java.util.ArrayList;

public class GameHuman {
	static boolean CHEATMODE = true;

	private String name;
	private String headOrTailChoice;
	private int attempt;
	private boolean guessed;
	private HashMap<Integer, HashMap<String, ArrayList<Integer>>> guesses;

	private MastermindGame mastermindGame = new MastermindGame();

	public GameHuman() {
		attempt = 0;
		guesses = new HashMap<Integer, HashMap<String, ArrayList<Integer>>>();
		guessed = false;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public int getAttempt() {
		return attempt;
	}
	private void setAttempt(int attempt) {
		this.attempt = attempt;
	}

	public void setChoice(String choice) {
		headOrTailChoice = choice;
	}

	public String getChoice() {
		return headOrTailChoice;
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

	public void reset() {
		clearGuesses();
		setChoice("");
		setAttempt(0);
		setGuessed(false);
	}

	private String createCode() {
		String letters = "ABCDEF";
		Random rand = new Random();
		String code = "";
		for (int i = 0; i < mastermindGame.getCodeLength(); i++) {
			int index = rand.nextInt(letters.length());
			code += letters.charAt(index);
		}
		return code;
	}

	public void play() {

		MastermindIO mastermindIO = new MastermindIO();

		System.out.println("---------------------------------------------------------\r\n" + "| " + name
				+ " jij gaat nu raden.                                |\r\n"
				+ "| Probeer de code zo snel mogelijk te raden.            |\r\n" + "| Geef per keer "
				+ mastermindGame.getCodeLength() + " letters in en dan [ENTER]             |\r\n"
				+ "| Bij 9 keer een foute code ben je af.                  |\r\n"
				+ "| Succes!                                               |\r\n"
				+ "---------------------------------------------------------");
		mastermindIO.getEntertoContinue();
		String code = createCode();
		int maxTries = mastermindGame.getMaxTries();
		if (CHEATMODE) {
			System.out.println(">>> CHEAT [" + code + "] <<<");
			System.out.println();
		}
		mastermindIO.showGame(guesses);
		while (attempt < maxTries) {
			if (CHEATMODE && attempt != 0) {
				System.out.println(">>> CHEAT [" + code + "] <<<");
				System.out.println();
			}
			String guess = mastermindIO.getGuess().toUpperCase();
			ArrayList<Integer> result = compareCode(guess, code);
			saveCode(attempt, guess, compareCode(guess, code));
			mastermindIO.showGame(guesses);
			attempt++;
			if (result.get(0) == mastermindGame.getCodeLength()) {
				System.out.println(name.toUpperCase() + " je hebt de code geraden!\r\n" + "jouw score is: " + attempt);
				setGuessed(true);
				return;
			}
		}
		System.out.println("Je hebt het niet geraden. De code was [" + code + "]");
		System.out.println("jouw score is: [NIET]");
		setGuessed(false);

	}

	private ArrayList<Integer> compareCode(String guess, String code) {
		ArrayList<Integer> result = new ArrayList<>();
		StringBuilder guessSB = new StringBuilder(guess);
		StringBuilder codeSB = new StringBuilder(code);
		int black = 0;
		for (int i = 0; i < codeSB.length(); i++) {

			if (guessSB.charAt(i) == codeSB.charAt(i)) {
				black++;
				guessSB.setCharAt(i, 'X');
				codeSB.setCharAt(i, 'X');
			}
		}

		int white = 0;
		for (int i = 0; i < codeSB.length(); i++) {
			if (codeSB.charAt(i) != 'X') {
				int index = guessSB.indexOf(Character.toString(codeSB.charAt(i)));
				if (index > -1) {
					white++;
					guessSB.setCharAt(index, 'X');
				}
			}
		}
		result.add(black);
		result.add(white);
		return result;
	}

	private void saveCode(int attempt, String guess, ArrayList<Integer> result) {
		if (!guesses.containsKey(attempt)) {
			guesses.put(attempt, new HashMap<String, ArrayList<Integer>>());
		}
		guesses.get(attempt).put(guess, result);
	}
}
