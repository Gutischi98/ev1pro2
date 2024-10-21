package cl.guti.android.appev1

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.NumberFormat
import java.util.*
import model.ItemMenu
import model.ItemMesa
import model.CuentaMesa

class MainActivity : AppCompatActivity() {

    private lateinit var cuentaMesa: CuentaMesa
    private lateinit var etCantidadCazuela: EditText
    private lateinit var etCantidadChoclo: EditText
    private lateinit var tvTotalCazuela: TextView
    private lateinit var tvTotalChoclo: TextView
    private lateinit var tvSubtotal: TextView
    private lateinit var tvPropina: TextView
    private lateinit var tvTotal: TextView
    private lateinit var switchPropina: Switch

    private val formatoMoneda = NumberFormat.getCurrencyInstance(Locale("es", "CL"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cuentaMesa = CuentaMesa()

        etCantidadCazuela = findViewById(R.id.cantCazuela)
        etCantidadChoclo = findViewById(R.id.cantChoclo)
        tvTotalCazuela = findViewById(R.id.totalCazuela)
        tvTotalChoclo = findViewById(R.id.totalChoclo)
        tvSubtotal = findViewById(R.id.subtotal)
        tvPropina = findViewById(R.id.tvPropina)
        tvTotal = findViewById(R.id.Total)
        switchPropina = findViewById(R.id.switchPropina)

        val cazuela = ItemMenu("Cazuela", 10000)
        val pastelDeChoclo = ItemMenu("Pastel de Choclo", 12000)

        etCantidadCazuela.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                actualizarMontos(cazuela, pastelDeChoclo)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        etCantidadChoclo.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                actualizarMontos(cazuela, pastelDeChoclo)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        switchPropina.setOnCheckedChangeListener { _, isChecked ->
            cuentaMesa.aceptaPropina = isChecked
            actualizarMontos(cazuela, pastelDeChoclo)
        }
    }

    private fun actualizarMontos(cazuela: ItemMenu, choclo: ItemMenu) {
        val cantidadCazuela = etCantidadCazuela.text.toString().toIntOrNull() ?: 0
        val cantidadChoclo = etCantidadChoclo.text.toString().toIntOrNull() ?: 0

        cuentaMesa.agregarOActualizarItem(ItemMesa(cazuela, cantidadCazuela))
        cuentaMesa.agregarOActualizarItem(ItemMesa(choclo, cantidadChoclo))

        tvTotalCazuela.text = formatoMoneda.format(cantidadCazuela * cazuela.precio)
        tvTotalChoclo.text = formatoMoneda.format(cantidadChoclo * choclo.precio)
        tvSubtotal.text = formatoMoneda.format(cuentaMesa.calcularTotalSinPropina())
        tvPropina.text = formatoMoneda.format(cuentaMesa.calcularPropina())
        tvTotal.text = formatoMoneda.format(cuentaMesa.calcularTotalConPropina())
    }
}