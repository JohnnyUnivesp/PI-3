package br.com.sparkcommerce.dao;

import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import br.com.olimposistema.aipa.dao.DAO;
import br.com.sparkcommerce.model.MovimentacaoEstoque;

@RequestScoped
public class MovimentacaoEstoqueDAO extends DAO<MovimentacaoEstoque> {

    @Deprecated
    public MovimentacaoEstoqueDAO() { super(null, null); }

    @Inject
    public MovimentacaoEstoqueDAO(EntityManager em) {
        super(em, MovimentacaoEstoque.class);
    }

    public List<MovimentacaoEstoque> buscarHistoricoCompleto() {
    	return em.createQuery(
    	        "SELECT m FROM MovimentacaoEstoque m " +
    	        "LEFT JOIN FETCH m.produto p " +        // Carrega produto
    	        "LEFT JOIN FETCH m.usuario u " +        // Carrega usuário
    	        "ORDER BY m.dataMovimentacao DESC", 
    	        MovimentacaoEstoque.class)
    	        .getResultList();
    }

    public List<MovimentacaoEstoque> buscarPorProduto(Long produtoId) {
        return em.createQuery(
            "SELECT m FROM MovimentacaoEstoque m " +
            "WHERE m.produto.id = :produtoId " +
            "ORDER BY m.dataMovimentacao DESC", MovimentacaoEstoque.class)
            .setParameter("produtoId", produtoId)
            .getResultList();
    }
}