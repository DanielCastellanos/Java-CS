
package cliente;
/**
 *
 * @author PC11
 */
public class Servidor_Inf {
    private String nombre=null,grupo=null;

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
