package br.com.sparkcommerce.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.sparkcommerce.dao.MovimentacaoEstoqueDAO;
import br.com.sparkcommerce.dao.ProdutoDAO;
import br.com.sparkcommerce.interceptors.SomenteAdmin;
import br.com.sparkcommerce.model.MovimentacaoEstoque;
import br.com.sparkcommerce.model.Produto;
import br.com.sparkcommerce.model.Usuario;

@Controller
@Path("estoque")
public class EstoqueController {

    @Inject private ProdutoDAO produtoDAO;
    @Inject private Result result;
    @Inject private HttpSession session;
    @Inject private MovimentacaoEstoqueDAO movimentacaoEstoqueDAO;

    @Get("")@SomenteAdmin
    public void lista() {
        result.include("produtos", produtoDAO.listarTodosComEstoque());
    }

    @Post("ajustar")
    public void ajustar(Long produtoId, String tipo, int quantidade, String motivo) {
    	Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
        Produto produto = produtoDAO.selectPorIdLong(produtoId);
        int quantidadeFinal = tipo.equals("SAIDA") ? -quantidade : quantidade;
        try {
            produtoDAO.atualizarEstoque(produto, quantidadeFinal, usuario, motivo);
            result.include("success", "Estoque atualizado com sucesso");
        } catch (Exception e) {
            result.include("error", e.getMessage());
        }
        result.redirectTo(this).lista();
    }

    @Get("exportar")
    public void exportarHistorico(HttpServletResponse response) throws IOException {
        // Configuração do response
        response.setContentType("text/csv; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=historico_estoque.csv");
        
        // Busca todos os registros
        List<MovimentacaoEstoque> historico = movimentacaoEstoqueDAO.buscarHistoricoCompleto();
        
        // Escreve o CSV
        try (PrintWriter writer = response.getWriter()) {
            // Cabeçalho
            writer.println("Data;Produto;Categoria;Tipo;Quantidade;Usuario;Motivo");
            
            // Dados
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            
            for (MovimentacaoEstoque mov : historico) {
            	String linha = String.join(";",
            	        sdf.format(mov.getDataMovimentacao()),
            	        mov.getProduto() != null ? mov.getProduto().getNome() : "N/A",
    	        		mov.getProduto() != null ? mov.getProduto().getCategoria().getNome() : "N/A",
            	        mov.getTipo(),
            	        String.valueOf(mov.getQuantidade()),
            	        mov.getUsuario() != null ? mov.getUsuario().getNome() : "N/A",
            	        "\"" + mov.getMotivo().replace("\"", "\"\"") + "\""
            	    );
                writer.println(linha);
            }
        }
    }
}