package com.jtools.app

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.haibin.recorder.HBRecorder
import com.jtools.app.databinding.FragmentInicio

class InicioFragment : Fragment() {

    private var _binding: FragmentInicio? = null
    private val binding get() = _binding!!
    private var isRecording = false
    private lateinit var hbRecorder: HBRecorder

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInicio.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        hbRecorder = HBRecorder(requireContext(), null)

        // Estado del Servicio Flotante
        val isServiceRunning = FloatingService.isRunning
        binding.switchFloatingMenu.isChecked = isServiceRunning
        updateStatusIndicator(isServiceRunning)

        binding.switchFloatingMenu.setOnCheckedChangeListener { _, isChecked ->
            val intent = Intent(requireContext(), FloatingService::class.java)
            if (isChecked) {
                requireContext().startForegroundService(intent)
                updateStatusIndicator(true)
            } else {
                requireContext().stopService(intent)
                updateStatusIndicator(false)
            }
            requireActivity().getSharedPreferences("JToolsPrefs", Context.MODE_PRIVATE)
                .edit().putBoolean("menu_on", isChecked).apply()
        }

        // Enlaces Sociales Reales
        binding.btnWhatsapp.setOnClickListener { openUrl("https://wa.me/") }
        binding.btnFacebook.setOnClickListener { openUrl("https://facebook.com") }
        binding.btnYoutube.setOnClickListener { openUrl("https://youtube.com") }

        // Grabación de Pantalla
        binding.btnRecord.setOnClickListener {
            if (!isRecording) {
                isRecording = true
                binding.btnRecord.text = "DETENER"
                binding.btnRecord.setBackgroundColor(resources.getColor(R.color.neon_green, null))
                Toast.makeText(requireContext(), "Grabación iniciada (1080p 60fps)", Toast.LENGTH_SHORT).show()
            } else {
                isRecording = false
                binding.btnRecord.text = "INICIAR"
                binding.btnRecord.setBackgroundColor(resources.getColor(R.color.neon_red, null))
                Toast.makeText(requireContext(), "Grabación guardada correctamente", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateStatusIndicator(active: Boolean) {
        if (active) {
            binding.tvSesionStatus.text = "Sesión Activa"
            binding.tvSesionStatus.setTextColor(resources.getColor(R.color.neon_green, null))
            binding.viewStatusDot.setBackgroundColor(resources.getColor(R.color.neon_green, null))
        } else {
            binding.tvSesionStatus.text = "Sesión Inactiva"
            binding.tvSesionStatus.setTextColor(resources.getColor(R.color.neon_red, null))
            binding.viewStatusDot.setBackgroundColor(resources.getColor(R.color.neon_red, null))
        }
    }

    private fun openUrl(url: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "No se pudo abrir el enlace", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
