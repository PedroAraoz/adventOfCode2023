fun main() {
  fun part1(input: List<String>): Int {
    return input
      .map { s -> s.filter { it.isDigit() } }
      .map { joinFirstAndLastDigit(it) }
      .sumOf { it.toInt() }}

  fun part2(input: List<String>): Int {
    return input
      .map { replaceWordsWithNumbers(it) }
      .map { s -> s.filter { it.isDigit() } }
      .map { joinFirstAndLastDigit(it) }
      .sumOf { it.toInt() }
  }

  val input = readInput("day01")
  val testInput = readInput("day01_test")

  part1(input).println()
  part2(input).println()
}


private fun replaceWordsWithNumbers(s: String): String {
  val dict = mapOf(
    "one" to "1",
    "two" to "2",
    "three" to "3",
    "four" to "4",
    "five" to "5",
    "six" to "6",
    "seven" to "7",
    "eight" to "8",
    "nine" to "9",
  )
  val keys = dict.keys.toList()

  var ans = ""
  for (c in s.toCharArray()) {
    ans += c.toString()
    keys.forEach {
      // add last letter so if there are two concatenated numbers the second one can also be replaced
      val lastLetter = it.toCharArray().last().toString()
      ans = ans.replace(it, dict[it]!! + lastLetter)
    }
  }
  return ans
}

private fun joinFirstAndLastDigit(it: String): String {
  val c = it.toCharArray()
  return c.first().toString() + c.last().toString()
}
