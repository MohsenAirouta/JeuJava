package AdventureGameJava.src.com.adventure;

import java.io.Serializable;

/**
 * La classe analyse et stocke les commandes de l'utilisateur.
 * Une commande se compose d'un mot d'action et d'un second mot optionnel.
 */
public class Command implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String commandWord;
    private String secondWord;
    private String completeArgs;
    
   
    
    public Command(String commandWord, String secondWord) {
        this.commandWord = commandWord;
        this.secondWord = secondWord;
    }

    public Command(String commandWord, String secondWord, String completeArgs) {
        this.commandWord = commandWord;
        this.secondWord = secondWord;
        this.completeArgs = completeArgs;
    }
    
   
    public String getCommandWord() {
        return commandWord;
    }
    
   
    public String getSecondWord() {
        return secondWord;
    }
    
   
    public boolean hasSecondWord() {
        return secondWord != null;
    }

  
    public String getCompleteArgs() {
        return completeArgs;
    }   

   
    public void setCompleteArgs(String completeArgs) {
        this.completeArgs = completeArgs;
    }
    
   
    @Override
    public String toString() {
        if (secondWord == null) {
            return commandWord;
        } else {
            return commandWord + " " + secondWord;
        }
    }
} 