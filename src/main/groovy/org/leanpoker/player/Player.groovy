package org.leanpoker.player

class Player {

    static final String VERSION = 'groovy-licious';

    static int betRequest(def gameState) {
		def helper = new GameHelper(gameState: gameState)
		def random = Math.random()
		if (random < 0.1) return 0
		if (random < 0.6) return helper.minimumBet
		helper.us.stack
    }

    static void showdown(def gameState) {
    }
}
