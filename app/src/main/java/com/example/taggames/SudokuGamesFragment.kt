package com.example.taggames

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.example.taggames.databinding.FragmentSudokuGamesBinding


class SudokuGamesFragment(private val delCells: Int) : Fragment() {

    private lateinit var binding: FragmentSudokuGamesBinding

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
        binding.oneButton.setOnClickListener { onClickNumberButton(1) }
        binding.twoButton.setOnClickListener { onClickNumberButton(2) }
        binding.threeButton.setOnClickListener { onClickNumberButton(3) }
        binding.fourButton.setOnClickListener { onClickNumberButton(4) }
        binding.fiveButton.setOnClickListener { onClickNumberButton(5) }
        binding.sixButton.setOnClickListener { onClickNumberButton(6) }
        binding.sevenButton.setOnClickListener { onClickNumberButton(7) }
        binding.eightButton.setOnClickListener { onClickNumberButton(8) }
        binding.nineButton.setOnClickListener { onClickNumberButton(9) }
    }

    private fun onClickNumberButton(num: Int) {
        val selectedRow = binding.sudokuBoardView.selectedRow
        val selectedCol = binding.sudokuBoardView.selectedCol
        if (selectedRow != -1) {
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