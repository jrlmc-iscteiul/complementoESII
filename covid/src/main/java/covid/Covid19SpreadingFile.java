package covid;

import java.net.URL;

public class Covid19SpreadingFile {
	
	private String fileTimestamp;
	private String fileName;
	private String fileTag;
	private String tagDescription;
	private String spreadVisualizationLink;
	
	public Covid19SpreadingFile(String fileTimestamp, String fileName, String fileTag, String tagDescription,
			String spreadVisualizationLink) {
		this.fileTimestamp = fileTimestamp;
		this.fileName = fileName;
		this.fileTag = fileTag;
		this.tagDescription = tagDescription;
		this.spreadVisualizationLink = spreadVisualizationLink;
	}
		
	public String getFileTimestamp() {
		return fileTimestamp;
	}
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileTag() {
		return fileTag;
	}

	public void setFileTag(String fileTag) {
		this.fileTag = fileTag;
	}

	public String getTagDescription() {
		return tagDescription;
	}

	public void setTagDescription(String tagDescription) {
		this.tagDescription = tagDescription;
	}

	public String getSpreadVisualizationLink() {
		return spreadVisualizationLink;
	}

	public void setSpreadVisualizationLink(String spreadVisualizationLink) {
		this.spreadVisualizationLink = spreadVisualizationLink;
	}


	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Covid File: Timestamp: " + this.fileTimestamp + "; Name: " + this.fileName + "; Tag: " + this.fileTag + "; Description: " + this.tagDescription + "; Link: " + this.spreadVisualizationLink);

		return sb.toString();
	}

}
