package com.feisu.noise.audio

interface IPlayingCallback {
    fun changNoise()

    fun onPlay(isPlay:Boolean)
}