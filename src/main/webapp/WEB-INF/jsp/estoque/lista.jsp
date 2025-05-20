<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="tag" %>

<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Relatório de Vendas</title>
    <link rel="icon" type="image/x-icon" href="assets/img/favicon.ico" />
    <!-- Font Awesome icons (free version)-->
    <script src="https://use.fontawesome.com/releases/v5.15.1/js/all.js" crossorigin="anonymous"></script>
    <!-- Google fonts-->
    <link href="https://fonts.googleapis.com/css?family=Montserrat:400,700" rel="stylesheet" type="text/css" />
    <link href="https://fonts.googleapis.com/css?family=Droid+Serif:400,700,400italic,700italic" rel="stylesheet"
        type="text/css" />
    <link href="https://fonts.googleapis.com/css?family=Roboto+Slab:400,100,300,700" rel="stylesheet" type="text/css" />
    <!-- Core theme CSS (includes Bootstrap)-->
    <link href="css/styles.css" rel="stylesheet" />
</head>
<body>
	<!-- Navigation-->
    <tag:menu-superior></tag:menu-superior>
    <!-- Masthead-->
    <header class="masthead" id="login">
        <div class="container"></div>
    </header>
	<div class="container mt-4">
	    <!-- Mensagens de Feedback -->
	    <c:if test="${not empty success}">
	        <div class="alert alert-success alert-dismissible fade show">
	            ${success}
	            <button type="button" class="close" data-dismiss="alert">&times;</button>
	        </div>
	    </c:if>
	    <c:if test="${not empty error}">
	        <div class="alert alert-danger alert-dismissible fade show">
	            ${error}
	            <button type="button" class="close" data-dismiss="alert">&times;</button>
	        </div>
	    </c:if>
	
	    <div class="card shadow">
	        <div class="card-header bg-primary text-white">
	            <h5 class="mb-0">
	                <i class="fas fa-boxes mr-2"></i>Controle de Estoque
	                <button class="btn btn-sm btn-light float-right" onclick="location.reload()">
	                    <i class="fas fa-sync-alt"></i>
	                </button>
	            </h5>
	            <input type="text" id="searchInput" class="form-control mt-3" 
	                   placeholder="Digite para filtrar por produto ou categoria...">
	        </div>
	
	        <div class="table-responsive">
	            <table class="table table-hover mb-0">
	                <thead class="thead-dark">
	                    <tr>
	                        <th>Produto</th>
	                        <th>Categoria</th>
	                        <th class="text-center">Estoque Atual</th>
	                        <th class="text-center">Estoque Mínimo</th>
	                        <th class="text-center">Ações</th>
	                    </tr>
	                </thead>
	                <tbody>
	                    <c:forEach items="${produtos}" var="p">
	                        <tr>
	                            <td>${p.nome}</td>
	                            <td>${p.categoria.nome}</td>
	                            <td class="text-center">
	                                <span class="font-weight-bold ${p.quantidadeEstoque < p.estoqueminimo ? 'text-danger' : 'text-success'}">
	                                    ${p.quantidadeEstoque}
	                                </span>
	                                <c:if test="${p.quantidadeEstoque < p.estoqueminimo}">
	                                    <span class="badge badge-danger ml-2">ESTOQUE BAIXO</span>
	                                </c:if>
	                            </td>
	                            <td class="text-center">${p.estoqueminimo}</td>
	                            <td class="text-center">
	                                <button class="btn btn-sm btn-outline-primary ajustar-btn"
	                                    data-toggle="modal" 
	                                    data-target="#ajusteModal"
	                                    data-produto-id="${p.id}"
	                                    data-produto-nome="${p.nome}">
	                                    <i class="fas fa-edit mr-1"></i>Ajustar
	                                </button>
	                            </td>
	                        </tr>
	                    </c:forEach>
	                </tbody>
	            </table>
	        </div>
	        
	        <div class="card-footer text-right">
	            <a href="<c:url value='estoque/exportar'/>" 
	               class="btn btn-secondary">
	                <i class="fas fa-file-csv mr-2"></i>Exportar CSV
	            </a>
	        </div>
	    </div>
	</div>
	
	<!-- Modal de Ajuste de Estoque -->
	<div class="modal fade" id="ajusteModal" tabindex="-1">
	    <div class="modal-dialog modal-dialog-centered">
	        <div class="modal-content">
	            <form action="<c:url value='estoque/ajustar'/>" method="post">
	                <div class="modal-header bg-primary text-white">
	                    <h5 class="modal-title">
	                        <i class="fas fa-clipboard-list mr-2"></i>Ajuste de Estoque
	                    </h5>
	                    <button type="button" class="close text-white" data-dismiss="modal">&times;</button>
	                </div>
	                <div class="modal-body">
	                    <input type="hidden" name="produtoId" id="modalProdutoId">
	                    
	                    <div class="form-group">
	                        <label class="font-weight-bold">Produto</label>
	                        <input type="text" id="modalProdutoNome" 
	                               class="form-control-plaintext" readonly>
	                    </div>
	                    
	                    <div class="form-group">
	                        <label for="tipoMovimentacao" class="font-weight-bold">Tipo de Movimentação</label>
	                        <div class="btn-group btn-group-toggle w-100" data-toggle="buttons">
	                            <label class="btn btn-outline-success active">
	                                <input type="radio" name="tipo" id="tipoMovimentacao" value="ENTRADA" onchange="toggleQuantidade(this.value)" checked> Entrada
	                            </label>
	                            <label class="btn btn-outline-danger">
	                                <input type="radio" name="tipo" id="tipoMovimentacao" value="SAIDA" onchange="toggleQuantidade(this.value)"> Saída
	                            </label>
	                        </div>
	                    </div>
	                    
	                    <div class="form-group">
	                        <label for="quantidade" class="font-weight-bold">Quantidade</label>
	                        <input type="number" id="quantidade" name="quantidade" 
	                               class="form-control" 
	                               min="1" 
	                               step="1" 
	                               required>
                            <small class="form-text text-muted" id="quantidadeHelp">
					           Digite a quantidade positiva. O sistema converterá automaticamente para negativo se for saída.
					      	</small>
	                    </div>
	                    
	                    <div class="form-group">
	                        <label class="font-weight-bold">Motivo</label>
	                        <textarea name="motivo" class="form-control" 
	                                  rows="3" 
	                                  placeholder="Ex: Reposição de estoque, Venda para cliente..."
	                                  required></textarea>
	                    </div>
	                </div>
	                <div class="modal-footer">
	                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancelar</button>
	                    <button type="submit" class="btn btn-primary">
	                        <i class="fas fa-check mr-1"></i>Confirmar Ajuste
	                    </button>
	                </div>
	            </form>
	        </div>
	    </div>
	</div>
	<tag:footer />
	<script>
	function toggleQuantidade(tipo) {
	    const quantidadeInput = document.getElementById('quantidade');
	    if(tipo === 'SAIDA') {
	        quantidadeInput.previousElementSibling.textContent = 'Quantidade (será convertida para negativo):';
	    } else {
	        quantidadeInput.previousElementSibling.textContent = 'Quantidade:';
	    }
	}
	</script>
	<script>
	$(document).ready(function() {
	    // Configuração do Modal
	    $('#ajusteModal').on('show.bs.modal', function(event) {
	        const button = $(event.relatedTarget);
	        $('#modalProdutoId').val(button.data('produto-id'));
	        $('#modalProdutoNome').val(button.data('produto-nome'));
	    });
	
	    // Filtro de Busca
	    $('#searchInput').on('keyup', function() {
	        const filter = $(this).val().toUpperCase();
	        $('tbody tr').each(function() {
	            const text = $(this).text().toUpperCase();
	            $(this).toggle(text.indexOf(filter) > -1);
	        });
	    });
	});
	</script>
</body>
</html>
