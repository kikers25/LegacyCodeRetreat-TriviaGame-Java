package com.legacycoderetreat.trivia.game.test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.legacycoderetreat.trivia.game.Game;

public class RollCoverageTests {
	private List<String> messages = new ArrayList<String>();

	@Test
	public void currentPlayerNotInPenaltyBox() throws Exception {
		Game game = new Game() {
			@Override
			public void printMessage(Object message) {
				super.printMessage(message);
				RollCoverageTests.this.messages.add(message.toString());
			}
		};
		game.add("irrelevant player's name");
		assertFalse(game.isCurrentPlayerInPenaltyBox());
		assertEquals(0, game.getCurrentPlaceOfCurrentPlayer());

		// current place + roll <= 11
		game.roll(11);

		// check messages?
		assertEquals(Arrays.asList(
				"irrelevant player's name's new location is 11",
				"The category is Rock", "Rock Question 0"), messages);
	}

	@Test
	public void rollBeforeAddingPlayers() throws Exception {
		Game game = new Game();
		try {
			game.roll(0);
			fail("Game no longer blows up when you roll() without adding players");
		} catch (IndexOutOfBoundsException expected) {
		}
	}
}
