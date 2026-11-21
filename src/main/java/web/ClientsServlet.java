package web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ClientDao;
import dao.Impl.ClientDaoImpl;
import dto.ClientResponse;
import model.client;
import service.ClientService;
import service.Impl.ClientServiceImpl;
import util.JsonUtil;
import util.pageLire;

@WebServlet(name = "clients", urlPatterns = {"/clients","/client"})
public class ClientsServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final int TAILLE_PAGE=10;

    private ClientDao clientDao;
    private  ClientService secrviceClient;

    @Override
    public void init() throws ServletException {
    	
    	clientDao = new ClientDaoImpl();
    	secrviceClient = new ClientServiceImpl(clientDao);
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

        // pour vérifier des champs obligatoires
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
            // pour Vérifier l'existance de codeClient
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

            // créer  le nouveau client
            client nouveauClient = new client();

            nouveauClient.setCodeClient(codeClient);
            nouveauClient.setNomClient(nomClient);
            nouveauClient.setPrenomClient(prenomClient);
            nouveauClient.setTelephoneClient(telephoneClient);
            nouveauClient.setAdresseClient(adresseClient);

            // enregistrer le nouveau 
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

            // Concatenation le nom complet
            String nomComplet = nomClient;

            if (prenomClient != null && !prenomClient.isEmpty()) {
                nomComplet += " " + prenomClient;
            }

            // Réponse  en format JSON
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
    	

    	if("/client".equals(req.getServletPath())) {
    		try {
    			List<client> listClient =secrviceClient.findListClientALL();
    			
    			if (listClient == null) 
    			{
                    throw new ServletException(
                        "Le service a retourné une liste null."
                    );
    			}
				int total=listClient.size();
				int nbrPages=Math.max(
						1, (int)Math.ceil(total/(double)TAILLE_PAGE));
				int page=pageLire.lireEntier(req.getParameter("page"), 1);
				page=Math.min(Math.max(page, 1),nbrPages);
				int debut=(page-1)*TAILLE_PAGE;
				int fin=Math.min(debut+TAILLE_PAGE, total);
				
				req.setAttribute("client",listClient.subList(debut,fin));
				
				req.setAttribute("totalClients", total);
				req.setAttribute("pageCourante", page);
				req.setAttribute("nbPages", nbrPages);
				
				req.setAttribute("activePage", "Client");
				req.setAttribute("titrePage", "Client");
				
				req.getRequestDispatcher("Client.jsp").forward(req, resp);	
    		}catch(SQLException e) {
    			throw new ServletException("Erreur de charger les Clients",e);
    		}
    		}else 
    		{
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
}