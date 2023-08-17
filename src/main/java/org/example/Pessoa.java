package org.example;

public class Pessoa {
    private final String nome;
    private final String email;
    private final String cpf;

    public Pessoa(DadosCliente dados){
        this.nome = dados.Nome();
        this.email = dados.email();
        this.cpf = dados.cpf();
    }

    public String getPrimeiroNome() {
        return nome;
    }
    public String getemail() {
        return email;
    }
    public String getCpf() {
        return cpf;
    }

    @Override
    public String toString(){
        return "Nome: "+nome+", "+"CPF: "+ cpf+", "+"Email: "+email;
    }


}
