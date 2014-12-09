package edu.vtc.cis4150;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.nio.file.Path;

import jcifs.UniAddress;
import jcifs.smb.*;

/**
 * Handler for the samba server
 * @author YOURNAMEHERE
 *
 */

public class SmbHandler
{
    private UniAddress domain;
    private String smbroot = "smb://MISES/smb/";
    private NtlmPasswordAuthentication authentication;

    public SmbHandler(String address, String username, String password) throws Exception
    {
    	jcifs.Config.setProperty( "jcifs.netbios.wins", "75.69.70.180");
    	jcifs.Config.setProperty( "jcifs.smb.client.domain", "MISES");
    	jcifs.Config.setProperty( "jcifs.smb.client.username", "Kraete");
    	jcifs.Config.setProperty( "jcifs.smb.client.password", "Shuckamuck1!");
    	jcifs.Config.setProperty("jcifs.smb.client.snd_buf_size", "32768"); 
        jcifs.Config.setProperty("jcifs.smb.client.rcv_buf_size", "32768");
		jcifs.Config.setProperty( "jcifs.netbios.wins", address);
        setAuthentication(new NtlmPasswordAuthentication(
                address, username, password));
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
	 * create a new file on the Samba Server
	 * @param path - the path of the file
	 * @param content - the file contents
	 * @throws Exception
	 */
	//!!This function could be less wonky
	public void createFile(File file) throws Exception
	{
		SmbFile         sFile = new SmbFile(smbroot+file.getName(), authentication);
		File file2 = new File(file.getPath());
		FileInputStream fis   = new FileInputStream(file2);
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
     *
     * @param path
     * @throws java.lang.Exception
     */
    public void deleteFile(File file) throws Exception
    {
        SmbFile sFile = new SmbFile(smbroot+file.getName(), authentication);
        sFile.delete();
    } 
  
    /**
	 * get a file stored on the Samba Server
	 * @param path
	 * @param content
	 * @throws Exception
	 */
	public File getFile(Path target, File file) throws Exception
	{
		SmbFile sFile = new SmbFile(smbroot+file.getPath().toString().substring(5, file.getPath().toString().lastIndexOf(".")), getAuthentication());
        BufferedInputStream sfis = new BufferedInputStream(new SmbFileInputStream(sFile));
        FileOutputStream fos = new FileOutputStream(target.toString());
		byte[] b = new byte[32768];
		int n;
		while(( n = sfis.read( b )) > 0 ) {
			fos.write( b, 0, n );	
		}
		sfis.close();
        fos.close();
	    return file;
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
