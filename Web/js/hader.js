$(document).ready(function(){
             
            //Ocultamos el menú al cargar la página
            $("#menu").hide();
             
            /* mostramos el menú si hacemos click derecho
            con el ratón */
            $(document).bind("contextmenu", function(e){
                  $("#menu").css({'display':'block', 'left':e.pageX, 'top':e.pageY});
                  return false;
            });
             
             
            //cuando hagamos click, el menú desaparecerá
            $(document).click(function(e){
                  if(e.button == 0){
                        $("#menu").css("display", "none");
                  }
            });
             
            //si pulsamos escape, el menú desaparecerá
            $(document).keydown(function(e){
                  if(e.keyCode == 27){
                        $("#menu").css("display", "none");
                  }
            });
             
            //controlamos los botones del menú
            $("#menu").click(function(e){
                   
                  // El switch utiliza los IDs de los <li> del menú
                  switch(e.target.id){
                        case "copiar":
                              alert("copiado!");
                              break;      
                        case "mover":
                              alert("movido!");
                              break;
                        case "eliminar":
                              alert("eliminado!");
                              break;
                  }
                   
            });
             
                         
      });


$(document).ready(main);

 var cont = 1;

 function main()
{
    
    $(".bt-registro").click(regEntSal);
    
    $("#menu_bar").click(function(){
        if(cont == 1)
            {
                $('nav').animate({left:'70%'});
                
                cont=0;
            }
        else{
            cont=1;
            $('nav').animate({left:'100%'});
        }
    });
    $.ajax({
        type:"POST",
        url: "conection.php",
        data:{funcion:'consulta'},
        success:function(datos){
            console.log(datos);
            $('#tabla').dynatable({
                dataset:{
                 records:datos   
                }
            });
        },
        dataType:"json"
    });
    
   
}

function regEntSal(){
    
}