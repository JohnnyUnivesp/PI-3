<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="tag"%>

<!DOCTYPE html>
<html lang="pt-br">
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta name="description" content="" />
        <meta name="author" content="" />
        <title>E-Commerce - Categorias
        </title>
        <link rel="icon" type="image/x-icon" href="assets/img/favicon.ico" />
        <!-- Font Awesome icons (free version)-->
        <script src="https://use.fontawesome.com/releases/v5.15.1/js/all.js" crossorigin="anonymous"></script>
        <!-- Google fonts-->
        <link href="https://fonts.googleapis.com/css?family=Montserrat:400,700" rel="stylesheet" type="text/css" />
        <link href="https://fonts.googleapis.com/css?family=Droid+Serif:400,700,400italic,700italic" rel="stylesheet" type="text/css" />
        <link href="https://fonts.googleapis.com/css?family=Roboto+Slab:400,100,300,700" rel="stylesheet" type="text/css" />
        <!-- Core theme CSS (includes Bootstrap)-->
        <link href="css/styles.css" rel="stylesheet" />
    </head>
    <body id="page-top">
        <!-- Navigation-->
        <tag:menu-superior></tag:menu-superior>
        <!-- Masthead-->
        <header class="masthead" id="login">
            <div class="container">
               
            </div>
        </header>
        <!-- categorias-->
        <section class="page-section" id="categorias">
            <div class="container">
                <div class="text-center">
                    <h2 class="section-heading text-uppercase mb-3">Categorias</h2>
                </div>
                <button title="Editar" onclick="window.location.href='formcategoria'" class="btn btn-success mb-3"  type="button">
                    <i class="fas fa-plus mr 1"></i> 
                    Nova Categoria  
                </button>
                <table class="table table-hover">
                    <thead>
                      <tr>
                         <th scope="col" class="d-none">#</th> <!--  <th scope="col">#</th> Oculta o cabe�alho da coluna de ID -->
                        <th scope="col">Nome</th>
                        <th scope="col">A��es</th>
                      </tr>
                    </thead>
                    <tbody>
                   		
                   		<c:forEach var="categoria" items="${categorias}">
	                   		<tr>
		                        <th scope="row" class="d-none">${categoria.id}</th> <!-- <th scope="row">${categoria.id}</th> Oculta o valor do ID -->
		                        <td>${categoria.nome}</td>
		                        <td>
		                            <button title="Editar" onclick="window.location.href='formcategoria?categoria.id=${categoria.id}'" class="btn btn-info" type="button">
		                                <i class="fas fa-edit"></i>   
		                            </button>
		                            <button title="Excluir" onclick="window.location.href='deletacategoria/${categoria.id}'" class="btn btn-danger" type="button">
		                                <i class="fas fa-trash"></i>    
		                            </button>
		                        </td>
	                      	</tr>
                   		</c:forEach>
                     
                    </tbody>
                  </table>
                  
                  <style> /* Remove as bordas das linhas */
   					.table tr {
       					border-top: none !important; /* Remove a borda superior */
       					border-bottom: none !important; /* Remove a borda inferior */
   					}

   					.table th, .table td {
       					border: none !important; /* Remove todas as bordas das c�lulas */
   					}
				</style>
            </div>
        </section>
    
       <tag:footer></tag:footer>
       
        <!-- Bootstrap core JS-->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js"></script>
        <!-- Third party plugin JS-->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-easing/1.4.1/jquery.easing.min.js"></script>
        <!-- Contact form JS-->
        <script src="assets/mail/jqBootstrapValidation.js"></script>
        <script src="assets/mail/contact_me.js"></script>
        <!-- Core theme JS-->
        <script src="js/scripts.js"></script>
        <script>

        </script>
    </body>
</html>
