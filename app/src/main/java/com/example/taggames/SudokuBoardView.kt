package com.example.taggames

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import kotlin.math.min

class SudokuBoardView(context: Context, attributeSet: AttributeSet): View(context, attributeSet) {

    private var size = 9
    private var sqrtSize = 3
    private var cellSizePixels = 0f
    var selectedRow = -1
    var selectedCol = -1
    var delCells = 20
    var cellsNumberArrays:Array<Array<CellModel>> = Array(size) { Array(size) { CellModel("", true) } }

    private val thickLinePaint = Paint().apply {
        color = Color.BLACK
        strokeWidth = 6f
        style = Paint.Style.STROKE
    }
    private val thinLinePaint = Paint().apply {
        color = Color.BLACK
        strokeWidth = 4f
        style = Paint.Style.STROKE
    }
    private val selectedCellPaint = Paint().apply {
        style = Paint.Style.FILL_AND_STROKE
        color = Color.GRAY
    }
    private val conflictingCellPaint = Paint().apply {
        style = Paint.Style.FILL_AND_STROKE
        color = Color.parseColor("#E0E0E0")
    }
    private val  textPaint = Paint().apply {
        color = Color.BLACK
        style = Paint.Style.FILL
        textSize = 24f
    }
    private val emptyCellsPaint = Paint().apply {
        style = Paint.Style.FILL_AND_STROKE
        color = Color.parseColor("#B3B3B3")
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) { //задаёт размер объекта
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val sizePixels = min(widthMeasureSpec, heightMeasureSpec)
        changeArray()
        deleteNum()
        setMeasuredDimension(sizePixels, sizePixels)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawColor(Color.WHITE)
        cellSizePixels = (width / size).toFloat()
        fillCells(canvas)
        drawFalseCells(canvas)
        fellCell(canvas, selectedRow, selectedCol, selectedCellPaint)
        checkCells(canvas)
        drawLines(canvas)
        drawText(canvas)
    }

    private fun changeArray() {
        for (i in 0..2) {
            addLineNum(i, (i + 1) * 3)
        }
        for (i in 3..5) {
            addLineNum(i, (i % 3) * 3 + 1)
        }
        for (i in 6..8) {
            addLineNum(i, (i % 6) * 3 + 2)
        }
        repeat((0..5).random()){
            val intervals = mutableListOf(0..2, 3..5, 6..8).random()
            mixRow(intervals)
            mixCol(intervals)
        }
    }

    private fun addLineNum(r: Int, pos: Int) {
        val startNum = size - pos
        for (c in startNum until size) {
            cellsNumberArrays[r][c].number = (c - startNum + 1).toString()
            cellsNumberArrays[r][c].isEditable = true
        }
        val endNum = size - pos
        for (c in 0 until endNum) {
            cellsNumberArrays[r][c].number = (c + pos + 1).toString()
            cellsNumberArrays[r][c].isEditable = true
        }
    }

    private fun mixRow(interval: IntRange) {
        val randomRow = interval.random()
        var randomRow2 = interval.random()
        while (randomRow == randomRow2) {
            randomRow2 = interval.random()
        }
        val array = cellsNumberArrays[randomRow]
        cellsNumberArrays[randomRow] = cellsNumberArrays[randomRow2]
        cellsNumberArrays[randomRow2] = array
    }

    private fun mixCol(interval: IntRange) {
        val randomCol = interval.random()
        var randomCol2 = interval.random()
        while (randomCol == randomCol2) {
            randomCol2 = interval.random()
        }
        val array = Array(9, { CellModel("", true)})
        for (i in 0 until size) {
            array[i] = cellsNumberArrays[i][randomCol]
            cellsNumberArrays[i][randomCol] = cellsNumberArrays[i][randomCol2]
            cellsNumberArrays[i][randomCol2] = array[i]
        }
    }

    private fun deleteNum() {
        repeat(delCells) {
            var a = true
            while (a) {
                val col = (0..8).random()
                val row = (0..8).random()
                if (cellsNumberArrays[row][col].number !== "") {
                    a = false
                    cellsNumberArrays[row][col] = CellModel("", a)
                }
            }
        }
    }

    private fun drawFalseCells(canvas: Canvas?) {
        for (r in 0 until size) {
            for (c in 0 until size) {
                if (!cellsNumberArrays[r][c].isEditable) {
                    drawEmptyCell(r, c, canvas)
                }
            }
        }
    }

    private fun drawEmptyCell(r: Int, c: Int, canvas: Canvas?) {
        canvas?.drawRect((c * cellSizePixels), (r * cellSizePixels), (c+1) * cellSizePixels, (r+1) * cellSizePixels, emptyCellsPaint)
    }

    private fun fillCells(canvas: Canvas?) {
        if (selectedRow == -1 || selectedCol == -1) return

        for (r in 0..size) {
            for (c in 0..size) {
                checkKindCell(r, c, canvas)
            }
        }
    }

    private fun checkKindCell(r: Int, c: Int, canvas: Canvas?) {
        if (r == selectedRow || c == selectedCol) fellCell(canvas, r, c, conflictingCellPaint)

        else if (r / sqrtSize == selectedRow / sqrtSize && c / sqrtSize == selectedCol / sqrtSize)
            fellCell(canvas, r, c, conflictingCellPaint)
    }

    private fun fellCell(canvas: Canvas?, r: Int, c:Int, paint: Paint) {
        canvas?.drawRect((c * cellSizePixels), (r * cellSizePixels),
            (c+1) * cellSizePixels, (r+1) * cellSizePixels, paint)
    }

    private fun checkCells(canvas: Canvas?) {
        var a = true
        for (i in 0 until size) {
            a = checkStroke(i)

            if (!a) break
        }
        if (a) trueCells(canvas)
    }

    private fun  checkStroke(i: Int): Boolean {
        if (CellModel("", false) in cellsNumberArrays[i]
            || cellsNumberArrays[i].distinct().count() != size) {
            return false
        }
        return true
    }

    private fun trueCells(canvas: Canvas?) {
        for (r in 0 until size) {
            for (c in 0 until  size) {
                cellsNumberArrays[r][c].isEditable = true
            }
        }
        canvas?.drawColor(Color.GREEN)
    }

    private fun drawLines(canvas: Canvas?) {
        canvas?.drawRect(0f,0f, width.toFloat(), height.toFloat(), thickLinePaint)
        for (i in 1 until size) {
            val paintToUse = when (i % sqrtSize) {
                0 -> thickLinePaint
                else -> thinLinePaint
            }
            canvas?.drawLine(i * cellSizePixels, 0f,
                i * cellSizePixels, height.toFloat(), paintToUse)
            canvas?.drawLine(0f, i * cellSizePixels,
                width.toFloat(), i * cellSizePixels, paintToUse)
        }
    }

    private fun drawText(canvas: Canvas?) {
        for (r in 0 until size) {
            for (c in 0 until size) {
                canvas?.drawText(cellsNumberArrays[r][c].number, (cellSizePixels * (c) + (cellSizePixels/3f)),
                    (cellSizePixels * (r) + (cellSizePixels/1.5f)), textPaint.apply {
                        textSize = cellSizePixels / 2f
                        })
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        handleTouchEvent(event.x, event.y)
        return true
    }

    private fun handleTouchEvent(x:Float, y: Float) {
        selectedCol = (x / cellSizePixels).toInt()
        selectedRow = (y / cellSizePixels).toInt()
        invalidate()

    }

    fun changeNumber(row: Int, column: Int, num: Int) {
        if (!cellsNumberArrays[row][column].isEditable) {
            cellsNumberArrays[row][column].number = num.toString()
            invalidate()
        }
    }

    fun restart() {
        changeArray()
        deleteNum()
        invalidate()
    }
}