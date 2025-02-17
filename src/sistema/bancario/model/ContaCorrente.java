package sistema.bancario.model;

import java.math.BigDecimal;

public class ContaCorrente extends Conta {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final BigDecimal TARIFA_TRANSFERENCIA = new BigDecimal("0.02");

    public ContaCorrente(int numero) {
        super(numero);
    }

    @Override
    public BigDecimal calcularTarifaTransferencia(BigDecimal valor) {
        return valor.multiply(TARIFA_TRANSFERENCIA);
    }

    @Override
    public String toString() {
        return "ContaCorrente" + super.toString();
    }

}
