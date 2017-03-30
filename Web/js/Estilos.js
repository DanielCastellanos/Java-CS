var est=0;

function Estilos(){
    OpcNotificacion();
    
    console.log("Entro a Estilos");
    $("*#boton").button();
    $("*#txt_fecha").datepicker();
    $("*#slider").slider({value:100});
   // $("*#boton").click(registrado);
    $(".Enviar").click(logIn);
    $("*#titulo").addClass("ui-widget-header");
    $(".Registrar").click(cargar);
    $(".Registro").click(registrar);
    $(".volver").click(function(){window.location.href="."});
    //$('link[title=mystyle]')[0].disabled=true;
    $("#cargar_estilos").click(cargarHoja);//
};
function cargarHoja(){
    if(est==0){
        $('link[title=mystyle]')[0].disabled=false;
        est=1;
    }else{
        $('link[title=mystyle]')[0].disabled=true;
        est=0;
    }
    
        
    
}
function OpcNotificacion(){
    $.extend($.gritter.options,{
        position:'bottom-right',
        fade_in_speed:'medium',
        fade_out_speed:2000,
        time:1500
    })
}

function Mensaje(titulo,texto){
    $.gritter.add({
        title:titulo,
        text:texto
    })
}

function logIn(){
    var codigo;
    var pass;
    var opc;
    codigo=$("input:text[name=nombre]").val();
    pass=$("input:password[name=contraseña]").val();
    opc= "login";
    //pass.value= md5(pass);
    console.log(codigo+","+pass);
    $.post("conection.php",
           {codigo:codigo,password:pass,funcion:opc},
           function(res){
            console.log(res);
            //var json_info= JSON.parse(res);
            if(res == "1"){
                console.log("El usuario es valido");
                
                $.post("Sesion.php",
                        {usr:codigo,funcion:"start"},
                        function(data){
                        console.log("entra a metodo get");
                        //window.location.href="servicio.php";
                        console.log(data);
                        if(data == "1"){
                            window.location.href="servicio.php";
                        }else{
                            console.log(data);
                        }
                }
                );
                //*/window.location.href="servicio.php";
            }else{
                Mensaje("Error","Usuario y/o Contraseña Invalidos");
            }
    });
    
}

function registrar()//Modificado
{
    var nombre,codigo,cont,conf,correo,sexo,fech_nac,opc,lugar;
    nombre=$("input:text[name=nombre]").val();
    codigo=$("input:text[name=codigo]").val();
    cont=$("input:password[name=contraseña]").val();
    conf=$("input:password[name=confirmacion]").val();
    correo=$("input:text[name=correo]").val();
    lugar=$("#selector").val();
    //sexo=$("input:checkbox[name=sexo]").val();
    fech_nac=$("#fecha").val();
    
    opc="registrar";
    console.log(correo+","+lugar);
    
    if(cont==conf)
        {
            //cont.value=md5(cont);
            $.ajax({
                type:"POST",
                url: "conection.php",
                data:{nombre:nombre,
                codigo:codigo,
                pass:cont,
                correo:correo,
                sexo:sexo,
                fech_nac:fech_nac,
                lugar:lugar,
                funcion:opc},
                success:function(registrado){
                    if(registrado=="registro exitoso"){
                            Mensaje("Registro","Registro Exitoso");
                            window.location.href=".";
                        }
                    else{
                            Mensaje("Registro","Error en Registro");
                    }
                }
            });
        }
    else{
        Mensaje("Registro","Las Contraseñas no coiniden");
    }
}

function cargar()
{
    $("#Principal").load("Registro.html");
    window.history.pushState("Login", "/Registro", "Registro.html");
}

//////////////////////////////////////////////////77
function plazas()
{
    $.ajax({
        type:"POST",
        url: "conection.php",
        data: {funcion:'plaza'},
        success:function(datos){
            console.log(datos);
            for(index=0;index<datos.length;index++){
            var pl=JSON.parse(datos[index]);
            $('#selector').append(new Option(pl["lugar"],pl["id"]));
            }
        },
        dataType:"json"
    });
}