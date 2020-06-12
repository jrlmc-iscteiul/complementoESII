package covid;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

public class HTMLTable {
	
	public String createLine(Covid19SpreadingFile file) {
		StringBuilder sb = new StringBuilder();
		
		sb.append("<td class=\"tg-0lax\">" + file.getFileTimestamp() + "</td>");
		sb.append("<td class=\"tg-0lax\">" + file.getFileName() + "</td> \n");
		sb.append("<td class=\"tg-0lax\">" + file.getFileTag() + "</td> \n");
		sb.append("<td class=\"tg-0lax\">" + file.getTagDescription() + "</td> \n");
		sb.append("<td class=\"tg-0lax\"><a href=\"" + file.getSpreadVisualizationLink() + "\">Covid19Spreading</a></td> \n");
		
		 return sb.toString();
	}
		
	public void updateHTMLFile(File sourceFile, List<Covid19SpreadingFile> list) throws IOException {
		
		File destFile = new File("tableCovid2.html");
				
		try {
			Scanner sc = new Scanner(sourceFile);
			
			FileWriter fw = new FileWriter(destFile);
			PrintWriter pw = new PrintWriter(fw);
			
			while (sc.hasNextLine()) {
				
		        String line = sc.nextLine();
		        
		        if(line.contains("<tbody>")) {
		        	pw.println(line);
		        	
		        	
		        	for (Covid19SpreadingFile elementOFList : list) {
		        		pw.print("<tr> \n");
						pw.println(createLine(elementOFList));
						pw.println("</tr>");
					}
		        
		        	pw.println("</tbody>");
		        	pw.println("</table>");
		        	
		        	break;
		        }		    
		        
		        pw.println(line);
			}
		    
			sc.close();
			pw.flush();
			pw.close();
			
			sourceFile.delete();
			destFile.renameTo(new File("tableCovid.html"));
			
		} catch (IOException ioe) {
			System.out.println(ioe);
		}		
	}
}
