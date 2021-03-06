package org.leanpoker.player

class GameHelper {

	def gameState

	def getUs() {
		gameState.players.find { it.version ==~ /groovy.*/ }
	}

	def getMinimumBet() {
		gameState.current_buy_in - us.bet
	}

	def getCards() {
		us.hole_cards + gameState.community_cards
	}

	def getCommunityCards() {
		gameState.community_cards
	}

	def getMinimumRaise() {
		gameState.minimum_raise
	}

	def getCurrentBuyIn() {
		gameState.current_buy_in
	}
}
