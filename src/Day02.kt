fun main() {
  fun inputToGames(input: List<String>) = input.map { i ->
    val split = i.split(":")
    val gameId = split[0].onlyDigitsToInt()
    val stringDraws = split[1].trim().split(";")
    val draws = stringDraws.map { draw ->
      val colors = draw.split(",")
      Draw(
        red = colors.firstOrNull { it.onlyString() == "red" }?.onlyDigitsToInt() ?: 0,
        green = colors.firstOrNull { it.onlyString() == "green" }?.onlyDigitsToInt() ?: 0,
        blue = colors.firstOrNull { it.onlyString() == "blue" }?.onlyDigitsToInt() ?: 0,
      )
    }
    Game(
      gameId, draws
    )
  }

  fun part1(input: List<String>): Any {
    val maxDraw = Draw(12, 13, 14)
    val games = inputToGames(input)

    val filteredGames = games.filter { game ->
      game.draws.none { draw ->
        draw.blue > maxDraw.blue || draw.red > maxDraw.red || draw.green > maxDraw.green
      }
    }

    return filteredGames.sumOf { it.id }
  }

  fun part2(input: List<String>): Any {
    val games = inputToGames(input)
    val minCubes = games.map {game ->
      Game(
        id = game.id,
        draws = listOf(
          Draw(
            red = game.draws.maxBy { it.red }.red,
            green = game.draws.maxBy { it.green }.green,
            blue = game.draws.maxBy { it.blue }.blue
          )
        )
      )

    }
    return minCubes.sumOf {
      val min = it.draws[0]
      min.red * min.green * min.blue
    }
  }

  val input = readInput("day02")
  val testInput = readInput("day02_test")

  part1(input).println()
  part2(input).println()
}


data class Game(
  val id: Int,
  val draws: List<Draw>
)

data class Draw(
  val red: Int,
  val green: Int,
  val blue: Int,
)