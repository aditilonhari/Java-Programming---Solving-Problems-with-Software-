package C2_Assignmentwk2;

import edu.duke.*;
/** findAllGenes : 
 * A gene for a protein has a start codon, followed by a substring that is a multiple of 3, followed by a stop codon.
 * The program 
 *	1) finds and prints a gene in a strand of DNA 
 *	2) prints the stop codon that identifies the gene. If there is not a gene in the DNA string, then both print the empty string.

 * Algorithm:
 *      1) To find the first gene, find the start codon ATG.
 *	2) Next look immediately past ATG for the first occurrence of each of the three stop codons TAG, TGA, and TAA.
 * 	3) If the length of the substring between ATG and any of these three stop codons is a multiple of three, then a candidate for a gene is the start codon     			through the end of the stop codon. 
 * 	4) If there is more than one valid candidate, the smallest such string is the gene. The gene includes the start and stop codon.
 * 	5) If no start codon was found, then you are done.
 * 	6) If a start codon was found, but no gene was found, then start searching for another gene via the next occurrence of a start codon starting immediately 			after the start codon that didn't yield a gene.
 * 	7) If a gene was found, then start searching for the next gene immediately after this found gene.
 *
 * Example: 
 * 	DNA string is:	CATGTAATAGATGAATGACTGATAGATATGCTTGTATGCTATGAAAATGTGAAATGACCCA
 *	Genes found are: 
 *		ATGTAA
 *		ATGAATGACTGATAG
 * 		ATGCTATGA
 * 		ATGTGA
 *
 * @author Aditi
 * @version 5/14/16
 */

public class findAllGenes {

    // identifies and returns a gene for a protein
    public String findProtein(String dna) {
        String lower_dna = dna.toLowerCase();
        int start = lower_dna.indexOf("atg");
        if (start == -1) {
            return "";
        }
        
        int stop1 = lower_dna.indexOf("tag", start+3);
        int stop2 = lower_dna.indexOf("tga", start+3);
        int stop3 = lower_dna.indexOf("taa", start+3);
        if ((stop1 != -1) && ((stop1 - start) % 3 == 0)) {
            return dna.substring(start, stop1+3);
        }
        else if ((stop2 != -1) && ((stop2 - start) % 3 == 0)) {
            return dna.substring(start, stop2+3);
        }
        else if ((stop3 != -1) && ((stop3 - start) % 3 == 0)) {
            return dna.substring(start, stop3+3);
        }
        else {
            return "";
        }
    }
    
    //finds the first occurrence of each stop codon to the right of index. 
    //  From those stop codons that are a multiple of three from index, it returns the smallest index position.
    //  It returns -1 if no stop codon was found and there is no such position. 
    public int findStopIndex(String dna, int index){
        String lower_dna = dna.toLowerCase(); 
        
        int stop1 = lower_dna.indexOf("tag", index);
        int stop2 = lower_dna.indexOf("tga", index);
        int stop3 = lower_dna.indexOf("taa", index);
        int min = dna.length();
        
        if ((stop1 != -1) && ((stop1 - index) % 3 == 0)) {
            if(stop1 < min) 
                min = stop1;
        }
         
        if ((stop2 != -1) && ((stop2 - index) % 3 == 0)) {
            if(stop2 < min) 
                min = stop2;
        }
        
        if ((stop3 != -1) && ((stop3 - index) % 3 == 0)) {
            if(stop3 < min) 
                min = stop3;
        }
        
        if(min == dna.length())
            return -1;
        else
            return min;
    }  
    
    // returns the ratio of C’s and G’s in dna as a fraction of the entire strand of DNA
    public float cgRatio(String dna){
        String lower_dna = dna.toLowerCase();
        
        int count_c = 0, count_g = 0;
        int start = 0;
        while(true){
            int c = lower_dna.indexOf('c', start);
            if(c == -1){
                break;
            }
            count_c ++;
            start = c + 1;
        }
        
        start = 0;
        while(true){
            int g = lower_dna.indexOf('g', start);
            if(g == -1){
                break;
            }
            count_g ++;
            start = g + 1;
        }
        
        float ratio = ((float) count_c + count_g)/ dna.length();
        return ratio;
    }

    // prints all the genes it finds in DNA. This method repeatedly looks for a gene, and if it finds one, prints it and then look for another gene.
    public void printAll(String dna){

        String lower_dna = dna.toLowerCase();
        int start = 0;
        while(true){
            start = lower_dna.indexOf("atg", start);
            if(start == -1)
                break;
            
            int stop = findStopIndex(lower_dna, start+3);
            
            if(stop != -1){
                String gene = dna.substring(start, stop+3);
                System.out.println(gene);
                start = stop + 3;
            }
            else{
                start = start + 3;
            }
        }
    }
    
    // find and store all the genes in this large strand of DNA
    public StorageResource storeAll(String dna){

        StorageResource store = new StorageResource();
        String lower_dna = dna.toLowerCase();
        int start = 0;
        
        while(true){
            start = lower_dna.indexOf("atg", start);
            if(start == -1)
                break;
            
            int stop = findStopIndex(lower_dna, start+3);
            
            if(stop != -1){
                String gene = dna.substring(start, stop+3);
                store.add(gene);
                start = stop + 3;
            }
            else{
                start = start + 3;
            }           
        }
        return store;
    }
    
    public void storeAllCTG(){
        FileResource fr = new FileResource();
        String dna = fr.asString();
        String lower_dna = dna.toLowerCase();
        int start = 0;
        
        int count =0;
        while(true){
            start = lower_dna.indexOf("ctg", start);
            if(start == -1)
                break;
            
            count++;
         
            start = start + 3;
        }
        System.out.println("CTG count: " + count);
    }
    
    public void printGenes(StorageResource sr){
        int count_1 = 0, count_2 = 0;
        int max=0;
        String maxStr = "";
        for(String str : sr.data()){
            
            if(str.length() > max ){ max = str.length();
                maxStr = str;
            }
            
            if(str.length() > 60){
                System.out.println(str);
                count_1 ++;
            }
            
            if(cgRatio(str) > 0.35){
                System.out.println(str);
                count_2 ++;
            }    
        }
        System.out.println("Longest Gene of size " + max + " is " + maxStr);
        System.out.println("Genes greater than 60 nucleotides: " + count_1);
        System.out.println("Genes greater than cgratio 0.35: " + count_2);
    }
    
    public void testStorageFinder(){
        FileResource fr = new FileResource();
        String fr_str = fr.asString();
        StorageResource s1 = storeAll(fr_str);
    
        System.out.println("Print Genes:");
        printGenes(s1);
        System.out.println("Total number of genes: " + s1.size());
    }
    
    public void testFinder() {
        String dna = "CATGTAATAGATGAATGACTGATAGATATGCTTGTATGCTATGAAAATGTGAAATGACCCA";
        System.out.println("DNA String is:"); 
        System.out.println(dna);
        System.out.println("Gene found is:");
        printAll(dna);
    }

}
