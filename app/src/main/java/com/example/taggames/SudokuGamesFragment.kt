package com.example.taggames

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.example.taggames.databinding.FragmentSudokuGamesBinding


class SudokuGamesFragment(private val delCells: Int) : Fragment() {

    lateinit var binding: FragmentSudokuGamesBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSudokuGamesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.sudokuBoardView.delCells = delCells
        clickListener()
        setHasOptionsMenu(true)
    }

    private fun clickListener() {
        binding.oneTV.setOnClickListener { onClickNum(1) }
        binding.twoTV.setOnClickListener { onClickNum(2) }
        binding.threeTV.setOnClickListener { onClickNum(3) }
        binding.fourTV.setOnClickListener { onClickNum(4) }
        binding.fiveTV.setOnClickListener { onClickNum(5) }
        binding.sixTV.setOnClickListener { onClickNum(6) }
        binding.sevenTV.setOnClickListener { onClickNum(7) }
        binding.eightTV.setOnClickListener { onClickNum(8) }
        binding.nineTV.setOnClickListener { onClickNum(9) }
    }

    private fun onClickNum(num: Int) {
        val selectedRow = binding.sudokuBoardView.selectedRow
        val selectedCol = binding.sudokuBoardView.selectedCol
        if (selectedRow != -1){
            binding.sudokuBoardView.changeNumber(selectedRow,selectedCol, num)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> parentFragmentManager.beginTransaction().replace(R.id.placeholder, StartGameFragment()).commit()
            R.id.retry -> binding.sudokuBoardView.restart()
        }
        return super.onOptionsItemSelected(item)
    }
}