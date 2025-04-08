package co.tiagoaguiar.meuprimeiroaplicativo

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.util.Random

class MainActivity : AppCompatActivity() {

    private lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val textInput: EditText = findViewById(R.id.inputText)
        val outputText: TextView = findViewById(R.id.outpu_text)
        val buttonGenerator: Button = findViewById(R.id.button_gerator)

        preferences = getSharedPreferences("db", Context.MODE_PRIVATE)
        val result = preferences.getString("result", null)

        result?.let{
            outputText.text = "Ultima aposta: $result"
        }

        buttonGenerator.setOnClickListener{
            val text = textInput.text.toString()
            NumberGenerator(text, outputText)
        }

    }



    private fun NumberGenerator(textInput: String, textOutput: TextView){

        //texto vazio
        if(textInput.isEmpty()){
            Toast.makeText(this,"Por favor informe um numero entre 6 e 15", Toast.LENGTH_LONG).show()
            return
        }

        val qtd = textInput.toInt()

        if(qtd < 6 || qtd > 15){
            Toast.makeText(this,"Por favor informe um numero entre 6 e 15", Toast.LENGTH_LONG).show()
            return
        }

        val numbers = mutableSetOf<Int>()
        val random = Random()

        while (true){
            val number = random.nextInt(60)
            numbers.add(number + 1)

            if(numbers.size == qtd){
                break
            }
        }

        textOutput.text = numbers.joinToString("-")

        val editor = preferences.edit()
        preferences.edit().apply {
            putString("result", textOutput.text.toString())
            apply()
        }
    }



}