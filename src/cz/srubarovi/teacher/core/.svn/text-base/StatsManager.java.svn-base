/*
 * StatsManager.java
 *
 * Created on 25. prosinec 2007, 13:31
 */

package cz.srubarovi.teacher.core;

import cz.srubarovi.teacher.QuestionFrame.Status;
import cz.srubarovi.teacher.Settings;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Stepan
 */
public class StatsManager {
    
    public static final StatsManager INSTANCE=new StatsManager();
    
    public static class StatsItem {
        public StatsItem(long timestamp, EnumSet<Status> status) {
            this.timestamp=timestamp;
            this.status=status;
        }
        
        private final long timestamp;
        private final EnumSet<Status> status;

        public long getTimestamp() {
            return timestamp;
        }

        public EnumSet<Status> getStatus() {
            return status.clone();
        }
        
        public String toString() {
            return status.toString()+" "+timestamp;
        }
    }
    
    static class StatsFile {
        
        private String path;
        private Map<String, List<StatsItem>> data=new HashMap<String, List<StatsItem>>();
        
        private static final long HEADERv1=('S' << 8*7) +
                ('t' << 8*6)+
                ('a' << 8*5)+
                ('t' << 8*4)+
                ('s' << 8*3)+
                ('V' << 8*2)+
                ('1' << 8*1)+
                ' ';
        
        public StatsFile(String path) {
            this.path=path;
            try {
                
                load();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        
        public void add(String q, EnumSet<Status> status) {
            StatsItem item=new StatsItem(System.currentTimeMillis(), status);
            
            List<StatsItem> list=data.get(q);
            if(list==null) {
                list=new ArrayList<StatsItem>();
                data.put(q, list);
            }
            
            list.add(item);
            
            try {
                save();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        
        public List<StatsItem> get(String q) {
            List<StatsItem> returnvalue=data.get(q);
            
            if(returnvalue!=null) {
                return new ArrayList<StatsItem>(returnvalue);
            } else {
                return Collections.emptyList();
            }
        }

        private Collection<String> getAll() {
            return Collections.unmodifiableSet(data.keySet());
        }
        
        private File getFile() {
            return new File(Settings.INSTANCE.getProfileStatsDir(), path);
        }
        
        private void load() throws IOException {
            File path=getFile();
            if(!path.exists()) {
                return; //file does not exist - nothing to load
            }
            
            FileInputStream fis=new FileInputStream(path);
            DataInputStream dis=new DataInputStream(fis);
            
            long header=dis.readLong();
            //dos.writeLong(HEADERv1);
            
            
            
            while(dis.available()>0) {
                String q=dis.readUTF();
                
                //read entry count
                int c=dis.readInt();
                
                //read each entry
                ArrayList<StatsItem> statsItems=new ArrayList<StatsItem>(c);
                for (int i = 0; i < c; i++) {
                    statsItems.add(new StatsItem(dis.readLong(), shortToStatus(dis.readShort())));
                }
                
                data.put(q, statsItems);
            }
            
        }
        
        static short statusToShort(EnumSet<Status> set) {
            short result=0;
            
            for(Status s : set) {
                result |= 1 << s.ordinal();
            }
            return result;
        }
        
        static EnumSet<Status> shortToStatus(short num) {
            EnumSet<Status> set=EnumSet.noneOf(Status.class);
            for(Status s : Status.values()) {
                if( ((1 << s.ordinal()) & num) !=0 ) {
                    set.add(s);
                }
            }
            return set;
        }
        
        public void save() throws IOException {
            File path=getFile();
            if(!path.exists()) {
                path.getParentFile().mkdirs();
            }
            
            FileOutputStream fos=new FileOutputStream(path, false);
            DataOutputStream dos=new DataOutputStream(fos);
            
            dos.writeLong(HEADERv1);
            
            for(String q : data.keySet()) {
                dos.writeUTF(q);
                
                //write entry count
                int count=data.get(q).size();
                dos.writeInt(count);
                
                //write each entry
                for(StatsItem item : data.get(q)) {
                    dos.writeLong(item.getTimestamp());
                    dos.writeShort(statusToShort(item.getStatus()));
                }
            }
        }
    }
    
    /** Creates a new instance of StatsManager */
    public StatsManager() {
    }
    
    private Map<String, StatsFile> data=new HashMap<String, StatsFile>();

    public Collection<String> getAll(String path) {
        StatsFile file=getFile(path);
        return file.getAll();
    }
    
    private StatsFile getFile(String path) {
        StatsFile file=data.get(path);
        
        if(file==null) {
            file=new StatsFile(path);
            data.put(path, file);
        }
        return file;
    }
    
    public void add(String path, String question, EnumSet<Status> status) {
        StatsFile file=getFile(path);
        
        file.add(question, status);
    }
    
    public void clearCache() {
        data.clear();
    }
    
    public List<StatsItem> get(String path, String question) {
        StatsFile file=getFile(path);
        
        return file.get(question);
    }
}
