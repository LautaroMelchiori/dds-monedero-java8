package dds.monedero.model;

import dds.monedero.exceptions.MaximaCantidadDepositosException;
import dds.monedero.exceptions.MaximoExtraccionDiarioException;
import dds.monedero.exceptions.MontoNegativoException;
import dds.monedero.exceptions.SaldoMenorException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Cuenta {

  private double saldo = 0;
  private List<Movimiento> movimientos = new ArrayList<>();

  public Cuenta() {
    saldo = 0;
  }

  public Cuenta(double montoInicial) {
    saldo = montoInicial;
  }

  public void poner(double cuanto) {
    this.validarMonto(cuanto);
    this.validarExtraccionesDiarias();
    this.agregarDeposito(LocalDate.now(), cuanto);
  }

  public void sacar(double cuanto) {
    this.validarMonto(cuanto);
    this.validarLimitesDeExtraccion(cuanto);
    this.agregarExtraccion(LocalDate.now(), cuanto);
  }

  public void agregarExtraccion(LocalDate unaFecha, double unMonto) {
    this.setSaldo(this.getSaldo() - unMonto);
    Movimiento extraccion = new Extraccion(unaFecha, unMonto);
    this.movimientos.add(extraccion);
  }

  public void agregarDeposito(LocalDate unaFecha, double unMonto) {
    this.setSaldo(this.getSaldo() + unMonto);
    Movimiento deposito = new Deposito(unaFecha, unMonto);
    this.movimientos.add(deposito);
  }

  public void validarExtraccionesDiarias() {
    if (this.cantidadDeDepositosSupera(3)) {
      throw new MaximaCantidadDepositosException("Ya excedio los " + 3 + " depositos diarios");
    }
  }

  public boolean cantidadDeDepositosSupera(int unLimite) {
    return this.getMovimientos().stream()
        .filter(movimiento -> movimiento.fueDepositadoEn(LocalDate.now()))
        .count() >= unLimite;
  }

  public void validarLimitesDeExtraccion(double cuanto) {
    if (getSaldo() - cuanto < 0) {
      throw new SaldoMenorException("No puede sacar mas de " + getSaldo() + " $");
    }
    var montoExtraidoHoy = getMontoExtraidoA(LocalDate.now());
    var limite = 1000 - montoExtraidoHoy;
    if (cuanto > limite) {
      throw new MaximoExtraccionDiarioException(
          "No puede extraer mas de $ " + 1000 + " diarios, " + "límite: " + limite);
    }
  }

  public double getMontoExtraidoA(LocalDate fecha) {
    return getMovimientos().stream()
        .filter(movimiento -> movimiento.fueExtraidoEn(fecha))
        .mapToDouble(Movimiento::getMonto)
        .sum();
  }

  public List<Movimiento> getMovimientos() {
    return movimientos;
  }

  public void setMovimientos(List<Movimiento> movimientos) {
    this.movimientos = movimientos;
  }

  public double getSaldo() {
    return saldo;
  }

  public void setSaldo(double saldo) {
    this.saldo = saldo;
  }

  public void validarMonto(double unMonto) {
    if (unMonto <= 0) {
      throw new MontoNegativoException(unMonto + ": el monto a ingresar debe ser un valor positivo");
    }
  }
}
