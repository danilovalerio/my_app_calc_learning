package projetos.danilo.myappcalc

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import net.objecthunter.exp4j.ExpressionBuilder
import projetos.danilo.myappcalc.constants.*
import projetos.danilo.myappcalc.model.CalcScreen

class MainViewModel : ViewModel() {
    private val screen = CalcScreen()
    private var pressEqual = false
    private var hasError = false

    private val calcVisor: MutableLiveData<CalcScreen> by lazy {
        MutableLiveData<CalcScreen>()
    }

    fun getNumbers(): LiveData<CalcScreen> {
        return calcVisor
    }

    fun setClick(c: Any?) {
        if (hasError) {
            if (c == AC) processClick(AC)
        } else processClick(c.toString())
    }

    // Processa informação do click
    private fun processClick(c: String) {
        when (c) {
            AC -> resetCalc()
            BACK -> {
                if (screen.number.length > 1) {
                    screen.number = screen.number.substring(0, screen.number.length - 1)
                } else {
                    screen.number = NUM0
                }
            }
            POINT -> {
                if (!screen.number.contains(POINT)) {
                    if (screen.number == "") screen.number = "$NUM0." else screen.number += "$c"
                }
            }
            SIGNAL -> {
                screen.number =
                    if (screen.number.isNotEmpty() && screen.number.first() == MINUS.first()) {
                        screen.number.substring(1, screen.number.length)
                    } else {
                        "$MINUS${screen.number}"
                    }
            }
            PLUS -> setCalc(PLUS)
            MINUS -> setCalc(MINUS)
            MULTIPLY -> setCalc(MULTIPLY)
            DIVIDE -> setCalc(DIVIDE)
            EQUAL -> {
                screen.calc += screen.number
                makeCalc(screen.calc)
            }
            else -> {
                try {
                    c.toInt()
                    when(screen.number){
                        NUM0 -> screen.number = ""
                        "$MINUS$NUM0" -> screen.number = MINUS
                    }
                    screen.number += c
                } catch (e: Exception) {
                    hasError = true
                    screen.number = ERROR
                }
            }
        }
        calcVisor.value = screen
    }

    // Função AC da calculadora
    private fun resetCalc() {
        hasError = false
        pressEqual = false
        screen.calc = ""
        screen.number = NUM0
    }

    // Adiciona o cálculo a ser feito no visor.calc
    private fun setCalc(str: String) {
        if (pressEqual) {
            screen.calc = ""
            pressEqual = false
        }
        screen.calc += "${screen.number}${str}"
        screen.number = NUM0
    }

    // Mostra o resultado ao apertar o OK " = "
    private fun makeCalc(str: String) {
        var result: String
        try {
            val replaceStr = str.replace(PLUS, "+")
                .replace(MINUS, "-")
                .replace(DIVIDE, "/")
                .replace(MULTIPLY, "*")
            val calc = ExpressionBuilder(replaceStr).build()
            result = calc.evaluate().toString()
            screen.calc += EQUAL
        } catch (e: Exception) {
            hasError = true
            result = ERROR
        }

        screen.number =
            if (result.substring(
                    result.length - 2,
                    result.length
                ) == "$POINT$NUM0"
            ) result.substring(
                0,
                result.length - 2
            ) else result
        pressEqual = true
    }


}