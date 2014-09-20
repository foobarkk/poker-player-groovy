package org.leanpoker.player

class Player {

    static String VERSION

	static {
		if (Math.random() >= 0.5) {
			VERSION = 'groovy-random-algo'
		} else {
			VERSION = 'groovy-super-secret-algo-3'
		}
	}

	static int betRequest(def gameState) {

		def helper = new GameHelper(gameState: gameState)

		if (VERSION == 'groovy-random-algo') {
			def random = Math.random()
			if (random < 0.1) return 0
			if (random < 0.6) return Math.min(helper.minimumBet + 100, helper.us.stack)
			return helper.us.stack
		} else {
			def strategy = new Strategy(helper)
			return strategy.decision
		}
	}

    static void showdown(def gameState) {
    }
}
