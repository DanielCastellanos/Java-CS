<!DOCTYPE html>
<?php
session_start();
if(!isset($_SESSION['usr'])){
        header("location: Index.php");
        exit();
    }

//*/?>

<html lang="">
<head>
    <script src="js/jquery-3.1.1.js"></script>
    <script src="js/jquery.dynatable.js"></script>
    <script src="js/hader.js"></script>
    <link rel="stylesheet" href="css/jquery.dynatable.css">
    <link rel="stylesheet" href="css/servicio.css">
    <link rel="stylesheet" href="css/fuente.css">
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title></title>
</head>
<header>
        <div id="menu_bar">
            <a href="#" id="bt-menu"><span class="icon-menu"></span></a>
        </div>
    <div id="titulo">Servicio Social</div>
    <div id="lab">
        <?php
            echo $_SESSION['plaza'];    
        ?>
    </div>
        <nav>
            <ul>
                <li><a href="accountView">Mi cuenta</a></li>
                <li><a id="sessionClose">Salir</a></li>
            </ul>
        </nav>
    </header>
    
<body>
    <div id="wrapper">
        <div id="datos">
    <h2 id="nombre">
        <?php
            echo $_SESSION['Nombre'];    
        ?>
            </h2>
        <h2 id="codigo">
            <?php 
            echo $_SESSION['usr'];
            ?>
        </h2>
        </div>
        
        <div id="contenedor">
<table id="tabla" style="overflow-x:auto;">
  <thead>
      <tr>
          <th>Fecha</th>
          <th>Entrada</th>
          <th>Salida</th>
      </tr>
      </thead>
      <tbody>
        
    </tbody>
</table>
        </div>

    </div>
    <div id="bt-registro">
        
        <button>Registrar</button>
    
    </div>
         
</body>
</html>
