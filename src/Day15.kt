fun main() {
  fun asciiStringHelperAlgorithm(s: String): Int {
    var i = 0
    for (c in s) i = ((i + c.code) * 17) % 256
    return i
  }

  fun part1(input: List<String>): Any {
    val sequence = input.first().split(",")
    return sequence.sumOf { asciiStringHelperAlgorithm(it) }
  }

  data class Lens(
    val label: String,
    val focalLength: Int,
  )

  fun part2(input: List<String>): Any {
    val sequence = input.first().split(",").map { it.split("[=\\-]".toRegex()) }
    val hashmap = mutableMapOf<Int, MutableList<Lens>>()


    for (step in sequence) {
      val (label, stringNumber) = step
      val boxNumber = asciiStringHelperAlgorithm(label)
      if (stringNumber.isEmpty()) {
        if (hashmap.containsKey(boxNumber)) hashmap[boxNumber] =
          hashmap[boxNumber]!!.filterNot { it.label == label }.toMutableList()
      } else {
        // =
        val lens = Lens(
          label = label,
          focalLength = stringNumber.toInt()
        )
        if (hashmap.getOrDefault(boxNumber, emptyList()).any { it.label == lens.label }) {
          hashmap[boxNumber]!![hashmap[boxNumber]!!.indexOfFirst { it.label == lens.label }] = lens
        } else {
          if (!hashmap.containsKey(boxNumber)) hashmap[boxNumber] = mutableListOf()
          hashmap[boxNumber]!!.add(lens)
        }
      }
    }
    // (1 + boxnumber) * (indexOfLens + 1) * focalLength
    return hashmap.map { box ->
      box.value.mapIndexed { index, lens ->
        (index + 1) * lens.focalLength * (box.key + 1)
      }
    }.sumOf { it.sum() }
  }

  val input = readInput("day15")
  val testInput = readInput("day15_test")

  part1(input).println()
  part2(input).println()
}
