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