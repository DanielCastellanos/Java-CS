<?php 

    $opc= $_POST['funcion'];

    switch($opc){
            
        case 'start':
            sesionStart();
            break;
        case 'destroy':
            sesionDestroy();
            break;
    }

function sesionStart(){
    if(session_start()){
        $_SESSION['usr']= $_POST['usr'];
        
        echo "1";
    }
    else{
        echo "0";
    }
    
}

function sesionDestroy(){
    $_SESSION= array();
    if (ini_get("session.use_cookies")) {
    $params = session_get_cookie_params();
    setcookie(session_name(), '', time() - 42000,
        $params["path"], $params["domain"],
        $params["secure"], $params["httponly"]
    );
}
    session_destroy();
}
?>