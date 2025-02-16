package sistema.bancario.model;

import java.math.BigDecimal;

public interface IConta {
    
    void depositar(BigDecimal valor);
    
    boolean sacar(BigDecimal valor);
    
    boolean transferir(IConta destino, BigDecimal valor);
    
    BigDecimal getSaldo();
    
    int getNumero();
    
    boolean isAtiva(); 
    
    void setSaldo(BigDecimal saldo); 
    
    void ativar();
    
    void desativar();

    BigDecimal calcularTarifaTransferencia(BigDecimal valor);
}
