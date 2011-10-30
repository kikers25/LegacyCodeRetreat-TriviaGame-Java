package com.legacycoderetreat.trivia.game.acceptance.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Random;

import com.legacycoderetreat.trivia.game.Game;

public class GenerateAcceptanceTests {
	private static boolean notAWinner;

	public static void main(String[] args)  {
		PrintStream realSystemOut = System.out;

		try {
			String acceptanceTestFileName = String
					.format("acceptance/data/%04d.txt", 1);
			File acceptanceTestFile = new File(acceptanceTestFileName);
			PrintStream printToFile = new PrintStream(acceptanceTestFile);
			System.setOut(printToFile);

			Game aGame = new Game();

			aGame.add("Chet");
			aGame.add("Pat");
			aGame.add("Sue");

			Random rand = new Random(1237418251L * 1L + 37831L);
			do {
				aGame.roll(rand.nextInt(5) + 1);

				if (rand.nextInt(9) == 7) {
					notAWinner = aGame.wrongAnswer();
				} else {
					notAWinner = aGame.wasCorrectlyAnswered();
				}
			} while (notAWinner);

			printToFile.close();
		} catch (FileNotFoundException logged) {
			System.setOut(realSystemOut);
			logged.printStackTrace();
		}
		finally {
			System.setOut(realSystemOut);
		}
		System.out.println("Done.");
	}
}
