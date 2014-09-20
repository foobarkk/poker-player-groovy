package org.leanpoker.player

import groovy.json.JsonSlurper
import spock.lang.Specification

class StrategyTest extends Specification {

	def "test valid bet value"() {
		given:
		def gameState = new JsonSlurper().parseText(new File(fileName).text)
		def strategy = new Strategy(new GameHelper(gameState: gameState))

		expect:
		strategy.decision >= 0

		where:
		fileName | _
		'src/test/resources/nothing.json' | _
		'src/test/resources/onepair.json' | _
		'src/test/resources/twopairs.json' | _
		'src/test/resources/drill.json' | _
		'src/test/resources/fourofkind.json' | _
	}
}
