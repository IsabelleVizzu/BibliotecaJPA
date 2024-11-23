package entity;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import Apresentacao.Main;
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

@Entity
public class Recepcionista {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	private String nome;

	private String recepcionista_cpf;

	private String email;

	@OneToMany (mappedBy = "recepcionista")
	private List<Emprestimo> emprestimo;	
	
	public Recepcionista(){}
	
	
	public Recepcionista(String nome, String recepcionista_cpf, String email) {
        this.nome = nome;
        this.recepcionista_cpf = recepcionista_cpf;
        this.email = email;
        this.emprestimo = new ArrayList<Emprestimo>();
    }

	
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public String getRecepcionista_cpf() {
		return recepcionista_cpf;
	}


	public void setRecepcionista_cpf(String recepcionista_cpf) {
		this.recepcionista_cpf = recepcionista_cpf;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public List<Emprestimo> getEmprestimo() {
		return emprestimo;
	}


	public void setEmprestimo(ArrayList<Emprestimo> emprestimo) {
		this.emprestimo = emprestimo;
	}
	
	
}
