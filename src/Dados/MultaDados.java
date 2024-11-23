package Dados;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Query;

import Apresentacao.Main;
import entity.Cliente;
import entity.Emprestimo;
import entity.Multa;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;

public class MultaDados {
    

    public MultaDados(){}


    public List<Multa> visualizarMultas(String cpf, EntityManagerFactory emf){
        EntityManager em = emf.createEntityManager();
        List<Multa> multas =  null;

        try {
            Query query = em.createQuery("SELECT m FROM Multa m WHERE m.emprestimo.cliente.cliente_cpf = :cpf");
            query.setParameter("cpf", cpf);

            multas = query.getResultList();

        } finally {
            em.close();
        }

        return multas; 
    }


    public String addMulta(String descricao, double valor, Emprestimo emp, EntityManagerFactory emf){
        EntityManager em = null;
        EntityTransaction transaction = null;

        String mensagem = "";

        try{
            em = emf.createEntityManager();
            transaction = em.getTransaction();
            transaction.begin();

            Multa multa = new Multa(emp, valor, descricao);

            em.persist(multa);
            em.flush();
            transaction.commit();
            mensagem = "Multa cadastrado com sucesso.";
            
        } catch (RuntimeException e) {
            if (transaction != null) {
                try {
                    transaction.rollback();
                } catch (RuntimeException nestedException) {
                    mensagem = "Erro durante o rollback: " + nestedException.getMessage();
                }
            }
            mensagem = "Erro ao cadastrar a multa: " + e.getMessage();
        } finally {
            if (em != null) {
                em.close();  
            }
        }
        return mensagem;
    }

    
    public String removerMulta(Multa multa, EntityManagerFactory emf){
        EntityManager em = null;
        EntityTransaction transaction = null;

        String mensagem = "";

        try {
            em = emf.createEntityManager();
            transaction = em.getTransaction();
            transaction.begin();
            
            if(multa == null){
                mensagem = "Multa não encontrada.";

            }
            else{
            	int idd = multa.getId();
            	Multa m = em.createQuery("SELECT m FROM Multa  m WHERE m.id = :idd", Multa.class)
	                        .setParameter("idd", idd)
	                        .getSingleResult();
                em.remove(m);
                em.flush();
                transaction.commit();
                    
                mensagem = "Multa removida com sucesso.";
            }
            } catch (RuntimeException e) {

                if (transaction != null) {
                    try {
                        transaction.rollback();
                    } catch (RuntimeException nestedException) {
                        mensagem = "Erro durante o rollback: " + nestedException.getMessage();
                    }
                }
            } finally {
                if (em != null) {
                    em.close();  
                }
            }
            return mensagem;
    }


    public String alterarMulta(Multa multa, String descricao, double valor, EntityManagerFactory emf){
        EntityManager em = null;
        EntityTransaction transaction = null;

        String mensagem = "";

        try {
            em = emf.createEntityManager();
            transaction = em.getTransaction();
            transaction.begin();
               
            if(multa == null){
                mensagem = "Multa não encontrada.";
            }
            else{
            	int idd = multa.getId();
            	Multa m = em.createQuery("SELECT m FROM Multa  m WHERE m.id = :idd", Multa.class)
	                        .setParameter("idd", idd)
	                        .getSingleResult();
                m.setNome(descricao);
                m.setValor(valor);
        
                em.flush();
                transaction.commit();
        
                mensagem = "Multa atualizada com sucesso.";
            }
            } catch (RuntimeException e) {
                if (transaction != null) {
                    try {
                        transaction.rollback();
                    } catch (RuntimeException nestedException) {
                        mensagem = "Erro durante o rollback: " + nestedException.getMessage();
                    }
                }
            } finally {
                if (em != null) {
                    em.close();  
                }
            }
            return mensagem;
    }
    

}
