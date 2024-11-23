package Dados;

import java.util.ArrayList;
import java.util.List;

import Apresentacao.Main;
import entity.Cliente;
import entity.Emprestimo;
import entity.Livro;
import entity.Recepcionista;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;

public class EmprestimoDados {


    public EmprestimoDados(){}


    public Emprestimo buscarEmprestimo(Cliente cliente, String dataLoc, EntityManagerFactory emf){
        EntityManager em = emf.createEntityManager();
        Emprestimo emprestimo = null;

        try {
            List<Emprestimo> emprestimos = em.createQuery(
                    "SELECT e FROM Emprestimo e WHERE e.dataDeLocacao = :dataLoc AND e.cliente = :cliente", Emprestimo.class)
                .setParameter("dataLoc", dataLoc)
                .setParameter("cliente", cliente)
                .getResultList();

            if (!emprestimos.isEmpty()) {
                emprestimo = emprestimos.get(0);  
            }
        }finally {
            em.close();  
        }
        return emprestimo;
    }
    

    public String cadastrarEmprestimo(EntityManagerFactory emf, Recepcionista recepcionista, Emprestimo emp){
        EntityManager em = null;
        EntityTransaction transaction = null;

        String mensagem = "";
  
        try {
            em = emf.createEntityManager();
            transaction = em.getTransaction();
            transaction.begin();
      
            em.persist(emp);
            em.flush();
            transaction.commit();
            mensagem = "Empréstimo registrado com sucesso.";
            
        } catch (RuntimeException e) {
            if (transaction != null) {
                try {
                    transaction.rollback();
                } catch (RuntimeException nestedException) {
                    mensagem = "Erro durante o rollback: " + nestedException.getMessage();
                }
            }
            mensagem = "Erro ao cadastrar o empréstimo: " + e.getMessage();
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return mensagem;
    }


    public String alterarEmprestimoDeLivro(EntityManagerFactory emf, String nome, Emprestimo emprestimo) {
        EntityManager em = null;
        EntityTransaction transaction = null;

        String mensagem = "";
    
        try {
            em = emf.createEntityManager();
            transaction = em.getTransaction();
            transaction.begin();
    
            Livro livro = em.createQuery("SELECT l FROM Livro l WHERE l.nome = :nome", Livro.class)
                            .setParameter("nome", nome)
                            .getSingleResult();
     
            livro.setEmprestimo(emprestimo);
    
            em.flush();
            transaction.commit();
    
        } catch (NoResultException e) {
            mensagem = "Livro não encontrado";
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            mensagem = "Erro ao atualizar o livro: " + e.getMessage();
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return mensagem;
    }


    public String removerEmprestimo(Cliente cliente, String dataLoc, EntityManagerFactory emf){
        EntityManager em = null;
        EntityTransaction transaction = null;

        String mensagem = "";
        
        try {
            em = emf.createEntityManager();
            transaction = em.getTransaction();
            transaction.begin();
            
            Emprestimo emprestimo = em.createQuery(
                    "SELECT l FROM Emprestimo l WHERE l.dataDeLocacao = :dataLoc AND l.cliente = :cliente",
                    Emprestimo.class)
                .setParameter("dataLoc", dataLoc) 
                .setParameter("cliente", cliente) 
                .getSingleResult();
            
            
            if (emprestimo != null) {
                for (Livro livro : emprestimo.getLivros()) {
                    livro.setEmprestimo(null);
                }
                em.remove(emprestimo);  
                em.flush();
                transaction.commit();
                mensagem = "Empréstimo removido com sucesso.";
                
            } else {
                mensagem = "Empréstimo não encontrado.";
            }
        } catch (RuntimeException e) {
            if (transaction != null) {
                try {
                    transaction.rollback();
                } catch (RuntimeException nestedException) {
                    mensagem = "Erro durante o rollback: " + nestedException.getMessage();
                }
            }
            mensagem = "Erro ao remover o empréstimo: " + e.getMessage();
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return mensagem;
    }
    
    
    public String alterarEmprestimo(Cliente cliente, String dataLoc, String novoPrazo, String novaData, EntityManagerFactory emf){
        EntityManager em = null;
        EntityTransaction transaction = null;

        String mensagem = "";
    
        try {
            em = emf.createEntityManager();
            transaction = em.getTransaction();
            transaction.begin();

            Emprestimo emprestimo = em.createQuery("SELECT l FROM Emprestimo l WHERE l.dataDeLocacao = :dataLoc AND l.cliente = :cliente",
                    Emprestimo.class)
                .setParameter("dataLoc", dataLoc) 
                .setParameter("cliente", cliente) 
                .getSingleResult();
            
            if (emprestimo != null) {
                
                emprestimo.setDataDeDevolucao(novaData);  
                emprestimo.setPrazo(novoPrazo);  
    
                em.flush();
                transaction.commit();
                mensagem = "Empréstimo atualizado com sucesso.";
            } else {
                mensagem = "Empréstimo não encontrado.";
            }
        } catch (RuntimeException e) {
            if (transaction != null) {
                try {
                    transaction.rollback();
                } catch (RuntimeException nestedException) {
                    mensagem = "Erro durante o rollback: " + nestedException.getMessage();
                }
            }
            mensagem = "Erro ao alterar o empréstimo: " + e.getMessage();
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return mensagem;
    }
    
}
