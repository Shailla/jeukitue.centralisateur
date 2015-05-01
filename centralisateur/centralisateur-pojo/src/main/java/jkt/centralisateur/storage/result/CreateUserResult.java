package jkt.centralisateur.storage.result;

public enum CreateUserResult {
    /** Utilisateur créé avec succès */
    SUCCESS,
    
    /** Echec de création, l'utilisateur existe déjà */
    ALREADY_EXISTING_USER
}
