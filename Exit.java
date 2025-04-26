package AdventureGameJava.src.com.adventure;

import java.io.Serializable;

/**
 * La classe Exit représente une sortie d'une zone vers une autre.
 * Les sorties peuvent être verrouillées, invisibles ou à sens unique.
 * 
 * 
 */
public class Exit implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Zone destination;
    private boolean locked;
    private String keyName;
    private boolean visible;
    private boolean oneWay;
    
    /**
     * Constructeur pour une sortie standard
     * 
     * @param destination La zone vers laquelle cette sortie mène
     */
    public Exit(Zone destination) {
        this.destination = destination;
        this.locked = false;
        this.keyName = null;
        this.visible = true;
        this.oneWay = false;
    }
    
    /**
     * Constructeur pour une sortie spéciale
     * 
     * @param destination La zone vers laquelle cette sortie mène
     * @param locked Si la sortie est verrouillée
     * @param keyName Le nom de la clé nécessaire pour déverrouiller la sortie
     * @param visible Si la sortie est initialement visible
     */
    public Exit(Zone destination, boolean locked, String keyName, boolean visible) {
        this.destination = destination;
        this.locked = locked;
        this.keyName = keyName;
        this.visible = visible;
        this.oneWay = false;
    }
    
    /**
     * Constructeur pour une sortie entièrement personnalisable
     * 
     * @param destination La zone vers laquelle cette sortie mène
     * @param locked Si la sortie est verrouillée
     * @param keyName Le nom de la clé nécessaire pour déverrouiller la sortie
     * @param visible Si la sortie est initialement visible
     * @param oneWay Si la sortie est à sens unique
     */
    public Exit(Zone destination, boolean locked, String keyName, boolean visible, boolean oneWay) {
        this.destination = destination;
        this.locked = locked;
        this.keyName = keyName;
        this.visible = visible;
        this.oneWay = oneWay;
    }
    
    /**
     * Obtient la zone de destination de cette sortie
     * 
     * @return La zone de destination
     */
    public Zone getDestination() {
        return destination;
    }
    
    /**
     * Vérifie si cette sortie est verrouillée
     * 
     * @return vrai si la sortie est verrouillée, faux sinon
     */
    public boolean isLocked() {
        return locked;
    }
    
    /**
     * Tente de déverrouiller cette sortie avec une clé
     * 
     * @param key La clé à essayer
     * @return vrai si la sortie a été déverrouillée, faux sinon
     */
    public boolean unlock(Item key) {
        if (!locked) {
            return true; // Déjà déverrouillée
        }
        
        if (key != null && key.getName().equalsIgnoreCase(keyName)) {
            locked = false;
            return true;
        }
        
        return false;
    }
    
    /**
     * Vérifie si cette sortie est visible
     * 
     * @return vrai si la sortie est visible, faux sinon
     */
    public boolean isVisible() {
        return visible;
    }
    
    /**
     * Définit si cette sortie est visible
     * 
     * @param visible Si la sortie doit être visible
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }
    
    /**
     * Vérifie si cette sortie est à sens unique
     * 
     * @return vrai si la sortie est à sens unique, faux sinon
     */
    public boolean isOneWay() {
        return oneWay;
    }
    
    /**
     * Obtient le nom de la clé nécessaire pour déverrouiller cette sortie
     * 
     * @return Le nom de la clé requise, ou null si aucune clé n'est nécessaire
     */
    public String getKeyName() {
        return keyName;
    }
    
    /**
     * Définit l'état de verrouillage de cette sortie
     * 
     * @param locked vrai si la sortie doit être verrouillée, faux sinon
     */
    public void setLocked(boolean locked) {
        this.locked = locked;
    }
    
} 