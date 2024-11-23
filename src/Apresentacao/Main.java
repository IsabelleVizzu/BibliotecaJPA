package Apresentacao;

import java.util.List;
import java.util.Scanner;

import entity.Recepcionista;
import Dados.*;
import Negocio.*;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;


	public class Main {

	    public static void main(String[] args) {


	    EntityManagerFactory emf = Persistence.createEntityManagerFactory("bibliotecaPU");
		EntityManager em = emf.createEntityManager();

	    RecepcionistaApresentacao  recep = new RecepcionistaApresentacao();
        Recepcionista recepcionistaLogada = null;


	    while (recepcionistaLogada == null) {
            imprimir("Bem-vindo ao sistema da biblioteca.");
            imprimir("Por favor, faça login ou cadastre-se.");
            imprimir("1 - Login");
            imprimir("2 - Cadastro");
            
            int opcao = Integer.parseInt(askQuestion("Digite o número da opção desejada:"));
            
            if (opcao == 1) {
                // Login
                String cpf = askQuestion("Digite seu CPF para login: ");
                recepcionistaLogada = recep.buscarRecepcionista(cpf, emf);
                if (recepcionistaLogada != null) {
                    imprimir("Login realizado com sucesso! Bem-vindo, " + recepcionistaLogada.getNome());
                } else {
                    imprimir("CPF não encontrado. Tente novamente ou cadastre-se.");
                }
            } else if (opcao == 2) {
                recepcionistaLogada = recep.addRecepcionista(emf);
            } else {
                imprimir("Opção inválida. Tente novamente.");
            }
        }


	    menu: while (true) {
	        imprimir("Bem-vindo recepcionista ao sistema da biblioteca.");
	        imprimir("Escolha uma opção:");
	        imprimir("0 - Sair");
	        imprimir("1 - Cadastrar livro");
	        imprimir("2 - Remover livro");
	        imprimir("3 - Alterar livro");
	        imprimir("4 - Remover recepcionista");
	        imprimir("5 - Alterar dados de recepcionista");
	        imprimir("6 - Cadastrar outro(a) recepcionista");
	        imprimir("7 - Cadastrar Cliente");
	        imprimir("8 - Remover Cliente");
	        imprimir("9 - Alterar dados do Cliente");
	        imprimir("10 - Registrar Empréstimo");
	        imprimir("11 - Cadastrar Multa");
	        imprimir("12 - Remover Multa");
	        imprimir("13 - Alterar Multa");
	        imprimir("14 - Alterar Empréstimo");
	        imprimir("15 - Remover Empréstimo");
	        imprimir("16 - Buscar por Empréstimo");
	        imprimir("17 - Buscar por Livro");
	        imprimir("18 - Buscar por Cliente");
	        imprimir("19 - Buscar por Multa");
	        imprimir("20 - Buscar por Recepcionista");    

	        
	        int opcao = Integer.parseInt(askQuestion("Digite o número da opção desejada:"));
	        
	        switch (opcao) {
		        case 17:
	            	imprimir("Buscando Livro");
	                imprimir(recep.buscarLivroPorNome(askQuestion("Digite o nome do livro que deseja encontrar: "), emf)!= null ? "Livro encontrado" : "Livro não encontrado");
	                break;
		        case 18:
	            	imprimir("Buscando Cliente");
	                imprimir(recep.buscarCliente(askQuestion("Digite o cpf do cliente que deseja encontrar: "), emf)!= null ? "Cliente encontrado" : "Cliente não encontrado"); 
	                break;
		        case 19:
	            	imprimir("Buscando Multa");
	                imprimir(recep.visualizarMultas(askQuestion("Digite o cpf do cliente da multa que deseja encontrar: "), emf)!= null ? "Multa encontrada" : "Multa não encontrada"); 
	                break;
		        case 20:
	            	imprimir("Buscando Recepcionista");
	                imprimir(recep.buscarRecepcionista(askQuestion("Digite o cpf da recepcionista que deseja encontrar: "), emf)!= null ? "Recepcionista encontrado" : "Recepcionista não encontrado");  
	                break;
		        case 14:
	                recep.alterarEmprestimo(emf);  
	                break;
	            case 15:
	                recep.removerEmprestimo(emf);
	                break;
	            case 16:
	            	imprimir("Buscando Empréstimo");
	            	imprimir(recep.buscarEmprestimo(emf) != null ? "Empréstimo encontrado" : "Empréstimo não encontrado");
	                break;
                case 1:
	                recep.addLivro(emf);  
	                break;
	            case 2:
	                recep.removerLivro(emf);
	                break;
	            case 3:
	                recep.alterarLivro(emf);
	                break;
	            case 4:
	                recep.removerRecepcionista(emf);
	                break; 
	            case 5:
	                recep.alterarRecepcionista(emf);
	                break;
	            case 6:
	                recep.addRecepcionista(emf);
	                break;
	            case 7:
	                recep.addCliente(emf);
	                break;
	            case 8:
	                recep.removerCliente(emf);
	                break;
	            case 9:
	                recep.alterarDadosCliente(emf);
	                break;
	            case 10:
	                recep.cadastrarEmprestimo(emf, recepcionistaLogada);
	                break;
	            case 11:
	                recep.addMulta(emf);
	                break;
	            case 12:
	                recep.removerMulta(emf);
	                break;
	            case 13:
	                recep.alterarMulta(emf);
	                break;
	            case 0:
	                System.out.println("Saindo do sistema...");
	                emf.close(); 
	                break menu;
	            default:
	                imprimir("Opção inválida. Tente novamente.");
	        }
	    }
	    
        imprimir("Obrigado por usar o sistema da biblioteca.");

    }

	    
	public static String askQuestion(String question) {
	    Scanner scanner = new Scanner(System.in);
	    String response = "";
	    while (true) {
	        try {
	            System.out.print(question + " ");
	            response = scanner.nextLine();
	            break;
	        } catch (IllegalArgumentException e) {
	            System.out.println("Dado inválido");
	        }
	    }
	    return response;
	}
	
	
	public static double askQuestionDouble(String question) {
	    Scanner scanner = new Scanner(System.in);
		double valor = 0;
	    while (true) {
	        try {
	            System.out.print(question + " ");
	            valor = scanner.nextDouble();
	            break;
	        } catch (IllegalArgumentException e) {
	            System.out.println("Dado inválido");
	        }
	    }
	    return valor;
	}
	
	
	public static void imprimir (String mensagem) {
	    System.out.println(mensagem);
	}
}