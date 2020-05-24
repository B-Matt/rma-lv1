package com.matejarlovic.soundboard

import android.media.AudioAttributes
import android.media.AudioManager
import android.media.SoundPool
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var soundPool: SoundPool

    private var mamicSound: Int = 0
    private var ramsaySound: Int = 0
    private var trumpSound: Int = 0
    private var arnoldSound: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Setup sounds
        initSound()
        loadSounds()

        // Click Listeners
        mamicImage.setOnClickListener {
            playSound(mamicSound)
        }

        ramsayImage.setOnClickListener {
            playSound(ramsaySound)
        }

        trumpImage.setOnClickListener {
            playSound(trumpSound)
        }

        terminatorImage.setOnClickListener {
            playSound(arnoldSound)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        soundPool.release()
    }

    private fun initSound() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val audioAttributes = AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setFlags(AudioAttributes.FLAG_AUDIBILITY_ENFORCED)
                .setUsage(AudioAttributes.USAGE_GAME)
                .build()

            soundPool = SoundPool.Builder()
                .setMaxStreams(5)
                .setAudioAttributes(audioAttributes)
                .build()
        } else {
            soundPool = SoundPool(5, AudioManager.STREAM_MUSIC, 0)
        }
    }

    private fun loadSounds() {
        mamicSound = soundPool.load(this, R.raw.mamic_sound, 1);
        ramsaySound = soundPool.load(this, R.raw.ramsay_sound, 1);
        trumpSound = soundPool.load(this, R.raw.trump_sound, 1);
        arnoldSound = soundPool.load(this, R.raw.arnold_sound, 1);
    }

    private fun playSound(sound: Int) {
        soundPool.play(sound, 1f,1f, 0 ,0, 1f)
    }
}
