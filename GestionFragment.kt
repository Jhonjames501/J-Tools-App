package com.jtools.app

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.jtools.app.databinding.FragmentGestion

class GestionFragment : Fragment() {

    private var _binding: FragmentGestion? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGestion.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val prefs = requireActivity().getSharedPreferences("JToolsPrefs", Context.MODE_PRIVATE)

        binding.switchActivarBoton.isChecked = prefs.getBoolean("activar_boton", true)
        binding.switchActivarBoton.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit().putBoolean("activar_boton", isChecked).apply()
            val intent = Intent(requireContext(), FloatingService::class.java)
            if (isChecked) requireContext().startForegroundService(intent)
            else requireContext().stopService(intent)
        }

        binding.seekbarTransparencia.progress = prefs.getInt("transparencia", 65)
        binding.seekbarTransparencia.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                prefs.edit().putInt("transparencia", progress).apply()
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        binding.btnPosIzq.setOnClickListener {
            prefs.edit().putInt("float_gravity", Gravity.START or Gravity.CENTER_VERTICAL).apply()
            Toast.makeText(requireContext(), "Posición: Izquierda", Toast.LENGTH_SHORT).show()
        }
        binding.btnPosDer.setOnClickListener {
            prefs.edit().putInt("float_gravity", Gravity.END or Gravity.CENTER_VERTICAL).apply()
            Toast.makeText(requireContext(), "Posición: Derecha", Toast.LENGTH_SHORT).show()
        }
        binding.btnPosCen.setOnClickListener {
            prefs.edit().putInt("float_gravity", Gravity.CENTER).apply()
            Toast.makeText(requireContext(), "Posición: Centro", Toast.LENGTH_SHORT).show()
        }

        binding.switchAutoOcultar.isChecked = prefs.getBoolean("auto_ocultar", false)
        binding.switchAutoOcultar.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit().putBoolean("auto_ocultar", isChecked).apply()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
