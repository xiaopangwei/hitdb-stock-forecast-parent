package cn.hitdb.enrich.core.model.ml;

import java.io.Serializable;
import java.util.TreeMap;

public class FeatureConfig extends TreeMap<Integer,Double> implements Serializable{
  public FeatureConfig(){
      super();
  }

  public FeatureConfig(int num){
      super();
      for(int i=0;i<num;i++){
          this.put(i,1.0);
      }
  }

  public void update(int index, double value){
      this.put(index,value);
  }

    @Override
    public String toString() {
        return super.toString();
    }
}
