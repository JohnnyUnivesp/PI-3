<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
        <h2 class="mb-4">Relatório de Vendas</h2>

        <form action="<c:url value="relatorio" />" method="post" class="mb-5 card card-body bg-light">
            <div class="form-row align-items-center">
                <div class="col-md-4 mb-2">
                    <label>Início</label>
                    <input type="date" class="form-control" value="${inicio}" name="dataInicio" required>
                </div>
                <div class="col-md-4 mb-2">
                    <label>Fim</label>
                    <input type="date" class="form-control" value="${fim}" name="dataFim" required>
                </div>
                <div class="col-md-4 mb-2">
                    <button type="submit" class="btn btn-primary btn-block mt-4">
                        <i class="fas fa-file-alt mr-2"></i>Gerar Relatório
                    </button>
                </div>
            </div>
        </form>

        <c:if test="${not empty relatorio}">
            <div class="mb-5">
                <h4 class="mb-3">Resumo do Período</h4>
                <div class="row">
                    <div class="col-md-3">
                        <div class="card text-white bg-dark mb-3">
                            <div class="card-body">
                                <h5 class="card-title">Período</h5>
                                <p class="card-text">
                                    ${inicioFormatado} - 
                                    ${fimFormatado}
                                </p>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="card text-white bg-info mb-3">
                            <div class="card-body">
                                <h5 class="card-title">Total Faturado</h5>
                                <p class="card-text">${valorFormatado}</p>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="card text-white bg-success mb-3">
                            <div class="card-body">
                                <h5 class="card-title">Pedidos</h5>
                                <p class="card-text">${totalPedidos}</p>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="card text-white bg-warning mb-3">
                            <div class="card-body">
                                <h5 class="card-title">Ticket Médio</h5>
                                <p class="card-text">
                                    <fmt:formatNumber value="${relatorio.ticketMedio}" type="currency" currencySymbol="R$"/>
                                </p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Seção de Produtos Mais Vendidos -->
            <div class="row">
                <div class="col-md-6 mb-4">
                    <h4 class="mb-3">Produtos Mais Vendidos</h4>
                    <div class="table-responsive">
                        <table class="table table-bordered table-hover">
                            <thead class="thead-dark">
                                <tr>
                                    <th>Produto</th>
                                    <th class="text-center">Quantidade</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="produto" items="${relatorio.rankingProdutos}">
                                    <tr>
                                        <td>${produto.key}</td>
                                        <td class="text-center">${produto.value}</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>

                <!-- Seção de Categorias -->
                <div class="col-md-6 mb-4">
                    <h4 class="mb-3">Faturamento por Categoria</h4>
                    <div class="table-responsive">
                        <table class="table table-bordered table-hover">
                            <thead class="thead-dark">
                                <tr>
                                    <th>Categoria</th>
                                    <th class="text-center">Total</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="categoria" items="${relatorio.vendasPorCategoria}">
                                    <tr>
                                        <td>${categoria.key}</td>
                                        <td class="text-center">
                                            <fmt:formatNumber value="${categoria.value}" 
                                                type="currency" 
                                                currencySymbol="R$"
                                                minFractionDigits="2"/>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </c:if>

        <c:if test="${not empty param.dataInicio}">
		    <c:choose>
		        <c:when test="${not empty relatorio}">
		            <!-- Conteúdo do relatório aqui -->
		        </c:when>
		        <c:otherwise>
		            <div class="alert alert-warning text-center">
		                Nenhum dado encontrado para o período informado
		            </div>
		        </c:otherwise>
		    </c:choose>
		</c:if>
    </div>

    <tag:footer />

    <!-- Scripts necessários -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js"></script>
    <script src="js/scripts.js"></script>
</body>
</html>