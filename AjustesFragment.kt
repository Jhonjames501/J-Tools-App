package com.jtools.app

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import com.jtools.app.databinding.FragmentAjustes

class AjustesFragment : Fragment() {

    private var _binding: FragmentAjustes? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAjustes.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val prefs = requireActivity().getSharedPreferences("JToolsPrefs", Context.MODE_PRIVATE)

        // SeekBar Tamaño Botón Flotante
        binding.seekbarTamanoBoton.progress = prefs.getInt("float_size_progress", 24)
        binding.seekbarTamanoBoton.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val actualSize = progress + 32
                binding.tvTamanoBoton.text = "Tamaño del Botón Flotante ${actualSize}px"
                prefs.edit().putInt("float_size_progress", progress).putInt("float_size", actualSize * 2).apply()
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        // SeekBar Opacidad Panel
        binding.seekbarOpacidadPanel.progress = prefs.getInt("panel_opacity", 72)
        binding.seekbarOpacidadPanel.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.tvOpacidadPanel.text = "Opacidad del Panel Transparente $progress%"
                prefs.edit().putInt("panel_opacity", progress).apply()
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        // Permisos Reales
        binding.switchPermisoStorage.setOnCheckedChangeListener { _, _ ->
            val intent = Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION)
            startActivity(intent)
        }

        binding.switchPermisoOverlay.setOnCheckedChangeListener { _, _ ->
            val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
