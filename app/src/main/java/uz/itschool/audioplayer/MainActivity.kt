package uz.itschool.audioplayer

import android.media.MediaPlayer
import android.media.MediaPlayer.OnCompletionListener
import android.os.Bundle
import android.os.Handler
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.appcompat.app.AppCompatActivity
import uz.itschool.audioplayer.adapter.AdapterSong
import uz.itschool.audioplayer.databinding.ActivityMainBinding
import uz.itschool.audioplayer.model.Song


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var isPlaying = false
    private lateinit var mediaPlayer: MediaPlayer
    private var nextSong: Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var list = mutableListOf<Song>()
        list.add(Song("Wanna be yours", R.drawable.wanna, R.raw.wanna))
        list.add(Song("Paint the town red", R.drawable.siu, R.raw.bitch))
        list.add(Song("Lana Del Rey - Say Yes to Heaven", R.drawable.img, R.raw.lanadelreysyth))
        list.add(Song("Daylight", R.drawable.daylight, R.raw.daylight))
        list.add(Song("Withoutme", R.drawable.withoutme, R.raw.withoutme))
        list.add(Song("Jingalamo", R.drawable.jingalamo, R.raw.jingalamo))

        mediaPlayer = MediaPlayer.create(this, R.raw.lanadelreydmd)
        binding.seekBar.max = mediaPlayer.duration / 1000
        nextSong = R.raw.lanadelreydmd

        binding.recyclerView.adapter = AdapterSong(list, object : AdapterSong.MySong {
            override fun onItemClick(song: Song) {
                mediaPlayer.stop()
                mediaPlayer = MediaPlayer.create(this@MainActivity, song.song)
                mediaPlayer.start()
                binding.play.setText("Pause")
                isPlaying = true
                binding.seekBar.progress = 0
                nextSong = song.song
            }

        }, this)

        val mHandler = Handler()
        runOnUiThread(object : Runnable {
            override fun run() {
                val mCurrentPosition: Int = mediaPlayer.currentPosition / 1000
                binding.seekBar.progress = mCurrentPosition
                mHandler.postDelayed(this, 1000)
            }
        })

        binding.seekBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                if (mediaPlayer != null && b) {
                    mediaPlayer.seekTo(i * 1000)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })
        mediaPlayer.setOnCompletionListener(OnCompletionListener {
            for (i in 0..<list.size) {
                if (nextSong == list[i].song) {
                    if (i == list.size-1) {
                        var song = list[0].song
                        mediaPlayer.stop()
                        mediaPlayer = MediaPlayer.create(this@MainActivity, song)
                        mediaPlayer.start()
                        binding.play.setText("Pause")
                        isPlaying = true
                        binding.seekBar.progress = 0
                        nextSong = song
                    } else {
                        var song = list[i+1].song
                        mediaPlayer.stop()
                        mediaPlayer = MediaPlayer.create(this@MainActivity, song)
                        mediaPlayer.start()
                        binding.play.setText("Pause")
                        isPlaying = true
                        binding.seekBar.progress = 0
                        nextSong = song
                    }
                }

            }
        })

        binding.play.setOnClickListener {
            if (!isPlaying) {
                mediaPlayer.start()
                binding.play.setText("Pause")
                isPlaying = true
            } else if (isPlaying) {
                mediaPlayer.pause()
                binding.play.setText("Play")
                isPlaying = false
            }
        }
        binding.next.setOnClickListener {
            for (i in 0..<list.size) {
                if (nextSong == list[i].song) {
                    if (i == list.size-1) {
                        var song = list[0].song
                        mediaPlayer.stop()
                        mediaPlayer = MediaPlayer.create(this@MainActivity, song)
                        mediaPlayer.start()
                        binding.play.setText("Pause")
                        isPlaying = true
                        binding.seekBar.progress = 0
                        nextSong = song
                        return@setOnClickListener
                    } else {
                        var song = list[i+1].song
                        mediaPlayer.stop()
                        mediaPlayer = MediaPlayer.create(this@MainActivity, song)
                        mediaPlayer.start()
                        binding.play.setText("Pause")
                        isPlaying = true
                        binding.seekBar.progress = 0
                        nextSong = song
                        return@setOnClickListener
                    }
                }

            }
        }

        binding.back.setOnClickListener {
            for (i in 0..<list.size) {
                if (nextSong == list[i].song) {
                    if (i == 0) {
                        var song = list[list.size-1].song
                        mediaPlayer.stop()
                        mediaPlayer = MediaPlayer.create(this@MainActivity, song)
                        mediaPlayer.start()
                        binding.play.setText("Pause")
                        isPlaying = true
                        binding.seekBar.progress = 0
                        nextSong = song
                        return@setOnClickListener
                    } else {
                        var song = list[i-1].song
                        mediaPlayer.stop()
                        mediaPlayer = MediaPlayer.create(this@MainActivity, song)
                        mediaPlayer.start()
                        binding.play.setText("Pause")
                        isPlaying = true
                        binding.seekBar.progress = 0
                        nextSong = song
                        return@setOnClickListener
                    }
                }

            }
        }


    }
}
