package org.iesvdm.dao;

import org.iesvdm.modelo.Pedido;

import java.util.List;

public interface PedidoDAO {

    List<Pedido> getAllByIdComercial(int idComercial);

}
