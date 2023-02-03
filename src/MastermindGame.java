import java.util.Random;

public class MastermindGame {

	private int codeLength;
	private int maxTries;

	public MastermindGame() {
		codeLength = 4;
		maxTries = 9;
	}

	public int getCodeLength() {
		return codeLength;
	}

	public int getMaxTries() {
		return maxTries;
	}

	public void start() {

		System.out.println("=========================================================\r\n"
				+ "| Welkom bij Mastermind!                                |\r\n"
				+ "=========================================================\r\n"
				+ "| Je gaat tegen de computer spelen.                     |\r\n"
				+ "| Het doel is zo snel mogelijk een geheime code raden.  |\r\n" + "| De code bestaat uit "
				+ codeLength + " letters.                        |\r\n"
				+ "| Mogelijke letters zijn: A, B, C, D, E, F              |\r\n"
				+ "| Een letter kan 0 t/m 4 keer voorkomen in de code.     |\r\n"
				+ "| Jij raadt de code van de computer en andersom.        |\r\n"
				+ "| Wie met het minste pogingen de code raadt wint.       |\r\n"
				+ "| Bij een gelijkspel spelen we nog een ronde.           |\r\n"
				+ "=========================================================");
		MastermindIO mastermindIO = new MastermindIO();
		GameHuman gameHuman = new GameHuman();
		GameComputer gameComputer = new GameComputer();

		gameHuman.setName(mastermindIO.getHumanPlayerName());
		
		startGame(gameHuman, gameComputer);

	}

	private void getResult(GameHuman gameHuman, GameComputer gameComputer) {
		int maxTries = getMaxTries();
		int humanScore = gameHuman.getAttempt();
		int computerScore = gameComputer.getAttempt();
		boolean gameHumanGuessed = gameHuman.isGuessed();
		boolean gameComputerGuessed = gameComputer.isGuessed();

		if (!gameHumanGuessed && gameComputerGuessed) {
			humanScore = maxTries + 1;
			System.out.println("Eindscore: " + gameHuman.getName() + ": [NIET] - COMPUTER: " + computerScore);
		}
		if (gameHumanGuessed && !gameComputerGuessed) {
			computerScore = maxTries + 1;
			System.out.println("Eindscore: " + gameHuman.getName() + ": " + humanScore + " - COMPUTER: [NIET]");
		}
		if (!gameHumanGuessed && !gameComputerGuessed) {
			computerScore = maxTries + 1;
			humanScore = maxTries + 1;
			System.out.println("Eindscore: " + gameHuman.getName() + ": [NIET] - COMPUTER: [NIET]");
		}
		if (gameHumanGuessed && gameComputerGuessed) {
			System.out
					.println("Eindscore: " + gameHuman.getName() + ": " + humanScore + " - COMPUTER: " + computerScore);
		}

		System.out.println("==================================================================");

		if (humanScore == computerScore) {
			gameHuman.reset();
			gameComputer.reset();

			System.out.println("Het is gelijkspel, we spelen nog een ronde!");
			System.out.println("==================================================================");
			startGame(gameHuman, gameComputer);
		}
		if (humanScore < computerScore) {
			System.out.println("Bravo " + gameHuman.getName() + " je hebt de computer verslagen!");
			System.out.println("==================================================================");
		}
		if (humanScore > computerScore) {
			System.out.println("Helaas " + gameHuman.getName() + " je hebt verloren!");
			System.out.println("==================================================================");
		}
	}

	private void startGame(GameHuman gameHuman, GameComputer gameComputer) {
		MastermindIO mastermindIO = new MastermindIO();
		gameHuman.setChoice(mastermindIO .getHeadorTail());

		String headOrTail = doToss();

		if (headOrTail.equals(gameHuman.getChoice())) {
			System.out.println(gameHuman.getName() + " jij mag beginnen!");
			gameHuman.play();
			gameComputer.play();
		} else {
			System.out.println("De computer begint!");
			gameComputer.play();
			gameHuman.play();
		}

		getResult(gameHuman, gameComputer);
	}

	private String doToss() {
		System.out.print("flipping: ");
		int heads = 0;
		int tails = 0;
		String headOrTail = "";
		Random rand = new Random();
		for (int i = 0; i < 5; i++) {
			int flip = rand.nextInt(2);

			if (flip == 0) {
				if (i == 4) {
					System.out.println("kop");
				} else {
					System.out.print("kop|");
				}
				heads++;
			} else {
				if (i == 4) {
					System.out.println("munt");
				} else {
					System.out.print("munt|");
				}
				tails++;
			}
		}
		System.out.print("Het is geworden: ");
		if (heads > tails) {
			System.out.println("kop (" + heads + " keer gegooid)");
			headOrTail = "kop";
		} else {
			System.out.println("munt (" + tails + " keer gegooid)");
			headOrTail = "munt";
		}
		return headOrTail;

	}

}
