package com.zionhuang.music.ui.viewholders

import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.zionhuang.music.R
import com.zionhuang.music.databinding.ItemSongBinding
import com.zionhuang.music.db.entities.Song
import com.zionhuang.music.download.DownloadTask
import com.zionhuang.music.ui.listeners.SongPopupMenuListener

class SongViewHolder(
        val binding: ItemSongBinding,
        private val popupMenuListener: SongPopupMenuListener,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(song: Song) {
        binding.song = song
        binding.btnMoreAction.setOnClickListener { view ->
            PopupMenu(view.context, view).apply {
                inflate(R.menu.menu_song)
                setOnMenuItemClickListener {
                    when (it.itemId) {
                        R.id.action_edit -> popupMenuListener.editSong(song, view)
                        R.id.action_download -> popupMenuListener.downloadSong(song.id, view.context)
                        R.id.action_delete -> popupMenuListener.deleteSong(song.id)
                    }
                    true
                }
                menu.findItem(R.id.action_download).isVisible = song.downloadState == DownloadTask.STATE_NOT_DOWNLOADED
                show()
            }
        }
        binding.executePendingBindings()
    }

    fun setProgress(task: DownloadTask) {
        binding.progressBar.apply {
            max = task.totalBytes.toInt()
            progress = task.currentBytes.toInt()
        }
    }
}