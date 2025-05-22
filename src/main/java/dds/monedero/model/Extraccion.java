package dds.monedero.model;

import java.time.LocalDate;

public class Extraccion extends Movimiento{
  public Extraccion(LocalDate fecha, double monto) {
    super(fecha, monto);
  }

  @Override
  public boolean fueExtraidoEn(LocalDate unaFecha) {
    return this.getFecha().equals(unaFecha);
  }

  @Override
  public boolean fueDepositadoEn(LocalDate unaFecha) {
    return false;
  }
}
