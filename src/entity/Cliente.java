package entity;

import java.util.ArrayList;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Cliente {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String nome;
	private String cliente_cpf;

	@OneToMany(mappedBy = "cliente")
	private ArrayList<Emprestimo> emprestimo;
	
	public Cliente() {
		
	}
	

    public Cliente(String nome, String cliente_cpf){
		this.nome = nome;
		this.cliente_cpf = cliente_cpf;
		this.emprestimo =  new ArrayList<Emprestimo>();
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

	public String getCliente_cpf() {
		return cliente_cpf;
	}

	public void setCliente_cpf(String cliente_cpf) {
		this.cliente_cpf = cliente_cpf;
	}

	public ArrayList<Emprestimo> getEmprestimo() {
		return emprestimo;
	}

	public void setEmprestimo(ArrayList<Emprestimo> emprestimo) {
		this.emprestimo = emprestimo;
	}


}

