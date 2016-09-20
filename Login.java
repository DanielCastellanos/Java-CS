/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scraping;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Scraping {

    public static void main(String[] args) {
        //emulamos un navegador web final 
        WebClient webClient = new WebClient();
        try {
//Pagina donde se hara la consulta 
            HtmlPage page1 = webClient.getPage("http://siiauescolar.siiau.udg.mx/wus/gupprincipal.forma_inicio");
//nombre del formulario final 
            HtmlForm form = page1.getFormByName("detect");
//el valor "f" no es arbitrario es el nombre del formulario web de google 
//nombre de la caja de texto 
            HtmlTextInput textField = form.getInputByName("p_codigo_c");
            HtmlPasswordInput nip = form.getInputByName("p_clave_c");
//el valor "q" no es arbitrario es el nombre de la caja de texto del formulario web de google 
//nombre del boton del formulario final 
            HtmlSubmitInput button = form.getInputByValue("Ingresar");
//el valor "btnG" no es arbitrario es el nombre del boton del formulario web de google 
//llenamos la caja de texto 
            textField.setValueAttribute("210436966");
            nip.setValueAttribute("");
//Creamos la pagina que nos devolverá el resultado final 
            HtmlPage pageResultado;
//hacemos clic en el boton del formulario y asignamos el resultado a la pagina pageResultado 
            pageResultado = button.click();
//imprimimos el resultado 
            System.out.println(pageResultado.asText());
           
            
//cerramos el navegador emulado, para liberar todo esto de la memoria 
            webClient.close();
        } catch (IOException ex) {
            Logger.getLogger(Scraping.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FailingHttpStatusCodeException ex) {
            Logger.getLogger(Scraping.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}


