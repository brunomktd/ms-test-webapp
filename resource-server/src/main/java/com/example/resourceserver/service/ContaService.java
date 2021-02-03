package com.example.resourceserver.service;

import com.example.resourceserver.model.Conta;
import com.example.resourceserver.repository.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContaService {
    @Autowired
    private ContaRepository contaRepository;

    public Conta cadastrar(Conta conta) {
        return contaRepository.save(conta);
    }

    public List<Conta> findAll() {
        return contaRepository.findAll().stream()
                .sorted(Comparator.comparingInt(Conta::getSaldo).reversed())
                .collect(Collectors.toList());
    }

    public void deletar(Integer id) {
        contaRepository.deleteById(id);
    }

    public Conta depositar(Integer id, Integer valor) {
        Optional<Conta> byId = contaRepository.findById(id);
        if (byId.isPresent()){
            Conta userConta = byId.get();
            userConta.setSaldo(userConta.getSaldo() + valor);
            return contaRepository.save(userConta);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    public Conta findById(Integer id) {
        Optional<Conta> byId = contaRepository.findById(id);
        if (byId.isPresent()){
            return byId.get();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
}
