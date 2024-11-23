package Negocio;

import java.util.List;
import javax.management.Query;

import entity.Livro;
import jakarta.persistence.EntityManagerFactory;
import Dados.LivroDados;

public class LivroNegocio {
    private LivroDados livro = new LivroDados();


    public LivroNegocio(){}


    public Livro buscarLivroPorNome(String nome, EntityManagerFactory emf) {
        return livro.buscarLivroPorNome(nome, emf);
    }


    public String addLivro(String nome, String autor, EntityManagerFactory emf){
        return livro.addLivro(nome, autor, emf);
    }


    public String removerLivro(String nome, EntityManagerFactory emf){
        return livro.removerLivro(nome, emf);
    }


    public String alterarLivro(String nome, String novoNome, String novoAutor, EntityManagerFactory emf){
        return livro.alterarLivro(nome, novoNome, novoAutor, emf);
    }

    
}
