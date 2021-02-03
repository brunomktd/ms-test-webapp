package com.example.webapp.controller;

import com.example.webapp.service.ContaService;
import com.example.webapp.vo.ContaVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.spring5.context.webflux.IReactiveDataDriverContextVariable;
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable;
import reactor.core.publisher.Flux;

import java.util.List;

@Controller
@RequestMapping("/conta")
public class ContaController {

    private final ContaService contaService;

    @Autowired
    public ContaController(ContaService contaService) {
        this.contaService = contaService;
    }

    // Código que usado juntamente com o servidor Netty faz com que a requisição apareça 1 por 1 na tela
//    @GetMapping
//    public String home(Model model){
//                IReactiveDataDriverContextVariable contas =
//                new ReactiveDataDriverContextVariable(contaService.findAllReactive(), 1);
//        model.addAttribute("contas", contas);
//        return "conta/conta";
//    }

    @GetMapping
    public String home(Model model){
        List<ContaVO> contas = contaService.findAll();
        model.addAttribute("contas", contas);
        return "conta/conta";
    }


    @PostMapping("/cadastrar")
    public String cadastrar(ContaVO novaConta){
        contaService.cadastrar(novaConta);
        return "redirect:/conta";
    }

    @PostMapping("/depositar")
    public String depositar(ContaVO conta){
        contaService.depositar(conta.getId(), conta.getSaldo());
        return "redirect:/conta";
    }

    @PostMapping("/deletar")
    public String deletar(Integer id){
        contaService.excluir(id);
        return "redirect:/conta";
    }

}
