package web;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ClientDao;
import dao.Impl.ClientDaoImpl;
import dto.ClientResponse;
import model.client;
import util.JsonUtil;

@WebServlet(name = "clients", urlPatterns = {"/clients"})
public class ClientsServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private ClientDao clientDao;

    @Override
    public void init() throws ServletException {
        clientDao = new ClientDaoImpl();
    }
    
    @Override
    protected void doPost(
            HttpServletRequest req,
            HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        String codeClient = req.getParameter("codeClient");
        String nomClient = req.getParameter("nomClient");
        String prenomClient = req.getParameter("prenomClient");
        String telephoneClient = req.getParameter("telephoneClient");
        String adresseClient = req.getParameter("adresseClient");

        // 1. Vérification des champs obligatoires
        if (codeClient == null || codeClient.trim().isEmpty()
                || nomClient == null || nomClient.trim().isEmpty()) {

            JsonUtil.repondreJson(
                resp,
                HttpServletResponse.SC_BAD_REQUEST,
                JsonUtil.erreurJson(
                    "Le code et le nom du client sont obligatoires",
                    "CLIENT_DATA_REQUIRED"
                )
            );
            return;
        }

        codeClient = codeClient.trim();
        nomClient = nomClient.trim();

        if (prenomClient != null) prenomClient = prenomClient.trim();
        if (telephoneClient != null) telephoneClient = telephoneClient.trim();
        if (adresseClient != null) adresseClient = adresseClient.trim();

        try {
            // 2. Vérifier que le code n'existe pas déjà
            client existant = clientDao.findByCodeClient(codeClient);

            if (existant != null) {
                JsonUtil.repondreJson(
                    resp,
                    HttpServletResponse.SC_CONFLICT,
                    JsonUtil.erreurJson(
                        "Ce code client existe déjà",
                        "CLIENT_ALREADY_EXISTS"
                    )
                );
                return;
            }

            // 3. Construire l'entité client
            client nouveauClient = new client();

            nouveauClient.setCodeClient(codeClient);
            nouveauClient.setNomClient(nomClient);
            nouveauClient.setPrenomClient(prenomClient);
            nouveauClient.setTelephoneClient(telephoneClient);
            nouveauClient.setAdresseClient(adresseClient);

            // 4. Enregistrer dans la base de données
            boolean ajoute = clientDao.ajouterClient(nouveauClient);

            if (!ajoute) {
                JsonUtil.repondreJson(
                    resp,
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    JsonUtil.erreurJson(
                        "La création du client a échoué",
                        "CLIENT_CREATE_ERROR"
                    )
                );
                return;
            }

            // 5. Préparer le nom complet
            String nomComplet = nomClient;

            if (prenomClient != null && !prenomClient.isEmpty()) {
                nomComplet += " " + prenomClient;
            }

            // 6. Réponse JSON
            java.util.Map<String, Object> resultat =
                    new java.util.LinkedHashMap<>();

            resultat.put("ok", true);
            resultat.put("id", nouveauClient.getId());
            resultat.put("codeClient", nouveauClient.getCodeClient());
            resultat.put("nomComplet", nomComplet);
            resultat.put("message", "Client créé avec succès");

            JsonUtil.repondreJson(
                resp,
                HttpServletResponse.SC_CREATED,
                JsonUtil.toJson(resultat)
            );

        } catch (SQLException e) {

            log("Erreur SQL lors de la création du client", e);

            JsonUtil.repondreJson(
                resp,
                HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                JsonUtil.erreurJson(
                    "Erreur lors de la création du client",
                    "CLIENT_CREATE_ERROR"
                )
            );
        }
    }
    @Override
    protected void doGet(
            HttpServletRequest req,
            HttpServletResponse resp)
            throws ServletException, IOException {

        String codeClient = req.getParameter("codeClient");

        if (codeClient == null || codeClient.trim().isEmpty()) {
           JsonUtil.repondreJson(resp, HttpServletResponse.SC_BAD_REQUEST, 
        		   JsonUtil.erreurJson("Code Client Obligatoire", "CLIENT_CODE_REQUIRED"));
            return;
        }

        try {
            client cl = clientDao.findByCodeClient(codeClient.trim());
            
            ClientResponse resultat;

            if (cl == null) {
            	resultat=new ClientResponse(false, 0, null, null, null,"Client introuvable");
            } else {
            	resultat= new ClientResponse(
            			true,
            			cl.getId(),
                        cl.getCodeClient(),
                        cl.getNomClient(),
                        cl.getPrenomClient(),
                        "Client trouvé"
            			);
            }
            
            JsonUtil.repondreJson(resp, HttpServletResponse.SC_OK, JsonUtil.toJson(resultat));

        } catch (SQLException e) {
            log("Erreur lors de la recherche du client", e);

            JsonUtil.repondreJson(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, 
            		JsonUtil.erreurJson("Erreur lors de la recherche du client", "CLIENT_SEARCH_ERROR"));
        }
    }
}