package org.example

import kotlin.random.Random

val MINE = 'X'
val SAFECELL = '.'
val MARKEDCELL = '*'
val EXPLOREDCELL = '/'


data class Coordinate(val x: Int, val y: Int)

class MineField(val rows: Int, val columns: Int, val numberMines: Int) {
    val field: MutableList<MutableList<Char>> = mutableListOf()
    val shownField: MutableList<MutableList<Char>> = mutableListOf()
    val listOfMines: MutableList<Coordinate> = mutableListOf()
    var isGameFinish: Boolean
    var step = 0
    val checkedCells: MutableList<Coordinate> = mutableListOf()
    val uncheckedCells: MutableList<Coordinate> = mutableListOf()

    init {
        isGameFinish = false
        //generate fields with all safe cells
        for (i in 0 until rows) {
            val tempList1: MutableList<Char> = mutableListOf()
            val tempList2: MutableList<Char> = mutableListOf()
            for (j in 0 until columns) {
                tempList1.add(SAFECELL)
                tempList2.add(EXPLOREDCELL)
            }
            field.add(tempList2)
            shownField.add(tempList1)
        }
        //generate mines on the field
        var countMines = 0
        do {
            val x = Random.nextInt(0, rows)
            val y = Random.nextInt(0, columns)
            if (field[x][y] != MINE) {
                field[x][y] = MINE
                listOfMines.add(Coordinate(x, y))
                countMines++
            }
        } while (countMines != numberMines)
    }

    fun calculateMines(x: Int, y: Int) {
        if (field[x][y] == EXPLOREDCELL) {
            field[x][y] = '1'
        } else {
            field[x][y] = field[x][y] + 1
        }
    }

    fun showTips() {
        for (element in listOfMines) {
            if (element.x + 1 < rows && element.y + 1 < columns && field[element.x + 1][element.y + 1] != MINE) {
                calculateMines(element.x + 1, element.y + 1)
            }
            if (element.x - 1 >= 0 && element.y + 1 < columns && field[element.x - 1][element.y + 1] != MINE) {
                calculateMines(element.x - 1, element.y + 1)
            }
            if (element.x + 1 < rows && element.y - 1 >= 0 && field[element.x + 1][element.y - 1] != MINE) {
                calculateMines(element.x + 1, element.y - 1)
            }
            if (element.x - 1 >= 0 && element.y - 1 >= 0 && field[element.x - 1][element.y - 1] != MINE) {
                calculateMines(element.x - 1, element.y - 1)
            }
            if (element.x + 1 < rows && field[element.x + 1][element.y] != MINE) {
                calculateMines(element.x + 1, element.y)
            }
            if (element.x - 1 >= 0 && field[element.x - 1][element.y] != MINE) {
                calculateMines(element.x - 1, element.y)
            }
            if (element.y + 1 < rows && field[element.x][element.y + 1] != MINE) {
                calculateMines(element.x, element.y + 1)
            }
            if (element.y - 1 >= 0 && field[element.x][element.y - 1] != MINE) {
                calculateMines(element.x, element.y - 1)
            }
        }
    }

    fun printField() {
        println(
            " │123456789│\n" +
                    "—│—————————│"
        )

        repeat(rows) {
            println("${it + 1}|" + shownField[it].joinToString("") + "|")
        }

        println("—│—————————│")
    }

    fun resetField() {
        field.clear()
        listOfMines.clear()
        //generate fields with all safe cells
        for (i in 0 until rows) {
            val tempList: MutableList<Char> = mutableListOf()
            for (j in 0 until columns) {
                tempList.add(EXPLOREDCELL)
            }
            field.add(tempList)
        }
            //generate mines on the field
            var countMines = 0
            do {
                val x = Random.nextInt(0, rows)
                val y = Random.nextInt(0, columns)
                if (field[x][y] != MINE) {
                    field[x][y] = MINE
                    listOfMines.add(Coordinate(x, y))
                    countMines++
                }
            } while (countMines != numberMines)

    }

    fun checkMineAround(element: Coordinate): Boolean {
        if (element.x + 1 < rows && element.y + 1 < columns && field[element.x + 1][element.y + 1] == MINE) {
            return true
        }
        if (element.x - 1 >= 0 && element.y + 1 < columns && field[element.x - 1][element.y + 1] == MINE) {
            return true
        }
        if (element.x + 1 < rows && element.y - 1 >= 0 && field[element.x + 1][element.y - 1] == MINE) {
            return true
        }
        if (element.x - 1 >= 0 && element.y - 1 >= 0 && field[element.x - 1][element.y - 1] == MINE) {
            return true
        }
        if (element.x + 1 < rows && field[element.x + 1][element.y] == MINE) {
            return true
        }
        if (element.x - 1 >= 0 && field[element.x - 1][element.y] == MINE) {
            return true
        }
        if (element.y + 1 < rows && field[element.x][element.y + 1] == MINE) {
            return true
        }
        if (element.y - 1 >= 0 && field[element.x][element.y - 1] == MINE) {
            return true
        }
        return false
    }

    fun showCellsAround(element: Coordinate) {
        if (element.x + 1 < rows && element.y + 1 < columns) {
            shownField[element.x + 1][element.y + 1] = field[element.x + 1][element.y + 1]
            if (shownField[element.x + 1][element.y + 1] == EXPLOREDCELL) {
                uncheckedCells.add(Coordinate(element.x + 1, element.y + 1))
            }
        }
        if (element.x - 1 >= 0 && element.y + 1 < columns) {
            shownField[element.x - 1][element.y + 1] = field[element.x - 1][element.y + 1]
            if (shownField[element.x - 1][element.y + 1] == EXPLOREDCELL) {
                uncheckedCells.add(Coordinate(element.x - 1, element.y + 1))
            }
        }
        if (element.x + 1 < rows && element.y - 1 >= 0) {
            shownField[element.x + 1][element.y - 1] = field[element.x + 1][element.y - 1]
            if (shownField[element.x + 1][element.y - 1] == EXPLOREDCELL) {
                uncheckedCells.add(Coordinate(element.x + 1, element.y - 1))
            }
        }
        if (element.x - 1 >= 0 && element.y - 1 >= 0) {
            shownField[element.x - 1][element.y - 1] = field[element.x - 1][element.y - 1]
            if (shownField[element.x - 1][element.y - 1] == EXPLOREDCELL) {
                uncheckedCells.add(Coordinate(element.x - 1, element.y - 1))
            }
        }
        if (element.x + 1 < rows) {
            shownField[element.x + 1][element.y] = field[element.x + 1][element.y]
            if (shownField[element.x + 1][element.y] == EXPLOREDCELL) {
                uncheckedCells.add(Coordinate(element.x + 1, element.y))
            }
        }
        if (element.x - 1 >= 0) {
            shownField[element.x - 1][element.y] = field[element.x - 1][element.y]
            if (shownField[element.x - 1][element.y] == EXPLOREDCELL) {
                uncheckedCells.add(Coordinate(element.x - 1, element.y))
            }
        }
        if (element.y + 1 < rows) {
            shownField[element.x][element.y + 1] = field[element.x][element.y + 1]
            if (shownField[element.x][element.y + 1] == EXPLOREDCELL) {
                uncheckedCells.add(Coordinate(element.x, element.y + 1))
            }
        }
        if (element.y - 1 >= 0) {
            shownField[element.x][element.y - 1] = field[element.x][element.y - 1]
            if (shownField[element.x][element.y - 1] == EXPLOREDCELL) {
                uncheckedCells.add(Coordinate(element.x, element.y - 1))
            }
        }
    }

    fun freeCommand(element: Coordinate) {
        uncheckedCells.remove(element)
        checkedCells.add(element)
        showCellsAround(element)
    }

     fun processUserAction() {
        val (y, x, command) = readln().split(" ")
        val intX = x.toInt() - 1
        val intY = y.toInt() - 1
        val cord = Coordinate(intX, intY)
        if (command == "mine") {
            if (shownField[intX][intY] == MARKEDCELL) {
                shownField[intX][intY] = SAFECELL
            } else if (shownField[intX][intY] == SAFECELL) {
                shownField[intX][intY] = MARKEDCELL
            }
        } else if (command == "free") {
            if (listOfMines.contains(cord)) {
                //the first cell explored with the free command
                if (step == 0) {
                    //regenerate mines and tips
                    do {
                        resetField()
                        showTips()
                    } while (listOfMines.contains(cord))
                    step++
                    shownField[intX][intY] = field[intX][intY]
                    if (shownField[intX][intY] == EXPLOREDCELL) {
                        freeCommand(cord)
                        while (!uncheckedCells.isEmpty()) {
                            val temp = uncheckedCells.first()
                            if (!checkedCells.contains(temp)) {
                                shownField[temp.x][temp.y] = field[temp.x][temp.y]
                                freeCommand(temp)
                            } else uncheckedCells.remove(temp)
                        }
                    }
                } else {
                    println("You stepped on a mine and failed!")
                    shownField[intX][intY] = MINE
                    printField()
                    isGameFinish = true
                    return
                }
            } else {
                step++
                shownField[intX][intY] = field[intX][intY]
                if (shownField[intX][intY] == EXPLOREDCELL) {
                    freeCommand(cord)
                    while (!uncheckedCells.isEmpty()) {
                        val temp = uncheckedCells.first()
                        if (!checkedCells.contains(temp)) {
                            shownField[temp.x][temp.y] = field[temp.x][temp.y]
                            freeCommand(temp)
                        } else uncheckedCells.remove(temp)
                    }
                }

            }
        }
        printField()
    }

    fun isFinishGame(): Boolean {
        if (isGameFinish == true) {
            return true
        }
        var countMarkedMines = 0
        var wrongMineMark = 0
        for (i in 0 until rows) {
            for (j in 0 until columns) {
                if (shownField[i][j] == MARKEDCELL) {
                    if (listOfMines.contains(Coordinate(i, j))) {
                        countMarkedMines++
                    } else {
                        wrongMineMark++
                    }
                }
            }
        }
        if (countMarkedMines == listOfMines.size && wrongMineMark == 0 ) {
            println("Congratulations! You found all the mines!")
            return true
        } else {
            //Opening all the safe cells so that only those with unexplored mines are left
            for (i in 0 until rows) {
                for (j in 0 until columns) {
                    if (shownField[i][j] == SAFECELL && !listOfMines.contains(Coordinate(i, j)) ||
                        shownField[i][j] == MARKEDCELL && !listOfMines.contains(Coordinate(i, j))) {
                        return false
                    }
                }
            }
            println("Congratulations! You found all the mines!")
            return true
        }
    }
}


fun main() {
    println("How many mines do you want on the field?")
    val numberMines = readln().toInt()
    val mineField = MineField(9, 9, numberMines)
    mineField.showTips()
    mineField.printField()

    do {
        println("Set/unset mines marks or claim a cell as free:")
        mineField.processUserAction()
    } while (!mineField.isFinishGame())

}