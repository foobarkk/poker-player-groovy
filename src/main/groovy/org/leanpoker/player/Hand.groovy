package org.leanpoker.player

class Hand {

	def rankOrder = (2..10).collect{it as String} + ['J', 'Q', 'K', 'A']

	def cards = []

	def hasOnePair = false
	def hasTwoPairs = false
	def hasDrills = false
	def hasFullHouse = false
	def hasFourOfKind = false
	def hasFlush = false

	def hasStraight = false
	def hasStraightFlush = false
	def hasRoyalFlush = false

	void calculateRanking() {
		def orderByRank = cards.clone().sort { rankOrder.indexOf(it.rank) }
		def countBySuit = [:]

		def straightCount = 0
		def straightEndRank
		def prevCard
		def rankCount = 1
		orderByRank.each { card ->
			if (prevCard) {
				if (rankOrder.indexOf(prevCard.rank) + 1 == rankOrder.indexOf(card.rank)) {
					straightCount++
					straightEndRank = card.rank
				}
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
		hasStraight = straightCount == 4
		hasStraightFlush = hasStraight && hasFlush
		hasRoyalFlush = hasStraightFlush && (rankOrder.last() == straightEndRank)
	}

	def getHandValue() {
		calculateRanking()
		def handValue = 1
		if (this.hasRoyalFlush) handValue = 10
		if (this.hasStraightFlush) handValue = 9
		if (this.hasFourOfKind) handValue = 8
		if (this.hasFullHouse) handValue = 7
		if (this.hasFlush) handValue = 6
		if (this.hasStraight) handValue = 5
		if (this.hasDrills) handValue = 4
		if (this.hasTwoPairs) handValue = 3
		if (this.hasOnePair) handValue = 2
		handValue
	}

}
