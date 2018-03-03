package com.epam.aem.training.models;


import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceUtil;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.Model;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.jcr.Node;
import javax.jcr.RepositoryException;
import java.util.ArrayList;
import java.util.List;

@Model(adaptables=Resource.class)
public class NewModel {

    @Inject
    private String dinamicpath;

    @Inject
    private List<String> staticpath;

    @Inject
    private String option;

    @Inject
    ResourceResolver resourceResolver;


    private List<InfoPage> staticlist;

    private List<InfoPage> dinamiclist;

    public List<InfoPage> getStaticlist() {
        return staticlist;
    }

    public List<InfoPage> getDinamiclist() {
        return dinamiclist;
    }

    public String getDinamicpath() {
        return dinamicpath;
    }

    public List<String> getStaticpath() {
        return staticpath;
    }

    @PostConstruct
    public void init(){
        if(option.equals("static")) {
            staticlist = new ArrayList<>();
            getStaticInfo();
        }
        if(option.equals("dinamic")) {
            dinamiclist = new ArrayList<>();
            getDinamicInfo();
        }
    }

    public void getStaticInfo() {
        for(String s:staticpath) {
            if (s != null) {
                Resource resource = resourceResolver.getResource(s);
                Node node = resource.getChild("jcr:content").adaptTo(Node.class);
                String title = "";
                String description = "";
                String image = "";
                try {
                    title = node.getProperty("jcr:title").getString();
                    description = node.getProperty("jcr:description").getString();
                    image = node.getNode("image/file").getPath();
                } catch (RepositoryException e) {
                    e.printStackTrace();
                }
                staticlist.add(new InfoPage(title, description, image, resource.getPath()+".html"));
            }
        }
        }

    public void getDinamicInfo(){
        if(dinamicpath!=null) {
            Resource resource = resourceResolver.getResource(dinamicpath);
            Iterable<Resource> children = resource.getChildren();
            for (Resource child : children) {
                if (child.isResourceType("cq:Page")) {
                    Node node = child.getChild("jcr:content").adaptTo(Node.class);
                    String title = "";
                    String description = "";
                    String image = "";
                    try {
                        title = node.getProperty("jcr:title").getString();
                        description = node.getProperty("jcr:description").getString();
                        image = node.getNode("image/file").getPath();
                    } catch (RepositoryException e) {
                        e.printStackTrace();
                    }
                    dinamiclist.add(new InfoPage(title, description, image, child.getPath() + ".html"));
                }
            }
        }
    }
}
