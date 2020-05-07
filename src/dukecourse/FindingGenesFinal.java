package dukecourse;

import edu.duke.*;

public class FindingGenesFinal {

    int findStopCodon(String dna, int startIndex, String stopCodon)
    {
        int currIndex = dna.indexOf(stopCodon, startIndex+3);
        while(currIndex != -1)
        {
            int diff = currIndex - startIndex;
            if(diff % 3 == 0)
            {
                return currIndex;
            }
            else
            {
                currIndex = dna.indexOf(stopCodon, currIndex+1);
            }
        }
        return dna.length();
    }
    
    void printAllGene(String dna)
    {
        int startIndex = 0;
        while(true)
        {
            String currentGene = findGene(dna, startIndex);
            
            if(currentGene.isEmpty())
                break;
            
            System.out.println(currentGene);
            
            startIndex = (dna.indexOf(currentGene, startIndex) + currentGene.length()); 
        }
    }
    
    String findGene(String dna, int where)
    {
        int startIndex = dna.indexOf("ATG", where);
        if(startIndex == -1)
            return "";
        
        int taaIndex = findStopCodon(dna, startIndex, "TAA");
        int tagIndex = findStopCodon(dna, startIndex, "TAG");
        int tgaIndex = findStopCodon(dna, startIndex, "TGA");
        
        int minIndex = 0;
        
        minIndex = Math.min(taaIndex,Math.min(tagIndex, tgaIndex));
        
        if(minIndex == -1 || minIndex == dna.length())
            return "";
        
        return dna.substring(startIndex, minIndex+3);
    }
    
    StorageResource getAllGene(String dna)
    {
        StorageResource geneList = new StorageResource();
        int startIndex = 0;
        while(true)
        {
            String currentGene = findGene(dna, startIndex);
            
            if(currentGene.isEmpty())
                break;
            else
            {
                geneList.add(currentGene);
            }
            startIndex = dna.indexOf(currentGene, startIndex) + currentGene.length(); 
        }
        return geneList;
    }
    
    double cgRatio(String dna)
    {
        int length = dna.length();
        double count = 0;
        int startc = 0;
        int startg = 0;
        int posc,posg;
        while (true) 
        {
            posc = dna.indexOf("C", startc);
            posg = dna.indexOf("G", startg);
            if(posc == -1 && posg == -1)
                break;
            else
                {
                    if(posc != -1)
                        {
                            count++;
                            startc = posc + 1;
                        }
                    if(posg != -1)
                        {
                            count++;
                            startg = posg + 1;
                        }
                }           
        }
        
        return count/length;
    }
    
    void ctgCount(String dna)
    {
        int count = 0;
        int start = 0;
        int  i = 0;
        int pos; 
        while(true)
        {   
            pos = dna.indexOf("CTG",start);
            if(pos == -1)
                break;
            else{
                count++;
            }
            start = pos+3;
        }
        System.out.println("CTG - " + count);
    }
    
    /*void testFindStopCodon()
    {
        String dna = "AAATGGTAACTATAATAA";
        //            012345678901234567
        int gene = findStopCodon(dna,2,"TAA");
        System.out.println("Gene - " + gene);
        
        dna = "AAGTAACTATAATAA";
        gene = findStopCodon(dna,2,"TAA");
        System.out.println("Gene - " + gene);
        
        //       V  V  V  V  V V 
        dna = "AAATGGCAACTAGCTAACGTACT";
        //     01234567890123456789012
        gene = findStopCodon(dna,2,"TAA");
        System.out.println("Gene - " + gene);
    }*/
    
    /*void testFindGene()
    {
        String dna = "AATGCTACAAAACCCGGGTAACC";
        System.out.println("DNA - " + dna);
        //System.out.println("Gene -" + findGene(dna));
        
        dna = "AACCATGGCTTACTAACCAATT";
        System.out.println("DNA - " + dna);
        //System.out.println("Gene -" + findGene(dna));
        
        dna = "AACCATGGGCCCTGCTAGGGGTAAAAA";
        System.out.println("DNA - " + dna);
       // System.out.println("Gene -" + findGene(dna));
        
        dna = "ATGAAACCCGGGTACTGAGGG";
        System.out.println("DNA - " + dna);
       // System.out.println("Gene -" + findGene(dna));
    }*/
    
    void testGetAllGenes()
    {
        //            V     V  V        V  V     V  
        String dna = "ATGAAATAAATGCCCGGGTAGATGTCCTGA";
        //            012345678901234567890123456789
        
        System.out.println("DNA - " + dna.toUpperCase());
        //printAllGene(dna.toUpperCase());
        StorageResource gene = getAllGene(dna.toUpperCase());
        for(String s : gene.data())
            System.out.println("Gene - " + s + " " + s.length());
        processGenes(gene);
    }
    
    void testCGRatio()
    {
        System.out.print("\u000C");
        
        double result = cgRatio("ATGONECCCGGGAAAXXXYYYGGGGTAGYYCTGCCCATGENDZZZTAA");
        System.out.println(result);
    }
    
    void processGenes(StorageResource sr)
    {
    int count1 = 0, count2 = 0;
    int maxLength = 0;
    int i = 0;
        for(String s : sr.data())
        {
            i++;
            double cgratio = cgRatio(s);
            int length = s.length();
            if(length > 60 || cgratio > 0.35)
            {
                System.out.println(s);
                if(length > 60)
                    count1++;
                if(cgratio > 0.35)
                    count2++;
            }
            maxLength = Math.max(maxLength, s.length());
        }
        System.out.println("No. of Genes having length > 60 - " + count1);
        System.out.println("No. of Genes having CG Ratio > 0.35 - " + count2);
        System.out.println("Longest Gene - " + maxLength);
        System.out.println("Total Genes - " + i);
    }
    
    void testProcessGeneTemp()
    {
        StorageResource genes = new StorageResource();
        genes.add("ATGGCCGGGTAA");          //CG ratio : 0.5833   Length : 12
        genes.add("ATGGCCGGGAAATAA");       //CG ratio : 0.4666   Length : 15
        genes.add("ATGGAAAAAAAATAA");       //CG ratio : 0.1333   Length : 15
        genes.add("ATGTAA");                //CG ratio : 0.1666   Length : 6
        genes.add("ATGGCCTTTCCCAAATAG");    //CG ratio : 0.4444   Length : 18
        processGenes(genes);
    }
    
    void testProcessGeneFinal()
    {
        FileResource fr = new FileResource();
        String dna = fr.asString();
        StorageResource sr = getAllGene(dna.toUpperCase());
        processGenes(sr);
        ctgCount(dna.toUpperCase());
    }
    public static void main(String[] args) {
        FindingGenesFinal obj = new FindingGenesFinal();
        obj.testProcessGeneFinal();
    }
    
}
