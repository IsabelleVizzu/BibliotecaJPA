package Negocio;

import java.util.ArrayList;

import Dados.EmprestimoDados;
import entity.*;
import jakarta.persistence.EntityManagerFactory;

public class EmprestimoNegocio {
    private EmprestimoDados emp = new EmprestimoDados();


    public void addEmprestimo(Emprestimo emprestimo, Recepcionista recep){
        recep.getEmprestimo().add(emprestimo);
	}


    public Emprestimo buscarEmprestimo(Cliente cliente, String dataLoc, EntityManagerFactory emf) {
        return emp.buscarEmprestimo(cliente, dataLoc, emf);
    }


    public ArrayList<String> cadastrarEmprestimo(EntityManagerFactory emf, Recepcionista recepcionista, String dataInicial, String dataDevolucao, String prazo, Cliente cliente, ArrayList<Livro> livros){
        ArrayList<String> mensagens = new ArrayList<>();
        Emprestimo emprestimo = new Emprestimo(dataInicial, dataDevolucao, prazo, cliente, recepcionista, livros, null);
        for (Livro l:livros) {
            String mensagem = emp.alterarEmprestimoDeLivro(emf, l.getNome(), emprestimo);
            mensagens.add(mensagem);
        }
        addEmprestimo(emprestimo, recepcionista);
        mensagens.add(emp.cadastrarEmprestimo(emf, recepcionista, emprestimo));
        return mensagens;
    }


    public String removerEmprestimo(Cliente cliente, String dataLoc, EntityManagerFactory emf){
        return emp.removerEmprestimo(cliente, dataLoc, emf);
    }


    public String alterarEmprestimo(Cliente cliente, String dataLoc, String novoPrazo, String novaData, EntityManagerFactory emf){
        return emp.alterarEmprestimo(cliente, dataLoc, novoPrazo, novaData, emf);
    }
    
    
}
