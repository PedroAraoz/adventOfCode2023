// This is horrible code and a bad solution!
fun main() {
  fun part1(input: List<String>): Any {
    val seeds = input.first().replace("seeds: ", "").split(" ").toLong()
    var rest = input.subList(1, input.size).filter { it.isNotBlank() }.toMutableList()

    val maps: MutableList<NamedMap> = mutableListOf()

    while (rest.isNotEmpty()) {
      val name = rest.removeFirst().replace(" map:", "")
      val mapString = rest.takeWhile { it.onlyString().isBlank() }
      rest = rest.subList(mapString.size, rest.size)

      maps.add(
        NamedMap(
          fromName = name.split("-")[0],
          toName = name.split("-")[2],
          fromNumber = mapString.map { l ->
            val (_, keyStart, len) = l.split(" ").toLong()
            (keyStart..<keyStart + len)
          },
          toNumber = mapString.map { l ->
            val (valStart, _, len) = l.split(" ").toLong()
            (valStart..<valStart + len)
          }
        )
      )
    }

    var numbers = seeds
    for (map in maps) {
      numbers = numbers.map { n: Long ->
        var r: Long = n
        for (i in map.fromNumber.indices) {
          if (map.fromNumber[i].contains(n)) {
            val index = map.fromNumber[i].indexOf(n)
            r = map.toNumber[i].first + index
          }
        }
        r
      }
    }
    return numbers.min()
  }

  fun part2(input: List<String>): Any {
    val seedRanges = input.first().replace("seeds: ", "").split(" ").toLong()

    var rest = input.subList(1, input.size).filter { it.isNotBlank() }.toMutableList()
    val maps: MutableList<NamedRanges> = mutableListOf()

    while (rest.isNotEmpty()) {
      val name = rest.removeFirst().replace(" map:", "")
      val mapString = rest.takeWhile { it.onlyString().isBlank() }
      rest = rest.subList(mapString.size, rest.size)

      maps.add(
        NamedRanges(
          fromName = name.split("-")[0],
          toName = name.split("-")[2],
          sourceFrom = mapString.map {
            val (_, keyStart, _) = it.split(" ").toLong()
            keyStart
          },
          sourceTo = mapString.map {
            val (_, keyStart, len) = it.split(" ").toLong()
            keyStart + len - 1
          },
          destinationFrom = mapString.map {
            val (valStart, _, _) = it.split(" ").toLong()
            valStart
          },
          destinationTo = mapString.map {
            val (valStart, _, len) = it.split(" ").toLong()
            valStart + len
          }
        )
      )
    }

    val seedPairs = seedRanges.windowed(2, 2)

    var minVal = Long.MAX_VALUE
    for (seedPair in seedPairs) {
      println("change in pair")
      var currentSeed = seedPair[0]
      while (currentSeed < seedPair[0] + seedPair[1]) {
        var currentVal = currentSeed
        for (map in maps) {
          for (i in 0 until map.sourceFrom.size) {
            if (map.sourceFrom[i] <= currentVal && currentVal < map.sourceTo[i]) {
              val diff = currentVal - map.sourceFrom[i]
              currentVal = map.destinationFrom[i] + diff
              break
            }
          }
        }
        if (currentVal < minVal) minVal = currentVal
        currentSeed++
      }
    }
    return minVal
  }

  val input = readInput("day05")
  val testInput = readInput("day05_test")

  part1(input).println()
  part2(input).println()
}

data class NamedRanges(
  val fromName: String,
  val toName: String,
  val sourceFrom: List<Long>,
  val sourceTo: List<Long>,
  val destinationFrom: List<Long>,
  val destinationTo: List<Long>,
)

data class NamedMap(
  val fromName: String,
  val toName: String,
  val fromNumber: List<LongRange>,
  val toNumber: List<LongRange>
)