package com.example.numberguesser

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import kotlin.random.Random

/**
 * A simple [Fragment] subclass.
 * Use the [game_fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class game_fragment : Fragment() {
    private var guesses: Int = 0
        set(value) {
            field = value
            guessesTextView.text = getString(R.string.numberOfGuesses) + ' ' + value.toString()
        }

    private lateinit var infoText: TextView
    private var guessedNumber: Int = 0
    private lateinit var progressBar: SeekBar
    private lateinit var guessesTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_game_fragment, container, false)

        infoText = view.findViewById(R.id.currentText)
        val numberText = view.findViewById<TextView>(R.id.currentValue)
        numberText.text = "0"
        progressBar = view.findViewById(R.id.seekBar)
        progressBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (seekBar == null) {
                    return
                }
                numberText.text = seekBar.progress.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        guessesTextView = view.findViewById(R.id.guessesText)
        val guessButton = view.findViewById<Button>(R.id.guess_button)
        guessButton.setOnClickListener { onGuessButtonClicked() }
        val restartButton: Button = view.findViewById(R.id.restart_button)
        restartButton.setOnClickListener { startGame() }
        val plusButton: Button = view.findViewById(R.id.plus_button)
        plusButton.setOnClickListener { onPlusButtonClicked() }
        val minusButton: Button = view.findViewById(R.id.minus_button)
        minusButton.setOnClickListener { onMinusButtonClicked() }
        startGame()
        return view
    }

    private fun startGame() {
        guesses = 0
        generateNumber()
        infoText.text = getString(R.string.start_game_text)
    }

    private fun generateNumber() {
        guessedNumber = Random(System.nanoTime()).nextInt(0, 100)
    }

    private fun onGuessButtonClicked() {
        ++guesses
        val value = progressBar.progress
        if (value == guessedNumber){
            infoText.text = getString(R.string.game_won_text) + ' ' + guessedNumber
        }
        else if (value > guessedNumber){
            infoText.text = getString(R.string.number_is_less) + ' ' + value.toString()
        }
        else {
            infoText.text = getString(R.string.number_is_greater_than) + ' ' + value.toString()
        }
    }

    private fun onPlusButtonClicked() {
        progressBar.progress++
    }

    private fun onMinusButtonClicked() {
        progressBar.progress--
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         * @return A new instance of fragment game_fragment.
         */
        @JvmStatic
        fun newInstance() =
            game_fragment().apply {
                arguments = Bundle().apply {}
            }
    }
}