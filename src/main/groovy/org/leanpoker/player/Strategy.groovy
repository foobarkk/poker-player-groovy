package org.leanpoker.player

class Strategy {

	static enum Decision { FOLD, MAYBE_CALL, CALL, MAYBE_RAISE, RAISE }

	GameHelper helper

	private communityHand
	private ourHand
	private communityValue
	private ourValue
	private playerBets = []

	static int raiseCount = 0
	static int communityCardCount = 0

	Strategy(GameHelper helper) {
		this.helper = helper
		communityHand = new Hand(cards: helper.communityCards)
		communityValue = this.communityHand.handValue
		ourHand = new Hand(cards: helper.cards)
		ourValue = this.ourHand.handValue
		helper.gameState.players.findAll { it.version != Player.VERSION }.each {
			// Kati védelem
			if (it.bet + it.stack > 0) {
				playerBets << ((double) it.bet / (double) (it.bet + it.stack))
			}
		}
		if (helper.communityCards.size() != communityCardCount) {
			raiseCount = 0
		}
		communityCardCount = helper.communityCards.size()
	}

	def getDecision() {
		def decision
		switch (ourValue - communityValue) {
			case 0:
				if (communityValue in [1, 2] && communityHand.cards.size() <= 3) {
					decision = Decision.CALL
					break
				}
				decision = Decision.FOLD
				break
			case 1..2:
				decision = Decision.MAYBE_CALL
				break
			case 3..4:
				decision = Decision.CALL
				break
			case 5..6:
				decision = Decision.MAYBE_RAISE
				break
			case 7..10:
				decision = Decision.RAISE
				break
		}
		switch (decision) {
			case Decision.FOLD:
				return 0
			case Decision.MAYBE_CALL:
				if (helper.currentBuyIn > helper.us.stack * 0.75 && raiseCount > 1) {
					return 0
				}
			case Decision.CALL:
				raiseCount++
				return helper.minimumBet
			case Decision.MAYBE_RAISE:
				if (communityValue < 3 && playerBets.count { it > 0.75 }) {
					return helper.minimumBet
				}
			case Decision.RAISE:
				if (raiseCount < 2) {
					raiseCount++
					return ourValue * helper.minimumRaise
				}
				return helper.minimumBet
		}
	}

}
