<?php
///Clase creada para ordenar y facilitar el manejo de sesiones
check();
$opc= &_GET['function'];
switch(opc){
    
    case '1':
        ini($_GET['usr']);
        break;
    case '2':
        check();
        break;
    case '3':
        cerrar();
        break;
}
    function ini($usr){
        
        session_start();
        $_SESSION['usr']= $usr;
        
    }
    function check(){
    if(!isset($_SESSION['usr']))
    {
        header('Location: Index.html');
        exit();
        
    }
}

    function cerrar(){
        echo 'Esta entrando aquí';
        session_destroy();
    }

?>