fun main() {

  fun horizontal(pattern: List<String>): Pair<Int, Int> {
    // if ----
    var mirrorIndex = -2
    for (i in 0 until pattern.size - 1) {
      val row = pattern[i]
      val nextRow = pattern[i + 1]
      if (row == nextRow) mirrorIndex = i
    }
//    val s = pattern
//      .mapIndexed { index, s -> Pair(index, s) }
//      .groupBy { it }.filter { it.value.size > 1 }.flatMap { it.value }
//    val s = pattern.mapIndexed { index, s -> Pair(index, s) }
//      .groupBy { it.second }
//      .filter { it.value.size > 1 }
//      .map { it.value.map { it.first } }
//      .filter { it[1] == (pattern.size - it[0]) }
//      .size
    val s = pattern.mapIndexed { index, s -> Pair(index, s) }.groupBy { it.second }.filter { it.value.size > 1 }
      .map { it.value.map { it.first } }.map { it[0] }
    val q = s.windowed(2).filter { it[1] - it[0] == 1 }.size + if (s.isNotEmpty()) 1 else 0

    return Pair(q, mirrorIndex + 1)
  }

  fun vertical(pattern: List<String>): Pair<Int, Int> {
    // if |
    var mirrorIndex = -2
    val cols = mutableListOf<String>()
    for (i in 0 until pattern[0].length - 1) {
      var column = ""
      var nextColumn = ""
      for (j in pattern.indices) {
        column += pattern[j][i]
        nextColumn += pattern[j][i + 1]
      }
      cols.add(column)
      if (column == nextColumn) mirrorIndex = i
    }
//    val s = cols.groupBy { it }.filter { it.value.size > 1 }.flatMap { it.value }.size
//    val s = cols.mapIndexed { index, s -> Pair(index, s) }
//      .groupBy { it.second }
//      .filter { it.value.size > 1 }
//      .map { it.value.map { it.first } }
//      .filter { it[1] == (cols.size - it[0]) }
//      .size
//    val s = cols.mapIndexed { index, s -> Pair(index, s) }
//      .groupBy { it.second }
//      .filter { it.value.size > 1 }
//      .map { it.value.map { it.first } }
//      .filter { it[1] - it[0] == 1 }
    val s = cols.mapIndexed { index, s -> Pair(index, s) }.groupBy { it.second }.filter { it.value.size > 1 }
      .map { it.value.map { it.first } }.map { it[0] }
    val q = if (mirrorIndex <= 0) -1
      else s.windowed(2).filter { it[1] - it[0] == 1 }.size + if (s.isNotEmpty()) 1 else 0
    return Pair(q, mirrorIndex + 1)
  }

  fun part1(input: List<String>): Any {
    val patterns = input.joinToString(",").split(",,").map { it.split(",") }
    val q = patterns.map {
      val v = vertical(it)
      val h = horizontal(it)

      if (v.first < h.first) 100 * h.second
      else v.second
    }.sum()
    return q
  }


  fun part2(input: List<String>): Any {
    return input
  }

  val input = readInput("day13")
  val testInput = readInput("day13_test")

  part1(input).println()
  part2(testInput).println()
}
