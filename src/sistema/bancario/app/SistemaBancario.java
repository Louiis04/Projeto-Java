package sistema.bancario.app;

import java.util.ArrayList;
import java.util.Scanner;
import sistema.bancario.model.Cliente;
import sistema.bancario.model.Conta;
import sistema.bancario.persistencia.Persistencia;

public class SistemaBancario {
    private ArrayList<Cliente> clientes;

    public SistemaBancario() {
        this.clientes = Persistencia.carregarDados();
    }

    public void cadastrarCliente(String nome, String cpf) {
        if (!clientes.contains(new Cliente(cpf))) {
            clientes.add(new Cliente(nome, cpf));
            System.out.println("Cliente cadastrado com sucesso!");
        } else {
            System.out.println("Cliente já cadastrado.");
        }
    }

    public Cliente buscarCliente(String cpf) {
        int index = clientes.indexOf(new Cliente(cpf));
        return index != -1 ? clientes.get(index) : null;
    }

    public void listarClientes() {
        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
        } else {
            clientes.forEach(System.out::println);
        }
    }

    public void removerCliente(String cpf) {
        Cliente cliente = buscarCliente(cpf);
        if (cliente != null) {
            clientes.remove(cliente);  
            System.out.println("Cliente removido com sucesso!");
        } else {
            System.out.println("Cliente não encontrado.");
        }
    }

    public void menu() {
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n--- Sistema Bancário ---");
            System.out.println("1. Cadastrar cliente");
            System.out.println("2. Listar clientes");
            System.out.println("3. Gerenciar contas de cliente");
            System.out.println("4. Remover cliente");
            System.out.println("5. Salvar dados e sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    System.out.print("Nome: ");
                    String nome = scanner.nextLine();
                    System.out.print("CPF: ");
                    String cpf = scanner.nextLine();
                    cadastrarCliente(nome, cpf);
                    break;
                case 2:
                    listarClientes();
                    break;
                case 3:
                    System.out.print("CPF do cliente: ");
                    cpf = scanner.nextLine();
                    Cliente cliente = buscarCliente(cpf);
                    if (cliente != null) {
                        menuContas(cliente, scanner);
                    } else {
                        System.out.println("Cliente não encontrado.");
                    }
                    break;
                    case 4:
                    System.out.print("CPF do cliente a ser removido: ");
                    cpf = scanner.nextLine();
                    Cliente clienteParaRemover = buscarCliente(cpf);
                    if (clienteParaRemover != null) {
                        boolean temContaAtiva = false;
                        for (Conta conta : clienteParaRemover.getContas()) {
                            if (conta.isAtiva()) {
                                temContaAtiva = true;
                                break;
                            }
                        }
                
                        if (!temContaAtiva) {
                            removerCliente(cpf);
                            System.out.println("Cliente removido com sucesso!");
                        } else {
                            System.out.println("O cliente possui contas ativas e não pode ser removido.");
                        }
                    } else {
                        System.out.println("Cliente não encontrado.");
                    }
                    break;
                
                case 5:
                    Persistencia.salvarDados(clientes);
                    System.out.println("Saindo do sistema...");
                    break;
                default:
                    System.out.println("Opção inválida.");
                    break;
            }
        } while (opcao != 5);

        scanner.close();
    }

    private void menuContas(Cliente cliente, Scanner scanner) {
        int opcao;

        do {
            System.out.println("\n--- Gerenciamento de Contas ---");
            System.out.println("1. Adicionar conta");
            System.out.println("2. Listar contas");
            System.out.println("3. Realizar depósito");
            System.out.println("4. Realizar saque");
            System.out.println("5. Transferir entre contas");
            System.out.println("6. Ver saldo da conta");
            System.out.println("7. Remover conta");
            System.out.println("8. Ativar conta");
            System.out.println("9. Desativar conta");
            System.out.println("10. Ver balanço do cliente");
            System.out.println("11. Voltar");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
            case 1:
                System.out.print("Número da conta: ");
                int numeroConta = scanner.nextInt();
                scanner.nextLine();
                cliente.adicionarConta(new Conta(numeroConta), clientes); // 'clientes' é a lista de todos os clientes
                break;
            case 2:
                cliente.listarContas();
                break;
            case 3:
                System.out.print("Número da conta para depósito: ");
                numeroConta = scanner.nextInt();
                scanner.nextLine();
                Conta contaDeposito = cliente.buscarConta(numeroConta);
                if (contaDeposito != null) {
                    System.out.print("Valor para depósito: ");
                    float valorDeposito = scanner.nextFloat();
                    scanner.nextLine();
                    contaDeposito.depositar(valorDeposito);
                } else {
                    System.out.println("Conta não encontrada.");
                }
                break;
            case 4:
                System.out.print("Número da conta para saque: ");
                numeroConta = scanner.nextInt();
                scanner.nextLine();
                Conta contaSaque = cliente.buscarConta(numeroConta);
                if (contaSaque != null) {
                    System.out.print("Valor para saque: ");
                    float valorSaque = scanner.nextFloat();
                    scanner.nextLine();
                    contaSaque.sacar(valorSaque);
                } else {
                    System.out.println("Conta não encontrada.");
                }
                break;
            case 5:
                System.out.print("Número da conta de origem: ");
                int numeroContaOrigem = scanner.nextInt();
                scanner.nextLine();
                Conta contaOrigem = cliente.buscarConta(numeroContaOrigem);
                if (contaOrigem != null) {
                    System.out.print("Número da conta de destino: ");
                    int numeroContaDestino = scanner.nextInt();
                    scanner.nextLine();

                    Conta contaDestino = null;
                    Cliente clienteDestino = null;

                    for (Cliente c : clientes) { 
                        contaDestino = c.buscarConta(numeroContaDestino);
                        if (contaDestino != null) {
                            clienteDestino = c;
                            break;
                        }
                    }

                    if (contaDestino != null) {
                        System.out.println("Cliente de destino: " + clienteDestino.getNome());
                        System.out.print("Valor para transferência: ");
                        float valorTransferencia = scanner.nextFloat();
                        scanner.nextLine();
                        contaOrigem.transferir(contaDestino, valorTransferencia);
                    } else {
                        System.out.println("Conta de destino não encontrada.");
                    }
                } else {
                    System.out.println("Conta de origem não encontrada.");
                }
                break;
                case 6:
                    System.out.print("Número da conta para ver o saldo: ");
                    numeroConta = scanner.nextInt();
                    scanner.nextLine();
                    Conta contaSaldo = cliente.buscarConta(numeroConta);
                    if (contaSaldo != null) {
                        System.out.println("Saldo da conta: " + contaSaldo.getSaldo());
                    } else {
                        System.out.println("Conta não encontrada.");
                    }
                    break;
                    case 7:
                    System.out.print("Número da conta para remover: ");
                    numeroConta = scanner.nextInt();
                    scanner.nextLine();
                    Conta contaParaRemover = cliente.buscarConta(numeroConta);
                    if (contaParaRemover != null) {
                        if (!contaParaRemover.isAtiva() || contaParaRemover.getSaldo() == 0) {
                            cliente.removerConta(numeroConta);
                            System.out.println("Conta removida com sucesso!");
                        } else {
                            System.out.println("A conta está ativa e possui saldo, não pode ser removida.");
                        }
                    } else {
                        System.out.println("Conta não encontrada.");
                    }
                    break;
                
                case 8:
                    System.out.print("Número da conta para ativar: ");
                    numeroConta = scanner.nextInt();
                    scanner.nextLine();
                    Conta contaAtivar = cliente.buscarConta(numeroConta);
                    if (contaAtivar != null) {
                        contaAtivar.ativar();
                    } else {
                        System.out.println("Conta não encontrada.");
                    }
                    break;
                case 9:
                    System.out.print("Número da conta para desativar: ");
                    numeroConta = scanner.nextInt();
                    scanner.nextLine();
                    Conta contaDesativar = cliente.buscarConta(numeroConta);
                    if (contaDesativar != null) {
                        contaDesativar.desativar();
                    } else {
                        System.out.println("Conta não encontrada.");
                    }
                    break;
                case 10:
                    float saldoCliente = 0;
                    for (Conta conta : cliente.getContas()) {
                        if (conta.isAtiva()) {
                            saldoCliente += conta.getSaldo();
                        }
                    }
                    System.out.println("Balanço do cliente: " + saldoCliente);
                    break;
                case 11:
                    System.out.println("Voltando ao menu principal...");
                    break;
                default:
                    System.out.println("Opção inválida.");
                    break;
            }
        } while (opcao != 11);
    }

    public static void main(String[] args) {
        SistemaBancario sistema = new SistemaBancario();
        sistema.menu();
    }
}
