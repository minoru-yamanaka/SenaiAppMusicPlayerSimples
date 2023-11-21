package com.example.appmusica;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {

    // Declaração de variáveis
    private MediaPlayer mediaPlayer;  // Objeto para reprodução de áudio
    private SeekBar seekVolume;  // SeekBar para controlar o volume
    private AudioManager audioManager;  // Objeto para controlar o áudio do dispositivo

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicialização do MediaPlayer com o áudio "teresa" da pasta 'res/raw'
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.teresa);

        // Inicialização do SeekBar e configuração do áudio
        inicializarSeekBar();
    }

    // Método para iniciar a reprodução de áudio
    public void play(View view) {
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
    }

    // Método para pausar a reprodução de áudio
    public void parar(View view) {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    // Método para parar a reprodução de áudio e reiniciar
    public void stop(View view) {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            // adicione aqui o nome da música após
            mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.teresa);
        }
    }

    // Método para configurar o SeekBar e controlar o volume
    private void inicializarSeekBar() {
        seekVolume = findViewById(R.id.seekVolume);

        // Configurar o áudio
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        int volumeMaximo = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int volumeAtual = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        // Configurar os valores para SeekBar
        // Volume Máximo
        seekVolume.setMax(volumeMaximo);
        // Volume Atual
        seekVolume.setProgress(volumeAtual);

        // Configurar um ouvinte para o SeekBar
        seekVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Ajustar o volume com base no progresso do SeekBar
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, AudioManager.FLAG_SHOW_UI);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Ações a serem executadas quando o usuário começa a interagir com o SeekBar
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Ações a serem executadas quando o usuário para de interagir com o SeekBar
            }
        });
    }

    // Sobrescrito do método onStop para pausar a reprodução quando a activity é pausada
    @Override
    protected void onStop() {
        super.onStop();
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    // Sobrescrito do método onDestroy para liberar recursos quando a activity é destruída
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
