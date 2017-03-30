<?php
    
class Conexion extends mysqli{
    
    function __construct(){
        parent::__construct('127.0.0.1', 'root', '', 'serviciosocial');
        $this->query("SET NAMES 'utf8';");
        if($this->connect_errno){
            echo "Error";
            exit();
        }
    }
}
?>