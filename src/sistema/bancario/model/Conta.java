package sistema.bancario.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Conta implements Serializable {
    private int numero;
    private float saldo;
    private LocalDateTime dataCriacao;
    private boolean status;

    public Conta(int numero) {
        this.numero = numero;
        this.saldo = 0.0f;
        this.dataCriacao = LocalDateTime.now();
        this.status = true;
    }

    public void depositar(float valor) {
        if (status) {
            if (valor > 0) {
                saldo += valor;
                System.out.println("Depósito de " + valor + " realizado com sucesso!");
            } else {
                System.out.println("Valor inválido para depósito.");
            }
        } else {
            System.out.println("A conta está inativa. Operação não permitida.");
        }
    }

    public boolean sacar(float valor) {
        if (status) {
            if (valor > 0 && valor <= saldo) {
                saldo -= valor;
                System.out.println("Saque de " + valor + " realizado com sucesso!");
                return true;
            } else {
                System.out.println("Saldo insuficiente ou valor inválido para saque.");
            }
        } else {
            System.out.println("A conta está inativa. Operação não permitida.");
        }
        return false;
    }

    public boolean transferir(Conta destino, float valor) {
        if (this.status && destino.isAtiva()) {
            if (valor > 0 && valor <= this.saldo) {
                this.saldo -= valor;
                destino.saldo += valor;
                System.out.println("Transferência de " + valor + " realizada com sucesso!");
                return true;
            } else {
                System.out.println("Saldo insuficiente ou valor inválido para transferência.");
            }
        } else {
            System.out.println("Operação não permitida. Verifique o status da conta.");
        }
        return false;
    }
    

    public void ativar() {
        this.status = true;
        System.out.println("Conta ativada com sucesso.");
    }

    public void desativar() {
        if (this.saldo == 0) {
            this.status = false;
            System.out.println("Conta desativada com sucesso.");
        } else {
            System.out.println("Não é possível desativar a conta com saldo diferente de zero.");
        }
    }

    public int getNumero() {
        return numero;
    }

    public float getSaldo() {
        return saldo;
    }

    public boolean isAtiva() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
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
