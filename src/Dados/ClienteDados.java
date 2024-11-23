package Dados;

import java.util.List;
import jakarta.persistence.Query;

import Apresentacao.Main;
import entity.Cliente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;



public class ClienteDados {


    public ClienteDados(){}


    public Cliente buscarCliente(String cpf, EntityManagerFactory emf){
        EntityManager em = emf.createEntityManager();
        Cliente clienteEncontrado  = null;

        try {
        	Query query = em.createQuery("select l from Cliente l where l.cliente_cpf = :cpf");
        	query.setParameter("cpf", cpf);  
	        
            List<Cliente> clientes = query.getResultList();
	        if (!clientes.isEmpty()) {
	            clienteEncontrado = clientes.get(0);
	        }
	    } finally {
	        em.close();
	    }
	    return clienteEncontrado; 
    }


    public String addCliente(String cpf, String nome,  EntityManagerFactory emf){
        EntityManager em = null;
        EntityTransaction transaction = null;

        String mensagem = "";

        try{
            em = emf.createEntityManager();
            transaction = em.getTransaction();
            transaction.begin();

            if(buscarCliente(cpf, emf) != null){
                mensagem = "CPF já cadastrado";
            }
            else{
                Cliente cliente = new Cliente(nome, cpf);
                em.persist(cliente);
                em.flush();
                transaction.commit();
                mensagem = "Cliente cadastrado com sucesso.";
            }

        } catch (RuntimeException e) {
	        if (transaction != null) {
	            try {
	                transaction.rollback();
	            } catch (RuntimeException nestedException) {
	                mensagem = "Erro durante o rollback: " + nestedException.getMessage();
	            }
	        }
	        mensagem = "Erro ao cadastrar o cliente: " + e.getMessage();
	    } finally {
	        if (em != null) {
	            em.close();  
	        }
	    }
        return mensagem;
    }


    public String removerCliente(String cpf, EntityManagerFactory emf) {
	    Cliente cliente = buscarCliente(cpf, emf);
        
	    EntityManager em = null;
	    EntityTransaction transaction = null;

	    if (cliente != null) {
	        try {
	            em = emf.createEntityManager();
	            transaction = em.getTransaction();
	            transaction.begin();
	            
	            cliente = em.createQuery("SELECT c FROM Cliente c WHERE c.cliente_cpf = :cpf", Cliente.class)
	                        .setParameter("cpf", cpf)
	                        .getSingleResult();
	            
	            em.remove(cliente);
	            em.flush();
	            transaction.commit();
	            
	            return "Cliente removido com sucesso.";
	        } catch (RuntimeException e) {
	            if (transaction != null) {
	                try {
	                    transaction.rollback();
	                    
	                } catch (RuntimeException nestedException) {
	                    return  "Erro durante o rollback: " + nestedException.getMessage();
	                }
	            }
	        } finally {
	            if (em != null) {
	                em.close();  
	            }
	        }
	    } else {
	        return "Cliente não encontrado";
	    }
	    return "Erro ao remover o cliente.";
	}


	public String alterarDadosCliente(String cpf, String novoNome, String novoCpf, EntityManagerFactory emf){
	    EntityManager em = null;
	    EntityTransaction transaction = null;

        String mensagem = "";

	    try {
	        em = emf.createEntityManager();
	        transaction = em.getTransaction();
	        transaction.begin();

	        Cliente cliente = em.createQuery("SELECT c FROM Cliente c WHERE c.cliente_cpf = :cpf", Cliente.class)
	                            .setParameter("cpf", cpf)
	                            .getSingleResult();

	        if (buscarCliente(novoCpf, emf) != null) {
	            mensagem = "CPF já cadastrado";
	        } else {
	            cliente.setNome(novoNome);
	            cliente.setCliente_cpf(novoCpf);

	            em.flush();
	            transaction.commit();

	            mensagem = "Cadastro do Cliente atualizado com sucesso.";
	        }
	    } catch (NoResultException e) {
	        mensagem = "Cliente não encontrado";
	    } catch (Exception e) {
	        if (transaction != null && transaction.isActive()) {
	            transaction.rollback();
	        }
	        mensagem = "Erro ao atualizar o Cliente: " + e.getMessage();
	    } finally {
	        if (em != null) {
	            em.close();
	        }
	    }
        return mensagem;
	}
	

}
