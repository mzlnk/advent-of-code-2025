import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readText

/**
 * Reads lines from the given input txt file.
 */
fun readInputAsList(name: String) = readInputAsString(name).lines()

/**
 * Reads the content from the given input txt file
 */
fun readInputAsString(name: String) = Path("src/$name.txt").readText().trim()

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

fun <T> timed(func: () -> T): T {
    val start = System.currentTimeMillis()
    val result = func()
    val end = System.currentTimeMillis()
    println("Elapsed time: ${end - start}ms")
    return result
}
