package br.com.sparkcommerce.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.olimposistema.aipa.dao.DAO;
import br.com.olimposistema.aipa.dao.IdInvalidoException;
import br.com.sparkcommerce.model.Categoria;
import br.com.sparkcommerce.model.MovimentacaoEstoque;
import br.com.sparkcommerce.model.Produto;
import br.com.sparkcommerce.model.Usuario;

@RequestScoped
public class ProdutoDAO extends DAO<Produto> {
	
	@Deprecated public ProdutoDAO() {super(null,null);}
	
	@Inject
	public ProdutoDAO(EntityManager em) {
		super(em, Produto.class);
	}
	
	public List<Produto> buscaTodosOsProdutosOrdenadoPeloNome(){
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Produto> q = cb.createQuery(Produto.class);
		
		Root<Produto> r = q.from(Produto.class);
		q.select(r)
		.orderBy(cb.desc(r.get("nome")));
		
		TypedQuery<Produto> query = em.createQuery(q);
		List<Produto> produtos = query.getResultList();
		return produtos;
	}

	public List<Produto> buscaPorCategoriaEspecifica() {
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Produto> q = cb.createQuery(Produto.class);
		
		Root<Produto> r = q.from(Produto.class);
			Join<Produto, Categoria> jc = r.join("categoria", JoinType.INNER);
		
		q.select(r)
		.where(
				cb.and(
						cb.like(cb.lower(r.get("nome")), "%nome%")),
						cb.equal(jc.get("id"), 3)				
				);
				
		
		TypedQuery<Produto> query = em.createQuery(q);
		List<Produto> produtos = query.getResultList();
		return produtos;
		
	}
	
	public Long totalProdutos() {
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> q = cb.createQuery(Long.class);
		
		Root<Produto> r = q.from(Produto.class);
		q.multiselect(cb.count(r));
		
		TypedQuery<Long> query = em.createQuery(q);
		Long total = query.getSingleResult();
		return total;
		
	}
	
	public Produto selectPorIdLong(Long id) {
	    return em.find(Produto.class, id.intValue());
	}
	
	public Produto selectPorId(Produto produto) {
	    return em.find(Produto.class, produto.getId());
	}
	
	public void delete(Produto produto) {
		if(produto!= null && produto.getId() < 1) {
			throw new IdInvalidoException("Não foi Possível deletar pois o id é 0 ou inválido!  classe do registro:"+ produto.getClass().getSimpleName() + " id do registro: "+produto.getId());
		}
		produto = selectPorId(produto.getId());
		em.remove(produto);
	}
	
	public List<Produto> buscarProdutosAleatoriosPorCategoria(Long categoriaId) {
		int limite = 4;
		int intCategoriaId = categoriaId.intValue();
	    return em.createQuery("SELECT p FROM Produto p WHERE p.categoria.id = :categoriaId ORDER BY RANDOM()", Produto.class)
	             .setParameter("categoriaId", intCategoriaId)
	             .setMaxResults(limite)
	             .getResultList();
	}
	
	public void atualizarEstoque(Produto produto, int quantidade, Usuario usuario, String motivo) {
	    int novoEstoque = produto.getQuantidadeEstoque() + quantidade;
	    
	    if(novoEstoque < 0) {
	        throw new RuntimeException("Estoque não pode ser negativo");
	    }
	    
	    produto.setQuantidadeEstoque(novoEstoque);
	    atualizar(produto);
	    
	    MovimentacaoEstoque mov = new MovimentacaoEstoque();
	    mov.setProduto(produto);
	    mov.setDataMovimentacao(new Date());
	    mov.setCreated(new GregorianCalendar());
	    mov.setUpdated(new GregorianCalendar());
	    mov.setQuantidade(quantidade);
	    mov.setTipo(quantidade > 0 ? "ENTRADA" : "SAIDA");
	    mov.setMotivo(motivo);
	    mov.setUsuario(usuario);
	    mov.setAtivo(true);
	    mov.getId();
	    
	    em.persist(mov);
	}
	
	private void atualizar(Produto produto) {
		// TODO Auto-generated method stub
		
	}

	public List<Produto> listarTodosComEstoque() {
	    return em.createQuery(
	        "SELECT p FROM Produto p " +
	        "LEFT JOIN FETCH p.categoria " +
	        "ORDER BY p.nome", Produto.class)
	        .getResultList();
	}

	// Método para buscar histórico (se necessário)
	public List<MovimentacaoEstoque> buscarHistoricoPorProduto(Long produtoId) {
	    return em.createQuery(
	        "SELECT m FROM MovimentacaoEstoque m " +
    		"JOIN FETCH m.usuario u " +
	        "WHERE m.produto.id = :produtoId " +
	        "ORDER BY m.dataMovimentacao DESC", MovimentacaoEstoque.class)
	        .setParameter("produtoId", produtoId)
	        .getResultList();
	}
	@Override
	public List<Produto> filter(Produto filtro) {
	    CriteriaBuilder cb = em.getCriteriaBuilder();
	    CriteriaQuery<Produto> q = cb.createQuery(Produto.class);
	    Root<Produto> r = q.from(Produto.class);

	    List<Predicate> predicates = new ArrayList<>();

	    // Campos específicos
	    if (filtro.getNome() != null) {
	        predicates.add(cb.like(cb.lower(r.get("nome")), "%" + filtro.getNome().toLowerCase() + "%"));
	    }

	    if (filtro.getEstoqueminimo() > 0) { // Só considera se > 0
	        predicates.add(cb.equal(r.get("estoqueMinimo"), filtro.getEstoqueminimo()));
	    }

	    if (filtro.getCategoria() != null) {
	        predicates.add(cb.equal(r.get("categoria"), filtro.getCategoria()));
	    }

	    q.where(predicates.toArray(new Predicate[0]));
	    return em.createQuery(q).getResultList();
	}
}
