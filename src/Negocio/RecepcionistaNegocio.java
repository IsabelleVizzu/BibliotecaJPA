package Negocio;

import Dados.RecepcionistaDados;
import entity.Recepcionista;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

public class RecepcionistaNegocio {
    private RecepcionistaDados recep = new RecepcionistaDados();


    public RecepcionistaNegocio(){}


    public Recepcionista buscarRecepcionista(String cpf, EntityManagerFactory emf){
        return recep.buscarRecepcionista(cpf, emf);
    }


    public Recepcionista addRecepcionista(String nome, String cpf, String email,EntityManagerFactory emf){
        return recep.addRecepcionista(nome, cpf, email, emf);
    }


    public String alterarRecepcionista(String cpf, String nome, String email, EntityManagerFactory emf){
        return recep.alterarRecepcionista(cpf, nome, email, emf);
    }


    public String removerRecepcionista(String cpf, EntityManagerFactory emf){
        return recep.removerRecepcionista(cpf, emf);
    }
}
