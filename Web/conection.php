<?php 

require 'conexion.php';

$conn = new Conexion();
session_start();

if(isset($_POST['funcion'])){
    $opcion= $_POST['funcion'];
    
    switch($opcion){
            
        case 'login':
            login($conn);
            break;
        case 'registrar':
            registrar($conn);
            break;
        case 'modificar':
            modificar($conn);
            break;
        case 'consulta':
            obtenerRegistrosUsr($conn);
            break;
        case 'insertReg':
            insertReg($conn);
            break;
    }
}
    
    function registrar($conn){
        
        $nombre=$_POST['nombre'];
        $codigo=$_POST['codigo'];
        $contra=md5($_POST['pass']);
        $correo=$_POST['correo'];
        $sexo=$_POST['sexo'];
        $fecha_nac= $_POST['fech_nac'];
        $query= 'INSERT INTO usuario (Pk_Codigo ,Nombre ,Pass ,Fec_Nac) VALUES '."($codigo ,'$nombre' ,'$contra', '$fecha_nac');";
        
        if($resultado = $conn->query($query)){
            echo 'registro exitoso';
        }else{
            echo 'error de inserción '.$conn->error."\n $query";
        }
        $conn->close();
        
    }

    function modificar($conn){
        $tabla='usuario';
        $campo='Carrera';
        $valor='Computo';
        $id=210437652;
        $query="UPDATE $tabla SET $campo= '$valor' WHERE Pk_Codigo=$id;";
        echo $query;
        if($resultado = $mysqli->query($query)){
            echo 'Se realiizó la modificación';
        }else{
            echo 'error de inserción '.$conn->error."\n $query";
        }
        $conn->close();
    }

    function login($conn){
        
        $cod= $_POST['codigo'];
        $pass= md5($_POST['password']);
        //echo $pass;
        $consulta= "SELECT Nombre, Plaza_idPlaza FROM usuario WHERE Pk_Codigo='$cod' AND Pass='$pass'";
        $resultado= $conn->query($consulta);
        //$obj_json;
        if(mysqli_num_rows($resultado) > 0){
            /*$obj_json['valid']=1;
            echo json_encode($obj_json);*/
            $result=mysqli_fetch_array($resultado);
            $_SESSION['Nombre']=$result['Nombre'];
            getPlaza($conn, $result['Plaza_idPlaza']);
            echo "1";
        }else{
            //$obj_json['valid']=0;
            //echo json_encode($obj_json);
            $_SESSION= array();
            echo "0";
        }
        mysqli_close($conn);
    }

    function getPlaza($conn, $id){
    $consulta="SELECT Lugar FROM plaza WHERE idPlaza='$id'";
    if($resultado= $conn->query($consulta)){
        $_SESSION['plaza']=mysqli_fetch_array($resultado)['Lugar'];
    }else{
        $_SESSION['plaza']="No disponible";
    }
}

    function obtenerRegistrosUsr($conn){
        
        $consulta;
        $codigo=$_SESSION['usr'];
        $consulta= "SELECT idRegistro,DATE_FORMAT(Hr_Entrada,'%r'),DATE_FORMAT(Hr_Salida,'%r'),DATE_FORMAT(Fecha,'%d-%m-%Y'),Usuario_Pk_Codigo FROM registro WHERE Usuario_Pk_Codigo=$codigo order by idRegistro desc" ;
            if($resultado= $conn->query($consulta)){
                
                $registro=array();
                $registros=array();
                $cont=0;
                while($reg= mysqli_fetch_array($resultado)){
                    $registro["fecha"]=$reg[3];
                    $registro["entrada"]=$reg[1];
                    $hora=$reg[2];
                    if($hora=='12:00:00 AM'){
                        $hora="-";
                    }
                    $registro["salida"]=$hora;
                    $registros[]=$registro;
                }
                echo json_encode($registros);
            }
    }
    
    function insertReg($conn){
        
        $consulta="CALL RegEntrada('".$_SESSION['usr']."')";
        if($result= $conn->query($consulta)){
            echo "1";
        }else{
            echo "0";
        }
    }
/*
    function getNom($conn){
    
        $consulta= "SELECT Nombre FROM usuario WHERE Pk_Codigo=".$_SESSION['usr'];
        if($resultado= $conn->query($consulta)){
            $nom= mysqli_fetch_assoc($resultado);
            $_SESSION['Nombre']=$nom['Nombre'];
        }
    }//*/
    /*function obtenerRegistro($mysqli){
        $id= 12312;
        if($resultado= mysqli->query("SELECT *FROM usuario WHERE id=$id", MYSQL_USE_RESULT)){
            
        };
        
        mysql_free_result;
        return $resultado;
    }
    //*/
    //function login(){
        
        //if(isset($_POST['nombre'])){
  /*          $consulta= "SELECT Pk_Codigo, Nombre FROM usuario WHERE Nombre='Daniel';";
            if ($resultado = $conn->query($consulta)) {
                $user= mysqli_fetch_array($resultado);
                echo mysqli_num_rows($resultado); //*/
                
                /*while($user= mysqli_fetch_array($resultado)){
                    echo $user['Pk_Codigo'];
                    echo $user['Nombre'];
                }*/
    /* liberar el conjunto de resultados */
//            $resultado->close();
            //}
     //   }
   // }
    
?>