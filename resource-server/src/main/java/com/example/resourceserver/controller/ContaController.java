package com.example.resourceserver.controller;

import com.example.resourceserver.model.Conta;
import com.example.resourceserver.service.ContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ContaController {

    @Autowired
    private ContaService contaService;

    @PostMapping("/admin/contas")
    public Conta novaConta(@RequestBody Conta input){
        return contaService.cadastrar(input);
    }

    @GetMapping("/admin/contas")
    public List<Conta> buscarContas(){
        return contaService.findAll();
    }

    @DeleteMapping("/admin/contas/{id}")
    public void deletarConta(@PathVariable("id") Integer id){
        contaService.deletar(id);
    }

    @PutMapping("/protect/contas/{id}")
    public Conta depositar(@PathVariable("id") Integer id, @RequestParam Integer valor){
        return contaService.depositar(id, valor);
    }

    @GetMapping("/protect/contas/{id}")
    public Conta verificar(@PathVariable("id") Integer id){
        return contaService.findById(id);
    }
}
