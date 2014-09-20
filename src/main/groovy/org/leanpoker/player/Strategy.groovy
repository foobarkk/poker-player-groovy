package org.leanpoker.player

class Strategy {

	static enum Decision { FOLD, CALL, RAISE }

	GameHelper helper

	private communityHand
	private ourHand
	private communityValue
	private ourValue

	Strategy(GameHelper helper) {
		this.helper = helper
		communityHand = new Hand(cards: helper.communityCards)
		communityValue = this.communityHand.handValue
		ourHand = new Hand(cards: helper.cards)
		ourValue = this.ourHand.handValue
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
			case 1..10:
				decision = Decision.RAISE
				break
		}
		switch (decision) {
			case Decision.FOLD:
				return 0
			case Decision.CALL:
				return helper.minimumBet
			case Decision.RAISE:
				return ourValue * helper.minimumRaise
		}
	}

}
