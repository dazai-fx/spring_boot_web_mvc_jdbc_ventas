package org.iesvdm.controlador;

import java.util.List;

import org.iesvdm.modelo.Cliente;
import org.iesvdm.modelo.exception.ClienteNotFoundException;
import org.iesvdm.service.ClienteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
//Se puede fijar ruta base de las peticiones de este controlador.
//Los mappings de los métodos tendrían este valor /clientes como
//prefijo.
//@RequestMapping("/clientes")
public class ClienteController {
	
	private ClienteService clienteService;
	
	//Se utiliza inyección automática por constructor del framework Spring.
	//Por tanto, se puede omitir la anotación Autowired
	//@Autowired
	public ClienteController(ClienteService clienteService) {
		this.clienteService = clienteService;
	}
	
	//@RequestMapping(value = "/clientes", method = RequestMethod.GET)
	//equivalente a la siguiente anotación
	@GetMapping("/clientes") //Al no tener ruta base para el controlador, cada método tiene que tener la ruta completa
	public String listar(Model model) {
		
		List<Cliente> listaClientes =  clienteService.listAll();
		model.addAttribute("listaClientes", listaClientes);
				
		return "clientes";
		
	}

	@GetMapping("/clientes/{id}")
	public String mostrarCliente(@PathVariable Integer id, Model model, RedirectAttributes ra) {
		try {
			Cliente cliente = clienteService.one(id);
			model.addAttribute("cliente", cliente);
		} catch (ClienteNotFoundException e) {
			e.printStackTrace();
			ra.addFlashAttribute("error", "El cliente no existe");
			return "redirect:/clientes";
		}
		return "detalles-cliente";
	}

	@GetMapping("/clientes/editar/{id}")
	public String mostrarFormularioEditar(@PathVariable Integer id,
									Model model,
									RedirectAttributes ra) {
		try {
			Cliente cliente = clienteService.one(id);
			model.addAttribute("cliente", cliente);
			model.addAttribute("pageTitle", "Editar cliente");
		} catch (ClienteNotFoundException e) {
			e.printStackTrace();
			ra.addFlashAttribute("error", "El cliente no existe");
			return "redirect:/clientes";
		}
		return "formulario-cliente";
	}

	@GetMapping("/clientes/nuevo")
	public String mostrarFormularioNuevo(Model model) {
		model.addAttribute("cliente", new Cliente());
		model.addAttribute("pageTitle", "Añadir cliente");
		return "formulario-cliente";
	}

	@PostMapping("/clientes/guardar")
	public String guardarCliente(Cliente cliente,
						   RedirectAttributes ra) {

		try {
			clienteService.one((int) cliente.getId());
		} catch (ClienteNotFoundException e) {

			clienteService.newCliente(cliente);
			ra.addFlashAttribute("message", "El cliente ha sido creado correctamente");
			return "redirect:/clientes";
		}

		clienteService.replaceCliente(cliente);

		ra.addFlashAttribute("message", "El cliente ha sido actualizado correctamente");

		return "redirect:/clientes";

	}

	@GetMapping("/clientes/eliminar/{id}")
	public String eliminarCliente(@PathVariable Integer id, RedirectAttributes ra) {
		try {
			clienteService.one(id);
			clienteService.deleteCliente(id);
			ra.addFlashAttribute("message", "El cliente con id: " + id + " ha sido eliminado correctamente");
		} catch (ClienteNotFoundException e) {
			e.printStackTrace();
			ra.addFlashAttribute("error", e.getMessage());

		}
		return "redirect:/clientes";
	}
	
	
	
	

}
