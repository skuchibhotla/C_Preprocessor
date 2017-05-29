//Shashank Kuchibhotla
//CSCI 4220 Assignment 3

import java.io.*;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Preprocessor {
    public static void main(String[] args)
      {
        String filename= "javatest.c";
       // filename.replace("//","\\\\");
        String line = null;
        int i=0;
        String rawtext[] = new String[100000000];
        try{
            FileReader filereader = new FileReader(filename);
            BufferedReader bufferedreader = new BufferedReader(filereader);
            while((line=bufferedreader.readLine())!=null){
                //System.out.println(line);
                rawtext[i]=line;
                i++;
            }
            bufferedreader.close();
        }
        catch(FileNotFoundException ex){
            System.out.println("Unable to open file");
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
	

        int h=0;
        String nocommenttext[]=new String[100000000];
        nocommenttext=rawtext;
        int check_cmnt=0;
        int linenumber=0;
        int check=1;
        for(int repeat=0;repeat<50;repeat++){
        	int length=0;
            for(int j=0;j<nocommenttext.length;j++)
            {
                if(nocommenttext[j]==null){
                    break;
                }
                length++;
            }
        	for(int k=0;k<length;k++)
            {
            	StringBuilder sb = new StringBuilder();
                char[] charArray = nocommenttext[k].toCharArray();
            	if(check_cmnt==0){
            		for(int j=0;j<charArray.length;j++){
                        if(charArray[j]=='/'){
                            if(j<charArray.length){
                            	int go=0;
                                if(charArray[j+1]=='/'){
                                    j=charArray.length;
                                    continue;
                                }
                                else if(charArray[j+1]=='*'){
                                	//System.out.println("we found opening here "+k);
                                    for(int x=j+2;x<charArray.length;x++){
                                        if(charArray[x]=='*'&&charArray[x+1]=='/'){
                                            j=x+1;
                                            go=1;
                                            //System.out.println(" found closing in same line "+k);
                                            break;
                                        }
                                    }
                                    if(go==0){
                                    	//System.out.println("we dint found closing here "+k);
                                    	check_cmnt=1;
                                    	linenumber=k;
                                        break;
                                    }
                                    else{
                                    	continue;
                                    }
                                }
                                sb.append(charArray[j]);
                            }
                        }
                        sb.append(charArray[j]);
                    }
            		nocommenttext[k]=sb.toString();
            	}
            	else{
            		//System.out.println("checking closing here "+k);
            		int move=0;
            		for(int f=0;f<charArray.length;f++){
                        if(charArray[f]=='*'&&charArray[f+1]=='/'){
                            for(int w=f+2;w<charArray.length;w++){
                            	sb.append(charArray[w]);
                            }
                            //System.out.println("found closing here "+k);
                            move=1;
                            check_cmnt=0;
                           //System.out.println("start her "+linenumber+"end here "+k);
                            int liner=k;
                            for(int u=linenumber+1;u<k+1;u++){
                            	//System.out.println("Removing"+nocommenttext[u]);
                            	nocommenttext[u]=" ";
                            }
                            nocommenttext[k]=sb.toString();
                            linenumber=0;
                            break;
                            
                        }
                    }
            		if(move==0){
            			//System.out.println("dint found closing here "+k);
            			
            			continue;
            		}
            	}
            }
        	
        	length=0;
            for(int j=0;j<nocommenttext.length;j++)
            {
                if(nocommenttext[j]==null){
                    break;
                }
                length++;
            }
        
            for(int l=0;l<length;l++){
                StringBuilder sb = new StringBuilder();
                StringBuilder sb1 = new StringBuilder();
                if(nocommenttext[l].toLowerCase().contains("#define ")){
                    char[] charArray = nocommenttext[l].toCharArray();
                    if(charArray[0]=='#'){
                    	for(int y=8;y<charArray.length;y++){
                            if(charArray[y]==' '){
                                break;
                            }
                            sb.append(charArray[y]);
                        }
                        String variable=sb.toString();
                        for(int v=9+variable.length();v<charArray.length;v++){
                            sb1.append(charArray[v]);
                        }
                        String value=sb1.toString();
                        //System.out.println(variable+' '+value);
                        for(int o=l+1;o<length;o++){
                        	int q=0;
                        		Pattern p = Pattern.compile("\"([^\"]*)\"");
                        		Matcher m = p.matcher(nocommenttext[o]);
                        		while (m.find()) {
                        			if(m.group(1).contains(variable)){
                        				q=1;
                        				continue;
                        			}
                        		  
                        		}
                        		if(q==1){
                        			continue;
                        		}
                       		
                       	 	
                        	 if(Arrays.asList(nocommenttext[o].split(" ")).contains(variable)){
                        		 char[] charArray1 = nocommenttext[o].toCharArray();
                        		 char[] vararray = variable.toCharArray();
                                 //System.out.println(nocommenttext[o]);
                        		 int b=0;
                        		 if(charArray1[0]=='#'&&charArray1[1]=='d')
                        		 {
                        			 for(int y=0;y<vararray.length;y++){
                        				 if(charArray1[y+8]==vararray[y]){
                        					b++; 
                        				 }
                        			 }
                        			 if(b==vararray.length){
                        				 continue;
                        			 }
                        			 
                        		 }
                                 nocommenttext[o]=nocommenttext[o].replace(variable, value);
                             }
                        	 if(Arrays.asList(nocommenttext[o].split("[\\(\\)\\+\\-\\*\\;\\,]")).contains(variable)){
                        		 
                        		 nocommenttext[o]=nocommenttext[o].replace(variable, value);
                        	 }
                        	 if(Arrays.asList(nocommenttext[o].split("([\\s,]+)")).contains(variable)){
                        		 
                        		 nocommenttext[o]=nocommenttext[o].replace(variable, value);
                        	 }
                        }
                        nocommenttext[l]="";
                     }
                        
                    }   
                }
           
            for(int e=0;e<length;e++){
                StringBuilder sb = new StringBuilder();
                if(nocommenttext[e].toLowerCase().contains("#include ")){
                    char[] charArray = nocommenttext[e].toCharArray();
                    for(int y=10;y<charArray.length;y++){
                        if(charArray[y]=='\"'){
                            break;
                        }
                        sb.append(charArray[y]);
                    }
                    String file=sb.toString();
                   // System.out.println(file);
                   // file.replace("//","\\\\");
                    String rawtext1[] = new String[10000000];
                    int p=0;
                    try{
                        FileReader filereader = new FileReader(file);
                        BufferedReader bufferedreader = new BufferedReader(filereader);
                        while((line=bufferedreader.readLine())!=null){
                            //System.out.println(line);
                            rawtext1[p]=line;
                            p++;
                        }
                        bufferedreader.close();
                    }
                    catch(FileNotFoundException ex){
                        System.out.println("unable to open include "+file);
                    }
                    catch(IOException ex){
                        ex.printStackTrace();
                    }
                    for(int d=length;d>e;d--){
                        nocommenttext[d+(p-1)]=nocommenttext[d];
                    }
                    
                    for(int k=0;k<p;k++){
                        nocommenttext[e+k]=rawtext1[k];
                    }
                    length=0;
                    for(int j=0;j<nocommenttext.length;j++)
                    {
                        if(nocommenttext[j]==null){
                            break;
                        }
                        length++;
                    }
                }
              
            	for(int k=0;k<length;k++)
                {
                	StringBuilder sb1 = new StringBuilder();
                    char[] charArray = nocommenttext[k].toCharArray();
                	if(check_cmnt==0){
                		for(int j=0;j<charArray.length;j++){
                            if(charArray[j]=='/'){
                                if(j<charArray.length){
                                	int go=0;
                                    if(charArray[j+1]=='/'){
                                        j=charArray.length;
                                        continue;
                                    }
                                    else if(charArray[j+1]=='*'){
                                    	//System.out.println("we found opening here "+k);
                                        for(int x=j+2;x<charArray.length;x++){
                                            if(charArray[x]=='*'&&charArray[x+1]=='/'){
                                                j=x+1;
                                                go=1;
                                                //System.out.println(" found closing in same line "+k);
                                                break;
                                            }
                                        }
                                        if(go==0){
                                        	//System.out.println("we dint found closing here "+k);
                                        	check_cmnt=1;
                                        	linenumber=k;
                                            break;
                                        }
                                        else{
                                        	continue;
                                        }
                                    }
                                    sb1.append(charArray[j]);
                                }
                            }
                            sb1.append(charArray[j]);
                        }
                		nocommenttext[k]=sb1.toString();
                	}
                	else{
                		//System.out.println("checking closing here "+k);
                		int move=0;
                		for(int f=0;f<charArray.length;f++){
                            if(charArray[f]=='*'&&charArray[f+1]=='/'){
                                for(int w=f+2;w<charArray.length;w++){
                                	sb1.append(charArray[w]);
                                }
                                //System.out.println("found closing here "+k);
                                move=1;
                                check_cmnt=0;
                               //System.out.println("start her "+linenumber+"end here "+k);
                                int liner=k;
                                for(int u=linenumber+1;u<k+1;u++){
                                	//System.out.println("Removing"+nocommenttext[u]);
                                	nocommenttext[u]=" ";
                                }
                                nocommenttext[k]=sb1.toString();
                                linenumber=0;
                                break;
                                
                            }
                        }
                		if(move==0){
                			//System.out.println("dint found closing here "+k);
                			
                			continue;
                		}
                	}
                }
            }
        }
        for(int x=0;x<nocommenttext.length;x++)
        {
        	if(nocommenttext[x]==null){
                break;
            }
            else if(nocommenttext[x]=="\\n"||nocommenttext[x]==" "||nocommenttext[x]=="  "||nocommenttext[x]==""){
                continue;
            }
            System.out.println(nocommenttext[x]);
        }
     
      }
}
