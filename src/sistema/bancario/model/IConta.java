package sistema.bancario.model;

import java.math.BigDecimal;

import exceptions.ContaInexistenteException;
import exceptions.SaldoInsuficienteException;
import exceptions.StatusContaException;
import exceptions.ValorInvalidoException;

public interface IConta {
    
    void depositar(BigDecimal valor) throws ContaInexistenteException, ValorInvalidoException;
    
    void sacar(BigDecimal valor) throws ContaInexistenteException, SaldoInsuficienteException, ValorInvalidoException;
    
    void transferir(IConta destino, BigDecimal valor) throws ContaInexistenteException, SaldoInsuficienteException, ValorInvalidoException;
    
    BigDecimal getSaldo();
    
    int getNumero();
    
    boolean isAtiva(); 
    
    void setSaldo(BigDecimal saldo); 
    
    void ativar();
    
    void desativar() throws StatusContaException;

    BigDecimal calcularTarifaTransferencia(BigDecimal valor);
}
