package org.example;

import java.math.BigDecimal;

public record DadosConta(Integer numConta, BigDecimal saldo, DadosCliente dadosCliente) {
}
