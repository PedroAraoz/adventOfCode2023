fun main() {

  // https://www.baeldung.com/kotlin/lcm
  fun findLCM(a: Long, b: Long): Long {
    val larger = if (a > b) a else b
    val maxLcm = a * b
    var lcm = larger
    while (lcm <= maxLcm) {
      if (lcm % a == 0.toLong() && lcm % b == 0.toLong()) {
        return lcm
      }
      lcm += larger
    }
    return maxLcm
  }

  // https://www.baeldung.com/kotlin/lcm
  fun findLCMOfListOfNumbers(numbers: List<Int>): Long {
    var result = numbers[0].toLong()
    for (i in 1 until numbers.size) result = findLCM(result, numbers[i].toLong())
    return result
  }


  fun parseInput(input: List<String>) =
    input.drop(2).associate { line ->
      val (key, value) = line.split(" = ")
      val pair = value.split(",").onlyString().trim()
      key to Pair(pair[0], pair[1])
    }

  fun part1(input: List<String>): Any {
    val instructions = input[0]
    val map = parseInput(input)

    var currentNode = "AAA"
    var iter = instructions.iterator()
    var steps = 0
    while (currentNode != "ZZZ") {
      steps++
      if (!iter.hasNext()) iter = instructions.iterator()
      currentNode = when (iter.nextChar()) {
        'L' -> map[currentNode]!!.first
        else -> map[currentNode]!!.second
      }
    }
    return steps
  }

  fun part2(input: List<String>): Any {
    val instructions = input[0]
    val map: Map<String, Pair<String, String>> = parseInput(input)

    val currentNodes = map.keys.filter { it.last() == 'A' }
    val minSteps = mutableListOf<Int>()

    for (node in currentNodes) {
      var currentNode = node
      var iter = instructions.iterator()
      var steps = 0
      while (!currentNode.endsWith("Z")) {
        steps++
        if (!iter.hasNext()) iter = instructions.iterator()
        currentNode = when (iter.nextChar()) {
          'L' -> map[currentNode]!!.first
          else -> map[currentNode]!!.second
        }
      }
      minSteps.add(steps)
    }
    return findLCMOfListOfNumbers(minSteps)
  }

  val input = readInput("day08")
  val testInput = readInput("day08_test")

  part1(input).println()
  part2(input).println()
}
