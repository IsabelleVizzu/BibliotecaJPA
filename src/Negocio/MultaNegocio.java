package Negocio;

import java.util.List;

import Dados.MultaDados;
import entity.Emprestimo;
import entity.Multa;
import jakarta.persistence.EntityManagerFactory;

public class MultaNegocio {
    private MultaDados multa = new MultaDados();
    

    public MultaNegocio(){}


    public Multa escolherMulta(int id, List<Multa> multas){
        Multa multaEncontrada = null;
        if (!multas.isEmpty()) {
            for (Multa m : multas) {
                if (m.getId() == id) {
                    multaEncontrada = m;
                    break;
                }
            }
        }else{
            return multaEncontrada;
        }
        return multaEncontrada;
    }


    public List<Multa> visualizarMultas(String cpf, EntityManagerFactory emf){
        return multa.visualizarMultas(cpf, emf);
    }


    public String addMulta(String descricao, double valor, Emprestimo emp, EntityManagerFactory emf){
        return multa.addMulta(descricao, valor, emp, emf);
    }


    public String removerMulta(Multa multa, EntityManagerFactory emf){
        return this.multa.removerMulta(multa, emf);
    }


    public String alterarMulta(Multa multa, String descricao, double valor, EntityManagerFactory emf){
        return this.multa.alterarMulta(multa, descricao, valor, emf);
    }

    
}