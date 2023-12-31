import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readLines

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("src/resources/$name.txt").readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
  .toString(16)
  .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)

fun String.onlyDigitsToInt() = this.filter { it.isDigit() }.toInt()
fun String.onlyString() = this.filter { it.isLetter() }
fun List<String>.onlyString() = this.map { it.onlyString() }
fun List<String>.trim() = this.map { it.trim() }
fun List<String>.toInt() = this.map { it.toInt() }
fun List<String>.toLong() = this.map { it.toLong() }