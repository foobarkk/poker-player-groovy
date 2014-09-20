package org.leanpoker.player

import groovy.json.JsonSlurper
import spock.lang.Specification

class PlayerTest extends Specification {

	def "test valid value betting"() {
		given:
		def gameState = new JsonSlurper().parseText(new File('src/test/resources/testdata.json').text)

		expect:
		Player.betRequest(gameState) >= 0
	}
}
