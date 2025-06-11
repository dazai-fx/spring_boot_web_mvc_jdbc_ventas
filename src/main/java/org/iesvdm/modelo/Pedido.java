package org.iesvdm.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pedido {
    private int id;
    private Comercial comercial;
    private LocalDate fecha;
    private Cliente cliente;
    private double total;
}
