package Apresentacao;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import Dados.RecepcionistaDados;
import Negocio.*;
import entity.Cliente;
import entity.Emprestimo;
import entity.Livro;
import entity.Multa;
import entity.Recepcionista;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NoResultException;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Query;

public class RecepcionistaApresentacao {
	
    private RecepcionistaNegocio recep = new RecepcionistaNegocio();
    private ClienteNegocio cliente = new ClienteNegocio();
    private LivroNegocio livro = new LivroNegocio();
    private EmprestimoNegocio emp = new EmprestimoNegocio();
    private MultaNegocio multa =  new MultaNegocio();

    
    public Recepcionista buscarRecepcionista(String cpf, EntityManagerFactory emf){
        return recep.buscarRecepcionista(cpf, emf);
    }


    public Recepcionista addRecepcionista(EntityManagerFactory emf) {
	    Main.imprimir("Cadastrando Recepcionista");

	    String nome = Main.askQuestion("Digite o seu nome: ");
	    String cpf = Main.askQuestion("Digite o seu cpf: ");
	    String email = Main.askQuestion("Digite o seu email: ");

        Recepcionista verificarCadastro = recep.buscarRecepcionista(cpf, emf);

	    if (verificarCadastro == null) {
            verificarCadastro = recep.addRecepcionista(nome, cpf, email, emf);
	        System.out.println("Recepcionista cadastrado com sucesso.");
	    } else {
	        System.out.println("Recepcionista já cadastrado.");
	    }
	    
	    return verificarCadastro;
	}


    public void alterarRecepcionista(EntityManagerFactory emf) {
	    Main.imprimir("Edição de Recepcionista");
	    String cpf = Main.askQuestion("Digite o CPF da recepcionista que deseja atualizar: ");

	    String nome = Main.askQuestion("Digite o nome atualizado: ");
	    String email = Main.askQuestion("Digite o email atualizado: ");

        String mensagem = recep.alterarRecepcionista(cpf, nome, email, emf);
        Main.imprimir(mensagem);
	}


    public void removerRecepcionista(EntityManagerFactory emf) {
	    Main.imprimir("Remoção de Recepcionista");
	    String cpf = Main.askQuestion("Digite o cpf do(a) recepcionista: ");

	    // Verifique se o CPF foi informado corretamente
	    System.out.println("CPF informado: " + cpf);

	    String mensagem = recep.removerRecepcionista(cpf, emf);
        Main.imprimir(mensagem);
	}


    //Cliente
    public Cliente buscarCliente(String cpf, EntityManagerFactory emf){
        return cliente.buscarCliente(cpf, emf);
    }


    public void addCliente(EntityManagerFactory emf){
        Main.imprimir("Cadastrando Cliente");

        String cpf = Main.askQuestion("Digite o CPF do cliente: ");
        String nome = Main.askQuestion("Digite o nome do cliente: ");

        String mensagem = cliente.addCliente(cpf, nome, emf);
        Main.imprimir(mensagem);
    }

    
    public void removerCliente(EntityManagerFactory emf) {
	    Main.imprimir("Remoção do Cliente");
	    String cpf = Main.askQuestion("Digite o CPF do Cliente: ");


        String mensagem = cliente.removerCliente(cpf, emf);
        Main.imprimir(mensagem);
	}


	public void alterarDadosCliente(EntityManagerFactory emf) {
	    Main.imprimir("Edição dos dados do Cliente");
	    String cpf = Main.askQuestion("Digite o CPF do Cliente que deseja alterar: ");

	    String novoNome = Main.askQuestion("Digite o nome atualizado do Cliente: ");
	    String novoCpf = Main.askQuestion("Digite o CPF atualizado do Cliente: ");

	    String mensagem = cliente.alterarDadosCliente(cpf, novoNome, novoCpf, emf);
        Main.imprimir(mensagem);
	}
	
	
    //Livro
	public Livro buscarLivroPorNome(String nome, EntityManagerFactory emf) {
        return livro.buscarLivroPorNome(nome, emf);
    }


    public void addLivro(EntityManagerFactory emf) {
        Main.imprimir("Cadastrando Livro");
       
        String nome = Main.askQuestion("Digite o nome do livro: ");
        String autor = Main.askQuestion("Digite o nome do autor: ");
            
        String mensagem = livro.addLivro(nome, autor, emf);
        Main.imprimir(mensagem);
    }


    public void removerLivro(EntityManagerFactory emf){
        Main.imprimir("Remoção de Livro");
        String nome = Main.askQuestion("Digite o nome do livro: ");
        
        String mensagem = livro.removerLivro(nome, emf);
        Main.imprimir(mensagem);
    }
    
        
    public void alterarLivro(EntityManagerFactory emf) {
        Main.imprimir("Edição de Livro");
        String nome = Main.askQuestion("Digite o nome do livro que deseja alterar: ");

        String novoAutor = Main.askQuestion("Digite o nome atualizado do autor: ");
        String novoNome = Main.askQuestion("Digite o nome atualizado do livro: ");
            
        String mensagem = livro.alterarLivro(nome, novoNome, novoAutor, emf);
        Main.imprimir(mensagem);
    }


    //Emprestimo
    public Emprestimo buscarEmprestimo(EntityManagerFactory emf) {
        Cliente cliente = this.cliente.buscarCliente(Main.askQuestion("Digite o nome do cpf do cliente que realizou o empréstimo"), emf);
        String dataLoc = Main.askQuestion("Digite a data em que o empréstimo foi feito: ");

        Emprestimo verificarEmprestimo = null;

        if (recep == null) {
            return verificarEmprestimo;
	    } else {
	        verificarEmprestimo = emp.buscarEmprestimo(cliente, dataLoc, emf);
            return verificarEmprestimo;

	    }
    }


    public void cadastrarEmprestimo(EntityManagerFactory emf, Recepcionista recepcionista) {
        Main.imprimir("Registrando Empréstimo");

        String dataInicial = Main.askQuestion("Digite a data do empréstimodo: ");
        String dataDevolucao = Main.askQuestion("Digite a data prevista da devolução do empréstimodo: ");
        String prazo = Main.askQuestion("Digite o prazo em dias, em que o alugador ficará sobe posse do(s) livro(s): ");
        Cliente cliente = buscarCliente(Main.askQuestion("Digite o cpf do alugador: "), emf);
            
        ArrayList<Livro> livros = new ArrayList<>();
        Main.imprimir("Solicitação dos nomes dos livros");

        menu: while (true) {
            Livro livro = buscarLivroPorNome(Main.askQuestion("Digite o nome do livro: "), emf);
            if (livro != null) {
                    livros.add(livro);
            }
                
            switch (Main.askQuestion("Deseja adicionar mais um livro, 1-Sim e 2-Não")) {
                case "1":
                    break;
                case "2":
                    break menu;
                default:
                    break;
            }
        }

        ArrayList<String> mensagens = emp.cadastrarEmprestimo(emf, recepcionista, dataInicial, dataDevolucao, prazo, cliente, livros);

        for(String s: mensagens){
            System.out.println(s);
        }
    }


    public void removerEmprestimo(EntityManagerFactory emf) {
        Main.imprimir("Remoção de Empréstimo");
    
        Cliente cliente = buscarCliente(Main.askQuestion("Digite o nome do cpf do cliente que realizou o empréstimo"), emf);
        String dataLoc = Main.askQuestion("Digite a data em que o empréstimo foi feito: ");
           
        String mensagem = emp.removerEmprestimo(cliente, dataLoc, emf);
        Main.imprimir(mensagem);     
    }
    

    public void alterarEmprestimo(EntityManagerFactory emf) {
        Main.imprimir("Edição de Empréstimo");

        Cliente cliente = buscarCliente(Main.askQuestion("Digite o nome do cpf do cliente que realizou o empréstimo"), emf);
        String dataLoc = Main.askQuestion("Digite a data em que o empréstimo foi feito: ");
        String novaData = Main.askQuestion("Digite a nova data de devolução do empréstimo: ");
        String novoPrazo = Main.askQuestion("Digite o novo prazo para a devolução do empréstimo: ");
            
        String mensagem = emp.alterarEmprestimo(cliente, dataLoc, novoPrazo, novaData, emf);
        Main.imprimir(mensagem); 
    }
    

    //Multa
    public Multa visualizarMultas(String cpf, EntityManagerFactory emf) {
        List<Multa> multas =  multa.visualizarMultas(cpf, emf);
        Multa multaEncontrada = null;

        for(Multa m: multas){
            System.out.println("-Id: " + m.getId() + " -Descrição: " + m.getNome() + " -Valor: " + m.getValor());
        }

        if (multas.size() != 0) {
            int escolha = Integer.parseInt(Main.askQuestion("Digite o Id da multa que deseja: "));
            multaEncontrada = multa.escolherMulta(escolha, multas);
            return multaEncontrada;
        }
        else {
            return multaEncontrada;
        }
    }


    public void addMulta(EntityManagerFactory emf){
        Main.imprimir("Cadastrando Multa");
    
        Emprestimo emp = buscarEmprestimo(emf);
        String descricao = Main.askQuestion("Digite a descrição da multa: ");
        double valor  = Main.askQuestionDouble("Digite o valor da Multa: ");
    
        String mensagem = multa.addMulta(descricao, valor, emp, emf);
        Main.imprimir(mensagem); 
    }


    public void removerMulta(EntityManagerFactory emf){
        Main.imprimir("Remoção da Multa");
        String cpf = Main.askQuestion("Digite o CPF do Cliente: ");
        Multa mul = visualizarMultas(cpf, emf);
        
        String mensagem = multa.removerMulta(mul, emf);
        Main.imprimir(mensagem); 
    }


    public void alterarMulta(EntityManagerFactory emf){
        Main.imprimir("Alterar Multa");
        String cpf = Main.askQuestion("Digite o CPF do Cliente: ");
        Multa mul = visualizarMultas(cpf, emf);

        String descricao = Main.askQuestion("Digite a descrição atualizada: ");
        double valor = Main.askQuestionDouble("Digite o valor atualizado: ");


        String mensagem = multa.alterarMulta(mul, descricao, valor, emf);
        Main.imprimir(mensagem); 
    }
    

}
 