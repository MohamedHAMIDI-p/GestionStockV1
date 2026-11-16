package dto;

public class ClientResponse {

    private boolean exists;
    private int id;
    private String codeClient;
    private String nomClient;
    private String prenomClient;
    private String message;

    public ClientResponse() {
    }

    public ClientResponse(boolean exists, int id,
            String codeClient, String nomClient,
            String prenomClient, String message) {

        this.exists = exists;
        this.id = id;
        this.codeClient = codeClient;
        this.nomClient = nomClient;
        this.prenomClient = prenomClient;
        this.message = message;
    }

    public boolean isExists() {
        return exists;
    }

    public void setExists(boolean exists) {
        this.exists = exists;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodeClient() {
        return codeClient;
    }

    public void setCodeClient(String codeClient) {
        this.codeClient = codeClient;
    }

    public String getNomClient() {
        return nomClient;
    }

    public void setNomClient(String nomClient) {
        this.nomClient = nomClient;
    }

    public String getPrenomClient() {
        return prenomClient;
    }

    public void setPrenomClient(String prenomClient) {
        this.prenomClient = prenomClient;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}