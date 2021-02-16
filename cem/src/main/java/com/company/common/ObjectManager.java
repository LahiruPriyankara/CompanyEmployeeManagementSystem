/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.common;

import java.util.Enumeration;
import java.util.Vector;
import javax.servlet.http.HttpSession;
import com.company.common.SBLException;

/**
 *
 * @author sits_lahirupr
 */
public class ObjectManager {
 private Vector loadedObjects;
    private HttpSession session;
    
    public ObjectManager() {
        super();
    }
    
    public ObjectManager(HttpSession session) throws SBLException
    {

        try
        {
            this.loadedObjects = session.getAttribute("_loadedObjects")!=null ? (Vector)session.getAttribute("_loadedObjects"):new Vector();
            this.session = session;

        }
        catch(Exception ex)
        {	
            ex.printStackTrace();
            throw new SBLException("User Session terminated. Open a new browser window to Log-in again");
        }
    }
    
    public void put(String name,Object value,int scope)
    {
        session.setAttribute(name,value);
        ObjectData objInfo = new ObjectData(name,scope);
        loadedObjects.add(objInfo);
        session.setAttribute("_loadedObjects",loadedObjects);
    }

    public void put(String name,Object value)
    {
        session.setAttribute(name,value);
    }

    public Object get(String name) 
    {
        Object obj = session.getAttribute(name)==null?null:session.getAttribute(name);
        return obj;
    }

    public void cleanup(int scope)
    {
        for(int i=0;i<loadedObjects.size();i++)
        {

            ObjectData objInfo = (ObjectData)loadedObjects.get(i);
            if(objInfo.getScope()==scope)
            {
                    loadedObjects.remove(i);
                    i--;
                    session.removeAttribute(objInfo.getObjName());
            }
        }
        session.setAttribute("_loadedObjects",loadedObjects);
    }

    public void cleanup()
    {
        for(int i=0;i<loadedObjects.size();i++)
        {

            ObjectData objInfo = (ObjectData)loadedObjects.get(i);
            if(objInfo.getScope()!=ApplicationConstants.SCOPE_GLOBAL)
            {
                loadedObjects.remove(i);
                i--;
                session.removeAttribute(objInfo.getObjName());
            }
        }
        session.setAttribute("_loadedObjects",loadedObjects);
    }

    /**
     * @author 
     * @param int keepSession
     */
    public void cleanOther(int keepSession){
        for(int i=0;i<loadedObjects.size();i++)
        {

            ObjectData objInfo = (ObjectData)loadedObjects.get(i);
            if(!(objInfo.getScope()==keepSession || objInfo.getScope()==ApplicationConstants.SCOPE_GLOBAL))
            {
                    loadedObjects.remove(i);
                    i--;
                    session.removeAttribute(objInfo.getObjName());
            }
        }
        session.setAttribute("_loadedObjects",loadedObjects);

    }

    public void cleanupAll()
    {
        Enumeration en = session.getAttributeNames();
        while(en.hasMoreElements())
        {
                session.removeAttribute((String)en.nextElement());
        }
    }

    public void update(String name,Object value)
    {
        session.setAttribute(name,value);
    }

    public void remove(String name)
    {
        session.removeAttribute(name);
    }        
}

