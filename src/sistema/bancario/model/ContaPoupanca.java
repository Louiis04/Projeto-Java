package sistema.bancario.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ContaPoupanca implements IConta {
    private static final BigDecimal TARIFA_TRANSFERENCIA = new BigDecimal("0.02"); 

    private int numero;
    private BigDecimal saldo;
    private LocalDateTime dataCriacao;
    private boolean status;

    public ContaPoupanca(int numero) {
        this.numero = numero;
        this.saldo = BigDecimal.ZERO;
        this.setDataCriacao(LocalDateTime.now());
        this.status = true;
    }

    @Override
    public void depositar(BigDecimal valor) {
        if (status && valor.compareTo(BigDecimal.ZERO) > 0) {
            saldo = saldo.add(valor);
            System.out.println("Depósito de R$ " + valor + " realizado com sucesso!");
        } else {
            System.out.println("Depósito inválido ou conta inativa.");
        }
    }

    @Override
    public boolean sacar(BigDecimal valor) {
        if (status && valor.compareTo(BigDecimal.ZERO) > 0 && valor.compareTo(saldo) <= 0) {
            saldo = saldo.subtract(valor);
            System.out.println("Saque de R$ " + valor + " realizado com sucesso!");
            return true;
        }
        System.out.println("Saque inválido. Saldo insuficiente ou conta inativa.");
        return false;
    }

    @Override
    public boolean transferir(IConta destino, BigDecimal valor) {
        if (this.status && destino.isAtiva()) {
            if (valor.compareTo(BigDecimal.ZERO) > 0 && valor.compareTo(this.saldo) <= 0) {
                BigDecimal tarifa = BigDecimal.ZERO;

                if (this instanceof ContaPoupanca && destino instanceof ContaCorrente) {
                    tarifa = calcularTarifaTransferencia(valor);
                    System.out.println("Tarifa de R$ " + tarifa + " será aplicada.");
                }

                BigDecimal valorComTarifa = valor.add(tarifa);
                this.setSaldo(this.getSaldo().subtract(valorComTarifa));
                destino.setSaldo(destino.getSaldo().add(valor));
                System.out.println("Transferência de R$ " + valor + " realizada com sucesso!");
                return true;
            } else {
                System.out.println("Saldo insuficiente ou valor inválido para transferência.");
            }
        } else {
            System.out.println("Operação não permitida. Verifique o status da conta.");
        }
        return false;
    }

    @Override
    public BigDecimal getSaldo() {
        return saldo;
    }

    @Override
    public int getNumero() {
        return numero;
    }

    @Override
    public boolean isAtiva() {
        return status;
    }

    @Override
    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    @Override
    public void ativar() {
        this.status = true;
    }

    @Override
    public void desativar() {
        if (this.saldo.compareTo(BigDecimal.ZERO) == 0) {
            this.status = false;
            System.out.println("Conta desativada.");
        } else {
            System.out.println("Conta precisa estar zerada para ser desativada.");
        }
    }

    @Override
    public BigDecimal calcularTarifaTransferencia(BigDecimal valor) {
        return valor.multiply(TARIFA_TRANSFERENCIA);
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

	@Override
	public String toString() {
		return "ContaPoupanca [numero=" + numero + ", saldo=" + saldo + ", dataCriacao=" + dataCriacao + ", status="
				+ status + "]";
	}
    
    
}
