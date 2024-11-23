package Negocio;

import Dados.ClienteDados;
import entity.Cliente;
import jakarta.persistence.EntityManagerFactory;

public class ClienteNegocio {
    private ClienteDados cliente = new ClienteDados();


    public ClienteNegocio(){}


    public Cliente buscarCliente(String cpf, EntityManagerFactory emf){
        return cliente.buscarCliente(cpf, emf);
    }


    public String addCliente(String cpf, String nome,  EntityManagerFactory emf){
        return cliente.addCliente(cpf, nome, emf);
    }
    

    public String removerCliente(String cpf, EntityManagerFactory emf){
        return cliente.removerCliente(cpf, emf);
    }


    public String alterarDadosCliente(String cpf, String novoNome, String novoCpf, EntityManagerFactory emf){
        return cliente.alterarDadosCliente(cpf, novoNome, novoCpf, emf);
    }

    
}
