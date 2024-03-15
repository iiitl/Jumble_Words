package com.example.android.unscramble.ui.game

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {

    private var wordsList: MutableList<String> = mutableListOf()
    private lateinit var currentWord: String
    var i = 0
    private var _score = MutableLiveData(0)
    val score: LiveData<Int>
        get() = _score

    private var _currentWordCount = MutableLiveData(0)
    val currentWordCount: LiveData<Int>
        get() = _currentWordCount

    private val _currentScrambledWord = MutableLiveData<String>()
    val currentScrambledWord: LiveData<String>
        get() = _currentScrambledWord
    public fun shuffle(str: String): String {
        val charArray = str.toCharArray()
        for (i in charArray.indices) {
            val randomIndex = charArray.indices.random()
            val temp = charArray[i]
            charArray[i] = charArray[randomIndex]
            charArray[randomIndex] = temp
        }
        return String(charArray)
    }

    private fun getNextWord() {
        currentWord = allWordsList.random()

        if (wordsList.contains(currentWord)) {
            getNextWord()
        } else {
            _currentScrambledWord.value = shuffle(currentWord)
            _currentWordCount.value = (_currentWordCount.value)?.inc()
            wordsList.add(currentWord)
        }

    }

    init {
        Log.d("GameFragment", "GameViewModel created!")
        getNextWord()
    }

    fun nextWord(): Boolean {
        return if (_currentWordCount.value!! < MAX_NO_OF_WORDS) {
            getNextWord()
            true
        } else false
    }

    private fun increaseScore() {
        _score.value = 20 * i
    }

    fun isUserWordCorrect(playerWord: String): Boolean {
        if (playerWord.equals(currentWord, true)) {
            i = i + 1
            increaseScore()
            return true
        }
        return false
    }

    fun reinitializeData() {
        i = 0
        _score.value = 0
        _currentWordCount.value = 0
        wordsList.clear()
        getNextWord()
    }

}