package org.leanpoker.player

class Player {

    static String VERSION

	static {
		if (Math.random() >= 0.5) {
			VERSION = 'groovy-old-strategy'
		} else {
			VERSION = 'groovy-new-strategy-1'
		}
	}

	static int betRequest(def gameState) {
		try {
			def helper = new GameHelper(gameState: gameState)

			if (VERSION == 'groovy-old-strategy') {
				def strategy = new Strategy(helper)
				return strategy.decision
			} else {
				def strategy = new NewStrategy(helper)
				return strategy.decision
			}
		} catch (Exception e) {
			return 0
		}
	}

    static void showdown(def gameState) {
    }
}
