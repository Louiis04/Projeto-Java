package sistema.bancario.persistencia;

import java.io.*;
import java.util.ArrayList;
import sistema.bancario.model.Cliente;

public class Persistencia {
    private static final String FILE_NAME = "dados_bancarios.dat";  

    public static void salvarDados(ArrayList<Cliente> clientes) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(clientes);  
            System.out.println("Dados salvos com sucesso!");
        } catch (IOException e) {
            System.out.println("Erro ao salvar os dados: " + e.getMessage());
        }
    }

    public static ArrayList<Cliente> carregarDados() {
        ArrayList<Cliente> clientes = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            clientes = (ArrayList<Cliente>) ois.readObject(); 
            System.out.println("Dados carregados com sucesso!");
        } catch (FileNotFoundException e) {
            System.out.println("Nenhum dado encontrado. Um novo arquivo será criado ao salvar.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao carregar os dados: " + e.getMessage());
        }
        return clientes;
    }
}
