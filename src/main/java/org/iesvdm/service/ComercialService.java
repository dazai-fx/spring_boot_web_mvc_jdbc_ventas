package org.iesvdm.service;

import org.iesvdm.dao.ComercialDAO;
import org.iesvdm.modelo.Comercial;
import org.iesvdm.modelo.exception.ComercialNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ComercialService {

    @Autowired
    ComercialDAO comercialDAO;

    public List<Comercial> getAll(){
        return comercialDAO.getAll();
    }

    public Comercial getOne(int id) throws ComercialNotFoundException {
        Optional<Comercial> optCom = comercialDAO.find(id);
        if (optCom.isPresent())
            return optCom.get();
        else
            throw new ComercialNotFoundException("No se encontro Comercial "+id);
    }

    public void newComercial(Comercial comercial) {
        comercialDAO.create(comercial);
    }

    public void replaceComercial(Comercial comercial) {
        comercialDAO.update(comercial);
    }

    public void deleteComercial(int id) {
        comercialDAO.delete(id);
    }

}
