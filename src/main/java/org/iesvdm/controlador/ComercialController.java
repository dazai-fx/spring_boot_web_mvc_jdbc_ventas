package org.iesvdm.controlador;

import org.iesvdm.modelo.Comercial;
import org.iesvdm.modelo.Pedido;
import org.iesvdm.modelo.exception.ComercialNotFoundException;
import org.iesvdm.service.ComercialService;
import org.iesvdm.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class ComercialController {

    @Autowired
    private ComercialService comercialService;
    @Autowired
    private PedidoService pedidoService;

    public ComercialController(ComercialService comercialService) {
        this.comercialService = comercialService;
    }

    @GetMapping("/comerciales")
    public String comerciales(Model model) {

        List<Comercial> listaComerciales = comercialService.getAll();

        model.addAttribute("listaComerciales", listaComerciales);

        return "comerciales";

    }

    @GetMapping("/comerciales/{id}")
    public String mostrarComercial(@PathVariable Integer id,
                                   Model model,
                                   RedirectAttributes ra) {

        try{
            Comercial comercial = comercialService.getOne(id);
            List<Pedido> listPedidos = pedidoService.getAllByComercialID(id);
            model.addAttribute("comercial", comercial);
            model.addAttribute("listaPedidos", listPedidos);

        } catch (ComercialNotFoundException e){
            e.printStackTrace();
            ra.addFlashAttribute("error", "El comercial no existe");
            return "redirect:/comerciales";
        }

        return "detalles-comercial";

    }

    @GetMapping("/comerciales/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Integer id,
                                          Model model,
                                          RedirectAttributes ra) {

        try{
            Comercial comercial = comercialService.getOne(id);
            model.addAttribute("comercial", comercial);
            model.addAttribute("pageTitle", "Editar comercial");
        }catch (ComercialNotFoundException e){
            e.printStackTrace();
            ra.addFlashAttribute("error", "El comercial no existe");
            return "redirect:/comerciales";
        }

        return "formulario-comercial";
    }

    @GetMapping("/comerciales/nuevo")
    public String nuevoComercial(Model model) {
        model.addAttribute("comercial", new Comercial());
        model.addAttribute("pageTitle", "Nuevo comercial");
        return "formulario-comercial";
    }

    @PostMapping("/comerciales/guardar")
    public String guardarComercial(Comercial comercial,
                                   RedirectAttributes ra) {
        try{
            comercialService.getOne(comercial.getId());
        }catch (ComercialNotFoundException e){
            comercialService.newComercial(comercial);
            ra.addFlashAttribute("message", "El comercial ha sido guardado correctamente");
            return "redirect:/comerciales";
        }

        comercialService.replaceComercial(comercial);
        ra.addFlashAttribute("message", "El comercial ha sido actualizado correctamente");

        return "redirect:/comerciales";

    }

    @GetMapping("/comerciales/eliminar/{id}")
    public String eliminarComercial(@PathVariable Integer id,
                                    RedirectAttributes ra) {

        try{
            comercialService.getOne(id);
            comercialService.deleteComercial(id);
            ra.addFlashAttribute("message", "El comercial con id "+id+" ha sido eliminado con exito");

        }catch (ComercialNotFoundException e){
            e.printStackTrace();
            ra.addFlashAttribute("error", "El comercial no existe");
        }

        return "redirect:/comerciales";
    }





}
