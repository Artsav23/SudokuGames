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
    private var deleteCells = 20
    private val grayColor = Color.parseColor("#252525")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentStartGameBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.start.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.placeholder, SudokuGamesFragment(deleteCells)).commit()
        }

        binding.easy.setOnClickListener {
            chooseDifficultyLevel(Complexity.Easy.cells)
        }
        binding.medium.setOnClickListener {
            chooseDifficultyLevel(Complexity.Medium.cells)
        }
        binding.hard.setOnClickListener {
            chooseDifficultyLevel(Complexity.Hard.cells)
        }
    }

    private fun chooseDifficultyLevel(deleteCells: Int) {
        binding.hard.setTextColor(Color.WHITE)
        binding.easy.setTextColor(grayColor)
        binding.medium.setTextColor(grayColor)
        this.deleteCells = deleteCells
    }
}