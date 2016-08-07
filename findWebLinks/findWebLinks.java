package C2_Assignmentwk2;

import edu.duke.*;

/**
 * findWebLinks : 
 * The program reads the lines from the file at URL location and prints each URL on the page that is a link to youtube.com.
 * Assume that a link to youtube.com has no spaces in it and would be in the format (where [stuff] represents characters that are not verbatim):
 *	“http:[stuff]youtube.com[stuff]”
 *
 * Algorithm: 
 *	1) Use URLResource and read the file word by word.
 * 	2) For each word, check to see if “youtube.com” is in it. If it is, find the double quote to the left and right of it to identify the URL. 
 * 	Caution: The word Youtube could appear in different cases such as YouTube or youtube or YOUTUBE. You can find the URLs easier by converting the string to   		lowercase. However, you will need the original string (with uppercase and lowercase letters) to view the YouTube URL to answer a quiz question because 		YouTube links are case sensitive.  
 *
 * 
 * @author Aditi
 * @version 5/14/16
 */
public class findWebLinks {

    public void findYoutubeLinks(){
        URLResource url = new URLResource("http://www.dukelearntoprogram.com/course2/data/manylinks.html");
        
        for (String word : url.words()) {
            String lower_word = word.toLowerCase();
            int found = lower_word.indexOf("youtube.com");
            if(found != -1){
                String substr = lower_word.substring(0, found-1);
                int startQuote = substr.lastIndexOf("\"",found);
                int lastQuote = lower_word.indexOf("\"",startQuote+1);
                String youtube = word.substring(startQuote, lastQuote+1);
                System.out.println("Youtube URL found: " + youtube);
            }
        }
    }

    public StorageResource findURLs(String url){
        URLResource webPage = new URLResource(url);
        
        StorageResource allURLs = new StorageResource();
        for (String word : webPage.words()) {
            String lower_word = word.toLowerCase();
            int found = lower_word.indexOf("href=");
            if(found != -1){
                int startQuote = lower_word.indexOf("\"",found);
                int lastQuote = lower_word.indexOf("\"",startQuote+1);
                if(lastQuote == -1)
                    lastQuote = lower_word.length();
                String subStr = lower_word.substring(startQuote+1, lastQuote);
                if(subStr.startsWith("http")){
                    allURLs.add(word.substring(startQuote+1, lastQuote));
                }
            }
        }
        return allURLs;
    }

    public void testURLWithStorage(){
       
        StorageResource sr = findURLs("http://www.dukelearntoprogram.com/course2/data/newyorktimes.html");
        
        for(String subURL : sr.data()){
            System.out.println(subURL);        
        }
        
        System.out.println("Total urls: " + sr.size());
        int countSecuredURLs = 0;
        for(String subURL : sr.data()){
            if(subURL.startsWith("https"))
                countSecuredURLs ++;
        }
        System.out.println("Total secured urls: "+ countSecuredURLs);
        
        int countComAll = 0, countCom = 0;
        for(String subURL : sr.data()){
            int found = subURL.indexOf(".com");
            if(found != -1)
                countComAll++;
                
            boolean foundCom = subURL.endsWith(".com");
            boolean foundComSlash = subURL.endsWith(".com/");
            if(foundCom || foundComSlash)
                countCom++;
        }
        System.out.println("Total urls with .com: " + countComAll);
        System.out.println("Total urls ending with .com and .com/: " + countCom);       
        
        int countDot = 0;
        for(String subURL : sr.data()){
            int start = 0;
            while(true){
                int dot = subURL.indexOf('.', start);
                if(dot == -1)
                    break;
                countDot ++;
                start = dot + 1;
            }
        }
        System.out.println("Total '.' in all links: " + countDot); 
    }
}
