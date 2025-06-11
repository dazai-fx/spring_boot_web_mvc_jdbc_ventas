package org.iesvdm.dao;

import org.iesvdm.modelo.Cliente;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ClienteDAOImplTest {

    @Autowired
    ClienteDAOImpl clienteDAO;

    @Test
    void getAll() {
        List<Cliente> clientes = clienteDAO.getAll();
        clientes.forEach(System.out::println);
    }
}