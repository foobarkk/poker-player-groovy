package org.leanpoker.player

import groovy.json.JsonSlurper
import spock.lang.Specification

class HandTest extends Specification {

	def createHandFromFile(def fileName) {
		def gameState = new JsonSlurper().parseText(new File(fileName).text)
		def hand = new Hand(cards: new GameHelper(gameState: gameState).cards)
		hand.calculateRanking()
		hand
	}

	def "test no hand"() {
		given:
		def hand = createHandFromFile('src/test/resources/nothing.json')

		expect:
		!hand.hasOnePair
		!hand.hasDrills
		!hand.hasTwoPairs
		!hand.hasFourOfKind
	}

	def "test one pair"() {
		given:
		def hand = createHandFromFile('src/test/resources/onepair.json')

		expect:
		hand.hasOnePair
		!hand.hasDrills
		!hand.hasTwoPairs
		!hand.hasFourOfKind
	}

	def "test two pairs"() {
		given:
		def hand = createHandFromFile('src/test/resources/twopairs.json')

		expect:
		hand.hasOnePair
		!hand.hasDrills
		hand.hasTwoPairs
		!hand.hasFourOfKind
	}

	def "test drill"() {
		given:
		def hand = createHandFromFile('src/test/resources/drill.json')

		expect:
		hand.hasOnePair
		hand.hasDrills
		!hand.hasTwoPairs
		!hand.hasFourOfKind
	}

	def "test foruOfKind"() {
		given:
		def hand = createHandFromFile('src/test/resources/fourofkind.json')

		expect:
		hand.hasOnePair
		hand.hasDrills
		!hand.hasTwoPairs
		hand.hasFourOfKind
	}

	def "test flush"() {
		given:
		def hand = createHandFromFile('src/test/resources/flush.json')

		expect:
		hand.hasFlush
	}
}
