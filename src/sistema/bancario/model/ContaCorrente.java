package sistema.bancario.model;

import java.math.BigDecimal;

import exceptions.ContaInexistenteException;
import exceptions.SaldoInsuficienteException;
import exceptions.ValorInvalidoException;

public class ContaCorrente extends Conta {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ContaCorrente(int numero) {
        super(numero);
    }

    @Override
    public void transferir(IConta destino, BigDecimal valor) throws ContaInexistenteException, SaldoInsuficienteException, ValorInvalidoException {
        if (!this.isAtiva()) {
            throw new ContaInexistenteException("Erro: Não é possível transferir de uma conta inativa.");
        }
        if (!destino.isAtiva()) {
            throw new ContaInexistenteException("Erro: Não é possível transferir para uma conta inativa.");
        }
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValorInvalidoException("Erro: O valor da transferência deve ser maior que zero.");
        }
        if (valor.compareTo(this.getSaldo()) > 0) {
            throw new SaldoInsuficienteException("Erro: Saldo insuficiente para transferência.");
        }

        BigDecimal tarifa = calcularTarifaTransferencia(valor);
        BigDecimal valorComTarifa = valor.add(tarifa);
        
        this.setSaldo(this.getSaldo().subtract(valorComTarifa));
        destino.setSaldo(destino.getSaldo().add(valor));

        getHistorico().add(new Transacao("Transferência enviada", valor, this.getNumero(), destino.getNumero(), tarifa));
        destino.getHistorico().add(new Transacao("Transferência recebida", valor, this.getNumero(), destino.getNumero(), tarifa));

        System.out.println("Transferência de R$ " + valor + " realizada com sucesso! (Tarifa: R$ " + tarifa + ")");
    }

    public BigDecimal calcularTarifaTransferencia(BigDecimal valor) {
        return valor.multiply(new BigDecimal("0.02")); 
    }
    
    
}