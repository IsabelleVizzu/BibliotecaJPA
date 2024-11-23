package Dados;

import java.util.List;
import jakarta.persistence.Query;

import Apresentacao.Main;
import entity.Livro;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;

public class LivroDados {
    

    public LivroDados(){}


    public Livro buscarLivroPorNome(String nome, EntityManagerFactory emf) {
        EntityManager em = emf.createEntityManager();
        Livro livroEncontrado = null;
        
        try {
            Query query = em.createQuery("select l from Livro l where l.nome = :nome");
            query.setParameter("nome", nome);
            
            List<Livro> livros = query.getResultList();
            if (!livros.isEmpty()) {
                livroEncontrado = livros.get(0);
            }
        } finally {
            em.close();
        }
        return livroEncontrado; 
    }


    public String addLivro(String nome, String autor, EntityManagerFactory emf) {
        EntityManager em = null;
        EntityTransaction transaction = null;

        String mensagem = "";

        try {
            em = emf.createEntityManager();
            transaction = em.getTransaction();
            transaction.begin();
            
            Livro livro = new Livro(nome, autor);
            em.persist(livro); 
            
            em.flush(); 
            transaction.commit();
            
            mensagem = "Livro cadastrado com sucesso.";
        } catch (RuntimeException e) {
            if (transaction != null) {
                try {
                    transaction.rollback();
                } catch (RuntimeException nestedException) {
                    mensagem = "Erro durante o rollback: " + nestedException.getMessage();
                }
            }
            mensagem = "Erro ao cadastrar o livro: " + e.getMessage();
        } finally {
            if (em != null) {
                em.close();  
            }
        }
        return mensagem;
    }


    public String removerLivro(String nome, EntityManagerFactory emf){
        Livro livro = buscarLivroPorNome(nome, emf);
        
        EntityManager em = null;
        EntityTransaction transaction = null;
    
        if (livro != null) {
            try {
                em = emf.createEntityManager();
                transaction = em.getTransaction();
                transaction.begin();
                
                livro = em.createQuery("SELECT l FROM Livro l WHERE l.nome = :nome", Livro.class)
                        .setParameter("nome", nome)
                        .getSingleResult();
                
                em.remove(livro);
                em.flush();
                transaction.commit();
            } catch (RuntimeException e) {
                if (transaction != null) {
                    try {
                        transaction.rollback();
                    } catch (RuntimeException nestedException) {
                        return "Erro durante o rollback: " + nestedException.getMessage();
                    }
                }
            } finally {
                if (em != null) {
                    em.close();  
                }
            }
            return "Livro removido com sucesso.";
        }
        return "Livro não encontrado";
    }
    
    
    public String alterarLivro(String nome, String novoNome, String novoAutor, EntityManagerFactory emf){
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
    
            livro.setAutor(novoAutor);
            livro.setNome(novoNome);

            em.flush();
            transaction.commit();

            mensagem = "Livro atualizado com sucesso.";
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


}
