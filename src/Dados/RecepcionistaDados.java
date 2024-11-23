package Dados;

import java.util.List;

import jakarta.persistence.Query;

import Apresentacao.Main;
import entity.Recepcionista;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;

public class RecepcionistaDados {


    public RecepcionistaDados(){}


    public Recepcionista buscarRecepcionista(String cpf, EntityManagerFactory emf) {
	    EntityManager em = emf.createEntityManager();
	    Recepcionista recep = null;
	    
	    try {
	    	Query query = em.createQuery("SELECT l FROM Recepcionista l WHERE l.recepcionista_cpf = :cpf");
	    	query.setParameter("cpf", cpf);


	    	Main.imprimir("");
	        List<Recepcionista> recepcionistas = query.getResultList();
	        
	        if (!recepcionistas.isEmpty()) {
	        	recep = recepcionistas.get(0);
	        	System.out.println(recep);
	        }
	    } finally {
	        em.close();
	    }

	    return recep; 
	}


    public Recepcionista addRecepcionista(String nome, String cpf, String email, EntityManagerFactory emf) {
	    EntityManager em = null;
	    EntityTransaction transaction = null;
	    Recepcionista recep = null;

	    try {
	        em = emf.createEntityManager();
	        transaction = em.getTransaction();
	        transaction.begin();

	        recep = buscarRecepcionista(cpf, emf);

	        if (recep == null) {
	            recep = new Recepcionista(nome, cpf, email);
	            em.persist(recep);
	            em.flush(); 
	            transaction.commit();
	        } 
	    } catch (RuntimeException e) {
	        if (transaction != null && transaction.isActive()) {
	            try {
	                transaction.rollback();
	            } catch (RuntimeException nestedException) {
	                System.out.println("Erro durante o rollback: " + nestedException.getMessage());
	            }
	        }
	    } finally {
	        if (em != null) {
	            em.close();
	        }
	    }
	    return recep;
	}


    public String alterarRecepcionista(String cpf, String nome, String email, EntityManagerFactory emf) {
	    EntityManager em = null;
	    EntityTransaction transaction = null;

        String mensagem = "";

	    try {
	        em = emf.createEntityManager();
	        transaction = em.getTransaction();
	        transaction.begin();

	        Recepcionista recep = em.createQuery("SELECT r FROM Recepcionista r WHERE r.recepcionista_cpf = :cpf", Recepcionista.class)
	                                .setParameter("cpf", cpf)
	                                .getSingleResult();

	        recep.setNome(nome);
	        recep.setEmail(email);

	        em.flush();
	        transaction.commit();

	        mensagem = "Recepcionista atualizada com sucesso.";
	    } catch (NoResultException e) {
	        mensagem = "Recepcionista não encontrada";
	    } catch (Exception e) {
	        if (transaction != null && transaction.isActive()) {
	            transaction.rollback();
	        }
	        mensagem = "Erro ao atualizar a recepcionista: " + e.getMessage();
	    } finally {
	        if (em != null) {
	            em.close();
	        }
	    }
	    return mensagem;
	}


    public String removerRecepcionista(String cpf, EntityManagerFactory emf) {
	    EntityManager em = null;
	    EntityTransaction transaction = null;

        String mensagem = "";

	    try {
	        em = emf.createEntityManager();
	        transaction = em.getTransaction();
	        transaction.begin();

	        Recepcionista recepcionista = em.createQuery("SELECT r FROM Recepcionista r WHERE r.recepcionista_cpf = :cpf", Recepcionista.class)
	                                        .setParameter("cpf", cpf)
	                                        .getSingleResult();

	        if (recepcionista != null) {
	            em.remove(recepcionista); 
	            em.flush(); 
	            transaction.commit(); 

	            mensagem = "Recepcionista removido(a) com sucesso.";
	        } else {
	            mensagem = "Recepcionista não encontrado.";
	        }
	    } catch (RuntimeException e) {
	        if (transaction != null && transaction.isActive()) {
	            try {
	                transaction.rollback(); 
	            } catch (RuntimeException nestedException) {
	                mensagem = "Erro durante o rollback: " + nestedException.getMessage();
	            }
	        }
	        mensagem = "Erro ao remover o(a) recepcionista: " + e.getMessage();
	    } finally {
	        if (em != null) {
	            em.close(); 
	        }
	    }
        
        return mensagem;

	}

}
