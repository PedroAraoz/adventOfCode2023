import kotlin.math.max
import kotlin.math.pow

fun main() {

  val hashMap = hashMapOf<Int, List<List<String>>>()

  data class SpringData(
    val springs: String,
    val numbers: List<Int>
  )

  fun getNumbers(springs: String): List<Int> {
    return springs.split(".")
      .filter { it.isNotBlank() }
      .map { it.length }
  }

//  fun allPermutations(set: List<String>, n: Int): Set<List<String>> {
//    if (set.isEmpty()) return emptySet()
//
//    fun _allPermutations(list: List<String>, n: Int): Set<List<String>> {
//      if (list.isEmpty()) return setOf(emptyList())
//      if (list[0].length >= n) return setOf(list)
//      val result: MutableSet<List<String>> = mutableSetOf()
//      for (i in list.indices) {
//        _allPermutations(list - list[i], n).forEach { item ->
//          result.add(item + list[i])
//        }
//      }
//      return result
//    }
//
//    return _allPermutations(set.toList(), n)
//  }

  fun allPermutations(n: Int): List<List<String>> {
    return if (n == 1) (listOf(listOf("."), listOf("#")))
    else if (hashMap.containsKey(n)) return hashMap[n]!!
    else {
      val ans = allPermutations(n - 1).flatMap { listOf(it + listOf("."), it + listOf("#")) }
      hashMap[n] = ans
      ans
    }
  }

  fun getAllCombinations(springs: String, maxBroken: Int): List<String> {
    val lst = mutableListOf<String>()
    val numberOfQuestions = springs.count { it == '?' }
//    val a = List(numberOfQuestions - maxBroken) { "." }
//    val b = List(maxBroken) { "#" }
    val all = allPermutations(numberOfQuestions)
      .toSet().toList()
      .map { it.joinToString("") }

    for (option in all) {
      var s = springs
      for (i in 0 until numberOfQuestions) {
        s = s.replaceFirst('?', option[i])
      }
      lst.add(s)
    }
    return lst
//    return emptyList()
  }


  fun part1(input: List<String>): Any {
    val springsData = input.map {
      val (springs, numbers) = it.split(" ")
      SpringData(springs, numbers.split(",").toInt())
    }

    var acc: Long = 0
    println(".".repeat(springsData.size))
    for (springData in springsData) {
      acc += getAllCombinations(
        springData.springs,
        springData.numbers.sum() - springData.springs.filter { it == '#' }.length
      ).filter { getNumbers(it) == springData.numbers }.size
      print(".")
    }

    return acc
  }

  fun part2(input: List<String>): Any {
    return input
  }

  val input = readInput("day12")
  val testInput = readInput("day12_test")

//  part1(input).println()
  part1(testInput).println()

}
