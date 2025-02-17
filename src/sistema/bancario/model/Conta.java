package sistema.bancario.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import exceptions.ContaInexistenteException;
import exceptions.SaldoInsuficienteException;
import exceptions.StatusContaException;
import exceptions.ValorInvalidoException;

public abstract class Conta implements IConta, Serializable {
    private static final long serialVersionUID = 1L;

    private int numero;
    private BigDecimal saldo;
    private LocalDateTime dataCriacao;
    private boolean status;
    private List<Transacao> historico;

    public Conta(int numero) {
        this.numero = numero;
        this.saldo = BigDecimal.ZERO;
        this.dataCriacao = LocalDateTime.now();
        this.status = true;
        this.historico = new ArrayList<>();
    }

    public void depositar(BigDecimal valor) throws ContaInexistenteException, ValorInvalidoException {
        if (!status) {
            throw new ContaInexistenteException("Erro: Não é possível depositar em uma conta inativa.");
        }
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValorInvalidoException("Erro: O valor do depósito deve ser maior que zero.");
        }

        saldo = saldo.add(valor);
        historico.add(new Transacao("Depósito", valor));
        System.out.println("Depósito de R$ " + valor + " realizado com sucesso!");
    }

    public void sacar(BigDecimal valor) throws ContaInexistenteException, SaldoInsuficienteException, ValorInvalidoException {
        if (!status) {
            throw new ContaInexistenteException("Erro: Não é possível sacar de uma conta inativa.");
        }
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValorInvalidoException("Erro: O valor do saque deve ser maior que zero.");
        }
        if (valor.compareTo(saldo) > 0) {
            throw new SaldoInsuficienteException("Erro: Saldo insuficiente para saque.");
        }

        saldo = saldo.subtract(valor);
        historico.add(new Transacao("Saque", valor));
        System.out.println("Saque de R$ " + valor + " realizado com sucesso!");
    }

    public void transferir(IConta destino, BigDecimal valor) throws ContaInexistenteException, SaldoInsuficienteException, ValorInvalidoException {
        if (!this.status) {
            throw new ContaInexistenteException("Erro: Não é possível transferir de uma conta inativa.");
        }
        if (!destino.isAtiva()) {
            throw new ContaInexistenteException("Erro: Não é possível transferir para uma conta inativa.");
        }
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValorInvalidoException("Erro: O valor da transferência deve ser maior que zero.");
        }
        if (valor.compareTo(this.saldo) > 0) {
            throw new SaldoInsuficienteException("Erro: Saldo insuficiente para transferência.");
        }

        BigDecimal tarifa = calcularTarifaTransferencia(valor);
        BigDecimal valorComTarifa = valor.add(tarifa);
        this.setSaldo(this.getSaldo().subtract(valorComTarifa));
        destino.setSaldo(destino.getSaldo().add(valor));

        historico.add(new Transacao("Transferência enviada", valor, this.numero, destino.getNumero(), tarifa));
        destino.getHistorico().add(new Transacao("Transferência recebida", valor, this.numero, destino.getNumero(), tarifa));

        System.out.println("Transferência de R$ " + valor + " realizada com sucesso! (Tarifa: R$ " + tarifa + ")");
    }
    
    public void extratoPorMesAno(int mes, int ano) {
        System.out.println("Extrato da Conta Corrente " + numero + " para " + mes + "/" + ano + ":");
        boolean encontrou = false;

        for (Transacao transacao : historico) {
            if (transacao.getDataHora().getMonthValue() == mes && transacao.getDataHora().getYear() == ano) {
                System.out.println(transacao);
                encontrou = true;
            }
        }

        if (!encontrou) {
            System.out.println("Nenhuma transação encontrada para esse período.");
        }
    }

    public void ativar() {
        this.status = true;
    }

    public void desativar() throws StatusContaException {
        if (this.saldo.compareTo(BigDecimal.ZERO) == 0) {
            this.status = false;
            System.out.println("Conta desativada com sucesso.");
        } else {
            throw new StatusContaException("Erro: A conta precisa estar zerada para ser desativada.");
        }
    }

    public BigDecimal calcularTarifaTransferencia(BigDecimal valor) {
        return BigDecimal.ZERO;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public List<Transacao> getHistorico() {
        return historico;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public int getNumero() {
        return numero;
    }

    public boolean isAtiva() {
        return status;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    @Override
    public String toString() {
        return "Conta{" +
                "numero=" + numero +
                ", saldo=" + saldo +
                ", dataCriacao=" + dataCriacao +
                ", status=" + (status ? "Ativa" : "Inativa") +
                '}';
    }
}
