import java.util.*
import kotlin.random.Random


class FloodIt(private val size: Int, private val colorsCount: Int) {
    private val field = Array(size) { i -> Array(size) { j -> Dot(Pair(i, j), Random.nextInt(0, colorsCount)) } }
    private val dRow = arrayOf(-1, 0, 1, 0)
    private val dCol = arrayOf(0, 1, 0, -1)

    data class Dot(
        val position: Pair<Int, Int>,
        var color: Int,
        var isVisited: Boolean = false,
        var isActive: Boolean = true
    )

    init {
        while (!field.all { it.all { equals(field[0][0]) } }) {
            printField()
            val color = readLine()!!.toInt()
            BFS(0, 0, color)
        }
    }

    private fun printField() {
        field.forEach { it ->
            println(it.map { it.color }.joinToString(" "))
        }
    }

    private fun makeUnvisited() {
        for (i in field.indices) {
            for (j in field[0].indices) {
                field[i][j].isVisited = false
            }
        }
    }

    private fun isValid(adjY: Int, adjX: Int, i: Int, j: Int): Boolean {
        if (adjY < 0 || adjX < 0 || adjY >= size || adjX >= size) return false
        if (!field[adjY][adjX].isVisited) {
            if (field[adjY][adjX].isActive) {
                if (field[adjY][adjX].color == field[i][j].color) return true
            } else return true
        }
        return false
    }

    private fun BFS(i: Int, j: Int, color: Int) {
        val q: Queue<Pair<Int, Int>> = LinkedList()
        q.add(Pair(i, j))
        field[i][j].isVisited = true
        val cellsToBeChanged = mutableListOf<Pair<Int, Int>>()
        while (!q.isEmpty()) {
            val cell = q.peek()
            val y = cell.first
            val x = cell.second
            cellsToBeChanged.add(Pair(y, x))
            q.remove()
            for (k in 0..3) {
                val adjX = x + dCol[k]
                val adjY = y + dRow[k]
                if (isValid(adjY, adjX, i, j)) {
                    q.add(Pair(adjY, adjX))
                    field[adjY][adjX].isVisited = true
                }
            }
        }
        for ((y, x) in cellsToBeChanged) {
            field[y][x].color = color
            field[y][x].isActive = false
        }
        makeUnvisited()
    }
}

fun main() {
    println("Enter size: ")
    val size = readLine()!!.toInt()
    println("Enter colors count: ")
    val colorsCount = readLine()!!.toInt()
    FloodIt(size, colorsCount)
}