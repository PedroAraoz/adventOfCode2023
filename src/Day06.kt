fun main() {
  fun getWaysToWin(race: Race): Int {
    var waysToWin = 0
    for (i in 1 until race.time) if ((race.time - i) * i > race.record) waysToWin++
    return waysToWin
  }

  fun part1(input: List<String>): Any {
    val parsed = input.map { i -> i.split(":")[1].split(" ").filter { it.isNotBlank() } }.map { it.toLong() }
    val races = parsed.first().zip(parsed.last()) { a, b -> Race(a, b) }
    return races.map { getWaysToWin(it) }.reduce { acc, i -> acc * i }
  }

  fun part2(input: List<String>): Any {
    val parsed = input.map { it.split(":")[1] }.map { it.replace(" ", "") }.toLong()
    val race = Race(parsed.first(), parsed.last())
    return getWaysToWin(race)
  }

  val input = readInput("day06")
  val testInput = readInput("day06_test")

  part1(input).println()
  part2(input).println()

}

data class Race(
  val time: Long,
  val record: Long,
)