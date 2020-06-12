package covid;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.lib.ObjectId;
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
		List<Covid19SpreadingFile> listCovid19SpreadingFile = new ArrayList<Covid19SpreadingFile>();
		
		 for (Ref ref : listTags) {
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
                                          
                     String url = new String("http://visualdataweb.de/webvowl/#iri=https://github.com/vbasto-iscte/ESII1920/raw/master/covid19spreading.rdf");
                     
                     listCovid19SpreadingFile.add(new Covid19SpreadingFile(commit.getAuthorIdent().getWhen().toLocaleString().toString(),"covid19spreading.rdf", ref.getName().replace("refs/tags/", ""), commit.getFullMessage(), url));
                     
                     table.updateHTMLFile(new File("tableCovid.html"), listCovid19SpreadingFile);  
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
		
		System.out.println("funcionou");
	}
}
