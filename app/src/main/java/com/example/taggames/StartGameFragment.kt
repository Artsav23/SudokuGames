package com.example.taggames

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.taggames.databinding.FragmentStartGameBinding

class StartGameFragment : Fragment() {

    lateinit var binding: FragmentStartGameBinding
    private var delCells = 20
    private val gray = "#252525"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentStartGameBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.start.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.placeholder, SudokuGamesFragment(delCells)).commit()
        }

        binding.easy.setOnClickListener {
            chooseDifficultyLevel(10)
        }
        binding.medium.setOnClickListener {
            chooseDifficultyLevel(20)
        }
        binding.hard.setOnClickListener {
            chooseDifficultyLevel(30)
        }
    }

    private fun chooseDifficultyLevel(deleteCells: Int) {
        binding.hard.setTextColor(Color.WHITE)
        binding.easy.setTextColor(Color.parseColor(gray))
        binding.medium.setTextColor(Color.parseColor(gray))
        delCells = deleteCells
    }
}