package controlador;

import libreria.Signable;

/**
 * La clase {SignableFactory} es una fábrica de objetos que implementan la interfaz
 * {@link Signable}. Esta clase proporciona un método estático para obtener instancias de 
 * clases que implementan la interfaz {Signable}, como el {Cliente}.
 * 
 * El propósito de esta clase es desacoplar la creación de objetos del tipo {Signable}
 * y permitir la flexibilidad de cambiar la implementación concreta sin afectar otras partes
 * del código que utilicen esta interfaz.
 * 
 * En este caso, la implementación concreta proporcionada por el método {getSignable}
 * es la clase {Cliente}, pero puede modificarse fácilmente en el futuro para proporcionar
 * diferentes implementaciones de {Signable}.
 * 
 * @author Guillermo Flecha
 */
public class SignableFactory {
    
    /**
     * Devuelve una nueva instancia de una clase que implementa la interfaz {Signable}.
     * 
     * Actualmente, el método retorna una instancia de la clase {Cliente}, pero puede
     * ser modificado para devolver cualquier otra clase que implemente {@link Signable}.
     * 
     * @return Una nueva instancia de {Signable}, en este caso, un objeto {Cliente}.
     */
    public static Signable getSignable(){
        return new Cliente();
    } 
}

