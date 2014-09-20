package org.leanpoker.player

class Player {

    static final String VERSION = 'groovy-licious';

    static int betRequest(def gameState) {
		def helper = new GameHelper(gameState: gameState)
		def strategy = new Strategy(helper)
		strategy.decision
//		def random = Math.random()
//		if (random < 0.1) return 0
//		if (random < 0.6) return Math.min(helper.minimumBet + 100, helper.us.stack)
//		helper.us.stack
    }

    static void showdown(def gameState) {
    }
}
