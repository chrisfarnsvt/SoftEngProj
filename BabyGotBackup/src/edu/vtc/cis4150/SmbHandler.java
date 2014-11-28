package edu.vtc.cis4150;

//A handler class that implements JCIFS methods for transferring data to/from a samba server.

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.ListIterator;

import jcifs.UniAddress;
import jcifs.smb.*;
 

public class SmbHandler
{
    private UniAddress domain;
    private String smbroot = "smb://rothbard/bgb/";
    private NtlmPasswordAuthentication authentication;

    public SmbHandler(String address, String username, String password) throws Exception
    {
    	jcifs.Config.setProperty("jcifs.smb.client.snd_buf_size", "32768"); 
        jcifs.Config.setProperty("jcifs.smb.client.rcv_buf_size", "32768");
		jcifs.Config.setProperty( "jcifs.netbios.wins", address);
        setAuthentication(new NtlmPasswordAuthentication(
                address, username, password));
    }
 
    /**
     * Returns a LinkedList<String> containing the contents of the given directory
     * @param path - the directory to list
     * @return fList - a LinkedLink<String> of the contents of the given directory 
     * @throws java.lang.Exception
     */
    public LinkedList<String> getList(String path) throws Exception
    {
        LinkedList<String> fList = new LinkedList<String>();
        SmbFile f = new SmbFile(path, authentication);
        SmbFile[] fArr = f.listFiles();        
        
        for(int a = 0; a < fArr.length; a++)
        {
            fList.add(fArr[a].getName());
        } 
        return fList;
    }
 
    /**
     * Does the path exist?
     * @param path
     * @return true if the given path exists, else false
     * @throws java.lang.Exception
     */
    public boolean isExist(String path) throws Exception
    {
        SmbFile sFile = new SmbFile(path, authentication);
        
        return sFile.exists();
    }
 
    /**
     * Is the path a directory?
     * @param path
     * @return true if the given path is a directory, else false
     * @throws java.lang.Exception
     */
    //!!May be required to check if the path exists first
    public boolean isDir(String path) throws Exception
    {
        SmbFile sFile = new SmbFile(path, authentication);
 
        return sFile.isDirectory();
    }
 
    /**
     * Create a new directory on the Samba Server
     * @param path the path of the directory
     * @throws java.lang.Exception
     */
    public void createDir(String path) throws Exception
    {
       SmbFile sFile = new SmbFile(path, authentication);
       
       sFile.mkdir();
    }
    
    /**
     * create a new file on the Samba Server
     * @param path - the path of the file
     * @param content - the file contents
     * @throws Exception
     */
    //!!This function could be less wonky
    public void createFile(File file) throws Exception
    {
    	SmbFile sFile = new SmbFile(smbroot+file.getName(), authentication);
		FileInputStream   fis  = new FileInputStream(file);
   	    try {
   	    	SmbFileOutputStream sfos = new SmbFileOutputStream(sFile);  	     
			byte[] b = new byte[32768];
			int n;
			while(( n = fis.read( b )) > 0 ) {
				sfos.write( b, 0, n );	
			}
			fis.close();
   	        sfos.close();
   	    } catch (SmbException e) {
			if (e.getMessage() == "The system cannot find the path specified."){
				String dir = file.getPath().substring(12, file.getPath().lastIndexOf("/"));	   	    	
				String dirs[] = dir.split("/");
				dir = smbroot;
				for(int i = 0; i < dirs.length; i++){
					dir = dir + "/" + dirs[i];
					if(!(isExist(dir))){
						createDir(dir);
					}
				}
				createFile(file);
			}
			//else{System.out.println(e);}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
    }
 
    /**
     * Create a new session backup on the server
     * 
     * @param session - the sessiont o backup
     * @param root - The root directory of the Samba Share (e.g. "smb://hostname/smb/")
     * @throws Exception
     */
    public void backupSession(Session session) throws Exception{
    	//smbroot += session.getSessionName()+"/";
    	if(!(isExist(smbroot))){
    		createDir(smbroot);
    	}
    	//ListIterator<String> i = session.getFileList().listIterator();
    	
    	/**while(i.hasNext()){
    		String filename = i.next();
    		SmbFile sFile = new SmbFile(smbroot+"/"+filename, authentication);    
    		if(sFile.isDirectory()){
    			sFile.mkdir();
    		}
    		else{
    			createFile(filename);
    		}	    	
    	}	*/
    }
    
    
    public void restoreSession(Session session) throws Exception{
   
    	//ListIterator<String> i = session.getFileList().listIterator();
    	/*
    	while(i.hasNext()){
    		String filename = i.next();
    		filename = filename.substring(filename.indexOf("/")+1, filename.length());
    	
    	    File oFile = new File(filename, filename);
    	    if(!(oFile.exists())){
    	    	oFile.getParentFile().mkdirs();
        		oFile.createNewFile();
    	    }
    		FileOutputStream fos = new FileOutputStream(oFile);
        	SmbFile sFile = new SmbFile(smbroot+filename, authentication);
       	    try {
       	    	SmbFileInputStream sfis = new SmbFileInputStream(sFile);  	     
    			byte[] b = new byte[32768];
    			int n;
    			while(( n = sfis.read( b )) > 0 ) {
    				fos.write( b, 0, n );	
    			}
    			sfis.close();
       	        fos.close();
       	    } catch (SmbException e) {}	    	
    	}	*/
    }
    
    /**
     * get a file stored on the Samba Server
     * @param path
     * @param content
     * @throws Exception
     */
    public SmbFile getSmbFile(String path) throws Exception
    {
       SmbFile sFile = new SmbFile(path, authentication);
       return sFile;
    }
    
    /**
     *
     * @param path
     * @throws java.lang.Exception
     */
    public void delete(String path) throws Exception
    {
        SmbFile sFile = new SmbFile(path, authentication);
        sFile.delete();
    }
 
    /**
     *
     * @param path
     * @return
     * @throws java.lang.Exception
     */
    public long size(String path) throws Exception
    {
        SmbFile sFile = new SmbFile(path, authentication);
 
        return sFile.length();
    }
 
    /**
     *
     * @param path
     * @return
     * @throws java.lang.Exception
     */
    public String getFileName(String path) throws Exception
    {
        SmbFile sFile = new SmbFile(path, authentication);
 
        return sFile.getName();
    }
 
    /**
     * @return the domain
     */
    public UniAddress getDomain()
    {
        return domain;
    }
 
    /**
     * @param domain the domain to set
     */
    public void setDomain(UniAddress domain)
    {
        this.domain = domain;
    }
 
    /**
     * @return the authentication
     */
    public NtlmPasswordAuthentication getAuthentication()
    {
        return authentication;
    }
 
    /**
     * @param authentication the authentication to set
     */
    public void setAuthentication(NtlmPasswordAuthentication authentication)
    {
        this.authentication = authentication;
    }
 
}
