package uz.itschool.audioplayer.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.itschool.audioplayer.databinding.MusicIteamBinding
import uz.itschool.audioplayer.model.Song

class AdapterSong(
    var songList: MutableList<Song>,
    var mySong: MySong,
    var context: Context
) : RecyclerView.Adapter<AdapterSong.MyHolder>() {

    class MyHolder(binding: MusicIteamBinding) : RecyclerView.ViewHolder(binding.root) {
        var name = binding.name
        var img = binding.image
        var play = binding.play
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(
            MusicIteamBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val song = songList[position]
        holder.name.text = song.name
        holder.img.setImageResource(song.img)

        holder.play.setOnClickListener {
            mySong.onItemClick(song)
        }

    }

    override fun getItemCount(): Int {
        return songList.size
    }

    interface MySong {
        fun onItemClick(song: Song)
    }
}