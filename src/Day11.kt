import java.awt.Point
import kotlin.math.abs

fun main() {
  fun part1(input: List<String>): Any {
    val galaxy = mutableListOf<String>()
    for (j in input.indices) {
      val row = input[j]
      if (row.all { it == '.' }) galaxy.add(row)
      galaxy.add(row)
    }
    val lst = mutableListOf<Int>()
    for (i in input.indices) {
      var col = ""
      for (j in input[i].indices) {
        col += (input[j][i])
      }
      if (col.all { it == '.' }) {
        lst.add(i)
      }
    }
    for (j in galaxy.indices) {
      var row = galaxy[j]
      for ((count, i) in lst.withIndex()) {
        row = StringBuilder(row).apply { insert(i + count, '.') }.toString()
      }
      galaxy[j] = row
    }

    val points = mutableListOf<Pair<Int, Int>>()

    for (j in galaxy.indices) {
      for (i in galaxy[j].indices) {
        if (galaxy[j][i] == '#') points.add(Pair(i, j))
      }
    }

    var n = 0
    for (i in points.indices) {
      val p1 = points[i]
      for (j in i + 1..<points.size) {
        val p2 = points[j]
        n += abs(p1.first - p2.first) + abs(p1.second - p2.second)
      }
    }


    return n
  }

  fun calculate(galaxy: MutableList<String>, p1: Pair<Int, Int>, p2: Pair<Int, Int>): Int {
    var count = 0

    val (a,b) = if (p1.second < p2.second) Pair(p1,p2) else Pair(p2,p1)

    for (j in a.second..b.second) {
      if (galaxy[j][a.first] == '*') count += 1000000 - 1
    }

    val (c,d) = if (p1.first < p2.first) Pair(p1,p2) else Pair(p2,p1)

    for (i in c.first .. d.first) {
      if (galaxy[c.second][i] == '*') count += 1000000 -1
    }

    return count
  }

  fun part2(input: List<String>): Any {
    val galaxy = mutableListOf<String>()
    for (j in input.indices) {
      val row = input[j]
      if (row.all { it == '.' }) galaxy.add("*".repeat(row.length))
      else galaxy.add(row)
    }
    val lst = mutableListOf<Int>()
    for (i in input.indices) {
      var col = ""
      for (j in input[i].indices) {
        col += (input[j][i])
      }
      if (col.all { it == '.' || it == '*' }) {
        lst.add(i)
      }
    }
    for (j in galaxy.indices) {
      var row = galaxy[j]
      for (i in lst) {
        row = StringBuilder(row).apply { replace(i, i+1, "*") }.toString()
      }
      galaxy[j] = row
    }


    val points = mutableListOf<Pair<Int, Int>>()

    for (j in galaxy.indices) {
      for (i in galaxy[j].indices) {
        if (galaxy[j][i] == '#') points.add(Pair(i, j))
      }
    }

    var n: Long = 0
    for (i in points.indices) {
      val p1 = points[i]
      for (j in i + 1..<points.size) {
        val p2 = points[j]
        n += abs(p1.first - p2.first) + abs(p1.second - p2.second) + calculate(galaxy, p1, p2)
      }
    }

    galaxy.forEach {
      it.forEach { print(it) }
      println()
    }

    return n
  }

  val input = readInput("day11")
  val testInput = readInput("day11_test")

  part1(input).println()
  part2(input).println()
}
