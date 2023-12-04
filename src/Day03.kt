fun main() {
  fun getNumber(input: List<String>, i: Int, j: Int): Int {
    val n = input[i][j]
    if (n.isDigit()) {
      var s = "$n"
      var dir = 1
      while (input[i].getOrNull(j + dir)?.isDigit() == true) {
        s = s + input[i][j + dir]
        dir++
      }
      dir = 1
      while (input[i].getOrNull(j - dir)?.isDigit() == true) {
        s = input[i][j - dir] + s
        dir++
      }
      return s.toInt()
    }
    return 0
  }

  fun getAllNumbers(input: List<String>, i: Int, j: Int): MutableList<Int> {
    val numbers = mutableListOf<Int>()
    numbers.add(getNumber(input, i + 1, j))
    numbers.add(getNumber(input, i + 1, j + 1))
    numbers.add(getNumber(input, i + 1, j - 1))
    numbers.add(getNumber(input, i - 1, j))
    numbers.add(getNumber(input, i - 1, j + 1))
    numbers.add(getNumber(input, i - 1, j - 1))
    numbers.add(getNumber(input, i, j + 1))
    numbers.add(getNumber(input, i, j - 1))
    return numbers
  }


  fun part1(input: List<String>): Any {
    val allNumbers = mutableListOf(0)
    for (i in input.indices) {
      val row = input[i]
      for (j in row.indices) {
        val e = row[j]
        if (!e.isDigit() && e != '.') {
          val numbers = getAllNumbers(input, i, j)
          allNumbers.addAll(numbers.toSet())
        }
      }
    }
    return allNumbers.sumOf { it }
  }


  fun part2(input: List<String>): Any {
    val allNumbers = mutableListOf(0)
    for (i in input.indices) {
      val row = input[i]
      for (j in row.indices) {
        val e = row[j]
        if (!e.isDigit() && e != '.') {
          var numbers = getAllNumbers(input, i, j)
          numbers.removeAll { it == 0 }
          numbers = numbers.toSet().toMutableList()
          if (numbers.size == 2) {
            allNumbers.add(numbers[0] * numbers[1])
          }
        }
      }

    }
    return allNumbers.sumOf { it }
  }

  val input = readInput("day03")
  val testInput = readInput("day03_test")

  part1(input).println()
  part2(input).println()
}
