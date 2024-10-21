package model

class CuentaMesa {
    private val items: MutableList<ItemMesa> = mutableListOf()
    var aceptaPropina: Boolean = true

    fun agregarOActualizarItem(itemMesa: ItemMesa) {
        val itemExistente = items.find { it.itemMenu.nombre == itemMesa.itemMenu.nombre }
        if (itemExistente != null) {
            itemExistente.cantidad = itemMesa.cantidad
        } else {
            items.add(itemMesa)
        }
    }

    fun calcularTotalSinPropina(): Int {
        return items.sumOf { it.calcularSubtotal() }
    }

    fun calcularPropina(): Int {
        return if (aceptaPropina) (calcularTotalSinPropina() * 0.1).toInt() else 0
    }

    fun calcularTotalConPropina(): Int {
        return calcularTotalSinPropina() + calcularPropina()
    }
}