fun main() {
  fun part1(input: List<String>): Any {
    val map = input.toMutableList()
    var noChange = false
    while (!noChange) {
      noChange = true
      for (i in 1 until map.size) {
        var prevRow = map[i - 1].split("").toMutableList()
        var row = map[i].split("").toMutableList()
        for (j in row.indices) {
          if (row[j] == "O" && prevRow[j] == ".") {
            noChange = false
            row[j] = "."
            prevRow[j] = "O"
          }
        }
        map[i - 1] = prevRow.joinToString("")
        map[i] = row.joinToString("")
      }
    }


    return map.map { it.filter { it == 'O' }.length }.reversed().mapIndexed { index, i -> i * (index + 1) }.sum()
  }

  fun rollNorth(map: MutableList<String>) {
    var noChange = false
    while (!noChange) {
      noChange = true
      for (i in 1 until map.size) {
        var prevRow = map[i - 1].split("").toMutableList()
        var row = map[i].split("").toMutableList()
        for (j in row.indices) {
          if (row[j] == "O" && prevRow[j] == ".") {
            noChange = false
            row[j] = "."
            prevRow[j] = "O"
          }
        }
        map[i - 1] = prevRow.joinToString("")
        map[i] = row.joinToString("")
      }
    }
  }

  fun part2(input: List<String>): Any {
    var map = input.toMutableList()

    var n: Long = 0
    while (n < 4000000000) {
      println(n/4000000000)
      map = input.toMutableList()
      rollNorth(map)
      n++
    }

    return map.map { it.filter { it == 'O' }.length }.reversed().mapIndexed { index, i -> i * (index + 1) }.sum()
  }

  val input = readInput("day14")
  val testInput = readInput("day14_test")

  part1(input).println()
  part2(input).println()
}
