
import org.apache.hadoop.*;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;

import org.apache.hadoop.*;


import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.util.*;

public class InvertedIndex{

        public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException{
            if(args.length !=2){
                System.err.println("Usage: InvertedIndex <input path> <output path>");
                System.exit(-1);
            }
            Job job = new Job();
            job.setJarByClass(InvertedIndex.class);
            job.setJobName("Inverted Index");

            FileInputFormat.addInputPath(job, new Path(args[0]));
            FileOutputFormat.setOutputPath(job, new Path(args[1]));

            job.setMapperClass(InvertedIndexMapper.class);
            job.setReducerClass(InvertedIndexReducer.class);
            job.setOutputKeyClass(Text.class);
            job.setOutputValueClass(Text.class);
            job.waitForCompletion(true);
        }
}
class InvertedIndexReducer extends Reducer<Text, Text, Text, Text>{
public void reduce(Text word, Iterable<Text> docID, Context context)
throws IOException, InterruptedException{
    HashMap<String,Integer> toret =new HashMap<String, Integer>();
        StringBuilder st = new StringBuilder(); 
   for(Text l : docID) {
        //List la = toret.get(word);
        
           // StringBuilder tomake = new StringBuilder();
             if(st.lastIndexOf(l.toString())<0){   
            st.append(" " + ":: " + l);
                //toret.put(l.toString(),new Integer(1));
 //           toret.put(word.toString(), tomake.toString());
  }
//else{
//toret.put(l.toString(),toret.get(l.toString())+1);
//}
        
    //toret.put(word,la);
        
//    context.write(word, new Text(tomake.toString()));
}
context.write(word,new Text(st.toString()));
}


}
 class InvertedIndexMapper extends Mapper<LongWritable, Text,Text, Text> {
    Dictionary<Text,Integer> assocarray = new Hashtable<Text,Integer>();

    private Text word = new Text();

     public void map(LongWritable key, Text value, Context context)
         throws IOException, InterruptedException{

         String line = value.toString();
         StringTokenizer tokenizer = new StringTokenizer(line);

         while(tokenizer.hasMoreTokens()){
             word.set(tokenizer.nextToken());
             String FileName = ((FileSplit)context.getInputSplit()).getPath().getName();
             Text t = new Text(key.toString());
             context.write(word,new Text( FileName));
         }
     }
}