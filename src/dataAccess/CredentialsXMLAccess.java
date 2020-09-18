package dataAccess;

import dataAccess.utils.CryptoUtils;
import model.Credentials;
import model.exceptions.CredentialsNotSetException;
import model.exceptions.CryptoException;
import model.exceptions.FileException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

public class CredentialsXMLAccess implements ICredentialsAccess {
    private static Credentials dbCredentials;
    private static final String KEY = System.getenv("APPDATA");
    private static final String FOLDER_PATH = KEY + "\\radiateur-app";

    public CredentialsXMLAccess() {}

    private void getData() throws ParserConfigurationException, CryptoException, IOException, SAXException {
        File credentialsFile = new File(FOLDER_PATH + "\\credentials.xml");
        String key = KEY.substring(8, 24);
        CryptoUtils.decrypt(key, new File(FOLDER_PATH + "\\credentials.lck"), credentialsFile);
        Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(credentialsFile);

        document.getDocumentElement().normalize();
        dbCredentials = new Credentials(document.getElementsByTagName("DatabaseUserData").item(0).getChildNodes().item(0).getTextContent(), document.getElementsByTagName("DatabaseUserData").item(0).getChildNodes().item(1).getTextContent().toCharArray());
        credentialsFile.delete();
    }

    public String getUsername() throws CredentialsNotSetException, ParserConfigurationException, CryptoException, SAXException, IOException {
        if (credentialsAreSet()) {
            if (dbCredentials == null)
                getData();
        } else throw new CredentialsNotSetException();
        return dbCredentials.getUsername();
    }

    public char[] getPassword() throws CredentialsNotSetException, ParserConfigurationException, CryptoException, SAXException, IOException {
        if (credentialsAreSet()) {
            if (dbCredentials == null)
                getData();
        } else throw new CredentialsNotSetException();
        return dbCredentials.getPassword();
    }

    public void setCredentials(String username, char[] password) throws FileException {
        try {
            File radiateurFolder = new File(FOLDER_PATH);
            radiateurFolder.mkdir();
            File credentialsFile = new File(FOLDER_PATH + "\\credentials.xml");

            if (credentialsFile.createNewFile()) {
                Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
                Transformer transformer = TransformerFactory.newInstance().newTransformer();
                DOMSource source = new DOMSource(document);
                StreamResult streamResult = new StreamResult(credentialsFile);
                Element rootElem = document.createElement("DatabaseUserData");
                Element usernameElem = document.createElement("Username");
                Element passwordElem = document.createElement("Password");
                String key = KEY.substring(8, 24);

                document.appendChild(rootElem);
                rootElem.appendChild(usernameElem);
                usernameElem.appendChild(document.createTextNode(username));
                rootElem.appendChild(passwordElem);
                passwordElem.appendChild(document.createTextNode(new String(password)));
                transformer.transform(source, streamResult);
                CryptoUtils.encrypt(key, credentialsFile, new File(FOLDER_PATH + "\\credentials.lck"));
                credentialsFile.delete();
            }

        } catch (Exception exception) {
            throw new FileException("fichier de configuration", exception.getMessage());
        }
    }

    public static boolean credentialsAreSet() {
        return (new File(FOLDER_PATH + "\\credentials.lck")).exists();
    }
}
