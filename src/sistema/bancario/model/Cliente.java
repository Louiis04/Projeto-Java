package sistema.bancario.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class Cliente implements Serializable {
    private String nome;
    private String cpf;
    private ArrayList<Conta> contas;

    public Cliente(String nome, String cpf) {
        this.nome = nome;
        this.cpf = cpf;
        this.contas = new ArrayList<>();
    }

    public Cliente(String cpf) {
        this.cpf = cpf;
        this.contas = new ArrayList<>();
    }

    public void adicionarConta(Conta conta) {
        contas.add(conta);
    }

    public void listarContas() {
        if (contas.isEmpty()) {
            System.out.println("Nenhuma conta cadastrada para este cliente.");
        } else {
            contas.forEach(System.out::println);
        }
    }

    public Conta buscarConta(int numero) {
        for (Conta conta : contas) {
            if (conta.getNumero() == numero) {
                return conta; 
            }
        }
        return null; 
    }

    public void removerConta(int numero) {
        Conta conta = buscarConta(numero);
        if (conta != null) {
            if (conta.getSaldo() == 0) {
                contas.remove(conta);
                System.out.println("Conta removida com sucesso!");
            } else {
                System.out.println("Conta com saldo, remova o saldo para finalizar a conta!");
            }
        } else {
            System.out.println("Conta não encontrada.");
        }
    }

    public ArrayList<Conta> getContas() {
        return contas;
    }

    public String getCpf() {
        return cpf;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Cliente)) return false;
        Cliente cliente = (Cliente) obj;
        return Objects.equals(cpf, cliente.cpf);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cpf);
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "nome='" + nome + '\'' +
                ", cpf='" + cpf + '\'' +
                ", contas=" + contas.size() +
                '}';
    }
}
