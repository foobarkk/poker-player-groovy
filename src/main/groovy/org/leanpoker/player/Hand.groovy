package org.leanpoker.player

class Hand {

	def cards = []

	def hasOnePair = false
	def hasTwoPairs = false
	def hasDrills = false
	def hasFullHouse = false
	def hasFourOfKind = false
	def hasFlush = false

	void calculateRanking() {
		def orderByRank = cards.clone().sort { it.rank }
		def countBySuit = [:]

		def prevCard
		def rankCount = 1
		orderByRank.each { card ->
			if (prevCard) {
				if (prevCard.rank == card.rank) {
					rankCount++
					switch (rankCount) {
						case 2:
							hasTwoPairs = hasOnePair
							hasOnePair = true
							break
						case 3:
							hasDrills = true
							break
						case 4:
							hasFourOfKind = true
							break
					}
				} else {
					rankCount = 1
				}
			}
			countBySuit[card.suit] = 1 + (countBySuit[card.suit] ?: 0)
			prevCard = card
		}
		hasFullHouse = hasOnePair && hasDrills && !hasFourOfKind
		hasFlush = (countBySuit.find { it.value == 5 } != null)
	}

	def getHandValue() {
		calculateRanking()
		def handValue = 1
		if (this.hasFourOfKind) handValue = 7
		if (this.hasFullHouse) handValue = 6
		if (this.hasFlush) handValue = 5
		if (this.hasDrills) handValue = 4
		if (this.hasTwoPairs) handValue = 3
		if (this.hasOnePair) handValue = 2
		handValue
	}

}
