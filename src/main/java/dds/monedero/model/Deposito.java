package dds.monedero.model;

import java.time.LocalDate;

public class Deposito extends Movimiento{
  public Deposito(LocalDate fecha, double monto) {
    super(fecha, monto);
  }

  @Override
  public boolean fueExtraidoEn(LocalDate unaFecha) {
    return false;
  }

  @Override
  public boolean fueDepositadoEn(LocalDate unaFecha) {
    return this.getFecha().equals(unaFecha);
  }
}
