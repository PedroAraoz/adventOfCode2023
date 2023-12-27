fun main() {

  fun getNeighbors(map: List<List<String>>, prev: Pair<Int, Int>?, v: Pair<Int, Int>): List<Pair<Int, Int>> {
    return when (map[v.second][v.first]) {
      "|" -> listOf(
        Pair(v.first, v.second + 1),
        Pair(v.first, v.second - 1),
      )

      "-" -> listOf(
        Pair(v.first + 1, v.second),
        Pair(v.first - 1, v.second),
      )

      "L" -> listOf(
        Pair(v.first, v.second - 1),
        Pair(v.first + 1, v.second),

        )

      "J" -> listOf(
        Pair(v.first, v.second - 1),
        Pair(v.first - 1, v.second),
      )

      "7" -> listOf(
        Pair(v.first, v.second + 1),
        Pair(v.first - 1, v.second),
      )

      "F" -> listOf(
        Pair(v.first, v.second + 1),
        Pair(v.first + 1, v.second),
      )

      "S" -> listOf(
        Pair(v.first + 1, v.second),
        Pair(v.first - 1, v.second),
        Pair(v.first, v.second + 1),
        Pair(v.first, v.second - 1),
      ).filter {
        getNeighbors(map, null, it).contains(v)
      }

      else -> emptyList()
    }.filterNot {
      it.first < 0 || it.second < 0 || it.first >= map[0].size || it.second >= map.size || map[it.second][it.first] == "." ||
          it == prev
    }
  }

  data class Line(
    val p1: Pair<Int, Int>,
    val p2: Pair<Int, Int>,
  )

  fun onLine(l1: Line, p: Pair<Int, Int>): Int {

    // Check whether p is on the line or not
    if (p.first <= Math.max(l1.p1.first, l1.p2.first) && p.first >= Math.min(
        l1.p1.first,
        l1.p2.first
      ) && (p.second <= Math.max(l1.p1.second, l1.p2.second)
          && p.second >= Math.min(l1.p1.second, l1.p2.second))
    ) return 1

    return 0
  }

  fun direction(a: Pair<Int, Int>, b: Pair<Int, Int>, c: Pair<Int, Int>): Int {
    val `val` = ((b.second - a.second) * (c.first - b.first)
        - (b.first - a.first) * (c.second - b.second))

    if (`val` == 0) // Collinear
      return 0
    else if (`val` < 0) // Anti-clockwise direction
      return 2

    // Clockwise direction
    return 1
  }

  fun isIntersect(l1: Line, l2: Line): Int {
    // Four direction for two lines and Pair<Integer, Integer>s of other
    // line
    val dir1 = direction(l1.p1, l1.p2, l2.p1)
    val dir2 = direction(l1.p1, l1.p2, l2.p2)
    val dir3 = direction(l2.p1, l2.p2, l1.p1)
    val dir4 = direction(l2.p1, l2.p2, l1.p2)

    // When intersecting
    if (dir1 != dir2 && dir3 != dir4) return 1

    // When p2 of line2 are on the line1
    if (dir1 == 0 && onLine(l1, l2.p1) === 1) return 1

    // When p1 of line2 are on the line1
    if (dir2 == 0 && onLine(l1, l2.p2) === 1) return 1

    // When p2 of line1 are on the line2
    if (dir3 == 0 && onLine(l2, l1.p1) === 1) return 1

    // When p1 of line1 are on the line2
    if (dir4 == 0 && onLine(l2, l1.p2) === 1) return 1

    return 0
  }

  fun checkInside(poly: Array<Pair<Int, Int>>, p: Pair<Int, Int>): Int {
    val n = poly.size

    // When polygon has less than 3 edge, it is not
    // polygon
    if (n < 3) return 0

    // Create a Pair<Integer, Integer> at infinity, y is same as Pair<Integer, Integer> p
    val pt = Pair(9999, p.second)
    val exline = Line(p, pt)
    var count = 0
    var i = 0
    do {
      // Forming a line from two consecutive Pair<Integer, Integer>s of
      // poly

      val side = Line(poly[i], poly[(i + 1) % n])
      if (isIntersect(side, exline) == 1) {
        // If side is intersects exline

        if (direction(side.p1, p, side.p2) == 0) return onLine(side, p)
        count++
      }
      i = (i + 1) % n
    } while (i != 0)

    // When count is odd
    return count and 1
  }

  fun part1(input: List<String>): Any {
    val map = input.map { it.split("").filter { it.isNotBlank() } }
    var start = Pair(0, 0)
    for (y in map.indices) {
      for (x in map[y].indices) {
        if (map[y][x] == "S") start = Pair(x, y)
      }
    }

    val (prev, v) = Pair(null, start)
    var (left, right) = getNeighbors(map, prev, v)
    var prevL = start
    var prevR = start
    var count = 1
    while (left != right) {
      val newLeft = getNeighbors(map, prevL, left).first()
      val newRight = getNeighbors(map, prevR, right).first()
      prevL = left
      left = newLeft

      prevR = right
      right = newRight
      count++
    }
    return count
  }

  fun pointIsInside(map: List<List<String>>, point: Pair<Int, Int>): Boolean {
    val (x, y) = point
    var row = map[y]
    var top = false
    var bottom = false
    var left = false
    var right = false

    val half = listOf("L", "J", "7", "F")
    val full = listOf("|")
    val all = half + full + listOf("S", "-")

    if (x == 14 && y == 6) {
      1+1
    }

    for (i in row.indices) {
      if (i > x && all.contains(row[i])) top = true
      if (i < x && all.contains(row[i])) bottom = true
    }
    val column = map.map { it[x] }
    for (i in column.indices) {
      if (i > y && all.contains(column[i])) left = true
      if (i < y && all.contains(column[i])) right = true
    }




    var reducedRow = row.slice(x..<row.size)
    val rowLen = reducedRow.size

    var sRow = reducedRow.joinToString("")
      .replace("S", "F")
      .replace("L-*7".toRegex(), "|")
      .replace("F-*J".toRegex(), "|")
    sRow += ".".repeat(rowLen - sRow.length)
    reducedRow = sRow.split("").filter { it.isNotBlank() }


    var intersection1 = 0.0
    var i = 0

    while (i < reducedRow.size) {
//      if ((row[i] == "L" && row.getOrNull(i + 1) == "7") || (row[i] == "F" && row.getOrNull(i + 1) == "J")) {
//      } else {
      if (full.contains(reducedRow[i])) intersection1 += 1
//      }
      i++
    }
//    for (i in x until row.size) {
//      // L7 FJ
//      if (row[i] == "L" || row[i] == "F") {
//        if (row.getOrNull(i + 1) == "7" || row.getOrNull(i + 1) == "J") {
//          intersection1--
//        }
//      }
//      if (full.contains(row[i])) intersection1 += 1
////      if (half.contains(row[i])) intersection1 += .5
//    }
//
//    var intersection2 = 0
//    for (i in 0 until x) {
//      if (all.contains(row[i])) intersection2++
//    }
//
//    var intersection3 = 0
//    for (i in y until column.size) {
//      if (column[i]) intersection3++
//    }
//
//    var intersection4 = 0
//    for (i in 0 until y) {
//      if (column[i]) intersection4++
//    }

    return (intersection1 % 2).toInt() != 0
        && top && bottom && left && right
//        && intersection2 % 2 != 0
//        && intersection3 % 2 != 0
    //        && intersection4 % 2 != 0
  }

  fun part2(input: List<String>): Any {

    val map = input.map { it.split("").filter { it.isNotBlank() } }
    var start = Pair(0, 0)
    for (y in map.indices) {
      for (x in map[y].indices) {
        if (map[y][x] == "S") start = Pair(x, y)
      }
    }

    val (prev, v) = Pair(null, start)
    var (left, right) = getNeighbors(map, prev, v)
    var prevL = start
    var prevR = start
    val loop = mutableSetOf(start)
    while (left != right) {
      loop.add(right)
      loop.add(left)
      val newLeft = getNeighbors(map, prevL, left).first()
      val newRight = getNeighbors(map, prevR, right).first()
      prevL = left
      left = newLeft

      prevR = right
      right = newRight
    }
    loop.add(left)

    map.mapIndexed { y, row ->
      List(row.size) { x ->
        if (loop.contains(Pair(x, y))) map[y][x] else " "
      }
    }.forEach {
      it.forEach { print(it) }
      println()
    }

    val bb = map.mapIndexed { y, row ->
      List(row.size) { x ->
        loop.contains(Pair(x, y))
      }
    }
    var count = 0
    for (y in bb.indices) {
      for (x in bb[y].indices) {
        if (loop.contains(Pair(x, y))) when (map[y][x]) {
          "7" -> print("┐")
          "F" -> print("┌")
          "J" -> print("┘")
          "L" -> print("└")
          "|" -> print("│")
          "-" -> print("─")
          "S" -> print("│")
//          else -> print(map[y][x])
          else -> print(".")
        }
//        else if (checkInside(loop.toTypedArray(), Pair(x,y)) == 1) {
        else if (pointIsInside(map, Pair(x, y))) {
          print("O")
          count++
        } else print(" ")
      }
      println()
    }
    return count
  }

  val input = readInput("day10")
  val testInput = readInput("day10_test")

  part1(input).println()
  part2(input).println()
}