package hi;


//import com.google.auth.Credentials;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

import com.google.api.gax.paging.Page;
import com.google.api.services.dataproc.Dataproc;
import com.google.api.services.dataproc.model.HadoopJob;
import com.google.api.services.dataproc.model.Job;
import com.google.api.services.dataproc.model.JobPlacement;
import com.google.api.services.dataproc.model.SubmitJobRequest;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.WriteChannel;
import com.google.cloud.storage.*;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.hash.Hashing;
import com.google.common.hash.HashingOutputStream;
import com.google.common.io.BaseEncoding;
import com.google.common.primitives.Ints;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import java.io.*;
import java.nio.ByteBuffer;
import java.security.DigestOutputStream;
import java.security.MessageDigest;


/**
 * Sample for Google Cloud Storage JSON API client.
 */
class panel3 extends JPanel{
    panel3(){
        final JTextField Wordtolookup = new JTextField("Lookup word");
        add(Wordtolookup);
        final JButton thebutton= new JButton("lookup for ShakeSpeare WAIT A MINUTE TO RETRIEVE");

        thebutton.addActionListener(new Action() {
            public Object getValue(String key) {
                return null;
            }

            public void putValue(String key, Object value) {

            }

            public void setEnabled(boolean b) {

            }

            public boolean isEnabled() {
                return false;
            }

            public void addPropertyChangeListener(PropertyChangeListener listener) {

            }

            public void removePropertyChangeListener(PropertyChangeListener listener) {

            }

            public void actionPerformed(ActionEvent e) {
                GoogleCredentials credentialds = null;
                try {
                    credentialds = GoogleCredentials.fromStream(new FileInputStream("C:\\Users\\DGC20\\Downloads\\FinalProject1660-33c376fddfe9.json"))
                            .createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                Storage storage = StorageOptions.newBuilder().setCredentials(credentialds).build().getService();
                String bucketname = "dataproc-staging-us-west1-873726945575-r8fekrze";
                String targetName = "Output.txt";
                String source1 = "Data/Hugo/OWC/part-r-00000";
                String source2 = "Data/Hugo/OWC/part-r-00001";
                String source3 = "Data/Hugo/OWC/part-r-00002";
                String source4 = "Data/Hugo/OWC/part-r-00003";
                String source5 = "Data/Hugo/OWC/part-r-00004";
                String source6 = "Data/Hugo/OWC/part-r-00005";
                String source7 = "Data/Hugo/OWC/part-r-00006";
                BlobId id = BlobId.of(bucketname,targetName);
                BlobInfo bi = BlobInfo.newBuilder(id).setContentType("text/plain").build();
                Blob bf=  storage.create(bi);
                System.out.println(bf.getName());
                Storage.ComposeRequest f = Storage.ComposeRequest.newBuilder().setTarget(bi).addSource(source1)
                        .addSource(source2)
                        .addSource(source3)
                        .addSource(source4)
                        .addSource(source5)
                        .addSource(source6)
                        .addSource(source7).build();
                storage.compose(f);
                BlobId id2 = BlobId.of(bucketname,targetName);
                Blob out = storage.get(id2);
                BlobInfo blobinfo = BlobInfo.newBuilder(id2).setContentType("text/plain").build();

                byte[] info = storage.readAllBytes(id2);
                String toout = new String(info);
                System.out.println(thebutton.getText());
                String jp= toout.substring(toout.indexOf("\n"+Wordtolookup.getText()+"\t ::")+1,toout.indexOf("\n"+Wordtolookup.getText()+"\t ::")+ 500);
                String neww = jp.substring(0,jp.indexOf("\n"));
                JOptionPane.showMessageDialog (new panel1(),neww);
            }



});add(thebutton);
    }
}
class panel2 extends JPanel{
    panel2(){
        JButton thebutton= new JButton("Create Index for ShakeSpeare WAIT A MINUTE TO RETRIEVE");

        thebutton.addActionListener(new Action() {
            public Object getValue(String key) {
                return null;
            }

            public void putValue(String key, Object value) {

            }

            public void setEnabled(boolean b) {

            }

            public boolean isEnabled() {
                return false;
            }

            public void addPropertyChangeListener(PropertyChangeListener listener) {

            }

            public void removePropertyChangeListener(PropertyChangeListener listener) {

            }

            public void actionPerformed(ActionEvent e) {
                System.out.println("ok");
                GoogleCredentials credentialds = null;
                try {
                    credentialds = GoogleCredentials.fromStream(new FileInputStream("C:\\Users\\DGC20\\Downloads\\FinalProject1660-33c376fddfe9.json"))
                            .createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                Storage storage = StorageOptions.newBuilder().setCredentials(credentialds).build().getService();
                // Blob blobs = storage.list("dataproc-staging-us-west1-873726945575-r8fekrze");
                System.out.println("Buckets:");
                Page<Blob> buckets = storage.list("dataproc-staging-us-west1-873726945575-r8fekrze");
                for (Blob bucket : buckets.iterateAll()) {
                    System.out.println(bucket.toString());
                    if(bucket.getName().contains("Data/Hugo/OWC/")){
                        boolean delete= storage.delete(bucket.getBlobId());
                        System.out.println(delete);
                    }
                    //   BlobId b =bucket.getBlobId();
                    //  storage.delete(b);
                    //System.out.println("dfone");
                }
                GoogleCredentials credentials = null;
                try {
                    credentials = GoogleCredentials.fromStream(new FileInputStream("C:\\Users\\DGC20\\Downloads\\FinalProject1660-6dd845109432.json"))
                            .createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                HttpRequestInitializer requestInitializer = new HttpCredentialsAdapter(credentials);
                //  Dataproc dataproc = new Dataproc.Builder(new NetHttpTransport(),new JacksonFactory(), requestInitializer).build();
                System.out.println("ok");
                Dataproc dataproc = new Dataproc.Builder(new NetHttpTransport(), new JacksonFactory(), requestInitializer).build();
                try {
                    dataproc.projects().regions().jobs().submit(
                            "finalproject1660", "us-west1", new SubmitJobRequest()
                                    .setJob(new Job().setPlacement(new JobPlacement()
                                            .setClusterName("cluster-cc4a"))
                                            .setHadoopJob(new HadoopJob()
                                                    .setMainClass("InvertedIndex")
                                                    .setJarFileUris(ImmutableList.of("gs://dataproc-staging-us-west1-873726945575-r8fekrze/JAeRp1/Index12.jar"))
                                                    .setArgs(ImmutableList.of(
                                                            "gs://dataproc-staging-us-west1-873726945575-r8fekrze/Data/Shake/comed", "gs://dataproc-staging-us-west1-873726945575-r8fekrze/Data/Hugo/OWC")))))
                            .execute();

                    System.out.println("done");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                boolean istrue= false;
                while(!istrue) {
                    Page<Blob> buceket = storage.list("dataproc-staging-us-west1-873726945575-r8fekrze");
                    for (Blob bucekeet : buckets.iterateAll()) {
                        if (bucekeet.getName().equals("Data/Hugo/OWC/part-r-00000")) {
                            istrue = true;
                        }
                    }
                }

            }
        }); add(thebutton);
    }
}class panel5 extends JPanel{
    panel5(){
        final JTextField Wordtolookup = new JTextField("Lookup word");
        add(Wordtolookup);
        final JButton thebutton= new JButton("lookup for tolly WAIT A MINUTE TO RETRIEVE");

        thebutton.addActionListener(new Action() {
            public Object getValue(String key) {
                return null;
            }

            public void putValue(String key, Object value) {

            }

            public void setEnabled(boolean b) {

            }

            public boolean isEnabled() {
                return false;
            }

            public void addPropertyChangeListener(PropertyChangeListener listener) {

            }

            public void removePropertyChangeListener(PropertyChangeListener listener) {

            }

            public void actionPerformed(ActionEvent e) {
                GoogleCredentials credentialds = null;
                try {
                    credentialds = GoogleCredentials.fromStream(new FileInputStream("C:\\Users\\DGC20\\Downloads\\FinalProject1660-33c376fddfe9.json"))
                            .createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                Storage storage = StorageOptions.newBuilder().setCredentials(credentialds).build().getService();
                String bucketname = "dataproc-staging-us-west1-873726945575-r8fekrze";
                String targetName = "Output.txt";
                String source1 = "Data/Hugo/OWC/part-r-00000";
                String source2 = "Data/Hugo/OWC/part-r-00001";
                String source3 = "Data/Hugo/OWC/part-r-00002";
                String source4 = "Data/Hugo/OWC/part-r-00003";
                String source5 = "Data/Hugo/OWC/part-r-00004";
                String source6 = "Data/Hugo/OWC/part-r-00005";
                String source7 = "Data/Hugo/OWC/part-r-00006";
                BlobId id = BlobId.of(bucketname,targetName);
                BlobInfo bi = BlobInfo.newBuilder(id).setContentType("text/plain").build();
                Blob bf=  storage.create(bi);
                System.out.println(bf.getName());
                Storage.ComposeRequest f = Storage.ComposeRequest.newBuilder().setTarget(bi).addSource(source1)
                        .addSource(source2)
                        .addSource(source3)
                        .addSource(source4)
                        .addSource(source5)
                        .addSource(source6)
                        .addSource(source7).build();
                storage.compose(f);
                BlobId id2 = BlobId.of(bucketname,targetName);
                Blob out = storage.get(id2);
                BlobInfo blobinfo = BlobInfo.newBuilder(id2).setContentType("text/plain").build();

                byte[] info = storage.readAllBytes(id2);
                String toout = new String(info);
                System.out.println(thebutton.getText());
                String jp= toout.substring(toout.indexOf("\n"+Wordtolookup.getText()+"\t ::")+1,toout.indexOf("\n"+Wordtolookup.getText()+"\t ::")+ 500);
                String neww = jp.substring(0,jp.indexOf("\n"));
                JOptionPane.showMessageDialog (new panel1(),neww);
            }



        });add(thebutton);
    }
}
class panel4 extends JPanel{
    panel4(){
        JButton thebutton= new JButton("Create Index for tollyboi WAIT A MINUTE TO RETRIEVE");

        thebutton.addActionListener(new Action() {
            public Object getValue(String key) {
                return null;
            }

            public void putValue(String key, Object value) {

            }

            public void setEnabled(boolean b) {

            }

            public boolean isEnabled() {
                return false;
            }

            public void addPropertyChangeListener(PropertyChangeListener listener) {

            }

            public void removePropertyChangeListener(PropertyChangeListener listener) {

            }

            public void actionPerformed(ActionEvent e) {
                System.out.println("ok");
                GoogleCredentials credentialds = null;
                try {
                    credentialds = GoogleCredentials.fromStream(new FileInputStream("C:\\Users\\DGC20\\Downloads\\FinalProject1660-33c376fddfe9.json"))
                            .createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                Storage storage = StorageOptions.newBuilder().setCredentials(credentialds).build().getService();
                // Blob blobs = storage.list("dataproc-staging-us-west1-873726945575-r8fekrze");
                System.out.println("Buckets:");
                Page<Blob> buckets = storage.list("dataproc-staging-us-west1-873726945575-r8fekrze");
                for (Blob bucket : buckets.iterateAll()) {
                    System.out.println(bucket.toString());
                    if(bucket.getName().contains("Data/Hugo/OWC/")){
                        boolean delete= storage.delete(bucket.getBlobId());
                        System.out.println(delete);
                    }
                    //   BlobId b =bucket.getBlobId();
                    //  storage.delete(b);
                    //System.out.println("dfone");
                }
                GoogleCredentials credentials = null;
                try {
                    credentials = GoogleCredentials.fromStream(new FileInputStream("C:\\Users\\DGC20\\Downloads\\FinalProject1660-6dd845109432.json"))
                            .createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                HttpRequestInitializer requestInitializer = new HttpCredentialsAdapter(credentials);
                //  Dataproc dataproc = new Dataproc.Builder(new NetHttpTransport(),new JacksonFactory(), requestInitializer).build();
                System.out.println("ok");
                Dataproc dataproc = new Dataproc.Builder(new NetHttpTransport(), new JacksonFactory(), requestInitializer).build();
                try {
                    dataproc.projects().regions().jobs().submit(
                            "finalproject1660", "us-west1", new SubmitJobRequest()
                                    .setJob(new Job().setPlacement(new JobPlacement()
                                            .setClusterName("cluster-cc4a"))
                                            .setHadoopJob(new HadoopJob()
                                                    .setMainClass("InvertedIndex")
                                                    .setJarFileUris(ImmutableList.of("gs://dataproc-staging-us-west1-873726945575-r8fekrze/JAeRp1/Index12.jar"))
                                                    .setArgs(ImmutableList.of(
                                                            "gs://dataproc-staging-us-west1-873726945575-r8fekrze/Data/Tolstoy", "gs://dataproc-staging-us-west1-873726945575-r8fekrze/Data/Hugo/OWC")))))
                            .execute();

                    System.out.println("done");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                boolean istrue= false;
                while(!istrue) {
                    Page<Blob> buceket = storage.list("dataproc-staging-us-west1-873726945575-r8fekrze");
                    for (Blob bucekeet : buckets.iterateAll()) {
                        if (bucekeet.getName().equals("Data/Hugo/OWC/part-r-00000")) {
                            istrue = true;
                        }
                    }
                }

            }
        }); add(thebutton);
    }
}
class panel1 extends JPanel{
    panel1(){
final JTextPane Wordtolookup = new JTextPane();
Wordtolookup.setText("What Word to look up");
final JButton thebutton = new JButton("Whattolookup");
thebutton.addActionListener(new Action() {
    public Object getValue(String key) {
        return null;
    }

    public void putValue(String key, Object value) {

    }

    public void setEnabled(boolean b) {

    }

    public boolean isEnabled() {
        return false;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {

    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {

    }

    public void actionPerformed(ActionEvent e) {
        GoogleCredentials credentialds = null;
        try {
            credentialds = GoogleCredentials.fromStream(new FileInputStream("C:\\Users\\DGC20\\Downloads\\FinalProject1660-33c376fddfe9.json"))
                    .createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        Storage storage = StorageOptions.newBuilder().setCredentials(credentialds).build().getService();
        String bucketname = "dataproc-staging-us-west1-873726945575-r8fekrze";
        String targetName = "Output.txt";
        String source1 = "Data/Hugo/OWC/part-r-00000";
        String source2 = "Data/Hugo/OWC/part-r-00001";
        String source3 = "Data/Hugo/OWC/part-r-00002";
        String source4 = "Data/Hugo/OWC/part-r-00003";
        String source5 = "Data/Hugo/OWC/part-r-00004";
        String source6 = "Data/Hugo/OWC/part-r-00005";
        String source7 = "Data/Hugo/OWC/part-r-00006";
        BlobId id = BlobId.of(bucketname,targetName);
        BlobInfo bi = BlobInfo.newBuilder(id).setContentType("text/plain").build();
        Blob bf=  storage.create(bi);
        System.out.println(bf.getName());
        Storage.ComposeRequest f = Storage.ComposeRequest.newBuilder().setTarget(bi).addSource(source1)
                .addSource(source2)
                .addSource(source3)
                .addSource(source4)
                .addSource(source5)
                .addSource(source6)
                .addSource(source7).build();
        storage.compose(f);
        BlobId id2 = BlobId.of(bucketname,targetName);
        Blob out = storage.get(id2);
        BlobInfo blobinfo = BlobInfo.newBuilder(id2).setContentType("text/plain").build();

        byte[] info = storage.readAllBytes(id2);
        String toout = new String(info);
        System.out.println(thebutton.getText());
        String jp= toout.substring(toout.indexOf("\n"+Wordtolookup.getText()+"\t ::")+1,toout.indexOf("\n"+Wordtolookup.getText()+"\t ::")+ 500);
        String neww = jp.substring(0,jp.indexOf("\n"));
        JOptionPane.showMessageDialog (new panel1(),neww);
    }
});
add(Wordtolookup);
add(thebutton);
    }

        }
public class HaddopFrame extends JFrame {
    public HaddopFrame(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        JTabbedPane t = new JTabbedPane();
        this.setContentPane(t);

        JTabbedPane pp = new JTabbedPane();
        JButton hugoII = new JButton();
        JButton hugo2 = new JButton();
        t.add(new panel1(),"Search Hugo");
        t.add(new panel2(), "Create Shake index");
        t.add(new panel3(), "Lookup Shake word");
        t.add(new panel4(), "Index tolstoy");
        t.add(new panel5(), "Lookup Tolstoy");
        hugo2.setText("hello");
        hugoII.setText("Click to send Hugo.tar.gz WAIT A MINUTE TO RETRIEVE INDEX!!!!!");
        hugo2.addActionListener(new Action() {
            public Object getValue(String key) {
                return null;
            }

            public void putValue(String key, Object value) {

            }

            public void setEnabled(boolean b) {

            }

            public boolean isEnabled() {
                return false;
            }

            public void addPropertyChangeListener(PropertyChangeListener listener) {

            }

            public void removePropertyChangeListener(PropertyChangeListener listener) {

            }

            public void actionPerformed(ActionEvent e) {
                GoogleCredentials credentialds = null;
                try {
                    credentialds = GoogleCredentials.fromStream(new FileInputStream("C:\\Users\\DGC20\\Downloads\\FinalProject1660-33c376fddfe9.json"))
                            .createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                Storage storage = StorageOptions.newBuilder().setCredentials(credentialds).build().getService();
                String bucketname = "dataproc-staging-us-west1-873726945575-r8fekrze";
                String targetName = "Output.txt";
                String source1 = "Data/Hugo/OWC/part-r-00000";
                String source2 = "Data/Hugo/OWC/part-r-00001";
                String source3 = "Data/Hugo/OWC/part-r-00002";
                String source4 = "Data/Hugo/OWC/part-r-00003";
                String source5 = "Data/Hugo/OWC/part-r-00004";
                String source6 = "Data/Hugo/OWC/part-r-00005";
                String source7 = "Data/Hugo/OWC/part-r-00006";
                BlobId id = BlobId.of(bucketname,targetName);
                BlobInfo bi = BlobInfo.newBuilder(id).setContentType("text/plain").build();
                Blob bf=  storage.create(bi);
                System.out.println(bf.getName());
                Storage.ComposeRequest f = Storage.ComposeRequest.newBuilder().setTarget(bi).addSource(source1)
                        .addSource(source2)
                        .addSource(source3)
                        .addSource(source4)
                        .addSource(source5)
                        .addSource(source6)
                        .addSource(source7).build();
                storage.compose(f);
                BlobId id2 = BlobId.of(bucketname,targetName);
                Blob out = storage.get(id2);
                BlobInfo blobinfo = BlobInfo.newBuilder(id2).setContentType("text/plain").build();

                byte[] info = storage.readAllBytes(id2);
                String toout = new String(info);
               String jp= toout.substring(toout.indexOf("\nKing\t ::")+1,toout.indexOf("\nKing\t ::")+ 500);
                String neww = jp.substring(0,jp.indexOf("\n"));
                System.out.println(neww);
            }
        });
        setSize(400,400);
        hugoII.addActionListener(new Action() {

            public Object getValue(String key) {
                return null;
            }

            public void putValue(String key, Object value) {

            }

            public void setEnabled(boolean b) {

            }

            public boolean isEnabled() {
                return false;
            }

            public void addPropertyChangeListener(PropertyChangeListener listener) {

            }

            public void removePropertyChangeListener(PropertyChangeListener listener) {

            }

            public void actionPerformed(ActionEvent e) {

                System.out.println("ok");
                GoogleCredentials credentialds = null;
                try {
                    credentialds = GoogleCredentials.fromStream(new FileInputStream("C:\\Users\\DGC20\\Downloads\\FinalProject1660-33c376fddfe9.json"))
                            .createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                Storage storage = StorageOptions.newBuilder().setCredentials(credentialds).build().getService();
                // Blob blobs = storage.list("dataproc-staging-us-west1-873726945575-r8fekrze");
                System.out.println("Buckets:");
                Page<Blob> buckets = storage.list("dataproc-staging-us-west1-873726945575-r8fekrze");
                for (Blob bucket : buckets.iterateAll()) {
                    System.out.println(bucket.toString());
                    if(bucket.getName().contains("Data/Hugo/OWC/")){
                        boolean delete= storage.delete(bucket.getBlobId());
                        System.out.println(delete);
                    }
                    //   BlobId b =bucket.getBlobId();
                    //  storage.delete(b);
                    //System.out.println("dfone");
                }
                GoogleCredentials credentials = null;
                try {
                    credentials = GoogleCredentials.fromStream(new FileInputStream("C:\\Users\\DGC20\\Downloads\\FinalProject1660-6dd845109432.json"))
                            .createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                HttpRequestInitializer requestInitializer = new HttpCredentialsAdapter(credentials);
                //  Dataproc dataproc = new Dataproc.Builder(new NetHttpTransport(),new JacksonFactory(), requestInitializer).build();
                System.out.println("ok");
                Dataproc dataproc = new Dataproc.Builder(new NetHttpTransport(), new JacksonFactory(), requestInitializer).build();
                try {
                    dataproc.projects().regions().jobs().submit(
                            "finalproject1660", "us-west1", new SubmitJobRequest()
                                    .setJob(new Job().setPlacement(new JobPlacement()
                                            .setClusterName("cluster-cc4a"))
                                            .setHadoopJob(new HadoopJob()
                                                    .setMainClass("InvertedIndex")
                                                    .setJarFileUris(ImmutableList.of("gs://dataproc-staging-us-west1-873726945575-r8fekrze/JAeRp1/Index12.jar"))
                                                    .setArgs(ImmutableList.of(
                                                            "gs://dataproc-staging-us-west1-873726945575-r8fekrze/Data/Hugo", "gs://dataproc-staging-us-west1-873726945575-r8fekrze/Data/Hugo/OWC")))))
                            .execute();

                    System.out.println("done");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                boolean istrue= false;
                while(!istrue) {
                    Page<Blob> buceket = storage.list("dataproc-staging-us-west1-873726945575-r8fekrze");
                    for (Blob bucekeet : buckets.iterateAll()) {
                        if (bucekeet.getName().equals("Data/Hugo/OWC/part-r-00000")) {
                            istrue = true;
                        }
                    }
                }


            }
        });
        //pp.add(hugoII, "HugoCreateIndex");
        t.add(hugoII, "HugoCreateIndex");
        t.add(new Panel().add(hugo2),"HugoRetrieveIndex");
        //pp.add(hugo2,"HugoRetrieveIndex");

    }

    /**
     * Be sure to specify the name of your application. If the application name is {@code null} or
     * blank, the application will log a warning. Suggested format is "MyCompany-ProductName/1.0".
     */
    private static final String APPLICATION_NAME = "Google-StorageSample/1.1";

    public static void main(String[] args) throws IOException {
  //      GoogleCredentials cred = GoogleCredentials.fromStream(new FileInputStream("C:\\Users\\DGC20\\Downloads\\FinalProject1660-6dd845109432.json")).createScoped("https://www.googleapis.com/auth/cloud-platform")
                HaddopFrame h = new HaddopFrame();
            }

        }

