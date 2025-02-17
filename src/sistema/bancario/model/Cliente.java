package sistema.bancario.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Objects;

public class Cliente implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nome;
    private String cpf;
    private ArrayList<IConta> contas;

    public Cliente(String nome, String cpf) {
        this.nome = nome;
        this.cpf = cpf;
        this.contas = new ArrayList<>();
    }

    public Cliente(String cpf) {
        this.cpf = cpf;
        this.contas = new ArrayList<>();
    }

    public void adicionarConta(IConta conta, ArrayList<Cliente> clientes) {
        if (numeroContaExiste(clientes, conta.getNumero())) {
            System.out.println("Número da conta já existe. Não é possível criar a conta.");
        } else {
            contas.add(conta);
            System.out.println("Conta adicionada com sucesso!");
        }
    }

    public void listarContas() {
        if (contas.isEmpty()) {
            System.out.println("Nenhuma conta cadastrada para este cliente.");
        } else {
            contas.forEach(System.out::println);
        }
    }

    public IConta buscarConta(int numero) {
        for (IConta conta : contas) {
            if (conta.getNumero() == numero) {
                return conta;
            }
        }
        return null;
    }

    public void removerConta(int numero) {
        IConta conta = buscarConta(numero);
        if (conta != null) {
            if (conta.getSaldo().compareTo(BigDecimal.ZERO) == 0) {
                contas.remove(conta);
                System.out.println("Conta removida com sucesso!");
            } else {
                System.out.println("Conta com saldo, remova o saldo para finalizar a conta!");
            }
        } else {
            System.out.println("Conta não encontrada.");
        }
    }

    public ArrayList<IConta> getContas() {
        return contas;
    }

    public String getCpf() {
        return cpf;
    }

    public String getNome() {
        return nome;
    }

    public static boolean numeroContaExiste(ArrayList<Cliente> clientes, int numeroConta) {
        for (Cliente cliente : clientes) {
            for (IConta conta : cliente.getContas()) {
                if (conta.getNumero() == numeroConta) {
                    return true;
                }
            }
        }
        return false;
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
