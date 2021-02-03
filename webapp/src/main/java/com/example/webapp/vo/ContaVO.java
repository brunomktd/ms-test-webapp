package com.example.webapp.vo;

public class ContaVO {
    private Integer id;
    private Integer saldo;
    private String proprietario;

    public Integer getId() {
        return id;
    }

    public ContaVO setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getSaldo() {
        return saldo;
    }

    public ContaVO setSaldo(Integer saldo) {
        this.saldo = saldo;
        return this;
    }

    public String getProprietario() {
        return proprietario;
    }

    public ContaVO setProprietario(String proprietario) {
        this.proprietario = proprietario;
        return this;
    }
}
