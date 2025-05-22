package dds.monedero.model;

import dds.monedero.exceptions.MaximaCantidadDepositosException;
import dds.monedero.exceptions.MaximoExtraccionDiarioException;
import dds.monedero.exceptions.MontoNegativoException;
import dds.monedero.exceptions.SaldoMenorException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.Assertions;

public class MonederoTest {
  private Cuenta cuenta;

  @BeforeEach
  void init() {
    cuenta = new Cuenta();
  }

  @Test
  @DisplayName("Es posible poner $1500 en una cuenta vacía")
  void Poner() {
    Assertions.assertEquals(0, cuenta.getSaldo());
    cuenta.poner(1500);
    Assertions.assertEquals(1500, cuenta.getSaldo());
  }

  @Test
  @DisplayName("No es posible poner montos negativos")
  void PonerMontoNegativo() {
    Assertions.assertThrows(MontoNegativoException.class, () -> cuenta.poner(-1500));
  }

  @Test
  @DisplayName("Es posible realizar múltiples depósitos consecutivos")
  void TresDepositos() {
    cuenta.poner(1500);
    cuenta.poner(456);
    cuenta.poner(1900);
    Assertions.assertEquals(1500+456+1900, cuenta.getSaldo());
  }

  @Test
  @DisplayName("No es posible superar la máxima cantidad de depositos diarios")
  void MasDeTresDepositos() {
    Assertions.assertThrows(MaximaCantidadDepositosException.class, () -> {
      cuenta.poner(1500);
      cuenta.poner(456);
      cuenta.poner(1900);
      cuenta.poner(245);
    });
  }

  @Test
  @DisplayName("Es posible extraer $999 de una cuenta con $1000")
  void Extraer() {
    cuenta.poner(1000);
    cuenta.sacar(999);
    Assertions.assertEquals(1, cuenta.getSaldo());
  }

  @Test
  @DisplayName("Es posible extraer varias veces consecutivas")
  void cuatroExtracciones() {
    cuenta.poner(1000);
    cuenta.sacar(10);
    cuenta.sacar(20);
    cuenta.sacar(30);
    cuenta.sacar(40);
    Assertions.assertEquals(900, cuenta.getSaldo());
  }

  @Test
  @DisplayName("No es posible extraer más que el saldo disponible")
  void ExtraerMasQueElSaldo() {
    Assertions.assertThrows(SaldoMenorException.class, () -> {
      cuenta.setSaldo(90);
      cuenta.sacar(1001);
    });
  }

  @Test
  @DisplayName("No es posible extraer más que el límite diario")
  void ExtraerMasDe1000() {
    Assertions.assertThrows(MaximoExtraccionDiarioException.class, () -> {
      cuenta.setSaldo(5000);
      cuenta.sacar(1001);
    });
  }

  @Test
  @DisplayName("No es posible extraer un monto negativo")
  void ExtraerMontoNegativo() {
    Assertions.assertThrows(MontoNegativoException.class, () -> cuenta.sacar(-500));
  }

}