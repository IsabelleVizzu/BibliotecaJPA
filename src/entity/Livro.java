package entity;

import jakarta.persistence.*;

@Entity
public class Livro {

    @Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

    private String nome;

    private String autor;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "emprestimo_associado", nullable = true)
    private Emprestimo emprestimo;


    public Livro(){}


    public Livro(String nome, String autor){
        this.nome = nome;
        this.autor = autor;
        this.emprestimo = null;
    }
    

    public String getNome(){return nome;}

    public String getAutor(){return autor;}

    public Emprestimo getEmprestimo(){return emprestimo;}

    public void setNome(String nome){ this.nome = nome;}

    public void setAutor(String autor){ this.autor = autor;}

    public void setEmprestimo(Emprestimo emp){ this.emprestimo = emp;}

    
}