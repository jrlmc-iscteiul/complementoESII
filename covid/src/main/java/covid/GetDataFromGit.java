package covid;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.eclipse.jgit.api.CheckoutCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.PathFilter;

public class GetDataFromGit {
	
	List<String> rdfFiles = new ArrayList<>();
	HTMLTable table = new HTMLTable();
	
	public void useGitRepository() throws InvalidRemoteException, TransportException, GitAPIException, IOException {
		
		Git git;
		System.out.println();
		
		File file =  new File("/cloneESII");
		
		if(file.isDirectory() && !file.exists())
			git = Git.cloneRepository().setURI("https://github.com/vbasto-iscte/ESII1920.git").setDirectory(file).call();
		else {
			git = Git.open(new File("/cloneESII"));
			git.pull();
			git.checkout();
		}
		
		Repository repository = git.getRepository();
		
		List<Ref> listTags = git.tagList().call();
		System.out.println(listTags.toString());
		
		List<ObjectId> listFiles = new ArrayList<>();
		
		List<Covid19SpreadingFile> listCovid19SpreadingFile = new ArrayList<Covid19SpreadingFile>();
		Integer i = 0;
		
		 for (Ref ref : listTags) {
			 
			 Covid19SpreadingFile fileToTable;
			 i++;
			 
             System.out.println("Tag: " + ref.getName());
                          
             ObjectId tagFile = repository.resolve(ref.getName());
                                                    
             try (RevWalk revWalk = new RevWalk(repository)) {
                 
            	 RevCommit commit = revWalk.parseCommit(tagFile); 
                 RevTree tree = commit.getTree();
               
                 try (TreeWalk treeWalk = new TreeWalk(repository)) {
                     treeWalk.addTree(tree);
                     treeWalk.setRecursive(true);
                     treeWalk.setFilter(PathFilter.create("covid19spreading.rdf"));
                     
                     if (!treeWalk.next()) 
                         throw new IllegalStateException("Did not find expected file 'covid19spreading.rdf'");
                                         
                     ObjectId objectId = treeWalk.getObjectId(0);
                     listFiles.add(objectId); 
                                       
                     fileToTable = new Covid19SpreadingFile(commit.getAuthorIdent().getWhen().toLocaleString().toString(),"covid19spreading.rdf", ref.getName().replace("refs/tags/", ""), commit.getFullMessage(), "idk too");
                     listCovid19SpreadingFile.add(fileToTable);
                     
                     table.updateHTMLFile(new File("tableCovid.html"), listCovid19SpreadingFile);  
                     
                     try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                 }
             }
         }       
	}

	public static void main(String[] args) {
		GetDataFromGit gdfg = new GetDataFromGit();
		
		try {
			gdfg.useGitRepository();
		} catch (GitAPIException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
