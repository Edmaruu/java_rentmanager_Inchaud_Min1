<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
<%@include file="/WEB-INF/views/common/head.jsp"%>
<body class="hold-transition skin-blue sidebar-mini">

<div class="wrapper">

    <%@ include file="/WEB-INF/views/common/header.jsp" %>
    <!-- Left side column. contains the logo and sidebar -->
    <%@ include file="/WEB-INF/views/common/sidebar.jsp" %>

    <!-- Content Wrapper. Contains page content -->


    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>
                Utilisateurs
            </h1>
        </section>

        <!-- Main content -->
        <section class="content">
            <div class="row">
                <div class="col-md-12">
                    <!-- Horizontal Form -->
                    <div class="box">
                        <!-- form start -->
                        <form class="form-horizontal" method="post" >
                            <div class="box-body">

                                <c:if test="${not empty message}">
                                    <div class="alert alert-danger">${message}</div>
                                </c:if>

                                <div class="form-group">
                                    <label for="last_name" class="col-sm-2 control-label">Nom</label>

                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" id="last_name" name="last_name" placeholder="Nom">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="first_name" class="col-sm-2 control-label">Prenom</label>

                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" id="first_name" name="first_name" placeholder="Prenom">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="email" class="col-sm-2 control-label">Email</label>

                                    <div class="col-sm-10">
                                        <input type="email" class="form-control" id="email" name="email" placeholder="Email">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="naissance" class="col-sm-2 control-label">Naissance</label>

                                    <div class="col-sm-10">
                                        <input type="date" class="form-control" id="naissance" name="naissance" placeholder="Naissance">
                                    </div>
                                </div>
                            </div>
                            <!-- /.box-body -->
                            <div class="box-footer">
                                <button type="submit" class="btn btn-info pull-right">Ajouter</button>
                            </div>
                            <!-- /.box-footer -->
                        </form>
                    </div>
                    <!-- /.box -->
                </div>
                <!-- /.col -->
            </div>
        </section>
        <!-- /.content -->
    </div>

    <%@ include file="/WEB-INF/views/common/footer.jsp" %>
</div>
<!-- ./wrapper -->

<%@ include file="/WEB-INF/views/common/js_imports.jsp" %>

<script>
    document.addEventListener("DOMContentLoaded", function() {
        var form = document.querySelector("form");

        form.addEventListener("submit", function(event) {
            var lastName = document.getElementById("last_name").value.trim();
            var firstName = document.getElementById("first_name").value.trim();
            var naissance = document.getElementById("naissance").value.trim();

            // Vérifier si le nom et le prénom font au moins 3 caractères
            if (lastName.length < 3 || firstName.length < 3) {
                alert("Le nom et le prénom doivent faire au moins 3 caractères.");
                event.preventDefault(); // Empêcher la soumission du formulaire
                return;
            }

            // Vérifier si l'utilisateur a au moins 18 ans
            var birthDate = new Date(naissance);
            var now = new Date();
            var age = now.getFullYear() - birthDate.getFullYear();
            var monthDiff = now.getMonth() - birthDate.getMonth();
            if (monthDiff < 0 || (monthDiff === 0 && now.getDate() < birthDate.getDate())) {
                age--;
            }
            if (age < 18) {
                alert("Vous devez avoir au moins 18 ans pour vous inscrire.");
                event.preventDefault(); // Empêcher la soumission du formulaire
                return;
            }

        });


    });
</script>

</body>
</html>
