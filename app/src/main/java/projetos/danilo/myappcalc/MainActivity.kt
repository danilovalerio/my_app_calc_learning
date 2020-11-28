package projetos.danilo.myappcalc

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import projetos.danilo.myappcalc.constants.*
import projetos.danilo.myappcalc.model.CalcScreen

class MainActivity : AppCompatActivity() {
    // Iniciando o ViewModel
    private val model: MainViewModel by viewModels()

    private val btnNum0 by lazy { findViewById<Button>(R.id.btnNum0) }
    private val btnNum1 by lazy { findViewById<Button>(R.id.btnNum1) }
    private val btnNum2 by lazy { findViewById<Button>(R.id.btnNum2) }
    private val btnNum3 by lazy { findViewById<Button>(R.id.btnNum3) }
    private val btnNum4 by lazy { findViewById<Button>(R.id.btnNum4) }
    private val btnNum5 by lazy { findViewById<Button>(R.id.btnNum5) }
    private val btnNum6 by lazy { findViewById<Button>(R.id.btnNum6) }
    private val btnNum7 by lazy { findViewById<Button>(R.id.btnNum7) }
    private val btnNum8 by lazy { findViewById<Button>(R.id.btnNum8) }
    private val btnNum9 by lazy { findViewById<Button>(R.id.btnNum9) }
    private val btnPonto by lazy { findViewById<Button>(R.id.btnPonto) }
    private val btnAC by lazy { findViewById<Button>(R.id.btnAC) }
    private val btnLimpa by lazy { findViewById<ImageButton>(R.id.btnLimpa) }
    private val btnMais by lazy { findViewById<Button>(R.id.btnMais) }
    private val btnMenos by lazy { findViewById<Button>(R.id.btnMenos) }
    private val btnMultiplica by lazy { findViewById<Button>(R.id.btnMultiplica) }
    private val btnDivide by lazy { findViewById<Button>(R.id.btnDivide) }
    private val btnIgual by lazy { findViewById<Button>(R.id.btnIgual) }
    private val btnSinal by lazy { findViewById<Button>(R.id.btnSinal) }

    private val tvCalc by lazy { findViewById<TextView>(R.id.tvCalc) }
    private val tvNumbers by lazy { findViewById<TextView>(R.id.tvNumbers) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupListOfButtons()
    }

    private fun setupListOfButtons() {
        //Lista de botões da calculadora
        val listButton = listOf<View>(
            btnNum0, btnNum1, btnNum2, btnNum3,
            btnNum4, btnNum5, btnNum6, btnNum7,
            btnNum8, btnNum9, btnPonto, btnAC,
            btnLimpa, btnMais, btnMenos, btnMultiplica,
            btnDivide, btnIgual, btnSinal
        )

        for (item in listButton) {
            if(item is Button) {
                item.text = getBtnConstant(item).toString()
            }
        }

        //Configurando a função de clique para os botões
        for (item in listButton) {
            item.click()
        }

        // Setando viewmodel para receber as Strings
        // de cálculo e números
        model.getNumbers().observe(this, Observer<CalcScreen> {
            tvNumbers.text = it.number
            tvCalc.text = it.calc
        })
    }

    //Atribui a constante respectiva para cada botao
    private fun getBtnConstant(any: Any): Any? {
        return when(any) {
            btnNum0 -> NUM0
            btnNum1 -> NUM1
            btnNum2 -> NUM2
            btnNum3 -> NUM3
            btnNum4 -> NUM4
            btnNum5 -> NUM5
            btnNum6 -> NUM6
            btnNum7 -> NUM7
            btnNum8 -> NUM8
            btnNum9 -> NUM9
            btnPonto -> POINT
            btnSinal -> SIGNAL
            btnLimpa -> BACK
            btnAC -> AC
            btnMais -> PLUS
            btnMenos -> MINUS
            btnMultiplica -> MULTIPLY
            btnDivide -> DIVIDE
            btnIgual -> EQUAL
            else -> null
        }
    }

    // Inserindo o Listener no Botão
    private fun View.click() {
        this.setOnClickListener {
            send(it)
        }
    }

    // Função que envia o botão clicado
    private fun send(v: View) {
        // Seta o valor da variável c de acordo com a tecla clicada
        val c = getBtnConstant(v)
        if (c != null) {
            model.setClick(c)
        }
    }
}