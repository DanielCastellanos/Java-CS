package bloqueo;

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

public class CheckConnection {

    public boolean isConnected() {
        try {
            WebClient webClient = new WebClient();

            HtmlPage page1 = webClient.getPage("http://siiauescolar.siiau.udg.mx/wus/gupprincipal.forma_inicio");
            HtmlForm form = page1.getFormByName("detect");
//nombre de la caja de texto 
            HtmlTextInput codigo = form.getInputByName("p_codigo_c");
            HtmlPasswordInput clave = form.getInputByName("p_clave_c");
//nombre del boton del formulario final 
            HtmlSubmitInput button = form.getInputByValue("Ingresar");
//llenamos la caja de texto 
            codigo.setValueAttribute("123456789");
            clave.setValueAttribute("23654789");
//Creamos la pagina que nos devolverá el resultado final 
            HtmlPage pageResultado;
//hacemos clic en el boton del formulario y asignamos el resultado a la pagina pageResultado 
            pageResultado = button.click();

            webClient.close();
            return true;
        } catch (IOException ex) {

            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (FailingHttpStatusCodeException ex) {

            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}
