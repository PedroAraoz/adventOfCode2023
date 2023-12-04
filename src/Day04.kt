import kotlin.math.pow

fun main() {
  fun parseCards(input: List<String>): List<Card> =
    input.map { i ->
      val splitLine = i.split(":")
      val (winning, my) = splitLine[1].split("|").map { it.trim().split(" ").filter { e -> e.isNotBlank() }.toInt() }
      Card(
        id = splitLine[0].onlyNumber(),
        myWinningNumbers = winning.intersect(my.toSet()).toList()
      )
    }


  fun part1(input: List<String>): Any {
    val cards = parseCards(input)
    return cards.sumOf { card ->
      if (card.myWinningNumbers.isEmpty()) 0
      else (1 * 2.0.pow(card.myWinningNumbers.size - 1)).toInt()
    }
  }

  fun part2(input: List<String>): Any {
    val cards = parseCards(input).toMutableList()
    var count = 1
    val maxId = cards.maxBy { it.id }.id
    while (count <= maxId) {
      val sameNumberedCards = cards.filter { it.id == count }
      val n = sameNumberedCards[0].myWinningNumbers.size
      for (i in (count + 1..count + n)) {
        for (j in sameNumberedCards.indices) {
          // add card i, j number of times to the cards list
          cards.add(cards.find { it.id == i }!!)
        }
      }
      count++
    }
    return cards.size
  }

  val input = readInput("day04")
  val testInput = readInput("day04_test")

  part1(input).println()
  part2(input).println()
}


private data class Card(
  val id: Int,
  val myWinningNumbers: List<Int> = listOf()
)