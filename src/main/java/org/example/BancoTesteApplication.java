package org.example;


import java.math.BigDecimal;
import java.util.InputMismatchException;
import java.util.Scanner;


public class BancoTesteApplication {

    private final Scanner entrada = new Scanner(System.in);
    private final ContaService service = new ContaService();
    private boolean controlador = true;

    void menu() {
        while (controlador) {

            try {
                System.out.println("Banco Teste");
                System.out.println("Selecione a Opção desejada:");
                System.out.println("""
                        1 - Abrir Conta
                        2 - Listar Contas Abertas
                        3 - Depositar valor
                        4 - Sacar valor
                        5 - Consultar Saldo
                        6 - Realizar Transferencia
                        7 - Encerrar Conta
                        8 - Sair

                        --->\s""");
                int opcao = entrada.nextInt();
                switch (opcao) {
                    case 1 ->
                        abrirConta();

                    case 2 ->
                        listarContas();

                    case 3 ->
                        depositarValor();

                    case 4 ->
                        sacarValor();

                    case 5 ->
                        consultarSaldo();

                    case 6 ->
                        realizarTransferencia();

                    case 7 ->
                        encerrarConta();

                    case 8 ->
                        controlador = false;
                    default -> {
                        System.out.println("Digite apenas os números de 1 a 6\n" +
                                "Digite uma Tecla Qualquer e Aperte ENTER");
                        entrada.next();
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println("Caractere Inválido\n");
                entrada.nextLine();
            }
        }




    }

    private void abrirConta(){
        try {
            System.out.println("Digite o Nome do Titular: ");
            String nome = entrada.next();

            System.out.println("CPF: ");
            String cpf = entrada.next();

            System.out.println("Email: ");
            String email = entrada.next();

            System.out.println("Número da Conta: ");
            Integer numeroConta = entrada.nextInt();

            System.out.println("Depósito Inicial: ");
            BigDecimal depositoInicial = entrada.nextBigDecimal();

            service.abrir(new DadosConta(numeroConta, depositoInicial, new DadosCliente(nome, cpf, email)));
        }catch (InputMismatchException e){
            System.out.println("Caractere Inválido");
        }catch (RuntimeException e){
            throw new RuntimeException(e);
        }
    }

    private void listarContas(){
        System.out.println("Contas Cadastradas: ");
        var contas = service.listarContas();
        contas.forEach(System.out::println);

        System.out.println("Pressione Qualquer tecla e dê ENTER para voltar ao menu principal");
        entrada.next();
    }

    private void depositarValor(){
        try {
            System.out.println("Qual o número da conta: ");
            int numConta = entrada.nextInt();

            System.out.println("Qual o valor de Depósito: ");
            BigDecimal valor = entrada.nextBigDecimal();

            service.realizarDeposito(numConta, valor);
            System.out.println("""
                                Valor depositado com Sucesso
                                Tecle ENTER
                                \s""");
            entrada.next();
        }catch (InputMismatchException e){
            throw new RuntimeException(e);
        }
    }

    private void sacarValor(){
        try {
            System.out.println("Qual o número da conta: ");
            int numConta = entrada.nextInt();

            System.out.println("Qual o valor de Saque: ");
            BigDecimal valor = entrada.nextBigDecimal();

            service.realizarSaque(numConta, valor);
            System.out.println("""
                                Valor Sacado com Sucesso
                                Tecle ENTER
                                \s""");
            entrada.next();
        }catch (InputMismatchException e){
            throw new RuntimeException(e);
        }
    }

    private void consultarSaldo(){
        System.out.println("Consultar saldo");
    }

    private void realizarTransferencia(){
        System.out.println("Qual o número da conta de Origem: ");
        int numContaOrigem = entrada.nextInt();

        System.out.println("Qual o número da conta de Destino: ");
        int numContaDestino = entrada.nextInt();

        System.out.println("Qual o valor de Saque: ");
        BigDecimal valor = entrada.nextBigDecimal();

        service.realizarTransferencia(numContaOrigem,numContaDestino,valor);
        System.out.println("""
                                Valor Transferido com Sucesso
                                Tecle ENTER
                                \s""");
        entrada.next();
    }

    private void encerrarConta(){
        try {
            System.out.println("Qual o número da conta: ");
            int numConta = entrada.nextInt();

            service.encerrarConta(numConta);

            System.out.println("""
                                Conta Encerrada com Sucesso
                                Tecle ENTER
                                \s""");
            entrada.next();

        }catch (InputMismatchException e){
            throw new RuntimeException();
        }
    }
}
