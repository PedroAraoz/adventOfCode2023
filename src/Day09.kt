fun main() {

  fun diffBetweenValues(history: List<Long>): List<Long> =
    history.zipWithNext().map { it.second - it.first }

  fun getSumOfPredictions(histories: List<List<Long>>): Long = histories.sumOf { history ->
    val lst = mutableListOf(history, diffBetweenValues(history))
    while (!lst.last().all { it == 0.toLong() }) lst.add(diffBetweenValues(lst.last()))
    lst.sumOf { it.last() }
  }

  fun part1(input: List<String>): Any {
    val histories = input.map { it.split(" ").toLong() }
    return getSumOfPredictions(histories)
  }

  fun part2(input: List<String>): Any {
    val histories = input.map { it.split(" ").toLong().reversed() }
    return getSumOfPredictions(histories)
  }

  val input = readInput("day09")
  val testInput = readInput("day09_test")

  part1(input).println()
  part2(input).println()
}