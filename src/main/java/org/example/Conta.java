package org.example;


import java.math.BigDecimal;

public class Conta{
    private final Pessoa titular;
    private final Integer numConta;
    private final BigDecimal saldo;


    public Conta(DadosConta dados, Pessoa titular) {

        this.titular = titular;
        this.numConta = dados.numConta();
        this.saldo = dados.saldo();
    }


    public Integer getNumConta() {
        return numConta;
    }


    public BigDecimal getSaldo() {
        return saldo;
    }


    @Override
    public String toString(){
        return "Número da Conta: "+numConta+", "+"Saldo: "+saldo+", "+ this.titular.toString();
    }
}
