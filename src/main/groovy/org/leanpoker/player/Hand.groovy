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

	def highCardCount = 0

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
			if (rankOrder.indexOf(card.rank) >= 8) highCardCount++
			countBySuit[card.suit] = 1 + (countBySuit[card.suit] ?: 0)
			prevCard = card
		}
		hasFullHouse = hasOnePair && hasDrills && !hasFourOfKind
		hasFlush = (countBySuit.find { it.value == 5 } != null)
		hasStraight = straightCount == 4
		hasStraightFlush = hasStraight && hasFlush
		hasRoyalFlush = hasStraightFlush && (rankOrder.last() == straightEndRank)
	}

}
