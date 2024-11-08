package main;

import dataBase.Pool;
import dataBase.DAO;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;
import libreria.Signable;

/**
 * La clase {Factory} es un patrón de diseño que se utiliza para crear instancias
 * de objetos relacionados con la interfaz {Signable}. Esta clase proporciona
 * un método estático que devuelve una nueva instancia de {DAO}, la cual implementa
 * la interfaz {@link Signable}, utilizando un pool de conexiones a la base de datos.
 * 
 * El objetivo principal de esta clase es centralizar la creación de objetos que manejan
 * operaciones de registro e inicio de sesión, y proporcionar una instancia lista para usarse
 * en otras partes de la aplicación.
 * 
 * @author Asier del Campo.
 */
public class Factory {

    // Pool de conexiones a la base de datos utilizado por la clase DAO
    private static final Pool connectionPool = new Pool();

    /**
     * Método estático que crea y devuelve una instancia de {Signable}.
     * 
     * Este método utiliza el pool de conexiones {connectionPool} para crear
     * una nueva instancia de {DAO}. El objeto {DAO} es responsable de gestionar
     * las operaciones de autenticación y registro de usuarios, y se conecta a la base de datos
     * a través del pool de conexiones proporcionado.
     * 
     * @return Una nueva instancia de {Signable}, concretamente de {AO}.
     */
    public static Signable createSignable() {
        return new DAO(connectionPool);
    }
}

