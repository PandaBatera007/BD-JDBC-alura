package org.example;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Set;

public class ContaService {

    private final ConnectionFactory connection = new ConnectionFactory();



    public Set<Conta> listarContas() {
        Connection conn = connection.recuperarConexao();
        return new ContaDAO(conn).listar();
    }

    public void abrir(DadosConta dados){
        Connection conn = connection.recuperarConexao();
        new ContaDAO(conn).salvar(dados);
    }

    private Conta buscaContaPorNumero(Integer numConta){
        Connection conn = connection.recuperarConexao();
        Conta conta = new ContaDAO(conn).listarPorNumero(numConta);
        if(conta != null){
            return conta;
        }else {
            throw new RuntimeException();
        }
    }

    public void realizarDeposito(Integer numConta, BigDecimal valor){
        var conta = buscaContaPorNumero(numConta);
        if(valor.compareTo(BigDecimal.ZERO) <= 0){
            throw new RuntimeException("Valor menor que 1");
        }

        BigDecimal novoValor = conta.getSaldo().add(valor);
        Connection conn = connection.recuperarConexao();
        new ContaDAO(conn).alterar(conta.getNumConta(),novoValor);
    }

    public void realizarSaque(Integer numConta, BigDecimal valor){
        var conta = buscaContaPorNumero(numConta);

        if(valor.compareTo(BigDecimal.ZERO) <= 0){
            throw new RuntimeException("Valor precisa ser maior que zero");
        }

        if(valor.compareTo(conta.getSaldo()) > 0){
            throw new RuntimeException("Saldo Insuficiente");
        }

        BigDecimal valorSubtraido = conta.getSaldo().subtract(valor);
        Connection conn = connection.recuperarConexao();
        new ContaDAO(conn).alterar(conta.getNumConta(),valorSubtraido);
    }

    public void realizarTransferencia(Integer numContaOrigem, Integer numContaDestino, BigDecimal valor){
        realizarSaque(numContaOrigem,valor);
        realizarDeposito(numContaDestino,valor);
    }

    public void encerrarConta(Integer numConta){
        var conta = buscaContaPorNumero(numConta);
        if (conta.getSaldo().compareTo(BigDecimal.ZERO) > 0){
            System.out.println("ERR0 = Para encerrar a Conta o saldo precisa ser zero");
            throw new RuntimeException();
        }
        Connection conn = connection.recuperarConexao();
        new ContaDAO(conn).deletar(numConta);
    }

}
