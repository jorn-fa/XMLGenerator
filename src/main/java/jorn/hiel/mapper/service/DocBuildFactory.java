package jorn.hiel.mapper.service;

import org.springframework.stereotype.Service;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

@Service
public class DocBuildFactory {

    DocumentBuilderFactory secureDbf;


    public DocumentBuilderFactory getSecureDbf() throws ParserConfigurationException {

        // Will report as false positive with older versions of SonarCube ( squid:S2755 )
        if (secureDbf == null) {


            secureDbf = DocumentBuilderFactory.newInstance();
            secureDbf.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            secureDbf.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
            secureDbf.setFeature("http://xml.org/sax/features/external-general-entities", false);
            secureDbf.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            secureDbf.setFeature("http://apache.org/xml/features/dom/create-entity-ref-nodes", false);
            secureDbf.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            secureDbf.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);

            secureDbf.setXIncludeAware(false);
            secureDbf.setExpandEntityReferences(false);
        }

        return secureDbf;
    }
}