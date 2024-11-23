package entity;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.Date;

@Entity
public class Emprestimo {

    private String dataDeLocacao;
    private String dataDeDevolucao;
    private String prazo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recepcionista_cpf", nullable = true) 
    private Recepcionista recepcionista;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(mappedBy = "emprestimo")
    private ArrayList<Livro> livros;

    @OneToMany(mappedBy = "emprestimo")
    private ArrayList<Multa> multas;

    
    public Emprestimo() {
    	
    }
    
    public Emprestimo(String dataDeLocacao, String dataDeDevolucao, String prazo,
                      Cliente cliente, Recepcionista recepcionista, ArrayList<Livro> livros, ArrayList<Multa> multas) {
        this.dataDeLocacao = dataDeLocacao;
        this.dataDeDevolucao = dataDeDevolucao;
        this.prazo = prazo;
        this.cliente = cliente;
        this.recepcionista = recepcionista;
        this.livros = livros != null ? livros : new ArrayList<>();
        this.multas = multas != null ? multas : new ArrayList<>();
    }

    // Getters and setters
    public String getDataDeLocacao() {
        return dataDeLocacao;
    }

    public void setDataDeLocacao(String dataDeLocacao) {
        this.dataDeLocacao = dataDeLocacao;
    }

    public String getDataDeDevolucao() {
        return dataDeDevolucao;
    }

    public void setDataDeDevolucao(String dataDeDevolucao) {
        this.dataDeDevolucao = dataDeDevolucao;
    }

    public String getPrazo() {
        return prazo;
    }

    public void setPrazo(String prazo) {
        this.prazo = prazo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Recepcionista getRecepcionista() {
        return recepcionista;
    }

    public void setRecepcionista(Recepcionista recepcionista) {
        this.recepcionista = recepcionista;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Livro> getLivros() {
        return livros;
    }

    public void setLivros(ArrayList<Livro> livros) {
        this.livros = livros;
    }

    public ArrayList<Multa> getMultas() {
        return multas;
    }

    public void setMultas(ArrayList<Multa> multas) {
        this.multas = multas;
    }
}
