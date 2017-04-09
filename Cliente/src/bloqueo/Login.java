package bloqueo;

import cliente.SesionCliente;
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

public class Login {

    public boolean login(String cod, String nip) {
        //emulamos un navegador web final 
        boolean log = false;

        try {
            WebClient webClient = new WebClient();
//Pagina donde se hara la consulta 
            HtmlPage page1 = webClient.getPage("http://siiauescolar.siiau.udg.mx/wus/gupprincipal.forma_inicio");
//nombre del formulario final 
            HtmlForm form = page1.getFormByName("detect");
//el valor "detect" no es arbitrario es el nombre del formulario web de siiau 
//nombre de la caja de texto 
            HtmlTextInput codigo = form.getInputByName("p_codigo_c");
            HtmlPasswordInput clave = form.getInputByName("p_clave_c");
//el valor "p_codigo_c" no es arbitrario es el nombre de la caja de texto del formulario web de siiau 
//nombre del boton del formulario final 
            HtmlSubmitInput button = form.getInputByValue("Ingresar");
//el valor "Ingresar" no es arbitrario es el nombre del boton del formulario web de google 
//llenamos la caja de texto 
            codigo.setValueAttribute(cod);
            clave.setValueAttribute(nip);
//Creamos la pagina que nos devolverá el resultado final 
            HtmlPage pageResultado;
//hacemos clic en el boton del formulario y asignamos el resultado a la pagina pageResultado 
            pageResultado = button.click();
//imprimimos el resultado 
            String res = pageResultado.asText();
            if (res.equals("Untitled Document")) {
                log = true; 
                cliente.Cliente.sesion= new SesionCliente(cod);
            }
//cerramos el navegador emulado, para liberar todo esto de la memoria 
            webClient.close();
        } catch (IOException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FailingHttpStatusCodeException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        return log;
    }
}
