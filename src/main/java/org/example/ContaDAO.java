package org.example;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class ContaDAO {
   private final Connection conn;

   ContaDAO(Connection connection){
        this.conn = connection;
   }

    public void salvar(DadosConta dados) {

        var cliente = new Pessoa(dados.dadosCliente());
        var conta = new Conta(dados,cliente);

       String sql = "INSERT INTO conta (numero, saldo, cliente_nome, cliente_cpf, cliente_email)" +
                "VALUES (?, ?, ?, ?, ?)";

       try{
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setInt(1, conta.getNumConta());
            preparedStatement.setBigDecimal(2, conta.getSaldo());
            preparedStatement.setString(3,dados.dadosCliente().Nome());
            preparedStatement.setString(4, dados.dadosCliente().cpf());
            preparedStatement.setString(5, dados.dadosCliente().email());

            preparedStatement.execute();
            preparedStatement.close();
            conn.close();

       } catch (SQLException e) {
            throw new RuntimeException(e);
       }
    }

    public Set<Conta> listar(){
       PreparedStatement ps;
       ResultSet resultSet;
       Set<Conta> contas = new HashSet<>();

       String sql = "SELECT * FROM conta";
       try{
           ps = conn.prepareStatement(sql);
           resultSet = ps.executeQuery();

           while (resultSet.next()){
               Integer numConta = resultSet.getInt(1);
               BigDecimal saldo = resultSet.getBigDecimal(2);
               String nome = resultSet.getString(3);
               String cpf = resultSet.getString(4);
               String email = resultSet.getString(5);

               DadosCliente dadosCliente = new DadosCliente(nome, cpf, email);
               Pessoa cliente = new Pessoa(dadosCliente);
               contas.add(new Conta(new DadosConta(numConta,saldo,dadosCliente),cliente));

           }
           ps.close();
           resultSet.close();
           conn.close();
       }catch (SQLException e){
           throw new RuntimeException(e);
       }
       return contas;
    }

    public Conta listarPorNumero(Integer numConta){

       String sql = "SELECT * FROM conta WHERE numero = ?";

       PreparedStatement ps;
       ResultSet rs;
       Conta conta = null;

       try{
           ps = conn.prepareStatement(sql);
           ps.setInt(1, numConta);
           rs = ps.executeQuery();


           while(rs.next()) {

               Integer numero = rs.getInt(1);
               BigDecimal saldo = rs.getBigDecimal(2);
               String nome = rs.getString(3);
               String cpf = rs.getString(4);
               String email = rs.getString(5);

               DadosCliente dadosCliente = new DadosCliente(nome, cpf, email);
               Pessoa cliente = new Pessoa(dadosCliente);

               conta = new Conta(new DadosConta(numero, saldo, new DadosCliente(nome, cpf, email)), cliente);
           }
           rs.close();
           ps.close();
           conn.close();


       }catch (SQLException e ){
           throw new RuntimeException(e);
       }
       return conta;
    }

    public void alterar(Integer numConta, BigDecimal valor){
       PreparedStatement ps;
       String sql = "UPDATE conta SET saldo = ? WHERE numero = ?";

       try{
           conn.setAutoCommit(false);

           ps = conn.prepareStatement(sql);

           ps.setBigDecimal(1,valor);
           ps.setInt(2,numConta);

           ps.execute();
           conn.commit();
           ps.close();
           conn.close();
       }catch (SQLException e){
           try {
               conn.rollback();
           }catch (SQLException ex) {
               throw new RuntimeException(ex);
           }
           throw new RuntimeException(e);
       }
    }

    public void deletar(Integer numConta){
        String sql = "DELETE FROM conta WHERE numero = ?";
        PreparedStatement ps;

        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, numConta);

            ps.execute();
            ps.close();
            conn.close();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
