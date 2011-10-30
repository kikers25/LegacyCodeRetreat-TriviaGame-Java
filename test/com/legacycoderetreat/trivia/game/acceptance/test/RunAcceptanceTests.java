package com.legacycoderetreat.trivia.game.acceptance.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Date;
import java.util.Random;

import com.legacycoderetreat.trivia.game.Game;

public class RunAcceptanceTests {
	private static boolean notAWinner;

	public static void main(String[] args) throws IOException {
		String acceptanceTestDirectoryName = String.format("acceptance/%tQ",
				new Date());
		File acceptanceTestDirectory = new File(acceptanceTestDirectoryName);
		boolean mkdirs = acceptanceTestDirectory.mkdirs();
		if (!mkdirs)
			throw new RuntimeException(String.format(
					"Couldn't create directory [%s]; can't run tests",
					acceptanceTestDirectory.getCanonicalPath()));

		PrintStream realSystemOut = System.out;
		try {
			for (int i = 0; i < 10000; i++) {

				String acceptanceTestFileName = String.format("%04d.txt", i);
				File acceptanceTestFile = new File(acceptanceTestDirectory,
						acceptanceTestFileName);
				PrintStream printToFile = new PrintStream(acceptanceTestFile);
				System.setOut(printToFile);

				Game aGame = new Game();

				aGame.add("Chet");
				aGame.add("Pat");
				aGame.add("Sue");

				Random rand = new Random(1237418251L * (long) (i) + 37831L);
				do {
					aGame.roll(rand.nextInt(5) + 1);

					if (rand.nextInt(9) == 7) {
						notAWinner = aGame.wrongAnswer();
					} else {
						notAWinner = aGame.wasCorrectlyAnswered();
					}
				} while (notAWinner);

				printToFile.close();
			}
		} catch (FileNotFoundException wrapped) {
			System.setOut(realSystemOut);
			throw new RuntimeException("Something went horribly wrong", wrapped);
		} finally {
			System.setOut(realSystemOut);
		}
		System.out.println("Done.");
	}
}
