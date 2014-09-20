package org.leanpoker.player

class Hand {

	def cards = []
	def orderBySuit = []
	def orderByRank = []

	def hasOnePair = false
	def hasTwoPairs = false
	def hasDrills = false
	def hasFourOfKind = false

	void calculateValue() {
		orderByRank = cards.clone().sort { it.rank }
		orderBySuit = cards.clone().sort { it.suit }

		def prevCard
		def count = 1
		orderByRank.each { card ->
			if (prevCard) {
				if (prevCard.rank == card.rank) {
					count++
					switch (count) {
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
					count = 1
				}
			}
			prevCard = card
		}
	}

}
