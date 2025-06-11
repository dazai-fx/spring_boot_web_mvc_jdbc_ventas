package org.iesvdm.service;

import java.util.List;
import java.util.Optional;

import org.iesvdm.dao.ClienteDAO;
import org.iesvdm.modelo.Cliente;
import org.iesvdm.modelo.exception.ClienteNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {
	
	private ClienteDAO clienteDAO;
	
	//Se utiliza inyección automática por constructor del framework Spring.
	//Por tanto, se puede omitir la anotación Autowired
	//@Autowired
	public ClienteService(ClienteDAO clienteDAO) {
		this.clienteDAO = clienteDAO;
	}
	
	public List<Cliente> listAll() {
		
		return clienteDAO.getAll();
		
	}

	public Cliente one(Integer id) throws ClienteNotFoundException{
		Optional<Cliente> optFab = clienteDAO.find(id);
		if (optFab.isPresent())
			return optFab.get();
		else
			throw new ClienteNotFoundException("No se encontro Cliente "+id);
	}

	public void newCliente(Cliente cliente) {
		clienteDAO.create(cliente);

	}

	public void replaceCliente(Cliente cliente) {
		clienteDAO.update(cliente);
	}

	public void deleteCliente(Integer id) {
		clienteDAO.delete(id);
	}
	
	

}
