fun main() {

  fun getType(hand: String): Int {
    // todo refactor to be less horrible
    val lst = hand.toCharArray().toList()

    fun isFiveOfAKind(s: List<Char>) = s.groupBy { it }.size == 1
    fun isFourOfAKind(s: List<Char>): Boolean {
      val g = s.groupBy { it }
      return g.size == 2 && g.any { it.value.size == 4 }
    }

    fun isFull(s: List<Char>): Boolean {
      val g = s.groupBy { it }
      return g.size == 2 && g.any { it.value.size == 3 } && g.any { it.value.size == 2 }
    }

    fun isThreeOfAKind(s: List<Char>): Boolean {
      val g = s.groupBy { it }
      return g.size == 3 && g.any { it.value.size == 3 }
    }

    fun isTwoPair(s: List<Char>): Boolean {
      val g = s.groupBy { it }
      return g.size == 3 && g.filter { it.value.size == 2 }.size == 2
    }

    fun isOnePair(s: List<Char>) = s.groupBy { it }.size == 4

    return if (isFiveOfAKind(lst)) 6
    else if (isFourOfAKind(lst)) 5
    else if (isFull(lst)) 4
    else if (isThreeOfAKind(lst)) 3
    else if (isTwoPair(lst)) 2
    else if (isOnePair(lst)) 1
    else 0
  }

  fun getTypeWithJoker(hand: String): Int {
    var newHand = hand
    if (hand.contains("J")) {
      if (hand == "JJJJJ") return 6
      val c = hand.replace("J", "").groupBy { it }.maxBy { it.value.size }.key
      newHand = newHand.replace('J', c)
    }
    return getType(newHand)
  }

  fun toNumberedHand(order: String, hand: String): Long {
    var numberedHand = hand
    order.forEachIndexed { i, c ->
      numberedHand = numberedHand.replace("$c", "${i + 10}")
    }
    return numberedHand.toLong()
  }

  fun sortAndGetFinalValue(rounds: List<Round>): Int {
    return rounds.sortedWith(compareBy({ it.handType }, { it.numberedHand }))
      .mapIndexed { index, round ->
        (index + 1) * round.bet
      }.sum()
  }

  fun part1(input: List<String>): Any {
    val rounds = input.map { it.split(" ") }.map {
      Round(
        numberedHand = toNumberedHand("23456789TJQKA", it[0]),
        bet = it[1].toInt(),
        handType = getType(it[0])
      )
    }
    return sortAndGetFinalValue(rounds)
  }

  fun part2(input: List<String>): Any {
    val rounds = input.map { it.split(" ") }.map {
      Round(
        numberedHand = toNumberedHand("J23456789TQKA", it[0]),
        bet = it[1].toInt(),
        handType = getTypeWithJoker(it[0])
      )
    }
    return sortAndGetFinalValue(rounds)
  }

  val input = readInput("day07")
  val testInput = readInput("day07_test")

  part1(input).println()
  part2(input).println()
}

data class Round(
  val numberedHand: Long,
  val bet: Int,
  val handType: Int
)
