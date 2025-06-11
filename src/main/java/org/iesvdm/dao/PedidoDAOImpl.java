package org.iesvdm.dao;

import org.iesvdm.modelo.Cliente;
import org.iesvdm.modelo.Comercial;
import org.iesvdm.modelo.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public class PedidoDAOImpl implements PedidoDAO {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<Pedido> getAllByIdComercial(int idComercial) {

        List<Pedido> listPedidos = jdbcTemplate.query(
                """
                        SELECT P.id,
                        P.total,
                        P.fecha,
                        P.id_cliente,
                        P.id_comercial,
                        CO.nombre,
                        CO.apellido1,
                        CO.apellido2,
                        CO.comision,
                        CL.nombre,
                        CL.apellido1,
                        CL.apellido2,
                        CL.ciudad,
                        CL.categoria
                        FROM pedido P
                        LEFT JOIN comercial CO ON CO.id = P.id_comercial
                        LEFT JOIN cliente CL ON CL.id = P.id_cliente 
                        WHERE P.id_comercial = ? 
                        """,
                (rs, rowNum) -> {

                    Pedido p = new Pedido();

                    Comercial co = new Comercial();
                    Cliente cli = new Cliente();

                    co.setId(rs.getInt("P.id_comercial"));
                    co.setNombre(rs.getString("CO.nombre"));
                    co.setApellido1(rs.getString("CO.apellido1"));
                    co.setApellido2(rs.getString("CO.apellido2"));
                    co.setComision(rs.getFloat("CO.comision"));

                    cli.setId(rs.getInt("P.id_cliente"));
                    cli.setNombre(rs.getString("CL.nombre"));
                    cli.setApellido1(rs.getString("CL.apellido1"));
                    cli.setApellido2(rs.getString("CL.apellido2"));
                    cli.setCiudad(rs.getString("CL.ciudad"));
                    cli.setCategoria(rs.getInt("CL.categoria"));

                    p.setId(rs.getInt("P.id"));
                    p.setTotal(rs.getDouble("P.total"));
                    p.setFecha(rs.getDate("P.fecha").toLocalDate());
                    p.setCliente(cli);
                    p.setComercial(co);

                    return p;

                }, idComercial);

        return listPedidos;

    }
}
