import java.util.List;
import java.util.Date;
import java.io.File;

public class Session{

  public List<File> files;

  private int staticSessID;

  private boolean isEncrypted;

  private boolean isCompressed;

  private Date creationDate;

  private Date lastModifiedDate;

  private String backupLocation;

  private Session linkedSess;

  
  public void addFile(File file) {
  }

  public void removeFile(File file) {
  }

  public void clearFiles() {
  }

  public void newOperation() {
  }

  public Session() {
  }

  public File pullFile(File file) {
  return null;
  }

  public File copyFile(File file) {
  return null;
  }

  public List<File> viewFiles() {
  return null;
  }

  public void setBackupLocation(String filepath) {
  }

  public Session getLinkedSession() {
  return null;
  }

  public void setLinkedSession(Session session) {
  }

}