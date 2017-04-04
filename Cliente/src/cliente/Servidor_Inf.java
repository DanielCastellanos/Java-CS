
package cliente;
/**
 *
 * @author PC11
 */
public class Servidor_Inf {
    private String nombre=null,grupo=null,hostName=null;

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getGrupo() {
        return grupo;
    }
    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }
    @Override
    public String toString()
    {
        return "Nombre: "+nombre+" Grupo: "+grupo;
    }
}
