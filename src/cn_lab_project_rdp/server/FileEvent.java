package cn_lab_project_rdp.server;

import java.io.Serializable;

/* File event for file management */
public class FileEvent implements Serializable {

    public FileEvent() {
    }

    private static final long serialVersionUID = 1L;
    
	/* Parameters for file system */
    private String destinationDirectory;
    private String sourceDirectory;
    private String filename;
    private long fileSize;
    private byte[] fileData;
    private String status;

        /* Destination of the directory */
    public String getDestinationDirectory() {
        return destinationDirectory;
    }
	
	/* Set destination*/
    public void setDestinationDirectory(String destinationDirectory) {
        this.destinationDirectory = destinationDirectory;
    }

	/*  get Source directory */
    public String getSourceDirectory() {
        return sourceDirectory;
    }
   
	/* Set source directory */
    public void setSourceDirectory(String sourceDirectory) {
        this.sourceDirectory = sourceDirectory;
    }

	/* Get file name */
    public String getFilename() {
        return filename;
    }

	/* Set file name */
    public void setFilename(String filename) {
        this.filename = filename;
    }

	/* get file size */
    public long getFileSize() {
        return fileSize;
    }

	/* set file size */
    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

	/* get status */
    public String getStatus() {
        return status;
    }

	/* Set status */
    public void setStatus(String status) {
        this.status = status;
    }

	/* Get file data */
    public byte[] getFileData() {
        return fileData;
    }

	/* Set file data */
    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }
}
